package io.github.seggan.liquid.machinery;

import com.google.common.collect.BiMap;
import io.github.seggan.liquid.objects.LiquidMetal;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class SpraySolidifier extends AContainer {

    public SpraySolidifier(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected void registerDefaultRecipes() {
        BiMap<ItemStack, SlimefunItemStack> dusts = LiquidMetal.getLiquidDusts().inverse();
        BiMap<ItemStack, SlimefunItemStack> liquids = LiquidMetal.getLiquids();
        for (ItemStack solidDust : dusts.keySet()) {
            ItemStack solid = liquids.get(solidDust);
            registerRecipe(
                2,
                new ItemStack[]{solid},
                new ItemStack[]{dusts.get(solidDust), new ItemStack(Material.BUCKET)}
            );
        }
    }

    @Override
    public ItemStack getProgressBar() {
        return SlimefunItems.IRON_DUST;
    }

    @Override
    public int getEnergyConsumption() {
        return 64;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "SPRAY_SOLIDIFIER";
    }

    @Override
    public int getCapacity() {
        return 128;
    }
}
