package net.minestom.server.extras.blockplacement.rules;

import net.minestom.server.entity.Player;
import net.minestom.server.event.EventBinding;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.extras.blockplacement.BlockMatchers;
import net.minestom.server.extras.blockplacement.BlockTags;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.utils.Direction;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FacingPlacementRule {

    public static EventBinding<BlockEvent> FACING_PLACEMENT_RULE = EventBinding.filtered(EventFilter.BLOCK, BlockMatchers::hasFacing)
            .map(PlayerBlockPlaceEvent.class, FacingPlacementRule::onPlace)
            .build();

    private static void onPlace(@NotNull Block block, @NotNull PlayerBlockPlaceEvent event) {
        if (BlockMatchers.hasBlockFacingVertical(block)) {
            String facing = event.getBlockFace().getOppositeFace().toDirection().opposite().name();
            event.setBlock(block.withProperty("facing", facing));
        } else {
            Direction direction = event.getBlockFace() == BlockFace.BOTTOM ? getPlayerFacingOrientation(event.getPlayer()) : event.getBlockFace().toDirection();
            if (BlockTags.MINECRAFT_ANVILS.contains(block.namespace())) {
                direction = switch (direction) {
                    case DOWN, UP, WEST -> Direction.NORTH;
                    case NORTH -> Direction.EAST;
                    case SOUTH -> Direction.WEST;
                    case EAST -> Direction.SOUTH;
                };
            }
            if (BlockMatchers.shouldUseBlockFacing(block)) {
                direction = direction.opposite();
            }
            event.setBlock(block.withProperty("facing", direction.name()));
        }
    }

    private static Direction getPlayerFacingOrientation(@NotNull Player player) {
        float yaw = player.getPosition().yaw();
        if (yaw >= 135 || yaw <= -135) {
            return Direction.NORTH;
        } else if (yaw >= 45) {
            return Direction.WEST;
        } else if (yaw >= -45) {
            return Direction.SOUTH;
        } else {
            return Direction.EAST;
        }
    }

    // TODO: Sort - Chiseled Bookshelf, Hanging Signs, Pink Petals, decorated pot
    // TODO: Hoppers, Bell
}
