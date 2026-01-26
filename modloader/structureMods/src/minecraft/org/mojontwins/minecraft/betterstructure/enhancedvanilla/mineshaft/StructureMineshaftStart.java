package org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaft;

import java.util.Random;

import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureStart;

public class StructureMineshaftStart extends StructureStart {

	public StructureMineshaftStart(World world, Random rand, int cX, int cZ) {
		// Create a new component room; it takes absolute x, z coordinates
		ComponentMineshaftRoom componentMineshaftRoom = new ComponentMineshaftRoom(0, rand, (cX << 4) + 2, (cZ << 4) + 2);
		
		// Add the component to this list (it will be the first?)
		this.components.add(componentMineshaftRoom);
		
		// Build this component. This will start adding stuff attached to the component, recursively.
		componentMineshaftRoom.buildComponent(componentMineshaftRoom, this.components, rand);
		
		// Will iterate all existing components and make the overall BB contain the BBs of all the components.
		this.updateBoundingBox();
		
		// Make sure it is buried under ground!
		this.markAvailableHeight(world, rand, 10);
	}
	
}
