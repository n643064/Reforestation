package n643064.reforestation.client;

import n643064.reforestation.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MinecartRenderer;

public class ReforestationClient implements ClientModInitializer
{

    @Override
    public void onInitializeClient()
    {
        EntityRendererRegistry.register(ModEntityTypes.HARVESTER, ctx -> new MinecartRenderer<>(ctx, ModelLayers.HOPPER_MINECART));
    }
}
