package org.flyhighplato.spudder

import java.util.List;
import groovy.lang.Closure;

class SpuddSectionTree extends SpuddTree {
	
	public SpuddSectionTree(Spudder spudder, List levelNames, List levels, SpuddTree parent = null, boolean isNext = false) {
		super(spudder, levelNames, levels, parent, isNext);
	}
	
	public hasValue(Closure c) {
		return newInstance(spudder, levelNames, levels + [[c]], this, isNext)
	}
	
	public writeOut() {
		writeOut(this)
	}
}
