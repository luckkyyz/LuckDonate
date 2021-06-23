package me.luckkyyz.luckdonate.config;

import me.luckkyyz.luckapi.config.SettingConfig;
import me.luckkyyz.luckapi.menu.reader.MenuReaders;
import me.luckkyyz.luckapi.menu.reader.ReadMenu;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class OptionConfig extends SettingConfig {

    private ReadMenu cartMenu;

    public OptionConfig(Plugin plugin) {
        super(plugin);
    }

    public String getServer() {
        return config.getString("serverName");
    }

    public ReadMenu getCartMenu() {
        return cartMenu;
    }

    @Override
    protected void load(YamlConfiguration config) {
        cartMenu = MenuReaders.yamlPattern().read(config.getConfigurationSection("cartMenu"));
    }
}
