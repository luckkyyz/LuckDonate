package me.luckkyyz.luckdonate.donate.identifier;

import me.luckkyyz.luckapi.util.function.Optionality;
import me.luckkyyz.luckapi.util.itemstack.reader.ItemReaders;
import me.luckkyyz.luckapi.util.itemstack.reader.ReadItem;
import me.luckkyyz.luckdonate.config.OptionConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ConfigIdentifierRepository implements DonateIdentifierRepository {

    private Map<Integer, DonateIdentifier> identifierMap = new HashMap<>();
    private OptionConfig config;

    public ConfigIdentifierRepository(OptionConfig config) {
        this.config = config;
        reload();
    }

    @Override
    public Optionality<DonateIdentifier> find(int index) {
        return Optionality.optionalOfNullable(identifierMap.get(index));
    }

    @Override
    public void reload() {
        loadIdentifiers();
    }

    private void loadIdentifiers() {
        identifierMap = new HashMap<>();
        ConfigurationSection mainSection = config.getSection("identifiers");
        if(mainSection == null) {
            return;
        }
        mainSection.getKeys(false).forEach(key -> {
            int index = Integer.parseInt(key);
            ConfigurationSection section = mainSection.getConfigurationSection(key);
            if(section == null) {
                return;
            }
            ConfigurationSection itemSection = section.getConfigurationSection("itemstack");
            if(itemSection == null) {
                return;
            }
            ReadItem readItemStack = ItemReaders.yaml().read(itemSection);
            ItemStack itemStack = readItemStack.get();
            String command = section.getString("command", "").replaceFirst("/", "");

            DonateIdentifier identifier = new DonateIdentifier(index, itemStack, command);
            identifierMap.put(index, identifier);
        });
    }
}
