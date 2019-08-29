package folder.health.monitor.utilities;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {

    public Properties getAppProperties(String path) {

        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }
}
