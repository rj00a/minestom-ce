package net.minestom.server.instance.block.rule.vanilla;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class AxisPlacementRuleTest {

    // This is kind of a scuffed test. It is pretty much testing the implementation of the rule,
    // which is certainly not how tests _should_ be. But I am lazy and at least this should prompt
    // any changes to be tested (if they make it fail)
    @ParameterizedTest
    @ArgumentsSource(AxisProvider.class)
    void testAxisFacing(BlockFace blockFace, String expected) {
        var rule = new AxisPlacementRule(Block.OAK_LOG);
        var result = rule.blockPlace(
                null, rule.getBlock(), blockFace, null,
                null, null, null
        );
        assertEquals(expected, result.getProperty("axis"));
    }

    @Test
    void testAxisUpdateStub() {
        // Just a marker to add tests in case the impl ever changes.
        var rule = new AxisPlacementRule(Block.OAK_LOG);
        Block block = Block.BLACK_WOOL; // Not even a valid block
        var result = rule.blockUpdate(null, null, block);
        assertSame(block, result);
    }

    private static class AxisProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(BlockFace.NORTH, "z"),
                    Arguments.of(BlockFace.SOUTH, "z"),
                    Arguments.of(BlockFace.EAST, "x"),
                    Arguments.of(BlockFace.WEST, "x"),
                    Arguments.of(BlockFace.TOP, "y"),
                    Arguments.of(BlockFace.BOTTOM, "y")
            );
        }
    }

}
