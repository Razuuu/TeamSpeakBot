package de.razuuu.teamspeakbot.config;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final String CONFIG_FILE_NAME = "config.json";
    private BotConfig botConfig;
    private LanguageConfig languageConfig; // Hold the language messages here

    public Config() {
        botConfig = new BotConfig();
    }

    /**
     * Load the configuration from the file and the appropriate language file.
     */
    public void loadConfig() throws IOException {
        File configFile = new File(CONFIG_FILE_NAME);

        if (!configFile.exists()) {
            createConfig();
        }

        try (FileReader reader = new FileReader(configFile)) {
            Gson gson = new Gson();
            botConfig = gson.fromJson(reader, BotConfig.class);
        }

        logger.info("Configuration loaded successfully.");

        // Call loadLanguageFile without passing languageFileName
        loadLanguageFile();
    }

    /**
     * Creates the default config file if it doesn't exist.
     */
    private void createConfig() throws IOException {
        try (var resourceStream = Config.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (resourceStream == null) {
                logger.error("Default config file not found in resources.");
                System.exit(1);
            }

            File targetFile = new File(CONFIG_FILE_NAME);

            if (targetFile.createNewFile()) {
                logger.info("Configuration file created.");
            } else {
                logger.info("Configuration file already exists.");
            }

            try (var targetStream = Files.newOutputStream(targetFile.toPath())) {
                resourceStream.transferTo(targetStream);
            }

            logger.info("Default configuration file has been created. Please edit it.");
            System.exit(0);
        }
    }

    /**
     * Loads the appropriate language file based on the default language specified in the bot configuration.
     * The language file is loaded from the resources' folder.
     */
    public void loadLanguageFile() {
        String languageFileName = botConfig.getDefaultLanguage(); // Get the language file name
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("lang/" + languageFileName + ".json");

        if (inputStream == null) {
            logger.error("Language file not found: {}", languageFileName);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Gson gson = new Gson();
            languageConfig = gson.fromJson(reader, LanguageConfig.class);
            logger.info("Language file {} loaded successfully.", languageFileName);
        } catch (IOException e) {
            logger.error("Failed to load language file: {}", languageFileName, e);
        }
    }

    /**
     * Gets the BotConfig, which contains configuration details for the bot.
     *
     * @return The BotConfig object containing bot settings like host, query username, etc.
     */
    public BotConfig getBotConfig() {
        return botConfig;
    }

    /**
     * Gets the LanguageConfig, which contains the language-specific messages for the bot.
     *
     * @return The LanguageConfig object containing language-dependent strings like welcome message, help message, etc.
     */
    public LanguageConfig getLanguageConfig() {
        return languageConfig;
    }
}
