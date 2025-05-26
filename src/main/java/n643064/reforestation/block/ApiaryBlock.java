package n643064.reforestation.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
    @NotNull
    protected List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder)
    {
        if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof ApiaryBlockEntity apiary)
        {
            final ItemStack stack = this.getCloneItemStack(builder.getLevel(), apiary.getBlockPos(), blockState);
            return List.of(stack);
        }
        return super.getDrops(blockState, builder);
    }

}
