package Data;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;

public class UserData {
    public static final String BASE_URI = RestAssured.baseURI = "https://stellarburgers.education-services.ru/";
    static Faker faker = new Faker();
    public static final String USER_NAME = faker.name().username() + System.currentTimeMillis();
    public static final String USER_PASSWORD = faker.internet().password();
    public static final String USER_EMAIL = faker.internet().emailAddress();
    public static final String USER_REGISTER_API = "/api/auth/register";
    public static final String USER_LOGIN_API = "/api/auth/login";
    public static final String USER_DELETE_API = "/api/auth/user";

}
