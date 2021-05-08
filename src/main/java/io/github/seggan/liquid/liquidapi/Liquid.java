package io.github.seggan.liquid.liquidapi;

import io.github.seggan.liquid.VanillaItems;
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import lombok.Getter;
import lombok.ToString;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class Liquid {

    private static final Map<String, Liquid> ids = new HashMap<>();

    public static final Liquid NONE = new Liquid(new ItemStack(Material.BARRIER), "&7None");

    public static final Liquid ALUMINUM_BRONZE = new Liquid(SlimefunItems.ALUMINUM_BRONZE_INGOT);
    public static final Liquid ALUMINUM_BRASS = new Liquid(SlimefunItems.ALUMINUM_BRASS_INGOT);
    public static final Liquid ALUMINUM = new Liquid(SlimefunItems.ALUMINUM_INGOT);
    public static final Liquid DURALUMIN = new Liquid(SlimefunItems.DURALUMIN_INGOT);

    public static final Liquid GOLD = new Liquid(VanillaItems.GOLD_INGOT);
    public static final Liquid GOLD_4 = new Liquid(SlimefunItems.GOLD_4K);
    public static final Liquid GOLD_6 = new Liquid(SlimefunItems.GOLD_6K);
    public static final Liquid GOLD_8 = new Liquid(SlimefunItems.GOLD_8K);
    public static final Liquid GOLD_10 = new Liquid(SlimefunItems.GOLD_10K);
    public static final Liquid GOLD_12 = new Liquid(SlimefunItems.GOLD_12K);
    public static final Liquid GOLD_14 = new Liquid(SlimefunItems.GOLD_14K);
    public static final Liquid GOLD_16 = new Liquid(SlimefunItems.GOLD_16K);
    public static final Liquid GOLD_18 = new Liquid(SlimefunItems.GOLD_18K);
    public static final Liquid GOLD_20 = new Liquid(SlimefunItems.GOLD_20K);
    public static final Liquid GOLD_22 = new Liquid(SlimefunItems.GOLD_22K);
    public static final Liquid GOLD_24 = new Liquid(SlimefunItems.GOLD_24K);
    public static final Liquid GILDED_IRON = new Liquid(SlimefunItems.GILDED_IRON);

    public static final Liquid IRON = new Liquid(VanillaItems.IRON_INGOT);
    public static final Liquid STEEL = new Liquid(SlimefunItems.STEEL_INGOT);
    public static final Liquid DAMASCUS_STEEL = new Liquid(SlimefunItems.DAMASCUS_STEEL_INGOT);

    public static final Liquid FERROSILICON = new Liquid(SlimefunItems.FERROSILICON);
    public static final Liquid SILICON = new Liquid(SlimefunItems.SILICON);

    public static final Liquid COBALT = new Liquid(SlimefunItems.COBALT_INGOT);
    public static final Liquid NICKEL = new Liquid(SlimefunItems.NICKEL_INGOT);

    public static final Liquid COPPER = new Liquid(SlimefunItems.COPPER_INGOT);
    public static final Liquid BRONZE = new Liquid(SlimefunItems.BRONZE_INGOT);
    public static final Liquid CORINTHIAN_BRONZE = new Liquid(SlimefunItems.CORINTHIAN_BRONZE_INGOT);
    public static final Liquid BRASS = new Liquid(SlimefunItems.BRASS_INGOT);

    public static final Liquid TIN = new Liquid(SlimefunItems.TIN_INGOT);
    public static final Liquid ZINC = new Liquid(SlimefunItems.ZINC_INGOT);
    public static final Liquid SOLDER = new Liquid(SlimefunItems.SOLDER_INGOT);
    public static final Liquid LEAD = new Liquid(SlimefunItems.LEAD_INGOT);
    public static final Liquid BILLION = new Liquid(SlimefunItems.BILLON_INGOT);
    public static final Liquid SILVER = new Liquid(SlimefunItems.SILVER_INGOT);
    public static final Liquid MAGNESIUM = new Liquid(SlimefunItems.MAGNESIUM_INGOT);

    public static final Liquid HARDENED_METAL = new Liquid(SlimefunItems.HARDENED_METAL_INGOT);
    public static final Liquid REINFORCED_ALLOY = new Liquid(SlimefunItems.REINFORCED_ALLOY_INGOT);

    public static final Liquid URANIUM = new Liquid(SlimefunItems.URANIUM);
    public static final Liquid NEPTUNIUM = new Liquid(SlimefunItems.NEPTUNIUM);
    public static final Liquid PLUTONIUM = new Liquid(SlimefunItems.PLUTONIUM);
    public static final Liquid BOOSTED_URANIUM = new Liquid(SlimefunItems.BOOSTED_URANIUM);

    public static final Liquid CARBON = new Liquid(SlimefunItems.CARBON);
    public static final Liquid REDSTONE_ALLOY = new Liquid(SlimefunItems.REDSTONE_ALLOY);
    public static final Liquid SULFATE = new Liquid(SlimefunItems.SULFATE);

    public static final Liquid REDSTONE = new Liquid(VanillaItems.REDSTONE);
    public static final Liquid LAPIS = new Liquid(VanillaItems.LAPIS);

    private final ItemStack solid;
    private final String name;
    private final String id;
    private final boolean slimefunItem;

    public Liquid(ItemStack solid, String name) {
        this.solid = solid;
        this.name = ChatColors.color(name);

        SlimefunItem sfi;
        if (this.solid instanceof SlimefunItemStack) {
            this.slimefunItem = true;
            this.id = ((SlimefunItemStack) this.solid).getItemId();
        } else if ((sfi = SlimefunItem.getByItem(this.solid)) != null) {
            this.slimefunItem = true;
            this.id = sfi.getId();
        } else {
            this.slimefunItem = false;
            this.id = this.solid.getType().name();
        }

        ids.put(this.id, this);
    }

    public Liquid(ItemStack solid) {
        this(solid, "&6Molten " + (SlimefunPlugin.getMinecraftVersion() == MinecraftVersion.UNIT_TEST ? solid.getItemMeta().getDisplayName() : ItemUtils.getItemName(solid)));
    }

    @Nonnull
    public static Liquid getById(@Nonnull String id) {
        Liquid liquid = ids.get(id);
        if (liquid == null) {
            throw new IllegalArgumentException("No known Liquid with the given id");
        }

        return liquid;
    }

    @Nullable
    public static Liquid getBySolid(@Nonnull ItemStack solid) {
        for (Liquid liquid : ids.values()) {
            if (SlimefunUtils.isItemSimilar(solid, liquid.solid, false, false)) {
                return liquid;
            }
        }

        return null;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Liquid)) return false;

        return this.id.equals(((Liquid) obj).id);
    }

    public boolean isNone() {
        return this.id.equals(Liquid.NONE.getId());
    }
}
