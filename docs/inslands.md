---
layout: page
title: Inslands
permalink: /inslands/
---

![Inslands' Hell Theme](/assets/img/2024-06-03_15.46.11.png)

**Minecraft Inslands** started as en experiments. I wanted to be able to play minecraft in really dated or limited hardware, specially hardware with slow storage, such as the early Asus Eee PC or Atom based laptops with MicroSDs cards on slow USB-like connections. Indev generated the whole world in memory once and played from there, but such an old version was not very well optimized and lacked many features I personally miss. So I had the idea on writing a custom WorldProvider for **b1.7.3** that created limited size worlds at once and stored them complete in RAM, so it could take advantage of a chunked world for better processing but it didn't have to rely on reading and writing new chunks to disk.

**Inslands** generates a finite world (3 sizes available) and terraforms it so it forms an island surrounded by ocean, or a set of floating islands. Sometimes you get a big bunch of land, sometimes you just get a tiny archipielago.

Then I found it suitable to bring back the original Indev level themes and try to expand from there. When **Inslands** is complete, each level theme will have specific gameplay items and different experiences. I'm planning to add a mod loader of sorts too, so you can somehow add your own level themes.

**Inslands** is far for complete but I've decided to release a new build everytime I complete a set of features.

To play Inslands open b1.7.3's `minecraft.jar` and/or `minecraft_server.jar` with a file archiver, delete `META_INF`, and copy all files in the zips inside, or use the "add to jar" feature available in some launchers such as MultiMC or Prism. You can also download the full instance and drag and drop it into MultiMC / Prism.

**This mod will be eventually released as open source if form of patches**, but if you are really interested get to contact me so I can invite you to the private github repo right now.

This mod includes modified portions of or features inspired by these mods: Twilight Forest (for b1.7.3 & r1.3), Deadly Mosters, Better Dungeons (for b1.8 & r1.0.0), the Aether, Hippoplatimus pistons and a couple of BOP trees (currently as placeholders)

![Inslands' Floating Forest](/assets/img/2025-09-01_12.13.12.png)

# Themes in the server

To select a level theme in the server use `theme=Normal`, `Hell`, `Paradise` or `Forest` in `server.properties`.

# Download

Links to the latest version:

* [inslands-v250901_01--MultiMC-instance.zip](https://www.mojontwins.com/inslands/zip/inslands-v250901_01--MultiMC-instance.zip)
* [inslands-v250901_01--client.zip](https://www.mojontwins.com/inslands/zip/inslands-v250901_01--client.zip)
* [inslands-v250901_01--server.zip](https://www.mojontwins.com/inslands/zip/inslands-v250901_01--server.zip)
