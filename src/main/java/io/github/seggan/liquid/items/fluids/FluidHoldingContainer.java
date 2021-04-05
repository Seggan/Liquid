package io.github.seggan.liquid.items.fluids;

import io.github.mooy1.infinitylib.slimefun.abstracts.TickingContainer;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class FluidHoldingContainer extends TickingContainer {

    public FluidHoldingContainer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        setContents(b, Liquid.NONE, 0);
    }

    protected abstract int getLiquidCapacity();

    @Nonnull
    protected static Pair<Liquid, Integer> getContents(@Nonnull Config config) {
        Liquid liquid = Liquid.getById(config.getString("storedLiquid"));
        int amount = Integer.parseInt(config.getString("liquidAmount"));
        return new Pair<>(liquid, amount);
    }

    @Nonnull
    protected static Pair<Liquid, Integer> getContents(@Nonnull Block b) {
        return getContents(BlockStorage.getLocationInfo(b.getLocation()));
    }

    protected void setContents(@Nonnull Block b, @Nonnull Liquid liquid, int amount) {
        if (amount > 0) {
            BlockStorage.addBlockInfo(b, "storedLiquid", liquid.getId());
        } else {
            BlockStorage.addBlockInfo(b, "storedLiquid", Liquid.NONE.getId());
        }
        BlockStorage.addBlockInfo(b, "liquidAmount", String.valueOf(Math.min(Math.max(amount, 0), this.getLiquidCapacity())));
    }

    protected void addContents(@Nonnull Block b, Liquid liquid, int amount) {
        int storedAmount = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "liquidAmount"));
        setContents(b, liquid, storedAmount + amount);
    }
}
