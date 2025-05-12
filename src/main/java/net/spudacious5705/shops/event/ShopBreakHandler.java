// Path: net/spudacious5705/shops/event/ShopBreakHandler.java
package net.spudacious5705.shops.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.spudacious5705.shops.block.entity.ShopEntity;

public class ShopBreakHandler implements AttackBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ShopEntity shop) {
            if (!shop.canBreak(player)) {
                if (world.isClient()) {
                    // Consider using translatable text components for messages
                    player.sendMessage(Text.translatable("message.spuds-shops.cannot_break.not_owner"), true);
                }
                // FAIL is more appropriate here to indicate the action was denied.
                return ActionResult.FAIL;
            }
        }
        // If it's not our shop, or if canBreak is true, PASS to allow normal processing.
        return ActionResult.PASS;
    }
}