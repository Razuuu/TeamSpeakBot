package de.razuuu.teamspeakbot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import de.razuuu.teamspeakbot.config.BotConfig;
import de.razuuu.teamspeakbot.config.Config;
import de.razuuu.teamspeakbot.listeners.ClientJoinListener;
import de.razuuu.teamspeakbot.listeners.CommandListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TeamSpeakBot {

    private static final Logger logger = LoggerFactory.getLogger(TeamSpeakBot.class);

    private TS3Query query;
    private final Config config;

    /**
     * Constructor for TeamSpeakBot.
     *
     * @param config The configuration object containing bot settings and language.
     */
    public TeamSpeakBot(Config config) {
        this.config = config;
    }

    /**
     * The main method which serves as the entry point for the bot.
     * It initializes the bot, loads the configuration, and starts the bot.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        try {
            // Load the bot's configuration
            Config config = new Config();
            config.loadConfig();
            // Create and start the bot
            TeamSpeakBot bot = new TeamSpeakBot(config);
            bot.startBot();
        } catch (IOException exception) {
            logger.error("An error occurred while loading the configuration or starting the bot.", exception);
        }
    }

    /**
     * Starts the TeamSpeak bot, connects to the server, and registers listeners.
     */
    public void startBot() {
        // Retrieve bot configuration
        BotConfig botConfig = config.getBotConfig();

        // Set up the TS3 connection configuration
        TS3Config ts3Config = new TS3Config();
        ts3Config.setHost(botConfig.getHost());

        // Initialize the query to connect to the server
        query = new TS3Query(ts3Config);
        try {
            query.connect();
            TS3Api ts3api = query.getApi();
            ts3api.login(botConfig.getQueryUsername(), botConfig.getQueryPassword());
            ts3api.selectVirtualServerById(botConfig.getVirtualServerId());
            ts3api.setNickname(botConfig.getNickname());

            // Register listeners
            ts3api.registerAllEvents();
            ts3api.addTS3Listeners(new ClientJoinListener(ts3api, config));
            ts3api.addTS3Listeners(new CommandListener(this, ts3api, config));

            logger.info("Bot connected to {}!", botConfig.getHost());
        } catch (TS3CommandFailedException exception) {
            handleTS3Exception(exception);
        } catch (Exception e) {
            logger.error("An unexpected error occurred during bot startup.", e);
            stopBot();
        }
    }

    /**
     * Stops the bot by exiting the TS3 query connection.
     */
    public void stopBot() {
        if (query != null) {
            query.exit();
        }
        logger.info("Bot disconnected and stopped.");
    }

    /**
     * Handles exceptions related to the TS3 connection and logs the error.
     *
     * @param exception The TS3CommandFailedException to handle.
     */
    private void handleTS3Exception(TS3CommandFailedException exception) {
        if (exception.getMessage().contains("invalid loginname or password")) {
            logger.error("Login failed: Incorrect username or password.");
        } else {
            logger.error("An error occurred while connecting to the TeamSpeak server.", exception);
        }
        stopBot();
        System.exit(1);
    }
}
