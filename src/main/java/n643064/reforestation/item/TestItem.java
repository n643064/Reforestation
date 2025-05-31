package n643064.reforestation.item;

import n643064.reforestation.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class TestItem extends Item
{
    public TestItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext)
    {
        if (!useOnContext.getLevel().isClientSide)
            Util.harvestTree((ServerLevel) useOnContext.getLevel(), useOnContext.getClickedPos());
        return InteractionResult.PASS;
    }
}
