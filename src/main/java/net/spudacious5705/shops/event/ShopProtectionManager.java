// Path: net/spudacious5705/shops/event/ShopProtectionManager.java
package net.spudacious5705.shops.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.spudacious5705.shops.SpudaciousShops;
import net.spudacious5705.shops.block.entity.ShopEntity; // Assuming this is your shop block entity

public class ShopProtectionManager {

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            BlockEntity be = world.getBlockEntity(pos); // Get fresh BlockEntity

            if (be instanceof ShopEntity shop) {
                SpudaciousShops.LOGGER.info("[ShopProtection] PlayerBlockBreakEvents.BEFORE triggered for ShopEntity at {}.", pos);
                SpudaciousShops.LOGGER.info("[ShopProtection] BlockState: {}", state.toString());


                if (player != null) {
                    SpudaciousShops.LOGGER.info("[ShopProtection] Event 'player' Class: {}", player.getClass().getName());
                    SpudaciousShops.LOGGER.info("[ShopProtection] Event 'player' Name: {}", player.getName().getString());
                    SpudaciousShops.LOGGER.info("[ShopProtection] Event 'player' UUID: {}", player.getUuid().toString());

                    // For safety, add a getter in ShopEntity for its owner UUID if you don't have one
                    // SpudsShopsMod.LOGGER.info("[ShopProtection] Shop's Stored Owner UUID: {}", shop.getOwnerUuid());

                    boolean canBreak = shop.canBreak(player); // canBreak will also log
                    SpudaciousShops.LOGGER.info("[ShopProtection] shop.canBreak(player) returned: {}", canBreak);

                    if (!canBreak) {
                        SpudaciousShops.LOGGER.info("[ShopProtection] Denying break for player.");
                        return false; // Cancel break
                    }
                    SpudaciousShops.LOGGER.info("[ShopProtection] Allowing break for player.");
                    return true; // Allow break
                } else {
                    SpudaciousShops.LOGGER.info("[ShopProtection] Event 'player' is NULL. Assuming non-player action (e.g., turtle). Denying break.");
                    return false; // Cancel break for null player
                }
            }
            // Not a shop block, let vanilla handle it
            return true;
        });
    }
}