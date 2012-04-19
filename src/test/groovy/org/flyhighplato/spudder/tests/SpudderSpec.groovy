package org.flyhighplato.spudder.tests
import org.flyhighplato.spudder.Spudder
import spock.lang.Specification


class SpudderSpec extends Specification {
	def "generate SPUDD file"() {
		when:
			Range posRange = 0..<25
			Range collRange = 0..<2
			Spudder spudder = new Spudder(
									[	"apos1":posRange.collect{it-> "$it"},
										"apos2":posRange.collect{it-> "$it"},
										"wpos":posRange.collect{it-> "$it"}
									],
									[	"aloc":posRange.collect{it-> "$it"},
										"coll":collRange.collect{it-> "$it"}
									]
								)
		then: 
			spudder.init()
					.givenState("apos1")
					.givenState("apos2")
					.givenState("wpos")
					.valueIs { args ->
						
						if(args.apos1 == args.apos2 && args.apos2== args.wpos)
							return 0.0f
						else {
							double val = 25.0 * 25.0 * 25.0
							return String.format("%.20f",1.0f/(25.0 * 25.0 * 25.0))
						}
					}
			
	}
}
