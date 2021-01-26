package io.github.seggan.liquid;

import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.inventory.ItemStack;

public final class Util {

    private Util() {}

    public static String getID(ItemStack stack) {
        if (stack instanceof SlimefunItemStack) {
            return ((SlimefunItemStack) stack).getItemId();
        } else {
            return ChatUtils.removeColorCodes(ItemUtils.getItemName(stack))
                .toUpperCase().replace(" ", "_");
        }
    }
}
