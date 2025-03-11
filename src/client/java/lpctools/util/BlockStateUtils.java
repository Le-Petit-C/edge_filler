package lpctools.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public class BlockStateUtils {
    public static boolean isReplaceable(BlockState state){
        return state.isReplaceable() || state.getBlock().equals(Blocks.SCULK_VEIN);
    }
    public static boolean isReplaceable(BlockPos pos){
        ClientWorld world = MinecraftClient.getInstance().world;
        if(world == null) return false;
        return isReplaceable(world.getBlockState(pos));
    }
    public static boolean isContainingLiquid(BlockState state){
        return state.getFluidState().getLevel() != 0;
    }
    public static boolean isReplaceableLiquid(BlockState state){
        return isReplaceable(state) && isContainingLiquid(state);
    }
    public static boolean isZeroHardBlock(BlockState state){
        return state.getBlock().getHardness() == 0 || state.getBlock() == Blocks.KELP || state.getBlock() == Blocks.KELP_PLANT;
    }
}
