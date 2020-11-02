package io.github.seggan.liquid.machinery;

import com.google.common.collect.BiMap;
import io.github.seggan.liquid.Items;
import io.github.seggan.liquid.LiquidMetal;
import io.github.seggan.liquid.VanillaItems;
import io.github.seggan.liquid.objects.LContainer;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.mrCookieSlime.CSCoreLibPlugin.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Centrifuge extends LContainer implements RecipeDisplayItem {

    private static final Map<Material, SlimefunItemStack[]> byproducts = new HashMap<>();

    static {
        byproducts.put(Material.IRON_ORE, new SlimefunItemStack[]{
            SlimefunItems.COPPER_INGOT, SlimefunItems.GOLD_4K, Items.SLAG, SlimefunItems.COBALT_INGOT,
            SlimefunItems.NICKEL_INGOT, SlimefunItems.SULFATE
        });
    }

    public Centrifuge(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected void registerDefaultRecipes() {
        BiMap<ItemStack, SlimefunItemStack> liquids = LiquidMetal.getLiquids();
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_4K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_6K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_6K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_8K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_8K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_10K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_10K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_12K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_12K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_14K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_14K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_16K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_16K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_18K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_18K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_20K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_20K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_22K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_22K), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.GOLD_24K), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.STEEL_INGOT), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(SlimefunItems.DAMASCUS_STEEL_INGOT), liquids.get(Items.SLAG)
            }
        );
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(SlimefunItems.DAMASCUS_STEEL_INGOT), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(VanillaItems.IRON_INGOT), liquids.get(Items.SLAG)
            }
        );

        // Ores
        registerRecipe(
            5,
            new ItemStack[]{
                liquids.get(VanillaItems.IRON_ORE), new ItemStack(Material.BUCKET)
            },
            new ItemStack[]{
                liquids.get(VanillaItems.IRON_INGOT), liquids.get(Items.SLAG)
            }
        );
    }

    /*@Override
    protected MachineRecipe findNextRecipe(BlockMenu inv) {
        MachineRecipe recipe = super.findNextRecipe(inv);
        if (recipe != null) {
            return recipe;
        }

        final Map<Integer, ItemStack> inventory = new HashMap<>();

        for (int slot : getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);

            if (item != null) {
                inventory.put(slot, new ItemStackWrapper(item));
            }
        }

        if (!inventory.containsValue(new ItemStack(Material.BUCKET))) {
            return null;
        }

        oreLoop: {
            for (ItemStack stack : inventory.values()) {
                for (ItemStack ore : LiquidMetal.getOres()) {
                    if (SlimefunUtils.isItemSimilar(ore, stack, true)) {
                        break oreLoop;
                    }
                }
            }
            return null;
        }

        int ore = inventory.get(0).equals(new ItemStack(Material.BUCKET)) ? 1 : 0;
        int bucket = ore == 0 ? 1 : 0;

        List<ItemStack> outputtedItems = new ArrayList<>();
        BiMap<ItemStack, SlimefunItemStack> liquids = LiquidMetal.getLiquidOres();
        switch (liquids.inverse().get(inventory.get(ore)).getType()) {
            case IRON_ORE:
                for (int n = 0; n < inventory.get(bucket).getAmount(); n++) {
                    if (n < 3) {
                        outputtedItems.add(liquids.get(VanillaItems.IRON_INGOT));
                    } else {
                        outputtedItems.add(liquids.get(getByproduct(Material.IRON_ORE)));
                    }
                }
        }
        return new MachineRecipe(3,
            inventory.values().toArray(new ItemStack[0]),
            outputtedItems.toArray(new ItemStack[0]));
    }

    private SlimefunItemStack getByproduct(Material ore) {
        Random random = ThreadLocalRandom.current();

        SlimefunItemStack[] possible = byproducts.get(ore);

        return possible[random.nextInt(possible.length)];
    }*/

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
        return 1;
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.SUNFLOWER);
    }

    @Override
    public int getEnergyConsumption() {
        return 32;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public String getMachineIdentifier() {
        return "CENTRIFUGE";
    }

    @Override
    public int getCapacity() {
        return 64;
    }
}
