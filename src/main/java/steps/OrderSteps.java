package steps;

import model.OrderModel;
import model.UserModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static data.OrderData.CREATE_ORDER;
import static steps.UserSteps.userLogin;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказ с авторизацией.")
    public static Response createOrderWithAuthorization(UserModel user, OrderModel order){
        String fullAccessToken = userLogin(user).path("accessToken").toString();
        String accessToken = fullAccessToken.substring(7);
        return given()
                .auth().oauth2(accessToken)
                .header("content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then()
                .extract().response();
    }

    @Step("Создание заказа без авторизации.")
    public static Response createOrderWithoutAuthorization(OrderModel order){
        return given()
                .header("content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then()
                .extract().response();
    }

    @Step("Создание заказа без ингредиентов в теле запроса.")
    public static Response createOrderWithoutAuthorization() {
        return given()
                .body("{\"ingredients\": []}")
                .post(CREATE_ORDER)
                .then()
                .extract().response();
    }

}


