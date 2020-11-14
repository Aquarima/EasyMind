package com.easymind.core.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AppUtils {

    public static final Path DIRECTORY = Path.of(System.getProperty("user.home"), "/AppData/Roaming/EasyMind");

    public static final Path TEMPORARY = Path.of(System.getenv("TEMP"), "/EasyMind");

    public static boolean isRunning() {

        try {
            return Files.list(TEMPORARY)
                    .anyMatch(path -> path.getFileName()
                            .toString()
                            .equals("EasyMind.LOCK"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void checkFiles() {

        if (!Files.exists(TEMPORARY)) {
            IOUtils.create(TEMPORARY, true);
        }

        Path settings = Path.of(DIRECTORY + "/settings.json");

        if (!Files.exists(settings)) {
            IOUtils.copy(AppUtils.class.getResourceAsStream("/auto-generated/settings.json"), settings, null);
        }
    }

    public static String getProperty(String key) {

        Properties properties = new Properties();

        try {
            properties.load(AppUtils.class.getResourceAsStream("/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.getProperty(key);
    }

    public static Logger getLogger() {

        Logger logger = Logger.getLogger("EasyMind");
        logger.setLevel(Level.DEBUG);

        return logger;
    }
}
