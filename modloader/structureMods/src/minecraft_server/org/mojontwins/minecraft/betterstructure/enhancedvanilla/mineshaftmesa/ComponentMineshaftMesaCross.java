package org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaftmesa;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.mod_enhancedVanillaStructures;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentMineshaftMesaCross extends StructureComponent {
	private final int corridorDirection;
	private final boolean isMultipleFloors;

	public ComponentMineshaftMesaCross(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.corridorDirection = i4;
		this.boundingBox = structureBoundingBox3;
		this.isMultipleFloors = structureBoundingBox3.getYSize() > 3;
	}

	public static StructureBoundingBox findValidPlacement(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5) {
		StructureBoundingBox structureBoundingBox6 = new StructureBoundingBox(i2, i3, i4, i2, i3 + 3, i4);
		if(random1.nextInt(4) == 0) {
			structureBoundingBox6.maxY += 4;
		}

		switch(i5) {
		case 0:
			structureBoundingBox6.minX = i2 - 1;
			structureBoundingBox6.maxX = i2 + 3;
			structureBoundingBox6.maxZ = i4 + 4;
			break;
		case 1:
			structureBoundingBox6.minX = i2 - 4;
			structureBoundingBox6.minZ = i4 - 1;
			structureBoundingBox6.maxZ = i4 + 3;
			break;
		case 2:
			structureBoundingBox6.minX = i2 - 1;
			structureBoundingBox6.maxX = i2 + 3;
			structureBoundingBox6.minZ = i4 - 4;
			break;
		case 3:
			structureBoundingBox6.maxX = i2 + 4;
			structureBoundingBox6.minZ = i4 - 1;
			structureBoundingBox6.maxZ = i4 + 3;
		}

		return StructureComponent.findIntersecting(list0, structureBoundingBox6) != null ? null : structureBoundingBox6;
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		int i4 = this.getComponentType();
		switch(this.corridorDirection) {
		case 0:
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, i4);
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, i4);
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, i4);
			break;
		case 1:
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, i4);
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, i4);
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, i4);
			break;
		case 2:
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, i4);
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, i4);
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, i4);
			break;
		case 3:
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, i4);
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, i4);
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, i4);
		}

		if(this.isMultipleFloors) {
			if(random3.nextBoolean()) {
				StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, 2, i4);
			}

			if(random3.nextBoolean()) {
				StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, 1, i4);
			}

			if(random3.nextBoolean()) {
				StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, 3, i4);
			}

			if(random3.nextBoolean()) {
				StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, 0, i4);
			}
		}

	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if (this.getFractionSolidInBoundingBox(world1, structureBoundingBox3) < 0.4F || this.getFractionExposed(world1, structureBoundingBox3) > 0.5F) {
			return false;
		} else {
			if(this.isMultipleFloors) {
				this.fillWithBlocks(world1, structureBoundingBox3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, 0, 0, false);
				this.fillWithBlocks(world1, structureBoundingBox3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, 0, 0, false);
				this.fillWithBlocks(world1, structureBoundingBox3, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, 0, 0, false);
				this.fillWithBlocks(world1, structureBoundingBox3, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, 0, 0, false);
				this.fillWithBlocks(world1, structureBoundingBox3, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, 0, 0, false);
			} else {
				this.fillWithBlocks(world1, structureBoundingBox3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, 0, 0, false);
				this.fillWithBlocks(world1, structureBoundingBox3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, 0, 0, false);
			}

			// Columns
			this.fillWithBlocksWithRandomVariation(world1, structureBoundingBox3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.minX + 1, this.boundingBox.maxY - 1, this.boundingBox.minZ + 1, Block.wood.blockID, Block.wood.blockID, random2, 4, false);
			this.fillWithBlocksWithRandomVariation(world1, structureBoundingBox3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.minX + 1, this.boundingBox.maxY - 1, this.boundingBox.maxZ - 1, Block.wood.blockID, Block.wood.blockID, random2, 4, false);
			this.fillWithBlocksWithRandomVariation(world1, structureBoundingBox3, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY - 1, this.boundingBox.minZ + 1, Block.wood.blockID, Block.wood.blockID, random2, 4, false);
			this.fillWithBlocksWithRandomVariation(world1, structureBoundingBox3, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxX - 1, this.boundingBox.maxY - 1, this.boundingBox.maxZ - 1, Block.wood.blockID, Block.wood.blockID, random2, 4, false);

			for(int i4 = this.boundingBox.minX; i4 <= this.boundingBox.maxX; ++i4) {
				for(int i5 = this.boundingBox.minZ; i5 <= this.boundingBox.maxZ; ++i5) {
					this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, i4, this.boundingBox.maxY, i5, structureBoundingBox3);
					int i6 = this.getBlockIdAtCurrentPosition(world1, i4, this.boundingBox.minY - 1, i5, structureBoundingBox3);
					if(i6 == 0) {
						this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 8|2, i4, this.boundingBox.minY - 1, i5, structureBoundingBox3); // Was: planks, 0
					}
					
					this.throwblockChainUp(world1, this.boundingBox.minX, this.boundingBox.minZ, structureBoundingBox3);
					this.throwblockChainUp(world1, this.boundingBox.minX, this.boundingBox.maxZ, structureBoundingBox3);
					this.throwblockChainUp(world1, this.boundingBox.maxX, this.boundingBox.minZ, structureBoundingBox3);
					this.throwblockChainUp(world1, this.boundingBox.maxX, this.boundingBox.maxZ, structureBoundingBox3);
				}
			}

			return true;
		}
	}

	private void throwblockChainUp(World world1, int x, int z, StructureBoundingBox structureBoundingBox3) {
		if(
				this.getBlockIdAtCurrentPositionNoVertClip(world1, x, this.boundingBox.maxY + 1, z, structureBoundingBox3) == 0 && 
				!this.canBlockSeeTheSky(world1, x, this.boundingBox.maxY + 1, z, structureBoundingBox3)
		) {
			int y = this.boundingBox.maxY + 1;
			while(this.getBlockIdAtCurrentPositionNoVertClip(world1, x, y, z, structureBoundingBox3) == 0) {
				this.placeBlockAtCurrentPositionNoVertClip(world1, mod_enhancedVanillaStructures.blockChain.blockID, 0, x, y, z, structureBoundingBox3);
				y ++;
			}
		}
	}
}
