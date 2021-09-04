package io.github.seggan.liquid.machinery;

import com.google.common.collect.BiMap;
import io.github.seggan.liquid.Items;
import io.github.seggan.liquid.Liquid;
import io.github.seggan.liquid.objects.LiquidMetal;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Solidifier extends AContainer implements RecipeDisplayItem {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(Liquid.getInstance(), "solidifier"),
        Items.SOLIDIFIER
    );

    public Solidifier(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(
            10,
            new ItemStack[]{new ItemStack(Material.LAVA_BUCKET)},
            new ItemStack[]{new ItemStack(Material.OBSIDIAN), new ItemStack(Material.BUCKET)}
        );

        BiMap<SlimefunItemStack, ItemStack> liquids = LiquidMetal.getLiquids().inverse();
        for (SlimefunItemStack liquid : liquids.keySet()) {
            registerRecipe(
                10,
                new ItemStack[]{liquid},
                new ItemStack[]{liquids.get(liquid), new ItemStack(Material.BUCKET)}
            );
        }
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.ICE);
    }

    @Override
    public int getEnergyConsumption() {
        return 8;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "SOLIDIFIER";
    }

    @Override
    public int getCapacity() {
        return 16;
    }
}
