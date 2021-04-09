package io.github.seggan.liquid.machinery;

import io.github.seggan.liquid.Items;
import io.github.seggan.liquid.LiquidAddon;
import io.github.seggan.liquid.liquidapi.FluidHoldingContainer;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

// TODO make it consume energy
public class Mixer extends FluidHoldingContainer implements EnergyNetComponent {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(LiquidAddon.getInstance(), "mixer"),
        Items.MIXER
    );


    public Mixer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {

    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        return new int[0];
    }

    @Override
    protected void tick(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Config data) {

    }

    @Override
    protected int getLiquidCapacity(int tankId) {
        return 0;
    }


    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return null;
    }

    @Override
    public int getCapacity() {
        return 0;
    }
}
