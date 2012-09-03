#Drug
## Mehr Freund als Droge - eine Alternative zu Wiki

(Drug ist russisch und bedeutet Freund, wird eher wie Druug ausgesprochen.)

# die kurze Erklärung

In einem klassischen Wiki könnte für den Begriff "Boot" folgender Definition hinterlegt sein:

	Boot:
	Ein Ding, dass auf Wasser schwimmt und mit dem man deshalb auf Wasser fahren kann.

Dann ist in dem Text das Wort "fahren" verlinkt, das wie folgt definiert sein könnte:

	fahren:
	Man bewegt sich von A nach B mit Kontakt zur Oberfläche.
	
Wenn nun jemand beschreiben möchte, was zu beachten ist, wenn man mit dem "Boot fährt", dann hat er
drei Möglichkeiten:

1. Er beschreibt das unter dem Begriff "Boot"
2. Er beschreibt das unter dem Begriff "fahren"
3. Er beschreibt das unter "Boot fahren"

Problem: Die Erklärung gehört weder zu 1 noch zu 2. Bei 3 wird aus "Boot fahren" schnell auch "Mit dem Boot fahren", "Ein Boot fahren"... 
Außerdem ist nicht mehr erkennbar, dass es sowohl um "Boot" als auch um "fahren" geht.

# Die lange Erklärung

Wikis sind gut, um Begriffsdefinitionen abzulegen und zu vernetzten. Leider werden sie oft benutzt, um Prozesse, Hierarchien und Beschreibungen
abzubilden. Dafür sind sie eigentlich eher schlecht geeignet.

Das Projekt beschäftigt sich mit einer Alternative, die ausdrucksstärker ist und daher Wissen präziser darstellen und vernetzen kann. Dabei steht eine
pragmatische Lösung im Vordergrund.

Die Idee kann man kurz wie folgt beschreiben: neben der in Wikis üblichen Begriffsbeschreibung kann man Definitionen nach dem Muster
Subjekt, Prädikat, Objekt erstellen. Dabei sind die Angaben als Schlüssel zu verstehen. Das Subjekt ist optional, ebenfalls kann eine Ortsbeziehung (z.B. in Leipzig) angegeben werden.

Interessant wird es, wenn die Begriffsdefinitionen, die für Subjekt, Prädikat, Objekt und Ort benutzt werden, auch Informationen über Wortbeugungen 
oder alternative Bezeichnungen enthalten.

Ebenfalls denkbar wäre eine Ausweitung auf Informationen wie Neuer Mitarbeiter -> Mitarbeiter -> Mensch.

(das soll aber nicht in sowas wie RDF enden: http://www.w3.org/TR/rdf-concepts/)

# Version

Das ist hier eine erste Alpha-Version. Die benötigte MongoDB-Instanz wird ohne Installation heruntergeladen und gestartet. Es werden keine Veränderungen an
irgendwelchen anderen Installationen vorgenommen.

.. viel Spass.

# Syntax

## Grundlagen - Subjekt, Prädikat, Objekt

Es gilt die Markdown-Syntax (wird durch https://github.com/sirthias/pegdown implementiert), die um ein paar Erweiterungen ergänzt wurde.
Auf Begriffsdefinitionen wird klassisch per [[Begriff]] verlinkt. Die Markierungen für Subjekt**(s)**, Prädikat**(p)** und Objekt**(o)** folgen einer einfachen Syntax. Beispiele:

* Begriff als Subjekt: **[s:Begriff]**
* Begriff als Prädikat mit Wortstamm: **[s:gefahren->fahren]**
* Begriff als Objekt, der aber in der Ausgabe nicht dargestellt wird: **[!o:Begriff]**
* Einfacher Satz: **[s:Ich]** **[p:baue->bauen]** ein **[o:Haus]**.
* Vermutlich häufigster Anwendungsfall: **[p:Aktion->alsPrädikat]** **[o:Objekt]**

	> [o:Arbeitsplatz] [p:einrichten]
	
	> [o:Festplatte] [p:formatioeren]
	
## Eindeutigkeit und Kollisionen

Es kann passieren, dass in einem Absatz mehr als einmal Subjekt, Prädikat oder Objekt benutzt wird. Dann kann nicht mehr automatisch ermittelt werden, was zusammen
gehört. In diesem Fall kann vor dem ":" eine Zahl von 0-9 angegeben werden.

* Absatz mit Kollision:

	> Ich [p:fahre->fahren] mit dem [o:Boot]. Dabei muss das [o:Boot] immer [p:beleuchtet->beleuchten] sein.

* Anpassungen um Kollisionen zu vermeiden:

	> Ich [p:fahre->fahren] mit dem [o:Boot]. Dabei muss das [o0:Boot] immer [p0:beleuchtet->beleuchten] sein.

## Ortsbezug

Zusätzlich gibt es eine ähnlich gelagerte Syntax für die optionale Ortsbeziehung:

* Eine Beschreibung mit Ortsbezug: **[o:Blumen]** **[p:importieren]** aus **[from:Holland]**



## Aktuell

	[s:Ich] [p:bauen|baue] ein [o:Haus]. Das ist ein normaler [Begriff].
	
## Überarbeitung

	(Ich-) (-baue->bauen-) ein (-Haus).
	(Ich-) (-kaufe->kaufen-) einen (-Laptop) in (@China).
	(Ich-) (-schicke->schicken-) einen (-Laptop) nach (->China).
	(Man-) (-schickt->schicken-) mir einen (-Laptop) aus (<-China).
	(Ich-) (-besuche->besuchen-) meine (-Tante) bei (@@Peking).
	
## Changelog (aktuell ist oben)

- mongodb wird jetzt ohne installation auch beim starten der webanwendung gestartet

- von wiki-markup und antlr auf peg-parser basiertes markDown umgestellt

- wicket bootstrap added

