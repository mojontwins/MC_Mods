package org.mojontwins.minecraft.betterstructure.enhancedvanilla;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.ChunkPosition;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.MapGenBase;
import net.minecraft.src.World;

public abstract class MapGenStructure extends MapGenBase {
	protected World world;
	protected HashMap<Long,StructureStart> coordMap = new HashMap<Long,StructureStart>();

	@Override
	// func_867_a == generate
	/*
	public void generate(IChunkProvider iChunkProvider1, World world2, int i3, int i4, byte[] b5) {
		super.generate(iChunkProvider1, world2, i3, i4, b5);
	}
	*/
	public void func_867_a(IChunkProvider iChunkProvider1, World world2, int i3, int i4, byte[] b5) {
		super.func_867_a(iChunkProvider1, world2, i3, i4, b5);
	}

	@Override
	// func_868_a == recursiveGenerate
	//protected void recursiveGenerate(World world, int cX, int cZ, int chunkX, int chunkZ, byte[] blockArray) {
	protected void func_868_a(World world, int cX, int cZ, int chunkX, int chunkZ, byte[] blockArray) {
		// chunkX, chunkZ is the chunk being generated.
		// cX, cZ is the chunk being iterated from all chunks surrounding chunkX, chunkZ within a +-8 range.
		
		// First check if this chunk isn't already in the structure starts coordinate map.
		if(!this.coordMap.containsKey(ChunkCoordLongPair.chunkXZ2Long(cX, cZ))) {
			
			// It's not...
			this.rand.nextInt();
			
			// Can the structure spawn at cX, cZ?
			if(this.canSpawnStructureAtCoords(world, cX, cZ)) {
				
				// Generate a new structureStart at cX, cZ and add to the list.
				StructureStart structureStart = this.getStructureStart(cX, cZ);
				System.out.println ("Adding " + structureStart.getClass() + " @ " + (cX * 16) + " " + (cZ * 16));
				this.coordMap.put(ChunkCoordLongPair.chunkXZ2Long(cX, cZ), structureStart);
			}

		}
	}

	/*
	 * This method is called on population. If a StructureStart was generated within a +-8 range, draw the parts pertaining to this chunk
	 */
	public boolean generateStructuresInChunk(World world, Random rand, int chunkX, int chunkZ, boolean mostlySolid) {
		int x0 = (chunkX << 4) + 8;
		int z0 = (chunkZ << 4) + 8;
		boolean spawnedAny = false;
		
		Iterator<StructureStart> iterator = this.coordMap.values().iterator();

		while(iterator.hasNext()) {
			// Get one of the existing structures
			StructureStart structureStart = (StructureStart)iterator.next();
			
			// If parts of this structure overlap this chunk...
			if(structureStart.isSizeableStructure() && structureStart.getBoundingBox().intersectsWith(x0, z0, x0 + 15, z0 + 15)) {
				
				// Generate parts of this structure overlapping this chunk
				structureStart.generateStructure(world, rand, new StructureBoundingBox(x0, z0, x0 + 15, z0 + 15), mostlySolid);
				spawnedAny = true;
			}
		}

		return spawnedAny;
	}

	public boolean hasStructureAt(int i1, int i2, int i3) {
		Iterator<StructureStart> iterator4 = this.coordMap.values().iterator();

		while(true) {
			StructureStart structureStart5;
			do {
				do {
					if(!iterator4.hasNext()) {
						return false;
					}

					structureStart5 = (StructureStart)iterator4.next();
				} while(!structureStart5.isSizeableStructure());
			} while(!structureStart5.getBoundingBox().intersectsWith(i1, i3, i1, i3));

			Iterator<StructureComponent> iterator6 = structureStart5.getComponents().iterator();

			while(iterator6.hasNext()) {
				StructureComponent structureComponent7 = (StructureComponent)iterator6.next();
				if(structureComponent7.getBoundingBox().isVecInside(i1, i2, i3)) {
					return true;
				}
			}
		}
	}

	public ChunkPosition getNearestInstance(World world1, int i2, int i3, int i4) {
		
		this.rand.setSeed(world1.getRandomSeed());
		long j5 = this.rand.nextLong();
		long j7 = this.rand.nextLong();
		long j9 = (long)(i2 >> 4) * j5;
		long j11 = (long)(i4 >> 4) * j7;
		this.rand.setSeed(j9 ^ j11 ^ world1.getRandomSeed());
		//this.recursiveGenerate(world1, i2 >> 4, i4 >> 4, 0, 0, (byte[])null);
		this.func_868_a(world1, i2 >> 4, i4 >> 4, 0, 0, (byte[])null);
		double d13 = Double.MAX_VALUE;
		ChunkPosition chunkPosition15 = null;
		Iterator<StructureStart> iterator16 = this.coordMap.values().iterator();

		ChunkPosition chunkPosition19;
		int i20;
		int i21;
		int i22;
		double d23;
		while(iterator16.hasNext()) {
			StructureStart structureStart17 = (StructureStart)iterator16.next();
			if(structureStart17.isSizeableStructure()) {
				StructureComponent structureComponent18 = (StructureComponent)structureStart17.getComponents().get(0);
				chunkPosition19 = structureComponent18.getCenter();
				i20 = chunkPosition19.x - i2;
				i21 = chunkPosition19.y - i3;
				i22 = chunkPosition19.z - i4;
				d23 = (double)(i20 + i20 * i21 * i21 + i22 * i22);
				if(d23 < d13) {
					d13 = d23;
					chunkPosition15 = chunkPosition19;
				}
			}
		}

		if(chunkPosition15 != null) {
			return chunkPosition15;
		} else {
			List<ChunkPosition> list25 = this.func_40482_a();
			if(list25 != null) {
				ChunkPosition chunkPosition26 = null;
				Iterator<ChunkPosition> iterator27 = list25.iterator();

				while(iterator27.hasNext()) {
					chunkPosition19 = (ChunkPosition)iterator27.next();
					i20 = chunkPosition19.x - i2;
					i21 = chunkPosition19.y - i3;
					i22 = chunkPosition19.z - i4;
					d23 = (double)(i20 + i20 * i21 * i21 + i22 * i22);
					if(d23 < d13) {
						d13 = d23;
						chunkPosition26 = chunkPosition19;
					}
				}

				return chunkPosition26;
			} else {
				return null;
			}
		}
	}

	protected List<ChunkPosition> func_40482_a() {
		return null;
	}

	protected abstract boolean canSpawnStructureAtCoords(World world, int x, int z);

	protected abstract StructureStart getStructureStart(int i1, int i2);
}
