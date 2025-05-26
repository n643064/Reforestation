package n643064.reforestation.block;

import n643064.reforestation.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static n643064.reforestation.Reforestation.MODID;

public class ModBlocks
{
    public static Block APIARY;
    public static BlockEntityType<ApiaryBlockEntity> APIARY_BLOCK_ENTITY;

    public static void init()
    {
        APIARY = registerBlockWithItem("apiary", new ApiaryBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE))).getBlock();
        APIARY_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "apiary"),
                BlockEntityType.Builder.of(ApiaryBlockEntity::new, APIARY).build());
    }


    private static BlockItem registerBlockWithItem(String name, Block block)
    {
        final ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(MODID, name);
        Registry.register(BuiltInRegistries.BLOCK, rl, block);
        final BlockItem bi = Registry.register(BuiltInRegistries.ITEM, rl, new BlockItem(block, new Item.Properties()));
        ModItems.ITEM_SET.add(bi);
        return bi;
    }
}
