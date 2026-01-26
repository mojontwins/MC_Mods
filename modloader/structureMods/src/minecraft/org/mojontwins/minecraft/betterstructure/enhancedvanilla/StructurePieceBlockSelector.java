package org.mojontwins.minecraft.betterstructure.enhancedvanilla;

import java.util.Random;

public abstract class StructurePieceBlockSelector {
	protected int selectedBlockId;
	protected int selectedBlockMetaData;

	public abstract void selectBlocks(Random random1, int i2, int i3, int i4, boolean z5);

	public int getSelectedBlockId() {
		return this.selectedBlockId;
	}

	public int getSelectedBlockMetaData() {
		return this.selectedBlockMetaData;
	}
}
