package n643064.reforestation.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ApiaryBlockEntity extends BeehiveBlockEntity
{
    public ApiaryBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(blockPos, blockState);
    }
    private String resourceType = "stone";

    public ItemStack getDropStack()
    {
        return new ItemStack(Items.HONEYCOMB, 3);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState state, ApiaryBlockEntity apiaryBlockEntity)
    {
        // TODO: something here idk
        BeehiveBlockEntity.serverTick(level, blockPos, state, apiaryBlockEntity);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider)
    {
        super.loadAdditional(compoundTag, provider);
        resourceType = compoundTag.getString("resourceType");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider)
    {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putString("resourceType", resourceType);
    }
}
