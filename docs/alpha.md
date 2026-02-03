---
layout: page
title: Alpha ML project
permalink: /alpha/
nav: yes
---

This will be my Alpha 1.1.2_01 related modding project anytime soon. The idea is releasing a jarmod to provide a Modloader, some QOL shit and stuff. Then, my next project will be porting some of my favorite Modloader based Beta / early Release mods. This is the wishlist:

- [ ] Modloader / Modloader MP, ported from r1.2.5. I know there's a Modloader for a1.1.2_01 but it doesn't support SMP and lacks some of the features I'm used to in r1.2.5, plus stuff to make easier to add structures (that is, the missing hooks during the chunk generation stage and at the beginning of the population stage), also proper way to get MC instance and easy reflection.
- [X] McRegion save format.
- [X] Simple SinglePlayerCommands for debugging, needs world.enableCheats.
- [ ] Coordinates in F3 GUI.
- [ ] Enter Seed when creating a new world.
- [ ] FOV Slider which many people want (not me).
- [ ] 256 block IDs usable (edits Chunk/World)
- [ ] Fixed armor behaviour, 'cause Armors in alpha are basicly useless.
- [ ] Fixed block breaking behavior, with the correct durations and drops.
- [ ] Fixed boats.
- [ ] Burning wood produces coal.
- [ ] Dehardcode atlas size & make it bigger.
- [ ] Release leaf decay (less chunk updates)
- [ ] Release falling sand (no lag in farlands, less chunk updates)
- [X] Send "this is a winter world" server->client.
- [ ] Remember last used server.
- [ ] 256 blocks tall?
- ???
- Some optimizations.

I especifically don't want to fix bugs 'cause they are heavily explotied by actual a1.1.2_01 players (i.e. Mongster) and those are part of the experience that makes Alpha worth playing on its own. **I just want something that lets me add optional contents easily**. Optional as in you don't want it you leave the mod out.

Planned mods:

- Silk touch in gold tools.
- New matherial tier to provide "efficiency".
- Crying Obsidian set spawn thing.
- Chocolate quest, of course.
- Stuff I like from Infhell.
- More zombies.
- City generation.

Whenever I start doing this I'll keep a diary here.

## Diary of sorts

Starting point is the merged codebase RMCPJ 1.2 produces which seems to work (yay!) and saves me a lot of trouble.

### Snow Covered server->client

When the server sends the last Packet1Login to the Client (`NetServerHandler.doLogin()`), it sends it as a dummy signal with "", "", 0. I'm using that 0 (protocol Number) to signal if the world in the server is snow covered (1). `NetClientHandler.handleLogin (p1l)` will set SnowCovered in `this.worldClient` upon reading this value.

### Seed / Enable cheats

Need a new text box in the GUI.


