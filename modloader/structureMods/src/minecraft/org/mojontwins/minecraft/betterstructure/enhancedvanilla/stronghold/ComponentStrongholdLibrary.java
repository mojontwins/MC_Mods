package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructurePieceTreasure;

public class ComponentStrongholdLibrary extends ComponentStronghold {
	private static final StructurePieceTreasure[] treasureList = new StructurePieceTreasure[] {
			new StructurePieceTreasure(Item.book.shiftedIndex, 0, 1, 3, 20),
			new StructurePieceTreasure(Item.paper.shiftedIndex, 0, 2, 7, 20), 
			new StructurePieceTreasure(Block.obsidian.blockID, 0, 1, 1, 1), 
			new StructurePieceTreasure(Item.compass.shiftedIndex, 0, 1, 1, 1)};
	protected final EnumDoor doorType;
	private final boolean isLargeRoom;

	public ComponentStrongholdLibrary(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.coordBaseMode = i4;
		this.doorType = this.getRandomDoor(random2);
		this.boundingBox = structureBoundingBox3;
		this.isLargeRoom = structureBoundingBox3.getYSize() > 6;
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
	}

	public static ComponentStrongholdLibrary findValidPlacement(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5, int i6) {
		StructureBoundingBox structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -4, -1, 0, 14, 11, 15, i5);
		if(!canStrongholdGoDeeper(structureBoundingBox7) || StructureComponent.findIntersecting(list0, structureBoundingBox7) != null) {
			structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -4, -1, 0, 14, 6, 15, i5);
			if(!canStrongholdGoDeeper(structureBoundingBox7) || StructureComponent.findIntersecting(list0, structureBoundingBox7) != null) {
				return null;
			}
		}

		return new ComponentStrongholdLibrary(i6, random1, structureBoundingBox7, i5);
	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		// System.out.println ("Attempting library");
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			// System.out.println ("Failed library (water)");
			return false;
		} else if(this.getAirRatioInStructureBoundingBox(world1, structureBoundingBox3) > 0.2F) {
			// System.out.println ("Failed library (air = " + this.getAirRatioInStructureBoundingBox(world1, structureBoundingBox3) + ")");
			return false;
		} else {
			byte b4 = 11;
			if(!this.isLargeRoom) {
				b4 = 6;
			}

			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 0, 0, 0, 13, b4 - 1, 14, true, random2, StructureStrongholdPieces.getStrongholdStones());
			this.placeDoor(world1, random2, structureBoundingBox3, this.doorType, 4, 1, 0);
			this.randomlyFillWithBlocks(world1, structureBoundingBox3, random2, 0.07F, 2, 1, 1, 11, 4, 13, Block.web.blockID, Block.web.blockID, false);

			int i7;
			for(i7 = 1; i7 <= 13; ++i7) {
				if((i7 - 1) % 4 == 0) {
					this.fillWithBlocks(world1, structureBoundingBox3, 1, 1, i7, 1, 4, i7, Block.planks.blockID, Block.planks.blockID, false);
					this.fillWithBlocks(world1, structureBoundingBox3, 12, 1, i7, 12, 4, i7, Block.planks.blockID, Block.planks.blockID, false);
					this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, 2, 3, i7, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, 11, 3, i7, structureBoundingBox3);
					if(this.isLargeRoom) {
						this.fillWithBlocks(world1, structureBoundingBox3, 1, 6, i7, 1, 9, i7, Block.planks.blockID, Block.planks.blockID, false);
						this.fillWithBlocks(world1, structureBoundingBox3, 12, 6, i7, 12, 9, i7, Block.planks.blockID, Block.planks.blockID, false);
					}
				} else {
					this.fillWithBlocks(world1, structureBoundingBox3, 1, 1, i7, 1, 4, i7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
					this.fillWithBlocks(world1, structureBoundingBox3, 12, 1, i7, 12, 4, i7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
					if(this.isLargeRoom) {
						this.fillWithBlocks(world1, structureBoundingBox3, 1, 6, i7, 1, 9, i7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
						this.fillWithBlocks(world1, structureBoundingBox3, 12, 6, i7, 12, 9, i7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
					}
				}
			}

			for(i7 = 3; i7 < 12; i7 += 2) {
				this.fillWithBlocks(world1, structureBoundingBox3, 3, 1, i7, 4, 3, i7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
				this.fillWithBlocks(world1, structureBoundingBox3, 6, 1, i7, 7, 3, i7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
				this.fillWithBlocks(world1, structureBoundingBox3, 9, 1, i7, 10, 3, i7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
			}

			if(this.isLargeRoom) {
				this.fillWithBlocks(world1, structureBoundingBox3, 1, 5, 1, 3, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
				this.fillWithBlocks(world1, structureBoundingBox3, 10, 5, 1, 12, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
				this.fillWithBlocks(world1, structureBoundingBox3, 4, 5, 1, 9, 5, 2, Block.planks.blockID, Block.planks.blockID, false);
				this.fillWithBlocks(world1, structureBoundingBox3, 4, 5, 12, 9, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
				this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 9, 5, 11, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 8, 5, 11, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 9, 5, 10, structureBoundingBox3);
				this.fillWithBlocks(world1, structureBoundingBox3, 3, 6, 2, 3, 6, 12, Block.fence.blockID, Block.fence.blockID, false);
				this.fillWithBlocks(world1, structureBoundingBox3, 10, 6, 2, 10, 6, 10, Block.fence.blockID, Block.fence.blockID, false);
				this.fillWithBlocks(world1, structureBoundingBox3, 4, 6, 2, 9, 6, 2, Block.fence.blockID, Block.fence.blockID, false);
				this.fillWithBlocks(world1, structureBoundingBox3, 4, 6, 12, 8, 6, 12, Block.fence.blockID, Block.fence.blockID, false);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, 9, 6, 11, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, 8, 6, 11, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, 9, 6, 10, structureBoundingBox3);
				i7 = this.getMetadataWithOffset(Block.ladder.blockID, 3);
				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, i7, 10, 1, 13, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, i7, 10, 2, 13, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, i7, 10, 3, 13, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, i7, 10, 4, 13, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, i7, 10, 5, 13, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, i7, 10, 6, 13, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, i7, 10, 7, 13, structureBoundingBox3);
				byte b8 = 7;
				byte b9 = 7;
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8 - 1, 9, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8, 9, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8 - 1, 8, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8, 8, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8 - 1, 7, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8, 7, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8 - 2, 7, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8 + 1, 7, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8 - 1, 7, b9 - 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8 - 1, 7, b9 + 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8, 7, b9 - 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, b8, 7, b9 + 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, b8 - 2, 8, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, b8 + 1, 8, b9, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, b8 - 1, 8, b9 - 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, b8 - 1, 8, b9 + 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, b8, 8, b9 - 1, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, b8, 8, b9 + 1, structureBoundingBox3);
			}

			this.createTreasureChestAtCurrentPosition(world1, structureBoundingBox3, random2, 3, 3, 5, treasureList, 1 + random2.nextInt(4));
			if(this.isLargeRoom) {
				this.placeBlockAtCurrentPosition(world1, 0, 0, 12, 9, 1, structureBoundingBox3);
				this.createTreasureChestAtCurrentPosition(world1, structureBoundingBox3, random2, 12, 8, 1, treasureList, 1 + random2.nextInt(4));
			}

			return true;
		}
	}
}
