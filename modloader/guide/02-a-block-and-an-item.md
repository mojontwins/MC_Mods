# 2. A couple of blocks and an item.

In this chapter we'll learn how to start a new mod, how to add blocks and items, and a simple way to debug what you have created ingame (unless you go and install NEI or the like): We'll be creating a chest at spawn and filling it with the new blocks and item.

## Creating a new mod

To create a new mod you basicly start by extending the BaseModMP class. Modloader scans the minecraft.jar and the mods folder to find clases that extend BaseModMP and, if they are correct, hey load them as mods. There's a startup method you have to implement plus several other things to override to get what you want.

The easiest way to extend BaseModMP is right-clicking on the `net.minecraft.src` package in the client and selecting New - Class. This will pop up a dialog. Enter your mod name (which should be `mod_Whatever`, for example `mod_fungalCalamity`) in the "Name" field, enter "BaseModMP" in the "Superclass" field, and then make sure to check "Constructors from superclass" and "Inherited abstract methods". Click "Finish" and you should get something like this:

```java
	package net.minecraft.src;

	public class mod_fungalCalamity extends BaseModMp {

		public mod_fungalCalamity() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String Version() {
			// TODO Auto-generated method stub
			return null;
		}

	}
```

This is where we start. Edit the `Version` method to return a proper string

```java
	@Override
	public String Version() {
		return "Fungal Calamity v1.0";
	}
```

## Our mod

Our goal is adding a big, branched mushroom (not unlike those in Twilight Forest) that spawns around spots of podzol that you may find in swamps. We'll be adding a new mob too, the Fungal Calamity, which will spawn near the mushrooms, and who can chase you and throw you an explosive mushroom. We'll add a gameplay dynamic: if you hit the mushroom it won't exploade and you'll be able to pick it up and throw it as well.

In this chapter we'll add new blocks and the mushroom item.

## Blocks

The world is made of blocks. In every (x, y, z) there's a block and metadata. Such metadata is used to provide a state to the block, or to define a subtype. The growth of crops, different wool colors, wood types or flowing water direction are achieved with metadata. We call "Blockstate" to the pair formed by a block and its associated metadata.

## Adding blocks

ModLoader is rather barebones. It provides a number of hooks so you can add stuff to the game, and nothing else really. The most knowledge you need for creating mods using ModLoader is of the actual Minecraft engine and how it works. The best practice is keep the classes involved open for reference. 

For example, to add blocks you just create new objects of a class that extends (or *is*) `Block` the same way normal blocks are defined. The only difference is that you define and instantiate them in your mod class, and that you have to call the ModLoader API so they get added to the right parts of the Minecraft engine, which is called *registering the block*.

An instance of the class `Block` is a basic, opaque, one texture for all faces block that drops itself when broken. If you want to extend or modify this you have to implement your own class that extends `Block`. Basic blocks, tho, have several properties you can modify via method chaining upon instantiation, something that looks like this:

```java
	public static final Block cobblestone = new Block(4, 16, Material.rock)
		.setHardness(2.0F)
		.setResistance(10.0F)
		.setStepSound(soundStoneFootstep)
		.setBlockName("stonebrick");    
```

The basic `Block` constructor takes three parameters: 

* A unique `blockID`, which can be 0-4095 (or 0-255 before r1.2), although only values ranging from 0 to 255 can be used for terrain generation as only byte arrays are used during that stage. 

* A texture index `blockIndexInTexture`, which locates the block texture in the texture atlas `terrain.png` which contains all 256 textures.

* The block material `blockMaterial`, which is an object of the class `Material`, indicating what the block is made of. Some available materials are `grass`, `ground`, `wood`, `rock`, `iron`, `water`, `lava`, `leaves`... open the `Material` class to get a full list. 

So the above example for the vanilla cobblestone block (that you can find in `Block.java`) will define `cobblestone` as a new block with ID 4, texture index 16, and made of `Material.rock`.

After the constructor there's a series of chained setters to give the new block some properties like hardness, blast resistance, associated sounds, and block name. `.setBlockName` which gives the new block a name, is **mandatory** if you are using `ModLoader`. Available setters are:

