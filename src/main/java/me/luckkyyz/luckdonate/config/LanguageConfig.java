package me.luckkyyz.luckdonate.config;

import me.luckkyyz.luckapi.config.MessageConfig;
import org.bukkit.plugin.Plugin;

public class LanguageConfig extends MessageConfig<Messages> {

    public LanguageConfig(Plugin plugin) {
        super(plugin, Messages.values());
    }
}
