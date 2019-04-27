public class CurrencyConversionDataEntry  extends AbstractModelObject{
	
	private String amoutntOfMoney1;
	private String currency1;
	private String amoutntOfMoney2;
	private String currency2;
	private java.sql.Timestamp timestamp;
	private String comment;

	public CurrencyConversionDataEntry() {
		super();
		amoutntOfMoney1 = "null";
	}

	public CurrencyConversionDataEntry(java.sql.Timestamp timestamp, String amoutntOfMoney1, 
			String currency1, String amoutntOfMoney2, String currency2, String comment) {
		super();
		this.amoutntOfMoney1 = amoutntOfMoney1;
		this.currency1 = currency1;
		this.amoutntOfMoney1 = amoutntOfMoney2;
		this.currency1 = currency2;
		this.timestamp = timestamp;
		this.comment = comment;
	}
	
	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}
	
	public String getAmoutntOfMoney1() {
		return amoutntOfMoney1;
	}

	
	public String getCurrency1() {
		return currency1;
	}
	
	public String getAmoutntOfMoney2() {
		return amoutntOfMoney2;
	}

	
	public String getCurrency2() {
		return currency2;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setTimestamp(java.sql.Timestamp timestamp) {
		java.sql.Timestamp oldValue = this.timestamp;
		firePropertyChange("timestamp", oldValue, timestamp);
		this.timestamp = timestamp;
	}	
	
	public void setAmoutntOfMoney1(String amoutntOfMoney1) {
		String oldValue = this.amoutntOfMoney1;
		firePropertyChange("amoutntOfMoney1", oldValue, amoutntOfMoney1);
		this.amoutntOfMoney1 = amoutntOfMoney1;
	}

	public void setCurrency1(String currency1) {
		String oldValue = this.currency1;
		firePropertyChange("currency1", oldValue, currency1);
		this.currency1 = currency1;
	}
	
	public void setAmoutntOfMoney2(String amoutntOfMoney2) {
		String oldValue = this.amoutntOfMoney2;
		firePropertyChange("amoutntOfMoney2", oldValue, amoutntOfMoney2);
		this.amoutntOfMoney2 = amoutntOfMoney2;
	}

	public void setCurrency2(String currency2) {
		String oldValue = this.currency2;
		firePropertyChange("currency2", oldValue, currency2);
		this.currency2 = currency2;
	}
	
	public void setComment(String comment) {
		String oldValue = this.comment;
		firePropertyChange("comment", oldValue, comment);
		this.comment = comment;
	}

}
