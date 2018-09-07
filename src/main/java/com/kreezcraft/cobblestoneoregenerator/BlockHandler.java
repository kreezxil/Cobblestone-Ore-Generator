package com.kreezcraft.cobblestoneoregenerator;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHandler
{
  @SubscribeEvent
  public void onBlockCreate(BlockEvent.NeighborNotifyEvent event)
  {
    try
    {
      BlockPos pos = event.getPos();
      World world = event.getWorld();
      if ((!event.getForceRedstoneUpdate()) || (!world.isBlockPowered(pos))) {
        return;
      }
      if ((event.getState().getBlock() == Blocks.COBBLESTONE) && (isGen(world, pos, false)))
      {
        Config.oreBlocks StoneBlock = rndBlock(Config.CobblestoneBlockList);
        if (StoneBlock != null) {
          world.setBlockState(pos, Block.getBlockFromName(StoneBlock.name).getStateFromMeta(StoneBlock.meta));
        }
      }
      if ((event.getState().getBlock() == Blocks.STONE) && (event.getState().getBlock().getMetaFromState(event.getState()) == 0) && (isGen(world, pos, true)))
      {
        Config.oreBlocks StoneBlock = rndBlock(Config.StoneBlockList);
        if (StoneBlock != null) {
          world.setBlockState(pos, Block.getBlockFromName(StoneBlock.name).getStateFromMeta(StoneBlock.meta));
        }
      }
    }
    catch (Throwable ex)
    {
      System.out.println("[CobblestoneOreGenerator] error getting/setting block.\n" + ex);
    }
  }
  
  private boolean isGen(World world, BlockPos pos, boolean Stone)
  {
    Block N = world.getBlockState(pos.offset(EnumFacing.NORTH, 1)).getBlock();
    Block E = world.getBlockState(pos.offset(EnumFacing.EAST, 1)).getBlock();
    Block S = world.getBlockState(pos.offset(EnumFacing.SOUTH, 1)).getBlock();
    Block W = world.getBlockState(pos.offset(EnumFacing.WEST, 1)).getBlock();
    if (Stone)
    {
      Block U = world.getBlockState(pos.offset(EnumFacing.UP, 1)).getBlock();
      if (((U == Blocks.FLOWING_LAVA) || (U == Blocks.LAVA)) && ((N == Blocks.FLOWING_WATER) || (E == Blocks.FLOWING_WATER) || (S == Blocks.FLOWING_WATER) || (W == Blocks.FLOWING_WATER) || (N == Blocks.WATER) || (E == Blocks.WATER) || (S == Blocks.WATER) || (W == Blocks.WATER))) {
        return true;
      }
      return false;
    }
    if (((N == Blocks.FLOWING_LAVA) || (E == Blocks.FLOWING_LAVA) || (S == Blocks.FLOWING_LAVA) || (W == Blocks.FLOWING_LAVA)) && ((N == Blocks.FLOWING_WATER) || (E == Blocks.FLOWING_WATER) || (S == Blocks.FLOWING_WATER) || (W == Blocks.FLOWING_WATER))) {
      return true;
    }
    return false;
  }
  
  private Config.oreBlocks rndBlock(TreeMap<Double, Config.oreBlocks> BlockList)
  {
    Random random = new Random();
    for (Map.Entry<Double, Config.oreBlocks> oreEntry : BlockList.entrySet()) {
      if (((Double)oreEntry.getKey()).doubleValue() > random.nextDouble() * 100.0D) {
        return (Config.oreBlocks)oreEntry.getValue();
      }
    }
    return null;
  }
}
