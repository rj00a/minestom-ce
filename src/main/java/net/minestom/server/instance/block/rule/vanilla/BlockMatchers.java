package net.minestom.server.instance.block.rule.vanilla;

import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockMatchers {

    // For chests that can become double chests
    public static boolean isConnectableChest(@NotNull Block block) {
        return block.compare(Block.CHEST) || block.compare(Block.TRAPPED_CHEST);
    }

    public static boolean isWall(@NotNull Block block) {
        return BlockTags.MINECRAFT_WALLS.contains(block.namespace());
    }

    public static boolean hasAxis(@NotNull Block block) {
        return block.getProperty("axis") != null;
    }

    // Rotation - 0-15 based on facing angle
    public static boolean hasRotation(@NotNull Block block) {
        return block.getProperty("rotation") != null;
    }

    public static boolean hasFacing(@NotNull Block block) {
        return block.getProperty("facing") != null;
    }

    public static boolean hasBlockFacingVertical(@NotNull Block block) {
        return HAS_VERTICAL.contains(block.namespace());
    }



    private static final Set<NamespaceID> HAS_VERTICAL = Set.of(
            NamespaceID.from("minecraft:dispenser"),
            NamespaceID.from("minecraft:sticky_piston"),
            NamespaceID.from("minecraft:piston"),
            NamespaceID.from("minecraft:command_block"),
            NamespaceID.from("minecraft:chain_command_block"),
            NamespaceID.from("minecraft:repeating_command_block"),
            NamespaceID.from("minecraft:dropper"),
            NamespaceID.from("minecraft:end_rod"),
            NamespaceID.from("minecraft:observer"),
            NamespaceID.from("minecraft:shulker_box"),
            NamespaceID.from("minecraft:white_shulker_box"),
            NamespaceID.from("minecraft:orange_shulker_box"),
            NamespaceID.from("minecraft:magenta_shulker_box"),
            NamespaceID.from("minecraft:light_blue_shulker_box"),
            NamespaceID.from("minecraft:yellow_shulker_box"),
            NamespaceID.from("minecraft:lime_shulker_box"),
            NamespaceID.from("minecraft:pink_shulker_box"),
            NamespaceID.from("minecraft:gray_shulker_box"),
            NamespaceID.from("minecraft:light_gray_shulker_box"),
            NamespaceID.from("minecraft:cyan_shulker_box"),
            NamespaceID.from("minecraft:purple_shulker_box"),
            NamespaceID.from("minecraft:blue_shulker_box"),
            NamespaceID.from("minecraft:brown_shulker_box"),
            NamespaceID.from("minecraft:green_shulker_box"),
            NamespaceID.from("minecraft:red_shulker_box"),
            NamespaceID.from("minecraft:black_shulker_box"),
            NamespaceID.from("minecraft:barrel"),
            NamespaceID.from("minecraft:amethyst_cluster"),
            NamespaceID.from("minecraft:large_amethyst_bud"),
            NamespaceID.from("minecraft:medium_amethyst_bud"),
            NamespaceID.from("minecraft:small_amethyst_bud"),
            NamespaceID.from("minecraft:lightning_rod")
    );

    public static boolean shouldUseBlockFacing(@NotNull Block block) {
        return BlockTags.MINECRAFT_WALL_SIGNS.contains(block.namespace()) ||
                BlockTags.MINECRAFT_BUTTONS.contains(block.namespace()) ||
                BlockTags.MINECRAFT_TRAPDOORS.contains(block.namespace()) || BLOCK_FACING_LITERALS.contains(block.namespace());
    }

    private static final Set<NamespaceID> BLOCK_FACING_LITERALS = Set.of(
            NamespaceID.from("minecraft:wall_torch"),
            NamespaceID.from("minecraft:redstone_wall_torch"),
            NamespaceID.from("minecraft:soul_wall_torch"),
            NamespaceID.from("minecraft:carved_pumpkin"),
            NamespaceID.from("minecraft:jack_o_lantern"),
            NamespaceID.from("minecraft:repeater"),
            NamespaceID.from("minecraft:ender_chest"),
            NamespaceID.from("minecraft:tripwire_hook"),
            NamespaceID.from("minecraft:skeleton_wall_skull"),
            NamespaceID.from("minecraft:wither_skeleton_wall_skull"),
            NamespaceID.from("minecraft:zombie_wall_head"),
            NamespaceID.from("minecraft:player_wall_head"),
            NamespaceID.from("minecraft:creeper_wall_head"),
            NamespaceID.from("minecraft:dragon_wall_head"),
            NamespaceID.from("minecraft:piglin_wall_head"),
            NamespaceID.from("minecraft:trapped_chest"),
            NamespaceID.from("minecraft:chest"),
            NamespaceID.from("minecraft:comparator"),
            // No Tag for Wall Banners?
            NamespaceID.from("minecraft:white_wall_banner"),
            NamespaceID.from("minecraft:orange_wall_banner"),
            NamespaceID.from("minecraft:magenta_wall_banner"),
            NamespaceID.from("minecraft:light_blue_wall_banner"),
            NamespaceID.from("minecraft:yellow_wall_banner"),
            NamespaceID.from("minecraft:lime_wall_banner"),
            NamespaceID.from("minecraft:pink_wall_banner"),
            NamespaceID.from("minecraft:gray_wall_banner"),
            NamespaceID.from("minecraft:light_gray_wall_banner"),
            NamespaceID.from("minecraft:cyan_wall_banner"),
            NamespaceID.from("minecraft:purple_wall_banner"),
            NamespaceID.from("minecraft:blue_wall_banner"),
            NamespaceID.from("minecraft:brown_wall_banner"),
            NamespaceID.from("minecraft:green_wall_banner"),
            NamespaceID.from("minecraft:red_wall_banner"),
            NamespaceID.from("minecraft:black_wall_banner"),
            NamespaceID.from("minecraft:white_glazed_terracotta"),
            NamespaceID.from("minecraft:orange_glazed_terracotta"),
            NamespaceID.from("minecraft:magenta_glazed_terracotta"),
            NamespaceID.from("minecraft:light_blue_glazed_terracotta"),
            NamespaceID.from("minecraft:yellow_glazed_terracotta"),
            NamespaceID.from("minecraft:lime_glazed_terracotta"),
            NamespaceID.from("minecraft:pink_glazed_terracotta"),
            NamespaceID.from("minecraft:gray_glazed_terracotta"),
            NamespaceID.from("minecraft:light_gray_glazed_terracotta"),
            NamespaceID.from("minecraft:cyan_glazed_terracotta"),
            NamespaceID.from("minecraft:purple_glazed_terracotta"),
            NamespaceID.from("minecraft:blue_glazed_terracotta"),
            NamespaceID.from("minecraft:brown_glazed_terracotta"),
            NamespaceID.from("minecraft:green_glazed_terracotta"),
            NamespaceID.from("minecraft:red_glazed_terracotta"),
            NamespaceID.from("minecraft:black_glazed_terracotta"),
            NamespaceID.from("minecraft:glow_lichen"),
            NamespaceID.from("minecraft:cocoa"),
            NamespaceID.from("minecraft:dead_tube_coral_wall_fan"),
            NamespaceID.from("minecraft:dead_brain_coral_wall_fan"),
            NamespaceID.from("minecraft:dead_bubble_coral_wall_fan"),
            NamespaceID.from("minecraft:dead_fire_coral_wall_fan"),
            NamespaceID.from("minecraft:dead_horn_coral_wall_fan"),
            NamespaceID.from("minecraft:tube_coral_wall_fan"),
            NamespaceID.from("minecraft:brain_coral_wall_fan"),
            NamespaceID.from("minecraft:bubble_coral_wall_fan"),
            NamespaceID.from("minecraft:fire_coral_wall_fan"),
            NamespaceID.from("minecraft:horn_coral_wall_fan"),
            NamespaceID.from("minecraft:ladder"),
            NamespaceID.from("minecraft:vine"),
            NamespaceID.from("minecraft:loom"),
            NamespaceID.from("minecraft:smoker"),
            NamespaceID.from("minecraft:blast_furnace"),
            NamespaceID.from("minecraft:furnace"),
            NamespaceID.from("minecraft:grindstone"),
            NamespaceID.from("minecraft:lectern"),
            NamespaceID.from("minecraft:stonecutter"),
            NamespaceID.from("minecraft:bee_nest"),
            NamespaceID.from("minecraft:beehive"),
            NamespaceID.from("minecraft:big_dripleaf"),
            NamespaceID.from("minecraft:small_dripleaf")
    );
}
