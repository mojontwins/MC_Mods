package net.minecraft.src;

import java.lang.reflect.Field;

import net.minecraft.server.MinecraftServer;

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
	private boolean hijacked = false;

	public mod_BetterStructureApi() {
		ModLoader.SetInGameHook(this, true, true);
	}
	
	@Override
	public String Version() {
		return "v0.1 b1.7.3";
	}

	// Tick
	@Override
	public void OnTickInGame(MinecraftServer mc) {

		// This may seem ugly... It is! But ModLoader doesn't have the hooks I need.
		// In the server, we have to hijack every existing WordServer instance.
		if(!this.hijacked) {
			this.hijacked = true;
			
			for(WorldServer world : mc.worldMngr) {
	
				// The world has changed. I have to replace the chunk provider generator.
				if(world.chunkProvider instanceof ChunkProvider) {
					ChunkProviderServer provider = (ChunkProviderServer)world.chunkProvider;
					
					// The generator in provider is a private attribute so we have to use reflection.
					// Attribute private IChunkProvider serverChunkGenerator; is index #2
					
					Field cpgf = null;
					try {
						cpgf = provider.getClass().getDeclaredFields()[2];
						cpgf.setAccessible(true);
						
						// Replace with our proxy
						IChunkProvider proxy = new ChunkProviderGenerateProxy((IChunkProvider) cpgf.get(provider), world);
						Class<?> clazz = cpgf.get(provider).getClass();
						cpgf.set(provider, proxy);
						
						System.out.println("Class " + clazz + " hijacked!");
					} catch (Exception e) {
						e.printStackTrace ();
					}
				}
			}
		}
		
	}
}
