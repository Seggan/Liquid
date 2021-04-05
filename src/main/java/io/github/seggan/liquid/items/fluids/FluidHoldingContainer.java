package io.github.seggan.liquid.items.fluids;

import javafx.util.Pair;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

public interface FluidHoldingContainer {

    int getLiquidCapacity();

    @Nonnull
    static Pair<Liquid, Integer> getContents(@Nonnull Block b) {
        Liquid liquid = Liquid.getById(BlockStorage.getLocationInfo(b.getLocation(), "storedLiquid"));
        int amount = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "liquidAmount"));
        return new Pair<>(liquid, amount);
    }

    default void setContents(@Nonnull Block b, @Nonnull Liquid liquid, int amount) {
        BlockStorage.addBlockInfo(b, "storedLiquid", liquid.getId());
        BlockStorage.addBlockInfo(b, "liquidAmount", String.valueOf(Math.min(amount, this.getLiquidCapacity())));
    }

    default void addContents(@Nonnull Block b, int amount) {
        int storedAmount = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "liquidAmount"));
        BlockStorage.addBlockInfo(
            b,
            "liquidAmount",
            String.valueOf(Math.min(storedAmount + amount, this.getLiquidCapacity()))
        );
    }
}
