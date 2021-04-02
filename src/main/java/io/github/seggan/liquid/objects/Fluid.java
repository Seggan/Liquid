package io.github.seggan.liquid.objects;

import javax.annotation.Nonnull;

import io.github.seggan.liquid.Items;
import io.github.seggan.liquid.Util;
import io.github.seggan.liquid.machinery.Mixer;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import lombok.Builder;
import lombok.NonNull;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Fluid extends SlimefunItem {

    private static final Set<Fluid> fluids = new HashSet<>();

    private ItemStack recipeDisplayItem;
    private ItemStack bottleItem;
    @Nonnull
    private final ItemStack bucketItem = this.getItem();
    private ItemStack nuggetItem;
    private ItemStack ingotItem;
    private ItemStack gemItem;
    private ItemStack blockItem;
    private ItemStack dustItem;
    private ItemStack oreItem;

    public Fluid(SlimefunItemStack item, ItemStack solid) {
        super(Items.category, item, Mixer.RECIPE_TYPE, new ItemStack[]{
            new ItemStack(Material.BUCKET), solid, null,
            null, null, null,
            null, null, null
        });

        fluids.add(this);

        addItemHandler((ItemUseHandler) PlayerRightClickEvent::cancel);
    }

    // I put this here to get around the stack limit of buckets and give people a better way to display a fluid
    // Ex. STAINED_GLASS_PANES
    public Fluid setRecipeDisplayItem(@Nonnull ItemStack recipeDisplayItem) {
        Validate.notNull(recipeDisplayItem, "recipeDisplayItem must not be null!");
        this.recipeDisplayItem = recipeDisplayItem;
        return this;
    }

    public static int getBottleFluidAmount() {
        return 3;
    }

    public static int getBucketFluidAmount() {
        return 9;
    }

    public static int getNuggetFluidAmount() {
        return 1;
    }

    public static int getIngotFluidAmount() {
        return 9;
    }

    public static int getGemFluidAmount() {
        return 9;
    }

    public static int getBlockFluidAmount() {
        return 81;
    }

    public static int getDustFluidAmount() {
        return 9;
    }

    public static int getOreFluidAmount() {
        return 18;
    }

    public static void loadItems() {

    }

    private static void loadItem(ItemStack item) {
        SlimefunItemStack stack = new SlimefunItemStack(
            "MOLTEN_" + Util.getID(item).replace("_INGOT", ""),
            Material.LAVA_BUCKET,
            "&6Molten " + ChatUtils.removeColorCodes(ItemUtils.getItemName(item))
                .replace(" Ingot", "")
        );
    }
}
