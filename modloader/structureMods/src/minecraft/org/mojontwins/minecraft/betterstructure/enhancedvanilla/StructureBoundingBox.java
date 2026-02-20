package org.mojontwins.minecraft.betterstructure.enhancedvanilla;

public class StructureBoundingBox {
	public int minX;
	public int minY;
	public int minZ;
	public int maxX;
	public int maxY;
	public int maxZ;

	public StructureBoundingBox() {
	}

	public static StructureBoundingBox getNewBoundingBox() {
		return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	public static StructureBoundingBox getComponentToAddBoundingBox(int i0, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
		switch(i9) {
		case 0:
			return new StructureBoundingBox(i0 + i3, i1 + i4, i2 + i5, i0 + i6 - 1 + i3, i1 + i7 - 1 + i4, i2 + i8 - 1 + i5);
		case 1:
			return new StructureBoundingBox(i0 - i8 + 1 + i5, i1 + i4, i2 + i3, i0 + i5, i1 + i7 - 1 + i4, i2 + i6 - 1 + i3);
		case 2:
			return new StructureBoundingBox(i0 + i3, i1 + i4, i2 - i8 + 1 + i5, i0 + i6 - 1 + i3, i1 + i7 - 1 + i4, i2 + i5);
		case 3:
			return new StructureBoundingBox(i0 + i5, i1 + i4, i2 + i3, i0 + i8 - 1 + i5, i1 + i7 - 1 + i4, i2 + i6 - 1 + i3);
		default:
			return new StructureBoundingBox(i0 + i3, i1 + i4, i2 + i5, i0 + i6 - 1 + i3, i1 + i7 - 1 + i4, i2 + i8 - 1 + i5);
		}
	}

	public StructureBoundingBox(StructureBoundingBox structureBoundingBox1) {
		this.minX = structureBoundingBox1.minX;
		this.minY = structureBoundingBox1.minY;
		this.minZ = structureBoundingBox1.minZ;
		this.maxX = structureBoundingBox1.maxX;
		this.maxY = structureBoundingBox1.maxY;
		this.maxZ = structureBoundingBox1.maxZ;
	}

	public StructureBoundingBox(int i1, int i2, int i3, int i4, int i5, int i6) {
		this.minX = i1;
		this.minY = i2;
		this.minZ = i3;
		this.maxX = i4;
		this.maxY = i5;
		this.maxZ = i6;
	}

	public StructureBoundingBox(int i1, int i2, int i3, int i4) {
		this.minX = i1;
		this.minZ = i2;
		this.maxX = i3;
		this.maxZ = i4;
		this.minY = 1;
		this.maxY = 512;
	}

	public boolean intersectsWith(StructureBoundingBox structureBoundingBox1) {
		return this.maxX >= structureBoundingBox1.minX && this.minX <= structureBoundingBox1.maxX && this.maxZ >= structureBoundingBox1.minZ && this.minZ <= structureBoundingBox1.maxZ && this.maxY >= structureBoundingBox1.minY && this.minY <= structureBoundingBox1.maxY;
	}

	public boolean intersectsWith(int i1, int i2, int i3, int i4) {
		return this.maxX >= i1 && this.minX <= i3 && this.maxZ >= i2 && this.minZ <= i4;
	}

	public void expandTo(StructureBoundingBox structureBoundingBox1) {
		this.minX = Math.min(this.minX, structureBoundingBox1.minX);
		this.minY = Math.min(this.minY, structureBoundingBox1.minY);
		this.minZ = Math.min(this.minZ, structureBoundingBox1.minZ);
		this.maxX = Math.max(this.maxX, structureBoundingBox1.maxX);
		this.maxY = Math.max(this.maxY, structureBoundingBox1.maxY);
		this.maxZ = Math.max(this.maxZ, structureBoundingBox1.maxZ);
	}

	public void offset(int i1, int i2, int i3) {
		this.minX += i1;
		this.minY += i2;
		this.minZ += i3;
		this.maxX += i1;
		this.maxY += i2;
		this.maxZ += i3;
	}

	public boolean isVecInside(int i1, int i2, int i3) {
		return i1 >= this.minX && i1 <= this.maxX && i3 >= this.minZ && i3 <= this.maxZ && i2 >= this.minY && i2 <= this.maxY;
	}
	
	public boolean isVecInsideHorizontally(int i1, int i2, int i3) {
		return i1 >= this.minX && i1 <= this.maxX && i3 >= this.minZ && i3 <= this.maxZ && i2 >= 0 && i2 <= 127;
	}

	public int getXSize() {
		return this.maxX - this.minX + 1;
	}

	public int getYSize() {
		return this.maxY - this.minY + 1;
	}

	public int getZSize() {
		return this.maxZ - this.minZ + 1;
	}

	public int getCenterX() {
		return this.minX + (this.maxX - this.minX + 1) / 2;
	}

	public int getCenterY() {
		return this.minY + (this.maxY - this.minY + 1) / 2;
	}

	public int getCenterZ() {
		return this.minZ + (this.maxZ - this.minZ + 1) / 2;
	}

	public String toString() {
		return "(" + this.minX + ", " + this.minY + ", " + this.minZ + "; " + this.maxX + ", " + this.maxY + ", " + this.maxZ + ")";
	}
}
