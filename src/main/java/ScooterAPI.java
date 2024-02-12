import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ScooterAPI {
    public static final String URL = "http://qa-scooter.praktikum-services.ru";
    public static final String CREATE_COURIER_ENDPOINT = "/api/v1/courier";
    public static final String LOGIN_COURIER_ENDPOINT = "/api/v1/courier/login";
    public static final String DELETE_COURIER_ENDPOINT = "/api/v1/courier/";
    public static final String ORDER_ENDPOINT = "/api/v1/orders";
    private RequestSpecification baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }
    protected Response createCourier() {
        return given()
                .spec(baseRequestSpec())
                .body("{ \"login\": \"" + Courier.login + "\", \"password\": \"" + Courier.password + "\", \"firstName\": \"" + Courier.firstName + "\" }")
                .post(CREATE_COURIER_ENDPOINT)
                .thenReturn();
    }
    protected void deleteCourier() {
        Response response = loginCourier(Courier.login, Courier.password);
        int id = response.jsonPath().getInt("id");
        given()
                .spec(baseRequestSpec())
                .when()
                .delete(DELETE_COURIER_ENDPOINT + id)
                .thenReturn();
    }

    protected Response loginCourier(String login, String password) {
        return given()
                .spec(baseRequestSpec())
                .body("{ \"login\": \"" + login + "\", \"password\": \"" + password + "\" }")
                .when()
                .post(LOGIN_COURIER_ENDPOINT)
                .thenReturn();
    }

    protected Response createOrderWithColor(List<String> color) {
        StringBuilder colorJson = new StringBuilder("[");
        for (int i = 0; i < color.size(); i++) {
            colorJson.append("\"").append(color.get(i)).append("\"");
            if (i < color.size() - 1) {
                colorJson.append(", ");
            }
        }
        colorJson.append("]");
        String requestBody = "{" +
                "\"firstName\": \"Naruto\"," +
                "\"lastName\": \"Uchiha\"," +
                "\"address\": \"Konoha, 142 apt.\"," +
                "\"metroStation\": 4," +
                "\"phone\": \"+7 800 355 35 35\"," +
                "\"rentTime\": 5," +
                "\"deliveryDate\": \"2024-03-03\"," +
                "\"comment\": \"Saske, come back to Konoha\"," +
                "\"color\": " + colorJson.toString() +
                "}";
        return given()
                .spec(baseRequestSpec())
                .body(requestBody)
                .post(ORDER_ENDPOINT)
                .thenReturn();
    }

    protected Response getOrderList() {
        return given()
                .spec(baseRequestSpec())
                .get(ORDER_ENDPOINT)
                .thenReturn();
    }
}
