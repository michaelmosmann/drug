#Drug
## Mehr Freund als Droge - eine Alternative zu Wiki

(Drug ist russisch und bedeutet Freund, wird eher wie Druug ausgesprochen.)

Wikis sind gut, um Begriffsdefinitionen abzulegen und zu vernetzten. Leider werden sie oft benutzt, um Prozesse, Hierarchien und Beschreibungen
abzubilden. Dafür sind sie eigentlich eher schlecht geeignet.

Das Projekt beschäftigt sich mit einer Alternative, die ausdrucksstärker ist und daher Wissen präziser darstellen und vernetzen kann. Dabei steht eine
pragmatische Lösung im Vordergrund.

Die Idee kann man kurz wie folgt beschreiben: neben der in Wikis üblichen Begriffsbeschreibung kann man Definitionen nach dem Muster
Subjekt, Prädikat, Objekt erstellen. Dabei sind alle drei Angaben als Schlüssel zu verstehen. Optional kann eine Ortsbeziehung angegeben werden.

Interessant wird es, wenn die Begriffsdefinitionen, die für Subjekt, Prädikat, Objekt und Ort benutzt werden, auch Informationen über Wortbeugungnen 
oder alternative Bezeichnungen enthalten.

Ebenfalls denkbar wäre eine Ausweitung auf Informationen wie Neuer Mitarbeiter -> Mitarbeiter -> Mensch.

# Version

Das ist hier eine erste Alpha-Version. Man benötigt zum ausführen eine installierte MongoDB Datenbank, die auf den Standardport hört.

.. viel Spass.

# Syntax

Die Markup-Syntax ist bereits im Wandel. Vorschläge gern als Kommentar.

## Aktuell

	[s:Ich] [p:bauen|baue] ein [o:Haus]. Das ist ein normaler [Begriff].
	
## Überarbeitung

	(Ich-) (-baue->bauen-) ein (-Haus).
	(Ich-) (-kaufe->kaufen-) einen (-Laptop) in (@China).
	(Ich-) (-schicke->schicken-) einen (-Laptop) nach (->China).
	(Man-) (-schickt->schicken-) mir einen (-Laptop) aus (<-China).
	(Ich-) (-besuche->besuchen-) meine (-Tante) bei (@@Peking).
	

