package net.minestom.server.instance.block.rule;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.item.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BlockPlacementRule {
    protected final Block block;

    protected BlockPlacementRule(@NotNull Block block) {
        this.block = block;
    }

    /**
     * Called when the block state id can be updated (for instance if a neighbour block changed).
     *
     * @param instance      the instance of the block
     * @param blockPosition the block position
     * @param currentBlock  the current block
     * @return the updated block
     */
    public abstract @NotNull Block blockUpdate(
            @NotNull Block.Getter instance,
            @NotNull Point blockPosition,
            @NotNull Block currentBlock
    );

    /**
     * Called when the block is placed.
     *
     * @param instance       the instance of the block
     * @param block          the block placed
     * @param blockFace      the block face
     * @param placePosition  the block position clicked to perform the placement
     * @param cursorPosition the cursor position on the block
     * @param playerPosition the position of the player who placed the block
     * @param usedItemMeta   the item meta used to place the block
     * @return the block to place, {@code null} to cancel
     */
    public abstract @Nullable Block blockPlace(
            @NotNull Block.Getter instance,
            @NotNull Block block,
            @NotNull BlockFace blockFace,
            @NotNull Point placePosition,
            @NotNull Point cursorPosition,
            @NotNull Pos playerPosition,
            @NotNull ItemMeta usedItemMeta
    );

    public @Nullable Block blockPlace2(
            @NotNull Block.Getter instance,
            @NotNull Block block,
            @NotNull BlockFace blockFace,
            @NotNull Point placePosition,
            @NotNull Point cursorPosition,
            @NotNull Pos playerPosition,
            @NotNull ItemMeta usedItemMeta,
            AtomicBoolean replace
    ) {
        return null;
    }

    public boolean isSelfReplaceable(@NotNull Block block) {
        return false;
    }

    public @NotNull Block getBlock() {
        return block;
    }
}
