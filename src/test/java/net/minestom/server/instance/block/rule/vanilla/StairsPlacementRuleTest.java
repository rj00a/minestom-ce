package net.minestom.server.instance.block.rule.vanilla;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
import net.minestom.server.instance.block.rule.vanilla.placementrules.StairsPlacementRule;
import net.minestom.server.item.ItemMeta;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.testing.util.MockBlockGetter;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class StairsPlacementRuleTest {
    private static final Block.Getter EMPTY_BLOCK_GETTER = MockBlockGetter.empty();
    public static final Block BLOCK = Block.STONE_STAIRS;
    private static final ItemMeta EMPTY_META = ItemStack.of(Material.AIR).meta();

    private final BlockPlacementRule rule = new StairsPlacementRule(BLOCK);

    void testPlacementFacing() {

    }

    // Property: half

    @ParameterizedTest
    @EnumSource(value = BlockFace.class, names = {"NORTH", "SOUTH", "EAST", "WEST"})
    void testPlacementHorizontalHalfBottom(@NotNull BlockFace blockFace) {
        var result = rule.blockPlace(new BlockPlacementRule.PlacementState(
                EMPTY_BLOCK_GETTER, BLOCK, blockFace,
                Vec.ZERO.relative(blockFace.getOppositeFace()),
                new Vec(0, 0.25, 0), Pos.ZERO, EMPTY_META, false
        ));
        assertEquals(BlockFace.BOTTOM.name().toLowerCase(), result.getProperty("half"));
    }

    @ParameterizedTest
    @EnumSource(value = BlockFace.class, names = {"NORTH", "SOUTH", "EAST", "WEST"})
    void testPlacementHorizontalHalfTop(@NotNull BlockFace blockFace) {
        var result = rule.blockPlace(new BlockPlacementRule.PlacementState(
                EMPTY_BLOCK_GETTER, BLOCK, blockFace,
                Vec.ZERO.relative(blockFace.getOppositeFace()),
                new Vec(0, 0.75, 0), Pos.ZERO, EMPTY_META, false
        ));
        assertEquals(BlockFace.TOP.name().toLowerCase(), result.getProperty("half"));
    }

    @Test
    void testPlacementHalfTop() {
        var result = rule.blockPlace(new BlockPlacementRule.PlacementState(
                EMPTY_BLOCK_GETTER, BLOCK, BlockFace.TOP,
                Vec.ZERO, Vec.ZERO, Pos.ZERO, EMPTY_META, false
        ));
        assertEquals("bottom", result.getProperty("half"));
    }

    @Test
    void testPlacementHalfBottom() {
        var result = rule.blockPlace(new BlockPlacementRule.PlacementState(
                EMPTY_BLOCK_GETTER, BLOCK, BlockFace.BOTTOM,
                Vec.ZERO, Vec.ZERO, Pos.ZERO, EMPTY_META, false
        ));
        assertEquals("top", result.getProperty("half"));
    }

    // Property: shape

    void testPlacementShape() {

    }

    // Property: waterlogged

    @Test
    void testPlacementWaterloggedStub() {
        var result = rule.blockPlace(new BlockPlacementRule.PlacementState(
                EMPTY_BLOCK_GETTER, BLOCK, BlockFace.TOP,
                Vec.ZERO, Vec.ZERO, Pos.ZERO, EMPTY_META, false
        ));
        assertEquals("false", result.getProperty("waterlogged"));
    }

}
