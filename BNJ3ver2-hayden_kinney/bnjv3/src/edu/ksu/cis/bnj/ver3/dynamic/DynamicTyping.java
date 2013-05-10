package edu.ksu.cis.bnj.ver3.dynamic;

import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;

/*
 * \file DynamicTyping.java \author Jeffrey M. Barber
 */
public class DynamicTyping {

	public static boolean IsDynamic(BeliefNetwork bn) {
		if (bn == null)
			return false;
		// Check the names for naming conventions [0],[t-1],[t]
		BeliefNode[] nodes = bn.getNodes();
		if (nodes.length == 0)
			return false;

		for (int i = 0; i < nodes.length; i++) {
			String name = nodes[i].getName();

			// name MUST be unique
			for (int k = i + 1; k < nodes.length; k++) {
				if (name.equals(nodes[k].getName()))
					return false;
			}

			boolean good = false;
			if (name.indexOf("[t]") >= 0) {

				good = true;

			}
			if (name.indexOf("[t-1]") >= 0) {
				if (good)
					good = false;
				else {

					// must have a Unique! zero
					int Count0 = 0;
					String search = UnRoll.simpleReplaceAll(name, "[t-1]",
							"[0]");
					for (int k = 0; k < nodes.length; k++) {
						if (search.equals(nodes[k].getName()))
							Count0++;
					}

					// MUST have a Unique OutGoing interface
					int CountT = 0;
					String search2 = UnRoll.simpleReplaceAll(name, "[t-1]",
							"[t]");
					for (int k = 0; k < nodes.length; k++) {
						if (search.equals(nodes[k].getName()))
							CountT++;
					}

					// check uniqueness
					if (Count0 == 1 && CountT == 1)
						good = true;

					// must not have a parent of an [t]
					BeliefNode[] parents = bn.getParents(nodes[i]);
					for (int k = 0; k < parents.length && good; k++) {
						if (parents[k].getName().indexOf("[t]") >= 0) {
							good = false;
						}
					}
				}
			}
			if (name.indexOf("[0]") >= 0) {
				if (good)
					good = false;
				else {
					good = true;

					// can not depend on anything but zeros
					BeliefNode[] parents = bn.getParents(nodes[i]);
					for (int k = 0; k < parents.length && good; k++) {
						if (parents[k].getName().indexOf("[t]") >= 0) {
							good = false;
						}
						if (parents[k].getName().indexOf("[t-1]") >= 0) {
							good = false;
						}
					}
				}
			}
			if (!good)
				return false;
		}
		return true;
	}
}