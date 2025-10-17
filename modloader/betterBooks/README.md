# Better than Books

- Writable tomes, using the Tomes mod editor.
- New book item with associated NBT to contain text.
- New bookshelf block which can contain any book (empty, enchanted, written).

## The new bookshelf block

It needs an associated tile entity. I've never done tile entities in ModLoder. It will need a GUI as well, and an inventory. I want to do this properly, so a special renderer is needed. It has to render individual books as they are set up in the inventory. I need to break this down in tasks.

1.- Add ItemBook. It does nothing right now.

2.- Add the block and a basic block renderer (only the frame). Could use blockbench and use the json directly but I don't want to have to integrate my parser and stuff. I think I'll convert it manually to subblocks and write a renderer that's simmilar to BlockFence's. Block will be directional with a front face facing the player upon placing. Use the same metadata as other directional blocks.

3.- Add the tile entity. Use TileEntityChest as a template, as I will need an inventory. It will store 2 rows of 7 tomes. That way, when I make the renderer, books will be two pixels wide. Accept only ItemBook instances. Wait - there's no ItemBook in this version lol. `Item.book` is a plan `Item`. I can hijack this by substituting normal books so all of them are writable.

4.- Associate the tile entity to the Block, so the player can interact with it and add books.  

5.- Expand the block renderer to read the inventory in the TileEntity associated to the block and actually render books. 

### Stuf ItemBook

ItemBox does nothing right now, just a subclass of Item. Make an instance in the mod class, add a recipe

```java
	itemWritableBook = new ItemBook(itemWritableBookID);
	itemWritableBook.setIconIndex(ModLoader
			.addOverride("/gui/items.png", "/org/mojontwins/minecraft/betterbooks/ItemWritableBook.png"));
	ModLoader.addName(itemWritableBook, "Writable Book");

	ModLoader.addShapelessRecipe(
				new ItemStack(itemWritableBook),
				new Object[] {
					new ItemStack(Item.dyePowder, 1, 0), 		// Black dye = Ink sack.
					new ItemStack(Item.book),
					new ItemStack(Item.feather)
			});
```

### Textures

This will reuse vanilla textures. idx=4 (planks).

### The basic renderer

I've used blockbench to make the model that's based on six cubes. This will block exactly like how BlockFence works: it will call the cube renderer for each subblock. The only texture used will be idx=4 (planks).

This is the block as exported by blockbench. The opening faces North. 1st step will be porting this then rotating it for the other three facings (UV mapping omited)

```json
	"elements": [
		{
			"name": "bottom",
			"from": [0, 0, 0],
			"to": [16, 1, 16],
		},
		{
			"name": "center",
			"from": [1, 7, 0],
			"to": [15, 9, 15],
		},
		{
			"name": "top",
			"from": [0, 15, 0],
			"to": [16, 16, 16],
		},
		{
			"name": "W",
			"from": [0, 1, 0],
			"to": [1, 15, 16],
		},
		{
			"name": "E",
			"from": [15, 1, 0],
			"to": [16, 15, 16],
		},
		{
			"name": "S",
			"from": [1, 1, 15],
			"to": [15, 15, 16],
		}
	]
```

For each cube, I have to call `block.setBlockBounds(x1, y1, z1, x2, y2, x2)` and then `this.renderStandardBlock(block, x, y, z)`. `setBlockBounds` use double coordinates 0-1D. I can easily write a converter that generates the calls from the json. I will start doing so. I'm use the simple json importer I used for Inslands.


I got this:

```java
	// D:\Cosas\modloader\stuff\FillableBookshelf.json converted by blockbenchConverterSymple by na_th_an

	// Subblock bottom
	block.setBlockBounds(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0);
	this.renderStandardBlock(block, x, y, z);

	// Subblock center
	block.setBlockBounds(0.0625, 0.4375, 0.0, 0.9375, 0.5625, 0.9375);
	this.renderStandardBlock(block, x, y, z);

	// Subblock top
	block.setBlockBounds(0.0, 0.9375, 0.0, 1.0, 1.0, 1.0);
	this.renderStandardBlock(block, x, y, z);

	// Subblock W
	block.setBlockBounds(0.0, 0.0625, 0.0, 0.0625, 0.9375, 1.0);
	this.renderStandardBlock(block, x, y, z);

	// Subblock E
	block.setBlockBounds(0.9375, 0.0625, 0.0, 1.0, 0.9375, 1.0);
	this.renderStandardBlock(block, x, y, z);

	// Subblock S
	block.setBlockBounds(0.0625, 0.0625, 0.9375, 0.9375, 0.9375, 1.0);
	this.renderStandardBlock(block, x, y, z);
```

