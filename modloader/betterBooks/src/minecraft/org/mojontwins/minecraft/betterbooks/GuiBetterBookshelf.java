package org.mojontwins.minecraft.betterbooks;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ModLoader;

import org.lwjgl.opengl.GL11;

public class GuiBetterBookshelf extends GuiContainer {
	private int textureId; 
	
	public GuiBetterBookshelf(IInventory playerInventory, TileEntityBetterBookshelf theShelf) {
		super(new ContainerBetterBookshelf(playerInventory, theShelf));
	}
	
	@Override
	public void initGui() {
		super.initGui();

		try {
			this.textureId = this.mc.renderEngine.allocateAndSetupTexture(ModLoader.loadImage(this.mc.renderEngine, "/org/mojontwins/minecraft/betterbooks/GuiBetterBookshelf.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString("Book Shelf", 56, 6, 0x404040);
		this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.textureId);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
