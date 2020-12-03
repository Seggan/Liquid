package io.github.seggan.liquid;

import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
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
        "",
        "&7A high-temperature furnace",
        LoreBuilder.powerPerSecond(32),
        LoreBuilder.powerBuffer(64)
    );

    public static final SlimefunItemStack SOLIDIFIER = new SlimefunItemStack(
        "SOLIDIFIER",
        Material.LIGHT_BLUE_STAINED_GLASS,
        "&bSolidifier",
        "",
        "&7An advanced freezer",
        LoreBuilder.powerPerSecond(8),
        LoreBuilder.powerBuffer(16)
    );

    public static final SlimefunItemStack TESTLCONTAINER = new SlimefunItemStack(
        "TESTLCONTAINER",
        Material.HAY_BLOCK,
        "&bTest LContainer",
        "",
        "&7Test Item"
    );

    public static final SlimefunItemStack TESTLCONTAINER_2 = new SlimefunItemStack(
        "TESTLCONTAINER_2",
        Material.HAY_BLOCK,
        "&bTest LContainer 2",
        "",
        "&7Test Item"
    );

    public static final SlimefunItemStack MIXER = new SlimefunItemStack(
        "MIXER",
        Material.BLAST_FURNACE,
        "&6Mixer",
        "",
        "&7This upgraded melter is perfect",
        "&7for mixing liquids together",
        LoreBuilder.powerPerSecond(64),
        LoreBuilder.powerBuffer(128)
    );

    public static final SlimefunItemStack CENTRIFUGE = new SlimefunItemStack(
        "CENTRIFUGE",
        Material.HAY_BLOCK,
        "&6Centrifuge",
        "",
        "&7The centrifuge can separate liquids",
        LoreBuilder.powerPerSecond(32),
        LoreBuilder.powerBuffer(64)
    );

    public static final SlimefunItemStack CRYSTALLIZER = new SlimefunItemStack(
        "CRYSTALLIZER",
        Material.DIAMOND_BLOCK,
        "&bCrystallizer",
        "",
        "&7The crystallizer can solidify crystals",
        LoreBuilder.powerPerSecond(8),
        LoreBuilder.powerBuffer(16)
    );

    public static final SlimefunItemStack SLAG = new SlimefunItemStack(
        "SLAG",
        Material.CHARCOAL,
        "&7Slag",
        "",
        "&7One man's trash is another",
        "&7man's treasure..."
    );
}
