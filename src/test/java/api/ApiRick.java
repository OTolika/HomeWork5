package api;

import hooks.Hooks;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class ApiRick extends Hooks
{
    public RequestSpecification requestSpec;

    public ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public ApiRick()
    {
        String url = getProperty("urlRick");
        requestSpec = new RequestSpecBuilder()
                .build()
                .given().baseUri(url)
                .contentType(ContentType.JSON)
                .log().all();
    }

    public JSONObject getMorty()
    {
        Response respCharacterMorty = given(requestSpec)
                .when()
                .get("/api/character/?name=Morty")
                .then()
                .spec(responseSpec)
                .extract()
                .response();

        String resp = respCharacterMorty.getBody().asString();
        JSONObject jsonCharacterMorty = new JSONObject(resp);
        return jsonCharacterMorty.getJSONArray("results").getJSONObject(0);
    }

    public JSONObject getCharacterByURL(String url)
    {
        Response respCharacter = given(requestSpec)
                .when()
                .get(url)
                .then()
                .spec(responseSpec)
                .extract()
                .response();

        String resp = respCharacter.getBody().asString();
        JSONObject jsonCharacter = new JSONObject(resp);
        return jsonCharacter;
    }

    public String getLastCharacterURL(JSONObject episod)
    {
        JSONArray objects = episod.getJSONArray("characters");
        int lenght = objects.length();
        String lastCharacter = objects.getString(lenght - 1);
        return lastCharacter;
    }

    public JSONObject getLastEpisod(JSONObject character)
    {
        JSONArray obj = character.getJSONArray("episode");
        int lenght = obj.length();
        String lastEpisod = obj.getString(lenght - 1);

        Response respLastEpisod = given(requestSpec)
                .when()
                .get(lastEpisod)
                .then()
                .spec(responseSpec)
                .extract()
                .response();

        String resp = respLastEpisod.getBody().asString();
        JSONObject jsonLastEpisod = new JSONObject(resp);
        return  jsonLastEpisod;
    }

    public String getSpeciesCharacter (JSONObject jsonCharacter)
    {
        String speciesCharacter = jsonCharacter.getString("species");
        return speciesCharacter;
    }

    public String getLocationCharacter (JSONObject jsonCharacter)
    {
        String locationCharacter = jsonCharacter.getJSONObject("location").getString("name");
        return locationCharacter;
    }


}
