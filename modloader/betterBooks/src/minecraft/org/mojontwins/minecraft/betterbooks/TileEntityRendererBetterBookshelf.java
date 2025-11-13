package org.mojontwins.minecraft.betterbooks;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraft.src.World;

public class TileEntityRendererBetterBookshelf extends TileEntitySpecialRenderer {
	private World world;
	private RenderBlocks blockRenderer;

	@Override
	public void renderTileEntityAt(TileEntity theShelf, double xD, double yD, double zD, float partialTicks) {
		Tessellator tes = Tessellator.instance;
        this.bindTextureByName("/terrain.png");
        
        RenderHelper.disableStandardItemLighting();
     
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
		if (Minecraft.isAmbientOcclusionEnabled()) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
		} else {
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
		tes.startDrawingQuads();
		tes.setTranslation(xD - theShelf.xCoord, yD - theShelf.yCoord, zD - theShelf.zCoord);
		tes.setColorOpaque(1, 1, 1);
		
		RenderBlockBetterBookshelf.renderBooksInShelf(blockRenderer, (TileEntityBetterBookshelf) theShelf, theShelf.xCoord, theShelf.yCoord, theShelf.zCoord, this.world.getBlockMetadata(theShelf.xCoord, theShelf.yCoord, theShelf.zCoord));
	
		tes.setTranslation(0.0D, 0.0D, 0.0D);
		tes.draw();
        RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void cacheSpecialRenderInfo(World world) {
		this.world = world;
		this.blockRenderer = new RenderBlocks(world);
	}
}
