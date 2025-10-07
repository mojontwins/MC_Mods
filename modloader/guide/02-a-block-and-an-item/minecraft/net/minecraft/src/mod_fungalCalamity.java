package net.minecraft.src;

import org.mojontwins.minecraft.fungalcalamity.BlockMushroomCap;
import org.mojontwins.minecraft.fungalcalamity.BlockPodzol;

public class mod_fungalCalamity extends BaseModMp {
	public static Block blockPodzol;
	public static Block blockMushroomCapRed;
	public static Block blockMushroomCapBrown;
	
	@MLProp(name="blockPodzolID", info="Custom block ID for the Podzol block")
	public static int blockPodzolID = 140;
	@MLProp(name="blockMushroomCapRedID", info="Custom block ID for the Mushroom Red Cap block")
	public static int blockMushroomCapRedID = 100;
	@MLProp(name="blockMushroomCapBrownID", info="Custom block ID for the Mushroom Brown Cap block")
	public static int blockMushroomCapBrownID = 99;
	
	public static int texIdxPodzolTop;
	public static int texIdxPodzolBottom;
	public static int texIdxPodzolSides;
	
	public static int texMushroomStem;
	public static int texMushroomInner;
	public static int texMushroomBrown;
	public static int texMushroomRed;
	
	public mod_fungalCalamity() {
		blockPodzol = new BlockPodzol(blockPodzolID).
				setBlockName("Podzol").
				setTickOnLoad(true);
		ModLoader.RegisterBlock(blockPodzol);
		ModLoader.AddName(blockPodzol, "Podzol");
		
		texMushroomStem = ModLoader.addOverride("/terrain.png", "/com/mojontwins/minecraft/fungalcalamity/texMushroomStem.png");
		texMushroomInner = ModLoader.addOverride("/terrain.png", "/com/mojontwins/minecraft/fungalcalamity/texMushroomInner.png");
		texMushroomBrown = ModLoader.addOverride("/terrain.png", "/com/mojontwins/minecraft/fungalcalamity/texMushroomBrown.png");
		texMushroomRed = ModLoader.addOverride("/terrain.png", "/com/mojontwins/minecraft/fungalcalamity/texMushroomRed.png");
		
		blockMushroomCapBrown = new BlockMushroomCap(99, Material.wood, 0,
				texMushroomBrown, texMushroomStem, texMushroomInner);
		ModLoader.RegisterBlock(blockMushroomCapBrown);
		ModLoader.AddName(blockMushroomCapBrown, "Mushroom Cap Brown");
		
		blockMushroomCapRed = new BlockMushroomCap(99, Material.wood, 1,
				texMushroomRed, texMushroomStem, texMushroomInner);
		ModLoader.RegisterBlock(blockMushroomCapRed);
		ModLoader.AddName(blockMushroomCapBrown, "Mushroom Cap Red");
		
	}

	@Override
	public String Version() {
		return "Fungal Calamity v1.0";
	}

}
