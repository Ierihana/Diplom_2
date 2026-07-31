import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static steps.UserSteps.userCreating;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserTests extends BaseApiTest{


    @Test
    @DisplayName("Успешное создание уникального пользователя.")
    @Description("Пользователя можно создать. Статус и код ответа: 200 ОК. " +
            "Успешный запрос возвращает: 'success': true .")
    public void creatingUniqueUser(){
        isUserCreated = true;
        userCreating(user)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Попытка создания неуникального пользователя.")
    @Description("Если пользователь уже существует, то ожидаемый статус и код ответа: 403 Forbidden. " +
            "Ответ: 'success': false 'message': 'User already exists'.")
    public void creatingNonUniqueUser(){
        isUserCreated = true;
        userCreating(user);
        userCreating(user)
                .then()
                .and()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Попытка создания пользователя без пароля.")
    @Description("Если нет одного из полей, то ожидаемый статус и код ответа: 403 Forbidden. " +
            "'success': false, 'message': 'Email, password and name are required fields'.")
    public void creatingUserWithoutPassword(){
        user.setPassword("");
        userCreating(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Попытка создания пользователя без имени.")
    @Description("Если нет одного из полей, то ожидаемый статус и код ответа: 403 Forbidden. " +
            "'success': false, 'message': 'Email, password and name are required fields'.")
    public void creatingUserWithoutUserName(){
        user.setName("");
        userCreating(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Попытка создания пользователя без электронной почты.")
    @Description("Если нет одного из полей, то ожидаемый статус и код ответа: 403 Forbidden. " +
            "'success': false, 'message': 'Email, password and name are required fields'.")
    public void creatingUserWithoutEmail(){
        user.setEmail("");
        userCreating(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
