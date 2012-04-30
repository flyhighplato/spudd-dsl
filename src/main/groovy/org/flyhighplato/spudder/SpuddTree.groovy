package org.flyhighplato.spudder

import java.util.List;

public class SpuddTree {
	protected List levels = []
	protected List levelNames = []
	protected final SpuddTree parent
	protected final Spudder spudder
	protected final boolean isNext
	
	public SpuddTree(Spudder spudder, List levelNames, List levels, SpuddTree parent = null, boolean isNext = false) {
		this.levels = levels
		this.levelNames = levelNames
		this.spudder = spudder
		this.parent = parent
		this.isNext = isNext
	}
	
	protected SpuddTree newInstance(Spudder spudder, List levelNames, List levels, SpuddTree parent = null, boolean isNext = false) {
		return new SpuddTree(spudder, levelNames, levels, parent, isNext)
	}

	public SpuddTree withVariable(String stateName) {
		List variables = spudder.variables[stateName]
		if(isNext)
			stateName = stateName+"'"
		return newInstance(spudder, levelNames + [stateName], levels + [variables], this, isNext)
	}
	
	public SpuddTree withObservation(String obsName) {
		List observations = spudder.observations[obsName]
		if(isNext)
			obsName = obsName+"'"
		return newInstance(spudder, levelNames + [obsName], levels + [observations], this, isNext)
	}
	
	public SpuddTree then() {
		if(isNext)
			throw new Exception("This tree is already in next mode")
	
		return newInstance(spudder, levelNames, levels, this, true)
	}
	
	public boolean isNext() {
		return isNext
	}
	
	public hasValue(Closure c) {
		levels = levels + [[c]]
		writeOut(this);
	}
	
	protected writeOut(SpuddTree resultTree) {
		if(parent == null)
			spudder.writeLine(SpudderUtil.makeTree(resultTree.levelNames, resultTree.levels))
		else
			parent.writeOut(resultTree)
	}
	
	
}
