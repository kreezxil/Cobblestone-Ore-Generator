package com.kreezcraft.cobblestoneoregenerator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid=CobblestoneOreGenerator.MODID, version=CobblestoneOreGenerator.VERSION, name=CobblestoneOreGenerator.NAME, acceptableRemoteVersions="*", acceptedMinecraftVersions="[1.12.2]")
public class CobblestoneOreGenerator
{
  public static final String MODID = "cobblestoneoregen";
  public static final String NAME = "CobblestoneOreGenerator";
  public static final String VERSION = "@VERSION@"; 
  @SidedProxy(clientSide="com.kreezcraft.cobblestoneoregenerator.ClientProxy", serverSide="com.kreezcraft.cobblestoneoregenerator.ServerProxy")
  public static CommonProxy proxy;
  
  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    Config.init(event);
    proxy.preInit(event);
  }
  
  @Mod.EventHandler
  public void init(FMLInitializationEvent event)
  {
    proxy.init(event);
  }
  
  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event)
  {
    proxy.postInit(event);
  }
  
  @Mod.EventHandler
  public void ServerLoad(FMLServerStartingEvent event)
  {
    event.registerServerCommand(new Commands());
  }
}
