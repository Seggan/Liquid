package io.github.seggan.liquid.categories;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.seggan.liquid.Items;
import io.github.seggan.liquid.Liquid;
import io.github.seggan.liquid.machinery.Mixer;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

public class MixerCategory extends FlexCategory {

    public static final MixerCategory INSTANCE = new MixerCategory();

    private final int[] recipeSlots = new int[] {12, 13, 14, 21, 22, 23, 30, 31, 32};

    public MixerCategory() {
        super(new NamespacedKey(Liquid.getInstance(), "Mixer_recipes_category"),
            new CustomItem(Items.MIXER.getType(), "&6Mixer Recipes"));
    }

    @Override
    public boolean isVisible(Player player, PlayerProfile playerProfile, SlimefunGuideMode mode) {
        return mode != SlimefunGuideMode.CHEAT_MODE;
    }

    @Override
    public void open(Player player, PlayerProfile playerProfile, SlimefunGuideMode mode) {
        List<MachineRecipe> recipes = ((Mixer) SlimefunItem.getByItem(Items.MIXER)).getMachineRecipes();

        ChestMenu menu = new ChestMenu("&6Mixer Recipes");

        // Header
        for (int i = 0; i < 9; ++i) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.setEmptySlotsClickable(false);

        menu.addItem(1, new CustomItem(ChestMenuUtils.getBackButton(player, "",
            ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(player, "guide.back.guide")))
        );

        menu.addMenuClickHandler(1, (pl, slot, item, action) -> {
            SlimefunPlugin.getRegistry().getSlimefunGuide(mode).openMainMenu(playerProfile, 1);
            return false;
        });

        int i = 9;
        for (MachineRecipe recipe : recipes) {
            ItemStack out = recipe.getOutput()[0].clone();
            out.setAmount(1);
            menu.addItem(i++, out, (player1, i1, itemStack, clickAction) -> {
                displayItem(playerProfile, recipe);
                return false;
            });
        }

        menu.open(player);
    }

    private void displayItem(PlayerProfile p, MachineRecipe r) {
        ChestMenu menu = new ChestMenu("&7Recipe for " + r.getOutput()[0].getItemMeta().getDisplayName());

        Player player = p.getPlayer();

        // Header
        for (int i = 0; i < 9; ++i) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(1, new CustomItem(ChestMenuUtils.getBackButton(player, "",
            ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(player, "guide.back.guide")))
        );
        menu.addMenuClickHandler(1, (pl, s, is, action) -> {
            open(player, p, SlimefunGuideMode.SURVIVAL_MODE);
            return false;
        });

        displayItem(menu, player, p, r.getOutput()[0], r.getInput());

        menu.open(player);
    }

    private void displayItem(ChestMenu menu, Player p, PlayerProfile profile, ItemStack output, ItemStack[] recipe) {
        for (int i = 0; i < 9; ++i) {
            try {
                menu.addItem(recipeSlots[i], recipe[i], (player, i1, itemStack, clickAction) -> {
                    if (itemStack != null) {
                        SlimefunGuide.displayItem(profile, itemStack, true);
                    }
                    return false;
                });
            } catch (ArrayIndexOutOfBoundsException e) {
                menu.addItem(recipeSlots[i], new ItemStack(Material.AIR), ChestMenuUtils.getEmptyClickHandler());
            }
        }

        p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        menu.addItem(19, Mixer.RECIPE_TYPE.getItem(p), ChestMenuUtils.getEmptyClickHandler());
        menu.addItem(25, output, ChestMenuUtils.getEmptyClickHandler());
    }
}
