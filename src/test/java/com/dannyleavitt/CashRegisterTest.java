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
		//make sure no money displays correctly after initialization
		assertEquals("$0 0 0 0 0 0",cashRegister.show());
	}
	
	@Test
	public void registerShouldShowCorrectAmountsAfterAddingMoney() {
		//add 68 dollars in bills
		int[] addBillsArray = {1,2,3,4,5};
		cashRegister.put(addBillsArray);
		//make sure amounts correct
		assertEquals("$68 1 2 3 4 5", cashRegister.show());
	}
	
	@Test
	public void registerShouldShowCorrectAmountsAfterAddingAndRemovingMoney() throws NotEnoughBillsException {
		//going to model the input/output of challenge pdf
		//put in $68 in bills
		cashRegister.put(new int[]{1,2,3,4,5});
		//put in $60
		cashRegister.put(new int[]{1,2,3,0,5});
		//take $85
		cashRegister.take(new int[]{1,4,3,0,10});
		
		assertEquals("$43 1 0 3 4 0", cashRegister.show());
	}
	
	@Test
	public void registerShouldClearCorrectly(){
		cashRegister.put(new int[]{1,2,3,4,5});
		cashRegister.clear();
		
		assertEquals("$0 0 0 0 0 0", cashRegister.show());
	}
	
	@Test(expected=NotEnoughBillsException.class)
	public void registerShouldThrowErrorIfRemovingMoreThanItHas() throws NotEnoughBillsException {
		cashRegister.put(new int[]{1,2,3,4,5});
		//take 100 tens, which it doesn't have
		cashRegister.take(new int[]{1,100,0,0,0});
	}
	
	@Test
	public void registerShouldShowGiveCorrectChangeAndRemoveFromRegister() throws NotEnoughBillsException {
		cashRegister.put(new int[]{1,2,3,4,5});
		cashRegister.put(new int[]{1,2,3,0,5});
		cashRegister.take(new int[]{1,4,3,0,10});
		//change 11
		String changeReturn = cashRegister.change(11);

		assertEquals("0 0 1 3 0", changeReturn);
		assertEquals("$32 1 0 2 1 0", cashRegister.show());
	}
	
	@Test
	public void registerShouldMakeCorrectChangeFor8WithVariousCombosOf13() throws NotEnoughBillsException {
		//various test cases trying to make change for 8 with different combos of 13 in register
		
		//add 13 this way
		cashRegister.put(new int[]{0,0,2,1,1});
		assertEquals("0 0 1 1 1",cashRegister.change(8));
		assertEquals("$5 0 0 1 0 0",cashRegister.show());
		cashRegister.clear();
		
		//add 13 this way
		//a greedy solution would fail in this case. It would take a $5, and not be able to make $8 with remaining 2s
		//however solution still exists with all 2s.
		cashRegister.put(new int[]{0,0,1,4,0});
		assertEquals("0 0 0 4 0",cashRegister.change(8));
		cashRegister.clear();
		
		//add 13 this way, just one solution for 8
		cashRegister.put(new int[]{0,0,0,0,13});
		assertEquals("0 0 0 0 8",cashRegister.change(8));
		cashRegister.clear();
		
		//add 13 this way, there are two solutions here, but returns solution that maximizes use of larger bills
		cashRegister.put(new int[]{0,0,1,2,3});
		assertEquals("0 0 1 1 1",cashRegister.change(8));		
	}
	
	@Test(expected=NotEnoughBillsException.class)
	public void registerShouldThrowErrorIfCantMakeChange() throws NotEnoughBillsException {
		cashRegister.put(new int[]{1,2,3,4,5});
		cashRegister.put(new int[]{1,2,3,0,5});
		cashRegister.take(new int[]{1,4,3,0,10});
		cashRegister.change(11);
		//as in pdf, a change for 14 at this point would be impossible, even though there is $32 total in register
		cashRegister.change(14);
	}
	
	@Test(expected=NotEnoughBillsException.class)
	public void registerShouldThrowErrorIfChangeAmountOverTotalInRegister() throws NotEnoughBillsException {
		cashRegister.put(new int[]{1,2,3,4,5});
		//$400 is over what's in register so will throw error
		cashRegister.change(400);
	}
	

}
