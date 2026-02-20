package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentStrongholdCrossing extends ComponentStronghold {
	protected final EnumDoor doorType;
	private boolean field_35042_b;
	private boolean field_35043_c;
	private boolean field_35040_d;
	private boolean field_35041_e;

	public ComponentStrongholdCrossing(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.coordBaseMode = i4;
		this.doorType = this.getRandomDoor(random2);
		this.boundingBox = structureBoundingBox3;
		this.field_35042_b = random2.nextBoolean();
		this.field_35043_c = random2.nextBoolean();
		this.field_35040_d = random2.nextBoolean();
		this.field_35041_e = random2.nextInt(3) > 0;
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		int i4 = 3;
		int i5 = 5;
		if(this.coordBaseMode == 1 || this.coordBaseMode == 2) {
			i4 = 8 - i4;
			i5 = 8 - i5;
		}

		this.getNextComponentNormal((ComponentStrongholdStairs2)structureComponent1, list2, random3, 5, 1);
		if(this.field_35042_b) {
			this.getNextComponentX((ComponentStrongholdStairs2)structureComponent1, list2, random3, i4, 1);
		}

		if(this.field_35043_c) {
			this.getNextComponentX((ComponentStrongholdStairs2)structureComponent1, list2, random3, i5, 7);
		}

		if(this.field_35040_d) {
			this.getNextComponentZ((ComponentStrongholdStairs2)structureComponent1, list2, random3, i4, 1);
		}

		if(this.field_35041_e) {
			this.getNextComponentZ((ComponentStrongholdStairs2)structureComponent1, list2, random3, i5, 7);
		}

	}

	public static ComponentStrongholdCrossing findValidPlacement(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5, int i6) {
		StructureBoundingBox structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -4, -3, 0, 10, 9, 11, i5);
		ComponentStrongholdCrossing comp = new ComponentStrongholdCrossing(i6, random1, structureBoundingBox7, i5);
		return canStrongholdGoDeeper(structureBoundingBox7) && StructureComponent.findIntersecting(list0, structureBoundingBox7) == null ? comp : null;
	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if(this.getAirRatioBelowStructureBoundingBox(world1, structureBoundingBox3) > 0.2F) {
			return false;
		} else {
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 0, 0, 0, 9, 8, 10, true, random2, StructureStrongholdPieces.getStrongholdStones());
			this.placeDoor(world1, random2, structureBoundingBox3, this.doorType, 4, 3, 0);
			if(this.field_35042_b) {
				this.fillWithBlocks(world1, structureBoundingBox3, 0, 3, 1, 0, 5, 3, 0, 0, false);
			}

			if(this.field_35040_d) {
				this.fillWithBlocks(world1, structureBoundingBox3, 9, 3, 1, 9, 5, 3, 0, 0, false);
			}

			if(this.field_35043_c) {
				this.fillWithBlocks(world1, structureBoundingBox3, 0, 5, 7, 0, 7, 9, 0, 0, false);
			}

			if(this.field_35041_e) {
				this.fillWithBlocks(world1, structureBoundingBox3, 9, 5, 7, 9, 7, 9, 0, 0, false);
			}

			this.fillWithBlocks(world1, structureBoundingBox3, 5, 1, 10, 7, 3, 10, 0, 0, false);
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 1, 2, 1, 8, 2, 6, false, random2, StructureStrongholdPieces.getStrongholdStones());
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 4, 1, 5, 4, 4, 9, false, random2, StructureStrongholdPieces.getStrongholdStones());
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 8, 1, 5, 8, 4, 9, false, random2, StructureStrongholdPieces.getStrongholdStones());
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 1, 4, 7, 3, 4, 9, false, random2, StructureStrongholdPieces.getStrongholdStones());
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 1, 3, 5, 3, 3, 6, false, random2, StructureStrongholdPieces.getStrongholdStones());
			this.fillWithBlocks(world1, structureBoundingBox3, 1, 3, 4, 3, 3, 4, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
			this.fillWithBlocks(world1, structureBoundingBox3, 1, 4, 6, 3, 4, 6, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 5, 1, 7, 7, 1, 8, false, random2, StructureStrongholdPieces.getStrongholdStones());
			this.fillWithBlocks(world1, structureBoundingBox3, 5, 1, 9, 7, 1, 9, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
			this.fillWithBlocks(world1, structureBoundingBox3, 5, 2, 7, 7, 2, 7, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
			this.fillWithBlocks(world1, structureBoundingBox3, 4, 5, 7, 4, 5, 9, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
			this.fillWithBlocks(world1, structureBoundingBox3, 8, 5, 7, 8, 5, 9, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
			this.fillWithBlocks(world1, structureBoundingBox3, 5, 5, 7, 7, 5, 9, Block.stairDouble.blockID, Block.stairDouble.blockID, false);
			this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, 6, 5, 6, structureBoundingBox3);
			return true;
		}
	}
}
