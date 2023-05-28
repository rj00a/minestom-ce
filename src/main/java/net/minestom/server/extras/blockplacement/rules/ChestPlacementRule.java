package net.minestom.server.extras.blockplacement.rules;

import net.minestom.server.event.EventBinding;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.extras.blockplacement.BlockMatchers;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class ChestPlacementRule {

    public static EventBinding<BlockEvent> CHEST_PLACEMENT_RULE = EventBinding.filtered(EventFilter.BLOCK, BlockMatchers::isConnectableChest)
            .map(PlayerBlockPlaceEvent.class, ChestPlacementRule::onPlace)
            .map(PlayerBlockBreakEvent.class, ChestPlacementRule::onBreak)
            .build();

    private static void onPlace(Block block, PlayerBlockPlaceEvent event) {
        String facingDirection = block.getProperty("facing");
        block = block.withProperty("type", "single");

        // Place as single if player is shifting
        if (!event.getPlayer().isSneaking()) {
            // Check and try to make double chests
            // To combine into a double chest, it must be facing the same way on the left or right sides of the chest and must be the same type of chest
            switch (facingDirection) {
                case "south", "north" -> {
                    // Check blocks +/- X direction
                    Block neighborXPos = event.getInstance().getBlock(event.getBlockPosition().add(1, 0, 0));
                    Block neighborXNeg = event.getInstance().getBlock(event.getBlockPosition().add(-1, 0, 0));
                    if (neighborXPos.compare(block) && neighborXPos.getProperty("facing").equals(facingDirection) && neighborXPos.getProperty("type").equals("single")) {
                        // Valid chest, update self and neighbor
                        if (facingDirection.equals("south")) {
                            block = block.withProperty("type", "right");
                            event.getInstance().setBlock(event.getBlockPosition().add(1, 0, 0), block.withProperty("type", "left"));
                        } else {
                            block = block.withProperty("type", "left");
                            event.getInstance().setBlock(event.getBlockPosition().add(1, 0, 0), block.withProperty("type", "right"));
                        }
                    }
                    if (neighborXNeg.compare(block) && neighborXNeg.getProperty("facing").equals(facingDirection) && neighborXNeg.getProperty("type").equals("single")) {
                        // Valid chest, update self and neighbor
                        if (facingDirection.equals("south")) {
                            block = block.withProperty("type", "left");
                            event.getInstance().setBlock(event.getBlockPosition().add(-1, 0, 0), block.withProperty("type", "right"));
                        } else {
                            block = block.withProperty("type", "right");
                            event.getInstance().setBlock(event.getBlockPosition().add(-1, 0, 0), block.withProperty("type", "left"));
                        }
                    }
                }
                case "east", "west" -> {
                    // Check blocks +/- Z direction
                    Block neighborZPos = event.getInstance().getBlock(event.getBlockPosition().add(0, 0, 1));
                    Block neighborZNeg = event.getInstance().getBlock(event.getBlockPosition().add(0, 0, -1));
                    if (neighborZPos.compare(block) && neighborZPos.getProperty("facing").equals(facingDirection) && neighborZPos.getProperty("type").equals("single")) {
                        // Valid chest, update self and neighbor
                        if (facingDirection.equals("west")) {
                            block = block.withProperty("type", "right");
                            event.getInstance().setBlock(event.getBlockPosition().add(0, 0, 1), block.withProperty("type", "left"));
                        } else {
                            block = block.withProperty("type", "left");
                            event.getInstance().setBlock(event.getBlockPosition().add(0, 0, 1), block.withProperty("type", "right"));
                        }
                    }
                    if (neighborZNeg.compare(block) && neighborZNeg.getProperty("facing").equals(facingDirection) && neighborZNeg.getProperty("type").equals("single")) {
                        // Valid chest, update self and neighbor
                        if (facingDirection.equals("west")) {
                            block = block.withProperty("type", "left");
                            event.getInstance().setBlock(event.getBlockPosition().add(0, 0, -1), block.withProperty("type", "right"));
                        } else {
                            block = block.withProperty("type", "right");
                            event.getInstance().setBlock(event.getBlockPosition().add(0, 0, -1), block.withProperty("type", "left"));
                        }
                    }
                }
            }
        }
        event.setBlock(block);
    }

    private static void onBreak(@NotNull Block block, @NotNull PlayerBlockBreakEvent event) {
        if (block.getProperty("type").equals("left")) {
            String facingDirection = block.getProperty("facing");
            if (facingDirection != null) {
                switch (facingDirection) {
                    case "north" -> event.getInstance().setBlock(event.getBlockPosition().add(1, 0, 0), event.getInstance().getBlock(event.getBlockPosition().add(1, 0, 0)).withProperty("type", "single"));
                    case "south" -> event.getInstance().setBlock(event.getBlockPosition().add(-1, 0, 0), event.getInstance().getBlock(event.getBlockPosition().add(-1, 0, 0)).withProperty("type", "single"));
                    case "east" -> event.getInstance().setBlock(event.getBlockPosition().add(0, 0, 1), event.getInstance().getBlock(event.getBlockPosition().add(0, 0, 1)).withProperty("type", "single"));
                    case "west" -> event.getInstance().setBlock(event.getBlockPosition().add(0, 0, -1), event.getInstance().getBlock(event.getBlockPosition().add(0, 0, -1)).withProperty("type", "single"));
                }
            }
        } else if (block.getProperty("type").equals("right")) {
            String facingDirection = block.getProperty("facing");
            if (facingDirection != null) {
                switch (facingDirection) {
                    case "north" -> event.getInstance().setBlock(event.getBlockPosition().add(-1, 0, 0), event.getInstance().getBlock(event.getBlockPosition().add(-1, 0, 0)).withProperty("type", "single"));
                    case "south" -> event.getInstance().setBlock(event.getBlockPosition().add(1, 0, 0), event.getInstance().getBlock(event.getBlockPosition().add(1, 0, 0)).withProperty("type", "single"));
                    case "east" -> event.getInstance().setBlock(event.getBlockPosition().add(0, 0, -1), event.getInstance().getBlock(event.getBlockPosition().add(0, 0, -1)).withProperty("type", "single"));
                    case "west" -> event.getInstance().setBlock(event.getBlockPosition().add(0, 0, 1), event.getInstance().getBlock(event.getBlockPosition().add(0, 0, 1)).withProperty("type", "single"));
                }
            }
        }
    }
}