(NOTE, when I talk about `side` that's 0 bottom, 1 top, 2 north, 3 south, 4 east, 5 west)

* `public Block setBlockName(String name)` gives this block a name. This is needed by `ModLoader`. It won't let you register a block if it doesn't have a name.

* `protected Block setStepSound(StepSound s)` sets the sounds associated with the block. It says "step" but it's also "break". Available sounds are instances of `StepSound` such as `soundStoneFootstep`, `soundWoodFootstep`, `soundGrassFootstep`... etc. Again, check the `Block` class for a full list, as they are defined in there. 

* `protected Block setLightOpacity(int value)`, with value ranging from 0 to 255, defines how much light this block "blocks" (sorry). A transparent block (like air, or glass), or blocks like flowers or tall grass won't block light at all, so this will be 0. Opaque blocks like planks or dirt will block light completely, so this will be 255. That is done automaticly by the `Block` constructor. If you need a different value you have to define it explicitly using this setters. Blocks with special values are, for example, leaves, which have a value of `1` (that's why shadows under trees are *soft*) or water, which has a value of `3` (that's why the deeper, the darker).

* `protected Block setLightValue(float light)` defines the amount of light this block **emits**. This is represented by a value of 0 to 1. Default is 0, which means that the block does not emit light. Torches have a light value of `0.9375F`, redstone torches `0.625F` and lava and fire a value of `1`. Brown mushrooms emit some light (value `0.125F`). Active furnaces have a value of `0.875F`.

* `protected Block setResistance(float resistance)` defines the block's blast resistance, i.e. how much it resists explosions. To find the right value for your block just look around the vanilla blocks for reference.

* `protected Block setHardness(float hardness)` defines the block's hardness, i.e. how hard it is to break. To find the right value for your block just look around the vanilla blocks for reference. A value of `-1` makes the block indestructible.

* `protected Block setBlockUnbreakable()` is an alias for `setHardness(-1)`.

* `protected Block setTickOnLoad(boolean shouldTick)` tells the engine it should tick this block automaticly. This is used for blocks which need to change their state or do something over time, such as leaves decaying, sand falling, fire, crops growing or water spreading.

* `protected Block disableStats()` the engine will not keep stats for this block.

If you need to customize your block further than that (which will be true in most cases) you'll have to extend `Block` with a new class and override a number of methods. Listing all of them here will get boring very soon. We'll talk about some as we are adding stuff to the mod, and a comprehensive list will come eventually. The most common methods to be overriden when you extend `Block` are follwing. Don't worry if you don't get some concepts, we'll get to them. You can use this as a reference in the future:

* `public boolean renderAsNormalBlock()`, default true, i.e. the block is a opaque cube that covers the whole space. If you are using a special render, this should be overriden to return false.

* `public int getRenderType()`, default 0. The block renderer uses different renderers for different block types. General render types are those (I'm listing some you will be reusing most likely; there's more - study `RenderBlocks` for the full list):
	* Type 0 is for cubic blocks.
	* Type 1 is for "crossed squares" (used in flowers, mushrooms, tall grass or saplings).
	* Type 2 is used for torches.
	* Type 3 is that of fire.
	* Type 4 is used by fluids (water and lava).
	* Type 5 is redstone wire.
	* Type 6 is for crops (two planes intersecting two planes).
	* Type 7 is used for doors.
	* Type 8 is used with ladders.
	* Type 9 renders minecart tracks.
	* Type 10 is used by stairs.
	* Type 11 is used by fences.
	* Type 13 is used by cacti.

* `public int getRenderBlockPass()` - Minecraft sorts and renders blocks in two passes. Pass 0 is for solid blocks, and pass 1 is for traslucent blocks such as ice or water. By default it returns 0. Override to return 1 if you are implementing a traslucent block.

* `public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)` tells the renderer if a side should be rendered or not. By default, a side will be rendered if the block next to it is not opaque or of such face doesn't touch the block boundaries. For example, the top side of a slab will always be rendered no matter which block is on top, as that face doesn't touch the block upper boundary, but the right side of a dirt block shouldn't be rendered if another dirt block, which is opaque, is to its right. Few vanilla blocks override this method, most of them being transparent or traslucent blocks.

* `public int getBlockTextureFromSideAndMetadata(int side, int metadata)` returns the index in the texture atlas for the block, depending on the side and the metadata associated with the block position in the world. By default, it returns `blockIndexInTexture`, i.e. the same texture for all faces. If you want a different texture for each face or different textures depending on metadata (think on stone brick variants, or furnaces facing different directions) you have to override this method.

* `public int getBlockTextureFromSide(int side)` is the version of the above called generally to render the block in the inventory when metadata makes no sense. It's up to the block class to "assume" a metadata. Generally 0. Note that if you don't need metadata to select textures you can implement this method only, as `getBlockTextureFromSideAndMetadata` just calls `getBlockTextureFromSide` in `Block`.

* `public boolean isOpaqueCube()` tells if the block is Opaque, meaning that the block takes the full space and blocks light. It returns true by default. If you are adding a flower you'll have to override it to return false, for instance.

* `public boolean canPlaceBlockAt(World world, int x, int y, int z)` returns true if you can place the block at (x, y, z). This is overriden for plants, for example, to check if there's valid ground beneath (although if you extend `BlockPlant` there's a number of different methods to keep in mind - don't worry, we'll be adding a plant in a future chapter).

