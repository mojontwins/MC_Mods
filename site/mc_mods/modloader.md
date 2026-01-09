---
layout: page
title: ModLoader stuff
permalink: /modloader/
---

# What is this?

In this page you will find various stuff made for ModLoader, mostly b1.7.3 or r1.2.5 based and some reference / tutorial I'm writing from time to time with the hope that there's a somewhat good or usable reference that's easily found.

Some of these mods have been created from ideas I've gotten from posts in the [Golden Age Minecraft subreddit](https://www.reddit.com/r/GoldenAgeMinecraft/) by people just giving ideas or requesting modifications. So if you have an idea fetch me and I may be interested.

# Scattered Feature backport for 1.2.5	

![A screenshot of Minecraft 1.2.5 showing the Scattered Feature backport mod in action](/assets/img/mod_scattered_features.png)

This mod backports 1.3.2's Scattered Feature, that is Jungle and Desert temples, to 1.2.5. It requires ModLoader and ModLoaderMP.

## Installation

### Client

Add ModLoader and ModLoaderMP to minecraft.jar, create a `mods` folder in `.minecraft` and put `[1.2.5]-ScatteredFeature-1.0.zip` inside. This mod should be compatible with other ModLoader mods.

### Server

Add ModLoaderMP_Server to minecraft_server.jar, create a `mods` folder in `.minecraft` and put `[1.2.5]-ScatteredFeature-1.0_server.zip` inside.

### Conflicts

This mod adds three new blocks from 1.3.2 with IDs 128, 131 and 132. If you find conflict with your existing modpack, just edit the .cfg and assign different IDs.

## Download & sources

[Get them from Github](https://github.com/mojontwins/MC_Mods/tree/master/modloader/scatteredFeature).

## Resources

* ModLoader https://mcarchive.net/mods/modloader
* ModLoaderMP / ModLoaderMP_Server https://mcarchive.net/mods/modloadermp

# Better Books for 1.2.5

![A screenshot of Minecraft 1.2.5 showing the Better Books mod in action (shelves)](/assets/img/mod_betterbooks1.png)

![A screenshot of Minecraft 1.2.5 showing the Better Books mod in action (book edition)](/assets/img/mod_betterbooks2.png)

This mod adds custom writable books and bookshelves to store them. It was inspired by both the old Tomes mod and the vanilla 1.3+ feature, but I've written everything myself from scratch. Source code & development diary is available in [Github](https://github.com/mojontwins/MC_Mods/tree/master/modloader/betterBooks).

## Installation

### Client

Add ModLoader and ModLoaderMP to minecraft.jar, create a `mods` folder in `.minecraft` and copy `mod_BetterBooks-v0.1.zip` inside. This mod should be compatible with other ModLoader mods.

### Server

Add ModLoaderMP_Server to minecraft_server.jar, create a `mods` folder in `.minecraft` and copy `mod_BetterBooks-v0.1_server.zip` inside.

### Conflicts

This mod adds one block and one item. If you find conflict with your existing modpack, just edit the .cfg and assign different IDs.

## Download & sources

[Get them from Github](https://github.com/mojontwins/MC_Mods/tree/master/modloader/betterBooks).

## Resources

* ModLoader https://mcarchive.net/mods/modloader
* ModLoaderMP / ModLoaderMP_Server https://mcarchive.net/mods/modloadermp

## Recipes & help

* Writable books recipe is shapeless ink sack, book, and feather. Books are given a random color on crafting but can be dyed.
* Special bookshelves has the same recipe as vanilla but without the books, just the 6 planks ;). Right click on them to place books.
* When editing a book you can set a title. Text will flow naturally. If you need you can add page breaks pressing CTRL+ENTER.
* Writable books tooltip on GUIs will show the book title. This is useful when reviewing your bookshelves.

