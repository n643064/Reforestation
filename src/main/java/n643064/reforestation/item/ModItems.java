package n643064.reforestation.item;

import n643064.reforestation.block.ModBlocks;
import n643064.reforestation.entity.ModEntityTypes;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MinecartItem;

import java.util.ArrayList;

import static n643064.reforestation.Reforestation.MODID;

public class ModItems
{
    public static final ArrayList<Item> ITEM_LIST = new ArrayList<>();
    public static final CreativeModeTab ITEMS = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.reforestation.items"))
            .icon(() -> ModBlocks.APIARY.asItem().getDefaultInstance())
            .displayItems((itemDisplayParameters, output) ->
            {
                for (Item i : ITEM_LIST)
                    output.accept(i);
            })
            .build();

    public static final CustomMinecartItem HARVESTER_MINECART = new CustomMinecartItem(new Item.Properties().stacksTo(1), ModEntityTypes.HARVESTER);

    public static void init()
    {

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(MODID, "items"), ITEMS);


        registerItem("harvester_minecart", HARVESTER_MINECART);
        //registerItem("test", new TestItem(new Item.Properties()));

        registerGenericItem("frame");
        registerFrameAndComb("amethyst");
        registerFrameAndComb("coal");
        registerFrameAndComb("copper");
        registerFrameAndComb("diamond");
        registerFrameAndComb("emerald");
        registerFrameAndComb("gold");
        registerFrameAndComb("iron");
        registerFrameAndComb("lapis");
        registerFrameAndComb("redstone");
    }

    private static void registerFrameAndComb(String resource)
    {
        registerItem(resource + "_frame", new Item(new Item.Properties().stacksTo(1)));
        registerGenericItem(resource + "_comb");
    }

    private static void registerGenericItem(String name)
    {
        registerItem(name, new Item(new Item.Properties()));
    }

    private static void registerItem(String name, Item item)
    {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, name), item);
        ITEM_LIST.add(item);
    }
}
