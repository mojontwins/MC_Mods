package org.mojontwins.minecraft.betterbooks;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.mod_betterBooks;

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

	// This isn't really needed as you can't stack books in slots, *but*
	// I'd rather leave this here in case I do something fancy in the future.
	@Override
	public ItemStack transferStackInSlot(int slotIdx) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotIdx);

		if (slot != null && slot.getHasStack()) {
			// Make a copy of what's in the slot
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();

			// If stackToMerge isn't a vanilla book or an itemWritableBook do nothing, return null.
			if(stack.itemID != Item.book.shiftedIndex && stack.itemID != mod_betterBooks.itemWritableBook.shiftedIndex) return null;

			// first 14 slots are those of the shelf.
			if (slotIdx < 14) {
				// Search a target stack between 14 and this.inventorySlots.size, backwards (true) i.e. the player's inventory
				// and merge, if possible. Otherwise return null.
				if (!this.mergeItemStack(stackInSlot, 14, this.inventorySlots.size(), true)) {
					return null;
				}
			// The rest are those of the player's inventory & hotbar.
			} else {
				// TODO :: Fix this! It puts any # of vanilla books in just 1 slot!
				// Iterate all slots in the shelf inventory
				// If empty space: put 1 of whatever and decrease stack in 1.
				
				if(stack.itemID == mod_betterBooks.itemWritableBook.shiftedIndex || stack.stackSize == 1) {
					if (!this.mergeItemStack(stackInSlot, 0, 14, false)) {
						return null;
					}
				} else return null;
	
			}

			if (stackInSlot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return stack;
	}

	// I've added my own version here as this situation is special:
	// If stackToMerge comes from the inventory, it may have 1 itemWritableBook or up to 64 vanilla books.
	// If stackToMerge comes from the shelf, it may have 1 itemWritableBook or 1 vanilla book.
	@Override
	protected boolean mergeItemStack(ItemStack stackToMerge, int slotMin, int slotMax, boolean desc) {
		boolean success = false;
		int slotIdx = slotMin;

		if (desc) {
			slotIdx = slotMax - 1;
		}

		Slot curSlot;
		ItemStack curStack;

		if (stackToMerge.isStackable()) {
			while (stackToMerge.stackSize > 0 
					&& (!desc && slotIdx < slotMax || desc && slotIdx >= slotMin)) {
				
				// Get current slot & stack
				curSlot = (Slot) this.inventorySlots.get(slotIdx);
				curStack = curSlot.getStack();

				if (curStack != null 
						&& curStack.itemID == stackToMerge.itemID
						&& (!stackToMerge.getHasSubtypes() || stackToMerge.getItemDamage() == curStack.getItemDamage())
						&& ItemStack.func_46154_a(stackToMerge, curStack)) {
					int totalSize = curStack.stackSize + stackToMerge.stackSize;

					if (totalSize <= stackToMerge.getMaxStackSize() && totalSize <= curSlot.getSlotStackLimit()) {
						stackToMerge.stackSize = 0;
						curStack.stackSize = totalSize;
						curSlot.onSlotChanged();
						success = true;
					} else if (curStack.stackSize < stackToMerge.getMaxStackSize()) {
						stackToMerge.stackSize -= stackToMerge
								.getMaxStackSize() - curStack.stackSize;
						curStack.stackSize = stackToMerge.getMaxStackSize();
						curSlot.onSlotChanged();
						success = true;
					}
				}

				if (desc) {
					--slotIdx;
				} else {
					++slotIdx;
				}
			}
		}

		if (stackToMerge.stackSize > 0) {
			if (desc) {
				slotIdx = slotMax - 1;
			} else {
				slotIdx = slotMin;
			}

			while (!desc && slotIdx < slotMax || desc && slotIdx >= slotMin) {
				curSlot = (Slot) this.inventorySlots.get(slotIdx);
				curStack = curSlot.getStack();

				if (curStack == null) {
					curSlot.putStack(stackToMerge.copy());
					curSlot.onSlotChanged();
					stackToMerge.stackSize = 0;
					success = true;
					break;
				}

				if (desc) {
					--slotIdx;
				} else {
					++slotIdx;
				}
			}
		}

		return success;
	}
}
