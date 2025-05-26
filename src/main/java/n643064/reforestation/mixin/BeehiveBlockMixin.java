package n643064.reforestation.mixin;

import n643064.reforestation.block.ApiaryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.block.Block.popResource;

@Mixin(BeehiveBlock.class)
public class BeehiveBlockMixin
{
    @Inject(method = "dropHoneycomb", at = @At("HEAD"), cancellable = true)
    private static void dropHoneyComb(Level level, BlockPos blockPos, CallbackInfo ci)
    {
        if (level.getBlockEntity(blockPos) instanceof ApiaryBlockEntity apiary)
        {
            popResource(level, blockPos, apiary.getDropStack());
            ci.cancel();
        }
    }
}
