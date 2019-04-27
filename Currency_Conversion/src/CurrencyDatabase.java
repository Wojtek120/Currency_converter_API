import java.util.ArrayList;
import java.util.List;


public class CurrencyDatabase  extends AbstractModelObject{
	
	private List<CurrencyConversionDataEntry> entries = new ArrayList<CurrencyConversionDataEntry>();

	public CurrencyDatabase() {	
	}
	
	public void addEntry(CurrencyConversionDataEntry entry) {
		List<CurrencyConversionDataEntry> oldValue = entries;
		entries = new ArrayList<CurrencyConversionDataEntry>(entries);
		entries.add(0, entry);
		firePropertyChange("entries", oldValue, entries);
		firePropertyChange("entriesCount", oldValue.size(), entries.size());
	}
	
	public List<CurrencyConversionDataEntry> getEntries() {
		return entries;
	}
	
	public CurrencyConversionDataEntry getEntry(int i) {
		return entries.get(i);
	}
	
	public void eraseEntries() {
		List<CurrencyConversionDataEntry> oldValue = entries;
		entries = new ArrayList<CurrencyConversionDataEntry>();
		firePropertyChange("entries", oldValue, entries);
		firePropertyChange("entriesCount", oldValue.size(), entries.size());
	}
	
	public int getEntriesCount() {
		return entries.size();
	}

}