I could try and rotate it programatically but it's easier to rotate in blockbench and export.

If I am to model this after furnaces, metadata = X means that the X side is the front. So metadata = 4 means that side 4 is the front, i.e. North face. So the above code is for `meta == 4`.

### About facing, angles, rendering, etc

Sometimes method mappings can be quite misleading. Those are the right values and faces.

Imagine a block set at (0, 0, 0).

* Its NORTH face is looking towards -Z, this is side 2.
* Its SOUTH face is looking towards +Z, this is side 3.
* Its WEST  face is looking towards -X, this is side 4.
* Its EAST  face is looking towards +X, this is side 5.

We use this formula to get an angle 0-3 (ortho) the entity is looking at:

```java
	int angle = MathHelper.floor_double((double) (el.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
```

Angle is:

* 0 looking SOUTH
* 1 looking WEST
* 2 looking NORTH
* 3 looking EAST.

So if the player looks, for instance, `WEST`, the block should be looking toward the player, that is, looking `EAST`. If we are using "meta N = side N is front", this should get meta 5.

To look right on the inventory, meta should be 3 (looking EAST). `getBlockTextureFromSide` in `Block` is called for that so we are redirecting to `getBlockTextureFromSideAndMetadata` passing 3 as metadata.

### Back to the custom renderer

To register the custom renderer, the basic drill:

1.- Add a new `RenderBlockBetterBookshelf` at `org.mojontwins.minecraft.betterbooks` with the usual render methods (world & inventory).

2.- Add a render type attribute, `renderTypeBetterBookshelf`.

3.- Generate & assign a value on `load()`: 

```java
	// Add a special renderer for better bookshelves. `true` means `as 3D on the inventory`.
	renderTypeBetterBookshelf =  ModLoader.getUniqueBlockModelID(this, true);
```

4.- Override the render type related methods in `BlockBetterBookshelf`. `renderAsNormalBlock` is used for a number of things, for instance to draw shadows on top. I'm returning true for this one.

```java
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
```

Finally hook my custom class in `mod_betterBooks` rendering related overrides:

```java
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
```

I'm adding a crafting recipe RN, which is that of normal bookshelves, bar the books...

### Remember:

* The TileEntity stores the data and implements IInventory.
* Slots contain items in the GUI.
* Container are a set of slots and define their placement.
* GUI draws stuff on the screen and makes everything work together.

### The tile entity

This will be like a chest with only 2x7 slots that only admits ItemBook. We start by mimicking how chests work in class `TileEntityBetterBookshelf`. Nothing very fancy, just space for 14 items rather than 27.

Remember that you have to register the tile entity in your mod class:

```java
	ModLoader.registerTileEntity(TileEntityBetterBookshelf.class, "betterBookshelf");
```

### Special slots

`SlotBookShelf` will only allow not stackable `ItemBook` and `Item.book`.

```java
	public class SlotBookshelf extends Slot {

		public SlotBookshelf(IInventory inventory, int idx, int x, int y) {
			super(inventory, idx, x, y);
		}

		public int getSlotStackLimit() {
			return 1;
		}

		public boolean isItemValid(ItemStack stack) {
			return stack.getItem() instanceof ItemBook || 
					stack.itemID == Item.book.shiftedIndex;
		}
	}
```

### The Container

The GUI will need a Container made of slots. We'll be extending `Container` and instantating a copy of the player's inventory plus 14 spaces for items. Nothing fancy at all.

### The GUI

