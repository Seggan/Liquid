package io.github.seggan.liquid.machinery;

import io.github.seggan.liquid.objects.LContainer;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
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

public abstract class TestLContainer extends LContainer implements RecipeDisplayItem {

    public TestLContainer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(1, new ItemStack[] {
                new ItemStack(Material.STONE, 4), new ItemStack(Material.DIAMOND_BLOCK), SlimefunItems.CARBON,
                SlimefunItems.COMPRESSED_CARBON, SlimefunItems.BIG_CAPACITOR, SlimefunItems.ZINC_INGOT,
                new CustomItem(SlimefunItems.ADVANCED_CIRCUIT_BOARD, 5), SlimefunItems.GOLD_4K,
                new ItemStack(Material.GLASS)
            },
            new ItemStack[] {new ItemStack(Material.NETHERRACK), SlimefunItems.WITHER_PROOF_GLASS,
                new ItemStack(Material.IRON_INGOT),
                new ItemStack(Material.COAL), SlimefunItems.ELECTRIC_MOTOR, new CustomItem(SlimefunItems.AIR_RUNE, 10),
                new ItemStack(Material.BLAZE_ROD), new ItemStack(Material.OAK_LOG), new ItemStack(Material.LAVA_BUCKET)
            });
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
    public String getMachineIdentifier() {
        return "TESTLCONTAINER";
    }
}
