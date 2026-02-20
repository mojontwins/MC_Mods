package org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaftmesa;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructurePieceTreasure;

public class StructureMineshaftMesaPieces {
	private static final StructurePieceTreasure[] lootArray = new StructurePieceTreasure[]{
			new StructurePieceTreasure(Item.ingotIron.shiftedIndex, 0, 1, 5, 10), 
			new StructurePieceTreasure(Item.ingotGold.shiftedIndex, 0, 1, 3, 5), 
			new StructurePieceTreasure(Item.redstone.shiftedIndex, 0, 4, 9, 5), 
			new StructurePieceTreasure(Item.dyePowder.shiftedIndex, 4, 4, 9, 5), 
			new StructurePieceTreasure(Item.diamond.shiftedIndex, 0, 1, 2, 3), 
			//new StructurePieceTreasure(Item.nametagSimple.shiftedIndex, 0, 1, 2, 3),
			new StructurePieceTreasure(Item.coal.shiftedIndex, 0, 3, 8, 10), 
			new StructurePieceTreasure(Item.bread.shiftedIndex, 0, 1, 3, 15), 
			new StructurePieceTreasure(Item.pickaxeSteel.shiftedIndex, 0, 1, 1, 1), 
			new StructurePieceTreasure(Block.rail.blockID, 0, 4, 8, 1), 
			new StructurePieceTreasure(Item.seeds.shiftedIndex, 0, 2, 4, 10), 
	};

	private static StructureComponent getRandomComponent(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5, int i6) {
		int i7 = random1.nextInt(100);
		StructureBoundingBox structureBoundingBox8;
		if(i7 >= 80) {
			structureBoundingBox8 = ComponentMineshaftMesaCross.findValidPlacement(list0, random1, i2, i3, i4, i5);
			if(structureBoundingBox8 != null) {
				return new ComponentMineshaftMesaCross(i6, random1, structureBoundingBox8, i5);
			}
		} else if(i7 >= 60) {
			structureBoundingBox8 = ComponentMineshaftMesaStairs.findValidPlacement(list0, random1, i2, i3, i4, i5);
			if(structureBoundingBox8 != null) {
				return new ComponentMineshaftMesaStairs(i6, random1, structureBoundingBox8, i5);
			}
		} else {
			structureBoundingBox8 = ComponentMineshaftMesaCorridor.findValidPlacement(list0, random1, i2, i3, i4, i5);
			if(structureBoundingBox8 != null) {
				return new ComponentMineshaftMesaCorridor(i6, random1, structureBoundingBox8, i5);
			}
		}

		return null;
	}

	private static StructureComponent getNextMineShaftComponent(StructureComponent structureComponent0, List<StructureComponent> list1, Random random2, int i3, int i4, int i5, int i6, int i7) {
		if(i7 > 8) {
			return null;
		} else if(Math.abs(i3 - structureComponent0.getBoundingBox().minX) <= 80 && Math.abs(i5 - structureComponent0.getBoundingBox().minZ) <= 80) {
			StructureComponent structureComponent8 = getRandomComponent(list1, random2, i3, i4, i5, i6, i7 + 1);
			if(structureComponent8 != null) {
				list1.add(structureComponent8);
				structureComponent8.buildComponent(structureComponent0, list1, random2);
			}

			return structureComponent8;
		} else {
			return null;
		}
	}

	static StructureComponent getNextComponent(StructureComponent structureComponent0, List<StructureComponent> list1, Random random2, int i3, int i4, int i5, int i6, int i7) {
		return getNextMineShaftComponent(structureComponent0, list1, random2, i3, i4, i5, i6, i7);
	}

	static StructurePieceTreasure[] getTreasurePieces() {
		return lootArray;
	}
}
