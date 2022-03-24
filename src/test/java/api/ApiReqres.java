package api;

import hooks.Hooks;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class ApiReqres extends Hooks
{
    RequestSpecification requestSpecReqres;

    ResponseSpecification responseSpecReqresPost = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

    ResponseSpecification responseSpecReqresPut = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    ResponseSpecification responseSpecReqresDelete = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

    public ApiReqres()
    {
        String url = getProperty("urlReqres");
        requestSpecReqres = new RequestSpecBuilder()
            .build()
            .given().baseUri(url)
            .contentType("application/json; charset=utf-8")
            .log().all();
    }

    public JSONObject checkPost(JSONObject body)
    {
        Response respReqresPost = given(requestSpecReqres)
                .when()
                .body(body.toString())
                .post("/api/users")
                .then()
                .spec(responseSpecReqresPost)
                .extract()
                .response();

        String respRP = respReqresPost.getBody().asString();
        JSONObject jsonReqresPost = new JSONObject(respRP);
        return jsonReqresPost;
    }

    public JSONObject checkPut(int id, JSONObject body)
    {
        Response respReqresPut = given(requestSpecReqres)
                .when()
                .body(body.toString())
                .put("/api/users/" + id)
                .then()
                .spec(responseSpecReqresPut)
                .extract()
                .response();

        String respRP = respReqresPut.getBody().asString();
        JSONObject jsonReqresPut = new JSONObject(respRP);
        return jsonReqresPut;
    }

    public Response checkDelete(int id)
    {
        Response respReqresDelete = given(requestSpecReqres)
                .when()
                .delete("/api/users/" + id)
                .then()
                .spec(responseSpecReqresDelete)
                .extract()
                .response();
        return respReqresDelete;
    }
}
