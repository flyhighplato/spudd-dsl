package org.flyhighplato.spudder

class SpudderAction {
	String name
	Map<String,SpuddActionTree> anonymousVarDD = [:], anonymousObsDD = [:]
	Map<String,String> namedVarDD = [:], namedObsDD = [:]
	double cost = 0.0f
	Spudder spudder
	public SpudderAction(Spudder spudder, String actionName) {
		name = actionName
		this.spudder = spudder
	}
	
	private SpudderAction()
	{
		
	}
	
	private copy(SpudderAction otherAction) {
		spudder = otherAction.spudder
		name = otherAction.name
		anonymousVarDD = otherAction.anonymousVarDD
		namedVarDD = otherAction.namedVarDD
		anonymousObsDD = otherAction.anonymousObsDD
		namedObsDD = otherAction.namedObsDD
	}
	
	public SpudderAction withVariableTransition(String varName, SpuddActionTree t) {
		assert t
		SpudderAction act = new SpudderAction()
		act.copy(this)
		act.anonymousVarDD[varName] = t
		return act
	}
	
	public SpudderAction withVariableTransition(String varName, String ddName) {
		SpudderAction act = new SpudderAction()
		act.copy(this)
		act.namedVarDD[varName] = ddName
		return act
	}
	
	public SpudderAction withObsTransition(String varName, SpuddActionTree t) {
		assert t
		
		SpudderAction act = new SpudderAction()
		act.copy(this)
		act.anonymousObsDD[varName] = t
		return act
	}
	
	public SpudderAction withObsTransition(String varName, String ddName) {
		SpudderAction act = new SpudderAction()
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
