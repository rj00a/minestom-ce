package net.minestom.server.instance.block.rule.vanilla;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
import net.minestom.server.item.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class StairsPlacementRule extends BlockPlacementRule {

    public StairsPlacementRule(@NotNull Block block) {
        super(block);
    }


    @Override
    public @NotNull Block blockUpdate(@NotNull Block.Getter instance, @NotNull Point blockPosition, @NotNull Block block) {
        Shape shape = getShape(instance, blockPosition, getFacing(block), getHalf(block));
        return block.withProperty("shape", shape.toString().toLowerCase());
    }

    @Override
    public Block blockPlace(
            @NotNull Block.Getter instance,
            @NotNull Block block,
            @NotNull BlockFace blockFace,
            @NotNull Point placePosition,
            @NotNull Point cursorPosition,
            @NotNull Pos playerPosition,
            @NotNull ItemMeta usedItemMeta
    ) {
        var facing = BlockFace.fromYaw(playerPosition.yaw());
        var blockPosition = placePosition.relative(blockFace);

        var half = blockFace == BlockFace.BOTTOM || blockFace == BlockFace.TOP ?
                blockFace.getOppositeFace() :
                (cursorPosition.y() > 0.5 ? BlockFace.TOP : BlockFace.BOTTOM);
        var shape = getShape(instance, blockPosition, facing, half);

        String waterlogged = "false"; //todo
        return block.withProperties(Map.of(
                "facing", facing.name().toLowerCase(),
                "half", half.name().toLowerCase(),
                "shape", shape.name().toLowerCase(),
                "waterlogged", waterlogged));
    }

    private enum Shape {
        STRAIGHT,
        OUTER_LEFT,
        OUTER_RIGHT,
        INNER_LEFT,
        INNER_RIGHT
    }

    private static @Nullable BlockFace getFacing(Block block) {
        var value = block.getProperty("facing");
        if (value == null) return null;
        return BlockFace.valueOf(value.toUpperCase());
    }

    private static @Nullable BlockFace getHalf(Block block) {
        var value = block.getProperty("half");
        if (value == null) return null;
        return BlockFace.valueOf(value.toUpperCase());
    }

    private Shape getShape(Block.Getter instance, Point blockPosition, BlockFace facing, BlockFace half) {
        var shape = getShapeFromSide(instance, blockPosition, facing, true, Shape.OUTER_LEFT, Shape.OUTER_RIGHT, half);
        if (shape == null) {
            shape = getShapeFromSide(instance, blockPosition, facing, false, Shape.INNER_LEFT, Shape.INNER_RIGHT, half);
        }
        return shape == null ? Shape.STRAIGHT : shape;
    }

    private Shape getShapeFromSide(
            Block.Getter instance, Point blockPosition,
            BlockFace facing, boolean front,
            Shape left, Shape right, BlockFace half
    ) {
        var neighbor = instance.getBlock(blockPosition.relative(front ? facing : facing.getOppositeFace()));
        if (!isStairsBlock(neighbor) || half != getHalf(neighbor)) return null;

        BlockFace otherFacing = getFacing(neighbor);
        if (otherFacing == null) return null;
        // Skip faces with equal or opposite directions
        if (sameAxis(otherFacing, facing)) return null;

        if (checkNeighbor(instance, blockPosition, facing, front ? otherFacing.getOppositeFace() : otherFacing, half)) {
            return otherFacing == rotate(facing) ? left : right;
        }

        return null;
    }

    private boolean sameAxis(BlockFace face1, BlockFace face2) {
        return face1 == face2 || face1 == face2.getOppositeFace();
    }

    private BlockFace rotate(BlockFace facing) {
        return switch (facing) {
            case NORTH -> BlockFace.WEST;
            case EAST -> BlockFace.NORTH;
            case SOUTH -> BlockFace.EAST;
            case WEST -> BlockFace.SOUTH;
            default -> throw new IllegalStateException("Invalid block face");
        };
    }

    private boolean checkNeighbor(Block.Getter world, Point pos, BlockFace facing, BlockFace otherFacing, BlockFace half) {
        Block neighbor = world.getBlock(pos.relative(otherFacing));
        return !isStairsBlock(neighbor) || facing != getFacing(neighbor) || half != getHalf(neighbor);
    }

    public boolean isStairsBlock(Block block) {
        //todo probably should use stairs tag for this
        return block.name().contains("stairs");
    }

}
