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
import org.sableccsupport.parser.ast.GrammarStructure;
import org.sableccsupport.parser.ast.SCCNode;
import org.sableccsupport.parserfasader.SCCParserResult;

/**
 *
 * @author phucluoi
 * @version Dec 16, 2012
 */
public class SCCStructureScanner implements StructureScanner {
	
	@Override
	public List<? extends StructureItem> scan(ParserResult pr) {
		try {
			SCCParserResult sccpr = (SCCParserResult) pr;
			GrammarStructure str = sccpr.getStructure();
			return makeStructureItem(str);
		} catch (ClassCastException ex) {
			return makeEmptyStructure();
		}
	}

	@Override
	public Map<String, List<OffsetRange>> folds(ParserResult pr) {
		HashMap<String, List<OffsetRange>> foldTab = new HashMap<String, List<OffsetRange>>();
		return foldTab;
		//throw new UnsupportedOperationException("Not supported yet. Wait for it forever");
	}

	@Override
	public Configuration getConfiguration() {
		return new Configuration(true, true);
	}

	private static List<? extends StructureItem> makeEmptyStructure() {
		// TODO: make a default structure here (of course without navi feature,
		// just show some info about error)
		List<? extends StructureItem> items = new ArrayList<StructureItem>();
		return items;
	}

	private List<StructureItem> makeStructureItem(GrammarStructure structure) {
		List<StructureItem> grammar = new ArrayList<StructureItem>();
		// add package section
		SCCNode packageNode = structure.getPackage();
		if (packageNode != null) {
			SCCStructureItem packageSection = SCCStructureItem.createSectionItem(
					packageNode.name(), packageNode.offset(), SectionSortKey.PACKAGE);
			grammar.add(packageSection);
		}
		// add helper section
		SCCNode helpers = structure.getHelpers();
		makeSection(SectionSortKey.HELPER,helpers, new ItemBuilder() {
			@Override
			SCCStructureItem buildItem(String name, long offset) {
				return SCCStructureItem.createHelperItem(name, offset);
			}
		},grammar);
				
		//grammar.add(helpersSection);
		// add state section
		SCCNode states = structure.getStates();
		makeSection(SectionSortKey.STATE, states, new ItemBuilder() {
			@Override
			SCCStructureItem buildItem(String name, long offset) {
				return SCCStructureItem.createStateItem(name, offset);
			}
		},grammar);
		// add token section
		SCCNode tokens = structure.getTokens();
		makeSection(SectionSortKey.TOKEN, tokens, new ItemBuilder() {
			@Override
			SCCStructureItem buildItem(String name, long offset) {
				return SCCStructureItem.createTokenItem(name, offset);
			}
		},grammar);
		// add ignored token section
		SCCNode ignoredTokens = structure.getIgnoredTokens();
		makeSection(SectionSortKey.IGNORED, ignoredTokens, new ItemBuilder() {
			@Override
			SCCStructureItem buildItem(String name, long offset) {
				return SCCStructureItem.createTokenItem(name, offset);
			}
		},grammar);
		// add production section
		SCCNode products = structure.getProducts();
		makeSection(SectionSortKey.PRODUCT, products, new ItemBuilder() {
			@Override
			SCCStructureItem buildItem(String name, long offset) {
				return SCCStructureItem.createProductItem(name, offset);
			}
		},grammar);
		// add ast section
		SCCNode ast = structure.getAST();
		makeSection(SectionSortKey.AST, ast, new ItemBuilder() {
			@Override
			SCCStructureItem buildItem(String name, long offset) {
				return SCCStructureItem.createProductItem(name, offset);
			}
		},grammar);
		return grammar;
	}

	private SCCStructureItem makeSection(
			SectionSortKey key,
			SCCNode section,
			ItemBuilder builder,
			List<StructureItem> grammar) {
		assert section != null;
		if (section.getChildNodes().isEmpty()) {
			return null;
		} else {
			SCCStructureItem newSection = SCCStructureItem.createSectionItem(
					section.name(), section.offset(), key);

			List<SCCStructureItem> sectionItem = new ArrayList<SCCStructureItem>();
			for (SCCNode n : section.getChildNodes()) {
				SCCStructureItem s = builder.buildItem(n.name(), n.offset());
				sectionItem.add(s);
				if (!n.getChildNodes().isEmpty()) {
					List<SCCStructureItem> c = new ArrayList<SCCStructureItem>();
					for (SCCNode nn : n.getChildNodes()) {
						SCCStructureItem ii = builder.buildItem(nn.name(), nn.offset());
						c.add(ii);
					}
					s.setChild(c);
				}
			}
			newSection.setChild(sectionItem);
			grammar.add(newSection);
			return newSection;
		}
	}
}

abstract class ItemBuilder {

	abstract SCCStructureItem buildItem(String name, long offset);
}