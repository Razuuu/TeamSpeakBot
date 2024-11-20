package de.razuuu.teamspeakbot.config;

public class LanguageConfig {

    private String welcomeMessage;
    private String helpMessage;
    private String stopMessage;
    private String languageSetMessage;
    private String unknownCommandMessage;

    /**
     * Gets the welcome message.
     *
     * @return The welcome message, with %user% replaced by the client's nickname.
     */
    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    /**
     * Gets the help message.
     *
     * @return The help message describing the bot's functionality.
     */
    public String getHelpMessage() {
        return helpMessage;
    }

    /**
     * Gets the stop message.
     *
     * @return The message sent when the bot is shutting down.
     */
    public String getStopMessage() {
        return stopMessage;
    }

    /**
     * Gets the language set message.
     *
     * @return The message indicating the language change has been applied.
     */
    public String getLanguageSetMessage() {
        return languageSetMessage;
    }

    public String getUnknownCommandMessage() {
        return unknownCommandMessage;
    }
}
