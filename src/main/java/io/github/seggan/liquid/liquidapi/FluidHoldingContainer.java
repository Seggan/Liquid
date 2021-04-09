package io.github.seggan.liquid.liquidapi;

import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.mooy1.infinitylib.slimefun.abstracts.TickingContainer;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FluidHoldingContainer extends TickingContainer {

    public FluidHoldingContainer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    protected abstract int getLiquidCapacity(int tankId);

    @Nonnull
    protected InternalFluidTank getContents(@Nonnull Config config, int tankId) {
        String s = config.getString("tank" + tankId);
        if (s == null) {
            return InternalFluidTank.create(tankId, this.getLiquidCapacity(tankId));
        } else {
            return InternalFluidTank.deserialize(s);
        }
    }

    @Nonnull
    protected InternalFluidTank getContents(@Nonnull Block b, int tankId) {
        return this.getContents(BlockStorage.getLocationInfo(b.getLocation()), tankId);
    }

    protected void setContents(@Nonnull Block b, @Nonnull InternalFluidTank tank) {
        BlockStorage.addBlockInfo(b, "tank" + tank.getId(), tank.serialize());
    }

    protected final void updateLore(@Nonnull BlockMenu menu, int slot, int tankId) {
        InternalFluidTank contents = this.getContents(menu.getBlock(), tankId);
        List<String> newLore = new ArrayList<>(Arrays.asList(
            "",
            ChatColors.color("&7Contents: " + contents.getLiquid().getName()),
            ChatColors.color("&7Amount: " + contents.getAmount())
        ));
        LoreUtils.setLore(menu.getItemInSlot(slot), newLore);
    }
}
