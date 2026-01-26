package org.mojontwins.minecraft.betterstructure.enhancedvanilla;

import net.minecraft.src.ChunkCoordIntPair;

public class ChunkCoordLongPair {
    public final int chunkXPos;
    public final int chunkZPos;

    public ChunkCoordLongPair(int var1, int var2) {
        this.chunkXPos = var1;
        this.chunkZPos = var2;
    }

    public static int chunkXZ2Int(int var0, int var1) {
        return (var0 < 0 ? Integer.MIN_VALUE : 0) | (var0 & 32767) << 16 | (var1 < 0 ? '\u8000' : 0) | var1 & 32767;
    }

    public int hashCode() {
        return chunkXZ2Int(this.chunkXPos, this.chunkZPos);
    }

    public boolean equals(Object var1) {
        ChunkCoordIntPair var2 = (ChunkCoordIntPair)var1;
        return var2.chunkXPos == this.chunkXPos && var2.chunkZPos == this.chunkZPos;
    }
    
    public static Long chunkXZ2Long(int i2, int i3) {
		long j2 = (long)i2;
		long j4 = (long)i3;
		return j2 & 4294967295L | (j4 & 4294967295L) << 32;
	}
}
