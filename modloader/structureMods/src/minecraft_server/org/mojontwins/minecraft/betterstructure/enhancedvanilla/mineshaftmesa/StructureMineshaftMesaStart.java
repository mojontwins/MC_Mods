package org.mojontwins.minecraft.betterstructure.enhancedvanilla.mineshaftmesa;

import java.util.Random;

import net.minecraft.src.World;

import org.mojontwins.minecraft.betterstructure.enhancedvanilla.StructureStart;

public class StructureMineshaftMesaStart extends StructureStart {

	public StructureMineshaftMesaStart(World world, Random rand, int cX, int cZ) {
		// Create a new component room; it takes absolute x, z coordinates
		ComponentMineshaftMesaRoom componentMineshaftRoom = new ComponentMineshaftMesaRoom(0, rand, (cX << 4) + 2, (cZ << 4) + 2);
		
		// Add the component to this list (it will be the first?)
		this.components.add(componentMineshaftRoom);
		
		// Build this component. This will start adding stuff attached to the component, recursively.
		componentMineshaftRoom.buildComponent(componentMineshaftRoom, this.components, rand);
		
		// Will iterate all existing components and make the overall BB contain the BBs of all the components.
		this.updateBoundingBox();
		
		// Original Mineshaft start calls markAvailableHeight which seems to make sure mineshaft is under 63-10.
		// We want these mineshafts to be above ground.
		
	}

}
