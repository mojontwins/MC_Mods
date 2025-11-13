package org.mojontwins.minecraft.betterbooks;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;

public class TileEntityBetterBookshelf extends TileEntity implements IInventory {

	private ItemStack[] shelfContents = new ItemStack[14];
	public int numUsingPlayers;

	public TileEntityBetterBookshelf() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return 14;
	}

	/**
	 * Returns the stack in slot #
	 */
	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.shelfContents[slot];
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of
	 * the second int arg. Returns the new stack.
	 */
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.shelfContents[slot] != null) {
			ItemStack stack;

			if (this.shelfContents[slot].stackSize <= amount) {
				stack = this.shelfContents[slot];
				this.shelfContents[slot] = null;
				this.onInventoryChanged();
				return stack;
			} else {
				stack = this.shelfContents[slot].splitStack(amount);

				if (this.shelfContents[slot].stackSize == 0) {
					this.shelfContents[slot] = null;
				}

				this.onInventoryChanged();
				return stack;
			}
		} else {
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.shelfContents[slot] != null) {
			ItemStack stack = this.shelfContents[slot];
			this.shelfContents[slot] = null;
			return stack;
		} else {
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.shelfContents[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "Better Bookshelf";
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false : player.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
		++this.numUsingPlayers;
		this.worldObj.playNoteAt(this.xCoord, this.yCoord, this.zCoord, 1,
				this.numUsingPlayers);
	}

	@Override
	public void closeChest() {
		--this.numUsingPlayers;
		this.worldObj.playNoteAt(this.xCoord, this.yCoord, this.zCoord, 1,
				this.numUsingPlayers);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagList list = nbttagcompound.getTagList("Content");
		this.shelfContents = new ItemStack[this.getSizeInventory()];

		for(int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound nbtSlot = (NBTTagCompound)list.tagAt(i);
			byte slotIdx = nbtSlot.getByte("Slot");
			if(slotIdx >= 0 && slotIdx < this.shelfContents.length) {
				this.shelfContents[slotIdx] = ItemStack.loadItemStackFromNBT(nbtSlot);
			}
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < this.shelfContents.length; ++i) {
			if(this.shelfContents[i] != null) {
				NBTTagCompound nbtSlot = new NBTTagCompound();
				nbtSlot.setByte("Slot", (byte)i);
				this.shelfContents[i].writeToNBT(nbtSlot);
				list.appendTag(nbtSlot);
			}
		}

		nbttagcompound.setTag("Content", list);
	}

}