The GUI is bare bones. Just hold the slots and make shit happens. This is a container with slots, we'll just extend `GuiContainer` and will call the superclass with a new `ContainerBetterBookshelf` instance. Extending `GuiContainer` requires that we implement `void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)`.

Nothing very fance, we are just adapting what `GuiDispenser` (for example) does.

### Launching the GUI

This is done via `blockActivated` in `BlockBetterBookshelf` and using ModLoader. We'll associate with `TileEntityBetterBookshelf` too at this point, making `BlockBetterBookshelf` extend `BookContainer`. Note how we do nothing if we are in SMP. This is the client and the TileEntity interaction will be taken care of in the server. `ModLoader.openGUI` needs the player instance and the GUI object.

```java
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
				ModLoader.openGUI(thePlayer, new GuiBetterBookshelf(thePlayer.inventory, theShelf));
			}

			return true;
		}
	}
```

### Save data

The tile entity is now working, but we need to save the data. Typical inventory save, just like dispensars, etc.

### SMP support

We have to ask ModLoaderMP for an ID for our GUI, then override `HandleGUI`. Note how this is similar to launching the GUI, but we are not feching any actual TileEntity from the world as this will be dummy - everything is handled at the server. We'll adding `@MLProp` to the GUI ID in case there's collisions with other mods.

```java
	@MLProp(name="guiBetterBookshelfID", info="ID for the Bookshelf GUI")
	public static int guiBetterBookshelfID = 100;

	[...]

	ModLoaderMp.registerGUI(this, guiBetterBookshelfID);
```

Finally we gave to override `BaseModMP`'s `handleGUI` to fire up the right GUI screen when the packet gets to `ModLoaderMP`.

### Enhancing the renderer

The renderer should show the books. I'm using wool textures as they are colorful. Color for book i will be of Wool meta ((x + y + z) & 15). Each book will be a simple box. Nothing very fancy. If meta is 2/3 books will be rendered along the X axis, and if meta is 4/5 they will be rendered along the Z axis, two ""pixels"" away from the border (which is 1F / 16 * 2 = 0.125F To 1F / 16 * 14 = 0,875).

`RenderBlocks` doesn't have access to the full world to get the right tile entity, so we'll have to create a custom tile entity renderer. First of all, you have to add an extra parameter when registering the tile entity: 

```java
	ModLoader.registerTileEntity(TileEntityBetterBookshelf.class, "betterBookshelf", new TileEntityRendererBetterBookshelf());
```

The custom renderer we added before will render the frame, and the tile entity renderer will render the books.

In my 1st attempt, I've chosen the colors and sizes of books at random, but I think it would be better to calculate them when creating the book - somehow and store those properties encoded as damage. There's a hook, `takenFromCrafting (player, stack, craftMatrix)` I could use. I think this is the closest I can get, there's no way I can hijack the recipes.

```java
	@Override
	public void takenFromCrafting(EntityPlayer thePlayer, ItemStack theStack, IInventory craftMatrix) {
		
		if(theStack.itemID == itemWritableBook.shiftedIndex) {
			// Hook here so I can give books random colors and sizes.
			Random rand = new Random();
			int bookType = 0; 				// This is for the future
			int size = rand.nextInt(3);		// Three sizes
			int dye = rand.nextInt(16);
			
			// Encode as damage
			theStack.setItemDamage(dye | size << 4 | bookType << 6);
			
		}
	}
```

For the item itself, I'm using Item's `getColorFromDamage`. This means that I have to edit the icon I'm using to remove color. Also I will need two passes like with spawner eggs so the icon looks good (feather and pages should not be colorized). First of all `getColorFromDamage` will have to return the colorizer with renderPass 0, 0xFFFFFF on renderPass1. Also, `func_46058_c` (obfuscated) should return `true` so two render passes are used and `func_46057_a (int damage, int renderPass)` should return the right icon index depending on the render pass. 

To render the books in the tile entity, I'll be using a block that's easily dyeable as a model. Looking forward to the future and having more types of books, I'm adding this as an attribute to the Item class. I'm using `blockSteel` for writable books. This is what we finally got:

