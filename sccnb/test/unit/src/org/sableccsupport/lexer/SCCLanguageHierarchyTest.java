/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sableccsupport.lexer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author phucluoi
 */
public class SCCLanguageHierarchyTest {

    public SCCLanguageHierarchyTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testGetToken() {
		SCCTokenId tid;
		for (int i = -1; i<10; ++i)
		{
			tid = SCCLanguageHierarchy.getToken(i);
			System.out.println(tid);
		}
	}

}