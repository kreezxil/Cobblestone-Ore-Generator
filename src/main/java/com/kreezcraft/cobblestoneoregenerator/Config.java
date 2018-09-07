package com.kreezcraft.cobblestoneoregenerator;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	private static Configuration config;
	public static TreeMap<Double, oreBlocks> CobblestoneBlockList = new TreeMap();
	public static TreeMap<Double, oreBlocks> StoneBlockList = new TreeMap();
	private static File cfg;

	public static class oreBlocks {
		public String name;
		public int meta;

		oreBlocks(String name, int meta) {
			this.name = name;
			this.meta = meta;
		}
	}

	public static void init(FMLPreInitializationEvent event) {
		cfg = new File(event.getModConfigurationDirectory(), "CobblestoneOreGenerator.cfg");
		CobblestoneBlockList.put(Double.valueOf(8.0D), new oreBlocks("minecraft:coal_ore", 0));
		CobblestoneBlockList.put(Double.valueOf(6.0D), new oreBlocks("minecraft:iron_ore", 0));
		CobblestoneBlockList.put(Double.valueOf(5.0D), new oreBlocks("minecraft:redstone_ore", 0));
		CobblestoneBlockList.put(Double.valueOf(4.0D), new oreBlocks("minecraft:gold_ore", 0));
		CobblestoneBlockList.put(Double.valueOf(3.0D), new oreBlocks("minecraft:lapis_ore", 0));
		CobblestoneBlockList.put(Double.valueOf(2.5D), new oreBlocks("minecraft:quartz_ore", 0));
		CobblestoneBlockList.put(Double.valueOf(2.0D), new oreBlocks("minecraft:glowstone", 0));
		CobblestoneBlockList.put(Double.valueOf(1.0D), new oreBlocks("minecraft:diamond_ore", 0));
		CobblestoneBlockList.put(Double.valueOf(0.5D), new oreBlocks("minecraft:emerald_ore", 0));
		StoneBlockList.put(Double.valueOf(75.3D), new oreBlocks("minecraft:mossy_cobblestone", 0));
		StoneBlockList.put(Double.valueOf(75.2D), new oreBlocks("minecraft:stone", 1));
		StoneBlockList.put(Double.valueOf(75.1D), new oreBlocks("minecraft:stone", 3));
		StoneBlockList.put(Double.valueOf(75.0D), new oreBlocks("minecraft:stone", 5));
		StoneBlockList.put(Double.valueOf(50.5D), new oreBlocks("minecraft:sandstone", 0));
		StoneBlockList.put(Double.valueOf(50.0D), new oreBlocks("minecraft:red_sandstone", 0));
		StoneBlockList.put(Double.valueOf(10.5D), new oreBlocks("minecraft:gravel", 0));
		StoneBlockList.put(Double.valueOf(10.4D), new oreBlocks("minecraft:clay", 0));
		StoneBlockList.put(Double.valueOf(10.3D), new oreBlocks("minecraft:grass", 0));
		StoneBlockList.put(Double.valueOf(10.2D), new oreBlocks("minecraft:dirt", 2));
		StoneBlockList.put(Double.valueOf(10.1D), new oreBlocks("minecraft:mycelium", 0));
		StoneBlockList.put(Double.valueOf(10.0D), new oreBlocks("minecraft:sand", 0));
		StoneBlockList.put(Double.valueOf(5.2D), new oreBlocks("minecraft:netherrack", 0));
		StoneBlockList.put(Double.valueOf(5.1D), new oreBlocks("minecraft:soul_sand", 0));
		StoneBlockList.put(Double.valueOf(5.0D), new oreBlocks("minecraft:end_stone", 0));
		loadConfig();
	}

	public static int loadConfig() {
		TreeMap<Double, oreBlocks> result = new TreeMap();
		try {
			if (cfg.createNewFile()) {
				config = new Configuration(cfg);
				config.setCategoryComment("COBBLESTONEBLOCKS",
						"blocks and there chance to generate on cobblestone-generator\nD:\"block;meta\"=chance --> example: D:\"lucky:lucky_block;0=3.0\" ;)\nchance of every entry must be different!");
				config.getCategory("COBBLESTONEBLOCKS");
				for (Map.Entry<Double, oreBlocks>oreEntry: CobblestoneBlockList.entrySet()) {
					config.get("COBBLESTONEBLOCKS", ((oreBlocks) oreEntry.getValue()).name + ";" + ((oreBlocks) oreEntry.getValue()).meta,
							((Double) oreEntry.getKey()).doubleValue());
				}
				config.setCategoryComment("STONEBLOCKS",
						"blocks and there chance to generate on stone-generator\nD:\"block;meta\"=chance --> example: D:\"lucky:lucky_block;0=3.0\" ;)\nchance of every entry must be different!");
				config.getCategory("STONEBLOCKS");
				for (Map.Entry<Double, oreBlocks>oreEntry: StoneBlockList.entrySet()) {
					config.get("STONEBLOCKS", ((oreBlocks) oreEntry.getValue()).name + ";" + ((oreBlocks) oreEntry.getValue()).meta,
							((Double) oreEntry.getKey()).doubleValue());
				}
				config.save();
				System.out.println("[CobblestoneOreGenerator] new configuration file created");
			}
			config = new Configuration(cfg);

			String[] namedWithMeta = null;
			String name = null;
			int meta = 0;
			double chance = 0.0D;
			CobblestoneBlockList.clear();
			StoneBlockList.clear();
			Map<String, Property> stoneBlocks = config.getCategory("COBBLESTONEBLOCKS");
			for (Map.Entry<String, Property>oreEntry: stoneBlocks.entrySet()) {
				if (((String) oreEntry.getKey()).contains(";")) {
					namedWithMeta = ((String) oreEntry.getKey()).split(";");
					name = namedWithMeta[0];
					meta = Integer.parseInt(namedWithMeta[1]);
				} else {
					name = (String) oreEntry.getKey();
					meta = 0;
				}
				chance = ((Property) oreEntry.getValue()).getDouble();
				while (CobblestoneBlockList.containsKey(Double.valueOf(chance))) {
					chance += 0.1D;
				}
				if (CobblestoneBlockList.containsKey(Double.valueOf(chance))) {
					chance += 0.1D;
				}
				CobblestoneBlockList.put(Double.valueOf(chance), new oreBlocks(name, meta));
			}
			stoneBlocks.clear();
			stoneBlocks = config.getCategory("STONEBLOCKS");
			for (Map.Entry<String, Property>oreEntry: stoneBlocks.entrySet()) {
				if (((String) oreEntry.getKey()).contains(";")) {
					namedWithMeta = ((String) oreEntry.getKey()).split(";");
					name = namedWithMeta[0];
					meta = Integer.parseInt(namedWithMeta[1]);
				} else {
					name = (String) oreEntry.getKey();
					meta = 0;
				}
				chance = ((Property) oreEntry.getValue()).getDouble();
				while (StoneBlockList.containsKey(Double.valueOf(chance))) {
					chance += 0.1D;
				}
				if (StoneBlockList.containsKey(Double.valueOf(chance))) {
					chance += 0.1D;
				}
				StoneBlockList.put(Double.valueOf(chance), new oreBlocks(name, meta));
			}
			System.out.println("[CobblestoneOreGenerator] configuration file (" + CobblestoneBlockList.size() + "|"
					+ StoneBlockList.size() + " entrys) succesfully loaded");
		//	return CobblestoneBlockList.size() + StoneBlockList.size();
		} catch (IOException ex) {
			System.out.println("[CobblestoneOreGenerator] could not create configuration file\n" + ex);
			String[] namedWithMeta;
			String name;
			int meta;
			double chance;
			Map<String, Property> stoneBlocks;
			Iterator localIterator3;
			Map.Entry<String, Property> e;
			//return CobblestoneBlockList.size() + StoneBlockList.size();
		} finally {
			config = new Configuration(cfg);

			String[] namedWithMeta = null;
			String name = null;
			int meta = 0;
			double chance = 0.0D;
			CobblestoneBlockList.clear();
			StoneBlockList.clear();
			Map<String, Property> stoneBlocks = config.getCategory("COBBLESTONEBLOCKS");
			for (Map.Entry<String, Property>oreEntry: stoneBlocks.entrySet()) {
				if (((String) oreEntry.getKey()).contains(";")) {
					namedWithMeta = ((String) oreEntry.getKey()).split(";");
					name = namedWithMeta[0];
					meta = Integer.parseInt(namedWithMeta[1]);
				} else {
					name = (String) oreEntry.getKey();
					meta = 0;
				}
				chance = ((Property) oreEntry.getValue()).getDouble();
				while (CobblestoneBlockList.containsKey(Double.valueOf(chance))) {
					chance += 0.1D;
				}
				if (CobblestoneBlockList.containsKey(Double.valueOf(chance))) {
					chance += 0.1D;
				}
				CobblestoneBlockList.put(Double.valueOf(chance), new oreBlocks(name, meta));
			}
			stoneBlocks.clear();
			stoneBlocks = config.getCategory("STONEBLOCKS");
			for (Map.Entry<String, Property>oreEntry: stoneBlocks.entrySet()) {
				if (((String) oreEntry.getKey()).contains(";")) {
					namedWithMeta = ((String) oreEntry.getKey()).split(";");
					name = namedWithMeta[0];
					meta = Integer.parseInt(namedWithMeta[1]);
				} else {
					name = (String) oreEntry.getKey();
					meta = 0;
				}
				chance = ((Property) oreEntry.getValue()).getDouble();
				while (StoneBlockList.containsKey(Double.valueOf(chance))) {
					chance += 0.1D;
				}
				if (StoneBlockList.containsKey(Double.valueOf(chance))) {
					chance += 0.1D;
				}
				StoneBlockList.put(Double.valueOf(chance), new oreBlocks(name, meta));
			}

			System.out.println("[CobblestoneOreGenerator] configuration file (" + CobblestoneBlockList.size() + "|"
					+ StoneBlockList.size() + " entrys) succesfully loaded");
		}
		return CobblestoneBlockList.size() + StoneBlockList.size();
	}
}
