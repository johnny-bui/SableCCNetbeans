package org.sableccsupport.parser;

import java.util.Collection;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.ParserFactory;

/**
 *
 * @author phucluoi
 */
public class SCCParserFactory extends ParserFactory {

	@Override
	public Parser createParser(Collection<Snapshot> snapshots) {
		return new SCCParserFasader();
	}
}
