package org.mojontwins.minecraft.betterbooks;

import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;
import net.minecraft.src.mod_betterBooks;

public class BlockBetterBookshelf extends BlockContainer {
	private Random random = new Random();

	public BlockBetterBookshelf(int blockID) {
		super(blockID, 4, Material.wood);
		this.setLightOpacity(0);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving el) {
		int angle = MathHelper.floor_double((double) (el.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int meta = 0;
		
		// Player looking SOUTH (towards +Z), angle is 0. Block should look NORTH - Meta should be 2 (NORTH face)
		// Player looking WEST  (towards -X), angle is 1. Block should look EAST  - Meta should be 5 (EAST face)
		// Player looking NORTH (towards -Z), angle is 2. Block should look SOUTH - Meta should be 3 (SOUTH face)
		// Player looking EAST  (towards +X), angle is 3. Block should look WEST  - Meta should be 4 (EAST face)

		switch(angle) {
		case 0: meta = 2; break;
		case 1: meta = 5; break;
		case 2: meta = 3; break;
		case 3: meta = 4; break;
		}
		
		world.setBlockMetadataWithNotify(x, y, z, meta);
	}
	
	@Override
	public int getRenderType() {
		return mod_betterBooks.renderTypeBetterBookshelf;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return true; 
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public TileEntity getBlockEntity() {
		return new TileEntityBetterBookshelf();
	}
	
	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer thePlayer) {
		if(world.isRemote) {
			return true;
		} else {
			TileEntityBetterBookshelf theShelf = (TileEntityBetterBookshelf)world.getBlockTileEntity(x, y, z);
			if(theShelf != null) {
				// Note: this is different in the server: 
				ModLoader.openGUI(
						thePlayer, 
						mod_betterBooks.guiBetterBookshelfID,
						thePlayer.inventory,
						new ContainerBetterBookshelf(thePlayer.inventory, theShelf));
			}

			return true;
		}
	}
	
	/* Drop contents on break */
	@Override
	public void onBlockRemoval(World world, int x, int y, int z) {
		TileEntityBetterBookshelf theShelf = (TileEntityBetterBookshelf) world.getBlockTileEntity(x, y, z);
		float v, dx, dy, dz;
		
		if (theShelf != null) {
			for (int i = 0; i < theShelf.getSizeInventory(); ++i) {
				ItemStack stack = theShelf.getStackInSlot(i);

				if (stack != null) {
					dx = this.random.nextFloat() * 0.8F + 0.1F;
					dy = this.random.nextFloat() * 0.8F + 0.1F;
					dz = this.random.nextFloat() * 0.8F + 0.1F;
							
					EntityItem entityItem;

					while(stack.stackSize > 0) {
						
						int randomAmount = this.random.nextInt(21) + 10;
						if (randomAmount > stack.stackSize) {
							randomAmount = stack.stackSize;
						}
						stack.stackSize -= randomAmount;
						
						entityItem = new EntityItem(world, x + dx, y + dy, z + dz, 
								new ItemStack(stack.itemID, randomAmount, stack.getItemDamage())
						);
						
						v = 0.05F;
						entityItem.motionX = this.random.nextGaussian() * v;
						entityItem.motionY = this.random.nextGaussian() * v + 0.2F;
						entityItem.motionZ = this.random.nextGaussian() * v;

						if (stack.hasTagCompound()) {
							entityItem.item.setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
						}
						
						world.spawnEntityInWorld(entityItem);
					}
				}
			}
		}

		super.onBlockRemoval(world, x, y, z);
	}
}
