package io.github.seggan.liquid;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import io.github.seggan.liquid.categories.MixerCategory;
import io.github.seggan.liquid.machinery.Centrifuge;
import io.github.seggan.liquid.machinery.Crystallizer;
import io.github.seggan.liquid.machinery.Melter;
import io.github.seggan.liquid.machinery.Mixer;
import io.github.seggan.liquid.machinery.Solidifier;
import io.github.seggan.liquid.machinery.SpraySolidifier;
import io.github.seggan.liquid.objects.LiquidMetal;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LiquidAddon extends AbstractAddon implements SlimefunAddon {

    public static LiquidAddon instance = null;

    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;

        PluginUtils.setAddon(this);

        List<LiquidMetal> metals = new ArrayList<>();

        for (ItemStack metal : LiquidMetal.getMetals()) {
            SlimefunItemStack stack = new SlimefunItemStack(
                "MOLTEN_" + Util.getID(metal).replace("_INGOT", ""),
                Material.LAVA_BUCKET,
                "&6Molten " + ChatUtils.removeColorCodes(ItemUtils.getItemName(metal))
                    .replace(" Ingot", "")
            );
            LiquidMetal.addLiquid(metal, stack);
            metals.add(new LiquidMetal(stack, metal));
        }

        for (ItemStack crystal : LiquidMetal.getCrystals()) {
            SlimefunItemStack stack = new SlimefunItemStack(
                "MOLTEN_" + Util.getID(crystal),
                Material.LAVA_BUCKET,
                "&6Molten " + ChatUtils.removeColorCodes(ItemUtils.getItemName(crystal))
            );
            LiquidMetal.addCrystal(crystal, stack);
            metals.add(new LiquidMetal(stack, crystal));
        }

        for (ItemStack ore : LiquidMetal.getOres()) {
            SlimefunItemStack stack = new SlimefunItemStack(
                "MOLTEN_" + Util.getID(ore),
                Material.LAVA_BUCKET,
                "&6Molten " + ChatUtils.removeColorCodes(ItemUtils.getItemName(ore))
            );
            LiquidMetal.addOre(ore, stack);
            metals.add(new LiquidMetal(stack, ore));
        }

        new Melter(Items.category, Items.MELTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL,
            SlimefunItems.HEATING_COIL, SlimefunItems.ELECTRIFIED_CRUCIBLE_3, SlimefunItems.HEATING_COIL,
            SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL
        }).register(this);

        new Solidifier(Items.category, Items.SOLIDIFIER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT,
            SlimefunItems.COOLING_UNIT, SlimefunItems.FREEZER_2, SlimefunItems.COOLING_UNIT,
            SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT
        }).register(this);

        new SpraySolidifier(Items.category, Items.SPRAY_SOLIDIFIER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.HEATING_COIL, Items.SOLIDIFIER, SlimefunItems.HEATING_COIL,
            new ItemStack(Material.IRON_NUGGET), SlimefunItems.COOLING_UNIT, new ItemStack(Material.IRON_NUGGET),
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE
        }).register(this);

        new Mixer(Items.category, Items.MIXER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.CARBONADO, SlimefunItems.HEATING_COIL, SlimefunItems.CARBONADO,
            SlimefunItems.HEATING_COIL, Items.MELTER, SlimefunItems.HEATING_COIL,
            SlimefunItems.CARBONADO, SlimefunItems.HEATING_COIL, SlimefunItems.CARBONADO
        }).register(this);

        new Centrifuge(Items.category, Items.CENTRIFUGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.BOWL), SlimefunItems.REINFORCED_ALLOY_INGOT,
            new ItemStack(Material.BOWL), SlimefunItems.ELECTRIC_DUST_WASHER_3, new ItemStack(Material.BOWL),
            SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.BOWL), SlimefunItems.REINFORCED_ALLOY_INGOT
        }).register(this);

        new Crystallizer(Items.category, Items.CRYSTALLIZER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.COOLING_UNIT, new ItemStack(Material.BOWL), SlimefunItems.COOLING_UNIT,
            new ItemStack(Material.BOWL), Items.SOLIDIFIER, new ItemStack(Material.BOWL),
            SlimefunItems.COOLING_UNIT, new ItemStack(Material.BOWL), SlimefunItems.COOLING_UNIT
        }).register(this);

        new SlimefunItem(Items.category, Items.SLAG, Solidifier.RECIPE_TYPE, new ItemStack[]{
            LiquidMetal.getLiquids().get(Items.SLAG), null, null,
            null, null, null,
            null, null, null,
        }).register(this);

        metals.sort((o1, o2) -> ChatUtils.removeColorCodes(ItemUtils.getItemName(o1.getItem()))
            .replace(" Ingot", "")
            .replaceAll("\\(\\d+-Carat\\)", "")
            .compareToIgnoreCase(ChatUtils.removeColorCodes(ItemUtils.getItemName(o2.getItem()))
                .replace(" Ingot", "")
                .replaceAll("\\(\\d+-Carat\\)", "")));

        for (LiquidMetal metal : metals) {
            metal.register(this);
        }

        RecipeType.GRIND_STONE.register(new ItemStack[]{Items.SLAG}, SlimefunItems.SIFTED_ORE);

        MixerCategory.INSTANCE.register(this);
    }

    @Override
    protected int getMetricsID() {
        return 9408;
    }

    @Override
    protected String getGithubPath() {
        return "Seggan/Liquid/master";
    }

    @Override
    protected List<AbstractCommand> getSubCommands() {
        return new ArrayList<>();
    }

    public static LiquidAddon getInstance() {
        return instance;
    }
}
