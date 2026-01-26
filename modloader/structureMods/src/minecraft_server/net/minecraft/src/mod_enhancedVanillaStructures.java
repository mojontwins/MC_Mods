package net.minecraft.src;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureGeneratorMineshaft;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureGeneratorMineshaftMesa;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureGeneratorStronghold;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.tile.BlockChain;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.tile.BlockFenceIron;
import org.mojontwins.minecraft.betterstructureapi.ChunkProviderGenerateProxy;

public class mod_enhancedVanillaStructures extends BaseMod {
	public static Block blockChain;
	public static Block blockStoneBricks;
	public static Block blockFenceIron;
	
	@MLProp	public static boolean generateMineshafts = true;
	@MLProp	public static boolean generateStrongholds = true;
	@MLProp	public static boolean generateMountainMineshafts = true;
	@MLProp	public static int blockChainID = 160;
	@MLProp	public static int blockFenceIronID = 161;
	@MLProp	public static int blockStoneBricksID = 162;
	
	public static int renderTypeChain;
	public static int renderTypeFenceIron;
	
	public mod_enhancedVanillaStructures() {
		renderTypeChain = ModLoader.getUniqueBlockModelID(this, false);
		renderTypeFenceIron = ModLoader.getUniqueBlockModelID(this, false);
		
		blockChain = new BlockChain(blockChainID, ModLoader.addOverride("/terrain.png", "/enhancedVanillaStructures/blockchain.png"))
				.setHardness(1.0F)
				.setResistance(5.0F)
				.setStepSound(Block.soundMetalFootstep)
				.setBlockName("chain");
		ModLoader.RegisterBlock(blockChain);
		
		blockFenceIron = new BlockFenceIron(blockFenceIronID, ModLoader.addOverride("/terrain.png", "/enhancedVanillaStructures/blockfenceiron.png"))
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundMetalFootstep)
				.setBlockName("fenceIron");
		ModLoader.RegisterBlock(blockFenceIron);
		
		ModLoader.AddRecipe(new ItemStack(blockFenceIron, 16), new Object[]{"###", "###", '#', Item.ingotIron});
		
		blockStoneBricks = new Block(blockStoneBricksID, ModLoader.addOverride("/terrain.png", "/enhancedVanillaStructures/blockstonebricks.png"), Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setBlockName("stoneBricks");
		ModLoader.RegisterBlock(blockStoneBricks);
		
		ModLoader.AddRecipe(new ItemStack(blockStoneBricks, 4, 0), new Object[]{"##", "##", '#', Block.stone});
	}
	
	@Override
	public String Version() {
		return "v0.1 b1.7.3";
	}

	@Override
	public void ModsLoaded() {
		if(
			ModLoader.isModLoaded("mod_BetterStructureApi") ||
			ModLoader.isModLoaded("net.minecraft.src.mod_BetterStructureApi")
		) {
			if(generateMineshafts) {
				ChunkProviderGenerateProxy.registerStructureGenerator(new StructureGeneratorMineshaft());
			}
			if(generateStrongholds) {
				ChunkProviderGenerateProxy.registerStructureGenerator(new StructureGeneratorStronghold());
			}
			if(generateMountainMineshafts) {
				ChunkProviderGenerateProxy.registerStructureGenerator(new StructureGeneratorMineshaftMesa());
			}
		} else {
			throw new MinecraftException("mod_enhancedVanillaStructures needs mod_BetterStructureApi!");
		}
	}
	
	private boolean isAirBlock(IBlockAccess world, int x, int y, int z) {
		return world.getBlockId(x, y, z) == 0;
	}
}
