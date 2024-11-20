package de.razuuu.teamspeakbot.commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import de.razuuu.teamspeakbot.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelpCommand {

    private static final Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    private final TS3Api ts3api;
    private final Config config;

    /**
     * Constructor for the HelpCommand.
     *
     * @param ts3api The TS3Api object to interact with the TeamSpeak server.
     * @param config The Config object containing language and bot settings.
     */
    public HelpCommand(TS3Api ts3api, Config config) {
        this.ts3api = ts3api;
        this.config = config;
    }

    /**
     * Executes the Help Command and sends a help message to the user who requested it.
     *
     * @param event The event triggered when the user sends a text message.
     */
    public void execute(TextMessageEvent event) {
        String helpMessage =
                "\n" + config.getLanguageConfig().getHelpMessage() + "\n\n"
                + "Available Commands:" + "\n"
                + "• [B]!help[/B] - Displays this help message." + "\n"
                + "• [B]!stop[/B] - Stops the bot.";
        try {
            ts3api.sendPrivateMessage(event.getInvokerId(), helpMessage);

        } catch (Exception exception) {
            logger.error("Error sending help message: ", exception);
        }
    }
}
