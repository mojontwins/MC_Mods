package net.minecraft.src;

import java.util.Random;

import org.mojontwins.minecraft.betterbooks.BlockBetterBookshelf;
import org.mojontwins.minecraft.betterbooks.GuiBetterBookshelf;
import org.mojontwins.minecraft.betterbooks.ItemBook;
import org.mojontwins.minecraft.betterbooks.RenderBlockBetterBookshelf;
import org.mojontwins.minecraft.betterbooks.TileEntityBetterBookshelf;
import org.mojontwins.minecraft.betterbooks.TileEntityRendererBetterBookshelf;

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
		ModLoader.addName(blockBetterBookshelf, "Bookshelf");
		
		// Instantiate items
		itemWritableBook = (ItemBook) new ItemBook(itemWritableBookID, Block.blockSteel)
				.setItemName("writableBook");
		itemWritableBook.setIconIndex(ModLoader
				.addOverride("/gui/items.png", "/org/mojontwins/minecraft/betterbooks/ItemWritableBook.png"));
		itemWritableBook.featherIconIndex = ModLoader
				.addOverride("/gui/items.png", "/org/mojontwins/minecraft/betterbooks/ItemWritableBookFeather.png");
		ModLoader.addName(itemWritableBook, "Writable Book");
		
		// Add a special renderer for better bookshelves. `true` means `as 3D on the inventory`.
		renderTypeBetterBookshelf =  ModLoader.getUniqueBlockModelID(this, true);
		
		// Tile entities
		ModLoader.registerTileEntity(TileEntityBetterBookshelf.class, "betterBookshelf", new TileEntityRendererBetterBookshelf());
		
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
						new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i)), 
						new ItemStack(itemWritableBook, 1, -1)
			});
        }
		
		// Register GUIs with ModLoaderMP
		ModLoaderMp.registerGUI(this, guiBetterBookshelfID);
		ModLoaderMp.registerGUI(this, guiWritableBookID);
	}
	
	@Override
	public void renderInvBlock(RenderBlocks rb, Block block, int meta, int renderType) {
		if(renderType == renderTypeBetterBookshelf) {
			RenderBlockBetterBookshelf.renderInvBlock(rb, block, 0);
		}
	}

	@Override
    public boolean renderWorldBlock(RenderBlocks rb, IBlockAccess world, int x, int y, int z, Block block, int renderType) {
		if (renderType == renderTypeBetterBookshelf) {
			return RenderBlockBetterBookshelf.renderWorldBlock(rb, world, x, y, z, block);
		} else {
			return false;
		}
    }
	
	@Override
	public GuiScreen handleGUI(int guiID) {
		EntityPlayerSP thePlayer = ModLoader.getMinecraftInstance().thePlayer;
		InventoryPlayer theInventory = thePlayer.inventory;
		
		if(guiID == guiBetterBookshelfID) {
			// An new TileEntityBookshelf will do, as the *real* tile entity is in the server.
			return new GuiBetterBookshelf(theInventory, new TileEntityBetterBookshelf());
		} 

		return null;
	}
	
	@Override
	public void takenFromCrafting(EntityPlayer thePlayer, ItemStack theStack, IInventory craftMatrix) {
		
		if(theStack.itemID == itemWritableBook.shiftedIndex) {
			int damage = theStack.getItemDamage();
			int dye = damage & 15;
			int size = (damage >> 4) & 3;
			if(size == 0) {
				// Hook here so I can give books random colors and sizes.
				Random rand = new Random();
				int bookType = 0; 				// This is for the future
				size = rand.nextInt(8);		// Three sizes
				if(size < 2) {
					size = 1;
				} else if(size < 4) {
					size = 2;
				} else {
					size = 3;
				}
				
				dye = 14; // rand.nextInt(16);
				
				// Encode as damage
				theStack.setItemDamage(dye | size << 4 | bookType << 6);
			}
			
		}
	}
}
