package lde.tech26.robotfactory.acceptance;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import kotlin.Metadata;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@Metadata(
    mv = {1, 1, 18},
    bv = {1, 0, 3},
    k = 1,
    d1 = {
        "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0017\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\rH\u0017J\b\u0010\u000e\u001a\u00020\rH\u0017J\b\u0010\u000f\u001a\u00020\rH\u0017R\u0016\u0010\u0003\u001a\u00020\u00048\u0016X\u0097D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"},
    d2 = {"Lde/tech26/robotfactory/acceptance/lde.tech26.robotfactory.acceptance.OrderARobotAcceptanceTest;", "", "()V", "springBootPort", "",
        "getSpringBootPort", "()I", "postOrder", "Lio/restassured/response/Response;", "kotlin.jvm.PlatformType",
        "body", "", "should not allow invalid body", "", "should not allow invalid robot configuration",
        "should order a robot", "robot-factory"}
)
public class OrderARobotAcceptanceTest {

    @LocalServerPort
    private final int springBootPort = 0;

    public int getSpringBootPort() {
        return this.springBootPort;
    }

    @Test
    /* $FF was: should order a robot*/
    public void shouldOrderRobot() {
        this.postOrder("\n { \n\"components\": [\"I\",\"A\",\"D\",\"F\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.CREATED.value())
            .body("order_id", CoreMatchers.notNullValue(), new Object[0])
            .body("total", CoreMatchers.equalTo(160.11F), new Object[0]);
    }

    @Test
    /* $FF was: should not allow invalid body*/
    public void shouldNotAllowInvalid_body() {
        this.postOrder(
                "\n                    { \n                        \"components\": \"BENDER\"\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    /* $FF was: should not allow invalid robot configuration*/
    public void should_not_allow_invalid_robot_configuration() {
        this.postOrder(
                "\n                    {\n                        \"components\": [\"A\", \"C\", \"I\", \"D\"]\n                    }\n                ")
            .then().assertThat().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    private final Response postOrder(String body) {
        return RestAssured.given().body(body).contentType(ContentType.JSON)
            .when().port(this.getSpringBootPort()).post("/orders", new Object[0]);
    }
}
