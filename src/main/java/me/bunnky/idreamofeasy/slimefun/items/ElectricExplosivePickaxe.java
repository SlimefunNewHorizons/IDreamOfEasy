package me.bunnky.idreamofeasy.slimefun.items;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.tools.ExplosivePickaxe;
import me.bunnky.idreamofeasy.utils.IDOEUtility;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class ElectricExplosivePickaxe extends ExplosivePickaxe implements Rechargeable {

    private final float cap;
    private static final float COST = 0.3F;

    public ElectricExplosivePickaxe(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, float cap) {
        super(itemGroup, item, recipeType, recipe);
        IDOEUtility.setGlow(item);
        this.cap = cap;
        addItemHandler(onRightClick());
    }

    public ItemUseHandler onRightClick() {
        return PlayerRightClickEvent::cancel;
    }

    @Override
    public @NotNull ToolUseHandler getItemHandler() {
        return (e, tool, fortune, drops) -> {
            Player p = e.getPlayer();
            ItemStack itemInHand = p.getInventory().getItemInMainHand();

            if (!removeItemCharge(itemInHand, COST)) {
                e.setCancelled(true);
                return;
            }

            super.getItemHandler().onToolUse(e, tool, fortune, drops);
        };
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return cap;
    }

    @Override
    protected boolean canBreak(@NotNull Player p, @NotNull Block b) {
        return super.canBreak(p, b);
    }
}