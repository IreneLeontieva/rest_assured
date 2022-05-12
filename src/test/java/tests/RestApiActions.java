package tests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static listeners.CustomAllureListener.withCustomTemplates;
import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;

public class RestApiActions {

    @Owner("ileonteva")
    @DisplayName("SubScribe demowebshop")
    @Test
    void subScribe() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("email=cynic1394@gmail.com")
                .when()
                .post("http://demowebshop.tricentis.com/subscribenewsletter")
                .then()
                .log().all()
                .statusCode(200)
                .body("Success", is(true))
                .body("Result", is("Thank you for signing up! A verification email has been sent. We appreciate your interest."));
    }

    @Owner("ileonteva")
    @DisplayName("Add to card with cookies")
    @Test
    void addToCardWithCookies() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=61355710-3800-4d15-9c52-9261258a4e44;")
                .body("product_attribute_72_5_18=53&product_attribute_72_6_19=54&product_attribute_72_3_20=57&addtocart_72.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }

    @Owner("ileonteva")
    @DisplayName("Add to card without cookies")
    @Test
    void addToWishListWithoutCookies() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("addtocart_78.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/78/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"));
    }
}
