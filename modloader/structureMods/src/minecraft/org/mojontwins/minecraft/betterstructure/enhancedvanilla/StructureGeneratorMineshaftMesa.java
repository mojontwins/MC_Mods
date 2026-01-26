package org.mojontwins.minecraft.betterstructure.enhancedvanilla;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkProviderHell;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaftmesa.MapGenMineshaftMesa;
import org.mojontwins.minecraft.betterstructureapi.IBetterStructureGenerator;

public class StructureGeneratorMineshaftMesa implements
		IBetterStructureGenerator {
	protected MapGenMineshaftMesa mineshaftGenerator;
	private World worldObj = null;
		
	@Override
	public boolean generate(IChunkProvider provider, World world, Random rand, Chunk chunk, int chunkX, int chunkZ) {
		if(provider instanceof ChunkProviderHell) return true;
		
		if(world != this.worldObj || this.mineshaftGenerator == null) {
			this.worldObj = world;
			this.mineshaftGenerator = new MapGenMineshaftMesa(this.worldObj);
		}
		
		//this.mineshaftGenerator.generate(provider, this.worldObj, chunkX, chunkZ, chunk.blocks);
		this.mineshaftGenerator.func_867_a(provider, this.worldObj, chunkX, chunkZ, chunk.blocks);
		
		return true;
	}

	@Override
	public boolean populate(IChunkProvider provider, World world, Random rand,
			BiomeGenBase biome, int chunkX, int chunkZ) {

		this.mineshaftGenerator.generateStructuresInChunk(world, rand, chunkX, chunkZ, true);
		return true;
	}

}
