package org.mojontwins.minecraft.betterbooks;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemDye;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.mod_betterBooks;

import org.lwjgl.opengl.GL11;

public class RenderBlockBetterBookshelf {
	public static boolean renderWorldBlock(RenderBlocks rb, IBlockAccess world, int x, int y, int z, Block block) {
		switch(world.getBlockMetadata(x, y, z)) {
		case 2:
			// Facing NORTH
			// Subblock bottom
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock center
			block.setBlockBounds(0.0625F, 0.4375F, 0.0F, 0.9375F, 0.5625F, 0.9375F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock top
			block.setBlockBounds(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock RIGHT
			block.setBlockBounds(0.0F, 0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock LEFT
			block.setBlockBounds(0.9375F, 0.0625F, 0.0F, 1.0F, 0.9375F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock BACK
			block.setBlockBounds(0.0625F, 0.0625F, 0.9375F, 0.9375F, 0.9375F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);
			
			// Subblock MIDDLE
			block.setBlockBounds(0.0625F, 0.0625F, 0.5F-0.0625F, 0.9375F, 0.9375F, 0.5F+0.0625F);
			rb.renderStandardBlock(block, x, y, z);
			
			break;
			
		case 3:
			// Subblock bottom
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock center
			block.setBlockBounds(0.0625F, 0.4375F, 0.0F, 0.9375F, 0.5625F, 0.9375F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock top
			block.setBlockBounds(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock RIGHT
			block.setBlockBounds(0.9375F, 0.0625F, 0.0F, 1.0F, 0.9375F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock LEFT
			block.setBlockBounds(0.0F, 0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock BACK
			block.setBlockBounds(0.0625F, 0.0625F, 0.0F, 0.9375F, 0.9375F, 0.0625F);
			rb.renderStandardBlock(block, x, y, z);
			
			// Subblock MIDDLE
			block.setBlockBounds(0.0625F, 0.0625F, 0.5F-0.0625F, 0.9375F, 0.9375F, 0.5F+0.0625F);
			rb.renderStandardBlock(block, x, y, z);
			
			break;
			
		case 4:
			// Facing WEST
			// Subblock bottom
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock center
			block.setBlockBounds(0.0F, 0.4375F, 0.0625F, 0.9375F, 0.5625F, 0.9375F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock top
			block.setBlockBounds(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock RIGHT
			block.setBlockBounds(0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock LEFT
			block.setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.9375F, 0.0625F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock BACK
			block.setBlockBounds(0.9375F, 0.0625F, 0.0625F, 1.0F, 0.9375F, 0.9375F);
			rb.renderStandardBlock(block, x, y, z);
			
			// Subblock MIDDLE
			block.setBlockBounds(0.5F-0.0625F, 0.0625F, 0.0625F, 0.5F+0.0625F, 0.9375F, 0.9375F);
			rb.renderStandardBlock(block, x, y, z);
			
			break;
			
		case 5:
			// Facing EAST
			// Subblock bottom
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock center
			block.setBlockBounds(0.0F, 0.4375F, 0.0625F, 0.9375F, 0.5625F, 0.9375F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock top
			block.setBlockBounds(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock RIGHT
			block.setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.9375F, 0.0625F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock LEFT
			block.setBlockBounds(0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F, 1.0F);
			rb.renderStandardBlock(block, x, y, z);

			// Subblock BACK
			block.setBlockBounds(0.0F, 0.0625F, 0.0625F, 0.0625F, 0.9375F, 0.9375F);
			rb.renderStandardBlock(block, x, y, z);
			
			// Subblock MIDDLE
			block.setBlockBounds(0.5F-0.0625F, 0.0625F, 0.0625F, 0.5F+0.0625F, 0.9375F, 0.9375F);
			rb.renderStandardBlock(block, x, y, z);
			
			break;
		}
		
		return true;
	}
	
	public static void renderBooksInShelf(RenderBlocks rb, TileEntityBetterBookshelf theShelf, int x, int y, int z, int meta) {
		float h = .0625F;
		float v;
		float x1, y1, z1, x2, y2, z2;
		int colorMultiplier;
		int bookSize;
		float r, g, b;
		
		Block block;
		
		int sizeInventory = theShelf.getSizeInventory (); 
		for(int i = 0; i < sizeInventory; i ++) {

			ItemStack stack = theShelf.getStackInSlot(i);
			if(stack != null) {
				
				// No further checks are needed, the shelf can only contain books.
				// But normal books will need some overriding
				if(stack.itemID == Item.book.shiftedIndex) {
					block = Block.blockSnow;
					colorMultiplier = ItemDye.dyeColors[14];
					bookSize = 3;
				} else {
					int encodedBookFeatures = stack.getItemDamage();
					ItemBook itemBook = (ItemBook)(stack.getItem());
					
					block = itemBook.getBlockForTexturing();
					colorMultiplier = itemBook.getColorFromDamage(encodedBookFeatures, 0);
					bookSize = (encodedBookFeatures >> 4) & 3;
				}
				
				v = i >= sizeInventory / 2 ? .0625F : .5625F;
				
				// Reverse order for odd meta
				if (meta == 4 || meta == 3) {
					h = .0625F + (i % 7) * .125F;
				} else {
					h = .8125F - (i % 7) * .125F;
				}
				
				bookSize --;
				y1 = v; y2 = v + .250F + .0625F * bookSize;
				
				// Align to X or Z axis
				if (meta == 2 || meta == 3) {
					x1 = h; x2 = h + .125F;
					z1 = .125F; z2 = .875F;
				} else {
					z1 = h; z2 = h + .125F;
					x1 = .125F; x2 = .875F;
				}
				
				r = (colorMultiplier >> 16 & 255) / 255F;
				g = (colorMultiplier >> 8 & 255) / 255F;
				b = (colorMultiplier & 255) / 255F;
				
				block.setBlockBounds(x1, y1, z1, x2, y2, z2);

				if(Minecraft.isAmbientOcclusionEnabled()) {
					rb.renderStandardBlockWithAmbientOcclusion(block, x, y, z, r, g, b);
				} else {
					rb.renderStandardBlockWithColorMultiplier(block, x, y, z, r, g, b);
				}

				block.setBlockBounds(0, 0, 0, 1, 1, 1);
			}
		}
		
	}
	
	public static void renderInvBox(RenderBlocks rb, Block block) {
		Tessellator tes = Tessellator.instance;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tes.startDrawingQuads();
		tes.setNormal(0.0F, -1.0F, 0.0F);
        rb.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 1.0F, 0.0F);
        rb.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.0F, -1.0F);
        rb.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.0F, 1.0F);
        rb.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(-1.0F, 0.0F, 0.0F);
        rb.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(1.0F, 0.0F, 0.0F);
        rb.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
        tes.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
	
	public static void renderInvBlock(RenderBlocks rb, Block block, int damage) {
		// Subblock bottom
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		renderInvBox(rb, block);

		// Subblock center
		block.setBlockBounds(0.0625F, 0.4375F, 0.0F, 0.9375F, 0.5625F, 0.9375F);
		renderInvBox(rb, block);

		// Subblock top
		block.setBlockBounds(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderInvBox(rb, block);

		// Subblock RIGHT
		block.setBlockBounds(0.9375F, 0.0625F, 0.0F, 1.0F, 0.9375F, 1.0F);
		renderInvBox(rb, block);

		// Subblock LEFT
		block.setBlockBounds(0.0F, 0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F);
		renderInvBox(rb, block);

		// Subblock BACK
		block.setBlockBounds(0.0625F, 0.0625F, 0.0F, 0.9375F, 0.9375F, 0.0625F);
		renderInvBox(rb, block);;

		block.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
	}
}
