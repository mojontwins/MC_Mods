package org.mojontwins.minecraft.fungalcalamity;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.mod_fungalCalamity;

public class BlockPodzol extends Block {

	public BlockPodzol(int blockID) {
		super(blockID, Material.ground);
	}

	@Override
	public int getBlockTextureFromSide(int side) {
        switch(side) {
        case 0: return mod_fungalCalamity.texIdxPodzolBottom;
        case 1: return mod_fungalCalamity.texIdxPodzolTop;
        default: return mod_fungalCalamity.texIdxPodzolSides;
        }
    }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		// Attempt to expand if the world isn't remote
		if(!world.multiplayerWorld) {
			if(rand.nextInt(4) == 0) {
				int xx = x + rand.nextInt(3) - 1;
				int yy = y + rand.nextInt(5) - 3;
				int zz = z + rand.nextInt(3) - 1;
				
				int blockID = world.getBlockId(xx, yy, zz);
				
				if(
					(blockID == Block.dirt.blockID || blockID == Block.grass.blockID) && 
					Block.lightOpacity[world.getBlockId(xx, yy + 1, zz)] < 2
				) {
					
				}
			}
		}
	}
}
