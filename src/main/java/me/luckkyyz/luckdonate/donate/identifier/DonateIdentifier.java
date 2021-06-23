package me.luckkyyz.luckdonate.donate.identifier;

import me.luckkyyz.luckapi.util.PerformCommand;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DonateIdentifier {

    private int index;
    private ItemStack item;
    private String command;

    DonateIdentifier(int index, ItemStack item, String command) {
        this.index = index;
        this.item = item;
        this.command = command;
    }

    public int getIndex() {
        return index;
    }

    public ItemStack getItem() {
        return item;
    }

    public void executeCommand(Player player) {
        PerformCommand.perform(command.replaceAll("%player%", player.getName()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonateIdentifier that = (DonateIdentifier) o;
        return new EqualsBuilder()
                .append(index, that.index)
                .append(item, that.item)
                .append(command, that.command)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(index)
                .append(item)
                .append(command)
                .toHashCode();
    }
}
