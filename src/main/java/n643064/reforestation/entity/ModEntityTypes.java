package n643064.reforestation.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static n643064.reforestation.Reforestation.MODID;

public class ModEntityTypes
{

    public static EntityType<MinecartHarvester> HARVESTER;

    public static void init()
    {
        HARVESTER = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "harvester"),
                EntityType.Builder.of(MinecartHarvester::new, MobCategory.MISC)
                        .sized(0.98F, 0.7F)
                        .passengerAttachments(0.1875F)
                        .clientTrackingRange(8)
                        .build());
    }

}
