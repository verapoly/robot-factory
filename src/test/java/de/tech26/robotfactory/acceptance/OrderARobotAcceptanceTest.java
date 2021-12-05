package de.tech26.robotfactory.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import kotlin.Metadata;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@ExtendWith(MockitoExtension.class)
public class OrderARobotAcceptanceTest {

    @LocalServerPort
    private final int springBootPort = 8090;

    @Autowired
    ApplicationContext context;

    public int getSpringBootPort() {
        return this.springBootPort;
    }

    @Test
    //$FF was: should order a robot
    public void shouldOrderRobot() {
        this.postOrder("\n { \n\"components\": [\"I\",\"A\",\"D\",\"F\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.CREATED.value())
            .body("order_id", CoreMatchers.notNullValue(), new Object[0])
            .body("total", CoreMatchers.equalTo(160.11F));
    }

    @Test
    //$FF was: should not allow invalid body
    public void shouldNotAllowInvalidBody() {
        this.postOrder(
                "\n                    { \n                        \"components\": \"BENDER\"\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    //    $FF was: should not allow invalid robot configuration
    public void shouldNotAllowInvalidRobotConfiguration() {
        this.postOrder(
                "\n                    {\n                        \"components\": [\"A\", \"C\", \"I\", \"D\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    //    $FF was: should not allow invalid robot configuration
    public void shouldNotAllowUnavailableStockItems() {
        this.postOrder(
                "\n                    {\n                        \"components\": [\"A\", \"C\", \"I\", \"D\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    private Response postOrder(final String body) {
        return RestAssured.given().body(body).contentType(ContentType.JSON)
            .when().port(this.getSpringBootPort()).post("/orders");
    }
}
