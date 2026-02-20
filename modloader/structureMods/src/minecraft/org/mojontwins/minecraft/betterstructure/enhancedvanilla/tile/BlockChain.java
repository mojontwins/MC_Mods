package org.mojontwins.minecraft.betterstructure.enhancedvanilla.tile;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFence;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.mod_enhancedVanillaStructures;

public class BlockChain extends Block {

	public BlockChain(int id, int texId) {
		super(id, texId, Material.iron);
		
		float radius = 0.2F;
		this.setTickOnLoad(false);
		this.setBlockBounds(0.5F - radius, 0.0F, 0.5F - radius, 0.5F + radius, 1.0F, 0.5F + radius);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return this.canBlockStay(world, x, y, z);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		Block above = Block.blocksList[world.getBlockId(x, y + 1, z)];
		return above != null && (above.isOpaqueCube() || above.blockID == this.blockID || (above instanceof BlockFence));
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID) {
		if (!canBlockStay(world, x, y, z)) {
			//world.playAuxSFX(2001, x, y, z, blockID);
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
			world.setBlockWithNotify(x, y, z, 0);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
        return mod_enhancedVanillaStructures.renderTypeChain;
    }
}
