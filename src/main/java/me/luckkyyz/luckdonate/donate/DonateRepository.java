package me.luckkyyz.luckdonate.donate;

import me.luckkyyz.luckapi.api.Cancelable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DonateRepository extends Cancelable {

    CompletableFuture<List<Donate>> load(String player);

    void save(String player, String server, int index);

    void remove(Donate donate);
}
