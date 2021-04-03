package io.github.seggan.liquid.items;

import io.github.mooy1.infinitylib.PluginUtils;
import io.github.seggan.liquid.Items;
import io.github.seggan.liquid.objects.PersistentLiquid;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

/**
 * Class for the Fluid Tank. Warning: the set/get/add contents methods assume the passed item is a Fluid
 * Tank, perform validation on your own
 *
 * @author Seggan
 */
public class FluidTank extends SlimefunItem {

    private static final NamespacedKey LIQUID_KEY = PluginUtils.getKey("liquid_type");
    private static final NamespacedKey AMOUNT_KEY = PluginUtils.getKey("liquid_amount");

    private final int capacity;

    public FluidTank(SlimefunItemStack item, ItemStack[] recipe, int capacity) {
        super(Items.category, new SlimefunItemStack(
            "FLUID_TANK_CAPACITY_" + capacity,
            SlimefunUtils.getCustomHead("1e78e13fbfc5fe05840edd770cd9b7647279738e2f1b03d908d7fcd125189a9"),
            "&6Fluid Tank",
            "",
            "&7Capacity: " + capacity,
            "&7Contents: None",
            "&7Amount: 0"
        ), RecipeType.ENHANCED_CRAFTING_TABLE, recipe);

        this.capacity = capacity;
    }

    private static void updateLore(@Nonnull List<String> lore, @Nonnull Liquid liquid, int amount) {
        lore.set(2, ChatColors.color("&7Contents: " + liquid.getName()));
        updateLore(lore, amount);
    }

    private static void updateLore(@Nonnull List<String> lore, int amount) {
        lore.set(3, ChatColors.color("&7Amount: " + amount));
    }

    @Nonnull
    public static Pair<Liquid, Integer> getContents(@Nonnull ItemMeta meta) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        Liquid liquid = container.get(LIQUID_KEY, PersistentLiquid.TYPE);
        if (liquid == null) {
            throw new IllegalArgumentException("Meta not a Fluid Tank meta");
        }

        Integer amount = container.get(AMOUNT_KEY, PersistentDataType.INTEGER);
        if (amount == null) {
            throw new IllegalArgumentException("Meta not a Fluid Tank meta");
        }

        return new Pair<>(liquid, amount);
    }

    @Nonnull
    public static Pair<Liquid, Integer> getContents(@Nonnull ItemStack stack) {
        return getContents(Objects.requireNonNull(stack.getItemMeta()));
    }

    public void setContents(@Nonnull ItemMeta meta, @Nonnull Liquid liquid, int amount) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(LIQUID_KEY, PersistentLiquid.TYPE, liquid);
        container.set(AMOUNT_KEY, PersistentDataType.INTEGER, amount);

        List<String> lore = meta.getLore();
        updateLore(lore, liquid, amount);
        meta.setLore(lore);
    }

    public void setContents(@Nonnull ItemStack stack, @Nonnull Liquid liquid, int amount) {
        ItemMeta meta = Objects.requireNonNull(stack.getItemMeta());
        setContents(meta, liquid, amount);
        stack.setItemMeta(meta);
    }

    public void addContents(@Nonnull ItemMeta meta, int amount) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        Integer storedAmount = container.get(AMOUNT_KEY, PersistentDataType.INTEGER);
        if (storedAmount == null) {
            throw new IllegalArgumentException("Meta not a Fluid Tank meta");
        }

        int finalAmount = storedAmount + amount;

        container.set(AMOUNT_KEY, PersistentDataType.INTEGER, Math.min(finalAmount, capacity));

        List<String> lore = meta.getLore();
        updateLore(lore, finalAmount);
        meta.setLore(lore);
    }

    public void addContents(@Nonnull ItemStack stack, int amount) {
        ItemMeta meta = Objects.requireNonNull(stack.getItemMeta());
        addContents(meta, amount);
        stack.setItemMeta(meta);
    }
}
