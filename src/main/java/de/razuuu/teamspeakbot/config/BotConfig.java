package de.razuuu.teamspeakbot.config;

public class BotConfig {

    private String host;
    private String queryUsername;
    private String queryPassword;
    private String nickname;
    private int virtualServerId;
    private String defaultLanguage;
    private String commandPrefix;

    /**
     * Gets the host address of the TeamSpeak server.
     *
     * @return The host address of the server.
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the query username used for logging into the TeamSpeak server.
     *
     * @return The query username.
     */
    public String getQueryUsername() {
        return queryUsername;
    }

    /**
     * Gets the query password used for logging into the TeamSpeak server.
     *
     * @return The query password.
     */
    public String getQueryPassword() {
        return queryPassword;
    }

    /**
     * Gets the bot's nickname.
     *
     * @return The bot's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the virtual server ID that the bot should connect to.
     *
     * @return The virtual server ID.
     */
    public int getVirtualServerId() {
        return virtualServerId;
    }

    /**
     * Gets the default language for the bot's messages.
     *
     * @return The default language.
     */
    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    /**
     * Gets the command prefix.
     *
     * @return The command prefix.
     */
    public String getCommandPrefix() {
        return commandPrefix;
    }
}
