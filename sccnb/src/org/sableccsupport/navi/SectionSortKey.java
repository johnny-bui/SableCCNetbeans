
package org.sableccsupport.navi;

/**
 *
 * @author phucluoi
 * @version Dec 22, 2012
 */
public enum SectionSortKey {
	PACKAGE("Package"), 
	HELPER("Helpers"), 
	STATE("States"),
	TOKEN("Tokens"), 
	IGNORED("Ignored Tokens"), 
	PRODUCT("Procductions"), 
	AST("Abstract Syntax Tree");
	
	//private final int key;
	private final String name;
	SectionSortKey(/*int key,*/ String name) {
		//this.key = key;
		this.name = name;
	}

	public String getSectionName(){
		return name;
	}
/*
	public int key() {
		return key;
	}
*/
}
