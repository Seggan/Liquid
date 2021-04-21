package io.github.seggan.liquid.liquidapi;

import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * Represents a fluid tank inside an {@link FluidHoldingContainer}
 *
 * @author Seggan
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InternalFluidTank {

    private final int id;
    private final int capacity;

    private Liquid liquid;
    private int amount;

    @Nonnull
    public static InternalFluidTank create(int id, int capacity) {
        return new InternalFluidTank(id, capacity, Liquid.NONE, 0);
    }

    public String serialize() {
        return String.format("%d:%s:%d:%d", this.id, this.liquid.getId(), this.amount, this.capacity);
    }

    @Nonnull
    public static InternalFluidTank deserialize(@Nonnull String s) {
        String[] split = PatternUtils.COLON.split(s);

        int id = Integer.parseInt(split[0]);
        Liquid liquid = Liquid.getById(split[1]);
        int amount = Integer.parseInt(split[2]);
        int capacity = Integer.parseInt(split[3]);

        return new InternalFluidTank(id, capacity, liquid, amount);
    }

    public void setContents(@Nonnull Liquid liquid, int amount) {
        int clampedAmount = Math.min(Math.max(amount, 0), this.capacity);
        if (clampedAmount > 0) {
            this.liquid = liquid;
        } else {
            this.liquid = Liquid.NONE;
        }

        this.amount = clampedAmount;
    }

    /**
     * Adds the specified amount of liquid to the tank. Negative values remove liquid
     * @param amount the amount to add, or remove if negative
     */
    public void addContents(@Nonnull Liquid liquid, int amount) {
        this.setContents(liquid, this.amount + amount);
    }

    @Override
    public String toString() {
        return "InternalFluidTank[id=" + this.getId() + ", capacity=" + this.getCapacity() + ", liquid=" + this.getLiquid().getName() + ", amount=" + this.getAmount() + "]";
    }
}
