package io.github.seggan.liquid.objects;

import io.github.seggan.liquid.liquidapi.InternalFluidTank;
import io.github.seggan.liquid.liquidapi.Liquid;
import lombok.AccessLevel;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Getter
public final class MixerRecipe {
    private final Pair<Liquid, Integer> one;
    private final Pair<Liquid, Integer> two;
    private final Pair<Liquid, Integer> three;

    @Getter(AccessLevel.NONE)
    private final Set<Pair<Liquid, Integer>> cache = new HashSet<>();

    private final Pair<Liquid, Integer> output;

    private MixerRecipe(Pair<Liquid, Integer> one, Pair<Liquid, Integer> two, Pair<Liquid, Integer> three, Pair<Liquid, Integer> output) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.output = output;

        this.cache.addAll(Arrays.asList(this.one, this.two, this.three));
    }

    /**
     * Creates a new {@code MixerRecipe}
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static MixerRecipe createRecipe(Liquid onel, int onea, Liquid twol, int twoa, Liquid threel, int threea, Liquid outl, int outa) {
        return new MixerRecipe(new Pair<>(onel, onea), new Pair<>(twol, twoa), new Pair<>(threel, threea), new Pair<>(outl, outa));
    }

    /**
     * Creates a new {@code MixerRecipe} with all liquid amounts 1
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static MixerRecipe createRecipe(Liquid onel, Liquid twol, Liquid threel, Liquid outl) {
        int nonNone = 3;
        if (onel.isNone()) nonNone--;
        if (twol.isNone()) nonNone--;
        if (threel.isNone()) nonNone--;

        return new MixerRecipe(getPair(onel), getPair(twol), getPair(threel), new Pair<>(outl, nonNone));
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static MixerRecipe createRecipe(Liquid onel, int onea, Liquid twol, int twoa, Liquid outl, int outa) {
        return new MixerRecipe(new Pair<>(onel, onea), new Pair<>(twol, twoa), new Pair<>(Liquid.NONE, 0), new Pair<>(outl, outa));
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static MixerRecipe createRecipe(Liquid onel, Liquid twol, Liquid outl) {
        return createRecipe(onel, twol, Liquid.NONE, outl);
    }

    private static Pair<Liquid, Integer> getPair(Liquid l) {
        return new Pair<>(l, l.isNone() ? 0 : 1);
    }

    @ParametersAreNonnullByDefault
    public boolean matchesAndConsume(InternalFluidTank tankOne, InternalFluidTank tankTwo, InternalFluidTank tankThree, InternalFluidTank outputTank) {
        List<InternalFluidTank> tanks = new ArrayList<>(Arrays.asList(tankOne, tankTwo, tankThree));

        Map<Pair<Liquid, Integer>, InternalFluidTank> found = new HashMap<>();
        for (Pair<Liquid, Integer> liquid : this.cache) {
            InternalFluidTank foundTank = null;
            for (InternalFluidTank tank : tanks) {
                if (tank.getLiquid().equals(liquid.getFirstValue()) && tank.getAmount() >= liquid.getSecondValue()) {
                    foundTank = tank;
                    break;
                }
            }

            if (foundTank != null) {
                tanks.remove(foundTank);
                found.put(liquid, foundTank);
            } else {
                return false;
            }
        }

        if (found.size() == 3) {
            for (Map.Entry<Pair<Liquid, Integer>, InternalFluidTank> entry : found.entrySet()) {
                Pair<Liquid, Integer> liquid = entry.getKey();
                entry.getValue().addContents(liquid.getFirstValue(), -liquid.getSecondValue());
            }

            outputTank.addContents(this.output.getFirstValue(), this.output.getSecondValue());

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.one, this.two, this.three);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
}
