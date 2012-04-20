package org.flyhighplato.spudder

import groovy.lang.Closure;

import java.util.List

class SpuddDDTree extends SpuddTree {

	public SpuddDDTree(Spudder spudder, List levelNames, List levels) {
		super(spudder, levelNames, levels);
	}
	
	protected writeOut(SpuddTree resultTree) {
		if(parent == null)
			spudder.writeLine("dd " + SpudderUtil.makeTree(resultTree.levelNames, resultTree.levels) + "\r\n enddd")
		else
			parent.writeOut(resultTree)
	}

}
