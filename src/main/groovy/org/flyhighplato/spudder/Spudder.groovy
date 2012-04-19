package org.flyhighplato.spudder

class Spudder {
	Map<String, List<String>> variables, observations
	
	public class SpuddTree {
		private List levels = []
		private List levelNames = []
		public SpuddTree(List levelNames, List levels) {
			this.levels = levels
			this.levelNames = levelNames
		}
		
		public givenState(String stateName) {
			return new SpuddTree(levelNames + [stateName], levels + [variables[stateName]])
		}
		
		public valueIs(Closure c) {
			List tempLevels = levels + [[c]]
			println "${makeTree(levelNames, tempLevels)}"
			true
		}
	}
	
	String indent = "   "
	public Spudder(Map<String,List<String>> variableValues,Map<String,List<String>> observationValues) {
		variables = variableValues
		observations = observationValues
	}
	public init() {
		return new SpuddTree([""],["init"])
	} 
	
	private String makeTree(List<String> levelNames, List<Iterable> levels, Map valueAccumulator = [:], int currentLevelIx = 0) {
		return "${levels[0]} ${makeSubTree(levelNames.tail(), levels.tail(), valueAccumulator, 0, indent)}"
	}
	
	private String makeSubTree(List<String> levelNames, List<Iterable> levels, Map valueAccumulator = [:], int currentLevelIx = 0, String baseIndent = "") {
		assert (levels.size()) > currentLevelIx
		assert levels[currentLevelIx]
		
		
		String output = ""
		
		Iterable currentLevel = levels[currentLevelIx]
		String levelName = levelNames[currentLevelIx]
		
		currentLevel.each{ parameter ->
			currentLevelIx.times{ output+=indent }
			
			if(levels.size() - 1 > currentLevelIx) {
				
				output +="("
					if(levelName!="")
						output += " $levelName (${levelName}_${parameter} \r\n"
					else
						output += "($parameter \r\n" 
					
					output += baseIndent
					currentLevelIx.times{ output+=indent }
					
					Map newValueAccumulator = [:]
					newValueAccumulator.putAll(valueAccumulator)
					newValueAccumulator[levelName] = parameter
					
					output += makeSubTree(levelNames,levels,newValueAccumulator,currentLevelIx + 1,baseIndent) 
				
				currentLevelIx.times{ output+=indent }
					
				output += ") "
			}
			else {
				if(parameter instanceof Closure) {
					output += "( " + parameter(valueAccumulator)
				}
			}
			output += ")\r\n"
			output += baseIndent
			
		}
		
		return output
	}
}
