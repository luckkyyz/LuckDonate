package me.luckkyyz.luckdonate.command;

import me.luckkyyz.luckapi.command.ChatCommand;
import me.luckkyyz.luckapi.command.ExecutingChecks;
import me.luckkyyz.luckapi.command.ExecutingStrategy;
import me.luckkyyz.luckapi.menu.PreparedMenu;
import me.luckkyyz.luckapi.menu.button.ClickCallback;
import me.luckkyyz.luckapi.menu.button.MenuButton;
import me.luckkyyz.luckapi.menu.reader.ReadButton;
import me.luckkyyz.luckapi.menu.reader.ReadMenu;
import me.luckkyyz.luckdonate.config.LanguageConfig;
import me.luckkyyz.luckdonate.config.Messages;
import me.luckkyyz.luckdonate.config.OptionConfig;
import me.luckkyyz.luckdonate.donate.Donate;
import me.luckkyyz.luckdonate.donate.DonateRepository;
import me.luckkyyz.luckdonate.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class DonateCartsCommand {

    public DonateCartsCommand(Plugin plugin, OptionConfig config, LanguageConfig language, DonateRepository donateRepository) {
        ChatCommand.registerCommand("donatecarts", ExecutingStrategy.newBuilder()
                .commandStrategy()
                .addCheck(ExecutingChecks.player(), session -> session.send(language.getMessage(Messages.ONLY_PLAYER)))
                .addCheck(ExecutingChecks.permission(Permissions.LUCK_DONATE_CARTS), session -> session.send(language.getMessage(Messages.NO_PERMISSION)))
                .addAction(session -> {
                    Player player = session.getExecutor().getPlayer();

                    donateRepository.load(player.getName()).thenAccept(donates -> Bukkit.getScheduler().runTask(plugin, () -> {
                        ReadMenu menu = config.getCartMenu();

                        PreparedMenu.newBuilder()
                                .apply(config.getCartMenu())
                                .setLockUserInventory(true)
                                .setFillingStrategy((result, menuSession) -> {
                                    int donateIndex = 0;

                                    for(Map.Entry<Integer, ReadButton> entry : menu.getButtonMap().entrySet()) {
                                        int index = entry.getKey();
                                        ReadButton readButton = entry.getValue();

                                        if(readButton.getClickAction().equals("NONE")) {
                                            result.addButton(new MenuButton(readButton.getItem().get()), index);
                                            continue;
                                        }
                                        if(readButton.getClickAction().equals("CART")) {
                                            if(donates.size() <= donateIndex) {
                                                continue;
                                            }
                                            Donate donate = donates.get(donateIndex++);

                                            result.addButton(new MenuButton(donate.getItem(), new ClickCallback() {
                                                @Override
                                                public boolean process(Player player, ClickType clickType, int i) {
                                                    donate.executeCommand();
                                                    donate.remove();
                                                    language.getMessage(Messages.CART_SUCCESS).send(player);
                                                    player.closeInventory();
                                                    return true;
                                                }
                                            }), index);
                                        }
                                    }

                                }).create().open(player);
                        session.send(language.getMessage(Messages.CART_OPEN));
                    }));
                })
        );
    }

}
