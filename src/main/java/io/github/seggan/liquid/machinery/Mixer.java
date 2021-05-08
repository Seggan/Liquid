package io.github.seggan.liquid.machinery;

import com.google.common.collect.ImmutableSet;
import io.github.seggan.liquid.Items;
import io.github.seggan.liquid.LiquidAddon;
import io.github.seggan.liquid.items.fluids.PortableFluidTank;
import io.github.seggan.liquid.liquidapi.FluidHoldingContainer;
import io.github.seggan.liquid.liquidapi.InternalFluidTank;
import io.github.seggan.liquid.liquidapi.Liquid;
import io.github.seggan.liquid.objects.MixerRecipe;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

// TODO make it consume energy
public class Mixer extends FluidHoldingContainer implements EnergyNetComponent {

    private static final int[] BACKGROUND = new int[]{27, 28, 29, 30, 31, 36, 37, 39, 40, 45, 46, 47, 48, 49};
    private static final int[] INPUT_BORDER = new int[]{0, 2, 4, 6, 8, 9, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int[] OUTPUT_BORDER = new int[]{32, 33, 34, 35, 41, 44, 50, 51, 52, 53};

    private static final int TANK_0_CONTENTS = 1;
    private static final int TANK_1_CONTENTS = 3;
    private static final int TANK_2_CONTENTS = 5;

    private static final int TANK_0_INPUT = 10;
    private static final int TANK_1_INPUT = 12;
    private static final int TANK_2_INPUT = 14;

    private static final int OUTPUT_TANK_CONTENTS = 7;
    private static final int OUTPUT_TANK_INPUT = 16;

    private static final int ENABLED = 38;

    private static final int[] OUTPUT_SLOTS = new int[]{42, 43};

    private static final ItemStack ENABLED_ITEM = new CustomItem(
        Material.STRUCTURE_VOID,
        "&aEnabled",
        "",
        "&7Click to disable the Mixer"
    );
    private static final ItemStack DISABLED_ITEM = new CustomItem(
        Material.BARRIER,
        "&cDisabled",
        "",
        "&7Click to enable the Mixer"
    );

    public static final RecipeType RECIPE_TYPE = new RecipeType(
        new NamespacedKey(LiquidAddon.inst(), "mixer"),
        Items.MIXER
    );

    private static final Set<MixerRecipe> recipes = new HashSet<>();

    static {
        addRecipe(MixerRecipe.createRecipe(Liquid.ALUMINUM, Liquid.BRASS, Liquid.ALUMINUM_BRASS));
        addRecipe(MixerRecipe.createRecipe(Liquid.ALUMINUM, Liquid.BRONZE, Liquid.ALUMINUM_BRONZE));
        addRecipe(MixerRecipe.createRecipe(Liquid.ALUMINUM, Liquid.COPPER, Liquid.DURALUMIN));
        addRecipe(MixerRecipe.createRecipe(Liquid.IRON, Liquid.GOLD_24, Liquid.GILDED_IRON));
        addRecipe(MixerRecipe.createRecipe(Liquid.IRON, Liquid.CARBON, Liquid.STEEL));
        addRecipe(MixerRecipe.createRecipe(Liquid.STEEL, 1, Liquid.CARBON, 2, Liquid.NONE, 0, Liquid.DAMASCUS_STEEL, 3));
        addRecipe(MixerRecipe.createRecipe(Liquid.IRON, Liquid.SILICON, Liquid.FERROSILICON));
        addRecipe(MixerRecipe.createRecipe(Liquid.IRON, Liquid.COPPER, Liquid.NICKEL));
        addRecipe(MixerRecipe.createRecipe(Liquid.NICKEL, Liquid.COPPER, Liquid.COBALT));
        addRecipe(MixerRecipe.createRecipe(Liquid.COPPER, Liquid.TIN, Liquid.BRONZE));
        addRecipe(MixerRecipe.createRecipe(Liquid.COPPER, Liquid.ZINC, Liquid.BRASS));
        addRecipe(MixerRecipe.createRecipe(Liquid.BRONZE, Liquid.GOLD_24, Liquid.CORINTHIAN_BRONZE));
        addRecipe(MixerRecipe.createRecipe(Liquid.LEAD, Liquid.TIN, Liquid.SOLDER));
        addRecipe(MixerRecipe.createRecipe(Liquid.COPPER, Liquid.SILVER, Liquid.BILLION));
        addRecipe(MixerRecipe.createRecipe(Liquid.ALUMINUM, 3, Liquid.COPPER, 3, Liquid.STEEL, 3, Liquid.HARDENED_METAL, 4));
        addRecipe(MixerRecipe.createRecipe(Liquid.HARDENED_METAL, 1, Liquid.CARBON, 8, Liquid.GOLD_24, 3, Liquid.REINFORCED_ALLOY, 3));
        addRecipe(MixerRecipe.createRecipe(Liquid.URANIUM, Liquid.PLUTONIUM, Liquid.BOOSTED_URANIUM));
        addRecipe(MixerRecipe.createRecipe(Liquid.HARDENED_METAL, Liquid.REDSTONE, Liquid.FERROSILICON, Liquid.REDSTONE_ALLOY));
    }

    public Mixer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    public static void addRecipe(MixerRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);

        for (int i : INPUT_BORDER) {
            preset.addItem(i, ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i : OUTPUT_BORDER) {
            preset.addItem(i, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(ENABLED, DISABLED_ITEM);

        preset.addItem(TANK_0_CONTENTS, new CustomItem(
            Material.LAVA_BUCKET,
            "&6Tank 1 Contents (Input Below)",
            "",
            "&7Liquid: None",
            "&7Amount: 0"
        ), ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(TANK_1_CONTENTS, new CustomItem(
            Material.LAVA_BUCKET,
            "&6Tank 2 Contents (Input Below)",
            "",
            "&7Liquid: None",
            "&7Amount: 0"
        ), ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(TANK_2_CONTENTS, new CustomItem(
            Material.LAVA_BUCKET,
            "&6Tank 3 Contents (Input Below)",
            "",
            "&7Liquid: None",
            "&7Amount: 0"
        ), ChestMenuUtils.getEmptyClickHandler());

        preset.addItem(OUTPUT_TANK_CONTENTS, new CustomItem(
            Material.LAVA_BUCKET,
            "&6Output Tank Contents (Input Below)",
            "",
            "&7Liquid: None",
            "&7Amount: 0"
        ), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (Boolean.parseBoolean(BlockStorage.getLocationInfo(b.getLocation(), "enabled"))) {
            menu.replaceExistingItem(ENABLED, ENABLED_ITEM);
            menu.addMenuClickHandler(ENABLED, (p, slot, item, action) -> {
                BlockStorage.addBlockInfo(b, "enabled", Boolean.toString(false));
                this.onNewInstance(menu, b);
                return false;
            });
        } else {
            menu.replaceExistingItem(ENABLED, DISABLED_ITEM);
            menu.addMenuClickHandler(ENABLED, (p, slot, item, action) -> {
                BlockStorage.addBlockInfo(b, "enabled", Boolean.toString(true));
                this.onNewInstance(menu, b);
                return false;
            });
        }
    }

    @Override
    protected void tick(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Config data) {
        if (!Boolean.parseBoolean(data.getString("enabled"))) return;

        InternalFluidTank t1 = this.getContents(b, 0);
        InternalFluidTank t2 = this.getContents(b, 1);
        InternalFluidTank t3 = this.getContents(b, 2);
        InternalFluidTank out = this.getContents(b, 3);
        for (MixerRecipe recipe : recipes) {
            if (recipe.matchesAndConsume(t1, t2, t3, out)) {
                this.setContents(b, t1, t2, t3, out);
                break;
            }
        }

        int rate = LiquidAddon.inst().getConfig().getInt("liquid-transfer-rate", 1, Integer.MAX_VALUE);
        this.transferToPortableTank(menu, TANK_0_INPUT, -rate, 0);
        this.transferToPortableTank(menu, TANK_1_INPUT, -rate, 1);
        this.transferToPortableTank(menu, TANK_2_INPUT, -rate, 2);
        this.transferToPortableTank(menu, OUTPUT_TANK_INPUT, rate, 3);

        this.updateLore(menu, TANK_0_CONTENTS, t1.getId());
        this.updateLore(menu, TANK_1_CONTENTS, t2.getId());
        this.updateLore(menu, TANK_2_CONTENTS, t3.getId());
        this.updateLore(menu, OUTPUT_TANK_CONTENTS, out.getId());
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.INSERT) {
            SlimefunItem it = SlimefunItem.getByItem(item);
            if (it instanceof PortableFluidTank) {
                Pair<Liquid, Integer> contents = PortableFluidTank.getContents(item);
                Liquid liquid = contents.getFirstValue();

                if (!(menu instanceof BlockMenu)) return new int[0];
                Block b = ((BlockMenu) menu).getBlock();

                if (this.getContents(b, 0).getLiquid().equals(liquid)) {
                    return new int[]{TANK_0_INPUT};
                } else if (this.getContents(b, 1).getLiquid().equals(liquid)) {
                    return new int[]{TANK_1_INPUT};
                } else if (this.getContents(b, 2).getLiquid().equals(liquid)) {
                    return new int[]{TANK_2_INPUT};
                } else if (this.getContents(b, 3).getLiquid().equals(liquid)) {
                    return new int[]{OUTPUT_TANK_INPUT};
                } else {
                    return new int[0];
                }
            } else {
                return new int[0];
            }
        } else {
            return OUTPUT_SLOTS;
        }
    }

    @Override
    protected int getLiquidCapacity(int tankId) {
        return 729;
    }


    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return 512;
    }

    public static ImmutableSet<MixerRecipe> getRecipes() {
        return ImmutableSet.copyOf(recipes);
    }
}
