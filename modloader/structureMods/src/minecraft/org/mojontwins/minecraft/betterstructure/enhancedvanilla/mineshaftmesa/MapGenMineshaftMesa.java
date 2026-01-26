package org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaftmesa;

import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.MapGenStructure;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureStart;

public class MapGenMineshaftMesa extends MapGenStructure {
	public MapGenMineshaftMesa(World world) {
		this.world = world;
	}
	
	@Override
	protected boolean canSpawnStructureAtCoords(World world, int cX, int cZ) {
		return this.rand.nextInt(500) == 0;
	}

	@Override
	protected StructureStart getStructureStart(int cX, int cZ) {
		return new StructureMineshaftMesaStart(this.world, this.rand, cX, cZ);
	}

}
