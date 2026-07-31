import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.UserModel;
import org.junit.Before;
import org.junit.Test;

import static data.UserData.*;
import static data.UserData.USER_NAME;
import static steps.UserSteps.userCreating;
import static steps.UserSteps.userLogin;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTests extends BaseApiTest{

    @Override
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        user = new UserModel(USER_EMAIL, USER_PASSWORD, USER_NAME);
        isUserCreated = true;
        userCreating(user);
    }

    @Test
    @DisplayName("Вход под существующим пользователем.")
    @Description("После создания пользователя, можно авторизоваться с помощью пароля и " +
            "электронной почты. Статус и код ответа: 200 ОК. " +
            "Успешный запрос возвращает: 'success': true .")
    public void loginExistingUser(){
        userLogin(user)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Вход с неверным логином.")
    @Description("Попытка авторизоваться, используя неверный логин пользователя." +
            "Ожидаемый статус и код ответа:  401: Unauthorized. " +
            "Запрос возвращает: 'message': 'email or password are incorrect'.")
    public void loginWithIncorrectUsername(){
        Faker faker = new Faker();
        String correctEmail = user.getEmail();
        user.setEmail(faker.internet().emailAddress());
        userLogin(user)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
        user.setEmail(correctEmail);
    }

    @Test
    @DisplayName("Вход с неверным паролем.")
    @Description("Попытка авторизоваться, используя неверный пароль пользователя." +
            "Ожидаемый статус и код ответа:  401: Unauthorized. " +
            "Запрос возвращает: 'message': 'email or password are incorrect'.")
    public void loginWithIncorrectPassword(){
        Faker faker = new Faker();
        String correctPassword = user.getPassword();
        user.setPassword(faker.internet().password());
        userLogin(user)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
        user.setPassword(correctPassword);
    }
}
