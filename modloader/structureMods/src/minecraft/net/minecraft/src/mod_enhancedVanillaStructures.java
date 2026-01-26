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
		ModLoader.AddName(blockChain, "Iron Chain");
		
		blockFenceIron = new BlockFenceIron(blockFenceIronID, ModLoader.addOverride("/terrain.png", "/enhancedVanillaStructures/blockfenceiron.png"))
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundMetalFootstep)
				.setBlockName("fenceIron");
		ModLoader.RegisterBlock(blockFenceIron);
		ModLoader.AddName(blockFenceIron, "Iron Fence");
		
		ModLoader.AddRecipe(new ItemStack(blockFenceIron, 16), new Object[]{"###", "###", '#', Item.ingotIron});
		
		blockStoneBricks = new Block(blockStoneBricksID, ModLoader.addOverride("/terrain.png", "/enhancedVanillaStructures/blockstonebricks.png"), Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setBlockName("stoneBricks");
		ModLoader.RegisterBlock(blockStoneBricks);
		ModLoader.AddName(blockStoneBricks, "Stone Bricks");
		
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
	
	@Override
	public boolean RenderWorldBlock(RenderBlocks rb, IBlockAccess world, int x, int y, int z, Block block, int renderType) {
		Tessellator tes = Tessellator.instance;
		
		/* Custom renderers for chains and iron fences */
		
		if(renderType == renderTypeChain) {
			
			float brightness = block.getBlockBrightness(world, x, y, z);
			tes.setColorOpaque_F(brightness, brightness, brightness);
			
			int texId = block.getBlockTextureFromSideAndMetadata(0, world.getBlockMetadata(x, y, z));

			int u = (texId & 15) << 4;
			int v = texId & 240;
			double u1 = (double)((float)u / 256.0F);
			double u2 = (double)(((float)u + 15.99F) / 256.0F);
			double v1 = (double)((float)v / 256.0F);
			double v2 = (double)(((float)v + 15.99F) / 256.0F);
			double x1 = (double)x + 0.5D - (double)0.45F;
			double x2 = (double)x + 0.5D + (double)0.45F;
			double z1 = (double)z + 0.5D - (double)0.45F;
			double z2 = (double)z + 0.5D + (double)0.45F;
			double yy = (double)y;
			
			// Square 1
			
			tes.addVertexWithUV(x1, yy + 1.0D, z1,  u1, v2);
			tes.addVertexWithUV(x1, yy + 0.0D, z1,  u1, v1);
			tes.addVertexWithUV(x2, yy + 0.0D, z2,  u2, v1);
			tes.addVertexWithUV(x2, yy + 1.0D, z2,  u2, v2)
			;
			tes.addVertexWithUV(x2, yy + 1.0D, z2,  u1, v2);
			tes.addVertexWithUV(x2, yy + 0.0D, z2,  u1, v1);
			tes.addVertexWithUV(x1, yy + 0.0D, z1,  u2, v1);
			tes.addVertexWithUV(x1, yy + 1.0D, z1,  u2, v2);
			
			// Square 2 [upside down]
			
			tes.addVertexWithUV(x1, yy + 1.0D, z2,  u1, v1);
			tes.addVertexWithUV(x1, yy + 0.0D, z2,  u1, v2);
			tes.addVertexWithUV(x2, yy + 0.0D, z1,  u2, v2);
			tes.addVertexWithUV(x2, yy + 1.0D, z1,  u2, v1);
			
			tes.addVertexWithUV(x2, yy + 1.0D, z1,  u1, v1);
			tes.addVertexWithUV(x2, yy + 0.0D, z1,  u1, v2);
			tes.addVertexWithUV(x1, yy + 0.0D, z2,  u2, v2);
			tes.addVertexWithUV(x1, yy + 1.0D, z2,  u2, v1);
		
			return true;
		} else if(renderType == renderTypeFenceIron) {
			float brightness = block.getBlockBrightness(world, x, y, z);
			tes.setColorOpaque_F(brightness, brightness, brightness);

			int textureId;		
			int meta = world.getBlockMetadata(x, y, z);
			textureId = block.getBlockTextureFromSideAndMetadata(0, meta);
						
			int u = (textureId & 15) << 4;
			int v = textureId & 0xff0;
			double faceU1 = (double)((float)u / 256.0);
			double faceU2 = (double)(((float)u + 7.99F) / 256.0);
			double faceU3 = (double)(((float)u + 15.99F) / 256.0);
			double faceV1 = (double)((float)v / 256.0);
			double faceV2 = (double)(((float)v + 15.99F) / 256.0);

			double sideU1 = (double)((float)(u + 7) / 256.0);
			double sideU2 = (double)(((float)u + 8.99F) / 256.0);
			double sideV3 = (double)((float)v / 256.0);
			double sideV1 = (double)((float)(v + 8) / 256.0);
			double sideV2 = (double)(((float)v + 15.99F) / 256.0);
			
			double x0 = (double)x;
			double xc = (double)x + 0.5D;
			double x1 = (double)(x + 1);
			double z0 = (double)z;
			double zc = (double)z + 0.5D;
			double z1 = (double)(z + 1);
			double xc0 = (double)x + 0.5D - 0.0625D;
			double xc1 = (double)x + 0.5D + 0.0625D;
			double zc0 = (double)z + 0.5D - 0.0625D;
			double zc1 = (double)z + 0.5D + 0.0625D;
			
			BlockFenceIron bfi = (BlockFenceIron)block;
			boolean connectN = bfi.canThisPaneConnectToThisBlockID(world.getBlockId(x, y, z - 1));
			boolean connectS = bfi.canThisPaneConnectToThisBlockID(world.getBlockId(x, y, z + 1));
			boolean connectW = bfi.canThisPaneConnectToThisBlockID(world.getBlockId(x - 1, y, z));
			boolean connectE = bfi.canThisPaneConnectToThisBlockID(world.getBlockId(x + 1, y, z));
			boolean connectUP = block.shouldSideBeRendered(world, x, y + 1, z, 1);
			boolean connectDW = block.shouldSideBeRendered(world, x, y - 1, z, 0);
			
			if((!connectW || !connectE) && (connectW || connectE || connectN || connectS)) {
				if(connectW && !connectE) {
					tes.addVertexWithUV(x0, (double)(y + 1), zc, faceU1, faceV1);
					tes.addVertexWithUV(x0, (double)(y + 0), zc, faceU1, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 0), zc, faceU2, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 1), zc, faceU2, faceV1);
					tes.addVertexWithUV(xc, (double)(y + 1), zc, faceU1, faceV1);
					tes.addVertexWithUV(xc, (double)(y + 0), zc, faceU1, faceV2);
					tes.addVertexWithUV(x0, (double)(y + 0), zc, faceU2, faceV2);
					tes.addVertexWithUV(x0, (double)(y + 1), zc, faceU2, faceV1);
					if(!connectS && !connectN) {
						tes.addVertexWithUV(xc, (double)(y + 1), zc1, sideU1, sideV3);
						tes.addVertexWithUV(xc, (double)(y + 0), zc1, sideU1, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 0), zc0, sideU2, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 1), zc0, sideU2, sideV3);
						tes.addVertexWithUV(xc, (double)(y + 1), zc0, sideU1, sideV3);
						tes.addVertexWithUV(xc, (double)(y + 0), zc0, sideU1, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 0), zc1, sideU2, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 1), zc1, sideU2, sideV3);
					}
	
					if(connectUP || y < 127 && this.isAirBlock(world, x - 1, y + 1, z)) {
						tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc1, sideU2, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc0, sideU1, sideV2);
						tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc1, sideU2, sideV2);
						tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc0, sideU1, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc0, sideU1, sideV1);
					}
	
					if(connectDW || y > 1 && this.isAirBlock(world, x - 1, y - 1, z)) {
						tes.addVertexWithUV(x0, (double)y - 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc1, sideU2, sideV2);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc0, sideU1, sideV2);
						tes.addVertexWithUV(x0, (double)y - 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(x0, (double)y - 0.01D, zc1, sideU2, sideV2);
						tes.addVertexWithUV(x0, (double)y - 0.01D, zc0, sideU1, sideV2);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc0, sideU1, sideV1);
					}
				} else if(!connectW && connectE) {
					tes.addVertexWithUV(xc, (double)(y + 1), zc, faceU2, faceV1);
					tes.addVertexWithUV(xc, (double)(y + 0), zc, faceU2, faceV2);
					tes.addVertexWithUV(x1, (double)(y + 0), zc, faceU3, faceV2);
					tes.addVertexWithUV(x1, (double)(y + 1), zc, faceU3, faceV1);
					tes.addVertexWithUV(x1, (double)(y + 1), zc, faceU2, faceV1);
					tes.addVertexWithUV(x1, (double)(y + 0), zc, faceU2, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 0), zc, faceU3, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 1), zc, faceU3, faceV1);
					if(!connectS && !connectN) {
						tes.addVertexWithUV(xc, (double)(y + 1), zc0, sideU1, sideV3);
						tes.addVertexWithUV(xc, (double)(y + 0), zc0, sideU1, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 0), zc1, sideU2, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 1), zc1, sideU2, sideV3);
						tes.addVertexWithUV(xc, (double)(y + 1), zc1, sideU1, sideV3);
						tes.addVertexWithUV(xc, (double)(y + 0), zc1, sideU1, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 0), zc0, sideU2, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 1), zc0, sideU2, sideV3);
					}
	
					if(connectUP || y < 127 && this.isAirBlock(world, x + 1, y + 1, z)) {
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc1, sideU2, sideV3);
						tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc0, sideU1, sideV3);
						tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc1, sideU2, sideV3);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc0, sideU1, sideV3);
					}
	
					if(connectDW || y > 1 && this.isAirBlock(world, x + 1, y - 1, z)) {
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc1, sideU2, sideV3);
						tes.addVertexWithUV(x1, (double)y - 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(x1, (double)y - 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc0, sideU1, sideV3);
						tes.addVertexWithUV(x1, (double)y - 0.01D, zc1, sideU2, sideV3);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(x1, (double)y - 0.01D, zc0, sideU1, sideV3);
					}
				}
			} else {
				tes.addVertexWithUV(x0, (double)(y + 1), zc, faceU1, faceV1);
				tes.addVertexWithUV(x0, (double)(y + 0), zc, faceU1, faceV2);
				tes.addVertexWithUV(x1, (double)(y + 0), zc, faceU3, faceV2);
				tes.addVertexWithUV(x1, (double)(y + 1), zc, faceU3, faceV1);
				tes.addVertexWithUV(x1, (double)(y + 1), zc, faceU1, faceV1);
				tes.addVertexWithUV(x1, (double)(y + 0), zc, faceU1, faceV2);
				tes.addVertexWithUV(x0, (double)(y + 0), zc, faceU3, faceV2);
				tes.addVertexWithUV(x0, (double)(y + 1), zc, faceU3, faceV1);
				if(connectUP) {
					tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc1, sideU2, sideV2);
					tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc1, sideU2, sideV3);
					tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc0, sideU1, sideV3);
					tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc0, sideU1, sideV2);
					tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc1, sideU2, sideV2);
					tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc1, sideU2, sideV3);
					tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc0, sideU1, sideV3);
					tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc0, sideU1, sideV2);
				} else {
					if(y < 127 && this.isAirBlock(world, x - 1, y + 1, z)) {
						tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc1, sideU2, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc0, sideU1, sideV2);
						tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc1, sideU2, sideV2);
						tes.addVertexWithUV(x0, (double)(y + 1) + 0.01D, zc0, sideU1, sideV2);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc0, sideU1, sideV1);
					}
	
					if(y < 127 && this.isAirBlock(world, x + 1, y + 1, z)) {
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc1, sideU2, sideV3);
						tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc0, sideU1, sideV3);
						tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc1, sideU2, sideV3);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(xc, (double)(y + 1) + 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(x1, (double)(y + 1) + 0.01D, zc0, sideU1, sideV3);
					}
				}
	
				if(connectDW) {
					tes.addVertexWithUV(x0, (double)y - 0.01D, zc1, sideU2, sideV2);
					tes.addVertexWithUV(x1, (double)y - 0.01D, zc1, sideU2, sideV3);
					tes.addVertexWithUV(x1, (double)y - 0.01D, zc0, sideU1, sideV3);
					tes.addVertexWithUV(x0, (double)y - 0.01D, zc0, sideU1, sideV2);
					tes.addVertexWithUV(x1, (double)y - 0.01D, zc1, sideU2, sideV2);
					tes.addVertexWithUV(x0, (double)y - 0.01D, zc1, sideU2, sideV3);
					tes.addVertexWithUV(x0, (double)y - 0.01D, zc0, sideU1, sideV3);
					tes.addVertexWithUV(x1, (double)y - 0.01D, zc0, sideU1, sideV2);
				} else {
					if(y > 1 && this.isAirBlock(world, x - 1, y - 1, z)) {
						tes.addVertexWithUV(x0, (double)y - 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc1, sideU2, sideV2);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc0, sideU1, sideV2);
						tes.addVertexWithUV(x0, (double)y - 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(x0, (double)y - 0.01D, zc1, sideU2, sideV2);
						tes.addVertexWithUV(x0, (double)y - 0.01D, zc0, sideU1, sideV2);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc0, sideU1, sideV1);
					}
	
					if(y > 1 && this.isAirBlock(world, x + 1, y - 1, z)) {
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc1, sideU2, sideV3);
						tes.addVertexWithUV(x1, (double)y - 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(x1, (double)y - 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc0, sideU1, sideV3);
						tes.addVertexWithUV(x1, (double)y - 0.01D, zc1, sideU2, sideV3);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc1, sideU2, sideV1);
						tes.addVertexWithUV(xc, (double)y - 0.01D, zc0, sideU1, sideV1);
						tes.addVertexWithUV(x1, (double)y - 0.01D, zc0, sideU1, sideV3);
					}
				}
			}
	
			if((!connectN || !connectS) && (connectW || connectE || connectN || connectS)) {
				if(connectN && !connectS) {
					tes.addVertexWithUV(xc, (double)(y + 1), z0, faceU1, faceV1);
					tes.addVertexWithUV(xc, (double)(y + 0), z0, faceU1, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 0), zc, faceU2, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 1), zc, faceU2, faceV1);
					tes.addVertexWithUV(xc, (double)(y + 1), zc, faceU1, faceV1);
					tes.addVertexWithUV(xc, (double)(y + 0), zc, faceU1, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 0), z0, faceU2, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 1), z0, faceU2, faceV1);
					if(!connectE && !connectW) {
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU1, sideV3);
						tes.addVertexWithUV(xc0, (double)(y + 0), zc, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 0), zc, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU2, sideV3);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU1, sideV3);
						tes.addVertexWithUV(xc1, (double)(y + 0), zc, sideU1, sideV2);
						tes.addVertexWithUV(xc0, (double)(y + 0), zc, sideU2, sideV2);
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU2, sideV3);
					}
	
					if(connectUP || y < 127 && this.isAirBlock(world, x, y + 1, z - 1)) {
						tes.addVertexWithUV(xc0, (double)(y + 1), z0, sideU2, sideV3);
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU2, sideV1);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU1, sideV1);
						tes.addVertexWithUV(xc1, (double)(y + 1), z0, sideU1, sideV3);
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU2, sideV3);
						tes.addVertexWithUV(xc0, (double)(y + 1), z0, sideU2, sideV1);
						tes.addVertexWithUV(xc1, (double)(y + 1), z0, sideU1, sideV1);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU1, sideV3);
					}
	
					if(connectDW || y > 1 && this.isAirBlock(world, x, y - 1, z - 1)) {
						tes.addVertexWithUV(xc0, (double)y, z0, sideU2, sideV3);
						tes.addVertexWithUV(xc0, (double)y, zc, sideU2, sideV1);
						tes.addVertexWithUV(xc1, (double)y, zc, sideU1, sideV1);
						tes.addVertexWithUV(xc1, (double)y, z0, sideU1, sideV3);
						tes.addVertexWithUV(xc0, (double)y, zc, sideU2, sideV3);
						tes.addVertexWithUV(xc0, (double)y, z0, sideU2, sideV1);
						tes.addVertexWithUV(xc1, (double)y, z0, sideU1, sideV1);
						tes.addVertexWithUV(xc1, (double)y, zc, sideU1, sideV3);
					}
				} else if(!connectN && connectS) {
					tes.addVertexWithUV(xc, (double)(y + 1), zc, faceU2, faceV1);
					tes.addVertexWithUV(xc, (double)(y + 0), zc, faceU2, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 0), z1, faceU3, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 1), z1, faceU3, faceV1);
					tes.addVertexWithUV(xc, (double)(y + 1), z1, faceU2, faceV1);
					tes.addVertexWithUV(xc, (double)(y + 0), z1, faceU2, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 0), zc, faceU3, faceV2);
					tes.addVertexWithUV(xc, (double)(y + 1), zc, faceU3, faceV1);
					if(!connectE && !connectW) {
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU1, sideV3);
						tes.addVertexWithUV(xc1, (double)(y + 0), zc, sideU1, sideV2);
						tes.addVertexWithUV(xc0, (double)(y + 0), zc, sideU2, sideV2);
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU2, sideV3);
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU1, sideV3);
						tes.addVertexWithUV(xc0, (double)(y + 0), zc, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 0), zc, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU2, sideV3);
					}
	
					if(connectUP || y < 127 && this.isAirBlock(world, x, y + 1, z + 1)) {
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU1, sideV1);
						tes.addVertexWithUV(xc0, (double)(y + 1), z1, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), z1, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU2, sideV1);
						tes.addVertexWithUV(xc0, (double)(y + 1), z1, sideU1, sideV1);
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), z1, sideU2, sideV1);
					}
	
					if(connectDW || y > 1 && this.isAirBlock(world, x, y - 1, z + 1)) {
						tes.addVertexWithUV(xc0, (double)y, zc, sideU1, sideV1);
						tes.addVertexWithUV(xc0, (double)y, z1, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)y, z1, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)y, zc, sideU2, sideV1);
						tes.addVertexWithUV(xc0, (double)y, z1, sideU1, sideV1);
						tes.addVertexWithUV(xc0, (double)y, zc, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)y, zc, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)y, z1, sideU2, sideV1);
					}
				}
			} else {
				tes.addVertexWithUV(xc, (double)(y + 1), z1, faceU1, faceV1);
				tes.addVertexWithUV(xc, (double)(y + 0), z1, faceU1, faceV2);
				tes.addVertexWithUV(xc, (double)(y + 0), z0, faceU3, faceV2);
				tes.addVertexWithUV(xc, (double)(y + 1), z0, faceU3, faceV1);
				tes.addVertexWithUV(xc, (double)(y + 1), z0, faceU1, faceV1);
				tes.addVertexWithUV(xc, (double)(y + 0), z0, faceU1, faceV2);
				tes.addVertexWithUV(xc, (double)(y + 0), z1, faceU3, faceV2);
				tes.addVertexWithUV(xc, (double)(y + 1), z1, faceU3, faceV1);
				if(connectUP) {
					tes.addVertexWithUV(xc1, (double)(y + 1), z1, sideU2, sideV2);
					tes.addVertexWithUV(xc1, (double)(y + 1), z0, sideU2, sideV3);
					tes.addVertexWithUV(xc0, (double)(y + 1), z0, sideU1, sideV3);
					tes.addVertexWithUV(xc0, (double)(y + 1), z1, sideU1, sideV2);
					tes.addVertexWithUV(xc1, (double)(y + 1), z0, sideU2, sideV2);
					tes.addVertexWithUV(xc1, (double)(y + 1), z1, sideU2, sideV3);
					tes.addVertexWithUV(xc0, (double)(y + 1), z1, sideU1, sideV3);
					tes.addVertexWithUV(xc0, (double)(y + 1), z0, sideU1, sideV2);
				} else {
					if(y < 127 && this.isAirBlock(world, x, y + 1, z - 1)) {
						tes.addVertexWithUV(xc0, (double)(y + 1), z0, sideU2, sideV3);
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU2, sideV1);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU1, sideV1);
						tes.addVertexWithUV(xc1, (double)(y + 1), z0, sideU1, sideV3);
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU2, sideV3);
						tes.addVertexWithUV(xc0, (double)(y + 1), z0, sideU2, sideV1);
						tes.addVertexWithUV(xc1, (double)(y + 1), z0, sideU1, sideV1);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU1, sideV3);
					}
	
					if(y < 127 && this.isAirBlock(world, x, y + 1, z + 1)) {
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU1, sideV1);
						tes.addVertexWithUV(xc0, (double)(y + 1), z1, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), z1, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU2, sideV1);
						tes.addVertexWithUV(xc0, (double)(y + 1), z1, sideU1, sideV1);
						tes.addVertexWithUV(xc0, (double)(y + 1), zc, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), zc, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)(y + 1), z1, sideU2, sideV1);
					}
				}
	
				if(connectDW) {
					tes.addVertexWithUV(xc1, (double)y, z1, sideU2, sideV2);
					tes.addVertexWithUV(xc1, (double)y, z0, sideU2, sideV3);
					tes.addVertexWithUV(xc0, (double)y, z0, sideU1, sideV3);
					tes.addVertexWithUV(xc0, (double)y, z1, sideU1, sideV2);
					tes.addVertexWithUV(xc1, (double)y, z0, sideU2, sideV2);
					tes.addVertexWithUV(xc1, (double)y, z1, sideU2, sideV3);
					tes.addVertexWithUV(xc0, (double)y, z1, sideU1, sideV3);
					tes.addVertexWithUV(xc0, (double)y, z0, sideU1, sideV2);
				} else {
					if(y > 1 && this.isAirBlock(world, x, y - 1, z - 1)) {
						tes.addVertexWithUV(xc0, (double)y, z0, sideU2, sideV3);
						tes.addVertexWithUV(xc0, (double)y, zc, sideU2, sideV1);
						tes.addVertexWithUV(xc1, (double)y, zc, sideU1, sideV1);
						tes.addVertexWithUV(xc1, (double)y, z0, sideU1, sideV3);
						tes.addVertexWithUV(xc0, (double)y, zc, sideU2, sideV3);
						tes.addVertexWithUV(xc0, (double)y, z0, sideU2, sideV1);
						tes.addVertexWithUV(xc1, (double)y, z0, sideU1, sideV1);
						tes.addVertexWithUV(xc1, (double)y, zc, sideU1, sideV3);
					}
	
					if(y > 1 && this.isAirBlock(world, x, y - 1, z + 1)) {
						tes.addVertexWithUV(xc0, (double)y, zc, sideU1, sideV1);
						tes.addVertexWithUV(xc0, (double)y, z1, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)y, z1, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)y, zc, sideU2, sideV1);
						tes.addVertexWithUV(xc0, (double)y, z1, sideU1, sideV1);
						tes.addVertexWithUV(xc0, (double)y, zc, sideU1, sideV2);
						tes.addVertexWithUV(xc1, (double)y, zc, sideU2, sideV2);
						tes.addVertexWithUV(xc1, (double)y, z1, sideU2, sideV1);
					}
				}
			}

			return true;
		}
		
		return false;
	}

	private boolean isAirBlock(IBlockAccess world, int x, int y, int z) {
		return world.getBlockId(x, y, z) == 0;
	}
}
