package org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaft;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.World;
import net.minecraft.src.mod_enhancedVanillaStructures;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class ComponentMineshaftCorridor extends StructureComponent {
	private final boolean hasRails;
	private final boolean hasSpiders;
	private boolean spawnerPlaced;
	private int sectionCount;

	public ComponentMineshaftCorridor(int i1, Random random2, StructureBoundingBox structureBoundingBox3, int i4) {
		super(i1);
		this.coordBaseMode = i4;
		this.boundingBox = structureBoundingBox3;
		this.hasRails = random2.nextInt(3) == 0;
		this.hasSpiders = !this.hasRails && random2.nextInt(15) == 0;
		if(this.coordBaseMode != 2 && this.coordBaseMode != 0) {
			this.sectionCount = structureBoundingBox3.getXSize() / 5;
		} else {
			this.sectionCount = structureBoundingBox3.getZSize() / 5;
		}

	}

	public static StructureBoundingBox findValidPlacement(List<StructureComponent> list0, Random random1, int i2, int i3, int i4, int i5) {
		StructureBoundingBox structureBoundingBox6 = new StructureBoundingBox(i2, i3, i4, i2, i3 + 2, i4);

		int i7;
		for(i7 = random1.nextInt(3) + 2; i7 > 0; --i7) {
			int i8 = i7 * 5;
			switch(i5) {
			case 0:
				structureBoundingBox6.maxX = i2 + 2;
				structureBoundingBox6.maxZ = i4 + (i8 - 1);
				break;
			case 1:
				structureBoundingBox6.minX = i2 - (i8 - 1);
				structureBoundingBox6.maxZ = i4 + 2;
				break;
			case 2:
				structureBoundingBox6.maxX = i2 + 2;
				structureBoundingBox6.minZ = i4 - (i8 - 1);
				break;
			case 3:
				structureBoundingBox6.maxX = i2 + (i8 - 1);
				structureBoundingBox6.maxZ = i4 + 2;
			}

			if(StructureComponent.findIntersecting(list0, structureBoundingBox6) == null) {
				break;
			}
		}

		return i7 > 0 ? structureBoundingBox6 : null;
	}

	public void buildComponent(StructureComponent structureComponent1, List<StructureComponent> list2, Random random3) {
		int i4 = this.getComponentType();
		int i5 = random3.nextInt(4);
		switch(this.coordBaseMode) {
		case 0:
			if(i5 <= 1) {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, i4);
			} else if(i5 == 2) {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.maxZ - 3, 1, i4);
			} else {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.maxZ - 3, 3, i4);
			}
			break;
		case 1:
			if(i5 <= 1) {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, i4);
			} else if(i5 == 2) {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.minZ - 1, 2, i4);
			} else {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.maxZ + 1, 0, i4);
			}
			break;
		case 2:
			if(i5 <= 1) {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, i4);
			} else if(i5 == 2) {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.minZ, 1, i4);
			} else {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.minZ, 3, i4);
			}
			break;
		case 3:
			if(i5 <= 1) {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, i4);
			} else if(i5 == 2) {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.minZ - 1, 2, i4);
			} else {
				StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + random3.nextInt(3), this.boundingBox.maxZ + 1, 0, i4);
			}
		}

		if(i4 < 8) {
			int i6;
			int i7;
			if(this.coordBaseMode != 2 && this.coordBaseMode != 0) {
				for(i6 = this.boundingBox.minX + 3; i6 + 3 <= this.boundingBox.maxX; i6 += 5) {
					i7 = random3.nextInt(5);
					if(i7 == 0) {
						StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, i6, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, i4 + 1);
					} else if(i7 == 1) {
						StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, i6, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, i4 + 1);
					}
				}
			} else {
				for(i6 = this.boundingBox.minZ + 3; i6 + 3 <= this.boundingBox.maxZ; i6 += 5) {
					i7 = random3.nextInt(5);
					if(i7 == 0) {
						StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.minX - 1, this.boundingBox.minY, i6, 1, i4 + 1);
					} else if(i7 == 1) {
						StructureMineshaftPieces.getNextComponent(structureComponent1, list2, random3, this.boundingBox.maxX + 1, this.boundingBox.minY, i6, 3, i4 + 1);
					}
				}
			}
		}

	}

	public boolean addComponentParts(World world1, Random random2, StructureBoundingBox structureBoundingBox3, boolean mostlySolid) {
		if(this.isLiquidInStructureBoundingBox(world1, structureBoundingBox3)) {
			return false;
		} else if(mostlySolid && !this.isMostlySolidInStructureBoundingbox(world1, structureBoundingBox3)) {
			return false;
		} else {
			int i8 = this.sectionCount * 5 - 1;
			this.fillWithBlocks(world1, structureBoundingBox3, 0, 0, 0, 2, 1, i8, 0, 0, false);
			this.fillWithBlocksWithRandomVariation(world1, structureBoundingBox3, 0, -1, 0, 2, 0, i8, Block.stone.blockID, Block.cobblestone.blockID, random2, 4, true);
			this.randomlyFillWithBlocks(world1, structureBoundingBox3, random2, 0.9F, 0, 2, 0, 2, 2, i8, 0, 0, false); // Was: 0.8F
			if(this.hasSpiders) {
				this.randomlyFillWithBlocks(world1, structureBoundingBox3, random2, 0.6F, 0, 0, 0, 2, 1, i8, Block.web.blockID, 0, false);
			}

			int i9;
			int i10;
			int i11;
			for(i9 = 0; i9 < this.sectionCount; ++i9) {
				i10 = 2 + i9 * 5;
								
				// Modification: Only add arch if needed and if possible (so no floating stuff
				if(getBlockIdAtCurrentPositionNoVertClip(world1, 1, 3, i10, structureBoundingBox3) != 0) {
					if(
							this.getBlockIdAtCurrentPositionNoVertClip(world1, 0, -1, i10, structureBoundingBox3) != 0 &&
							this.getBlockIdAtCurrentPositionNoVertClip(world1, 2, -1, i10, structureBoundingBox3) != 0
					) {
						this.fillWithBlocks(world1, structureBoundingBox3, 0, 0, i10, 0, 1, i10, Block.fence.blockID, 0, false);
						this.fillWithBlocks(world1, structureBoundingBox3, 2, 0, i10, 2, 1, i10, Block.fence.blockID, 0, false);
						
						this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 0, 2, i10, structureBoundingBox3);
						this.placeBlockAtCurrentPosition(world1, Block.stairSingle.blockID, 2|8, 1, 2, i10, structureBoundingBox3);
						this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, 2, 2, i10, structureBoundingBox3);
					}
				} else {
					// blockChains if needed / possible
					if(
							this.getBlockIdAtCurrentPositionNoVertClip(world1, 0, -1, i10, structureBoundingBox3) != 0 &&
							!this.canBlockSeeTheSky(world1, 0, 1, i10, structureBoundingBox3)
					) {
						this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, 0, 0, i10, structureBoundingBox3);
						int y = 1;
						while(this.getBlockIdAtCurrentPositionNoVertClip(world1, 0, y, i10, structureBoundingBox3) == 0) {
							this.placeBlockAtCurrentPositionNoVertClip(world1, mod_enhancedVanillaStructures.blockChain.blockID, 0, 0, y, i10, structureBoundingBox3);
							y ++;
						}
					}
						
					if(
							this.getBlockIdAtCurrentPositionNoVertClip(world1, 2, -1, i10, structureBoundingBox3) != 0 &&
							!this.canBlockSeeTheSky(world1, 2, 1, i10, structureBoundingBox3)
					) {
						this.placeBlockAtCurrentPosition(world1, Block.fence.blockID, 0, 2, 0, i10, structureBoundingBox3);
						int y = 1;
						while(this.getBlockIdAtCurrentPositionNoVertClip(world1, 2, y, i10, structureBoundingBox3) == 0) {
							this.placeBlockAtCurrentPositionNoVertClip(world1, mod_enhancedVanillaStructures.blockChain.blockID, 0, 2, y, i10, structureBoundingBox3);
							y ++;
						}
					}
				}

				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.1F, 0, 2, i10 - 1, Block.web.blockID, 0);
				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.1F, 2, 2, i10 - 1, Block.web.blockID, 0);
				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.1F, 0, 2, i10 + 1, Block.web.blockID, 0);
				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.1F, 2, 2, i10 + 1, Block.web.blockID, 0);
				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.05F, 0, 2, i10 - 2, Block.web.blockID, 0);
				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.05F, 2, 2, i10 - 2, Block.web.blockID, 0);
				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.05F, 0, 2, i10 + 2, Block.web.blockID, 0);
				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.05F, 2, 2, i10 + 2, Block.web.blockID, 0);
				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.05F, 1, 2, i10 - 1, Block.torchWood.blockID, 0);
				this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.05F, 1, 2, i10 + 1, Block.torchWood.blockID, 0);
				if(random2.nextInt(100) == 0) {
					this.createTreasureChestAtCurrentPosition(world1, structureBoundingBox3, random2, 2, 0, i10 - 1, StructureMineshaftPieces.getTreasurePieces(), 3 + random2.nextInt(4));
				}

				if(random2.nextInt(100) == 0) {
					this.createTreasureChestAtCurrentPosition(world1, structureBoundingBox3, random2, 0, 0, i10 + 1, StructureMineshaftPieces.getTreasurePieces(), 3 + random2.nextInt(4));
				}

				if(this.hasSpiders && !this.spawnerPlaced) {
					i11 = this.getYWithOffset(0);
					int i12 = i10 - 1 + random2.nextInt(3);
					int i13 = this.getXWithOffset(1, i12);
					i12 = this.getZWithOffset(1, i12);
					if(structureBoundingBox3.isVecInside(i13, i11, i12)) {
						// System.out.println ("Shaft spawner @ " + i13 + " " + i11 + " " + i12);
						
						this.spawnerPlaced = true;
						world1.setBlockWithNotify(i13, i11, i12, Block.mobSpawner.blockID);
						TileEntityMobSpawner tileEntityMobSpawner14 = (TileEntityMobSpawner)world1.getBlockTileEntity(i13, i11, i12);
						if(tileEntityMobSpawner14 != null) {
							tileEntityMobSpawner14.setMobID("Spider");
						}
						
						for(int y = -1; y <= 1; y ++) {
							for(int x = -4; x <= 4; x ++) {
								for(int z = -4; z <= 4; z ++) {
									int xx = i13 + x; int yy = i11 + y; int zz = i12 + z;
									if(world1.getBlockId(xx, yy, zz) == 0) {
										world1.setBlockWithNotify(xx, yy, zz, Block.web.blockID);
									}
								}
							}
						}
					}
				}
			}

			for(i9 = 0; i9 <= 2; ++i9) {
				for(i10 = 0; i10 <= i8; ++i10) {
					i11 = this.getBlockIdAtCurrentPosition(world1, i9, -1, i10, structureBoundingBox3);
					if(i11 == 0) {
						this.placeBlockAtCurrentPosition(world1, Block.planks.blockID, 0, i9, -1, i10, structureBoundingBox3);
					}
				}
			}

			if(this.hasRails) {
				for(i9 = 0; i9 <= i8; ++i9) {
					i10 = this.getBlockIdAtCurrentPosition(world1, 1, -1, i9, structureBoundingBox3);
					if(i10 > 0 && Block.opaqueCubeLookup[i10]) {
						this.randomlyPlaceBlock(world1, structureBoundingBox3, random2, 0.7F, 1, 0, i9, Block.minecartTrack.blockID, this.getMetadataWithOffset(Block.minecartTrack.blockID, 0));
					}
				}
			}

			return true;
		}
	}
}
