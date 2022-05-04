package qaguru;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static listeners.CustomAllureListener.withCustomTemplates;
import static org.hamcrest.Matchers.is;

public class RestApi {

    @Test
    void getUserTest() {
        given()
                .filter(withCustomTemplates())
                .when()
                .get("https://reqres.in/api/users/4")
                .then()
                .statusCode(200)
                .body("data.first_name", is("Eve"));
    }

    @Test
    void getListUsersTest() {
        given()
                .filter(withCustomTemplates())
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total_pages", is(2));
    }

    @Test
    void createUserTest() {
        String data = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users/2")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    @Test
    void updateUserTest() {
        String dataUpdate = "{ \"name\": \"morpheus\", \"job\": \"zion resident\"}";

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(dataUpdate)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"));
    }

    @Test
    void deleteUserTest() {

        given()
                .filter(withCustomTemplates())
                .when()
                .delete("https://reqres.in/api/users/4")
                .then()
                .statusCode(204);
    }

    @Test
    void failedLogTest() {
        String dataReg = "{\"email\": \"peter@klaven\"}";

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(dataReg)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
