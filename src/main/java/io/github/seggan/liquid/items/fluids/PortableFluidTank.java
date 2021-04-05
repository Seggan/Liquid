package io.github.seggan.liquid.items.fluids;

import io.github.mooy1.infinitylib.PluginUtils;
import io.github.seggan.liquid.Items;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
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
public class PortableFluidTank extends UnplaceableBlock {

    private static final NamespacedKey LIQUID_KEY = PluginUtils.getKey("liquid_type");
    private static final NamespacedKey AMOUNT_KEY = PluginUtils.getKey("liquid_amount");

    @Getter
    private final int capacity;

    public PortableFluidTank(ItemStack[] recipe, int capacity) {
        super(Items.category, new SlimefunItemStack(
            "PORTABLE_FLUID_TANK_CAPACITY_" + capacity,
            FluidTankTextures.EMPTY.getTexture(),
            "&6Portable Fluid Tank",
            "",
            "&7Capacity: " + capacity,
            "&7Contents: None",
            "&7Amount: 0"
        ), RecipeType.ENHANCED_CRAFTING_TABLE, recipe);

        this.capacity = capacity;
    }

    private static void updateLore(@Nonnull List<String> lore, @Nonnull Liquid liquid, int amount) {
        lore.set(2, ChatColors.color("&7Contents: " + liquid.getName()));
        lore.set(3, ChatColors.color("&7Amount: " + amount));
    }

    @Nonnull
    public static Pair<Liquid, Integer> getContents(@Nonnull ItemMeta meta) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        Liquid liquid = container.getOrDefault(LIQUID_KEY, PersistentLiquid.TYPE, Liquid.NONE);
        Integer amount = container.getOrDefault(AMOUNT_KEY, PersistentDataType.INTEGER, 0);

        return new Pair<>(liquid, amount);
    }

    @Nonnull
    public static Pair<Liquid, Integer> getContents(@Nonnull ItemStack stack) {
        return getContents(Objects.requireNonNull(stack.getItemMeta()));
    }

    public void setContents(@Nonnull ItemMeta meta, @Nonnull Liquid liquid, int amount) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (amount > 0) {
            container.set(LIQUID_KEY, PersistentLiquid.TYPE, liquid);
        } else {
            container.set(LIQUID_KEY, PersistentLiquid.TYPE, Liquid.NONE);
        }
        int finalAmount = Math.min(Math.max(0, amount), this.capacity);
        container.set(AMOUNT_KEY, PersistentDataType.INTEGER, finalAmount);

        List<String> lore = meta.getLore();
        updateLore(lore, liquid, finalAmount);
        meta.setLore(lore);

        FluidTankTextures.getTexture(finalAmount, this.capacity).inject((SkullMeta) meta);
    }

    public void setContents(@Nonnull ItemStack stack, @Nonnull Liquid liquid, int amount) {
        ItemMeta meta = Objects.requireNonNull(stack.getItemMeta());
        setContents(meta, liquid, amount);
        stack.setItemMeta(meta);
    }

    public void addContents(@Nonnull ItemMeta meta, @Nonnull Liquid liquid, int amount) {
        int storedAmount = meta.getPersistentDataContainer().getOrDefault(AMOUNT_KEY, PersistentDataType.INTEGER, 0);
        this.setContents(meta,liquid, storedAmount + amount);
    }

    public void addContents(@Nonnull ItemStack stack, @Nonnull Liquid liquid, int amount) {
        ItemMeta meta = Objects.requireNonNull(stack.getItemMeta());
        addContents(meta, liquid, amount);
        stack.setItemMeta(meta);
    }
}
