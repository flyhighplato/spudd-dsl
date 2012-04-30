package org.flyhighplato.spudder

import groovy.lang.Closure;

import java.util.List;

class SpuddActionTree extends SpuddTree {
	public SpuddActionTree(Spudder spudder, List levelNames, List levels, SpuddTree parent = null, boolean isNext = false) {
		super(spudder, levelNames, levels, parent, isNext);
	}
	
	protected SpuddTree newInstance(Spudder spudder, List levelNames, List levels, SpuddTree parent = null, boolean isNext = false) {
		return new SpuddActionTree(spudder, levelNames, levels, parent, isNext)
	}
	
	public hasValue(Closure c) {
		return newInstance(spudder, levelNames, levels + [[c]], this, isNext)
	}
	
	public writeOut() {
		writeOut(this)
	}
}