```java
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

		// Return true if this icon needs two render passes
		@Override
	    public boolean func_46058_c() {
	        return true;
	    }

		// This is should be called `getIconFromDamageAndRenderPass`
		@Override
	    public int func_46057_a(int damage, int renderPass) {
			return renderPass == 0 ? this.iconIndex : this.featherIconIndex;
		}
		
		public Block getBlockForTexturing() {
			return blockForTexturing;
		}

		public void setBlockForTexturing(Block blockForTexturing) {
			this.blockForTexturing = blockForTexturing;
		}
		
	}
```

### Porting this to the server

I think it's a good idea start porting this right away and make sure the bookshelf works in SMP before moving to the books themselves.

This process involvers, typically, removing all "client side" stuff like renderers, names, etc.

Note that `ModLoader.openGUI` has a different prototype, of course, as no actual GUIs exist in the server. This is how it is now in `blockActivated`.

```java
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
```

## Writable books

Infrastructure is already there. What we need now is:

* Remember that books with texts are not very different to signs. But with more text.
* Book text will be stored in the itemstack's NBTcompound.
* A proper editor so you can navigate the book's pages and write in them.
* NBT storage.
* Sending the text over to the server when updating text, and back to the client when opening a book. This implies an understanding on how the ModLoaderMP special packet works.

I don't like the vanilla editor. I'll take a look at Tomes mod right now for inspiration.

### Dummy GUI

I've made a dummy GUI to test item right click and that the texture I've made looks good. To hook, this is needed in `ItemBook`:

```java
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		ModLoader.openGUI(entityplayer, mod_tome.editbookgui, entityplayer.inventory, new ContainerBook(entityplayer.inventory, new BookEntity(itemstack)));
		return itemstack;
	}
```

### The editor

Gosh this took longer than expected. It may still glitch tho, this one was hard to pull off.

### Book titles

For some reason now I can't leave with book titles. I want them to show as tooltips but I haven't managed to do so yet. So I'm going the reverse engineering path. I'm undestanding how tooltips work and when I do I'm hijacking them.

`GuiContainer` uses `ItemStack.getItemNameandInformation`. This method checks for the nbt tag compound associated to ItemStacks and retrieves an id and a level and builds a translatable string with that. Apparently, there's nothing I can really do here.

`getItemNameAndInformation` adds the result of `Item.getItemDisplayName`. Bingo!

```java
	public String getItemDisplayName(ItemStack par1ItemStack) {
        String var2 = ("" + StringTranslate.getInstance().translateNamedKey(this.getLocalItemName(par1ItemStack))).trim();
        return var2;
    }
```

This is it. Overriding this method in `ItemBook` should do the trick. I'm using the 1st text line but I might add a text box so you can title each book.

And it works *prefectly*. But I have one fear: is full NBT transferred with the packet or does this just hardcode the bit used for potions and enchantments? `Packet5PlayerInventory` does NOT transfer NBTs.

Come to think of it... I have absolutely no idea of how are itemstacks transferred, generally. I mean, potion effects and enchantments ARE shown in the client so they must travel somehow. Maybe `Packet104WindowItems`? This transfers full itemstack and theres'a `Packet.readItemStack` / `Packet.writeItemStack` pair of methods. Yup! In these methods, if the item `isDamageable ()` or `func_46056_k()` (!), the NBT tag is read / written. I guess that unnamed method is r1.3.2's MCP7.2's `getShareTag`.

It's good that I've researched this 'cause otherwise I would have had serious problems when porting this bit to the SMP. It wouldn't've worked!

Wait! Not so fast. Tools and Armor have NBTs (effects) and those are transferred as these items return `isDamageable()` == true. Potions don't. And `func_46056_k()` is nowhere to be found! It's in the furnace code for some reason. That bit is removed in 1.3.2, and the code it activates is very weird! I think it's a leftover. I dunno what should happen if I set this in `ItemBook` and then fiddle with the book in an oven lol. Will try.

I don't know what would happen if I make books `damageable`. That wouldn't look pretty as a bar could show somehow if damage != maxdamage. So I have to do some testing and if I manage to get this working get a proper note or gist or whatever so I don't forget. But let's leave that bit for when I port this bit to SMP. I want book titles now!

