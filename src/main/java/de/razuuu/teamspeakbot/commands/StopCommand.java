package de.razuuu.teamspeakbot.commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import de.razuuu.teamspeakbot.TeamSpeakBot;
import de.razuuu.teamspeakbot.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopCommand {

    private static final Logger logger = LoggerFactory.getLogger(StopCommand.class);

    private final TeamSpeakBot teamSpeakBot;
    private final TS3Api ts3api;
    private final Config config;

    /**
     * Constructor for the StopCommand.
     *
     * @param ts3api The TS3Api object to interact with the TeamSpeak server.
     * @param config The Config object containing language and bot settings.
     */
    public StopCommand(TeamSpeakBot teamSpeakBot, TS3Api ts3api, Config config) {
        this.teamSpeakBot = teamSpeakBot;
        this.ts3api = ts3api;
        this.config = config;
    }

    /**
     * Executes the Stop Command and sends a stop message to the user who requested it.
     *
     * @param event The event triggered when the user sends a text message.
     */
    public void execute(TextMessageEvent event) {
        String helpMessage = config.getLanguageConfig().getStopMessage();
        try {
            ts3api.sendPrivateMessage(event.getInvokerId(), helpMessage);
        } catch (Exception exception) {
            logger.error("Error sending stop message: ", exception);
        }

        teamSpeakBot.stopBot();
        logger.info("{} stopped the Bot.", event.getInvokerName());
    }
}
