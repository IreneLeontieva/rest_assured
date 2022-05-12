package tests;

import lombok.*;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static specs.SpecificationApi.requestReqresSpec;
import static specs.SpecificationApi.responseSpecification;
import static org.junit.jupiter.api.Assertions.*;

public class RestApiWithLombok {

    @Test
    void registerTest() {
        RegisterAndLoginUser registerAndLoginUser = new RegisterAndLoginUser();
        registerAndLoginUser.setEmail("eve.holt@reqres.in");
        registerAndLoginUser.setPassword("pistol");

        RegisterUserResponse registerAndLoginUserResponse = given()
                .spec(requestReqresSpec)
                .body(registerAndLoginUser)
                .post("register")
                .then()
                .spec(responseSpecification)
                .extract().as(RegisterUserResponse.class);

        assertNotNull(registerAndLoginUserResponse.getId());
        assertNotNull(registerAndLoginUserResponse.getToken());
    }

    @Test
    void getUserTest() {
        given()
                .spec(requestReqresSpec)
                .get("users/4")
                .then()
                .spec(responseSpecification)
                .body("data.first_name", is("Eve"));
    }

    @Test
    void getListUsersTest() {
        given()
                .spec(requestReqresSpec)
                .get("users?page=2")
                .then()
                .spec(responseSpecification)
                .body("total_pages", is(2));
    }

    @Test
    void updateUserTest() {
        CreateAndUpdateUser user = new CreateAndUpdateUser();
        user.setName("morpheus");
        user.setJob("zion resident");

        UpdateUserResponse userResponse = given()
                .spec(requestReqresSpec)
                .body(user)
                .put("users/2")
                .then()
                .spec(responseSpecification)
                .extract().as(UpdateUserResponse.class);

        assertEquals("morpheus", userResponse.getName());
        assertEquals("zion resident", userResponse.getJob());
    }

    @Test
    void createUserTest() {
        CreateAndUpdateUser user = new CreateAndUpdateUser();
        user.setName("morpheus");
        user.setJob("leader");

        CreateUserResponse userResponse = given()
                .spec(requestReqresSpec)
                .body(user)
                .post("users/2")
                .then()
                .statusCode(201)
                .extract().as(CreateUserResponse.class);

        assertEquals("morpheus", userResponse.getName());
        assertEquals("leader", userResponse.getJob());
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getCreatedAt());
    }

    @Test
    void deleteUserTest() {

        given()
                .spec(requestReqresSpec)
                .delete("users/4")
                .then()
                .statusCode(204);
    }

    @Test
    void failedLogTest() {
        RegisterAndLoginUser registerAndLoginUser = new RegisterAndLoginUser();
        registerAndLoginUser.setEmail("peter@klaven");

        RegisterAndLoginUserResponseFailed registerAndLoginUserResponseFailed = given()
                .spec(requestReqresSpec)
                .body(registerAndLoginUser)
                .post("login")
                .then()
                .statusCode(400)
                .extract().as(RegisterAndLoginUserResponseFailed.class);
        assertEquals("Missing password", registerAndLoginUserResponseFailed.getError());
    }

    @Test
    void failedRegisterTest() {
        RegisterAndLoginUser registerAndLoginUser = new RegisterAndLoginUser();
        registerAndLoginUser.setEmail("khomyakov@klaven");

        RegisterAndLoginUserResponseFailed registerAndLoginUserResponseFailed = given()
                .spec(requestReqresSpec)
                .body(registerAndLoginUser)
                .post("register")
                .then()
                .statusCode(400)
                .extract().as(RegisterAndLoginUserResponseFailed.class);
        assertEquals("Missing password", registerAndLoginUserResponseFailed.getError());
    }
}
