package com.kreezcraft.cobblestoneoregenerator;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;

public class ClientProxy
  extends CommonProxy
{
  public void preInit(FMLPreInitializationEvent event)
  {
    super.preInit(event);
  }
  
  public void init(FMLInitializationEvent event)
  {
    MinecraftForge.EVENT_BUS.register(new BlockHandler());
    super.init(event);
  }
  
  public void postInit(FMLPostInitializationEvent event)
  {
    super.postInit(event);
  }
}
