package org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaftmesa;

import java.util.List;
import java.util.Random;

import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentMineshaftMesaStairs extends StructureComponent {
	public ComponentMineshaftMesaStairs(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.coordBaseMode = i4;
		this.boundingBox = structureBoundingBox3;
	}

	public static StructureBoundingBox findValidPlacement(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5) {
		StructureBoundingBox structureBoundingBox6 = new StructureBoundingBox(i2, i3 - 5, i4, i2, i3 + 2, i4);
		switch(i5) {
		case 0:
			structureBoundingBox6.maxX = i2 + 2;
			structureBoundingBox6.maxZ = i4 + 8;
			break;
		case 1:
			structureBoundingBox6.minX = i2 - 8;
			structureBoundingBox6.maxZ = i4 + 2;
			break;
		case 2:
			structureBoundingBox6.maxX = i2 + 2;
			structureBoundingBox6.minZ = i4 - 8;
			break;
		case 3:
			structureBoundingBox6.maxX = i2 + 8;
			structureBoundingBox6.maxZ = i4 + 2;
		}

		return StructureComponent.findIntersecting(list0, structureBoundingBox6) != null ? null : structureBoundingBox6;
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		int i4 = this.getComponentType();
		switch(this.coordBaseMode) {
		case 0:
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, i4);
			break;
		case 1:
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, i4);
			break;
		case 2:
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, i4);
			break;
		case 3:
			StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, i4);
		}

	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if(mostlySolid && !this.isMostlySolidInStructureBoundingbox(world1, structureBoundingBox3)) {
			return false;
		} else {
			this.fillWithBlocks(world1, structureBoundingBox3, 0, 5, 0, 2, 7, 1, 0, 0, false);
			this.fillWithBlocks(world1, structureBoundingBox3, 0, 0, 7, 2, 2, 8, 0, 0, false);

			for(int i4 = 0; i4 < 5; ++i4) {
				this.fillWithBlocks(world1, structureBoundingBox3, 0, 5 - i4 - (i4 < 4 ? 1 : 0), 2 + i4, 2, 7 - i4, 2 + i4, 0, 0, false);
			}

			return true;
		}
	}
}
