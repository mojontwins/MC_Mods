package org.mojontwins.minecraft.betterbooks;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemDye;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;


public class ItemBook extends Item {
	// This block will be used as a base for texturing the book in the shelf
	private Block blockForTexturing;
	
	// Item will be rendered in two passes. This is the icon for the second pass
	public int featherIconIndex;

	public ItemBook(int id, Block blockForTexturing) {
		super(id);
		this.maxStackSize = 1;
		this.blockForTexturing = blockForTexturing;

	}

	@Override
	public int getColorFromDamage(int damage, int renderPass) {
		// Damage encodes several things, bits 0-3 are dye color
		return renderPass == 0 ? ItemDye.dyeColors[damage & 15] : 0xFFFFFF;
	}

	// This should be called `needsTwoRenderPasses`
	@Override
    public boolean func_46058_c() {
        return true;
    }

	// This is should be called `getIconFromDamageAndRenderPass`
	@Override
    public int func_46057_a(int damage, int renderPass) {
		return renderPass == 0 ? this.iconIndex : this.featherIconIndex;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer thePlayer) {
		ModLoader.getMinecraftInstance().displayGuiScreen(new GuiWritableBook(thePlayer));
		return super.onItemRightClick(stack, world, thePlayer);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		String title = "Empty Book";
		
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey("title")) {
				title = "Untitled Book";
				String s = nbt.getString("title");
				if(!"".equals(s)) title = s;
			}
		}
		return title;
	}
	
	public Block getBlockForTexturing() {
		return blockForTexturing;
	}

	public void setBlockForTexturing(Block blockForTexturing) {
		this.blockForTexturing = blockForTexturing;
	}
	
	// This method is apparently no longer used but it's in the engine. 
	// The interesting thing is that it makes the packet that transfers ItemStacks to read/write NBT
	@Override
	public boolean func_46056_k() {
		return true;
	}
}
