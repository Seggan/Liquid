package io.github.seggan.liquid.objects;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.ItemAttribute;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.utils.NumberUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.logging.Level;
import javax.annotation.Nonnull;

public interface FluidHolder extends ItemAttribute {

    int getFluidCapacity();

    default boolean isFluidHolder() {
        return getFluidCapacity() > 0;
    }

    default int getFluidAmount(@Nonnull Location l) {
        if (!isFluidHolder()) {
            return 0;
        }

        return getFluidAmount(l, BlockStorage.getLocationInfo(l));
    }

    default int getFluidAmount(@Nonnull Location l, @Nonnull Config data) {
        Validate.notNull(l, "Location was null!");
        Validate.notNull(data, "data was null!");

        if (!isFluidHolder()) {
            return 0;
        }

        String fluidAmount = data.getString("fluid-amount");

        if (fluidAmount != null) {
            return Integer.parseInt(fluidAmount);
        } else {
            return 0;
        }
    }

    default void setFluidAmount(@Nonnull Location l, int fluidAmount) {
        Validate.notNull(l, "Location was null!");
        Validate.isTrue(fluidAmount >= 0, "You can only set a fluidAmount of zero or more!");

        try {
            int fluidCapacity = getFluidCapacity();

            if (fluidCapacity > 0) {
                fluidAmount = NumberUtils.clamp(0, fluidAmount, fluidCapacity);

                // Do we even need to update the value?
                if (fluidAmount != getFluidAmount(l)) {
                    BlockStorage.addBlockInfo(l, "fluid-amount", String.valueOf(fluidAmount), false);

                }
            }
        } catch (Exception | LinkageError x) {
            Bukkit.getLogger().log(Level.SEVERE, x, () -> "Exception while trying to set the fluid-amount for \""
                    + /*getId()*/ "block" + "\" at " + new BlockPosition(l));
        }
    }

    default void addFluidAmount(@Nonnull Location l, int fluidAmount) {
        Validate.notNull(l, "Location was null!");
        Validate.isTrue(fluidAmount > 0, "You can only add a positive fluidAmount!");

        try {
            int fluidCapacity = getFluidCapacity();

            if (fluidCapacity > 0) {
                int currentAmount = getFluidAmount(l);

                if (currentAmount < fluidCapacity) {
                    int newFluidAmount = Math.min(fluidCapacity, currentAmount + fluidAmount);
                    BlockStorage.addBlockInfo(l, "fluid-amount", String.valueOf(newFluidAmount), false);
                }
            }
        } catch (Exception | LinkageError x) {
            Bukkit.getLogger().log(Level.SEVERE, x, () -> "Exception while trying to add an fluid-amount for \"" + /*getId()*/ "block" + "\" at " + new BlockPosition(l));
        }
    }

    default void removeFluidAmount(@Nonnull Location l, int fluidAmount) {
        Validate.notNull(l, "Location was null!");
        Validate.isTrue(fluidAmount > 0, "The fluidAmount to remove must be greater than zero!");

        try {
            int fluidCapacity = getFluidCapacity();

            if (fluidCapacity > 0) {
                int currentFluidAmount = getFluidAmount(l);

                // Check if there is even any fluids stored
                if (currentFluidAmount > 0) {
                    int newFluidAmount = Math.max(0, currentFluidAmount - fluidAmount);
                    BlockStorage.addBlockInfo(l, "fluid-amount", String.valueOf(newFluidAmount), false);

                }
            }
        } catch (Exception | LinkageError x) {
            Bukkit.getLogger().log(Level.SEVERE, x, () -> "Exception while trying to remove an fluid-amount for \"" + /*getId()*/ "block" + "\" at " + new BlockPosition(l));
        }
    }

    

    default Fluid getFluid(@Nonnull Location l) {
        if (!isFluidHolder()) {
            return null;
        }

        return getFluid(l, BlockStorage.getLocationInfo(l));
    }

    default Fluid getFluid(@Nonnull Location l, @Nonnull Config data) {
        Validate.notNull(l, "Location was null!");
        Validate.notNull(data, "data was null!");

        if (!isFluidHolder()) {
            return null;
        }

        String fluidStored = data.getString("fluid-stored");

        if (fluidStored != null) {
            return (Fluid) SlimefunItem.getById(fluidStored);
        } else {
            return null;
        }
    }

    default void setFluid(@Nonnull Location l, Fluid fluidStored) {
        Validate.notNull(l, "Location was null!");
        Validate.notNull(fluidStored, "fluidStored was null!");

        try {

            if (isFluidHolder()) {

                if (fluidStored != getFluid(l)) {
                    BlockStorage.addBlockInfo(l, "fluid-stored", fluidStored.getId(), false);

                }
            }
        } catch (Exception | LinkageError x) {
            Bukkit.getLogger().log(Level.SEVERE, x, () -> "Exception while trying to set the fluid-stored for \""
                    + /*getId()*/ "block" + "\" at " + new BlockPosition(l));
        }
    }

}
