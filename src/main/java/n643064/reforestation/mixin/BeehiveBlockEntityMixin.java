package n643064.reforestation.mixin;

import n643064.reforestation.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin
{
    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/entity/BlockEntity;<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
                    ordinal = 0
            ),
            index = 0
    )
    // Look on my works, ye Mighty, and despair!
    private static BlockEntityType<?> modifyBlockEntityType(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState)
    {
        if (blockState.is(ModBlocks.APIARY))
            return ModBlocks.APIARY_BLOCK_ENTITY;
        return blockEntityType;
    }
}

