package n643064.reforestation.entity;

import n643064.reforestation.Reforestation;
import n643064.reforestation.Util;
import n643064.reforestation.block.ModBlocks;
import n643064.reforestation.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.Stack;

public class MinecartHarvester extends MinecartHopper
{

    public MinecartHarvester(EntityType<? extends MinecartHopper> type, Level level)
    {
        super(type, level);
    }

    @Override
    public @NotNull BlockState getDefaultDisplayBlockState()
    {
        return ModBlocks.MACHINE_CASING.defaultBlockState();
    }

    @Override
    protected @NotNull Item getDropItem()
    {
        return ModItems.HARVESTER_MINECART;
    }

    @Override
    public @NotNull AABB getSuckAabb()
    {
        return Reforestation.SUCK_AABB;
    }

    @Override
    public void tick()
    {
        final Level level = level();
        if (!level.isClientSide)
        {
            Direction direction = getMotionDirection().getClockWise();
            harvest((ServerLevel) level, blockPosition().offset(direction.getNormal()));
            harvest((ServerLevel) level, blockPosition().offset(direction.getOpposite().getNormal()));
        }
        super.tick();
    }

    private final Stack<BlockPos> saplingPlacementStack = new Stack<>();

    @Override
    public boolean suckInItems()
    {
        if (super.suckInItems())
        {
            if (saplingPlacementStack.empty())
                return true;
            for (ItemStack stack : getItemStacks())
            {
                if (stack.is(ItemTags.SAPLINGS) && stack.getItem() instanceof BlockItem blockItem)
                {
                    int c = stack.getCount();
                    while (c-- != 0)
                    {
                        this.level().setBlockAndUpdate(saplingPlacementStack.pop(), blockItem.getBlock().defaultBlockState());
                        stack.shrink(1);
                        if (saplingPlacementStack.empty())
                            return true;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public @NotNull ItemStack getPickResult()
    {
        return new ItemStack(ModItems.HARVESTER_MINECART);
    }

    void harvest(ServerLevel level, BlockPos pos)
    {
        if (Util.harvestTree(level, pos))
        {
            for (ItemStack stack : getItemStacks())
            {
                if (stack.is(ItemTags.SAPLINGS) && stack.getItem() instanceof BlockItem blockItem)
                {
                    level.setBlockAndUpdate(pos, blockItem.getBlock().defaultBlockState());
                    stack.shrink(1);
                    return;
                }
            }
            saplingPlacementStack.add(pos);
        }
    }
}
