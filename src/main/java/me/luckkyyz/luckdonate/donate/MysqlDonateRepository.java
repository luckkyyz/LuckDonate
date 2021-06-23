package me.luckkyyz.luckdonate.donate;

import me.luckkyyz.luckapi.database.HikariDatabase;
import me.luckkyyz.luckapi.database.HikariQueryExecutors;
import me.luckkyyz.luckapi.database.QueryExecutors;
import me.luckkyyz.luckapi.database.serialize.DatabaseSerializers;
import me.luckkyyz.luckdonate.config.OptionConfig;
import me.luckkyyz.luckdonate.donate.identifier.DonateIdentifier;
import me.luckkyyz.luckdonate.donate.identifier.DonateIdentifierRepository;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MysqlDonateRepository implements DonateRepository {

    private OptionConfig config;
    private DonateIdentifierRepository identifierRepository;
    private HikariDatabase database;
    private QueryExecutors executors;

    public MysqlDonateRepository(Plugin plugin, OptionConfig config, DonateIdentifierRepository identifierRepository) {
        this.config = config;
        this.identifierRepository = identifierRepository;

        database = DatabaseSerializers.yaml().deserialize(config.getSection("database"));
        executors = new HikariQueryExecutors(database, plugin);

        executors.sync().update("CREATE TABLE IF NOT EXISTS `donates` (`player` VARCHAR(36) NOT NULL, `server` VARCHAR(36) NOT NULL, `identifier` INT NOT NULL);");
    }

    @Override
    public CompletableFuture<List<Donate>> load(String player) {
        return CompletableFuture.supplyAsync(() -> {
            List<Donate> donates = new ArrayList<>();
            executors.sync().result("SELECT * FROM donates WHERE player = ?;", result -> {
                while (result.next()) {
                    String server = result.getString("server");
                    if(!server.equals(config.getServer())) {
                        continue;
                    }
                    int identifier = result.getInt("identifier");
                    DonateIdentifier donateIdentifier = identifierRepository.find(identifier).orElse(null);
                    donates.add(new Donate(player, donateIdentifier, this));
                }
            }, player);
            return donates;
        });
    }

    @Override
    public void save(String player, String server, int index) {
        executors.async().update("INSERT INTO donates VALUES (?, ?, ?);", player, server, index);
    }

    @Override
    public void remove(Donate donate) {
        executors.async().update("DELETE FROM donates WHERE player = ? AND server = ? AND identifier = ? LIMIT 1;",
                donate.getName(), config.getServer(), donate.getIndex());
    }

    @Override
    public void cancel() {
        database.close();
    }
}
