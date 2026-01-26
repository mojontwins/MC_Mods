package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructurePieceTreasure;

public class ComponentStrongholdChestCorridor extends ComponentStronghold {
	private static final StructurePieceTreasure[] chestLoot = new StructurePieceTreasure[] {
			new StructurePieceTreasure(Block.obsidian.blockID, 0, 1, 1, 10),
			new StructurePieceTreasure(Item.diamond.shiftedIndex, 0, 1, 3, 3),
			//new StructurePieceTreasure(Item.nametagSimple.shiftedIndex, 0, 1, 1, 3),
			new StructurePieceTreasure(Item.ingotIron.shiftedIndex, 0, 1, 5, 10),
			new StructurePieceTreasure(Item.ingotGold.shiftedIndex, 0, 1, 3, 5),
			new StructurePieceTreasure(Item.redstone.shiftedIndex, 0, 4, 9, 5),
			new StructurePieceTreasure(Item.bread.shiftedIndex, 0, 1, 3, 15),
			new StructurePieceTreasure(Item.appleRed.shiftedIndex, 0, 1, 3, 15),
			new StructurePieceTreasure(Item.pickaxeSteel.shiftedIndex, 0, 1, 1, 5),
			new StructurePieceTreasure(Item.swordSteel.shiftedIndex, 0, 1, 1, 5),
			new StructurePieceTreasure(Item.plateSteel.shiftedIndex, 0, 1, 1, 5),
			new StructurePieceTreasure(Item.helmetSteel.shiftedIndex, 0, 1, 1, 5),
			new StructurePieceTreasure(Item.legsSteel.shiftedIndex, 0, 1, 1, 5),
			new StructurePieceTreasure(Item.bootsSteel.shiftedIndex, 0, 1, 1, 5),
			new StructurePieceTreasure(Item.appleGold.shiftedIndex, 0, 1, 1, 1) };
	private final EnumDoor doorType;
	private boolean hasMadeChest;

	public ComponentStrongholdChestCorridor(int i1, Random random2, StructureBoundingBox structureBoundingBox3,
			int i4) {
		super(i1);
		this.coordBaseMode = i4;
		this.doorType = this.getRandomDoor(random2);
		this.boundingBox = structureBoundingBox3;
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		this.getNextComponentNormal((ComponentStrongholdStairs2) structureComponent1, list2, random3, 1, 1);
	}

	public static ComponentStrongholdChestCorridor findValidPlacement(List<StructureComponent> list0, Random random1,
			int i2, int i3, int i4, int i5, int i6) {
		StructureBoundingBox structureBoundingBox7 = StructureBoundingBox.getComponentToAddBoundingBox(i2, i3, i4, -1,
				-1, 0, 5, 5, 7, i5);
		return canStrongholdGoDeeper(structureBoundingBox7)
				&& StructureComponent.findIntersecting(list0, structureBoundingBox7) == null
						? new ComponentStrongholdChestCorridor(i6, random1, structureBoundingBox7, i5)
						: null;
	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3,
			boolean mostlySolid) {
		if (this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else {
			this.fillWithRandomizedBlocks(world1, structureBoundingBox3, 0, 0, 0, 4, 4, 6, true, random2, StructureStrongholdPieces.getStrongholdStones());
			this.placeDoor(world1, random2, structureBoundingBox3, this.doorType, 1, 1, 0);
			this.placeDoor(world1, random2, structureBoundingBox3, EnumDoor.OPENING, 1, 1, 6);
			this.fillWithBlocks(world1, structureBoundingBox3, 3, 1, 2, 3, 1, 4, MapGenStronghold.bricksId,	MapGenStronghold.bricksId, false);
			this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 5, 3, 1, 1, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 5, 3, 1, 5, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 5, 3, 2, 2, structureBoundingBox3);
			this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 5, 3, 2, 4, structureBoundingBox3);

			int i4;
			for (i4 = 2; i4 <= 4; ++i4) {
				this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 5, 2, 1, i4, structureBoundingBox3);
			}

			if (!this.hasMadeChest) {
				i4 = this.getYWithOffset(2);
				int i5 = this.getXWithOffset(3, 3);
				int i6 = this.getZWithOffset(3, 3);
				if (structureBoundingBox3.isVecInside(i5, i4, i6)) {
					this.hasMadeChest = true;
					this.createTreasureChestAtCurrentPosition(world1, structureBoundingBox3, random2, 3, 2, 3,
							chestLoot, 2 + random2.nextInt(2));
				}
			}

			return true;
		}
	}
}
