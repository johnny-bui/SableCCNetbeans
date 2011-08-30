/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sableccsupport;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author phucluoi
 */
public class SCCDataObjectTest {

    public SCCDataObjectTest() {
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
	 * Test of createNodeDelegate method, of class SCCDataObject.
	 */ @Test
	public void testCreateNodeDelegate() {
		System.out.println("createNodeDelegate");
		SCCDataObject instance = null;
		Node expResult = null;
		Node result = instance.createNodeDelegate();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getLookup method, of class SCCDataObject.
	 */ @Test
	public void testGetLookup() {
		System.out.println("getLookup");
		SCCDataObject instance = null;
		Lookup expResult = null;
		Lookup result = instance.getLookup();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

}