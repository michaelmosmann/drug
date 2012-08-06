package de.flapdoodle.drug.markup;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import de.flapdoodle.drug.parser.MarkupLexer;
import de.flapdoodle.drug.parser.MarkupParser;

public class Markup extends AbstractContainer {

	public Markup(List<IPart> parts) {
		super(parts);
	}

	public static Markup fromString(String text) throws MarkupException {
		Markup ret = null;

		if (text != null) {
			ANTLRStringStream input = new ANTLRStringStream(text);
			MarkupLexer lexer = new MarkupLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			MarkupParser parser = new MarkupParser(tokens);

			MarkupParser.markup_return result;
			try {
				result = parser.markup();
				if (result != null) {
					ret = result.rmarkup;
				}
			} catch (RecognitionException e) {
				throw new MarkupException(text, e);
			}
		}
		return ret;
	}
	
	@Override
	public void inspect(IMarkupVisitor visitor) {
		visitor.begin();
		super.inspect(visitor);
		visitor.end();
	}
}
