package hooks;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    Properties prop;

    public String getProperty(String name) {
        return prop.getProperty(name);
    }

    public PropertyReader() {
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop = new Properties();
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}