* `public boolean canBlockStay(World world, int x, int y, int z)` is called to check if the block can stay in (x, y, z) depending on the surroundings. For example, a flower could not stay if the block beneath was removed. This method is also used by world generators, wich are the classes that populate chunks after they have been generated (they add trees, flowers...)

* `public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)` think of this as a more specific version of the method above, which also gets the side of the block you want to attach your block at. (x, y, z) is where your block would be if this would success.  By default, this calls `canPlaceBlockAt` ignoring the side.

* `public void onNeighborBlockChange(World world, int x, int y, int z, int blockID)` imagine that your block is at (x, y, z). If a block next to it changes (is removed or added, for example). Then this method is called, with the (x, y, z) coordinate **where your block is located** and the blockID of the changed block. Please remember this, (x, y, z) is YOUR BLOCK, not the block which changed. This is done to react to nearby changes. For example if you want to destroy your block if the ground below disappears, you check what's in (x, y - 1, z) and act accordingly.

* `public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata)` is called when a player destroys the block. It gets the coordinates and the metadata it had before disappearing. By default it does nothing.

* `public void onBlockDestroyedByExplosion(World world, int x, int y, int z)` is called when an explosion destroys the block. By default it does nothing.

* `public void onBlockAdded(World world, int x, int y, int z)` is called when your block is added to the world - for example when you place it. (x, y, z) is where it has been added. Concretely, it is called by the `Chunk` class when (x, y, z) is set to a block ID which is not 0 (air).

* `public void onBlockRemoved(World world, int x, int y, int z)` is called when your block is removed from the world - for example when you destroy it. (x, y, z) is where it was. Concretely, it is called by the `Chunk` class when (x, y, z) is set to 0 (air).

* `public void onBlockPlaced(World world, int x, int y, int z, int side)` is called when a player right-clicks to place a block. It's usually used to set metadata, for instance to attach a torch to a wall.

* `public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity)` is called right after the above, this time passing the entity (i.e. the player) who placed the block. It can be used, for example, to check where the entity is facing and place the block facing the entity (as in furnaces).

* `public int quantityDropped(Random rand)` returns is the amount of items the block drops when broken. By default this method returns 1. 

* `public int idDropped(int meta, Random rand)` returns the ID of the item the block drops when broken. By default this is the blockID, which means a block will drop itself by default. `meta` contains the metadata associated before breaking.

* `protected int damageDropped(int metadata)` `damage` is a VERY bad name for what it is. Granted, that value is used for damage, but it is also used for dye color, wood type and many other things. This method gets the metadata the block had before being broken and returns a value based on that. By default this is 0. For example, in `BlockLog` that represent logs, metadata is used to store wood type (and later, orientation). If this method wasn't overriden in `BlockLog` all logs would drop oak logs. Rather, this block is overriden and it returns `metadata & 3` which produces 0, 1, 2 representing oak, dark or birch. 

