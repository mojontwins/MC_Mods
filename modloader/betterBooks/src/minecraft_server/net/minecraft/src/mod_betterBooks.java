package net.minecraft.src;

import java.util.Random;

import org.mojontwins.minecraft.betterbooks.BlockBetterBookshelf;
import org.mojontwins.minecraft.betterbooks.ItemBook;
import org.mojontwins.minecraft.betterbooks.TileEntityBetterBookshelf;

public class mod_betterBooks extends BaseModMp {

	// Blocks
	public static Block blockBetterBookshelf;
	
	// Items
	public static ItemBook itemWritableBook;
	
	// Properties
	@MLProp(name="blockBetterBookshelfID", info="ID for the Bookshelf block")
	public static int blockBetterBookshelfID = 192;
	
	@MLProp(name="itemWritableBook", info="ID for the writable book item")
	public static int itemWritableBookID = 192;
	
	@MLProp(name="guiBetterBookshelfID", info="ID for the Bookshelf GUI")
	public static int guiBetterBookshelfID = 100;
	
	@MLProp(name="guiWritableBookID", info="ID for the Writable Book GUI")
	public static int guiWritableBookID = 101;
	
	@MLProp(name="packetType", info="ID for the ModLoaderMP packet")
	public static int packetType = 100;
	
	// Render type IDs
	public static int renderTypeBetterBookshelf;
	
	public mod_betterBooks() {
		
	}

	@Override
	public String getVersion() {
		return "Better Books 1.0";
	}

	@Override
	public void load() {
		// Instantiate blocks
		blockBetterBookshelf = new BlockBetterBookshelf(blockBetterBookshelfID)
				.setBlockName("betterBookshelf");
		
		// Register blocks
		ModLoader.registerBlock(blockBetterBookshelf);
		//ModLoader.addName(blockBetterBookshelf, "Bookshelf");
		
		// Instantiate items
		itemWritableBook = (ItemBook) new ItemBook(itemWritableBookID, Block.blockSteel)
				.setHasSubtypes(true)
				.setItemName("writableBook");
		itemWritableBook.setIconIndex(ModLoader
				.addOverride("/gui/items.png", "/org/mojontwins/minecraft/betterbooks/ItemWritableBook.png"));
		itemWritableBook.featherIconIndex = ModLoader
				.addOverride("/gui/items.png", "/org/mojontwins/minecraft/betterbooks/ItemWritableBookFeather.png");
		
		// Add a special renderer for better bookshelves. `true` means `as 3D on the inventory`.
		renderTypeBetterBookshelf =  ModLoader.getUniqueBlockModelID(this, true);
		
		// Tile entities
		ModLoader.registerTileEntity(TileEntityBetterBookshelf.class, "betterBookshelf");
		
		// Recipes
		ModLoader.addRecipe(
				new ItemStack(blockBetterBookshelf, 2), 
				new Object[] {"PPP", "   ", "PPP", 'P', Block.planks}
			);
		
		ModLoader.addShapelessRecipe(
				new ItemStack(itemWritableBook, 1, 14),
				new Object[] {
					new ItemStack(Item.dyePowder, 1, 0), 		// Black dye = Ink sack.
					new ItemStack(Item.book),
					new ItemStack(Item.feather)
			});
		
		for (int i = 0; i < 16; ++i) {
			ModLoader.addShapelessRecipe(
					new ItemStack(itemWritableBook, 1, i), 
					new Object[] {
						new ItemStack(Item.dyePowder, 1, i), 
						new ItemStack(itemWritableBook, 1, -1)
			});
        }

	}
	
	@Override
	public void takenFromCrafting(EntityPlayer thePlayer, ItemStack theStack, IInventory craftMatrix) {
		
		if(theStack.itemID == itemWritableBook.shiftedIndex) {
			
			// Find original book values
			ItemStack originalBook = null;
			for(int i = 0; i < craftMatrix.getSizeInventory(); i ++) {
				ItemStack tempStack = craftMatrix.getStackInSlot(i);
				if(tempStack != null && tempStack.itemID == itemWritableBook.shiftedIndex) {
					originalBook = tempStack;
				}
			}
			
			int dye; 
			int size;
			int bookType;
			
			if(originalBook == null) {
				// No book in craft matrix, get values at random
				
				bookType = 0; 						// This is for the future
				size = (new Random()).nextInt(8);	// Three sizes
				if(size < 2) {
					size = 1;
				} else if(size < 4) {
					size = 2;
				} else {
					size = 3;
				}
				
				dye = 14;
			} else {
				// Book in craft matrix, get size & type from it,
				// dye from crating results (set by recipe)
				int damage = originalBook.getItemDamage();
				bookType = damage >> 6;
				size = (damage >> 4) & 3;
				
				// Dye from the crafting results
				dye = theStack.getItemDamage() & 15;
			}
			
				
				// Encode as damage
				theStack.setItemDamage(dye | size << 4 | bookType << 6);
			
		}
	}
	
	@Override
	public void handlePacket(Packet230ModLoader packet, EntityPlayerMP thePlayer) {
		if(packet.packetType == packetType) {		
			int slotNumber = packet.dataInt[0];
			int pageNumber = packet.dataInt[1];
			String title = packet.dataString[0];
			String text = packet.dataString[1];
			
			ItemStack bookStack = thePlayer.inventory.getStackInSlot(slotNumber);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("page", pageNumber);
			nbt.setString("title", title);
			nbt.setString("text", text);
			bookStack.setTagCompound(nbt);
		}
	}
}
