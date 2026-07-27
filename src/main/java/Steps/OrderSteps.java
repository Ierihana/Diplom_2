package Steps;

import Model.OrderModel;
import Model.UserModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static Data.OrderData.CREATE_ORDER;
import static Steps.UserSteps.userLogin;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step
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

    @Step
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

    @Step
    public static Response createOrderWithoutAuthorization() {
        return given()
                .body("{\"ingredients\": []}")
                .post(CREATE_ORDER)
                .then()
                .extract().response();
    }

}


