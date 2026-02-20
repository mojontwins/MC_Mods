package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentStrongholdCorridor extends ComponentStronghold {
	private final int field_35052_a;

	public ComponentStrongholdCorridor(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.coordBaseMode = i4;
		this.boundingBox = structureBoundingBox3;
		this.field_35052_a = i4 != 2 && i4 != 0 ? structureBoundingBox3.getXSize() : structureBoundingBox3.getZSize();
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
	}

	public static StructureBoundingBox func_35051_a(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5) {
		StructureBoundingBox structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -1, -1, 0, 5, 5, 4, i5);
		StructureComponent structureComponent8 = StructureComponent.findIntersecting(list0, structureBoundingBox7);
		if(structureComponent8 == null) {
			return null;
		} else {
			if(structureComponent8.getBoundingBox().minY == structureBoundingBox7.minY) {
				for(int i9 = 3; i9 >= 1; --i9) {
					structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -1, -1, 0, 5, 5, i9 - 1, i5);
					if(!structureComponent8.getBoundingBox().intersectsWith(structureBoundingBox7)) {
						return StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -1, -1, 0, 5, 5, i9, i5);
					}
				}
			}

			return null;
		}
	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if(this.getAirRatioBelowStructureBoundingBox(world1, structureBoundingBox3) > 0.2F) {
			return false;
		} else {
			for(int i4 = 0; i4 < this.field_35052_a; ++i4) {
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 0, 0, i4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 1, 0, i4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 2, 0, i4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 3, 0, i4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 4, 0, i4, structureBoundingBox3);

				for(int i5 = 1; i5 <= 3; ++i5) {
					this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 0, i5, i4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, 0, 0, 1, i5, i4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, 0, 0, 2, i5, i4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, 0, 0, 3, i5, i4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 4, i5, i4, structureBoundingBox3);
				}

				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 0, 4, i4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 1, 4, i4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 2, 4, i4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 3, 4, i4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 4, 4, i4, structureBoundingBox3);
			}

			return true;
		}
	}
}
