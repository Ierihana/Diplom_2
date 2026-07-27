import Model.UserModel;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import static Data.UserData.*;
import static Data.UserData.USER_NAME;
import static Steps.UserSteps.userDelete;

public class BaseApiTest {
    UserModel user;
    protected boolean isUserCreated = false;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        user = new UserModel(USER_EMAIL, USER_PASSWORD, USER_NAME);

    }

    @After
    public void cleanUp() {
        if (isUserCreated) {
            userDelete(user);
        }

    }
}
