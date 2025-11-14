package net.minecraft.src;

import java.util.Random;

import org.mojontwins.minecraft.fungalcalamity.BlockMushroomCap;
import org.mojontwins.minecraft.fungalcalamity.BlockPodzol;
import org.mojontwins.minecraft.fungalcalamity.ItemThrowableMushroom;

public class mod_fungalCalamity extends BaseModMp {
	public static Block blockPodzol;
	public static Block blockMushroomCapRed;
	public static Block blockMushroomCapBrown;
	public static Item itemThrowableMushroom;
	
	@MLProp(name="blockPodzolID", info="Custom block ID for the Podzol block")
	public static int blockPodzolID = 140;
	@MLProp(name="blockMushroomCapRedID", info="Custom block ID for the Mushroom Red Cap block")
	public static int blockMushroomCapRedID = 100;
	@MLProp(name="blockMushroomCapBrownID", info="Custom block ID for the Mushroom Brown Cap block")
	public static int blockMushroomCapBrownID = 99;
	@MLProp(name="itemThrowableMushroomID", info="Custom item ID for the Throwable Mushroom item")
	public static int itemThrowableMushroomID = 5000;
	
	public static int texIdxPodzolTop;
	public static int texIdxPodzolBottom;
	public static int texIdxPodzolSides;
	
	public static int texMushroomStem;
	public static int texMushroomInner;
	public static int texMushroomBrown;
	public static int texMushroomRed;
	
	public mod_fungalCalamity() {
		blockPodzol = new BlockPodzol(blockPodzolID)
				.setBlockName("Podzol")
				.setTickOnLoad(true);
		ModLoader.RegisterBlock(blockPodzol);
		
		texMushroomStem = ModLoader.addOverride("/terrain.png", "/fungalcalamity/texMushroomStem.png");
		texMushroomInner = ModLoader.addOverride("/terrain.png", "/fungalcalamity/texMushroomInner.png");
		texMushroomBrown = ModLoader.addOverride("/terrain.png", "/fungalcalamity/texMushroomBrown.png");
		texMushroomRed = ModLoader.addOverride("/terrain.png", "/fungalcalamity/texMushroomRed.png");
		
		blockMushroomCapBrown = new BlockMushroomCap(blockMushroomCapBrownID, Material.wood, 0,
				texMushroomBrown, texMushroomStem, texMushroomInner)
			.setBlockName("mushroomCapBrown");
		ModLoader.RegisterBlock(blockMushroomCapBrown);
		
		blockMushroomCapRed = new BlockMushroomCap(blockMushroomCapRedID, Material.wood, 1,
				texMushroomRed, texMushroomStem, texMushroomInner)
			.setBlockName("mushroomCapRed");
		ModLoader.RegisterBlock(blockMushroomCapRed);
		
		itemThrowableMushroom = new ItemThrowableMushroom(itemThrowableMushroomID)
				.setIconIndex(ModLoader.addOverride("/gui/items.png", "/fungalcalamity/iconThrowableMushroom.png"))
				.setMaxStackSize(16)
				.setItemName("throwableItem");
		
	}
	
	@Override
	public void GenerateSurface(World world, Random rand, int x0, int z0) {
		int chestX = world.worldInfo.getSpawnX();
		int chestZ = world.worldInfo.getSpawnZ();
		
		if(x0 >> 4 == chestX >> 4 && z0 >> 4 == chestZ >> 4) {
			// Add a chest
			int chestY = world.getHeightValue(chestX, chestZ);
			System.out.println ("Generating chest @ " + chestX + " " + chestY + " " + chestZ);
			
			// Place a chest
			world.setBlockWithNotify(chestX, chestY, chestZ, Block.chest.blockID);
			
			// Placing a chest automaticly creates a chest tile entity.
			// We get a reference to it from world:
			TileEntityChest theChest = (TileEntityChest)world.getBlockTileEntity(chestX, chestY, chestZ);
			
			// And now we access its inventory and add our stuff.
			// We check for null 'cause shit happens.
			if (theChest != null) {
				
				int slot = 0;
				theChest.setInventorySlotContents(slot ++, new ItemStack(blockPodzol, 64));
				
				// Note that metadata will only make blocks look different in the chest. You can't place
				// the special metadata as we haven't defined a special ItemBlock as it won't be needed 
				// in the final version.
				for(int i = 0; i < 8; i ++) {
					theChest.setInventorySlotContents(slot ++, new ItemStack(blockMushroomCapRed, 16, rand.nextInt(16)));
					theChest.setInventorySlotContents(slot ++, new ItemStack(blockMushroomCapBrown, 16, rand.nextInt(16)));
				}
				theChest.setInventorySlotContents(0, new ItemStack(itemThrowableMushroom, 64));
			}
		}
	}

	@Override
	public String Version() {
		return "Fungal Calamity v1.0";
	}

}
