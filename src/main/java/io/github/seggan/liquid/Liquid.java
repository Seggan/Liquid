package io.github.seggan.liquid;

import io.github.seggan.liquid.machinery.Centrifuge;
import io.github.seggan.liquid.machinery.Melter;
import io.github.seggan.liquid.machinery.Solidifier;
import io.github.seggan.liquid.machinery.Mixer;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.CSCoreLibPlugin.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Liquid extends JavaPlugin implements SlimefunAddon {

    public static Liquid instance = null;

    @Override
    public void onEnable() {
        instance = this;

        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update") && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "Seggan/Liquid/master").start();
        }

        List<LiquidMetal> metals = new ArrayList<>();

        for (ItemStack metal : LiquidMetal.getMetals()) {
            SlimefunItemStack stack = new SlimefunItemStack(
                "MOLTEN_" + Util.getID(metal).replace("_INGOT", ""),
                Material.LAVA_BUCKET,
                "&6Molten " + ChatUtils.removeColorCodes(ItemUtils.getItemName(metal))
                    .replace(" Ingot", "")
            );
            LiquidMetal.addLiquid(metal, stack);
            metals.add(new LiquidMetal(stack));
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

        for (LiquidMetal metal : metals) {
            metal.register(this);
        }

        RecipeType.GRIND_STONE.register(new ItemStack[]{Items.SLAG}, SlimefunItems.SIFTED_ORE);
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static Liquid getInstance() {
        return instance;
    }

}
