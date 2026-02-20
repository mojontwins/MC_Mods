package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.Random;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructurePieceBlockSelector;

class StructureStrongholdStones extends StructurePieceBlockSelector {
	private StructureStrongholdStones() {
	}

	public void selectBlocks(Random rand, int x, int y, int z, boolean hollow) {
		if(!hollow) {
			this.selectedBlockId = 0;
			this.selectedBlockMetaData = 0;
		} else {
			this.selectedBlockId = MapGenStronghold.bricksId;
			float f6 = rand.nextFloat();
			if(f6 < 0.2F) {
				this.selectedBlockId = MapGenStronghold.bricksAlt1Id;
				this.selectedBlockMetaData = MapGenStronghold.bricksAlt1Meta;
			} else if(f6 < 0.5F) {
				this.selectedBlockId = MapGenStronghold.bricksAlt2Id;
				this.selectedBlockMetaData = MapGenStronghold.bricksAlt2Meta;
			} else {
				this.selectedBlockId = MapGenStronghold.bricksId;
				this.selectedBlockMetaData = MapGenStronghold.bricksMeta;
			}
		}

	}

	StructureStrongholdStones(StructureStrongholdPieceWeight2 structureStrongholdPieceWeight21) {
		this();
	}
}
