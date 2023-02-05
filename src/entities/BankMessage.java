package entities;

import org.json.JSONException;

public abstract class BankMessage {
	protected String cardCode;
	protected String ownner;
	protected String cvvCode;
	protected String dateExprired;
	
	protected BankMessage(String cardCode, String ownner, String cvvCode, String dateExpried) {
		this.cardCode = cardCode;
		this.ownner = ownner;
		this.cvvCode = cvvCode;
		this.dateExprired = dateExpried;
	}
	
	public abstract String toJSONString() throws JSONException;
}