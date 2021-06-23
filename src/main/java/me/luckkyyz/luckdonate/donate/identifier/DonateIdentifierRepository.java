package me.luckkyyz.luckdonate.donate.identifier;

import me.luckkyyz.luckapi.api.Cancelable;
import me.luckkyyz.luckapi.util.function.Optionality;

public interface DonateIdentifierRepository extends Cancelable {

    Optionality<DonateIdentifier> find(int index);

    void reload();

    @Override
    default void cancel() {
    }
}
