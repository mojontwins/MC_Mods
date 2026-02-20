package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

abstract class ComponentStronghold extends StructureComponent {
	protected ComponentStronghold(int i1) {
		super(i1);
	}

	protected void placeDoor(World world1, Random random2, StructureBoundingBox structureBoundingBox3, EnumDoor enumDoor4, int i5, int i6, int i7) {
		
		// Only generate a door on NON air 100%
		if(
				this.getBlockIdAtCurrentPosition(world1, i5, i6 - 1, i7, structureBoundingBox3) == 0 ||
				this.getBlockIdAtCurrentPosition(world1, i5 + 1, i6 - 1, i7, structureBoundingBox3) == 0 ||
				this.getBlockIdAtCurrentPosition(world1, i5 + 2, i6 - 1, i7, structureBoundingBox3) == 0
		) {
			return;
		}
		
		// Only generate if at least 1 non air above
		if(
				this.getBlockIdAtCurrentPosition(world1, i5, i6 + 3, i7, structureBoundingBox3) == 0 &&
				this.getBlockIdAtCurrentPosition(world1, i5 + 1, i6 + 3, i7, structureBoundingBox3) == 0 &&
				this.getBlockIdAtCurrentPosition(world1, i5 + 2, i6 + 3, i7, structureBoundingBox3) == 0
		) {
			return;
		}
		
		switch(EnumDoorHelper.doorEnum[enumDoor4.ordinal()]) {
		case 1:
		default:
			this.fillWithBlocks(world1, structureBoundingBox3, i5, i6, i7, i5 + 3 - 1, i6 + 3 - 1, i7, 0, 0, false);
			break;
		case 2:
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5, i6, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5, i6 + 1, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5, i6 + 2, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5 + 1, i6 + 2, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5 + 2, i6 + 2, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5 + 2, i6 + 1, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5 + 2, i6, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, Block.doorWood.blockID, 0, i5 + 1, i6, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, Block.doorWood.blockID, 8, i5 + 1, i6 + 1, i7, structureBoundingBox3);
			break;
		case 3:
			this.placeBlockAtCurrentPosition(world1, 0, 0, i5 + 1, i6, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, 0, 0, i5 + 1, i6 + 1, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.ironFenceId, MapGenStronghold.ironFenceMeta, i5, i6, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.ironFenceId, MapGenStronghold.ironFenceMeta, i5, i6 + 1, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.ironFenceId, MapGenStronghold.ironFenceMeta, i5, i6 + 2, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.ironFenceId, MapGenStronghold.ironFenceMeta, i5 + 1, i6 + 2, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.ironFenceId, MapGenStronghold.ironFenceMeta, i5 + 2, i6 + 2, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.ironFenceId, MapGenStronghold.ironFenceMeta, i5 + 2, i6 + 1, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.ironFenceId, MapGenStronghold.ironFenceMeta, i5 + 2, i6, i7, structureBoundingBox3);
			break;
		case 4:
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5, i6, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5, i6 + 1, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5, i6 + 2, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5 + 1, i6 + 2, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5 + 2, i6 + 2, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5 + 2, i6 + 1, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, i5 + 2, i6, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, Block.doorSteel.blockID, 0, i5 + 1, i6, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, Block.doorSteel.blockID, 8, i5 + 1, i6 + 1, i7, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, Block.button.blockID, this.getMetadataWithOffset(Block.button.blockID, 4), i5 + 2, i6 + 1, i7 + 1, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, Block.button.blockID, this.getMetadataWithOffset(Block.button.blockID, 3), i5 + 2, i6 + 1, i7 - 1, structureBoundingBox3);
		}

	}

	protected EnumDoor getRandomDoor(Random random1) {
		int i2 = random1.nextInt(5);
		switch(i2) {
		case 0:
		case 1:
		default:
			return EnumDoor.OPENING;
		case 2:
			return EnumDoor.WOOD_DOOR;
		case 3:
			return EnumDoor.GRATES;
		case 4:
			return EnumDoor.IRON_DOOR;
		}
	}

	protected StructureComponent getNextComponentNormal(ComponentStrongholdStairs2 componentStrongholdStairs21, List<StructureComponent> list2, Random random3, int i4, int i5) {
		switch(this.coordBaseMode) {
		case 0:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.minX + i4, this.boundingBox.minY + i5, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
		case 1:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY + i5, this.boundingBox.minZ + i4, this.coordBaseMode, this.getComponentType());
		case 2:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.minX + i4, this.boundingBox.minY + i5, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
		case 3:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY + i5, this.boundingBox.minZ + i4, this.coordBaseMode, this.getComponentType());
		default:
			return null;
		}
	}

	protected StructureComponent getNextComponentX(ComponentStrongholdStairs2 componentStrongholdStairs21, List<StructureComponent> list2, Random random3, int i4, int i5) {
		switch(this.coordBaseMode) {
		case 0:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY + i4, this.boundingBox.minZ + i5, 1, this.getComponentType());
		case 1:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.minX + i5, this.boundingBox.minY + i4, this.boundingBox.minZ - 1, 2, this.getComponentType());
		case 2:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY + i4, this.boundingBox.minZ + i5, 1, this.getComponentType());
		case 3:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.minX + i5, this.boundingBox.minY + i4, this.boundingBox.minZ - 1, 2, this.getComponentType());
		default:
			return null;
		}
	}

	protected StructureComponent getNextComponentZ(ComponentStrongholdStairs2 componentStrongholdStairs21, List<StructureComponent> list2, Random random3, int i4, int i5) {
		switch(this.coordBaseMode) {
		case 0:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY + i4, this.boundingBox.minZ + i5, 3, this.getComponentType());
		case 1:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.minX + i5, this.boundingBox.minY + i4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
		case 2:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY + i4, this.boundingBox.minZ + i5, 3, this.getComponentType());
		case 3:
			return StructureStrongholdPieces.getNextValidComponentAccess(componentStrongholdStairs21, list2, random3, this.boundingBox.minX + i5, this.boundingBox.minY + i4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
		default:
			return null;
		}
	}

	protected static boolean canStrongholdGoDeeper(StructureBoundingBox structureBoundingBox0) {
		return structureBoundingBox0 != null && structureBoundingBox0.minY > 10;
	}
}
