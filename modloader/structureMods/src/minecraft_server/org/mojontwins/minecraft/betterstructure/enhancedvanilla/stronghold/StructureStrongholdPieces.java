package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureBoundingBox;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureComponent;

public class StructureStrongholdPieces {
	/* Modified so there's a different set of pieces for the surface */
	private static final StructureStrongholdPieceWeight[] pieceWeightArray = new StructureStrongholdPieceWeight[] {
			new StructureStrongholdPieceWeight(ComponentStrongholdStraight.class, 40, 0), 
			new StructureStrongholdPieceWeight(ComponentStrongholdPrison.class, 5, 5), 
			new StructureStrongholdPieceWeight(ComponentStrongholdLeftTurn.class, 20, 0), 
			new StructureStrongholdPieceWeight(ComponentStrongholdRightTurn.class, 20, 0), 
			new StructureStrongholdPieceWeight(ComponentStrongholdRoomCrossing.class, 10, 20), //6), 
			new StructureStrongholdPieceWeight(ComponentStrongholdStairsStraight.class, 5, 25), //5), 
			new StructureStrongholdPieceWeight(ComponentStrongholdStairs.class, 10, 25), // 5), 
			new StructureStrongholdPieceWeight(ComponentStrongholdCrossing.class, 10, 20), // 4), 
			new StructureStrongholdPieceWeight(ComponentStrongholdChestCorridor.class, 5, 20), //4), 
			new StructureStrongholdPieceWeight2(ComponentStrongholdLibrary.class, 10, 10)}; //2)};

	private static final Class<?>[] surfaceAllowedComponents = new Class<?>[] {
		ComponentStrongholdStraight.class,
		ComponentStrongholdLeftTurn.class,
		ComponentStrongholdRightTurn.class,
		ComponentStrongholdCrossing.class,
		ComponentStrongholdStairsStraight.class		
	};
	
	private static List<StructureStrongholdPieceWeight> structurePieceList;
	private static Class<?> strongComponentType;
	static int totalWeight = 0;
	private static final StructureStrongholdStones strongholdStones = new StructureStrongholdStones((StructureStrongholdPieceWeight2)null);

	public static void prepareStructurePieces() {
		structurePieceList = new ArrayList<StructureStrongholdPieceWeight>();
		StructureStrongholdPieceWeight[] structureStrongholdPieceWeight0 = pieceWeightArray;
		
		int i1 = structureStrongholdPieceWeight0.length;

		for(int i2 = 0; i2 < i1; ++i2) {
			StructureStrongholdPieceWeight structureStrongholdPieceWeight3 = structureStrongholdPieceWeight0[i2];
			structureStrongholdPieceWeight3.instancesSpawned = 0;
			structurePieceList.add(structureStrongholdPieceWeight3);
		}

		strongComponentType = null;
	}

	private static boolean canAddStructurePieces() {
		boolean z0 = false;
		totalWeight = 0;

		StructureStrongholdPieceWeight structureStrongholdPieceWeight2;
		for(Iterator<StructureStrongholdPieceWeight> iterator1 = structurePieceList.iterator(); iterator1.hasNext(); totalWeight += structureStrongholdPieceWeight2.pieceWeight) {
			structureStrongholdPieceWeight2 = (StructureStrongholdPieceWeight)iterator1.next();
			if(structureStrongholdPieceWeight2.instancesLimit > 0 && structureStrongholdPieceWeight2.instancesSpawned < structureStrongholdPieceWeight2.instancesLimit) {
				z0 = true;
			}
		}

		return z0;
	}

	private static ComponentStronghold getStrongholdComponentFromWeightedPiece(Class<?> class0, List<StructureComponent> list1, Random random2, int i3, int i4, int i5, int i6, int i7) {
		Object object8 = null;
		if(class0 == ComponentStrongholdStraight.class) {
			object8 = ComponentStrongholdStraight.findValidPlacement(list1, random2, i3, i4, i5, i6, i7);
		} else if(class0 == ComponentStrongholdPrison.class) {
			object8 = ComponentStrongholdPrison.findValidPlacement(list1, random2, i3, i4, i5, i6, i7);
		} else if(class0 == ComponentStrongholdLeftTurn.class) {
			object8 = ComponentStrongholdLeftTurn.findValidPlacement(list1, random2, i3, i4, i5, i6, i7);
		} else if(class0 == ComponentStrongholdRightTurn.class) {
			object8 = ComponentStrongholdRightTurn.findValidPlacement(list1, random2, i3, i4, i5, i6, i7);
		} else if(class0 == ComponentStrongholdRoomCrossing.class) {
			object8 = ComponentStrongholdRoomCrossing.findValidPlacement(list1, random2, i3, i4, i5, i6, i7);
		} else if(class0 == ComponentStrongholdStairsStraight.class) {
			object8 = ComponentStrongholdStairsStraight.findValidPlacement(list1, random2, i3, i4, i5, i6, i7);
		} else if(class0 == ComponentStrongholdStairs.class) {
			object8 = ComponentStrongholdStairs.getStrongholdStairsComponent(list1, random2, i3, i4, i5, i6, i7);
		} else if(class0 == ComponentStrongholdCrossing.class) {
			object8 = ComponentStrongholdCrossing.findValidPlacement(list1, random2, i3, i4, i5, i6, i7);
		} else if(class0 == ComponentStrongholdChestCorridor.class) {
			object8 = ComponentStrongholdChestCorridor.findValidPlacement(list1, random2, i3, i4, i5, i6, i7);
		} else if(class0 == ComponentStrongholdLibrary.class) {
			object8 = ComponentStrongholdLibrary.findValidPlacement(list1, random2, i3, i4, i5, i6, i7);
		} 

		return (ComponentStronghold)object8;
	}

