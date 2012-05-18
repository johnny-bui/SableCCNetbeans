package org.sablecc.sablecc;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.sablecc.sablecc.lexer.Lexer;
import org.sablecc.sablecc.lexer.LexerException;
import org.sablecc.sablecc.node.Start;
import org.sablecc.sablecc.parser.Parser;
import org.sablecc.sablecc.parser.ParserException;

/**
 *
 * @author phucluoi
 */
public class FontAndColorGeneratorTest {
	
	public FontAndColorGeneratorTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}


	/**
	 * Test of transform method, of class TokenEnumGenerator.
	 */
	@Test
	public void testTransform() {
		String testNo_ = "nodownscore";
		String testResult = TokenEnumGenerator.transform(testNo_);
		assertEquals("Nodownscore", testResult);
		String testOne_ = "test_one";
		testResult = TokenEnumGenerator.transform(testOne_);
		assertEquals("TestOne", testResult);
		String testMore_ = "test_more_downscore";
		testResult = TokenEnumGenerator.transform(testMore_);
		assertEquals("TestMoreDownscore", testResult);
	}
	@Test
	public void testGetToken()
	{
		try {
			String curDir = System.getProperty("user.dir");
			System.err.println(curDir);
			//TODO: remove absolute path
			PushbackReader p = new PushbackReader(
				new FileReader("/home/phucluoi/Documents/job/uilCompiler/UILKool/src/uil.scc"));
			Lexer l = new Lexer(p);
			Parser parser = new Parser(l);
			Start tree = parser.parse();
			FontAndColorGenerator tg = new FontAndColorGenerator();
			tree.apply(tg);
			System.out.println(tg.getTokenLst());
			
		} catch (ParserException ex) {
			ex.printStackTrace();
		} catch (LexerException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
	}
}
