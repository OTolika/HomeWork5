import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matcher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class APITest
{
    @BeforeAll
    public static void beforeAll() throws Exception
    {
            RestAssured.filters(new AllureRestAssured());
    }

    //First exercise
    RequestSpecification requestSpec = new RequestSpecBuilder()
            .build()
            .given().baseUri("https://rickandmortyapi.com")
            .contentType(ContentType.JSON)
            .log().all();

    ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    @Tag("1api")
    @Test
    @DisplayName("Персонаж Морти")
    public void morty()
    {
        //Step 1
        System.out.println("Первый тест про Морти");
        Response respCharacterMorty = given(requestSpec)
                .when()
                .get("/api/character/?name=Morty")
                .then()
                .spec(responseSpec)
                .extract()
                .response();

        String resp = respCharacterMorty.getBody().asString();
        JSONObject jsonCharacterMorty = new JSONObject(resp);

        //Step 2
        JSONArray obj = jsonCharacterMorty.getJSONArray("results").getJSONObject(0).getJSONArray("episode");
        int lenght = obj.length();
        String lastEpisodMorty = obj.getString(lenght-1);

        String speciesMorty = jsonCharacterMorty.getJSONArray("results").getJSONObject(0).getString("species");
        String locationMorty = jsonCharacterMorty.getJSONArray("results").getJSONObject(0).getJSONObject("location").getString("name");

        //Step 3
        Response respLastEpisodMorty = given(requestSpec)
                .when()
                .get(lastEpisodMorty)
                .then()
                .spec(responseSpec)
                .extract()
                .response();

        resp = respLastEpisodMorty.getBody().asString();
        JSONObject jsonLastEpisodMorty = new JSONObject(resp);

        JSONArray objects = jsonLastEpisodMorty.getJSONArray("characters");
        lenght = objects.length();
        String lastCharacter = objects.getString(lenght-1);

        //Step 4
        Response respLastCharacter = given(requestSpec)
                .when()
                .get(lastCharacter)
                .then()
                .spec(responseSpec)
                .extract()
                .response();

        resp = respLastCharacter.getBody().asString();
        JSONObject jsonLastCharacter = new JSONObject(resp);

        String speciesLastCharacter = jsonLastCharacter.getString("species");
        String locationLastCharacter = jsonLastCharacter.getJSONObject("location").getString("name");

        //Step 5
        if (speciesMorty.equals(speciesLastCharacter))
        {
            System.out.println("Раса Морти такая же как раса персонажа");
        }
        else
        {
            System.out.println("Раса Морти НЕ такая же как раса персонажа");
        }

        if (locationMorty.equals(locationLastCharacter))
        {
            System.out.println("Местонахождение Морти такое же как местонахождение персонажа");
        }
        else
        {
            System.out.println("Местонахождение Морти НЕ такое же как местонахождение персонажа");
        }
    }

    //Second exercise
    RequestSpecification requestSpecReqres = new RequestSpecBuilder()
            .build()
            .given().baseUri("https://reqres.in/")
            .contentType("application/json; charset=utf-8")
            .log().all();

    ResponseSpecification responseSpecReqresPost = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

    ResponseSpecification responseSpecReqresPut = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    ResponseSpecification responseSpecReqresDelete = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

    @Tag("2api")
    @Test
    @DisplayName("Reqres")
    public void reqres()
    {
        System.out.println("Второй тест про Reqres");
        //Post
        JSONObject bodyPost = new JSONObject();
        bodyPost.put("name", "Tomato");
        bodyPost.put("job", "Eat maket");

        Response respReqresPost = given(requestSpecReqres)
                .when()
                .body(bodyPost.toString())
                .post("/api/users")
                .then()
                .spec(responseSpecReqresPost)
                .extract()
                .response();

        String respRP = respReqresPost.getBody().asString();
        JSONObject jsonReqresPost = new JSONObject(respRP);
        int id = jsonReqresPost.getInt("id");

        Assertions.assertEquals(jsonReqresPost.getString("name"), bodyPost.getString("name"));
        Assertions.assertEquals(jsonReqresPost.getString("job"), bodyPost.getString("job"));
        System.out.println("Name и job для POST совпадают");

        //Put
        JSONObject bodyPut = new JSONObject();
        bodyPut.put("name", "TomatoNew");
        bodyPut.put("job", "Eat maket");

        Response respReqresPut = given(requestSpecReqres)
                .when()
                .body(bodyPut.toString())
                .put("/api/users/" + id)
                .then()
                .spec(responseSpecReqresPut)
                .extract()
                .response();

        respRP = respReqresPut.getBody().asString();
        JSONObject jsonReqresPut = new JSONObject(respRP);

        Assertions.assertEquals(jsonReqresPut.getString("name"), bodyPut.getString("name"));
        Assertions.assertEquals(jsonReqresPut.getString("job"), bodyPut.getString("job"));
        System.out.println("Name и job для PUT совпадают");

        //Delete
        Response respReqresDelete = given(requestSpecReqres)
                .when()
                .delete("/api/users/" + id)
                .then()
                .spec(responseSpecReqresDelete)
                .extract()
                .response();

        Assertions.assertEquals(respReqresDelete.getStatusCode(), 204);
        System.out.println("Запись с номером " + id + " удалена");

    }
}
