package net.minestom.server.extras.blockplacement.rules;

import net.minestom.server.coordinate.Vec;
import net.minestom.server.event.EventBinding;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.instance.block.rule.vanilla.BlockMatchers;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class RotationPlacementRule {

    public static EventBinding<BlockEvent> ROTATION_PLACEMENT_RULE = EventBinding.filtered(EventFilter.BLOCK, BlockMatchers::hasRotation)
            .map(PlayerBlockPlaceEvent.class, RotationPlacementRule::onPlace)
            .build();

    private static void onPlace(@NotNull Block block, @NotNull PlayerBlockPlaceEvent event) {
        event.setBlock(block.withProperty("rotation", String.valueOf(getRotationNumber(event.getPlayer().getPosition().direction()))));
    }

    private static int getRotationNumber(@NotNull Vec playerFacing) {
        // From https://gamedev.stackexchange.com/questions/49290/whats-the-best-way-of-transforming-a-2d-vector-into-the-closest-8-way-compass-d
        // Scaled to a 16 way compass

        double delta = Math.PI * 2 / 16;
        double theta = Math.atan2(playerFacing.z(), playerFacing.x());

        double testangle = -Math.PI + delta/2;
        int index = 12; // Initial offset to get the compass to align properly with Minecraft's x/z directions

        // TODO: Would this work better with a division/modulo?
        while (theta > testangle) {
            index++;
            testangle += delta;
        }
        return index % 16;
        /*
        0	The block is facing south. - Player faces north (Negative Z)
        1	The block is facing south-southwest.
        2	The block is facing southwest.
        3	The block is facing west-southwest.
        4	The block is facing west. - Player Faces east (Positive X)
        5	The block is facing west-northwest.
        6	The block is facing northwest.
        7	The block is facing north-northwest.
        8	The block is facing north.
        9	The block is facing north-northeast.
        10	The block is facing northeast.
        11	The block is facing east-northeast.
        12	The block is facing east.
        13	The block is facing east-southeast.
        14	The block is facing southeast.
        15	The block is facing south-southeast.
         */
    }
}
