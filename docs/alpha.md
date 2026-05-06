---
layout: page
title: Alpha ML project
permalink: /alpha/
nav: yes
---

This will be my Alpha 1.1.2_01 related modding project anytime soon. The idea is releasing a jarmod to provide a Modloader, some QOL shit and stuff. Then, my next project will be porting some of my favorite Modloader based Beta / early Release mods. This is the wishlist:

* [ ] Modloader / Modloader MP, ported from r1.2.5. I know there's a Modloader for a1.1.2_01 but it doesn't support SMP and lacks some of the features I'm used to in r1.2.5, plus stuff to make easier to add structures (that is, the missing hooks during the chunk generation stage and at the beginning of the population stage), also proper way to get MC instance and easy reflection.
* [X] McRegion save format.
* [X] Simple SinglePlayerCommands for debugging, needs world.enableCheats.
* [X] Coordinates in F3 GUI.
* [X] Enter Seed when creating a new world.
	- [X] Select "snow covered" YES/NOW/RANDOM (like vanilla)
	- [X] Seed / Snow covered in the server.properties.
* [ ] FOV Slider which many people want (not me).
* [X] 256 block IDs usable (edits Chunk/World)
* [X] Fixed armor behaviour, 'cause Armors in alpha are basicly useless.
* [X] Item subtypes & metadata based subblocks. (ItemBlockWithMeta)
* [X] Fixed block breaking behavior, with the correct durations and drops. - also for wooden planks! [X] Add wooden planks to test.
* [X] Fixed boats - only the "too easy to break" and "drop itself".
* [X] Burning wood produces coal.
* [X] Release leaf decay (less chunk updates)
* [X] Release falling sand (no lag in farlands, less chunk updates)
* [X] Falling sand visible in SMP (i.e. adding it to the entity tracker)
* [X] Track entity paintings.
* [X] Send "this is a winter world" server->client.
* [X] Remember last used server.
* [X] Sky & Indev mapgens, SMP, etc
* [X] Fix to inconsistent caves (random seed not consistent in cave digging)
* [X] Lighting overflow.
* [X] Fix less ores in negative coordinates.
* [X] Fix wool (add metadata for colors, DO NOT use textures but colorizer)
* [X] Correct drops! Revise all blocks.
* [X] Fix Gui edit sign.
* [X] Metadata in server<->client block placement
* ???
* Some optimizations.
* [ ] 256 blocks tall?
* [ ] Dehardcode atlas size & make it bigger?

I especifically don't want to fix bugs 'cause they are heavily explotied by actual a1.1.2_01 players (i.e. Mongster) and those are part of the experience that makes Alpha worth playing on its own. **I just want something that lets me add optional contents easily**. Optional as in you don't want it you leave the mod out.

Whenever I start doing this I'll keep a diary here.

## Diary of sorts

Starting point is the merged codebase RMCPJ 1.2 produces which seems to work (yay!) and saves me a lot of trouble.

### Snow Covered server->client

When the server sends the last Packet1Login to the Client (`NetServerHandler.doLogin()`), it sends it as a dummy signal with "", "", 0. I'm using that 0 (protocol Number) to signal if the world in the server is snow covered (1). `NetClientHandler.handleLogin (p1l)` will set SnowCovered in `this.worldClient` upon reading this value.

### World generator

No such concept exists in this version. There's no world provider, WorldInfo, WorldType, etc. I don't want to go gun-ho with this. I just need an enum which gets saved with the world and a ways to transfer that enum value Server->Client. I can encode many more things in the dummy protocol number, I can index a worldType on that value >> 1.

## Release leaves / sand

Updating those should reduce a vast amount of chunk updates. Also, falling sand is not a tracked entity in SMP play, I have to add this.

* `handleVehicleSpawn` on `NetClientHandler` must support IDs 70 & 71 for falling sand and gravel.
* `EntityTracker.trackEntity` to handle the case of falling sand / gravel.
* `EntityTrackerEntry.getSpawnPacket` to generate the right spawn packets.

### Seed / Enable cheats

Need a new text box in the GUI.

### 256 blocks tall without going full-anvil

After all, we have RAM aplenty these days. But the anvil side of things that's cool is the sub-chunks that allow for better works. Anyhow I know there are 256 block tall mods out there for alpha. Let's look at what they do.

* I've seen an implementation that just adds a new set of arrays to the mix and uses them if y >= 128, in Chunk, but the it goes and ticks the same exact number of random blocks per tick, hence making random stuff twice as slow? - nope, it randomly ticks only the bottom (original) half of the world ... Hum... It also gets clever and it only updates light in the extra space if it "exists".

Will leave this for later.

### Wool 

This looked simple but implied many changes:

* Update the crafting manager to allow for Itemstacks as ingredients.
* Also to support shapeless recipes.

Now I have to make the game able to produce every dye. I'm still solving some. Blue for instance. Might add blue flowers to produce light blue dye that can be combined to make it more dense and produce blue, given the lack of lapis lazuli.

I know this is too much to add, but I like wool. 

### Undo some 

Once this is all working, undo all the colored wool stuff and repurpose it as a modloader mod. ie. leave the infrastructure but add this as a mod:

