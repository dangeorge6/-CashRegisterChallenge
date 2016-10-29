package com.dannyleavitt;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CashRegisterTest {

	private CashRegister cashRegister;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		 cashRegister = new CashRegister();
	}

	@Test
	public void registerShouldShowCorrectInitialAmount() {
		assertEquals(cashRegister.show(),"0 0 0 0 0 0");
	}

}
