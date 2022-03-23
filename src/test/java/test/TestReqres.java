package test;

import api.ApiReqres;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TestReqres extends ApiReqres
{
    @Tag("1TestReqresPost")
    @Test
    @DisplayName("Test Reqres Post")
    public void testPost()
    {
        checkPost();
    }

    @Tag("2TestReqresPut")
    @Test
    @DisplayName("Test Reqres Put")
    public void testPut()
    {
        int id = checkPost();
        checkPut(id);
    }

    @Tag("3TestReqresDelete")
    @Test
    @DisplayName("Test Reqres Delete")
    public void testDelete()
    {
        int id = checkPost();
        checkDelete(id);
    }
}
