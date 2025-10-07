package org.mojontwins.minecraft.fungalcalamity;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockMushroomCap extends Block {
	/** The mushroom type. 0 for brown, 1 for red. */
	private int mushroomType;
	
	public int texCap;
	public int texStem;
	public int texInner;

	public BlockMushroomCap(int id, Material material, int mushroomType,
			int texCap, int texStem, int texInner
	) {
		super(id, 0, material);
		this.mushroomType = mushroomType;
		this.texCap = texCap;
		this.texStem = texStem;
		this.texInner = texInner;
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		return meta == 10 && side > 1 ? this.blockIndexInTexture - 1
				: (meta >= 1 && meta <= 9 && side == 1 ? this.texCap
						: (meta >= 1 && meta <= 3 && side == 2 ? this.texCap
								: (meta >= 7 && meta <= 9 && side == 3 ? this.texCap
										: ((meta == 1 || meta == 4 || meta == 7) && side == 4 ? this.texCap
												: ((meta == 3 || meta == 6 || meta == 9) && side == 5 ? this.texCap
														: (meta == 14 ? this.texCap
																: (meta == 15 ? this.texStem
																		: this.texInner)))))));
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random rand) {
		int chance = rand.nextInt(10) - 7;

		if (chance < 0) {
			chance = 0;
		}

		return chance;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int meta, Random rand) {
		return Block.mushroomBrown.blockID + this.mushroomType;
	}
}
