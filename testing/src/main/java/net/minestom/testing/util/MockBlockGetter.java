package net.minestom.testing.util;

import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.Map;

public class MockBlockGetter implements Block.Getter, Block.Setter {

    public static @NotNull MockBlockGetter create() {
        return new MockBlockGetter(Map.of());
    }

    private final Map<Vec, Block> blocks = new HashMap<>();

    private MockBlockGetter(Map<Vec, Block> blocks) {
        blocks.forEach((pos, block) -> this.blocks.put(new Vec(pos.blockX(), pos.blockY(), pos.blockZ()), block));
    }

    @Override
    public @UnknownNullability Block getBlock(int x, int y, int z, @NotNull Condition condition) {
        return blocks.getOrDefault(new Vec(x, y, z), Block.AIR);
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull Block block) {
        blocks.put(new Vec(x, y, z), block);
    }
}
