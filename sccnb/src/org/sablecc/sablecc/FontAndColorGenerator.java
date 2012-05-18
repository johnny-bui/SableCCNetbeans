package org.sablecc.sablecc;

import org.sablecc.sablecc.node.ATokenDef;

/**
 *
 * @author phucluoi
 * @version May 17, 2012
 */
public class FontAndColorGenerator extends TokenEnumGenerator
{
	
	private String PRE = 
		"<!DOCTYPE fontscolors PUBLIC\n" + 
		"\t\"-//NetBeans//DTD Editor Fonts and Colors settings 1.1//EN\"\n" +
		"\t\"http://www.netbeans.org/dtds/EditorFontsColors-1_1.dtd\">\n"   +
		"<fontscolors>\n"
		;
	private String POST = "</fontscolors>";
	
	@Override
	public String getPRE()
	{
		return this.PRE;
	}
	@Override
	public String getPOST()
	{
		return this.POST;
	}
	
	@Override
	protected String constructEntryForToken(ATokenDef node) {
		String entry = "\t<fontcolor name=\"" + 
				transform(node.getId().getText()) + 
				"\" default=\"\" foreColor=\"\" />\n" ;
		return entry;
	}

	@Override
	protected String constructEntryForEOF() {
		String entry = "\t<fontcolor name=\"EOF\" default=\"blank\" />\n" ;
		return entry;
	}

	@Override
	protected String constructEntryForError() 
	{
		String entry = "\t<fontcolor name=\"ERROR\" default=\"error\" />\n" ;
		return entry;
	}
	
	
	
}
