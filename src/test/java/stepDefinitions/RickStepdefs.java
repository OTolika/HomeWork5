package stepDefinitions;

import api.ApiRick;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

public class RickStepdefs  extends ApiRick {
    JSONObject jsonCharacterMorty;
    JSONObject lastEpisod;
    JSONObject jsonLastCharacter;

    @Дано("загружен персонаж Морти")
    public void loadMorty() {
        jsonCharacterMorty = getMorty();
    }

    @Тогда("персонаж Морти существует")
    public void mortyExists() {
        Assertions.assertNotNull(jsonCharacterMorty);
    }

    @Дано("находим последний эпизод персонажа Морти")
    public void getLastEp() {
        lastEpisod = getLastEpisod(jsonCharacterMorty);
    }

    @Тогда("последний эпизод персонажа Морти существует")
    public void lastEpExists() {
        Assertions.assertNotNull(lastEpisod);
    }
    @Дано("загружаем последнего персонажа последнего эпизода")
    public void getLastChar() {
        String lastCharacter = getLastCharacterURL(lastEpisod);
        jsonLastCharacter = getCharacterByURL(lastCharacter);
    }

    @Тогда("последний персонаж последнего эпизода существует")
    public void lastCharExists() {
        Assertions.assertNotNull(jsonLastCharacter);
    }

    @Тогда("раса персонажей совпадает")
    public void lastCharRaceMatch() {
        String speciesMorty = getSpeciesCharacter(jsonCharacterMorty);
        String speciesLastCharacter = getSpeciesCharacter(jsonLastCharacter);
        Assertions.assertEquals(speciesMorty, speciesLastCharacter);
    }

    @Тогда("местоположение персонажей не совпадает")
    public void lastCharLocationNotMatch() {
        String locationMorty = getLocationCharacter(jsonCharacterMorty);
        String locationLastCharacter = getLocationCharacter(jsonLastCharacter);
        Assertions.assertNotEquals(locationMorty, locationLastCharacter);
    }

}
