package n643064.reforestation;

import com.google.common.collect.ImmutableSet;
import n643064.reforestation.block.ModBlocks;
import n643064.reforestation.entity.ModEntityTypes;
import n643064.reforestation.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.Set;

public class Reforestation implements ModInitializer
{
    public static final String MODID = "reforestation";

    public static AABB SUCK_AABB;


    public static ResourceKey<PoiType> APIARY;



    @Override
    public void onInitialize()
    {
        ServerWorldEvents.LOAD.register(Config::generateCaches);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success) -> Config.generateCaches(server, null));
        Config.setup();

        final int range = Config.CONFIG.harvesterItemPickupRange();
        SUCK_AABB = Shapes.box(-range, -2, -range, range, range, range).toAabbs().getFirst();

        ModEntityTypes.init();

        ModBlocks.init();
        ModItems.init();

        /*
            TODO:
                - Tree stuff
                - Planter
                - Other farming stuff
                - Magic stuff
                
         */



        final ResourceLocation apiary = ResourceLocation.fromNamespaceAndPath(MODID, "apiary");
        APIARY = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, apiary);
        final Set<BlockState> states = ImmutableSet.copyOf(ModBlocks.APIARY.getStateDefinition().getPossibleStates());
        PoiTypes.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, APIARY, states, 0, 1);
    }

}
