package n643064.reforestation.block;

import n643064.reforestation.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static n643064.reforestation.Reforestation.MODID;

public class ModBlocks
{
    public static Block APIARY;
    public static Block MACHINE_CASING;
    public static BlockEntityType<ApiaryBlockEntity> APIARY_BLOCK_ENTITY;

    public static void init()
    {
        APIARY = registerBlockWithItem("apiary", new ApiaryBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE))).getBlock();
        APIARY_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "apiary"),
                BlockEntityType.Builder.of(ApiaryBlockEntity::new, APIARY).build());
        MACHINE_CASING = registerBlockWithItem("machine_casing", new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)
                .sound(SoundType.HEAVY_CORE))).getBlock();
    }


    private static BlockItem registerBlockWithItem(String name, Block block)
    {
        final ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(MODID, name);
        Registry.register(BuiltInRegistries.BLOCK, rl, block);
        final BlockItem bi = Registry.register(BuiltInRegistries.ITEM, rl, new BlockItem(block, new Item.Properties()));
        ModItems.ITEM_LIST.add(bi);
        return bi;
    }
}
