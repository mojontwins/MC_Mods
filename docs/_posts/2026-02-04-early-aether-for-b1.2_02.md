---
layout: post
title:  "Early Aether for b1.2_02!"
date:   2026-02-04 07:39:00 +0100
categories: ModLoader v1.7.3
---

![En early portal]({{site.baseurl}}/assets/img/2026-02-04_11.29.03.png)

First of all, sorry if this was already a thing. I certainly didn't know about this.
I found and isolated a very early Aether mod build for Minecraft beta 1.2_02. One of the developers, Kodaichi, saved a backup of their dev folder with just the patched minecraft jars containing many versions with several mods. One of the jars contained this early indev version of the Aether mod alongside a number of different mods by other people (mo creatures, for instance) all patched into the jar.

As distributing patched minecraft jars is not a good idea, I painstakingly isolated the Aether mod from the rest. The process has required to actually decompiling and rebuilding (because it was mixed by SPC/WorldEdit and I couldn't find the jars and I decided to remove it from the files as it was not really needed), so sadly it needs Java 8 to run now.

![En early Aether!]({{site.baseurl}}/assets/img/2026-02-04_11.57.40.png)

The mod incorrectly generates stuff in the overworld (because it is obviously in a very early state of development). You can travel to the Aether dimension building the glowstone portal but you have to activate it with fire using flint&steel like a Nether portal.

When you travel to the aether it can take a lot of time to generate terrain as it is constantly overflowing the early lighting system inherited from alpha that b1.2 still uses. Just have patience.

I've uploaded the mod and a MultiMC instance. The mod includes modloader and some base class edits and has to be used as a jarmod to b1.2_02.

* [Get the mod]({{site.baseurl}}/assets/files/mod_aether_b1.2_02.zip).
* [Get the MultiMC instance]({{site.baseurl}}/assets/files/mod_aether_b1.2_02__MultiMC-instance.zip).
