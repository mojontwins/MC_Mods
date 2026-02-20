package org.mojontwins.minecraft.betterstructure.enhancedvanilla.stronghold;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BiomeGenDesert;
import net.minecraft.src.BiomeGenForest;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.World;
import net.minecraft.src.mod_enhancedVanillaStructures;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.MapGenStructure;
import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureStart;

public class MapGenStronghold extends MapGenStructure {
	public static final int chance = 2048; // 8192; // 10240;
	public static final int minSeparation = 64;
	
	public static int bricksId, bricksMeta;
	public static int bricksAlt1Id, bricksAlt1Meta;
	public static int bricksAlt2Id, bricksAlt2Meta;
	public static int ironFenceId, ironFenceMeta;
	
	private List<ChunkCoordIntPair> previousStrongholds = new ArrayList<ChunkCoordIntPair> ();
	
	public MapGenStronghold(World world) {
		this.world = world;
		
		// Define those as you wish
		
		MapGenStronghold.bricksId = mod_enhancedVanillaStructures.blockStoneBricks.blockID;
		MapGenStronghold.bricksMeta = 0;
		MapGenStronghold.bricksAlt1Id = Block.cobblestoneMossy.blockID;
		MapGenStronghold.bricksAlt1Meta = 0;
		MapGenStronghold.bricksAlt2Id = Block.stone.blockID;
		MapGenStronghold.bricksAlt2Meta = 0;
		MapGenStronghold.ironFenceId = mod_enhancedVanillaStructures.blockFenceIron.blockID;
		MapGenStronghold.ironFenceMeta  = 0;
	}
	
	@Override
	protected boolean canSpawnStructureAtCoords(World world, int cX, int cZ) {
		// Original code only works with GenLayer based worlds, so I've made up this
		// which is more simple and should be easily adaptable to different projects
		
		// Seed must be consistent
		long seed = this.world.getRandomSeed() + cX * 25117 + cZ * 151121;
		
		// Get a consistent random number
		final Random rand = new Random(seed);
		
				
		boolean succeeded = rand.nextInt(MapGenStronghold.chance) == 0;
		if (succeeded)  {
			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt((cX << 4) + 8, (cZ << 4) + 8);
			
			boolean b = !(
				biome instanceof BiomeGenForest ||
				biome instanceof BiomeGenDesert
					);
					
		
			if(b) {
				// Let's check it's not to close to other Strongholds
				Iterator<ChunkCoordIntPair> it = this.previousStrongholds.iterator();
				while(it.hasNext()) {
					ChunkCoordIntPair coords = it.next();
					if(
							Math.abs(coords.chunkXPos - cX) < minSeparation ||
							Math.abs(coords.chunkZPos - cZ) < minSeparation
					) {
						return false;
					}
				}
			
				System.out.println ("Stronghold succeeded for " + (16 * cX) + " " + (16 * cZ));
				this.previousStrongholds.add(new ChunkCoordIntPair(cX, cZ));
				return true;
			}
		}
		
		return false;
	}

	@Override
	protected StructureStart getStructureStart(int cX, int cZ) {
		return new StructureStrongholdStart(this.world, this.rand, cX, cZ);
	}
}
