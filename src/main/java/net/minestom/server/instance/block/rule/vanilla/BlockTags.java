package net.minestom.server.instance.block.rule.vanilla;

import net.minestom.server.MinecraftServer;
import net.minestom.server.gamedata.tags.Tag;

import java.util.Objects;

/**
 * A list of tags to various groups of blocks, taken from the block_tags.json from running <a href="https://github.com/hollow-cube/minestom-ce-data">Minestom CE's Data Generator</a>
 */
public final class BlockTags {

    public static final Tag MINECRAFT_STAIRS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:stairs"));

    public static final Tag MINECRAFT_WALLS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:walls"));

    public static final Tag MINECRAFT_SLABS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:slabs"));

    public static final Tag MINECRAFT_BUTTONS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:buttons"));

    public static final Tag MINECRAFT_FENCES = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:fences"));

    public static final Tag MINECRAFT_STANDING_SIGNS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:standing_signs"));

    public static final Tag MINECRAFT_WALL_SIGNS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:wall_signs"));

    public static final Tag MINECRAFT_ANVILS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:anvil"));

    public static final Tag MINECRAFT_TRAPDOORS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:trapdoors"));
}
