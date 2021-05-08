package io.github.seggan.liquid;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import io.github.seggan.liquid.categories.MixerCategory;
import io.github.seggan.liquid.items.fluids.PortableFluidTank;
import io.github.seggan.liquid.machinery.Melter;
import io.github.seggan.liquid.machinery.Mixer;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class LiquidAddon extends AbstractAddon implements SlimefunAddon {

    private static LiquidAddon instance = null;

    public LiquidAddon() {
        super();
    }

    protected LiquidAddon(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;

        PluginUtils.setPlugin(this);

        new PortableFluidTank(new ItemStack[9], 100).register(this);

        new Melter(Items.category, Items.MELTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL,
            SlimefunItems.HEATING_COIL, SlimefunItems.ELECTRIFIED_CRUCIBLE_3, SlimefunItems.HEATING_COIL,
            SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL
        }).register(this);

//        new Solidifier(Items.category, Items.SOLIDIFIER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
//            SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT,
//            SlimefunItems.COOLING_UNIT, SlimefunItems.FREEZER_2, SlimefunItems.COOLING_UNIT,
//            SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT
//        }).register(this);
//
//        new SpraySolidifier(Items.category, Items.SPRAY_SOLIDIFIER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
//            SlimefunItems.HEATING_COIL, Items.SOLIDIFIER, SlimefunItems.HEATING_COIL,
//            new ItemStack(Material.IRON_NUGGET), SlimefunItems.COOLING_UNIT, new ItemStack(Material.IRON_NUGGET),
//            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE
//        }).register(this);
//
        new Mixer(Items.category, Items.MIXER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.CARBONADO, SlimefunItems.HEATING_COIL, SlimefunItems.CARBONADO,
            SlimefunItems.HEATING_COIL, Items.MELTER, SlimefunItems.HEATING_COIL,
            SlimefunItems.CARBONADO, SlimefunItems.HEATING_COIL, SlimefunItems.CARBONADO
        }).register(this);
//
//        new Centrifuge(Items.category, Items.CENTRIFUGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
//            SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.BOWL), SlimefunItems.REINFORCED_ALLOY_INGOT,
//            new ItemStack(Material.BOWL), SlimefunItems.ELECTRIC_DUST_WASHER_3, new ItemStack(Material.BOWL),
//            SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.BOWL), SlimefunItems.REINFORCED_ALLOY_INGOT
//        }).register(this);
//
//        new Crystallizer(Items.category, Items.CRYSTALLIZER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
//            SlimefunItems.COOLING_UNIT, new ItemStack(Material.BOWL), SlimefunItems.COOLING_UNIT,
//            new ItemStack(Material.BOWL), Items.SOLIDIFIER, new ItemStack(Material.BOWL),
//            SlimefunItems.COOLING_UNIT, new ItemStack(Material.BOWL), SlimefunItems.COOLING_UNIT
//        }).register(this);
//
//        new SlimefunItem(Items.category, Items.SLAG, Solidifier.RECIPE_TYPE, new ItemStack[]{
//            LiquidMetal.getLiquids().get(Items.SLAG), null, null,
//            null, null, null,
//            null, null, null,
//        }).register(this);

        RecipeType.GRIND_STONE.register(new ItemStack[]{Items.SLAG}, SlimefunItems.SIFTED_ORE);

        MixerCategory.INSTANCE.register(this);
    }

    @Nullable
    @Override
    protected Metrics setupMetrics() {
        return new Metrics(this, 9408);
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Seggan/Liquid/master";
    }

    @Nonnull
    @Override
    protected List<AbstractCommand> getSubCommands() {
        return new ArrayList<>();
    }

    public static LiquidAddon inst() {
        return instance;
    }
}
