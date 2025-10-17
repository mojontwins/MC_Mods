package org.mojontwins.minecraft.betterbooks;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;


public class ContainerWritableBook extends Container {
	
	public ContainerWritableBook(IInventory inventoryPlayer, int slotId) {
		this.addSlot(new Slot(inventoryPlayer, slotId, 0, 0));
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

}
