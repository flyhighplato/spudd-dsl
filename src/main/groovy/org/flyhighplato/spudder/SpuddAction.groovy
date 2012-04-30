package org.flyhighplato.spudder

class SpuddAction {
	String name
	Map<String,SpuddActionTree> anonymousVarDD = [:], anonymousObsDD = [:]
	Map<String,String> namedVarDD = [:], namedObsDD = [:]
	double cost = 0.0f
	Spudder spudder
	public SpuddAction(Spudder spudder, String actionName) {
		name = actionName
		this.spudder = spudder
	}
	
	private SpuddAction()
	{
		
	}
	
	private copy(SpuddAction otherAction) {
		spudder = otherAction.spudder
		name = otherAction.name
		anonymousVarDD = otherAction.anonymousVarDD
		namedVarDD = otherAction.namedVarDD
		anonymousObsDD = otherAction.anonymousObsDD
		namedObsDD = otherAction.namedObsDD
	}
	
	public SpuddAction withVariableTransition(String varName, SpuddActionTree t) {
		assert t
		SpuddAction act = new SpuddAction()
		act.copy(this)
		act.anonymousVarDD[varName] = t
		return act
	}
	
	public SpuddAction withVariableTransition(String varName, String ddName) {
		SpuddAction act = new SpuddAction()
		act.copy(this)
		act.namedVarDD[varName] = ddName
		return act
	}
	
	public SpuddAction withObsTransition(String varName, SpuddActionTree t) {
		assert t
		
		SpuddAction act = new SpuddAction()
		act.copy(this)
		act.anonymousObsDD[varName] = t
		return act
	}
	
	public SpuddAction withObsTransition(String varName, String ddName) {
		SpuddAction act = new SpuddAction()
		act.copy(this)
		act.namedObsDD[varName] = ddName
		return act
	}
	
	public hasCost(double cost) {
		this.cost = cost
		writeOut()
	}
	
	private writeOut() {
		spudder.writeLine("action $name")
		anonymousVarDD.each{k,v ->
			spudder.write("  $k ")
			v.writeOut()
			spudder.write("")
		}
		namedVarDD.each{k,v ->
			spudder.writeLine("  $k ($v)")
		}
		spudder.writeLine(" observe")
		anonymousObsDD.each{k,v ->
			spudder.write("  $k (")
			v.writeOut()
			spudder.write(")")
		}
		namedObsDD.each{k,v ->
			spudder.writeLine("  $k ($v)")
		}
		spudder.writeLine(" endobserve")
		spudder.writeLine(" cost (" + String.format("%.20f",cost) + ")")
		spudder.writeLine("endaction")
	}
}
