package net.minestom.server.instance.block.rule.vanilla;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
import net.minestom.server.item.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Also usable for sea pickles */
public class CandlePlacementRule extends BlockPlacementRule {
    public static final int MAX_CANDLES = 4;

    public static final String CANDLE_PROPERTY = "candles";
    public static final String SEA_PICKLE_PROPERTY = "pickles";

    private final String property;

    public CandlePlacementRule(@NotNull Block block, @NotNull String property) {
        super(block);
        this.property = property;
    }

    @Override
    public @NotNull Block blockUpdate(Block.@NotNull Getter instance, @NotNull Point blockPosition, @NotNull Block currentBlock) {
        return currentBlock;
    }

    @Override
    public @Nullable Block blockPlace(
            Block.@NotNull Getter instance,
            @NotNull Block block,
            @NotNull BlockFace blockFace,
            @NotNull Point placePosition,
            @NotNull Point cursorPosition,
            @NotNull Pos playerPosition,
            @NotNull ItemMeta usedItemMeta
    ) {
        var existingBlock = instance.getBlock(placePosition);
        if (existingBlock.id() == block.id()) {
            // There is already a candle, and we are replacing it, increment the candle count
            var candles = Integer.parseInt(existingBlock.properties().get(property));
            if (candles == MAX_CANDLES) return null;
            return existingBlock.withProperty(property, String.valueOf(candles + 1));
        }

        return block;
    }

    @Override
    public boolean isSelfReplaceable(@NotNull Block block) {
        return Integer.parseInt(block.properties().get(property)) != MAX_CANDLES;
    }
}
