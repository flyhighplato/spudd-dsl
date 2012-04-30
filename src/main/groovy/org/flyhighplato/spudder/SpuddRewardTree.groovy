package org.flyhighplato.spudder

import java.util.List;

class SpuddRewardTree extends SpuddSectionTree {
	public SpuddRewardTree(Spudder spudder, List levelNames, List levels, SpuddTree parent = null, boolean isNext = false) {
		super(spudder, levelNames, levels, parent, isNext);
	}
	
	protected SpuddTree newInstance(Spudder spudder, List levelNames, List levels, SpuddTree parent = null, boolean isNext = false) {
		return new SpuddRewardTree(spudder, levelNames, levels, parent, isNext)
	}
}