### Back to the GUI

Just add a text box on top of the book so the user can enter a title. It has to be "Untitled Book" by default. Note that `GuiTextField` is almost unmapped. These methods are useful:

* `public void func_50033_b(boolean focused)` is `setFocused`.
* `public boolean func_50025_j()` is `getFocused`.
* `public void func_50037_a(char c, int scan)` is `keyTyped`.

### Dye recipes

Make books dyeable. This was easy:

```java

```

Problem: `takenFromCrafting` to set size & type interferes with this. Think of a solution. 

### Porting this to the server

Opening a GUI from the server seems to require a Container, but I don't have containers for books. Vanilla signs call entityPlayer.displayGUISign(TileEntitySign). This does absolutely nothing in the server, it's just a client thing, which makes sense. As I now understand, all the infrastructure about GUI openning client-server is to manage slots and inventories.

Right now you open the GUI in the client and this modifies the NBT associated to the ItemStack. How this gets transfered to the server I don't know, I'll have to do some tests and see how this behaves.

`Packet103SetSlot` is what transfers ItemStacks to and fro. That packet semes to be only server -> client, so I think I have to do something about this myself.

When I save the NBT in the client, I have to transfer it to the server via a custom packet. When I open a the GUI and read stuff, I have to retrieve it from the server, too. Or do I? Time to play around with this a bit and document my finds.

1st of all I have to understand, after login, how is the player inventory populated?

* When `NetClientHandler` receives `Packet1Login` it creates a new `PlayerControllerMP`, `WorldClient`, sets that world as `Minecraft.theWorld` via the `changeWorld` method, sets dimension, display `GuiDownloadTerrain`, sets `Minecraft.thePlayer.entityId` to the one received in the packet.

* `changeWorld` calls the player controller's `onWorldChange`, which does nothing. As the passed `player` is null, it creates it from the controller (`this.playerController.createPlayer`) and calls `preparePlayerToSpawn`.

* Player creation from the controller: does nothing special. Calls the super, this creates a `ContainerPlayer` for `inventorySlots`. This adds the crafting results slots, 4 slots for crafting, 4 slots for armor, 18 slots for inventory and 9 slots for the hotbar. All these reference inventoryPlayer.

* In `EntityClientPlayerMP`, there`s a `inventoryUpdateTickCounter` and in MCP it is marked as `used for sending inventory updates`, but this seems to be vestiges of an old version as it just increments and resets when it reaches 20, and it is a private attribute.

Let's look in the server side of things.

* `EntityPlayerMP`, on `onUpdateEntity` there's a bit that examines the inventory and detects maps and then sends map data. 

I mean, complete ItemStack must come from the server but WHEN (outside a GUIContainer shit) I still don't know and this pisses me off. Need to understand how.

### Ideas

I've had an idea. Since slots seem to be managed automaticly, if I add a container with a slot associated with the GUI, maybe the ItemStack will get auto-updated to the server when I finish writing. Let's try that.

Let's begin by making `GuiWritableBook` extend `GuiContainer` rather than `GuiScreen`. That will need instantiating a `Container` subclass to pass to the `super` constructor. The Container will be as simple as...

Slots must refer to IInventories, we should be able to get the active slot ID from the player and use this inventory directly. Let's try this:

```java
	public class ContainerWritableBook extends Container {
		
		public ContainerWritableBook(IInventory inventoryPlayer, int slotId) {
			this.addSlot(new Slot(inventoryPlayer, slotId, 0, 0));
		}

		@Override
		public boolean canInteractWith(EntityPlayer var1) {
			return true;
		}

	}
```

And this...

```java
	public GuiWritableBook(EntityPlayer thePlayer) {
		super(new ContainerWritableBook(thePlayer.inventory, thePlayer.inventory.currentItem));
		
		this.theBookStack = thePlayer.inventory.getCurrentItem();

		[...]
	}
```

And this, in ItemBook: 

```java
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer thePlayer) {
		if(!ModLoader.getMinecraftInstance().isMultiplayerWorld()) {
			ModLoader.getMinecraftInstance().displayGuiScreen(new GuiWritableBook(thePlayer));
		}

		return super.onItemRightClick(stack, world, thePlayer);
	}
