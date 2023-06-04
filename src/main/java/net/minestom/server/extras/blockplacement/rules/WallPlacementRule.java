package net.minestom.server.extras.blockplacement.rules;

import net.minestom.server.event.EventBinding;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.instance.block.rule.vanilla.BlockMatchers;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class WallPlacementRule {

    public static EventBinding<BlockEvent> WALL_PLACEMENT_RULE = EventBinding.filtered(EventFilter.BLOCK, BlockMatchers::isConnectableChest)
            .map(PlayerBlockPlaceEvent.class, WallPlacementRule::onPlace)
            .map(PlayerBlockBreakEvent.class, WallPlacementRule::onBreak)
            .build();

    private static void onPlace(@NotNull Block block, @NotNull PlayerBlockPlaceEvent event) {

    }

    private static void onBreak(@NotNull Block block, @NotNull PlayerBlockBreakEvent event) {

    }

    // Will connect to other walls nearby - does not have to be same type
    // Connections can either be "low" or "tall" - to be tall, there must be a connection above in the same connection
}
