package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentStrongholdRightTurn extends ComponentStrongholdLeftTurn {
	public ComponentStrongholdRightTurn(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1, random2, structureBoundingBox3, i4);
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		if(this.coordBaseMode != 2 && this.coordBaseMode != 3) {
			this.getNextComponentX((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 1);
		} else {
			this.getNextComponentZ((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 1);
		}

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
				this.fillWithBlocks(world1, structureBoundingBox3, 0, 1, 1, 0, 3, 3, 0, 0, false);
			} else {
				this.fillWithBlocks(world1, structureBoundingBox3, 4, 1, 1, 4, 3, 3, 0, 0, false);
			}

			return true;
		}
	}
}
