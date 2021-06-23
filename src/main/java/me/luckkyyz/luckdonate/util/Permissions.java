package me.luckkyyz.luckdonate.util;

import me.luckkyyz.luckapi.util.permission.PermissionNode;

public final class Permissions {

    private Permissions() {
        throw new UnsupportedOperationException();
    }

    public static final PermissionNode LUCK_DONATE_COMMAND = PermissionNode.node("LuckDonate.command");

    public static final PermissionNode LUCK_DONATE_COMMAND_RELOAD = PermissionNode.node("LuckDonate.command.reload");

    public static final PermissionNode LUCK_DONATE_COMMAND_GIVE = PermissionNode.node("LuckDonate.command.give");

    public static final PermissionNode LUCK_DONATE_CARTS = PermissionNode.node("LuckDonate.carts");

}
