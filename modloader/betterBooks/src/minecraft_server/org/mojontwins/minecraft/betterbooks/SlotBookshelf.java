package org.mojontwins.minecraft.betterbooks;

import net.minecraft.src.Block;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotBookshelf extends Slot {

	public SlotBookshelf(IInventory inventory, int idx, int x, int y) {
		super(inventory, idx, x, y);
	}

	/**
	 * Returns the maximum stack size for a given slot
	 */
	public int getSlotStackLimit() {
		return 1;
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for
	 * the armor slots.
	 */
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemBook || 
				stack.itemID == Item.book.shiftedIndex;
	}
}
