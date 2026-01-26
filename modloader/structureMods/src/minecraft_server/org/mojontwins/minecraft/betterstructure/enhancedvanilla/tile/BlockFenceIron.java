package org.mojontwins.minecraft.betterstructure.enhancedvanilla.tile;

import java.util.ArrayList;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.mod_enhancedVanillaStructures;

public class BlockFenceIron extends Block {

	public BlockFenceIron(int id, int texId) {
		super(id, texId, Material.iron);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		int id = world.getBlockId(x, y, z);
		return id == this.blockID ? false : super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	public void getCollidingBoundingBoxes(World world1, int x, int y, int z, AxisAlignedBB bb, ArrayList bbList) {
		boolean z7 = this.canThisPaneConnectToThisBlockID(world1.getBlockId(x, y, z - 1));
		boolean z8 = this.canThisPaneConnectToThisBlockID(world1.getBlockId(x, y, z + 1));
		boolean z9 = this.canThisPaneConnectToThisBlockID(world1.getBlockId(x - 1, y, z));
		boolean z10 = this.canThisPaneConnectToThisBlockID(world1.getBlockId(x + 1, y, z));
		if(z9 && z10 || !z9 && !z10 && !z7 && !z8) {
			this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.getCollidingBoundingBoxes(world1, x, y, z, bb, bbList);
		} else if(z9 && !z10) {
			this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
			super.getCollidingBoundingBoxes(world1, x, y, z, bb, bbList);
		} else if(!z9 && z10) {
			this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.getCollidingBoundingBoxes(world1, x, y, z, bb, bbList);
		}

		if((!z7 || !z8) && (z9 || z10 || z7 || z8)) {
			if(z7 && !z8) {
				this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
				super.getCollidingBoundingBoxes(world1, x, y, z, bb, bbList);
			} else if(!z7 && z8) {
				this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
				super.getCollidingBoundingBoxes(world1, x, y, z, bb, bbList);
			}
		} else {
			this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(world1, x, y, z, bb, bbList);
		}

	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		float f5 = 0.4375F;
		float f6 = 0.5625F;
		float f7 = 0.4375F;
		float f8 = 0.5625F;
		boolean z9 = this.canThisPaneConnectToThisBlockID(world.getBlockId(x, y, z - 1));
		boolean z10 = this.canThisPaneConnectToThisBlockID(world.getBlockId(x, y, z + 1));
		boolean z11 = this.canThisPaneConnectToThisBlockID(world.getBlockId(x - 1, y, z));
		boolean z12 = this.canThisPaneConnectToThisBlockID(world.getBlockId(x + 1, y, z));
		if(z11 && z12 || !z11 && !z12 && !z9 && !z10) {
			f5 = 0.0F;
			f6 = 1.0F;
		} else if(z11 && !z12) {
			f5 = 0.0F;
		} else if(!z11 && z12) {
			f6 = 1.0F;
		}

		if((!z9 || !z10) && (z11 || z12 || z9 || z10)) {
			if(z9 && !z10) {
				f7 = 0.0F;
			} else if(!z9 && z10) {
				f8 = 1.0F;
			}
		} else {
			f7 = 0.0F;
			f8 = 1.0F;
		}

		this.setBlockBounds(f5, 0.0F, f7, f6, 1.0F, f8);
	}
	
	public final boolean canThisPaneConnectToThisBlockID(int i1) {
		return Block.opaqueCubeLookup[i1] || i1 == this.blockID || i1 == Block.glass.blockID;
	}
}
