package me.luckkyyz.luckdonate.config;

import me.luckkyyz.luckapi.config.MessagePath;

public enum Messages implements MessagePath {

    NO_PERMISSION("noPermission", "&cУ Вас нет прав!"),
    HELP("help", "&7[&cLuck&fDonate&7] &7&l| &fПомощь по командам:\n" +
            "&7/luckdonate give [Никнейм] [Сервер] [Идентификатор] &7&l- &fВыдать донат\n" +
            "&7/luckdonate reload &7&l- &fПерезагрузить плагин"),
    NO_COMMAND("noCommand", "&cТакой команды не существует!"),
    RELOAD_SUCCESS("reloadSuccess", "&fВы успешно перезагрузили плагин!"),
    GIVE_USAGE("giveUsage", "&fИспользуй: &c/luckdonate give [Никнейм] [Сервер] [Идентификатор]"),
    GIVE_IDENTIFIER_NUMBER("giveIdentifierNumber", "&cИдентификатор должен быть числом!"),
    GIVE_SUCCESS("giveSuccess", "&fВы успешно выдали донат игроку!"),
    ONLY_PLAYER("onlyPlayer", "&cЭта команда только для игроков!"),
    CART_SUCCESS("cartSuccess", "&fВы успешно получили донат!"),
    CART_OPEN("cartOpen", "&fВы открыли меню с Вашими карточками!");

    private String path;
    private String defaultValue;

    Messages(String path, String defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }
}
