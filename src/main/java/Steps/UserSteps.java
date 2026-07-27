package Steps;

import Model.UserLoginModel;
import Model.UserModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static Data.UserData.*;
import static io.restassured.RestAssured.*;

public class UserSteps {

    @Step ("Создание пользователя: {user}")
    public static Response userCreating(UserModel user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(USER_REGISTER_API)
                .then()
                .extract().response();

    }
    @Step
    public static Response userLogin(UserModel user){
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        UserLoginModel loginUser = new UserLoginModel(userEmail, userPassword);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginUser)
                .when()
                .post(USER_LOGIN_API)
                .then()
                .extract().response();
    }

    @Step
    public static void userDelete(UserModel user){
        String fullAccessToken = userLogin(user).path("accessToken").toString();
        String accessToken = fullAccessToken.substring(7);
        given()
                .auth().oauth2(accessToken)
                .when()
                .delete(USER_DELETE_API)
                .then()
                .extract().response();
    }

}
