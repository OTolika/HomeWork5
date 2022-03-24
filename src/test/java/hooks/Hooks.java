package hooks;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class Hooks extends PropertyReader
{
    @BeforeAll
    public static void beforeAll() throws Exception
    {
        RestAssured.filters(new AllureRestAssured());
    }
}
