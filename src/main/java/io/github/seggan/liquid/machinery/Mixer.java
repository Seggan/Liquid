package io.github.seggan.liquid.machinery;

import io.github.seggan.liquid.objects.LContainer;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mrCookieSlime.CSCoreLibPlugin.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Mixer extends LContainer implements RecipeDisplayItem {

    public Mixer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected void registerDefaultRecipes() {
        // TODO: add recipes
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>(recipes.size() * 2);

        for (MachineRecipe recipe : recipes) {
            displayRecipes.add(new CustomItem(
                new ItemStack(Material.LAVA_BUCKET, recipe.getInput().length),
                "&3Multiple Input Recipe")); // Can't exactly show all 9 items at once
            displayRecipes.add(recipe.getOutput()[0]); // Main output should be the first item
        }

        return displayRecipes;
    }

    @Override
    public int getInventoryType() {
        return 0;
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FLINT_AND_STEEL);
    }

    @Override
    public int getEnergyConsumption() {
        return 64;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public String getMachineIdentifier() {
        return "UPGRADED_MELTER";
    }

    @Override
    public int getCapacity() {
        return 128;
    }
}
