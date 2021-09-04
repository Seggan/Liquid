package io.github.seggan.liquid.machinery;

import com.google.common.collect.BiMap;
import io.github.seggan.liquid.Items;
import io.github.seggan.liquid.Liquid;
import io.github.seggan.liquid.objects.LiquidMetal;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Melter extends AContainer implements RecipeDisplayItem {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(Liquid.getInstance(), "melter"),
        Items.MELTER
    );

    public Melter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(
            3,
            new ItemStack[]{new ItemStack(Material.OBSIDIAN), new ItemStack(Material.BUCKET)},
            new ItemStack[]{new ItemStack(Material.LAVA_BUCKET)}
        );
        BiMap<ItemStack, SlimefunItemStack> liquids = LiquidMetal.getLiquids();

        registerRecipe(
            3,
            new ItemStack[]{new ItemStack(Material.COAL), new ItemStack(Material.BUCKET)},
            new ItemStack[]{liquids.get(SlimefunItems.CARBON)}
        );

        registerRecipe(
            3,
            new ItemStack[]{new ItemStack(Material.CHARCOAL), new ItemStack(Material.BUCKET)},
            new ItemStack[]{liquids.get(SlimefunItems.CARBON)}
        );

        for (ItemStack metal : liquids.keySet()) {
            registerRecipe(
                3,
                new ItemStack[]{metal, new ItemStack(Material.BUCKET)},
                new ItemStack[]{liquids.get(metal)}
            );
        }
        BiMap<ItemStack, SlimefunItemStack> crystals = LiquidMetal.getLiquidCrystals();
        for (ItemStack crystal : crystals.keySet()) {
            registerRecipe(
                3,
                new ItemStack[]{crystal, new ItemStack(Material.BUCKET)},
                new ItemStack[]{crystals.get(crystal)}
            );
        }
        BiMap<ItemStack, SlimefunItemStack> ores = LiquidMetal.getLiquidOres();
        for (ItemStack ore : ores.keySet()) {
            registerRecipe(
                3,
                new ItemStack[]{ore, new ItemStack(Material.BUCKET)},
                new ItemStack[]{ores.get(ore)}
            );
        }
        BiMap<SlimefunItemStack, ItemStack> dusts = LiquidMetal.getLiquidDusts();
        for (SlimefunItemStack dust : dusts.keySet()) {
            registerRecipe(
                3,
                new ItemStack[]{dust, new ItemStack(Material.BUCKET)},
                new ItemStack[]{liquids.get(dusts.get(dust))}
            );
        }
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FLINT_AND_STEEL);
    }

    @Override
    public int getEnergyConsumption() {
        return 32;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "MELTER";
    }

    @Override
    public int getCapacity() {
        return 64;
    }
}
