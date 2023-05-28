package net.minestom.server.extras.blockplacement.rules;

import net.minestom.server.event.EventBinding;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.extras.blockplacement.BlockMatchers;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class AxisPlacementRule {

    public static EventBinding<BlockEvent> AXIS_PLACEMENT_RULE = EventBinding.filtered(EventFilter.BLOCK, BlockMatchers::hasAxis)
            .map(PlayerBlockPlaceEvent.class, AxisPlacementRule::onPlace)
            .build();

    private static void onPlace(@NotNull Block block, @NotNull PlayerBlockPlaceEvent event) {
        switch (event.getBlockFace()) {
            case EAST, WEST -> event.setBlock(block.withProperty("axis", "x"));
            case NORTH, SOUTH -> event.setBlock(block.withProperty("axis", "z"));
            default -> event.setBlock(block.withProperty("axis", "y"));
        }
    }
}