* `public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance)` contains the code that executes when a block breaks. By default it will drop `quantityDropped` item stacks of `idDropped` with `damageDropped(metadata)`. You can override this for complex block drops, but most of the time it will suffice to change `idDropped`, `quantityDropped` and `damageDropped`. 

* `public void updateTick(World world, int x, int y, int z, Random rand)` if you called `setTickOnLoad` this method will be called when the block is ticked. This is where you implement crop growing, fire spreading and stuff like that. By default this method is empty.

* `public void randomDisplayTick(World world, int x, int y, int z, Random rand)` in the client, every game tick, 1000 random blocks around the player are selected and this method is called for them. This is used to display special stuff such as particles (think of smoke in torches). By default this method is empty.

* `public boolean blockActivated(World world, int x, int y, int z, EntityPlayer thePlayer)` is called when `thePlayer` right-clicks on your block. (x, y, z) is where your block is. By default this does nothing.

* `public void onBlockClicked(World world, int x, int y, int z, EntityPlayer thePlayer)`  is called when `thePlayer` left-clicks on your block. (x, y, z) is where your block is. By default this does nothing.

* `public void onEntityWalking(World world, int x, int y, int z, Entity entity)` is called when `entity` steps over your block, which is placed at (x, y, z). This is used for example for trampling tilled field.

* `public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)` called when `entity` collides with our block, which is at (x, y, z). This used by cactus to cause damage or by pressure plates to get pressed.

* `public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)` is called by several processes and lets you adjust the size of the block's bounding box before it's going to be used for something. If the block's bounding box depends on metadata which may change (for example) this is where you check metadata at (x, y, z) and update the bounding box (for example calling `this.setBlockBounds` with the new dimensions).

* `public int getRenderColor(int metadata)` returns a color in ARGB format. This is used for example to give special colors to grass and leaves. By default it returns white.

* `public int colorMultiplier(IBlockAccess world, int x, int y, int z)` that's the same but it is called by the world renderer so grass and leaves can have a different color per x, z coordinate (for example). By default it returns white.

* `public boolean canProvidePower()` returns if the block is a redstone power source. By default returns false.

** TODO: Check those two again

* `public boolean isPoweringTo(IBlockAccess world, int x, int y, int z, int side)` returns true if this block at (x, y, z) is providing power to its side `side`.

* `public boolean isIndirectlyPoweringTo(World world, int x, int y, int z, int side)` returns true if this block at (x, y, z) is providing power to its side `side`.

** EOTODO

### Podzol

Podzol is a very basic, opaque block but it needs three different textures (top, bottom and sides), so you'll have to extend `Block` with your own class that implements that feature. The renderer calls `getBlockTextureFromSideAndMetadata` to get the block texture, so that's the method you'll need to override.

I like to keep all mod classes together in their package. Right click on Client and select New - Package. Create a new package called `org.mojontwins.minecraft.fungalcalamity`. Now right click on the package and select New - Class. Type `BlockPodzòl` the "Name" field, `net.minecraft.src.Block` in the "Superclass" field, and then make sure to check "Constructors from superclass" and "Inherited abstract methods". Click "Finish". Here it is, your new block class:

```java
	package org.mojontwins.minecraft.fungalcalamity;

	import net.minecraft.src.Block;
	import net.minecraft.src.Material;

	public class BlockPodzol extends Block {

		public BlockPodzol(int var1, Material var2) {
			super(var1, var2);
			// TODO Auto-generated constructor stub
		}

		public BlockPodzol(int var1, int var2, Material var3) {
			super(var1, var2, var3);
			// TODO Auto-generated constructor stub
		}

	}
```

Delete both constructors, we'll need something much simpler, as we won't be reusing this class for anything else. Add this new constructor. `Material.ground` will be marked as an error as eclipse is not able to find it. Just press Ctrl+Shift+O and eclipse will add the required import:

```java
	package org.mojontwins.minecraft.fungalcalamity;

	import net.minecraft.src.Block;
	import net.minecraft.src.Material;

	public class BlockPodzol extends Block {

		public BlockPodzol(int blockID) {
			super(blockID, Material.ground);
		}

	}
```

We'll customise this block later. Now get back to `mod_FungalCalamity`.

