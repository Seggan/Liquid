package io.github.seggan.liquid;

import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class Items {

    private Items() {}

    public static final Category category = new Category(
        new NamespacedKey(Liquid.getInstance(), "liquids"),
        new CustomItem(Material.LAVA_BUCKET, "&6Liquids")
    );

    public static final SlimefunItemStack MELTER = new SlimefunItemStack(
        "MELTER",
        Material.BLAST_FURNACE,
        "&6Melter",
        "&7A high-temperature furnace"
    );
}
