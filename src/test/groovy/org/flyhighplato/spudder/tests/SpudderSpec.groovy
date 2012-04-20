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
					
			/*spudder.dd("wumpusb")
					.withVariable("apos1")
					.withVariable("wpos")
					.then()
					.withVariable("wpos")
					.hasValue { args ->
						return String.format("%.20f",1.0f/(25.0 * 25.0 * 25.0))
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
					}*/
		then: 
			true
			
	}
}
