import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.specification.ProxySpecification.auth;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Praktikum {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    public void checkUserName() {
        given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NjNjM2I5NjFiYjBmMDAwM2Q3MjJlMWEiLCJpYXQiOjE3MTY5ODgyMTksImV4cCI6MTcxNzU5MzAxOX0.nUBh9YrIoNf5m9rt9Y7Eqt15H8PfkCLqioOi2CtTjEg")
                .get("/api/cards")
                .then().statusCode(200);
    }

    @Test
    public void createNewPlaceAndCheckResponse(){
        File json = new File("src/test/resources/newCard.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NjNjM2I5NjFiYjBmMDAwM2Q3MjJlMWEiLCJpYXQiOjE3MTY5ODgyMTksImV4cCI6MTcxNzU5MzAxOX0.nUBh9YrIoNf5m9rt9Y7Eqt15H8PfkCLqioOi2CtTjEg")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/cards");
        response.then().assertThat().body("data._id", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    public void updateProfile() {
        Profile profile  = new Profile("Василий Васильев", "Самый крутой исследователь"); // создай объект, который соответствует JSON
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NjNjM2I5NjFiYjBmMDAwM2Q3MjJlMWEiLCJpYXQiOjE3MTY5ODgyMTksImV4cCI6MTcxNzU5MzAxOX0.nUBh9YrIoNf5m9rt9Y7Eqt15H8PfkCLqioOi2CtTjEg")
                .and()
                .body(profile)
                .when()
                .patch("/api/users/me");
    }

    @Test
    public void checkUserName2() {
        User user = given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NjNjM2I5NjFiYjBmMDAwM2Q3MjJlMWEiLCJpYXQiOjE3MTY5ODgyMTksImV4cCI6MTcxNzU5MzAxOX0.nUBh9YrIoNf5m9rt9Y7Eqt15H8PfkCLqioOi2CtTjEg")
                .get("/api/users/me")
                .body().as(User.class);
        // напиши код для десериализации ответа в объект типа User
        // выбери подходящий assert
        MatcherAssert.assertThat(user, notNullValue());
    }

}