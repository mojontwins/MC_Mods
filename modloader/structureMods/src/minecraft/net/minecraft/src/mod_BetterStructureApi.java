package net.minecraft.src;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;

import org.mojontwins.minecraft.betterstructureapi.ChunkProviderGenerateProxy;

/*
 * This mod, by itself, adds nothing, but provides the means to add custom structures
 * from another mods.
 * Register your structures main classes implementing IBetterStructureGenerator calling
 * ChunkProviderGenerateProxy.registerStructureGenerator from your mod's ModsLoaded method
 * Don't remember to check if this mod is loaded first and give a proper error message! 
 */
public class mod_BetterStructureApi extends BaseMod {
	// This is used to detect if the world is new and has to be hijacked
	private World currentWorld = null;

	public mod_BetterStructureApi() {
		ModLoader.SetInGameHook(this, true, true);
	}
	
	@Override
	public String Version() {
		return "v0.1 b1.7.3";
	}

	// Tick
	@Override
	public boolean OnTickInGame(Minecraft mc) {

		// This may seem ugly... It is! But ModLoader doesn't have the hooks I need.
		if(this.currentWorld != mc.theWorld) {
			this.currentWorld = mc.theWorld;

			// The world has changed. I have to replace the chunk provider generator.
			if(this.currentWorld.chunkProvider instanceof ChunkProvider) {
				ChunkProvider provider = (ChunkProvider)this.currentWorld.chunkProvider;
				
				// The generator in provider is a private attribute so we have to use reflection.
				// Attribute private IChunkProvider chunkProvider; is index #2
				
				Field cpgf = null;
				try {
					cpgf = provider.getClass().getDeclaredFields()[2];
					cpgf.setAccessible(true);
					
					// Replace with our proxy
					IChunkProvider proxy = new ChunkProviderGenerateProxy((IChunkProvider) cpgf.get(provider), this.currentWorld);
					Class<?> clazz = cpgf.get(provider).getClass();
					cpgf.set(provider, proxy);
					
					System.out.println("Class " + clazz + " hijacked!");
				} catch (Exception e) {
					e.printStackTrace ();
				}
			}
		}
		
		return true;
	}
}
