package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentStrongholdStairs extends ComponentStronghold {
	private final boolean mainStairs;
	private final EnumDoor doorType;

	public ComponentStrongholdStairs(World world, int i1, Random random2, int i3, int i4) {
		super(i1);
		int base = world.getHeightValue(i3, i4);
		this.mainStairs = true;
		this.coordBaseMode = random2.nextInt(4);
		this.doorType = EnumDoor.OPENING;
		switch(this.coordBaseMode) {
		case 0:
		case 2:
			this.boundingBox = new StructureBoundingBox(i3, base, i4, i3 + 5 - 1, base + 10, i4 + 5 - 1);
			break;
		default:
			this.boundingBox = new StructureBoundingBox(i3, base, i4, i3 + 5 - 1, base + 10, i4 + 5 - 1);
		}

	}

	public ComponentStrongholdStairs(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.mainStairs = false;
		this.coordBaseMode = i4;
		this.doorType = this.getRandomDoor(random2);
		this.boundingBox = structureBoundingBox3;
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		if(this.mainStairs) {
			StructureStrongholdPieces.setComponentType(ComponentStrongholdCrossing.class);
		}

		this.getNextComponentNormal((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 1);
	}

	public static ComponentStrongholdStairs getStrongholdStairsComponent(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5, int i6) {
		StructureBoundingBox structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -1, -7, 0, 5, 11, 5, i5);
		return canStrongholdGoDeeper(structureBoundingBox7) && StructureComponent.findIntersecting(list0, structureBoundingBox7) == null ? new ComponentStrongholdStairs(i6, random1, structureBoundingBox7, i5) : null;
	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if(this.getAirRatioBelowStructureBoundingBox(world1, structureBoundingBox3) > 0.2F) {
			return false;
		} else {
			if(this.mainStairs) {
				;
			}

			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 0, 0, 0, 4, 10, 4, true, random2, StructureStrongholdPieces.getStrongholdStones());
			this.placeDoor(world1, random2, structureBoundingBox3, this.doorType, 1, 7, 0);
			this.placeDoor(world1, random2, structureBoundingBox3, EnumDoor.OPENING, 1, 1, 4);
			if (!this.mainStairs) {
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 2, 6, 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 1, 5, 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 1, 6, 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 1, 5, 2, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 1, 4, 3, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 1, 5, 3, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 2, 4, 3, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 3, 3, 3, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 3, 4, 3, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 3, 3, 2, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 3, 2, 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 3, 3, 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 2, 2, 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 1, 1, 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 1, 2, 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 1, 1, 2, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 1, 1, 3, structureBoundingBox3);
			}
			return true;
		}
	}
}
