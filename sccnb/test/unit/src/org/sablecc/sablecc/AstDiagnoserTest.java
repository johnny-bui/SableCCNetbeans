/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sablecc.sablecc;

import de.htwsaarland.astVisual.graphRepresent.GraphContainer;
import org.junit.*;
import static org.junit.Assert.*;
import org.sablecc.sablecc.node.*;

/**
 *
 * @author phucluoi
 */
public class AstDiagnoserTest {
	
	public AstDiagnoserTest() {
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
	 * Test of caseAGrammar method, of class AstDiagnoser.
	 */
	@Test
	public void testCaseAGrammar() {
		System.out.println("caseAGrammar");
		AGrammar node = null;
		AstDiagnoser instance = null;
		instance.caseAGrammar(node);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of caseAAst method, of class AstDiagnoser.
	 */
	@Test
	public void testCaseAAst() {
		System.out.println("caseAAst");
		AAst node = null;
		AstDiagnoser instance = null;
		instance.caseAAst(node);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of caseAAstProd method, of class AstDiagnoser.
	 */
	@Test
	public void testCaseAAstProd() {
		System.out.println("caseAAstProd");
		AAstProd node = null;
		AstDiagnoser instance = null;
		instance.caseAAstProd(node);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of caseAAstAlt method, of class AstDiagnoser.
	 */
	@Test
	public void testCaseAAstAlt() {
		System.out.println("caseAAstAlt");
		AAstAlt node = null;
		AstDiagnoser instance = null;
		instance.caseAAstAlt(node);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of caseAElem method, of class AstDiagnoser.
	 */
	@Test
	public void testCaseAElem() {
		System.out.println("caseAElem");
		AElem node = null;
		AstDiagnoser instance = null;
		instance.caseAElem(node);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of caseATokenSpecifier method, of class AstDiagnoser.
	 */
	@Test
	public void testCaseATokenSpecifier() {
		System.out.println("caseATokenSpecifier");
		ATokenSpecifier node = null;
		AstDiagnoser instance = null;
		instance.caseATokenSpecifier(node);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of caseAProductionSpecifier method, of class AstDiagnoser.
	 */
	@Test
	public void testCaseAProductionSpecifier() {
		System.out.println("caseAProductionSpecifier");
		AProductionSpecifier node = null;
		AstDiagnoser instance = null;
		instance.caseAProductionSpecifier(node);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of inAElem method, of class AstDiagnoser.
	 */
	@Test
	public void testInAElem() {
		System.out.println("inAElem");
		AElem node = null;
		AstDiagnoser instance = null;
		instance.inAElem(node);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of outAElem method, of class AstDiagnoser.
	 */
	@Test
	public void testOutAElem() {
		System.out.println("outAElem");
		AElem node = null;
		AstDiagnoser instance = null;
		instance.outAElem(node);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getError method, of class AstDiagnoser.
	 */
	@Test
	public void testGetError() {
		System.out.println("getError");
		AstDiagnoser instance = null;
		int expResult = 0;
		int result = instance.getError();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of hasAST method, of class AstDiagnoser.
	 */
	@Test
	public void testHasAST() {
		System.out.println("hasAST");
		AstDiagnoser instance = null;
		boolean expResult = false;
		boolean result = instance.hasAST();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getGraphContainer method, of class AstDiagnoser.
	 */
	@Test
	public void testGetGraphContainer() {
		System.out.println("getGraphContainer");
		AstDiagnoser instance = null;
		GraphContainer expResult = null;
		GraphContainer result = instance.getGraphContainer();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
}