* Replace BlockCloth with a BlockColoredCloth.
* Replace ItemBlock for BlockCloth for ItemBlockWithMeta.
* Add blue flower block.
* Add all recipes.

### Integrating ML. Client

I made the decission of making this a merged codebase project and maybe I shouldn't have. ModLoader is pretty much different in client/server, and the client side depends on Minecraft.java quite a bit. I'll try to have this going by adding a dummy Minecraft.java to the server, maybe, which contains dummy methods, and a way for ModLoader to decide if it is running on a client or a server.

This is the nth time I've done this. Those are the files & hooks:

#### New files:

* BaseMod
* BaseModMp
* EntityRendererProxy
* ISpawnable
* MLProp
* mod_ModLoaderMp
* ModLoader
* ModLoaderMp
* ModTextureAnimation
* ModTextureStatic
* NetClientHandlerEntity
* Packet230ModLoader

#### Hooks

* BlockDispenser -> ModLoader.dispenseEntity (N/A)
* ChunkProvider -> ModLoader.populateChunk after chunkProvider.populate.
* EntityItem -> ModLoader.onItemPickup(paramyw, this.item); after playsound onCollideWithPlayer.
* NetClientHandler -> ModLoader.serverConnect(this, par1Packet1Login); at the end of handleLogin, also extra hooks at handleVehicleSpawn; ModLoader.serverDisconnect(); at handleKickDisconnect & handleErrorMessage & disconnect, ModLoader.serverChat at handleChat; ModLoaderMp.handleGUI(par1Packet100OpenWindow); @ handleOpenWindow.
* RenderBlocks -> ModLoader.renderWorldBlock at the end of renderBlockByRenderType ; ModLoader.renderInvBlock at the end of renderBlockAsItem ; ModLoader.renderBlockIsItemFull3D at the end of renderItemIn3d.
* RenderManager -> ModLoader.addAllRenderers(this.entityRenderMap); at the end of adding entities to entityRenderMap.
* SlotCrafting -> ModLoader.takenFromCrafting at the end of func_48434_c
* SlotFurnace -> ModLoader.takenFromFurnace(this.thePlayer, paramaan); at the same place.
* TileEntityFurnace -> ModLoader.addAllFuel at getItemBurnTime,

#### Stuff: Spawning

Spawning in alpha is much more simple. There's two `SpawnerAnimals` objects in `PlayerControllerSP`, one for mobs, one for animals. Spawning is performed on the `onUpdate` method. To add new mobs/animals, one should be able to add stuff to the class arrays. The custom list should be injected everytime `PlayerControllerSP` is created. 

ModLoader's `addSpawn` directly adds `SpawnListEntry`s to the right list in the biomes, which makes no sense in Alpha.

For this version, `PlayerControllerSP` hosts lists of classes and Modloader will add classes to those lists.

Problem is, I'm using a merged codebase and those things are done in WorldServer in the server, so this has to be done differently.

I'll maintain the lists in SpawnLists.java, write to them from ModLoader, and consume them from PlayerControllerSP and WorldServer.

#### Stuff: Furnace recipes

There's no such a thing in Alpha. Smeltings are hardcoded in `TileEntityFurnace.getCookedItem`.

### Integrating ML. Server

This is the nth time I've done this. Those are the files & hooks:

#### New files:

* BaseMod
* BaseModMp
* EntityTrackerEntry2
* ISpawnable
* MLProp
* ModLoader
* ModLoaderMp
* Packet230ModLoader
* Pair

#### Hooks

* MinecraftServer -> ModLoader.initialize(this); right before the 1st console message; ModLoader.onTick(this); in onTick.
* ChunkProviderServer -> ModLoader.populateChunk after chunkProvider.populate.
* EntityTracker -> ModLoaderMp.handleEntityTrackers(this, par1Entity); at the end of TrackEntity.
* EntityTracker2 -> full block of integration at the beginning of getSpawnPacket.
* NetHandler -> hook in handleCustomPayload; new `getEntityPlayerMp`
* NetLoginHandler -> ModLoaderMp.handleAllLogins(entityplayermp); at the end of doLogin. 
* NetServerHandler -> ModLoader.serverDisconnect(this.playerEntity); at the end of `handleErrorMessage`; ModLoader.serverChat at the end of `handleChat`; ModLoaderMp.handleCommand at the end of `handleSlashCommand`; 
* SlotCrafting -> ModLoader.takenFromCrafting at the end of func_48434_c
* SlotFurnace -> ModLoader.takenFromFurnace(this.thePlayer, paramaan); at the same place.
* TileEntityFurnace -> ModLoader.addAllFuel at getItemBurnTime,

## Modloader Mods Ideas

* [ ] Colored wool / dies.
* [ ] Snowy mountains, so there's snow in non snowy worlds and you can build with it.
* [ ] Silk touch in gold tools.
* [ ] New material tier to provide "efficiency".
* [ ] Crying Obsidian set spawn thing.
* [ ] Chocolate quest, of course.
* [ ] Stuff I like from Infhell.
* [ ] More zombies.
* [ ] City generation.
* [ ] Baby animals


## Sshs

[Current sources]]({{site.baseurl}}/assets/files/A_112_ML_latest_src.zip) .