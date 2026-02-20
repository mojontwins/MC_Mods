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
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            
            Field[] blockFields = Block.class.getDeclaredFields();
            
            for (int i = 0; i < blockFields.length; i ++) {
                Block b = null;
                
                try {
                    b = (Block)blockFields[i].get(null);
                } catch (Exception e) { }
                
                if (b == originalBlock) {
                    // b is final and can't be overwritten so...
                    blockFields[i].setAccessible(true);
                    modifiersField.setInt(blockFields[i], blockFields[i].getModifiers() & ~Modifier.FINAL);
                    blockFields[i].set(null, newBlock);
                }
            }
            
            if (tooling) {
                // Block may be affected by tools, so we have to patch them.
                
                for (Item item : Item.itemsList) {
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
        
        System.out.println ("Replaced " + originalBlock.getClass() + " with " + newBlock.getClass());
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
### Lighting

Generating the Aether overflows everything big time in many ways. It is using the population stage (that stage that sets a few trees and some flowers, maybe a waterfal, tops, in Vanilla) to generate big islands that overflow the "legal 2x2 chunk area" in every direction, placing thousands of blocks that enqueue a pending relight operation each.

My idea is proxying the world.setBlock calls so it bypasses the normal setBlock and puts stuff directly into the data arrays of Chunk without performing a relight and mark the chunk "dirty" in a set, then, at the end of the process, do an "initial" relight to each dirty chunk in the same way it is performed when chunks are generated. This way the amount of relight checks should decrease dramatically, but I don't really know if this is going to work :D 

### Tricky portal shenanigans

I need the main minecraft timer to perform the portaling effect on screen, but it being a private attribute in Minecraft.java makes it trickier. I don't know if asking reflection for the value I need (renderpartialticks) every frame would be overkill. Let's see.

It wouldn't be so difficult if Modloader for b1.2 did actually work in Eclipse. I mean, I made it load mods from the environment temporally so I can develop this and I might do the same for other stuff, crossing my fingers so it will still work with the "vanilla" b1.2_02 Modloader.

Another thing - the code I use to run the portal is running from the GUI hook so it means it doesn't run at the game pace i.e. 20 ticks per second. This is the first thing I must ensure: make it work at the correct rate. Later modloaders have a proper tick hook. Gotta solve this and make my own tick hook SOMEHOW lol. Let's see the main tick method in Minecraft in case I can do something very creative. OR get my own timer and run with every tick from inside the OSD hook.

```java
    @Override
    public void OSDHook(Minecraft mc, boolean inGUI) {
        this.timer.updateTimer();
        
        for(int i = 0; i < this.timer.elapsedTicks; i ++) {
            this.tick(mc);
        }
        
    }
```

Also - I have to solve what happens if you die in the Aether. Will this we wise enough to put you in the proper dimension? Hmmm. 

When you die, `Minecraft.respawn()` is executed, which jumps directly to `usePortal` if the current world provider won't let you respawn (which is the case). In this version, usePortal just switches from 0->-1 or from -1->0. If you happen to have a different value in `player.dimension`. which is the case, i.e. 1, it will set -1 as a destination :-/.

```java
    public void usePortal() {
        if(this.thePlayer.dimension == -1) {
            this.thePlayer.dimension = 0;
        } else {
            this.thePlayer.dimension = -1;
        }
        // ...
    }
```

This is BAD. If you die in the Aether you will respawn in the Nether. Not cool. How can I change this behavior with the tools I have... I don't know yet. If `Player.dimension` wasn't used elsewhere I could just set it to -1 for custom dimensions - it is only used so the server can tell which dimension it is working, and not elsewhere. So this hack may work... For this version, at least. I will need a proper Dimension mod with proper hooks if I ever port this to a later version.

Problem: Have to check this -> if you exit the game while in the aether, you will be in the nether when you load back? What is saved to worldType? ...  This needs a different solution. For instance "let me respawn in the aether" + "set the aether spawn point to the portal coordinates".

Not a good solution either. This is what happens when level.dat exists, i.e. when the World is loaded:

```java
    if(var18.exists()) {
        try {
            NBTTagCompound var8 = CompressedStreamTools.func_1138_a(new FileInputStream(var18));
            NBTTagCompound var9 = var8.getCompoundTag("Data");
            this.randomSeed = var9.getLong("RandomSeed");
            this.spawnX = var9.getInteger("SpawnX");
            this.spawnY = var9.getInteger("SpawnY");
            this.spawnZ = var9.getInteger("SpawnZ");
            this.worldTime = var9.getLong("Time");
            this.sizeOnDisk = var9.getLong("SizeOnDisk");
            if(var9.hasKey("Player")) {
                this.nbtCompoundPlayer = var9.getCompoundTag("Player");
                int var10 = this.nbtCompoundPlayer.getInteger("Dimension");
                if(var10 == -1) {
                    var17 = new WorldProviderHell();
                }
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        }
    }
```

If dimension is -1 it will go to hell, otherwise it will be in the overworld. So if I fix making the player able to respawn in the aether (i.e. dimension remains 1), it will spawn in the overworld upon reloading.  If I don't, it will respawn in the nether. No solution is good. 

I wanted to avoid this but it seems that I need a proper Dimension API as a JAR mod... URM. The original has world patched so it loads the proper dimension. Can I Proxy "world"? I doubt that.

### A Dimension Mod

So we are throwing away our ModLoader based mod_MTDimension and do a proper jarmod that can sit alongside ModLoader (or not!). So it should not modify any classes that modloader modifies.

What do I need? 

* `UsePortal` in Minecraft.java ? Or I could just not use it as I have to modifiy `EntityPlayerSP` anyways.
* `EntityPlayerSP` - the `timeInPortal` shit is here. 
* `The portal effect is in `GUIIngame` ... which needs to be modified by Modloader. Unless I find a different way to do this, it seems that it won't be able to use a custom overlay.
* `World` needs to be multiple dimension aware.
* I can use the same `Dimension` objects I was using and also need to implement a new `UsePortal`.

I'll check any existing early dimension mods to get some inspiration.
