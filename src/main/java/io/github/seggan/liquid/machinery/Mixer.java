package io.github.seggan.liquid.machinery;

import com.google.common.collect.BiMap;
import io.github.seggan.liquid.Items;
import io.github.seggan.liquid.Liquid;
import io.github.seggan.liquid.VanillaItems;
import io.github.seggan.liquid.objects.LContainer;
import io.github.seggan.liquid.objects.LiquidMetal;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class Mixer extends LContainer implements RecipeDisplayItem {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(Liquid.getInstance(), "mixer"),
        Items.MIXER
    );


    public Mixer(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected void registerDefaultRecipes() {
        BiMap<ItemStack, SlimefunItemStack> liquids = LiquidMetal.getLiquids();
        BiMap<ItemStack, SlimefunItemStack> crystals = LiquidMetal.getLiquidCrystals();
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.COPPER_INGOT), liquids.get(SlimefunItems.ALUMINUM_INGOT),
                liquids.get(SlimefunItems.ZINC_INGOT), liquids.get(SlimefunItems.STEEL_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.HARDENED_METAL_INGOT), 4)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.COPPER_INGOT), liquids.get(SlimefunItems.TIN_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.BRONZE_INGOT), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.COPPER_INGOT), liquids.get(SlimefunItems.ZINC_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.BRASS_INGOT), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.BRONZE_INGOT), liquids.get(SlimefunItems.ALUMINUM_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.ALUMINUM_BRONZE_INGOT), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.BRASS_INGOT), liquids.get(SlimefunItems.ALUMINUM_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.ALUMINUM_BRASS_INGOT), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.COPPER_INGOT), liquids.get(SlimefunItems.ALUMINUM_INGOT),
                liquids.get(SlimefunItems.TIN_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.ALUMINUM_BRONZE_INGOT), 3)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.COPPER_INGOT), liquids.get(SlimefunItems.ALUMINUM_INGOT),
                liquids.get(SlimefunItems.ZINC_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.ALUMINUM_BRASS_INGOT), 3)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.CARBON), liquids.get(VanillaItems.IRON_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.STEEL_INGOT), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.COPPER_INGOT), liquids.get(SlimefunItems.ALUMINUM_INGOT),
                liquids.get(SlimefunItems.GOLD_12K), liquids.get(SlimefunItems.HARDENED_METAL_INGOT),
                liquids.get(SlimefunItems.LEAD_INGOT), liquids.get(SlimefunItems.STEEL_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.REINFORCED_ALLOY_INGOT), 3),
                new SlimefunItemStack(liquids.get(Items.SLAG), 3)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.COPPER_INGOT), liquids.get(SlimefunItems.GOLD_12K),
                liquids.get(SlimefunItems.TIN_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.CORINTHIAN_BRONZE_INGOT), 2),
                liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.TIN_INGOT), liquids.get(SlimefunItems.LEAD_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.SOLDER_INGOT), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.SILVER_INGOT), liquids.get(SlimefunItems.COPPER_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.BILLON_INGOT), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.SILICON), liquids.get(VanillaItems.IRON_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.FERROSILICON), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.HARDENED_METAL_INGOT), liquids.get(VanillaItems.REDSTONE)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.REDSTONE_ALLOY), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.COPPER_INGOT), liquids.get(VanillaItems.IRON_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.NICKEL_INGOT), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.NICKEL_INGOT), liquids.get(VanillaItems.IRON_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.COBALT_INGOT), 2)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.COBALT_INGOT), liquids.get(SlimefunItems.NICKEL_INGOT),
                liquids.get(SlimefunItems.ALUMINUM_INGOT)
            },
            new ItemStack[]{
                new SlimefunItemStack(liquids.get(SlimefunItems.MAGNET), 3)
            }
        );
        registerRecipe(
            3,
            new ItemStack[]{
                liquids.get(SlimefunItems.MAGNESIUM_INGOT), crystals.get(SlimefunItems.SALT)
            },
            new ItemStack[]{
                new SlimefunItemStack(crystals.get(SlimefunItems.MAGNESIUM_SALT), 2)
            }
        );
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>(recipes.size() * 2);

        for (MachineRecipe recipe : recipes) {
            displayRecipes.add(new CustomItemStack(
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
        return "MIXER";
    }

    @Override
    public int getCapacity() {
        return 128;
    }
}
