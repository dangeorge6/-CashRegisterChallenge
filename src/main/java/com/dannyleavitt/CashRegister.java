package com.dannyleavitt;

public class CashRegister {
	public int totalAmountInRegister;
	//each array index holds number of bills of a particular denomination in set {$20 $10 $5 $2 $1}
	//denominations indexed in decreasing order.
	public int[] billDenominationArray;
	
	public CashRegister() {
		this.totalAmountInRegister = 0;
		this.billDenominationArray = new int[]{0,0,0,0,0};
	}
	
	public String show(){
		StringBuilder sb = new StringBuilder();
		sb.append(totalAmountInRegister);
		for(int i:billDenominationArray){
			sb.append(" ").append(i);
		}
		return sb.toString();
	}
	
	public void put(int[] addBillsArray){
		for(int i=0; i<addBillsArray.length;i++){
			billDenominationArray[i] += addBillsArray[i];
		}
	}
	
	public void take(int[] removeBillsArray){
		for(int i=0; i<removeBillsArray.length;i++){
			if(billDenominationArray[i] < removeBillsArray[i]) throw new NotEnoughBillsException();
		}
		for(int i=0; i<removeBillsArray.length;i++){
			billDenominationArray[i] -= removeBillsArray[i];
		}
	}
	
	public void change(int changeAmount){
		//TODO
		
	}
	
}
