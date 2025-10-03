# 2. A couple of blocks and an item.

In this chapter we'll learn how to start a new mod, how to add blocks and items, and a simple way to debug what you have created ingame (unless you go and install NEI or the like): We'll be creating a chest at spawn and filling it with the new blocks and item.

## Creating a new mod

To create a new mod you basicly start by extending the BaseModMP class. Modloader scans the minecraft.jar and the mods folder to find clases that extend BaseModMP and, if they are correct, hey load them as mods. There's a startup method you have to implement plus several other things to override to get what you want.



## Our mod

Our goal is adding a big, branched mushroom (not unlike those in Twilight Forest) that spawns around spots of podzol that you may find in swamps. We'll be adding a new mob too, the Fungal Calamity, which will spawn near the mushrooms, and who can chase you and throw you an explosive mushroom. We'll add a gameplay dynamic: if you hit the mushroom it won't exploade and you'll be able to pick it up and throw it as well.

In this chapter we'll add new blocks and the mushroom item.

## Adding blocks

ModLoader is rather barebones. It provides a number of hooks so you can add stuff to the game, and nothing else really. For example, to add blocks you just create new objects of a class that extends (or *is*) `Block` the same way normal blocks are defined. The only difference is that you define and instantiate them in your mod class, and that you have to call the ModLoader API so they get added to the right parts of the Minecraft engine.



### Podzol

### Big mushroom block (only for b1.7.3)

## Adding an item

## Let's test it in a cool way

## Now port it to the server

## Generating your mod for the first time

