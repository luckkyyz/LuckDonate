package me.luckkyyz.luckdonate;

import me.luckkyyz.luckapi.LuckApi;
import me.luckkyyz.luckapi.menu.LuckMenuService;
import me.luckkyyz.luckapi.menu.MenuService;
import me.luckkyyz.luckdonate.command.DonateCartsCommand;
import me.luckkyyz.luckdonate.command.LuckDonateCommand;
import me.luckkyyz.luckdonate.config.LanguageConfig;
import me.luckkyyz.luckdonate.config.OptionConfig;
import me.luckkyyz.luckdonate.donate.DonateRepository;
import me.luckkyyz.luckdonate.donate.MysqlDonateRepository;
import me.luckkyyz.luckdonate.donate.identifier.ConfigIdentifierRepository;
import me.luckkyyz.luckdonate.donate.identifier.DonateIdentifierRepository;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "LuckDonate", version = "1.0-SNAPSHOT")
@ApiVersion(ApiVersion.Target.v1_16)
@Author("luckkyyz")

@Command(name = "donatecarts", aliases = {"carts", "cart"})
@Command(name = "luckdonate")
public final class LuckDonatePlugin extends JavaPlugin {

    private DonateIdentifierRepository identifierRepository;
    private DonateRepository donateRepository;

    @Override
    public void onEnable() {
        LuckApi luckApi = LuckApi.bootstrapWith(this);
        luckApi.registerService(MenuService.class, LuckMenuService::new);

        OptionConfig config = new OptionConfig(this);
        LanguageConfig language = new LanguageConfig(this);
        identifierRepository = new ConfigIdentifierRepository(config);
        donateRepository = new MysqlDonateRepository(this, config, identifierRepository);

        new LuckDonateCommand(language, config, identifierRepository, donateRepository);
        new DonateCartsCommand(this, config, language, donateRepository);
    }

    @Override
    public void onDisable() {
        identifierRepository.cancel();
        donateRepository.cancel();
    }
}
