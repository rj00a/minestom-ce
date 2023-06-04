package net.minestom.server.instance.block.rule.vanilla.placementrules;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
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
    public @NotNull Block blockUpdate(@NotNull UpdateState updateState) {
        return updateState.currentBlock();
    }

    @Override
    public @Nullable Block blockPlace(@NotNull PlacementState placementState) {
        var existingBlock = placementState.instance().getBlock(placementState.placePosition());
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
