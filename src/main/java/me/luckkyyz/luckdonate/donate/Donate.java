package me.luckkyyz.luckdonate.donate;

import me.luckkyyz.luckapi.util.function.Optionality;
import me.luckkyyz.luckapi.util.player.PlayerFilters;
import me.luckkyyz.luckdonate.donate.identifier.DonateIdentifier;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Donate {

    private String player;
    private DonateIdentifier donate;
    private DonateRepository repository;

    Donate(String player, DonateIdentifier donate, DonateRepository repository) {
        this.player = player;
        this.donate = donate;
        this.repository = repository;
    }

    public void remove() {
        repository.remove(this);
    }

    public String getName() {
        return player;
    }

    public Optionality<Player> getPlayer() {
        return PlayerFilters.byName(player);
    }

    public DonateIdentifier getDonate() {
        return donate;
    }

    public ItemStack getItem() {
        return getDonate().getItem();
    }

    public int getIndex() {
        return getDonate().getIndex();
    }

    public void executeCommand() {
        getPlayer().ifPresent(player1 -> getDonate().executeCommand(player1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donate donate1 = (Donate) o;
        return new EqualsBuilder()
                .append(player, donate1.player)
                .append(donate, donate1.donate)
                .append(repository, donate1.repository)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(player)
                .append(donate)
                .append(repository)
                .toHashCode();
    }
}
