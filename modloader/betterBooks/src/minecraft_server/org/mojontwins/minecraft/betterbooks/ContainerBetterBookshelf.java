package org.mojontwins.minecraft.betterbooks;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;

public class ContainerBetterBookshelf extends Container {
	private TileEntityBetterBookshelf theShelf;
	
	public ContainerBetterBookshelf(IInventory inventoryPlayer, TileEntityBetterBookshelf theShelf) {
		this.theShelf = theShelf;	
		int slotIdx = 0;

		// Allocate slots for the shelf.
		// Corner 25 22, +X = 18, +Y = 24 based on GuiBetterBookshelf.png
		for(int i = 0; i < 14; i ++) {
			this.addSlot(new SlotBookshelf(theShelf, slotIdx ++, 25 + 18 * (i % 7), 22 + 24 * (i / 7)));
		}

		// Add slots for the player inventory and hotbar:
		slotIdx = 0;
		for(int y = 0; y < 9; ++y) {
			this.addSlot(new Slot(inventoryPlayer, slotIdx ++, 8 + y * 18, 142));
		}
		
		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 9; ++x) {
				this.addSlot(new Slot(inventoryPlayer, slotIdx ++, 8 + x * 18, 84 + y * 18));
			}
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.theShelf.isUseableByPlayer(entityplayer);
	}

}
