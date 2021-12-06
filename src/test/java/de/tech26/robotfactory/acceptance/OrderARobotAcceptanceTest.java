package de.tech26.robotfactory.acceptance;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.tech26.robotfactory.domain.RobotPartType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension.class)
public class OrderARobotAcceptanceTest {

    @LocalServerPort
    private int springBootPort;

    public int getSpringBootPort() {
        return springBootPort;
    }

    @Test
    //$FF was: should order a robot
    public void shouldOrderRobot() {
        this.postOrder("\n { \n\"components\": [\"I\",\"A\",\"D\",\"F\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.CREATED.value())
            .body("order_id", CoreMatchers.notNullValue())
            .body("total", CoreMatchers.equalTo(160.11F));

    }

    @Test
    //$FF was: should not allow unsufficient stock
    public void shouldNotAllowUnsufficientStock() {
        this.postOrder("\n { \n\"components\": [\"I\",\"C\",\"D\",\"F\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.NOT_ACCEPTABLE.value())
            .body("message", CoreMatchers.equalTo("Stock content for code C is insufficient"));
    }

    @Test
    //$FF was: should not allow non-existing catalog item
    public void shouldNotAllowNotExistingDomain() {
        this.postOrder("\n { \n\"components\": [\"I\",\"C\",\"D\",\"Z\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
            .body("message", CoreMatchers.equalTo("Catalog Item for code Z is not found"));
    }

    @Test
    //    $FF was: should not allow invalid robot configuration( missing part)
    public void shouldNotAllowInvalidRobotConfigurationWrongPartsNUmber() {
        this.postOrder(
                "\n                    {\n                        \"components\": [\"A\", \"C\", \"I\", \"D\", \"I\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .body("error", CoreMatchers.equalTo("Unprocessable Entity"))
            .body("message", CoreMatchers.equalTo(
                String.format("Wrong number of robot components ordered ( required = %d , actual %d )",
                    RobotPartType.values().length, 5)));
    }

    @Test
    //$FF was: should not allow invalid body
    public void shouldNotAllowInvalidBody() {
        this.postOrder(
                "\n                    { \n                        \"components\": \"BENDER\"\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    //$FF was: should not allow invalid body
    public void shouldNotAllowInvalidBody2() {
        this.postOrder(
                "\n                    { \n                        \"components\": []                   }\n                ")
            .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    //    $FF was: should not allow invalid robot configuration( missing part)
    public void shouldNotAllowInvalidRobotConfiguration() {
        this.postOrder(
                "\n                    {\n                        \"components\": [\"A\", \"C\", \"I\", \"D\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .body("error", CoreMatchers.equalTo("Unprocessable Entity"))
            .body("message", CoreMatchers.equalTo("Robot components mismatch: missing parts - [MOBILITY]"));
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
