package de.razuuu.teamspeakbot.listeners;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.razuuu.teamspeakbot.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientJoinListener extends TS3EventAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ClientJoinListener.class);

    private final TS3Api ts3api;
    private final Config config;

    /**
     * Constructor for the ClientJoinListener.
     *
     * @param ts3api The TS3Api object to interact with the TeamSpeak server.
     */
    public ClientJoinListener(TS3Api ts3api, Config config) {
        this.ts3api = ts3api;
        this.config = config;
    }

    /**
     * This method is triggered when a client joins the server.
     * It sends a welcome message to the joining client.
     *
     * @param event The event that contains details about the client that joined.
     */
    @Override
    public void onClientJoin(ClientJoinEvent event) {
        // Get the welcome message from the loaded language config
        String message = config.getLanguageConfig().getWelcomeMessage().replace("%user%", event.getClientNickname());
        try {
            ts3api.sendPrivateMessage(event.getClientId(), message);
        } catch (Exception exception) {
            logger.error("Error when sending welcome message: ", exception);
        }
    }
}
