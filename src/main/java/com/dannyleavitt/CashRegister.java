package com.dannyleavitt;

import java.util.Arrays;

public class CashRegister {
	public int totalAmountInRegister;
	//each array index holds number of bills of a particular denomination in set {$20 $10 $5 $2 $1}
	//denominations indexed in decreasing order.
	public int[] billDenominationArray;
	
	//array to find the value of bill denomination for index in billDenominationArray
	//will minimize hard coding and allow for easier refactoring later if we want to specify bill denominations at runtime
	public int[] billLookup;
	
	public CashRegister() {
		this.totalAmountInRegister = 0;
		this.billDenominationArray = new int[]{0,0,0,0,0};
		this.billLookup = new int[]{20,10,5,2,1};	
	}
	
	public String show(){
		StringBuilder sb = new StringBuilder();
		sb.append("$").append(totalAmountInRegister);
		for(int i:billDenominationArray){
			sb.append(" ").append(i);
		}
		return sb.toString();
	}
	
	public void put(int[] addBillsArray){
		int totalAmount = 0;
		for(int i=0; i<addBillsArray.length;i++){
			billDenominationArray[i] += addBillsArray[i];
			totalAmount += billLookup[i]*addBillsArray[i];
		}
		totalAmountInRegister += totalAmount;
	}
	
	public void take(int[] removeBillsArray) throws NotEnoughBillsException{
		int totalAmount = 0;
		for(int i=0; i<removeBillsArray.length;i++){
			if(billDenominationArray[i] < removeBillsArray[i]) throw new NotEnoughBillsException();
		}
		for(int i=0; i<removeBillsArray.length;i++){
			billDenominationArray[i] -= removeBillsArray[i];
			totalAmount += billLookup[i]*removeBillsArray[i];
		}
		totalAmountInRegister -= totalAmount;
	}
	
	public String change(int changeAmount) throws NotEnoughBillsException{
		//TODO
		return "";
	}
	
	public void clear(){
		//clear out the register
		totalAmountInRegister = 0;
		Arrays.fill(billDenominationArray, 0);
	}
	
}
