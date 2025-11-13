package org.mojontwins.minecraft.betterbooks;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_betterBooks;


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
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer thePlayer) {
		ModLoader.openGUI(thePlayer, mod_betterBooks.guiWritableBookID, thePlayer.inventory, new ContainerWritableBook(thePlayer.inventory, thePlayer.inventory.currentItem));
		return stack;
	}
	
	// This method is apparently no longer used but it's in the engine. 
	// The interesting thing is that it makes the packet that transfers ItemStacks to read/write NBT
	@Override
	public boolean func_46003_i() {
		return true;
	}
}
