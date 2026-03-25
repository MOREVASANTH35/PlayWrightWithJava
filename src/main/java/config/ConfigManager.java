package config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigManager {

    private static Properties prop;

    static {
        try {
            prop = new Properties();
            prop.load(new FileInputStream("resources/config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}
