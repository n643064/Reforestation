package n643064.reforestation;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static n643064.reforestation.Config.CONFIG;

public class Util
{
    private static final int[][] cubeOffsets = new int[][]
            {
                    new int[] {0, 1, 0},
                    new int[] {0, -1, 0},

                    new int[] {1, 0, 0},
                    new int[] {-1, 0, 0},
                    new int[] {0, 0, 1},
                    new int[] {0, 0, -1},

                    new int[] {-1, 1, -1},
                    new int[] {1, 1, -1},
                    new int[] {1, 1, 1},
                    new int[] {-1, 1, 1},

                    new int[] {-1, 0, -1},
                    new int[] {1, 0, -1},
                    new int[] {1, 0, 1},
                    new int[] {-1, 0, 1},

                    new int[] {-1, -1, -1},
                    new int[] {1, -1, -1},
                    new int[] {1, -1, 1},
                    new int[] {-1, -1, 1},
            };

    private static final int[][] reducedOffsets = new int[][]
            {
                    new int[] {0, 1, 0},
                    new int[] {0, -1, 0},

                    new int[] {1, 0, 0},
                    new int[] {-1, 0, 0},
                    new int[] {0, 0, 1},
                    new int[] {0, 0, -1},
            };

    public static boolean harvestTree(ServerLevel level, BlockPos position)
    {
        final BlockState state = level.getBlockState(position);
        if (state.is(BlockTags.LOGS))
        {
            Block.dropResources(level.getBlockState(position), level, position);
            level.removeBlock(position, false);
            for (int[] i : cubeOffsets)
                harvestTree(level, position.offset(i[0], i[1], i[2]));
            return true;
        }
        else if (state.is(BlockTags.LEAVES))
        {
            Block.dropResources(state, level, position);
            level.removeBlock(position, false);
            for (int[] i : cubeOffsets)
                harvestLeaves(level, position.offset(i[0], i[1], i[2]), 0);
            return true;
        }
        return false;
    }

    public static void harvestLeaves(ServerLevel level, BlockPos position, int depth)
    {
        if (depth == CONFIG.leavesHarvestingMaxDepth())
            return;
        final BlockState state = level.getBlockState(position);
        if (state.is(BlockTags.LEAVES))
        {
            Block.dropResources(state, level, position);
            level.removeBlock(position, false);
            for (int[] i : cubeOffsets)
                harvestLeaves(level, position.offset(i[0], i[1], i[2]), depth+1);
        }
    }


}
