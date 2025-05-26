package n643064.reforestation.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.HashSet;

import static n643064.reforestation.Reforestation.MODID;

public class ModItems
{
    public static final HashSet<Item> ITEM_SET = new HashSet<>();
    public static final CreativeModeTab ITEMS = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.reforestation.items"))
            .displayItems((itemDisplayParameters, output) ->
            {
                for (Item i : ITEM_SET)
                    output.accept(i);
            })
            .build();


    public static void init()
    {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(MODID, "items"), ITEMS);

    }

    private Item registerGenericItem(String name,Item.Properties properties)
    {
        return registerItem(name, new Item(properties));
    }

    private Item registerItem(String name, Item item)
    {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, name), item);
        ITEM_SET.add(item);
        return item;
    }
}
