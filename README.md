drug
====

# Mehr Freund als Droge - eine Alternative zu Wiki

Wikis sind gut, um Begriffsdefinitionen abzulegen und zu vernetzten. Leider werden sie oft benutzt, um Prozesse, Hierarchien und Beschreibungen
abzubilden. Dafür sind sie eigentlich eher schlecht geeignet.

Das Projekt beschäftigt sich mit einer Alternative, die ausdrucksstärker ist und daher Wissen präziser darstellen und vernetzen kann. Dabei steht eine
pragmatische Lösung im Vordergrund.

Die Idee kann man kurz wie folgt beschreiben: neben der in Wikis üblichen Begriffsbeschreibung kann man Definitionen nach dem Muster
Subjekt, Prädikat, Objekt erstellen. Dabei sind alle drei Angaben als Schlüssel zu verstehen. Optional kann eine Ortsbeziehung angegeben werden.

# Version

Das ist hier eine erste Alpha-Version. Man benötigt zum ausführen eine installierte MongoDB Datenbank, die auf den Standardport hört.

.. viel Spass.

# Syntax

Die Markup-Syntax ist bereits im Wandel. Vorschläge gern als Kommentar.

## Aktuell

	[s:Ich] [p:bauen|baue] ein [o:Haus].
	
## Überarbeitung

	(Ich-) (-baue->bauen-) ein (-Haus).
	

