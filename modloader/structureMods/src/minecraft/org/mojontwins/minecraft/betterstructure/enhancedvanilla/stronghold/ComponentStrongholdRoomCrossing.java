package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructurePieceTreasure;

public class ComponentStrongholdRoomCrossing extends ComponentStronghold {
	private static final StructurePieceTreasure[] chestLoot = new StructurePieceTreasure[]{new StructurePieceTreasure(Item.ingotIron.shiftedIndex, 0, 1, 5, 10), new StructurePieceTreasure(Item.ingotGold.shiftedIndex, 0, 1, 3, 5), new StructurePieceTreasure(Item.redstone.shiftedIndex, 0, 4, 9, 5), new StructurePieceTreasure(Item.coal.shiftedIndex, 0, 3, 8, 10), new StructurePieceTreasure(Item.bread.shiftedIndex, 0, 1, 3, 15), new StructurePieceTreasure(Item.appleRed.shiftedIndex, 0, 1, 3, 15), new StructurePieceTreasure(Item.pickaxeSteel.shiftedIndex, 0, 1, 1, 1)};
	protected final EnumDoor doorType;
	protected final int roomType;

	public ComponentStrongholdRoomCrossing(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.coordBaseMode = i4;
		this.doorType = this.getRandomDoor(random2);
		this.boundingBox = structureBoundingBox3;
		this.roomType = random2.nextInt(5);
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		this.getNextComponentNormal((ComponentStrongholdStairs2)structureComponent1, list2, random3, 4, 1);
		this.getNextComponentX((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 4);
		this.getNextComponentZ((ComponentStrongholdStairs2)structureComponent1, list2, random3, 1, 4);
	}

	public static ComponentStrongholdRoomCrossing findValidPlacement(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5, int i6) {
		StructureBoundingBox structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -4, -1, 0, 11, 7, 11, i5);
		return canStrongholdGoDeeper(structureBoundingBox7) && StructureComponent.findIntersecting(list0, structureBoundingBox7) == null ? new ComponentStrongholdRoomCrossing(i6, random1, structureBoundingBox7, i5) : null;
	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if(this.getAirRatioInStructureBoundingBox(world1, structureBoundingBox3) > 0.2F) {
			return false;
		} else {
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 0, 0, 0, 10, 6, 10, true, random2, StructureStrongholdPieces.getStrongholdStones());
			this.placeDoor(world1, random2, structureBoundingBox3, this.doorType, 4, 1, 0);
			this.fillWithBlocks(world1, structureBoundingBox3, 4, 1, 10, 6, 3, 10, 0, 0, false);
			this.fillWithBlocks(world1, structureBoundingBox3, 0, 1, 4, 0, 3, 6, 0, 0, false);
			this.fillWithBlocks(world1, structureBoundingBox3, 10, 1, 4, 10, 3, 6, 0, 0, false);
			int i4;
			switch(this.roomType) {
			case 0:
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 5, 1, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 5, 2, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 5, 3, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, 4, 3, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, 6, 3, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, 5, 3, 4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, 5, 3, 6, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 4, 1, 4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 4, 1, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 4, 1, 6, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 6, 1, 4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 6, 1, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 6, 1, 6, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 5, 1, 4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 0, 5, 1, 6, structureBoundingBox3);
				break;
			case 1:
				for(i4 = 0; i4 < 5; ++i4) {
					this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 3, 1, 3 + i4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 7, 1, 3 + i4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 3 + i4, 1, 3, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 3 + i4, 1, 7, structureBoundingBox3);
				}

				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 5, 1, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 5, 2, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, MapGenStronghold.bricksId, MapGenStronghold.bricksMeta, 5, 3, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.waterMoving.blockID, 0, 5, 4, 5, structureBoundingBox3);
				break;
			case 2:
				for(i4 = 1; i4 <= 9; ++i4) {
					this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 1, 3, i4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 9, 3, i4, structureBoundingBox3);
				}

				for(i4 = 1; i4 <= 9; ++i4) {
					this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, i4, 3, 1, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, i4, 3, 9, structureBoundingBox3);
				}

				this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 5, 1, 4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 5, 1, 6, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 5, 3, 4, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 5, 3, 6, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 4, 1, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 6, 1, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 4, 3, 5, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 6, 3, 5, structureBoundingBox3);

				for(i4 = 1; i4 <= 3; ++i4) {
					this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 4, i4, 4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 6, i4, 4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 4, i4, 6, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, Block.cobblestone.blockID, 0, 6, i4, 6, structureBoundingBox3);
				}

				this.placeBlockAtCurrentPosition(world1, Block.torchWood.blockID, 0, 5, 3, 5, structureBoundingBox3);

				for(i4 = 2; i4 <= 8; ++i4) {
					this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 2, 3, i4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 3, 3, i4, structureBoundingBox3);
					if(i4 <= 3 || i4 >= 7) {
						this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 4, 3, i4, structureBoundingBox3);
						this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 5, 3, i4, structureBoundingBox3);
						this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 6, 3, i4, structureBoundingBox3);
					}

					this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 7, 3, i4, structureBoundingBox3);
					this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 8, 3, i4, structureBoundingBox3);
				}

				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, this.getMetadataWithOffset(Block.ladder.blockID, 4), 9, 1, 3, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, this.getMetadataWithOffset(Block.ladder.blockID, 4), 9, 2, 3, structureBoundingBox3);
				this.placeBlockAtCurrentPosition(world1, Block.ladder.blockID, this.getMetadataWithOffset(Block.ladder.blockID, 4), 9, 3, 3, structureBoundingBox3);
				this.createTreasureChestAtCurrentPosition(world1, structureBoundingBox3, random2, 3, 4, 8, chestLoot, 1 + random2.nextInt(4));
			}

			return true;
		}
	}
}
