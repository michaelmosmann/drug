package de.flapdoodle.drug.parser.guessing;

import junit.framework.TestCase;


public class TestGuessingParser extends TestCase {

	// wenn die Regel ist, dass Satzzeichen (.!?) und neue-Zeile-Zeichen den Context beenden,
	// dann sollte es meist gehen.
	// wenn er dann pro Zeile zu wenig hat und die Zeile davor (nur bei Newline) das fehlende Teil
	// findet, dann nimmt er das.
	// Nach : könnte man auch von einem neuen Kontext ausgehen.
	
	// zuerst prüfen, ob in einer Zeile keine Kollisionen auftreten.
	// -> wenn Zeile ok. Dann steht sie nicht für andere zur Verfügung.
	// allerdings müsste man hierbei schon die Satzzeichen beachten.
	
	// oder die Standardgrenzen sind die Satzzeichen. Bei Aufzählungen dann vielleicht
	// Klammern einführen? -> {}
	
	public void testGuessingParser() {
		String sampleText="Einrichten eines Arbeitsplatzes:\n" +
				"- [p:Beantragen] eines [o:Login]\n" + // prädikate immer klein? subjekt aus dem Context?
				"- [p:Beantragen] der [o:Schreibtischausstattung]\n" +
				"- [p:Bestellen] eines [o:Rechners->Rechner]\n" +
				"- [p:Einrichten] der [o:Standardsoftware]\n" +
				"- [p:Versenden] der [o:Einrichtungsmail] an den [->Mitarbeiter]\n" + // mit Ortsangabe
				"\n" +
				"- [p:Versenden] eines [o:Paketes->Paket] nach [->China]\n" + // in Richtung
				"- [p:Importieren] eines [o:Briefes->Brief] von [<-China]\n" + // aus Richtung
				"- [p:Besuchen] einer [o:Tante] in [@China]\n" + // in
				"- [p:Besuchen] eines [o:Onkels] bei [>China<]\n" + // bei
				"- was geht alles nach [->China]\n" + // Hier müsste man raten. Das in der Zeile kein Satz ist ..
				"oder doch eher so:" +
				"- [-Besuchen-] einer [-Tante] in [@China]" +
				"- [Ich-] [-besuche->besuchen-] meine [-Tante]" +
				"\n"
				;

		
	}
}
