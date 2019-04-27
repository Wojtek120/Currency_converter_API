import java.util.Date;

public class ChartEntry implements Comparable<ChartEntry> {
	
	private Date date;
	private String rate;

	public ChartEntry(Date date, String rate) {
		this.date = date;
		this.rate = rate;
	}

	public Date getDate() {
		return date;
	}
	
	public String getRate() {
		return rate;
	}
	
	@Override
	  public int compareTo(ChartEntry entry) {
	    if (getDate() == null || entry.getDate() == null) {
	      return 0;
	    }
	    return getDate().compareTo(entry.getDate());
	  }
}
