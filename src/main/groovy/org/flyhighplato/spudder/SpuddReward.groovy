package org.flyhighplato.spudder

class SpuddReward {
	SpuddRewardTree rewardTree
	Spudder spudder
	public SpuddReward(Spudder spudder, SpuddRewardTree t) {
		this.spudder = spudder
		rewardTree = t
		
		spudder.writeLine("reward")
		t.writeOut()
	}
}
