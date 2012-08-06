package de.flapdoodle.drug.markup;

import de.flapdoodle.drug.render.StraightTextRenderer;

import junit.framework.TestCase;


public class TestMarkup extends TestCase {

	public void testCorrectButNotValidMarkup() throws MarkupException {
		String markupAsString="Das ist ein {[o:Text], den [s0:man] leicht [p:lesen] kann.} Und im zweiten Teil {[p:fehlt|fehlen] dem [s:Text] die [o1:Botschaft]}";
		String match = "Das ist ein Text, den man leicht lesen kann. Und im zweiten Teil fehlen dem Text die Botschaft";
		Markup markup = Markup.fromString(markupAsString);
		assertNotNull(markup);

		StraightTextRenderer renderer=new StraightTextRenderer();
		markup.inspect(renderer);
		assertEquals(match, renderer.toString());
	}

	public void testOtherCorrectButNotValidMarkup() throws MarkupException {
		String markupAsString="Eine aus {[o:Ton] [p:formen|geformte] [o1:Vase] mit einem [Pinsel] blau [p1:anmalen]}";
		String match = "Eine aus Ton geformte Vase mit einem Pinsel blau anmalen";
		Markup markup = Markup.fromString(markupAsString);
		assertNotNull(markup);
		
		StraightTextRenderer renderer=new StraightTextRenderer();
		markup.inspect(renderer);
		assertEquals(match, renderer.toString());
	}
}
