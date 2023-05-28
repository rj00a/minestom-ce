package net.minestom.server.extras.blockplacement;

import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class BlockMatchers {

    // For chests that can become double chests
    public static boolean isConnectableChest(@NotNull Block block) {
        return block.compare(Block.CHEST) || block.compare(Block.TRAPPED_CHEST);
    }
}
