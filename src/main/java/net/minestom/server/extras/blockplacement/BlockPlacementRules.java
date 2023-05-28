package net.minestom.server.extras.blockplacement;

import net.minestom.server.event.EventNode;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.extras.blockplacement.rules.ChestPlacementRule;

public class BlockPlacementRules {

    public static void init(EventNode<BlockEvent> parentNode) {
        parentNode.register(ChestPlacementRule.CHEST_PLACEMENT_RULE);
    }
}
