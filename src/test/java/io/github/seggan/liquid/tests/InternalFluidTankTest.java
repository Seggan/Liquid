package io.github.seggan.liquid.tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import io.github.seggan.liquid.LiquidAddon;
import io.github.seggan.liquid.liquidapi.InternalFluidTank;
import io.github.seggan.liquid.liquidapi.Liquid;
import io.github.seggan.liquid.objects.MixerRecipe;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class InternalFluidTankTest {

    private static ServerMock server;
    private static SlimefunPlugin slimefun;
    private static LiquidAddon liquid;

    @Before
    public void setUp() {
        try {
            server = MockBukkit.mock();
            slimefun = MockBukkit.load(SlimefunPlugin.class);
            liquid = MockBukkit.load(LiquidAddon.class);
        } catch (UnimplementedOperationException ignored) {
        }
    }

    @Test
    public void testMixing() {
        MixerRecipe recipe = MixerRecipe.createRecipe(Liquid.ALUMINUM, 1, Liquid.ALUMINUM_BRASS, 2, Liquid.ALUMINUM_BRONZE, 3, Liquid.BOOSTED_URANIUM, 4);

        // Invalid recipe
        InternalFluidTank tank1 = InternalFluidTank.create(0, 100);
        tank1.setContents(Liquid.ALUMINUM, 1);

        InternalFluidTank tank2 = InternalFluidTank.create(0, 100);
        tank2.setContents(Liquid.ALUMINUM_BRASS, 1);

        InternalFluidTank tank3 = InternalFluidTank.create(0, 100);
        tank3.setContents(Liquid.ALUMINUM_BRONZE, 1);

        InternalFluidTank out = InternalFluidTank.create(0, 100);

        recipe.matchesAndConsume(tank1, tank2, tank3, out);

        Assertions.assertEquals(0, out.getAmount());

        // Exact amount
        tank1 = InternalFluidTank.create(0, 100);
        tank1.setContents(Liquid.ALUMINUM, 1);

        tank2 = InternalFluidTank.create(0, 100);
        tank2.setContents(Liquid.ALUMINUM_BRASS, 2);

        tank3 = InternalFluidTank.create(0, 100);
        tank3.setContents(Liquid.ALUMINUM_BRONZE, 3);

        out = InternalFluidTank.create(0, 100);

        recipe.matchesAndConsume(tank1, tank2, tank3, out);

        Assertions.assertEquals(Liquid.BOOSTED_URANIUM, out.getLiquid());
        Assertions.assertEquals(4, out.getAmount());

        // More than enough
        tank1 = InternalFluidTank.create(0, 100);
        tank1.setContents(Liquid.ALUMINUM, 12);

        tank2 = InternalFluidTank.create(0, 100);
        tank2.setContents(Liquid.ALUMINUM_BRASS, 23);

        tank3 = InternalFluidTank.create(0, 100);
        tank3.setContents(Liquid.ALUMINUM_BRONZE, 34);

        out = InternalFluidTank.create(0, 100);

        recipe.matchesAndConsume(tank1, tank2, tank3, out);

        Assertions.assertEquals(11, tank1.getAmount());
        Assertions.assertEquals(21, tank2.getAmount());
        Assertions.assertEquals(31, tank3.getAmount());

        // Let's mix it up!
        tank1 = InternalFluidTank.create(0, 100);
        tank1.setContents(Liquid.ALUMINUM, 12);

        tank3 = InternalFluidTank.create(0, 100);
        tank3.setContents(Liquid.ALUMINUM_BRASS, 34);

        tank2 = InternalFluidTank.create(0, 100);
        tank2.setContents(Liquid.ALUMINUM_BRONZE, 23);

        out = InternalFluidTank.create(0, 100);

        System.out.println(tank1);
        System.out.println(tank2);
        System.out.println(tank3);

        recipe.matchesAndConsume(tank1, tank2, tank3, out);

        Assertions.assertEquals(11, tank1.getAmount());
        Assertions.assertEquals(20, tank2.getAmount());
        Assertions.assertEquals(32, tank3.getAmount());
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}
