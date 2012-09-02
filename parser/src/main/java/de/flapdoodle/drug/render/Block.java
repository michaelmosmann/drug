package de.flapdoodle.drug.render;


public class Block {
	private Block() {
		// no instance
	}
	
	static abstract class AbstractBlock implements ITag {
		
	}
	
	public static class Start extends AbstractBlock {
		
	}
	
	public static class End extends AbstractBlock {
		
	}
}