Podzol will act like Mycelium. It will spread to nearby dirt or grass tiles. That will need to call `setTickOnLoad` on instantiation.

First of all create a class attribute for the block.

```java
	public static Block bolckPodzol;
````

Remember that blocks need a block ID. We could choose a free block ID, but that could conflict with another block ID in another mod so it is good practice to let the player configure your mod to choose the block ID theyself. To do that, we add an anotated attribute. ModLoader will find these attributes and create a configuration file for them automaticly. What we assign here is a default value:

```java
	@MLProp(name="blockPodzolID", info="Custom block ID for the Podzol block")
	public static int blockPodzolID = 140;
```

We need to create attributes to store the texture IDs. We'll need three texture indexes for the pozdol block:

```java
	public static int texIdxPodzolTop;
	public static int texIdxPodzolBottom;
	public static int texIdxPodzolSides;
```

We have everything we need to instantiate `blockPodzol`. We do this in the constructor (for b1.7.3) or in the load method (for r1.2.5):

```java
	public mod_fungalCalamity() {
		bolckPodzol = new BlockPodzol(blockPodzolID).
				setBlockName("Podzol").
				setTickOnLoad(true);
	}
```

Note how we use `blockPodzolID` as a block ID. If the player changed the ID in the automatic config file, the correct value will be already loaded before constructing the class. 

And now we load our custom textures. We need to add "texPodzolTop.png" and "texPodzolSides.png" to the package. For the botom we'll be using the vanilla dirt texture. Then we load the new textures. Again, you wanna do this in the `load` method in r1.2.5, but it's done in the constructor in `b1.7.3`:

```java
	texIdxPodzolTop = ModLoader.addOverride(
			"terrain.png", 
			"/org/mojontwins/minecraft/fungalcalamity/texPodzolTop.png");
	
	texIdxPodzolBottom = 2; 	// This is the dirt texture in the atlas

	texIdxPodzolSides = ModLoader.addOverride(
			"terrain.png",
			"/org/mojontwins/minecraft/fungalcalamity/texPodzolSides.png");
```

Now we have custom texture indexes, we get back to BlockPodzol to override a method:

```java
	@Override
	public int getBlockTextureFromSide(int side) {
        switch(side) {
        case 0: return mod_fungalCalamity.texIdxPodzolBottom;
        case 1: return mod_fungalCalamity.texIdxPodzolTop;
        default: return mod_fungalCalamity.texIdxPodzolSides;
        }
    }
```

What does this do? When Minecraft needs to render a block, it calls `getBlockTextureFromSideAndMetadata`. In `Block`, this method ingores metadata and calls `getBlockTextureFromSide`. If we would have needed metadata, we should have overriden `getBlockTextureFromSideAndMetadata`. 

Notice how we return a different texture based on which side the engine is calling. Side 0 is bottom and side 1 is top. For the rest of the cases (sides 2 to 5) we return the same texture.

We need this block to extend to adjoining `dirt` or `grass` blocks if they are not covered. This will be attempted when the block is ticked (the game will tick a number of blocks at random each game tick). That's why we added `setTickOnLoad(true)` to the constructor, se the block is ticked.

When a block is ticked, its `updateTick` is called. This is the next method we need to override. The code is taken and modified from vanilla `BlockGrass` but made simpler (and more aggressive). Note the `multiplayerWorld` check on World. This kind of stuff ensures that block updates only happen in the server on SMP (and get sent to the client).

```java
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		// Attempt to expand if the world isn't remote
		if(!world.multiplayerWorld) {
			if(rand.nextInt(4) == 0) {
				int xx = x + rand.nextInt(3) - 1;
				int yy = y + rand.nextInt(5) - 3;
				int zz = z + rand.nextInt(3) - 1;
				
				int blockID = world.getBlockId(xx, yy, zz);
				
				if(
					(blockID == Block.dirt.blockID || blockID == Block.grass.blockID) && 
					Block.lightOpacity[world.getBlockId(xx, yy + 1, zz)] < 2
				) {
					
				}
			}
		}
	}
```

Finally we have to *register* the block and give it a name. That integrates the block in the game.

```java
	ModLoader.RegisterBlock(blockPodzol);
	ModLoader.AddName(blockPodzol, "Podzol");
