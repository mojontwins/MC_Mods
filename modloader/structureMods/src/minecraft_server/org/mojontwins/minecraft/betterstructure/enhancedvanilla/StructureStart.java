package org.mojontwins.minecraft.betterstructure.enhancedvanilla;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.src.World;

public abstract class StructureStart {
	protected LinkedList<StructureComponent> components = new LinkedList<StructureComponent>();
	protected StructureBoundingBox boundingBox;

	public StructureBoundingBox getBoundingBox() {
		return this.boundingBox;
	}

	public LinkedList<StructureComponent> getComponents() {
		return this.components;
	}

	public void generateStructure(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		Iterator<StructureComponent> iterator4 = this.components.iterator();

		while(iterator4.hasNext()) {
			StructureComponent structureComponent5 = iterator4.next();
			if(structureComponent5.getBoundingBox().intersectsWith(structureBoundingBox3) && !structureComponent5.addComponentParts(world1, random2, structureBoundingBox3, mostlySolid)) {
				iterator4.remove();
			}
		}

	}

	/*
	 * Will iterate all existing components and make the overall BB contain the BBs of all the components. 
	 */
	protected void updateBoundingBox() {
		this.boundingBox = StructureBoundingBox.getNewBoundingBox();
		Iterator<StructureComponent> iterator = this.components.iterator();

		while(iterator.hasNext()) {
			StructureComponent component = iterator.next();
			this.boundingBox.expandTo(component.getBoundingBox());
		}

	}

	protected void markAvailableHeight(World world, Random rand, int blocksUnderground) {
		// Set a maximum top for the structure! By default, this is 63-10 = 50
		int maxY = 63 - blocksUnderground;
		
		// The whole structure height in blocks, plus 1
		int structureHeight = this.boundingBox.getYSize() + 1;
		
		// If structure fits under maxY, consider a slightly bigger height
		if(structureHeight < maxY) {
			structureHeight += rand.nextInt(maxY - structureHeight);
		}

		// This will raise the structure by a bit without surpassing maxY.
		int maxBottomY = structureHeight - this.boundingBox.maxY;
		
		// Offset this bb
		this.boundingBox.offset(0, maxBottomY, 0);
		
		// Offset all components.
		Iterator<StructureComponent> iterator = this.components.iterator();
		while(iterator.hasNext()) {
			StructureComponent structureComponent8 = iterator.next();
			structureComponent8.getBoundingBox().offset(0, maxBottomY, 0);
		}

	}

	protected void setRandomHeight(World world1, Random random2, int i3, int i4) {
		int i5 = i4 - i3 + 1 - this.boundingBox.getYSize();
		int i10;
		if(i5 > 1) {
			i10 = i3 + random2.nextInt(i5);
		} else {
			i10 = i3;
		}

		int i7 = i10 - this.boundingBox.minY;
		this.boundingBox.offset(0, i7, 0);
		Iterator<StructureComponent> iterator8 = this.components.iterator();

		while(iterator8.hasNext()) {
			StructureComponent structureComponent9 = iterator8.next();
			structureComponent9.getBoundingBox().offset(0, i7, 0);
		}

	}

	public boolean isSizeableStructure() {
		return true;
	}
}
