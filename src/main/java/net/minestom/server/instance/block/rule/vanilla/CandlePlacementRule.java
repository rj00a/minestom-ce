package net.minestom.server.instance.block.rule.vanilla;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
import net.minestom.server.item.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CandlePlacementRule extends BlockPlacementRule {
    public CandlePlacementRule(@NotNull Block block) {
        super(block);
    }

    @Override
    public @NotNull Block blockUpdate(Block.@NotNull Getter instance, @NotNull Point blockPosition, @NotNull Block currentBlock) {
        return block;
    }

    @Override
    public @Nullable Block blockPlace(Block.@NotNull Getter instance, @NotNull Block block, @NotNull BlockFace blockFace, @NotNull Point placePosition, @NotNull Point cursorPosition, @NotNull Pos playerPosition, @NotNull ItemMeta usedItemMeta) {
        var existingBlock = instance.getBlock(placePosition);
        if (existingBlock.id() == block.id()) {
            // There is already a candle, and we are replacing it, increment the candle count

        }



        System.out.println("PLACING L0000L " + block);
//        if ()
        return block;
    }

    @Override
    public boolean isSelfReplaceable() {
        return true;
    }

    @Override
    public @Nullable Block blockReplace(Block.@NotNull Getter instance, @NotNull Block block, @NotNull BlockFace blockFace, @NotNull Point replacePosition, @NotNull Point cursorPosition, @NotNull Pos playerPosition, @NotNull ItemMeta usedItemMeta) {
        var existingBlock = instance.getBlock(replacePosition);
        System.out.println(existingBlock);
        var candles = Integer.parseInt(existingBlock.properties().get("candles"));
        if (candles == 4) return null;
        return existingBlock.withProperty("candles", String.valueOf(candles + 1));
    }
}
