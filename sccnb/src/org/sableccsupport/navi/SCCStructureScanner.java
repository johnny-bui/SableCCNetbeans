/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sableccsupport.navi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.ParserResult;
import org.sableccsupport.parserfasader.SCCParserResult;

/**
 *
 * @author phucluoi
 * @version Dec 16, 2012
 */
public class SCCStructureScanner implements StructureScanner{

	@Override
	public List<? extends StructureItem> scan(ParserResult pr) {
		try{
			SCCParserResult sccpr = (SCCParserResult) pr;
			List<? extends StructureItem> items = sccpr.getStructure();
			return items;
		}catch (ClassCastException ex){
			return makeEmptyStructure();
		}
	}

	@Override
	public Map<String, List<OffsetRange>> folds(ParserResult pr) {
		HashMap<String,List<OffsetRange>> foldTab = new HashMap<String, List<OffsetRange>>();
		return foldTab;
		//throw new UnsupportedOperationException("Not supported yet. Wait for it forever");
	}

	@Override
	public Configuration getConfiguration() {
		return new Configuration(true, true);
	}
	
	private static List<? extends StructureItem> makeEmptyStructure(){
		// TODO: make a default structure here (of course without navi feature,
		// just show some info about error)
		List<? extends StructureItem> items = new ArrayList<StructureItem>();
		return items;
	}
}
