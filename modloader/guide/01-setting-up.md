# 1. Setting up MCP / Modloader / ModloaderMP

It's been a while since these things were new so some stuff had to be done in the baground so you can do this easily. First of all we have decided to use **JDK 6**, which was the version used to generate the original b1.7.3 and r1.2.5 versions of the game. There's no real reason why we are doing this, but somehow it felt off to me to generate new mods that were not binary compatible (as in .class file specification) with the original game.

I use Windows. Instructions for Linux / MacOS will be different. Feel free to contribute. Also my English is not the best. If you feel you should correct me, by all means, pull request it.

## Main folder

Let's isolate everything in a folder that is portable. Create a folder anywhere to store everything. You will end up having a folder for **JDK 6**, a folder for **Eclipse 3.7** and a folder for **MCP** at the same level.

## Instaling JDK 6

For Windows, the only download I could find of **JDK 6** was an .msi executable from Oracle. I've repackaged it so you can just decompress it anywhere. Extract it to your folder. Mind you, this is the 64bit version. 

[Download JDK 6 64bit for Windows as a .7z file](https://github.com/mojontwins/MC_Mods/raw/refs/heads/master/modloader/env/jdk1.6.0_45.7z).

## Installing Eclipse Indigo

Eclipse Indigo can run natively on **JDK 6**. Extract it to your folder too. Now get into the new `eclipse` folder and edit `eclipse.ini`. Add a `-vm` parameter on top to point to JDK 6. If you followed my directions, this is how `eclipse.ini` should look after edited (feel free to copy paste this into yours):

```
	-vm
	../jdk1.6.0_45/bin/javaw.exe
	-startup
	plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar
	--launcher.library
	plugins/org.eclipse.equinox.launcher.win32.win32.x86_64_1.1.100.v20110502
	-showsplash
	org.eclipse.platform
	--launcher.XXMaxPermSize
	256m
	--launcher.defaultAction
	openFile
	-vmargs
	-Xms40m
	-Xmx384m
```
Do not run Eclipse now. We'll do it later.

[Download Eclipse Indigo](https://www.eclipse.org/downloads/packages/release/indigo/r/eclipse-classic-37).

## Installing MCP

Depending on your goals you will need MCP43 (for b1.7.3), MCP62 (for r1.2.5) or both. To save you from all the fiddle needed to make the packages run nowadays, I've uploaded mine, which are almost ready to use.

* [Download my custom MCP43 + ModLoader + ModLoaderMP](https://github.com/mojontwins/MC_Mods/raw/refs/heads/master/modloader/env/mcp43.7z)
* [Download my custom MCP62 + ModLoader + ModLoaderMP](https://github.com/mojontwins/MC_Mods/raw/refs/heads/master/modloader/env/mcp62.7z)

## Custom MCP43 for b1.7.3

I've prepared MCP43 with updated sources and fixed ModLoader / ModLoaderMP. Download it and extract `mcp43.7z` in your folder. This should create a `mcp43` folder at the same level as `jdk1.6.0_45` and `eclipse`. Now enter the new folder and run these files in order (you can execute them from console or double click them): `recompile.bat`, then `updatemd5.bat`. Now you are ready to go!

To check you did everything right, run `startclient.bat`. The game should run.

## Custom MCP62 for r1.2.5

MCP62 has ben prepared but you still need to decompile. Extract mcp62.7z in your folder, get inside and run `decompile.bat`. Once it's finished, run `recompile.bat` and then `updatemd5.bat`.

To check you did everything right, run `startclient.bat`. The game should run.

## Before starting every mod

Before starting, make a copy of the `src` folder inside MCP. That way, when you finish a mod, you can ZIP the current src and tuck it away, grab the original src and reset MCP to start a new mod. 

## The general workflow

When creating ModLoader / ModLoaderMP mods you basicly do this:

* Create the client version.
* Port it over to the server.
* Test everything in Eclipse.
* Run `recompile.bat`.
* Run `reobfuscate.bat`. 
* Add additional resources to the generated files in the `reobf` folder, such as textures, etc.
* Create a zip archives for the client and server from the `reobf` folder.

We'll be dealing with these steps in detail several times.

## Decompiling external mods for examination

The easiest way to do this is going the Java 8 - RetroMCP Java stack. Install Zulu Java 8, use it to run the latest RetroMCP Java. Configure the right version. Open the downloaded minecraft.jar and add modloader, forge, and the mod you want to decompile, then hit the decompile button. Most of the time you'll get recompilation errors, but the code will be there for you to learn from. I will cover this topic too in a future chapter.

## Setting up Eclipse

Now it's time to run Eclipse for the first time. Once it loads it will ask you to select a Workspace. You should `Browse...` to your MCP folder, and select the `eclipse` folder inside it. The most portable solution is to directly type `..\MCP43\eclipse` or `..\MCP62\eclipse` which should point to the right place using relative paths. Check `Use this as the default` and press `OK`.

Eclipse should now open and you should find `Client` and `Server` in the left side bar. Now let's make sure we are using the right compiler. Go to `Window` -> `Preferences`. Expand `Java` in the left and click on `Installed JREs`. Our `jdk1.6_045` should appear in the table.

Close everything and you are ready!
