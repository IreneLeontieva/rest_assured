package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static listeners.CustomAllureListener.withCustomTemplates;

public class SpecificationApi {
    public static RequestSpecification requestReqresSpec = with()
            .baseUri("https://reqres.in/api/")
            .filter(withCustomTemplates())
            .log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