	private static ComponentStronghold getNextComponent(ComponentStrongholdStairs2 componentStrongholdStairs20, List<StructureComponent> list1, Random random2, int i3, int i4, int i5, int i6, int i7) {
		if(!canAddStructurePieces()) {
			return null;
		} else {
			if(strongComponentType != null) {
				ComponentStronghold componentStronghold8 = getStrongholdComponentFromWeightedPiece(strongComponentType, list1, random2, i3, i4, i5, i6, i7);
				strongComponentType = null;
				if(componentStronghold8 != null) {
					return componentStronghold8;
				}
			}

			int i13 = 0;

			while(i13 < 5) {
				++i13;
				int i9 = random2.nextInt(totalWeight);
				Iterator<StructureStrongholdPieceWeight> iterator10 = structurePieceList.iterator();

				while(iterator10.hasNext()) {
					StructureStrongholdPieceWeight structureStrongholdPieceWeight11 = (StructureStrongholdPieceWeight)iterator10.next();
					i9 -= structureStrongholdPieceWeight11.pieceWeight;
					if(i9 < 0) {
						if(!structureStrongholdPieceWeight11.canSpawnMoreStructuresOfType(i7) || structureStrongholdPieceWeight11 == componentStrongholdStairs20.field_35038_a) {
							break;
						}
						
						// check if next to surface to discard certain types

						ComponentStronghold componentStronghold12 = getStrongholdComponentFromWeightedPiece(structureStrongholdPieceWeight11.pieceClass, list1, random2, i3, i4, i5, i6, i7);
						
						if(componentStronghold12 != null) {
							
							if(i3 >= 64 && !allowedOnSurface(componentStronghold12)) {
								
							} else {
								
								++structureStrongholdPieceWeight11.instancesSpawned;
								componentStrongholdStairs20.field_35038_a = structureStrongholdPieceWeight11;
								if(!structureStrongholdPieceWeight11.canSpawnMoreStructures()) {
									structurePieceList.remove(structureStrongholdPieceWeight11);
								}
	
								return componentStronghold12;
							}
						}
					}
				}
			}

			StructureBoundingBox structureBoundingBox14 = ComponentStrongholdCorridor.func_35051_a(list1, random2, i3, i4, i5, i6);
			if(structureBoundingBox14 != null && structureBoundingBox14.minY > 1) {
				return new ComponentStrongholdCorridor(i7, random2, structureBoundingBox14, i6);
			} else {
				return null;
			}
		}
	}

	private static boolean allowedOnSurface(ComponentStronghold componentStronghold) {		
		return Arrays.asList(surfaceAllowedComponents).contains(componentStronghold.getClass());
	}

	private static StructureComponent getNextValidComponent(ComponentStrongholdStairs2 componentStrongholdStairs20, List<StructureComponent> list1, Random random2, int i3, int i4, int i5, int i6, int i7) {
		if(i7 > 50) {
			return null;
		} else if(Math.abs(i3 - componentStrongholdStairs20.getBoundingBox().minX) <= 112 && Math.abs(i5 - componentStrongholdStairs20.getBoundingBox().minZ) <= 112) {
			ComponentStronghold componentStronghold8 = getNextComponent(componentStrongholdStairs20, list1, random2, i3, i4, i5, i6, i7 + 1);
			if(componentStronghold8 != null) {
				list1.add(componentStronghold8);
				componentStrongholdStairs20.field_35037_b.add(componentStronghold8);
			}

			return componentStronghold8;
		} else {
			return null;
		}
	}

	static StructureComponent getNextValidComponentAccess(ComponentStrongholdStairs2 componentStrongholdStairs20, List<StructureComponent> list1, Random random2, int i3, int i4, int i5, int i6, int i7) {
		return getNextValidComponent(componentStrongholdStairs20, list1, random2, i3, i4, i5, i6, i7);
	}

	static Class<?> setComponentType(Class<?> class0) {
		strongComponentType = class0;
		return class0;
	}

	static StructureStrongholdStones getStrongholdStones() {
		return strongholdStones;
	}
}
