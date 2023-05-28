package net.minestom.server.extras.blockplacement;

import net.minestom.server.event.EventNode;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.extras.blockplacement.rules.AxisPlacementRule;
import net.minestom.server.extras.blockplacement.rules.ChestPlacementRule;
import net.minestom.server.extras.blockplacement.rules.FacingPlacementRule;
import net.minestom.server.extras.blockplacement.rules.RotationPlacementRule;

public class BlockPlacementRules {

    public static void init(EventNode<BlockEvent> parentNode) {
        parentNode.register(FacingPlacementRule.FACING_PLACEMENT_RULE);
        parentNode.register(AxisPlacementRule.AXIS_PLACEMENT_RULE);
        parentNode.register(RotationPlacementRule.ROTATION_PLACEMENT_RULE);

        parentNode.register(ChestPlacementRule.CHEST_PLACEMENT_RULE);
    }
}
