package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentStrongholdStraight extends ComponentStronghold {
	private final EnumDoor doorType;
	private final boolean expandsX;
	private final boolean expandsZ;

	public ComponentStrongholdStraight(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.coordBaseMode = i4;
		this.doorType = this.getRandomDoor(random2);
		this.boundingBox = structureBoundingBox3;
		this.expandsX = random2.nextInt(2) == 0;
		this.expandsZ = random2.nextInt(2) == 0;
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		this.getNextComponentNormal((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 1);
		if(this.expandsX) {
			this.getNextComponentX((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 2);
		}

		if(this.expandsZ) {
			this.getNextComponentZ((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 2);
		}

	}

	public static ComponentStrongholdStraight findValidPlacement(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5, int i6) {
		StructureBoundingBox structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -1, -1, 0, 5, 5, 7, i5);
		return canStrongholdGoDeeper(structureBoundingBox7) && StructureComponent.findIntersecting(list0, structureBoundingBox7) == null ? new ComponentStrongholdStraight(i6, random1, structureBoundingBox7, i5) : null;
	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if(this.getAirRatioBelowStructureBoundingBox(world1, structureBoundingBox3) > 0.2F) {
			return false;
		} else {
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 0, 0, 0, 4, 4, 6, true, random2, StructureStrongholdPieces.getStrongholdStones());
			this.placeDoor(world1, random2, structureBoundingBox3, this.doorType, 1, 1, 0);
			this.placeDoor(world1, random2, structureBoundingBox3, EnumDoor.OPENING, 1, 1, 6);
			this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.1F, 1, 2, 1, Block.torchWood.blockID, 0);
			this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.1F, 3, 2, 1, Block.torchWood.blockID, 0);
			this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.1F, 1, 2, 5, Block.torchWood.blockID, 0);
			this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.1F, 3, 2, 5, Block.torchWood.blockID, 0);
			if(this.expandsX) {
				this.fillWithBlocks(world1, structureBoundingBox3, 0, 1, 2, 0, 3, 4, 0, 0, false);
			}

			if(this.expandsZ) {
				this.fillWithBlocks(world1, structureBoundingBox3, 4, 1, 2, 4, 3, 4, 0, 0, false);
			}

			return true;
		}
	}
}
