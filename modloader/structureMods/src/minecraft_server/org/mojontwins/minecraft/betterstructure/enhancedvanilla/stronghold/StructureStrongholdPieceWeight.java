package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

class StructureStrongholdPieceWeight {
	public Class<?> pieceClass;
	public final int pieceWeight;
	public int instancesSpawned;
	public int instancesLimit;

	public StructureStrongholdPieceWeight(Class<?> pieceClass, int pieceWeight, int instancesLimit) {
		this.pieceClass = pieceClass;
		this.pieceWeight = pieceWeight;
		this.instancesLimit = instancesLimit;
	}

	public boolean canSpawnMoreStructuresOfType(int i1) {
		return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
	}

	public boolean canSpawnMoreStructures() {
		return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
	}
}
