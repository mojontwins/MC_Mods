# 3. Generating features

Even though they were called World Generators in the MCP mappings, more recent leaks have revealed that these things were called **Features** in the original sources, and I like to call them this way.

Features are trees, ore veins, small lakes, pools of lava and stuff like that. They are added during the pupulation phase of generating new chunks (we discussed this shortly in the last chapter).

I don't quite like how ModLoader implements the hooks to add your own population to the world, as it is run after everything else has been placed, which may result in highly populated chunks where you can't really place your stuff. Anyways, for the sake of this tutorial, we'll be using this (I'm working on a much more powerful solution, tho).

## What we want

We want areas of land turned into podzol, and huge mushrooms growing on them.

## Our first feature generator.

Feature generators can be whatever, but let's follow the trend in vanilla minecraft: we are extending `WorldGenerator.java`. We add a new class, `FeaturePodzolBlob` extending `WorldGenerator` and leave Eclipse fill in the methods from the superclass you need to implement. We get this skeleton (I've renamed the parameters, I always do, and you should do it too):

```java
	package org.mojontwins.minecraft.fungalcalamity;

	import java.util.Random;

	import net.minecraft.src.World;
	import net.minecraft.src.WorldGenerator;

	public class FeaturePodzolBlob extends WorldGenerator {

		@Override
		public boolean generate(World world, Random rand, int x0, int y0,
				int z0) {
			// TODO Auto-generated method stub
			return false;
		}

	}
``` 

There's no constructor, but we will add one so we can customise the generator easily. We'll take two parameters to define the size of the blob, something like this should do:

```java
	private int minRadius;
	private int maxRadius;
	
	public FeaturePodzolBlob(int minRadius, int maxRadius) {
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
	}
```

We'll use those attributes in the generation algorithm. We won't doing nothing very fancy. We'll be turning any surface block into podzol in a radius between minRadius and maxRadius, with some randomness here and there.

```java
	@Override
	public boolean generate(World world, Random rand, int x0, int y0, int z0) {
		int radius = minRadius + rand.nextInt(maxRadius - minRadius);
		for(int x = x0 - radius; x < x0 + radius; x ++) {
			for(int z = z0 - radius; z < z0 + radius; z ++) {
				int y = world.getHeightValue(x, z) - 1;
				int dx = Math.abs(x - x0);
				int dz = Math.abs(z - z0);
				int di = dx + dz / 2;
				if(di < 2 || rand.nextInt(di) == 0) {
					world.setBlock(x, y, z, mod_fungalCalamity.blockPodzol.blockID);
				}
			}
		}
		return false;
	}
```
