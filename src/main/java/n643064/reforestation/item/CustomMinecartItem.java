package n643064.reforestation.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class CustomMinecartItem extends Item
{
    // TODO: Dispenser behavior

    private final EntityType<? extends AbstractMinecart> entityType;
    public CustomMinecartItem(Properties properties, EntityType<? extends AbstractMinecart> entityType)
    {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    @NotNull
    public InteractionResult useOn(UseOnContext useOnContext)
    {
        final Level level = useOnContext.getLevel();
        final BlockPos blockPos = useOnContext.getClickedPos();
        final BlockState state = level.getBlockState(blockPos);

        if (state.is(BlockTags.RAILS) && level instanceof ServerLevel serverLevel)
        {
            System.out.println("serverlevel");
            final ItemStack stack = useOnContext.getItemInHand();
            double x = blockPos.getX() + 0.5;
            double y = blockPos.getY() + 0.0625;
            double z = blockPos.getZ() + 0.5;
            if (state.getBlock() instanceof BaseRailBlock railBlock && state.getValue(railBlock.getShapeProperty()).isAscending())
                y += 0.5;

            final AbstractMinecart minecart = entityType.create(level);
            assert minecart != null;
            minecart.setPos(x, y, z);
            minecart.xo = x;
            minecart.yo = y;
            minecart.zo = z;
            EntityType.createDefaultStackConfig(serverLevel, stack, useOnContext.getPlayer()).accept(minecart);

            stack.shrink(1);
            serverLevel.addFreshEntity(minecart);
            serverLevel.gameEvent(GameEvent.ENTITY_PLACE, blockPos, GameEvent.Context.of(useOnContext.getPlayer(), serverLevel.getBlockState(blockPos.below())));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else
            return InteractionResult.FAIL;
    }
}
