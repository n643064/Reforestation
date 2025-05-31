package n643064.reforestation.block;

import n643064.reforestation.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApiaryBlock extends BeehiveBlock
{
    public ApiaryBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new ApiaryBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
    {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModBlocks.APIARY_BLOCK_ENTITY, ApiaryBlockEntity::serverTick);
    }


    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack itemStack, @NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult)
    {
        if (!level.isClientSide && Config.CACHED_FRAME_TO_COMB.containsKey(itemStack.getItem()) && level.getBlockEntity(blockPos) instanceof ApiaryBlockEntity apiary)
        {
            if (!apiary.stack.isEmpty())
                player.addItem(apiary.stack);
            apiary.stack = itemStack.split(1);
            apiary.markUpdated();
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull BlockHitResult blockHitResult)
    {
        if (!player.isShiftKeyDown())
            return InteractionResult.SUCCESS_NO_ITEM_USED;
        if (level.getBlockEntity(blockPos) instanceof ApiaryBlockEntity apiary)
        {
            if (!apiary.stack.isEmpty())
            {
                if (level.isClientSide)
                {
                    player.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM);
                    return InteractionResult.SUCCESS;
                } else
                {
                    player.addItem(apiary.stack.copyAndClear());
                    apiary.markUpdated();
                }

            }
        }
        return InteractionResult.FAIL;
    }


    @Override
    public void onRemove(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, BlockState blockState2, boolean bl)
    {
        if (blockState.getBlock() != blockState2.getBlock())
        {
            if (level.getBlockEntity(blockPos) instanceof ApiaryBlockEntity entity)
            {
                Containers.dropContents(level, blockPos, entity);
                level.updateNeighbourForOutputSignal(blockPos,this);
            }
            super.onRemove(blockState, level, blockPos, blockState2, bl);
        }
    }

}
