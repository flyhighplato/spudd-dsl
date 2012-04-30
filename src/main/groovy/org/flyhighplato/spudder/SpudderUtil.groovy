package org.flyhighplato.spudder

class SpudderUtil {
	
	final static String INDENT = "   "
	
	public static String makeTree(List<String> levelNames, List<Iterable> levels, Map valueAccumulator = [:], int currentLevelIx = 0) {
		return "${levels[0]} ${makeSubTree(levelNames.tail(), levels.tail(), valueAccumulator, 0, INDENT)}"
	}
	
	public static String makeSubTree(List<String> levelNames, List<Iterable> levels, Map valueAccumulator = [:], int currentLevelIx = 0, String baseIndent = "") {
		assert (levels.size()) > currentLevelIx
		assert levels[currentLevelIx]
		
		Iterable currentLevel = levels[currentLevelIx]
		String levelName = levelNames[currentLevelIx]
		
		String output =""
		
		
		
		if(levelNames[currentLevelIx]) {
			if(currentLevelIx!=0) {
				output+=baseIndent
			}
			
			output+="($levelName \r\n"
		}
			
		currentLevel.eachWithIndex{ parameter, ix ->
			
			
			if(levels.size() - 1 > currentLevelIx) {

				(levelName.length() + 2).times{ output+=" "}
				output+=baseIndent

				if(levelName!=""){
					if(!levelNames[currentLevelIx+1]) {
						(levelName.length() + 2).times{ output+=" "}
					}
					
					if(levelName[-1]=="'") {
						
						output += "(${levelName[0..-2]}_${parameter}"
					}
					else
						output += "(${levelName}_${parameter}"
						
					if(levelNames[currentLevelIx+1])
					{
						output+="\r\n"
					}
				}
				else {
					output += "($parameter "
				}
				
				if(levelNames[currentLevelIx+1]) {
					output += baseIndent
				}
				
				Map newValueAccumulator = [:]
				newValueAccumulator.putAll(valueAccumulator)
				newValueAccumulator[levelName] = parameter
				
				String newBaseIndent = baseIndent
				levelName.length().times{newBaseIndent+=" "}
				output += makeSubTree(levelNames,levels,newValueAccumulator,currentLevelIx + 1,newBaseIndent)
				
				if(levelNames[currentLevelIx+1]) {
					currentLevelIx.times{ output+=INDENT }
					output+=baseIndent
				}
				output += ") \r\n"
				
			}
			else {
				if(parameter instanceof Closure) {
					//currentLevelIx.times{ output+=INDENT }
					//output+=baseIndent
					output += " (" + parameter(valueAccumulator) + ")"
				}
			}
			
			
		}
		
		if(levelNames[currentLevelIx]) {
			currentLevelIx.times{ output+=INDENT }
			output+=baseIndent

			output+=")\r\n"
			
		}
		
		if(levelName) {
			levelName.length().times{output+=" "}
		}
		
		return output
	}
}
