---
layout: page
title: Early Aether builds & fix
permalink: /earlyaether/
---

## Early Aether build for b1.2_02

![En early portal]({{site.baseurl}}/assets/img/2026-02-04_11.29.03.png)

I found and isolated a very early Aether mod build for Minecraft beta 1.2_02. One of the developers, Kodaichi, saved a backup of their dev folder with just the patched minecraft jars containing many versions with several mods. One of the jars contained this early indev version of the Aether mod alongside a number of different mods by other people (mo creatures, for instance) all patched into the jar.

As distributing patched minecraft jars is not a good idea, I painstakingly isolated the Aether mod from the rest. The process has required to actually decompiling and rebuilding (because it was mixed by SPC/WorldEdit and I couldn't find the jars and I decided to remove it from the files as it was not really needed), so sadly it needs Java 8 to run now.

![En early Aether!]({{site.baseurl}}/assets/img/2026-02-04_11.57.40.png)

The mod incorrectly generates stuff in the overworld (because it is obviously in a very early state of development). You can travel to the Aether dimension building the glowstone portal but you have to activate it with fire using flint&steel like a Nether portal.

When you travel to the aether it can take a lot of time to generate terrain as it is constantly overflowing the early lighting system inherited from alpha that b1.2 still uses. Just have patience.

I've uploaded the mod and a MultiMC instance. The mod includes modloader and some base class edits and has to be used as a jarmod to b1.2_02.

* [Get the mod]({{site.baseurl}}/assets/files/mod_aether_b1.2_02.zip).
* [Get the MultiMC instance]({{site.baseurl}}/assets/files/mod_aether_b1.2_02__MultiMC-instance.zip).

## Fixing this

I think this may be interesting. The original is there for archival, but I think this could be used to actually play it so my goal is turning this into a real standalone modloader mod that doesn't rely on altering base classes *and* making it playable. So this is my roadmap:

* [X] Getting b1.2_02 Modloader to compile, work and actually producing builds of mods. Ideally using RetroMCP Java but if I have to rely on old MCP I will.
* [X] Devising a way to add and activate my own custom portal without having to modify base classes. 
* [ ] Devising a way to add a Dimension without having to modify base classes.
* [ ] Port the actual code without the cheap retexturing used to have vanilla trees looking different. That means adding the missing blocks and also replicating the tree generators.
* [ ] Add ores and let animals spawn so you can actually live in the early aether. Animals don't spawn 'cause they need vanilla grass to do so, but this can be worked around.
* [ ] Multi dimension SMP is not possible with this version. The server only runs one WorldServer, and multi-dimensional support wouldn't be added until b1.6 Test Buld 3. Adding multi-dimensional support to the server is out of the scope of this project. What I can do is a means of starting a custom dimension world in the server in the same way you can run a nether server.

## Some roadmap entries in detail

### Getting b1.2_02 Modloader to compile, work and actually producing builds of mods.

This is what I had to do:

* Modloader / Modloader MP for b1.2_02, or at least the version that's available in the MCArchive, don't work out of the box. I had to fix them. So I...
	* Set up b1.2_02 in RMCPJ.
	* Decompile.
	* Fixed the sources in eclipse.
	* `Build` in RMCPJ.
	* Got zips with the fixed Modloader mod for the client and the server.
* Now I need an environment where the Modloader modded jars are the base version. So I...
	* Set up b1.2_02 in RMCPJ
	* Patch the jars with the zips generated in the previous step.
	* Decompile.
	* `Build` in RMCPJ produces empty zips -> I did it right.

### Fix to modloader

You won't be able to make this work from Eclipse unless you Patch modloader towards the end of `init`, right after it calls `readFromClassPath` you should add:

```java
	readFromClassPath(var3);
	// Fix so it works from Eclipse:
	addModDirectory(var3.getParentFile(), ModLoader.class.getClassLoader());
```

Otherwise it won't load mods from Eclipse, only when built.

### A way to add and activate my own custom portal.

Portal activation code is triggered from `BlockFire`, in a very Notchy hardcoded way. In `onBlockAdded`, if the block below happens to be obsidian, `Block.portal.tryToCreatePortal` is called. The way to do this without having to resort to base classes is replacing `BlockFire` with a proxy. This is how you replace a block:

1.- Using reflection, you find and grand yourself access to the attribute in `Block` that represents the block you want to change, i.e you gain access to `Block.fire`.
2.- You assign your new block instance to that attribute. `Block.fire = blockFireProxy`.
3.- Iterate `Item.itemlist`, for each item, If it is an ItemTool, and if that tool was effective against the original block, add your new block to the list (or replace existing). This is not needed for blockFire. But I'll be adding a general way to do this in case I need it in the future.

You can use your proxy to refine whatever you want. In this case, we'll be detecting glowstone to fire up our own portal.

```java
	public void replaceBlock(Block originalBlock, Block newBlock, boolean tooling) {
		
		// Find the block we want to replace in Block, and overwrite it
		
		try {
			Field[] blockFields = Block.class.getDeclaredFields();
			
			for (int i = 0; i < blockFields.length; i ++) {
				// b is final and can't be overwritten so...
				blockFields[i].setAccessible(true);
				blockFields[i].set(null, newBlock);
			}
			
			if (tooling) {
				// Block may be affected by tools, so we have to patch them.
				
				Item[] items = Item.itemsList;
				for (Item item : items) {
					if(item instanceof ItemTool) {
						// blocksEffectiveAgainst is the first field in ItemTool:
						Field blocksEffectiveAgainstF = ItemTool.class.getDeclaredFields()[0];
						blocksEffectiveAgainstF.setAccessible(true);
						Block[] blocksEffectiveAgainst = (Block[]) blocksEffectiveAgainstF.get(item);
						
						// Search if the original block is in the array; if so: substitute & break;
						for(int i = 0; i < blocksEffectiveAgainst.length; i ++) {
							if (blocksEffectiveAgainst[i].equals(originalBlock)) {
								blocksEffectiveAgainst[i] = newBlock;
								blocksEffectiveAgainstF.set(item, blocksEffectiveAgainst);
								break;
							}
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
```

Our proxy for BlockFire will be very simple

```java
	public class BlockFireProxy extends BlockFire {

		protected BlockFireProxy(int id, int texId) {
			super(id, texId);
		}
		
		// We patch this method to allow for our custom portal to fire up as well
		@Override
		public void onBlockAdded(World world, int x, int y, int z) {
			if(world.getBlockId(x, y - 1, z) == Block.lightStone.blockID) {
				// Do our portal
				
			} else super.onBlockAdded(world, x, y, z);
		}
	}
```

### A new Dimension

There isn't a proper abstraction for a Dimension in minecraft, which sucks. There've been simple mods that address this situation from early on. The Pudelhund's Dimension API didn't come out until early release. ShockAPI added Dimension abstraction too, but it didn't come out until b1.7.3. As I don't know any better, I'm making my own Dimension abstraction layer that I can reuse / port to other versions.

In order to not having to change any base classes, this library will do the dimmension hopping itself. This process is triggered from `EntityPlayerSP` which calls `Minecraft.usePortal`. Maybe I can use `OSDHook` keep my own set of variables and do the portalling myself with different triggers.

1.- The portal block implements `IBlockPortal` which makes it implement `getDimension ()`.
2.- When the portal block collides with the player, a counter is started to do the animation, which will be performed in `OSDHook`, and the IBlockPortal is stored.
3.- When the counter gets to 1.0F, my mod's `usePortal` is called. It will use the stored `IBlockPortal` to know the dimension and do the thing.

New portal blocks have to set the mods variables the same way vanilla Portal blocks set EntityPlayerSP's variables. The actual teleporting will be performed by the mod as well when timeInPortal reaches 1.0F.

### Blocks

Aerclouds have to stop the player from falling and not cause damage but Entity's fallDistance is protected, so we have to...

```java
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		try {
			Field fallDistanceF = entity.getClass().getDeclaredFields()[34]; // 34 is "fall distance"
			fallDistanceF.setAccessible(true);
			fallDistanceF.set(entity, 0.0F);
		} catch (Exception e) {}
	}
```

