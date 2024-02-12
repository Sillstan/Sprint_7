import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginCourierTest {
    private Courier courier;
    private int statusCode;

    @Before
    public void setUp() {
        RestAssured.baseURI = ScooterAPI.URL;
    }

    @Test
    @DisplayName("Логин курьера с валидными данными")
    public void testLoginValidCourier() {
        courier = new Courier("sarmat", "1234", "Sasha");
        Response response = new ScooterAPI().createCourier();
        statusCode = response.getStatusCode();
        Response responseSecond = new ScooterAPI().loginCourier(courier.getLogin(), courier.getPassword());
        assertEquals(200, responseSecond.getStatusCode());
        assertNotNull(responseSecond.jsonPath().getString("id"));
    }

    @Test
    @DisplayName("Логин курьера с пустым полем login")
    public void testLoginCourierWithoutLogin() {
        courier = new Courier("sarmat", "1234", "Sasha");
        Response response = new ScooterAPI().createCourier();
        statusCode = response.getStatusCode();
        Response responseSecond = new ScooterAPI().loginCourier("", courier.getPassword());
        assertEquals(400, responseSecond.getStatusCode());
        JsonPath jsonPath = responseSecond.getBody().jsonPath();
        String actualMessage = jsonPath.getString("message");
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Логин курьера с пустым полем password")
    public void testLoginCourierWithoutPassword() {
        courier = new Courier("sarmat", "1234", "Sasha");
        Response response = new ScooterAPI().createCourier();
        statusCode = response.getStatusCode();
        Response responseSecond = new ScooterAPI().loginCourier(courier.getLogin(), "");
        assertEquals(400, responseSecond.getStatusCode());
        JsonPath jsonPath = responseSecond.getBody().jsonPath();
        String actualMessage = jsonPath.getString("message");
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Логин курьера с невалидными данными в поле login")
    public void testLoginCourierWithInvalidLogin() {
        courier = new Courier("sarmat", "1234", "Sasha");
        Response response = new ScooterAPI().createCourier();
        statusCode = response.getStatusCode();
        Response responseSecond = new ScooterAPI().loginCourier("sodomitataspalanskii", courier.getPassword());
        assertEquals(404, responseSecond.getStatusCode());
        JsonPath jsonPath = responseSecond.getBody().jsonPath();
        String actualMessage = jsonPath.getString("message");
        String expectedMessage = "Учетная запись не найдена";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Логин курьера с невалидными данными в поле password")
    public void testLoginCourierWithInvalidPassword() {
        courier = new Courier("sarmat", "1234", "Sasha");
        Response response = new ScooterAPI().createCourier();
        statusCode = response.getStatusCode();
        Response responseSecond = new ScooterAPI().loginCourier(courier.getLogin(), "0000");
        assertEquals(404, responseSecond.getStatusCode());
        JsonPath jsonPath = responseSecond.getBody().jsonPath();
        String actualMessage = jsonPath.getString("message");
        String expectedMessage = "Учетная запись не найдена";
        assertEquals(expectedMessage, actualMessage);
    }

    @After
    public void tearDown() {
        if (statusCode < 300) {
            ScooterAPI scooterAPI = new ScooterAPI();
            scooterAPI.deleteCourier();
        }
    }
}
