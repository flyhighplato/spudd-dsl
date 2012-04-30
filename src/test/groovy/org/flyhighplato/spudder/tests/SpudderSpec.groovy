package org.flyhighplato.spudder.tests
import org.flyhighplato.spudder.Spudder
import spock.lang.Specification


class SpudderSpec extends Specification {
	def "generate SPUDD file"() {
		when:
			Range posRange = 0..<25
			Range collRange = 0..<2
			Spudder spudder = new Spudder( 
									"testSpudd.SPUDD",
									[	"apos1":posRange.collect{it-> "$it"},
										"apos2":posRange.collect{it-> "$it"},
										"wpos":posRange.collect{it-> "$it"}
									],
									[	"aloc":posRange.collect{it-> "$it"},
										"coll":collRange.collect{it-> "$it"}
									]
								)
			spudder.variables()
			spudder.observations()
			spudder.initBelief()
					.withVariable("apos1")
					.withVariable("apos2")
					.withVariable("wpos")
					.hasValue { args ->
						return String.format("%.20f",1.0f/(25.0 * 25.0 * 25.0))
					}
					
			spudder.dd("wumpusb")
					.withVariable("apos1")
					.withVariable("wpos")
					.then()
					.withVariable("wpos")
					.hasValue { args ->
						
						if(distance(Integer.parseInt(args["wpos"]),Integer.parseInt(args["wpos'"]))<=1)
						{
							return String.format("%.20f",1.0f/numAdjacent(Integer.parseInt(args["wpos"])))
						}
						else
						{
							return "0.0"
						}
					}
			
			spudder.dd("ldd")
					.then()
					.withVariable("apos1")
					.withObservation("aloc")
					.hasValue {args ->
						if(args["apos1'"] == args["aloc'"])
							return 1.0f
						else
							return 0.0f
					}
			
			spudder.dd("sensingdd")
					.then()
					.withVariable("apos1")
					.withVariable("wpos")
					.withObservation("coll")
					.hasValue { args ->
						if(args["coll'"] == "1")
							if(distance(Integer.parseInt(args["apos1'"]),Integer.parseInt(args["wpos'"]))<=1)
								return 1.0f
							else
								return 0.0f
						else
							if(distance(Integer.parseInt(args["apos1'"]),Integer.parseInt(args["wpos'"]))<=1)
								return 0.0f
							else
								return 1.0f
					}
			
			spudder.action("north")
					.withVariableTransition("apos1",
						spudder.actionTree()
								.withVariable("apos1")
								.then()
								.withVariable("apos1")
								.hasValue { args ->
									int oldPos = Integer.parseInt(args["apos1"])
									int newPos = Integer.parseInt(args["apos1'"])
									
									int oldPosY = oldPos/5
									int oldPosX = oldPos%5
									int newPosY = newPos/5
									int newPosX = newPos%5
									
									if(oldPosX==newPosX) {
										if(oldPosY<4) {
											if(oldPosY+1==newPosY)
												return 1.0f
											else
												return 0.0f
										}
										else {
											if(oldPosY==newPosY)
												return 1.0f
											else
												return 0.0f
										}
									}
									else
									{
										return 0.0f
									}
									
								}
					)
					.withVariableTransition("apos2",
						spudder.actionTree()
								.withVariable("apos2")
								.then()
								.withVariable("apos2")
								.hasValue { args ->
									if(distance(Integer.parseInt(args["apos2"]),Integer.parseInt(args["apos2'"]))<=1)
									{
										return String.format("%.20f",1.0f/(double)numAdjacent(Integer.parseInt(args["apos2"])))
									}
									else
									{
										return "0.0"
									}
								}
						)
					.withVariableTransition("wpos", "wumpusb")
					.withObsTransition("aloc", "ldd")
					.withObsTransition("coll", "sensingdd")
					.hasCost(1)
					
		then: 
			true
			
	}
	
	public int numAdjacent(int pos) {
		int posx=pos%5;
		int posy=pos/5;
		
		int adj=3;
		
		if(posx>0 && posx<4)
			adj++
		
		if(posy>0 && posy<4)
			adj++
	
		
		return adj
	}
	public int distance(int pos1, int pos2) {
		int pos1x=pos1%5;
		int pos1y=pos1/5;
		
		int pos2x=pos2%5;
		int pos2y=pos2/5;
		
		return Math.abs(pos1x-pos2x) + Math.abs(pos1y-pos2y)
	}
}


