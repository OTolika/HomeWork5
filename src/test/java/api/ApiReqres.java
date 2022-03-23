package api;

import hooks.Hooks;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class ApiReqres extends Hooks
{
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

    public int checkPost()
    {
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

        return id;
    }

    public void checkPut(int id)
    {
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

        String respRP = respReqresPut.getBody().asString();
        JSONObject jsonReqresPut = new JSONObject(respRP);

        Assertions.assertEquals(jsonReqresPut.getString("name"), bodyPut.getString("name"));
        Assertions.assertEquals(jsonReqresPut.getString("job"), bodyPut.getString("job"));
        System.out.println("Name и job для PUT совпадают");
    }

    public void checkDelete(int id)
    {
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
