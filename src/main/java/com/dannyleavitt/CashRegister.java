package com.dannyleavitt;

import java.util.*;

public class CashRegister {
	
	private int totalAmountInRegister;
	// each array index holds number of bills of a particular denomination in
	// set {$20 $10 $5 $2 $1}
	// denominations indexed in decreasing order.
	private int[] billDenominationArray;

	// array to find the value of bill denomination for index in
	// billDenominationArray
	// will minimize hard coding and allow for easier refactoring later if we
	// want to specify bill denominations at runtime
	private int[] billLookup;

	public CashRegister() {
		this.totalAmountInRegister = 0;
		this.billDenominationArray = new int[] { 0, 0, 0, 0, 0 };
		this.billLookup = new int[] { 20, 10, 5, 2, 1 };
	}

	public String show() {
		StringBuilder sb = new StringBuilder();
		sb.append("$").append(totalAmountInRegister);
		for (int i : billDenominationArray) {
			sb.append(" ").append(i);
		}
		return sb.toString();
	}

	public void put(int[] addBillsArray) {
		int totalAmount = 0;
		for (int i = 0; i < addBillsArray.length; i++) {
			billDenominationArray[i] += addBillsArray[i];
			totalAmount += billLookup[i] * addBillsArray[i];
		}
		totalAmountInRegister += totalAmount;
	}

	public void take(int[] removeBillsArray) throws NotEnoughBillsException {
		int totalAmount = 0;
		for (int i = 0; i < removeBillsArray.length; i++) {
			if (billDenominationArray[i] < removeBillsArray[i])
				throw new NotEnoughBillsException();
		}
		for (int i = 0; i < removeBillsArray.length; i++) {
			billDenominationArray[i] -= removeBillsArray[i];
			totalAmount += billLookup[i] * removeBillsArray[i];
		}
		totalAmountInRegister -= totalAmount;
	}

	public void clear() {
		// clear out the register
		totalAmountInRegister = 0;
		Arrays.fill(billDenominationArray, 0);
	}

	public String change(int changeAmount) throws NotEnoughBillsException {
		if (changeAmount > totalAmountInRegister) {
			//asking to change an amount not present in register
			throw new NotEnoughBillsException();
		}

		//use DP algorithm to get change
		int[] changeBack = getChangeDP(changeAmount);
		//remove solution from register
		take(changeBack);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < changeBack.length; i++) {
			sb.append(changeBack[i]);
			if (i != changeBack.length - 1)
				sb.append(" ");
		}
		return sb.toString();
	}

	private int[] getChangeDP(int total) throws NotEnoughBillsException {
		//Dynamic Programming solution to minimum coin problem, modified for limited number of each coin
		
		int[] SubProblems = new int[total + 1];	//array holding the minimum number of coins needed to make change for totals 0..total
		int[] coinUsedAtSubproblem = new int[total + 1];	//array to store the index of coin last used in solution to given subproblem

		int numOfCoins = billLookup.length;
		int[][] coinsLeftAfterSP = new int[total + 1][numOfCoins];		//each row holds the coin counts remaining after solution for the subproblem is executed
		for (int i = 0; i < numOfCoins; i++) {
			//initialize first row with starting amounts of each coin
			coinsLeftAfterSP[0][i] = billDenominationArray[i];
		}
		
		SubProblems[0] = 0;	//0 bills are needed to make $0 in change
		
		for (int spTotal = 1; spTotal <= total; spTotal++) {	//iterate through subproblems
			SubProblems[spTotal] = Integer.MAX_VALUE;	//start subproblem at +infinity
			for (int coinIndex = 0; coinIndex < numOfCoins; coinIndex++) {
				int coinValue = billLookup[coinIndex];
				int backTheValOfCoin = spTotal - coinValue;
				
				if (spTotal >= coinValue && (SubProblems[backTheValOfCoin] < Integer.MAX_VALUE)
						&& (coinsLeftAfterSP[backTheValOfCoin][coinIndex] > 0)) {
					//coin can only be chosen for subproblem if it is less than total for sp,
					//and there is a solution derived for SubProblems[backTheValOfCoin], and there is a coin of this denomination left to use
					if ((SubProblems[spTotal] > 1 + SubProblems[backTheValOfCoin])) {
						//if we go back the value of this coin in subproblems and add this coin (add 1 more coin to solution), 
						//will it be less than solutions we have derived thus far? If so, let's store it.
						SubProblems[spTotal] = 1 + SubProblems[backTheValOfCoin];
						coinUsedAtSubproblem[spTotal] = coinIndex;	//store index of the coin you used to solve this subproblem

						for (int m = 0; m < numOfCoins; m++) {
							//update the counts for each count for this subproblem
							if (m == coinIndex) {
								//this is the coin we used, decrement it from coins left array for this subproblem
								coinsLeftAfterSP[spTotal][m] = coinsLeftAfterSP[backTheValOfCoin][m] - 1;
							} else {
								coinsLeftAfterSP[spTotal][m] = coinsLeftAfterSP[backTheValOfCoin][m];
							}
						}

					}
				} else if (spTotal < coinValue) {
					//this coin is too big to be used for subproblem
					coinsLeftAfterSP[spTotal][coinIndex] = coinsLeftAfterSP[0][coinIndex];
				}
			}
		}
		
		if (coinUsedAtSubproblem[total] == 0){
			// no change could be made
			throw new NotEnoughBillsException();
		}
		
		//return diff between starting coin count and resulting coin count, this is the change used
		return arrayDiff(billDenominationArray,coinsLeftAfterSP[total]);
	
	}

	private static int[] arrayDiff(int[] a, int[] b) {
		if(a.length != b.length) return null;
		
		int[] c = new int[a.length];
		for(int i=0; i<a.length; i++){
			c[i] = a[i] - b[i];
		}
		return c;
	}

}
