package org.mojontwins.minecraft.betterstructureapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Chunk;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.World;

public class ChunkProviderGenerateProxy implements IChunkProvider {
	IChunkProvider originalProvider = null;
	private World worldObj = null;
	Random rand = new Random();
	
	private boolean debug = false;
	
	public static List<IBetterStructureGenerator> structureGenerators = new ArrayList<IBetterStructureGenerator>();
		
	public ChunkProviderGenerateProxy(IChunkProvider originalProvider, World world) {
		this.originalProvider = originalProvider;
		this.worldObj  = world;
	}
	
	public static void registerStructureGenerator(IBetterStructureGenerator generator) {
		structureGenerators.add(generator);
		System.out.println ("Registered structure generator " + generator.getClass());
	}

	@Override
	public boolean chunkExists(int chunkX, int chunkZ) {
		return this.originalProvider.chunkExists(chunkX, chunkZ);
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ) {
		Chunk chunk = this.originalProvider.provideChunk(chunkX, chunkZ);
		
		// Custom generation
		for(IBetterStructureGenerator structureGenerator : structureGenerators) {
			if(debug) System.out.println (structureGenerator.getClass() + " generating.");
			if(!structureGenerator.generate(this.originalProvider, chunk.worldObj, rand, chunk, chunkX, chunkZ)) break;
		}
		
		return chunk;
	}

	@Override
	public Chunk loadChunk(int chunkX, int chunkZ) {
		return this.originalProvider.loadChunk(chunkX, chunkZ);
	}

	@Override
	public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
		// Custom population
		boolean keepPopulating = true;
		BiomeGenBase biome = this.worldObj.getWorldChunkManager().getBiomeGenAt((chunkX << 4) + 16, (chunkZ << 4) + 16);
		this.rand.setSeed(this.worldObj.getRandomSeed());
        long l1 = this.rand.nextLong() / 2L * 2L + 1L;
        long l2 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)chunkX * l1 + (long)chunkZ * l2 ^ this.worldObj.getRandomSeed());
		
		for(IBetterStructureGenerator structureGenerator : structureGenerators) {
			if(debug) System.out.println (structureGenerator.getClass() + " populating.");
			keepPopulating = structureGenerator.populate(provider, this.worldObj, this.rand, biome, chunkX, chunkZ);
			if(!keepPopulating) break;
		}
		
		if (keepPopulating) this.originalProvider.populate(provider, chunkX, chunkZ);
	}

	@Override
	public boolean saveChunks(boolean z, IProgressUpdate progressUpdate) {
		return this.originalProvider.saveChunks(z, progressUpdate);
	}

	@Override
	public boolean func_361_a() {
		return this.originalProvider.func_361_a();
	}

	@Override
	public boolean func_364_b() {
		return this.originalProvider.func_364_b();
	}

}
