package org.mojontwins.minecraft.betterstructureapi;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Chunk;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;

public interface IBetterStructureGenerator {
	/*
	 * Must return true. Returning false will break the custom generation.
	 */
	public boolean generate(IChunkProvider provider, World world, Random rand, Chunk chunk, int chunkX, int chunkZ);
	
	/*
	 * Must return true. Returning false will break the custom population *and* vanilla population!
	 */
	public boolean populate(IChunkProvider provider, World world, Random rand, BiomeGenBase biome, int chunkX, int chunkZ);
}
