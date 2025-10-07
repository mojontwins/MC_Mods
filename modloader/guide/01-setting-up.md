# 1. Setting up MCP / Modloader / ModloaderMP

It's been a while since these things were new so some stuff had to be done in the baground so you can do this easily. First of all we have decided to use JDK 6, which was the version used to generate the original b1.7.3 and r1.2.5 versions of the game. There's no real reason why we are doing this, but somehow it felt off to me to generate new mods that were not binary compatible (as in .class file specification) with the original game.

I use Windows. Instructions for Linux / MacOS will be different. Feel free to contribute.

## Main folder

Let's isolate everything in a folder that is portable. Create a folder anywhere to store everything. You will end up having a folder for JDK 6, a folder for Eclipse and a folder for MCP at the same level.

## Instaling JDK 6

For Windows, the only download I could find of JDK6 was an .msi executable from Oracle. I've repackaged it so you can just decompress it anywhere. Extract it to your folder. Mind you, this is the 64bit version. 

Download JDK 6 64bit for Windows as a .7z file.

## Installing Eclipse Indigo

Eclipse Indigo can run natively on JDK 6. Extract it to your folder too. Now get into the new `eclipse` folder and edit `eclipse.ini`. Add a `-vm` parameter on top to point to JDK 6. If you followed my directions, this is how `eclipse.ini` should look after edited (feel free to copy paste this into yours):

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

Download Eclipse Indigo.

## Installing MCP

Depending on your goals you will need MCP43 (for b1.7.3), MCP62 (for r1.2.5) or both. 

## Custom MCP43 for b1.7.3

I've prepared MCP43 with updated sources and fixed ModLoader / ModLoaderMP. This should be ready to go. Just extract mcp43.7z in your folder.

To check you did it right, first run `recompile.bat` and the run `startclient.bat`. The game should run.

## Custom MCP62 for r1.2.5

MCP62 has ben prepared but you still need to decompile. Extract mcp62.7z in your folder, get inside and run `decompile.bat`. 

To check you did it right, first run `recompile.bat` and the run `startclient.bat`. The game should run.

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
