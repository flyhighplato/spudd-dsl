package org.flyhighplato.spudder

class SpudderUtil {
	
	final static String INDENT = "   "
	
	public static String makeTree(List<String> levelNames, List<Iterable> levels, Map valueAccumulator = [:], int currentLevelIx = 0) {
		return "${levels[0]} ${makeSubTree(levelNames.tail(), levels.tail(), valueAccumulator, 0, INDENT)}"
	}
	
	public static String makeSubTree(List<String> levelNames, List<Iterable> levels, Map valueAccumulator = [:], int currentLevelIx = 0, String baseIndent = "") {
		assert (levels.size()) > currentLevelIx
		assert levels[currentLevelIx]
		
		
		String output = ""
		
		Iterable currentLevel = levels[currentLevelIx]
		String levelName = levelNames[currentLevelIx]
		
		currentLevel.each{ parameter ->
			currentLevelIx.times{ output+=INDENT }
			
			if(levels.size() - 1 > currentLevelIx) {
				
				output +="("
					if(levelName!=""){
						if(levelName[-1]=="'") {
							output += " $levelName (${levelName[0..-2]}_${parameter} \r\n"
						}
						else
							output += " $levelName (${levelName}_${parameter} \r\n"
					}
					else
						output += "($parameter \r\n"
					
					output += baseIndent
					currentLevelIx.times{ output+=INDENT }
					
					Map newValueAccumulator = [:]
					newValueAccumulator.putAll(valueAccumulator)
					newValueAccumulator[levelName] = parameter
					
					output += makeSubTree(levelNames,levels,newValueAccumulator,currentLevelIx + 1,baseIndent)
				
				currentLevelIx.times{ output+=INDENT }
					
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
