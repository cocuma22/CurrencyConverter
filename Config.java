import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static String getApiKey() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
            return properties.getProperty("API_KEY");
        } catch (IOException e) {
            System.out.println("Error in loading the API key.");
            return null;
        }
    }
}