```

And that's it! We got ourselves a new block.

### Big mushroom block (only for b1.7.3)

We'll just grab the code for the mushroom block (`BlockMushroomCap`) and put it in our mod. Then we do the necessary steps to enable it in the mod class:

## Notes about world

The World class is pretty complex and has lots of useful methods you'll be using continuously. Some of the most used are:

* `public int getBlockId(int x, int y, int z)` will return the block ID at (x, y, z) in the world.

* `public Material getBlockMaterial(int x, int y, int z)` will return the material of the block at (x, y, z).

* `public int getBlockMetadata(int x, int y, int z)` will return the metadata at (x, y, z).

* `public boolean isAirBlock(int x, int y, int z)` will return true if there's air at (x, y, z).

* `public boolean blockExists(int x, int y, int z)` will return true if the block at (x, y, z) exists (that part of the world has been generated).

* `public boolean setBlockAndMetadata(int x, int y, int z, int blockID, int meta)` will place block with blockID at (x, y, z) with metadata and return true if that was successful.

* `public boolean setBlockAndMetadata(int x, int y, int z, int blockID)` will just set the block ID.

* `public boolean setBlockMetadata(int x, int y, int z, int meta)` will just change the metadata.

* `public boolean setBlockAndMetadataWithNotify(int x, int y, int z, int blockID, int metadata)` will set new blockID and metadata and will notify adjoining blocks. That notification will trigger each adjoining block's `onNeighborBlockChange` method. 

* `public boolean setBlockWithNotify(int x, int y, int z, int blockID)` will set a new blockID and notify the adjoining blocks.

* `public void setBlockMetadataWithNotify(int x, int y, int z, int meta)` will set new metadata and notify the adjoining blocks.

* `public boolean canBlockSeeTheSky(int x, int y, int z)` will return true if the op face of (x, y, z) is exposed (i.e. there's "sky" above).

* `public int getHeightValue(int x, int z)` will return the height of the world at (x, z) (i.e. the top "y" of the highest block that's not air).

* `public boolean isDaytime()` returns true during daytime.

There's many more but those will suffice for now.

## Adding an item

We are adding a throwable mushroom item. It won't do much for the moment, just exist. In a future chapter we'll make it become a projectile upon usage.

Items extend the `Item` class. Just like blocks, you can define some attributes by instantiating the `Item` class, but special stuff will need a subclass. The basic `Item` constructor just takes one parameter, the ID:

```java
	protected Item(int id);
```

Note that block and item IDs share he same space. The actual item ID is the one you pass to the constructor plus 256. That's why it's called `shiftedIndex`. So `Item flint = new Item(62)` will give flint a shifted index of 256 + 62. This made perfect sense in pre-Anvil minecraft which just had space for 256 blocks. So the first ID after the last block (256) was that of the first item. 

Just like Blocks, Item have a number of setters you may call upon object creation:

* `public Item setIconIndex(int texIdx)` will asign a texture index to the item. The atlas for items is `/gui/items.png`. 

* `public Item setMaxStackSize(int stackSize)` will set the maximum number of the same item that can be stacked. This is, by default, 64.


Once you decide to subclass `Item` with your own class there's a number of useful methods to override, for example:

* `public int getIconFromDamage(int damage)` this is another instance of not very appropriate naming. Damage is something like "an item's metadata", a value that defines the state of the current item. Item instances exist in the world as ItemStacks, which define an item shiftedIndex, a stack size, and a "damage" or item state. It's called *damage* 'cause the first item states were used indicate tool damage, but they are used for all kind of state related stuff: dye colors being a good example. This method selects a different texture index based on the item state or *damage*.



## Let's test it in a cool way

## Now port it to the server

## Generating your mod for the first time

## Notes about placing blocks

When you right-click to place a block, `ItemBlock` does this, in order:

* It checks if the block can be placed, which may include a call to `canPlaceBlockOnSide`. 
* It places the block in the world, which produces a call to `onBlockAdded`.
* Then `onBlockPlaced` is called passing x, y, z of the placed block and the side that was right-clicked.
* Then `onBlockPlacedBy` is called, passing x, y, z of the placed block and the player who right-clicked.

