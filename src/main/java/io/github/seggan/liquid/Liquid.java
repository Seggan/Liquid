package io.github.seggan.liquid;

import io.github.seggan.liquid.machinery.Melter;
import io.github.seggan.liquid.machinery.Solidifier;
import io.github.seggan.liquid.machinery.UpgradedMelter;
import io.github.seggan.liquid.machinery.Centrifuge;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
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

        for (SlimefunItemStack metal : LiquidMetal.getMetals()) {
            SlimefunItemStack stack = new SlimefunItemStack(
                "MOLTEN_" + metal.getItemId().replace("_INGOT", ""),
                Material.LAVA_BUCKET,
                "&6Molten " + ChatUtils.removeColorCodes(metal.getDisplayName())
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

        for (LiquidMetal metal : metals) {
            metal.register(this);
        }
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
