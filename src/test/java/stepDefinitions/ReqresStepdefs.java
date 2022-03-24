package stepDefinitions;

import api.ApiReqres;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;


public class ReqresStepdefs extends ApiReqres
{
    JSONObject body;
    JSONObject reqres;
    Response resp;

    @Дано("начальный объект")
    public void начальныйОбъект() {
        body = new JSONObject();
        body.put("name", "Tomato");
        body.put("job", "Eat maket");
    }

    @Когда("объект создан с помощью запроса Post")
    public void createdWithPOST() {
        reqres = checkPost(body);
    }

    @Тогда("объект имеет ожидаемые значения")
    public void hasExpectedValues() {
        Assertions.assertEquals(reqres.getString("name"), body.getString("name"));
        Assertions.assertEquals(reqres.getString("job"), body.getString("job"));
    }

    @Когда("значения объекта изменены")
    public void valuesChanged() {
        body = new JSONObject();
        body.put("name", "TomatoNew");
        body.put("job", "Eat maket");
    }

    @Когда("объект изменен с помощью Put")
    public void changedWithPut() {
        int id = reqres.getInt("id");
        reqres = checkPut(id, body);
    }

    @Когда("объект удален с помощью Delete")
    public void deleted() {
        int id = reqres.getInt("id");
        resp = checkDelete(id);
    }

    @Тогда("возвращается status code {int}")
    public void hasStatusCode(int arg0) {
        Assertions.assertEquals(resp.getStatusCode(), 204);
    }
}