```

Let's see if this keeps working in SP - It *works* but I have to redo the GUI rendering to reflect that of `GuiContainer`. Also, the slot is shown on screen. I have to figure out if I can override that somehow. How does `GuiContainer` rendering works?

### Rendering GuiContainer

`drawScreen` does this:

* `drawDefaultBackground`, that should be the shit that darkens the game area.
* Then it renders all the inventory slot frames.
* Then it calls `drawGuiContainerForegroundLayer`.
* Then it draws all items in the slots.
* Then it draws the tooltip.
* Finally it calls `super.drawScreen`.

That call to super draws all buttons in the control list. I think I can override this. I have my own `drawScreen` that calls super (it was `GuiScreen`'s, now it's `GuiContainer`'s). Just remove this call and render controls myself. Et voie-la! Now it works!

### Testing in SMP.

The server should get the book with its metadata i.e. the NBT shit. As we've seen, this NBT isn't transfered unless the block is either damageable or has that unnamed method returning true. As I don't know what that method does nor if hijacking it would have consecuences, I'd try the damageable part first. If item is at full damage, the damage bar shouldn't show.

Nope. We can't use this. We are using damage for book color and size. So we have to take the dirty approach. So let's override `func_46056_k` and make it return true :-/ `func_46003_i` in the server.

When I try to obtain an ItemBook from the creative inventory I get an exception in the server: it's attempting to read a string with length < 0. So back at the drawing board.

Problems also happens when I craft the book and get it from the inventory.

This has to be the `func_46056_k`. The exception is 'cause the server reads a packet255 ?! WHY?

Wait - I was missing the equivalent to this method in the server (`func_46003_i`). Now it doesn't crash. But if I log out and log in text is not retained. Somehow, my trickery with the invisible slot is not working. Maybe the slot is not sent if it's not really updated, maybe the slot needs to be signaled or marked dirty somehow. Hmmm.

I can't find a single way to get the ItemStack related NBT to the server. I should've researched this first lol.

### Custon packets in ModLoaderMP

In the client, I have `ModLoaderMP.sendPacket`. This needs an instance of `Packet230ModLoader`. This has a `modId`, a `packetType`, and several arrays (int, float...) I guess I'd have to send an int with some sort of ID to identify what I have to update in the server, maybe the slot number that contains the Book in the player's inventory, and the strings (title, text).

Server side you have to override `public void handlePacket(Packet230ModLoader packet230modloader, EntityPlayerMP entityplayermp)` in your mod. Apparently, `entityPlayerMP`'s `craftingInventory` should contain an instance of the active container if set on the item's call to `openGUI`.

So first, when the GUI is open, it is also "open" in the server, and the slot should be set and entityPlayerMP's crafting inventory should be `ContainerWritableBook`.

Let's try and send a packet.

This sends the data:

```java	
	Packet230ModLoader packet = new Packet230ModLoader();
	packet.packetType = mod_betterBooks.packetType;
	packet.dataString = new String [] {
			this.textFieldTitle.getText(),
			this.theEditor.text
	};
	packet.dataInt = new int [] {
			this.thePlayer.inventory.currentItem,
			this.currentPage
	};
	ModLoaderMp.sendPacket(ModLoaderMp.getModInstance(mod_betterBooks.class), packet);
```

Let's try and GET it, for now (do nothing but log), in the server's mod_betterBooks:

```java
	public void handlePacket(Packet230ModLoader packet, EntityPlayerMP thePlayer) {
		if(packet.packetType == packetType) {		
			int slotNumber = packet.dataInt[0];
			int pageNumber = packet.dataInt[1];
			String title = packet.dataString[0];
			String text = packet.dataString[1];

		}
	}
```

And data is getting to the server! Now I have to find the right itemstack to add the data. Slot # received in the packet seems about right. Let's use that.

```java
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
```

Yay! Got it working.

## TODO

* [ ] Solve "dyeable books" problem.
* [ ] Add CTRL+V to the editor.
