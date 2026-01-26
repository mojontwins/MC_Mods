package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentStrongholdLeftTurn extends ComponentStronghold {
	protected final EnumDoor doorType;

	public ComponentStrongholdLeftTurn(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.coordBaseMode = i4;
		this.doorType = this.getRandomDoor(random2);
		this.boundingBox = structureBoundingBox3;
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		if(this.coordBaseMode != 2 && this.coordBaseMode != 3) {
			this.getNextComponentZ((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 1);
		} else {
			this.getNextComponentX((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 1);
		}

	}

	public static ComponentStrongholdLeftTurn findValidPlacement(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5, int i6) {
		StructureBoundingBox structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -1, -1, 0, 5, 5, 5, i5);
		return canStrongholdGoDeeper(structureBoundingBox7) && StructureComponent.findIntersecting(list0, structureBoundingBox7) == null ? new ComponentStrongholdLeftTurn(i6, random1, structureBoundingBox7, i5) : null;
	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if(this.getAirRatioBelowStructureBoundingBox(world1, structureBoundingBox3) > 0.2F) {
			return false;
		} else {
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 0, 0, 0, 4, 4, 4, true, random2, StructureStrongholdPieces.getStrongholdStones());
			this.placeDoor(world1, random2, structureBoundingBox3, this.doorType, 1, 1, 0);
			if(this.coordBaseMode != 2 && this.coordBaseMode != 3) {
				this.fillWithBlocks(world1, structureBoundingBox3, 4, 1, 1, 4, 3, 3, 0, 0, false);
			} else {
				this.fillWithBlocks(world1, structureBoundingBox3, 0, 1, 1, 0, 3, 3, 0, 0, false);
			}

			return true;
		}
	}
}
