package org.mojontwins.minecraft.betterstructure.enhancedvanilla;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaftmesa.MapGenMineshaftMesa;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold.MapGenStronghold;
import org.mojontwins.minecraft.betterstructureapi.IBetterStructureGenerator;

public class StructureGeneratorStronghold implements IBetterStructureGenerator {

	protected MapGenStronghold strongholdGenerator;
	private World worldObj = null;
		
	@Override
	public boolean generate(IChunkProvider provider, World world, Random rand, Chunk chunk, int chunkX, int chunkZ) {
		if(!(provider instanceof ChunkProviderGenerate)) return true;
		
		if(world != this.worldObj || this.strongholdGenerator == null) {
			this.worldObj = world;
			this.strongholdGenerator = new MapGenStronghold(this.worldObj);
		}
		
		this.strongholdGenerator.func_667_a(provider, this.worldObj, chunkX, chunkZ, chunk.blocks);
		
		return true;
	}

	@Override
	public boolean populate(IChunkProvider provider, World world, Random rand,
			BiomeGenBase biome, int chunkX, int chunkZ) {

		this.strongholdGenerator.generateStructuresInChunk(world, rand, chunkX, chunkZ, true);
		return true;
	}
}
