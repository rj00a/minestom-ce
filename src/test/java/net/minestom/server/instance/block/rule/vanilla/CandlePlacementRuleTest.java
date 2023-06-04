package net.minestom.server.instance.block.rule.vanilla;

import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
import net.minestom.server.instance.block.rule.vanilla.placementrules.BlockStackingPlacementRule;
import net.minestom.testing.util.MockBlockGetter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CandlePlacementRuleTest {

    @Test
    void testNoCandle() {
        var rule = new BlockStackingPlacementRule(Block.BLACK_CANDLE, BlockStackingPlacementRule.CANDLE_PROPERTY);
        var result = rule.blockPlace(new BlockPlacementRule.PlacementState(
                MockBlockGetter.empty(),
                rule.getBlock(), null, Vec.ZERO,
                null, null, null, false
        ));
        assertEquals("1", result.getProperty("candles"));
    }

    @Test
    void testMaxCandle() {
        var rule = new BlockStackingPlacementRule(Block.BLACK_CANDLE, BlockStackingPlacementRule.CANDLE_PROPERTY);
        var block = Block.BLACK_CANDLE.withProperty("candles", "4");
        var result = rule.blockPlace(new BlockPlacementRule.PlacementState(
                MockBlockGetter.single(block),
                rule.getBlock(), null, Vec.ZERO,
                null, null, null, false
        ));
        assertNull(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testCandleStack(int candles) {
        var rule = new BlockStackingPlacementRule(Block.BLACK_CANDLE, BlockStackingPlacementRule.CANDLE_PROPERTY);
        var block = Block.BLACK_CANDLE.withProperty("candles", String.valueOf(candles));
        var result = rule.blockPlace(new BlockPlacementRule.PlacementState(
                MockBlockGetter.single(block),
                rule.getBlock(), null, Vec.ZERO,
                null, null, null, false
        ));
        assertEquals(String.valueOf(candles + 1), result.getProperty("candles"));
    }

    @Test
    void testUpdateStub() {
        // Just a marker to add tests in case the impl ever changes.
        var rule = new BlockStackingPlacementRule(Block.BLACK_CANDLE, BlockStackingPlacementRule.CANDLE_PROPERTY);
        Block block = Block.BLACK_WOOL; // Not even a valid block
        var result = rule.blockUpdate(new BlockPlacementRule.UpdateState(null, null, block));
        assertSame(block, result);
    }

    @Test
    void testSelfReplaceable() {
        // 1-3 candles are self replaceable, 4 is not
        var rule = new BlockStackingPlacementRule(Block.BLACK_CANDLE, BlockStackingPlacementRule.CANDLE_PROPERTY);
        assertTrue(rule.isSelfReplaceable(Block.BLACK_CANDLE.withProperty("candles", "1")));
        assertTrue(rule.isSelfReplaceable(Block.BLACK_CANDLE.withProperty("candles", "2")));
        assertTrue(rule.isSelfReplaceable(Block.BLACK_CANDLE.withProperty("candles", "3")));
        assertFalse(rule.isSelfReplaceable(Block.BLACK_CANDLE.withProperty("candles", "4")));
    }

}
