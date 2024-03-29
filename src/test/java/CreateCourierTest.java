import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateCourierTest {
    private Courier courier;
    private int statusCode;

    @Before
    public void setUp() {
        RestAssured.baseURI = ScooterAPI.URL;
    }

    @Test
    @DisplayName("Создание курьера со всеми полями")
    public void testCreateCourier() {
        courier = new Courier("sarmat", "1234", "Sasha");
        Response response = new ScooterAPI().createCourier(courier);
        statusCode = response.getStatusCode();
        assertEquals(201, statusCode);
        String expectedBody = "{\"ok\":true}";
        assertEquals(expectedBody, response.getBody().asString());
    }

    @Test
    @DisplayName("Создание курьера с занятым логином")
    public void testCreateDoubleCourier() {
        courier = new Courier("sarmat", "1234", "Sasha");
        Response response = new ScooterAPI().createCourier(courier);
        Response responseSecond = new ScooterAPI().createCourier(courier);
        statusCode = response.getStatusCode();
        assertEquals(409, responseSecond.getStatusCode());
        JsonPath jsonPath = responseSecond.getBody().jsonPath();
        String actualMessage = jsonPath.getString("message");
        String expectedMessage = "Этот логин уже используется";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void testCreateCourierWithoutLogin() {
        courier = new Courier("", "1234", "Sasha");
        Response response = new ScooterAPI().createCourier(courier);
        statusCode = response.getStatusCode();
        assertEquals(400, statusCode);
        JsonPath jsonPath = response.getBody().jsonPath();
        String actualMessage = jsonPath.getString("message");
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void testCreateCourierWithoutPassword() {
        courier = new Courier("sarmat", "", "Sasha");
        Response response = new ScooterAPI().createCourier(courier);
        statusCode = response.getStatusCode();
        assertEquals(400, statusCode);
        JsonPath jsonPath = response.getBody().jsonPath();
        String actualMessage = jsonPath.getString("message");
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Создание курьера без имени")
    public void testCreateCourierWithoutFirstName() {
        courier = new Courier("sarmat", "1234", "");
        Response response = new ScooterAPI().createCourier(courier);
        statusCode = response.getStatusCode();
        assertEquals(201, statusCode);
        String expectedBody = "{\"ok\":true}";
        assertEquals(expectedBody, response.getBody().asString());
    }

    @After
    public void tearDown() {
        if (statusCode == 201) {
            ScooterAPI scooterAPI = new ScooterAPI();
            scooterAPI.deleteCourier(courier.getLogin(), courier.getPassword());
        }
    }
}
