package me.luckkyyz.luckdonate.command;

import me.luckkyyz.luckapi.command.ChatCommand;
import me.luckkyyz.luckapi.command.ExecutingChecks;
import me.luckkyyz.luckapi.command.ExecutingStrategy;
import me.luckkyyz.luckdonate.config.LanguageConfig;
import me.luckkyyz.luckdonate.config.Messages;
import me.luckkyyz.luckdonate.config.OptionConfig;
import me.luckkyyz.luckdonate.donate.DonateRepository;
import me.luckkyyz.luckdonate.donate.identifier.DonateIdentifierRepository;
import me.luckkyyz.luckdonate.util.Permissions;
import org.apache.commons.lang.math.NumberUtils;

public class LuckDonateCommand {

    public LuckDonateCommand(LanguageConfig language, OptionConfig config, DonateIdentifierRepository identifierRepository, DonateRepository repository) {
        ChatCommand.registerCommand("luckdonate", ExecutingStrategy.newBuilder()
                .subCommandStrategy()
                .addCheck(ExecutingChecks.permission(Permissions.LUCK_DONATE_COMMAND), session -> session.send(language.getMessage(Messages.NO_PERMISSION)))
                .whenArgumentAbsent(session -> session.send(language.getMessage(Messages.HELP)))
                .whenSubCommandAbsent(session -> session.send(language.getMessage(Messages.NO_COMMAND)))
                .addSubCommand(ExecutingStrategy.newBuilder()
                        .commandStrategy()
                        .addCheck(ExecutingChecks.permission(Permissions.LUCK_DONATE_COMMAND_RELOAD), session -> session.send(language.getMessage(Messages.NO_PERMISSION)))
                        .addAction(session -> {
                            language.reload();
                            config.reload();
                            identifierRepository.reload();
                            session.send(language.getMessage(Messages.RELOAD_SUCCESS));
                        }), "reload", "restart"
                )
                .addSubCommand(ExecutingStrategy.newBuilder()
                        .commandStrategy()
                        .addCheck(ExecutingChecks.permission(Permissions.LUCK_DONATE_COMMAND_GIVE), session -> session.send(language.getMessage(Messages.NO_PERMISSION)))
                        .addCheck(session -> session.getArguments().length() == 3, session -> session.send(language.getMessage(Messages.GIVE_USAGE)))
                        .addCheck(session -> NumberUtils.isNumber(session.getArguments().get(3)), session -> session.send(language.getMessage(Messages.GIVE_IDENTIFIER_NUMBER)))
                        .addAction(session -> {
                            String name = session.getArguments().get(1);
                            String server = session.getArguments().get(2);
                            int identifier = Integer.parseInt(session.getArguments().get(3));

                            repository.save(name, server, identifier);
                            session.send(language.getMessage(Messages.GIVE_SUCCESS));
                        }), "give"
                )
        );
    }

}
