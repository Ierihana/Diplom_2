import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static Steps.UserSteps.userCreating;
import static Steps.UserSteps.userLogin;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTests extends BaseApiTest{

    @Test
    @DisplayName("Вход под существующим пользователем.")
    @Description("После создания пользователя, можно авторизоваться с помощью пароля и " +
            "электронной почты. Статус и код ответа: 200 ОК. " +
            "Успешный запрос возвращает: 'success': true .")
    public void loginExistingUser(){
        isUserCreated = true;
        userCreating(user);
        userLogin(user)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Вход с неверным логином и паролем.")
    @Description("Попытка авторизоваться, использую неверный логин и пароль пользователя." +
            "Статус и код ответа:  401: Unauthorized. " +
            "Запрос возвращает: 'message': 'email or password are incorrect'.")
    public void loginWithIncorrectUsernameAndPassword(){
        isUserCreated = true;
        userCreating(user);
        Faker faker = new Faker();
        String correctEmail = user.getEmail();
        String correctPassword = user.getPassword();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        userLogin(user)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
        user.setEmail(correctEmail);
        user.setPassword(correctPassword);
    }
}
