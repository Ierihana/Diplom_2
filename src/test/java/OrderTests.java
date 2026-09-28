import model.OrderModel;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import org.junit.Test;

import static data.OrderData.*;
import static steps.OrderSteps.createOrderWithAuthorization;
import static steps.OrderSteps.createOrderWithoutAuthorization;
import static steps.UserSteps.userCreating;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderTests extends BaseApiTest{

    @Test
    @DisplayName("Создание заказа с авторизацией пользователя.")
    @Description("Можно создать заказ с авторизованным пользователем. Статус и код ответа: 200 OK. " +
            "Успешный запрос возвращает: success true, а также список ингредиентов заказа")
    public void creatingOrderWithAuthorization(){
        OrderModel order = new OrderModel(INGREDIENTS_HASH);
        userCreating(user);
        createOrderWithAuthorization(user, order)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.ingredients", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации.")
    @Description("Можно создать заказ без авторизации пользователя. Статус и код ответа: 200 OK. " +
            "Успешный запрос возвращает: success true, а также номер заказа.")
    public void creatingOrderWithoutAuthorization(){
        OrderModel order = new OrderModel(INGREDIENTS_HASH);
        createOrderWithoutAuthorization(order)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }


    @Test
    @DisplayName("Создание заказа без ингредиентов.")
    @Description("При создании заказа без ингредиентов в теле запроса, статус и код ответа: 400 Bad Request. " +
            "Запрос возвращает: 'success': false " +
            "'message': 'Ingredient ids must be provided'.")
    public void creatingOrderWithoutIngredients(){
        createOrderWithoutAuthorization()
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo( false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов.")
    @Description("При создании заказа с неверным хешем ингредиентов в теле запроса, статус и код ответа: 500: Internal Server Error.")
    public void creatingOrderWithIncorrectHash(){
        OrderModel order = new OrderModel(WRONG_INGREDIENTS_HASH);
        createOrderWithoutAuthorization(order)
                .then()
                .statusCode(HTTP_INTERNAL_ERROR);
    }
}
