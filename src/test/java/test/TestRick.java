package test;

import api.ApiRick;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TestRick extends ApiRick
{
    @Tag("TestRick")
    @Test
    @DisplayName("Персонаж Морти")
    public void morty() {
        JSONObject jsonCharacterMorty = getMorty();
        Assertions.assertNotNull(jsonCharacterMorty);
    }

    @Tag("TestRick")
    @Test
    @DisplayName("Последний эпизод персонажа Морти")
    public void lastMortyEpisode()
    {
        JSONObject jsonCharacterMorty = getMorty();
        JSONObject lastEpisod = getLastEpisod(jsonCharacterMorty);
        Assertions.assertNotNull(lastEpisod);

    }

    @Tag("TestRick")
    @Test
    @DisplayName("Последний персонаж последнего эпизода персонажа Морти")
    public void lastMortyEpisodeCharacter() {
        JSONObject jsonCharacterMorty = getMorty();
        JSONObject lastEpisod = getLastEpisod(jsonCharacterMorty);

        String lastCharacter = getLastCharacterURL(lastEpisod);
        JSONObject jsonLastCharacter = getCharacterByURL(lastCharacter);

        Assertions.assertNotNull(jsonLastCharacter);
    }


    @Tag("TestRick")
    @Test
    @DisplayName("Последний персонаж последнего эпизода персонажа Морти: НЕсовпадение расы и места")
    public void lastMortyEpisodeCharacterMatch() {
        JSONObject jsonCharacterMorty = getMorty();
        JSONObject lastEpisod = getLastEpisod(jsonCharacterMorty);

        String lastCharacter = getLastCharacterURL(lastEpisod);
        JSONObject jsonLastCharacter = getCharacterByURL(lastCharacter);

        String speciesMorty = getSpeciesCharacter(jsonCharacterMorty);
        String locationMorty = getLocationCharacter(jsonCharacterMorty);

        String speciesLastCharacter = getSpeciesCharacter(jsonLastCharacter);
        String locationLastCharacter = getLocationCharacter(jsonLastCharacter);

        Assertions.assertEquals(speciesMorty, speciesLastCharacter);
        Assertions.assertNotEquals(locationMorty, locationLastCharacter);

    }
}
