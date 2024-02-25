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
    protected Response createCourier(Courier courier) {
        return given()
                .spec(baseRequestSpec())
                .body(courier)
                .post(CREATE_COURIER_ENDPOINT)
                .thenReturn();
    }
    protected void deleteCourier(String login, String password) {
        Response response = loginCourier(login, password);
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
        Order order = orderWithColor(color);
        return given()
                .spec(baseRequestSpec())
                .body(order)
                .post(ORDER_ENDPOINT)
                .thenReturn();
    }

    protected Response getOrderList() {
        return given()
                .spec(baseRequestSpec())
                .get(ORDER_ENDPOINT)
                .thenReturn();
    }

    protected Order orderWithColor(List<String> color) {
        String firstName = "Naruto";
        String lastName = "Uchiha";
        String address = "Konoha, 142 apt.";
        int metroStation = 4;
        String phone = "+7 800 355 35 35";
        int rentTime = 5;
        String deliveryDate = "2024-03-03";
        String comment = "Saske, come back to Konoha";
        return new Order (firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
