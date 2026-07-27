import Model.OrderModel;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import org.junit.Test;

import static Data.OrderData.*;
import static Steps.OrderSteps.createOrderWithAuthorization;
import static Steps.OrderSteps.createOrderWithoutAuthorization;
import static Steps.UserSteps.userCreating;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderTests extends BaseApiTest{

    @Test
    @DisplayName("Создание заказа с авторизацией пользователя.")
    @Description
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
    @Description
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
    @Description
    public void creatingOrderWithoutIngredients(){
        createOrderWithoutAuthorization()
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo( false))
                .body("message", equalTo("Ingredient ids must be provided"));

    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов.")
    @Description
    public void creatingOrderWithIncorrectHash(){
        OrderModel order = new OrderModel(WRONG_INGREDIENTS_HASH);
        createOrderWithoutAuthorization(order)
                .then()
                .statusCode(HTTP_INTERNAL_ERROR);

    }

}
