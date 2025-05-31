package n643064.reforestation.block;

import n643064.reforestation.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApiaryBlockEntity extends BeehiveBlockEntity implements WorldlyContainer
{
    public ApiaryBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(blockPos, blockState);
    }
    protected ItemStack stack = ItemStack.EMPTY;

    public ItemStack getDropStack()
    {
        final Item i = stack.getItem();
        if (Config.CACHED_FRAME_TO_COMB.containsKey(i))
            return Config.CACHED_FRAME_TO_COMB.get(i).getDefaultInstance();
        return new ItemStack(Items.HONEYCOMB, 3);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState state, ApiaryBlockEntity apiaryBlockEntity)
    {
        BeehiveBlockEntity.serverTick(level, blockPos, state, apiaryBlockEntity);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider)
    {
        if (compoundTag.isEmpty())
            stack = ItemStack.EMPTY;
        else
            stack = ItemStack.parseOptional(provider, compoundTag.getCompound("frame"));

    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider)
    {
        compoundTag.put("frame", stack.saveOptional(provider));
        super.saveAdditional(compoundTag, provider);
    }

    public void markUpdated()
    {
        assert level != null;
        level.setBlock(getBlockPos(), getBlockState().setValue(BeehiveBlock.HONEY_LEVEL, 0), 3);
        setChanged();
        assert level != null;
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider)
    {
        return saveCustomOnly(provider);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction direction)
    {
        return new int[]{0};
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction)
    {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction)
    {
        return false;
    }

    @Override
    public int getContainerSize()
    {
        return 1;
    }

    @Override
    public @NotNull ItemStack getItem(int i)
    {
        return stack;
    }

    @Override
    public @NotNull ItemStack removeItem(int i, int j)
    {
        final ItemStack s = stack.copyAndClear();
        markUpdated();
        return s;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int i)
    {
        return stack.copyAndClear();
    }

    @Override
    public void setItem(int i, ItemStack itemStack)
    {
        stack = itemStack;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        stack = ItemStack.EMPTY;
    }
}
