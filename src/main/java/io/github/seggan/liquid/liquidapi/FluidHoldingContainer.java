package io.github.seggan.liquid.liquidapi;

import io.github.mooy1.infinitylib.slimefun.abstracts.TickingContainer;
import io.github.seggan.liquid.items.fluids.PortableFluidTank;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    protected void setContents(@Nonnull Block b, @Nonnull InternalFluidTank... tanks) {
        for (InternalFluidTank tank : tanks) {
            this.setContents(b, tank);
        }
    }

    protected final void updateLore(@Nonnull BlockMenu menu, int slot, int tankId) {
        InternalFluidTank contents = this.getContents(menu.getBlock(), tankId);
        List<String> newLore = new ArrayList<>(Arrays.asList(
            "",
            ChatColors.color("&7Contents: " + contents.getLiquid().getName()),
            ChatColors.color("&7Amount: " + contents.getAmount())
        ));

        ItemStack stack = menu.getItemInSlot(slot);
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(newLore);
        stack.setItemMeta(meta);
    }

    /**
     * Transfers a certain amount of liquid (if compatible) from the specified {@link InternalFluidTank} to the
     * {@link PortableFluidTank}
     *
     * @param menu the {@link BlockMenu}
     * @param portableTankSlot the slot in the menu that the {@link PortableFluidTank} is in
     * @param amount amount of fluid to transfer from the internal tank to the portable tank. If the amount is
     * negative, fluid will be transferred from the portable to the internal tank instead
     * @param internalTankId the id of the internal tank
     * @return {@code true} if the transfer succeeded, {@code false} otherwise (i.e. incompatible fluids or
     * empty/full tank)
     */
    protected final boolean transferToPortableTank(@Nonnull BlockMenu menu, int portableTankSlot, int amount, int internalTankId) {
        InternalFluidTank tank = this.getContents(menu.getBlock(), internalTankId);
        ItemStack tankItem = menu.getItemInSlot(portableTankSlot);

        SlimefunItem item = SlimefunItem.getByItem(tankItem);
        if (item instanceof PortableFluidTank && tank.getAmount() > 0 && tankItem.getAmount() == 1) {
            PortableFluidTank fluidTank = (PortableFluidTank) item;

            ItemMeta meta = tankItem.getItemMeta();
            Pair<Liquid, Integer> fluidTankContents = PortableFluidTank.getContents(meta);
            // Check if not full
            if (fluidTankContents.getSecondValue() < fluidTank.getCapacity()) {
                Liquid internalTankLiquid = tank.getLiquid();
                Liquid fluidTankLiquid = fluidTankContents.getFirstValue();
                // Check if liquids are compatible
                if (fluidTankLiquid.equals(Liquid.NONE) || internalTankLiquid.equals(fluidTankLiquid)) {
                    fluidTank.addContents(meta, internalTankLiquid, amount);
                    tank.addContents(internalTankLiquid, -amount);
                    this.setContents(menu.getBlock(), tank);
                    tankItem.setItemMeta(meta);

                    return true;
                }
            }
        }

        return false;
    }
}
