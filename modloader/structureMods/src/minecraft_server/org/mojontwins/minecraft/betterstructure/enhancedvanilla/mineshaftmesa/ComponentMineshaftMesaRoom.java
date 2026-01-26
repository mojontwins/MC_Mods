package org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaftmesa;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentMineshaftMesaRoom extends StructureComponent {
	private LinkedList<StructureBoundingBox> chidStructures = new LinkedList<StructureBoundingBox>();

	public ComponentMineshaftMesaRoom(int i1, Random random2, int x, int z) {
		super(i1);
		int base = 66 + random2.nextInt(32);
		this.boundingBox = new StructureBoundingBox(x, base, z, x + 7 + random2.nextInt(6), base + 4 + random2.nextInt(6), z + 7 + random2.nextInt(6));
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		
		int i4 = this.getComponentType();
		int i6 = this.boundingBox.getYSize() - 3 - 1;
		if(i6 <= 0) {
			i6 = 1;
		}

		int i5;
		StructureComponent structureComponent7;
		StructureBoundingBox structureBoundingBox8;
		for(i5 = 0; i5 < this.boundingBox.getXSize(); i5 += 4) {
			i5 += random3.nextInt(this.boundingBox.getXSize());
			if(i5 + 3 > this.boundingBox.getXSize()) {
				break;
			}

			structureComponent7 = StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + i5, this.boundingBox.minY + random3.nextInt(i6) + 1, this.boundingBox.minZ - 1, 2, i4);
			if(structureComponent7 != null) {
				structureBoundingBox8 = structureComponent7.getBoundingBox();
				this.chidStructures.add(new StructureBoundingBox(structureBoundingBox8.minX, structureBoundingBox8.minY, this.boundingBox.minZ, structureBoundingBox8.maxX, structureBoundingBox8.maxY, this.boundingBox.minZ + 1));
			}
		}

		for(i5 = 0; i5 < this.boundingBox.getXSize(); i5 += 4) {
			i5 += random3.nextInt(this.boundingBox.getXSize());
			if(i5 + 3 > this.boundingBox.getXSize()) {
				break;
			}

			structureComponent7 = StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX + i5, this.boundingBox.minY + random3.nextInt(i6) + 1, this.boundingBox.maxZ + 1, 0, i4);
			if(structureComponent7 != null) {
				structureBoundingBox8 = structureComponent7.getBoundingBox();
				this.chidStructures.add(new StructureBoundingBox(structureBoundingBox8.minX, structureBoundingBox8.minY, this.boundingBox.maxZ - 1, structureBoundingBox8.maxX, structureBoundingBox8.maxY, this.boundingBox.maxZ));
			}
		}

		for(i5 = 0; i5 < this.boundingBox.getZSize(); i5 += 4) {
			i5 += random3.nextInt(this.boundingBox.getZSize());
			if(i5 + 3 > this.boundingBox.getZSize()) {
				break;
			}

			structureComponent7 = StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY + random3.nextInt(i6) + 1, this.boundingBox.minZ + i5, 1, i4);
			if(structureComponent7 != null) {
				structureBoundingBox8 = structureComponent7.getBoundingBox();
				this.chidStructures.add(new StructureBoundingBox(this.boundingBox.minX, structureBoundingBox8.minY, structureBoundingBox8.minZ, this.boundingBox.minX + 1, structureBoundingBox8.maxY, structureBoundingBox8.maxZ));
			}
		}

		for(i5 = 0; i5 < this.boundingBox.getZSize(); i5 += 4) {
			i5 += random3.nextInt(this.boundingBox.getZSize());
			if(i5 + 3 > this.boundingBox.getZSize()) {
				break;
			}

			structureComponent7 = StructureMineshaftMesaPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY + random3.nextInt(i6) + 1, this.boundingBox.minZ + i5, 3, i4);
			if(structureComponent7 != null) {
				structureBoundingBox8 = structureComponent7.getBoundingBox();
				this.chidStructures.add(new StructureBoundingBox(this.boundingBox.maxX - 1, structureBoundingBox8.minY, structureBoundingBox8.minZ, this.boundingBox.maxX, structureBoundingBox8.maxY, structureBoundingBox8.maxZ));
			}
		}

	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {

		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if(mostlySolid && !this.isMostlySolidInStructureBoundingbox(world1, structureBoundingBox3)) {
			return false;
		} else {
			this.fillWithBlocksWithRandomVariation(world1, structureBoundingBox3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Block.dirt.blockID, Block.gravel.blockID, random2, 2, true);
			this.fillWithBlocks(world1, structureBoundingBox3, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, 0, 0, false);
			Iterator<StructureBoundingBox> iterator4 = this.chidStructures.iterator();

			while(iterator4.hasNext()) {
				StructureBoundingBox structureBoundingBox5 = (StructureBoundingBox)iterator4.next();
				this.fillWithBlocks(world1, structureBoundingBox3, structureBoundingBox5.minX, structureBoundingBox5.maxY - 2, structureBoundingBox5.minZ, structureBoundingBox5.maxX, structureBoundingBox5.maxY, structureBoundingBox5.maxZ, 0, 0, false);
			}

			this.randomlyRareFillWithBlocks(world1, structureBoundingBox3, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, 0, false);
			return true;
		}
	}
}
