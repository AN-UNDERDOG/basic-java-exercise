package atm;

import java.io.Serializable;
import java.util.Date;

class Transaction implements Serializable{
	
	Date date;
	char type;
	double amount;
	double balance;
	String description;
	
	public Transaction(char type, double amount, double balance, String description) {
		this.type = type;
		this.amount = amount;
		this.balance = balance;
		this.description = description;
		this.date = new Date();
	}
	
	public String toString() {
		return "����[����:"+date.toLocaleString()+
				"\n��������:"+this.type+
				"\t���:"+this.amount+
				"\t���:"+this.balance+
				"\n����:"+this.description+"]";
	}
	
	
	
}