package de.razuuu.teamspeakbot.listeners;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.razuuu.teamspeakbot.TeamSpeakBot;
import de.razuuu.teamspeakbot.commands.HelpCommand;
import de.razuuu.teamspeakbot.commands.StopCommand;
import de.razuuu.teamspeakbot.config.Config;

public class CommandListener extends TS3EventAdapter {

    private final TeamSpeakBot teamSpeakBot;
    private final TS3Api ts3api;
    private final Config config;

    /**
     * Constructor for the CommandListener.
     *
     * @param teamSpeakBot The TeamSpeakBot instance, used to stop the bot.
     * @param ts3api       The TS3Api object to interact with the TeamSpeak server.
     * @param config       The Config object containing bot settings and language.
     */
    public CommandListener(TeamSpeakBot teamSpeakBot, TS3Api ts3api, Config config) {
        this.teamSpeakBot = teamSpeakBot;
        this.ts3api = ts3api;
        this.config = config;
    }

    /**
     * This method is triggered when a text message is received in the server chat.
     * It checks if the message contains a command and executes the appropriate action.
     * If the command is unknown, it sends a message prompting the user to type !help.
     *
     * @param event The event that contains the text message sent by the client.
     */
    @Override
    public void onTextMessage(TextMessageEvent event) {
        String message = event.getMessage().trim();

        if (message.startsWith(config.getBotConfig().getCommandPrefix())) {
            String command = message.substring(config.getBotConfig().getCommandPrefix().length()).trim();

            switch (command.toLowerCase()) {
                case "help":
                    HelpCommand helpCommand = new HelpCommand(ts3api, config);
                    helpCommand.execute(event);
                    break;
                case "stop":
                    StopCommand stopCommand = new StopCommand(teamSpeakBot, ts3api, config);
                    stopCommand.execute(event);
                    break;
                default:
                    String unknownCommandMessage = config.getLanguageConfig().getUnknownCommandMessage();
                    ts3api.sendPrivateMessage(event.getInvokerId(), unknownCommandMessage);
                    break;
            }
        }
    }
}
