import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
	private final String dbName = "currencyconversion.currencytable";
    private Connection connect = null;
    private ResultSet resultSet = null;
    private int size = 0;
    

	public DatabaseHelper() {
		
		try {
			//Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby:C:\\Users\\Wojciech\\MyDB;user=waluta;password=waluta");			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			//close();
		}
	}
	
	public void getDataFromDatabase(CurrencyDatabase entries, int order)
	{
		String orderString = " ORDER BY DATEANDTIME";
		switch (order) {
	    case 0:
	    	orderString = " ORDER BY DATEANDTIME DESC";
	        break;
	    case 1:
	    	orderString = " ORDER BY DATEANDTIME";
	        break;
	    case 2:
	    	orderString = " ORDER BY FIRSTAMOUNT DESC";
	        break;
	    case 3:
	    	orderString = " ORDER BY FIRSTAMOUNT";
	        break;
	    case 4:
	    	orderString = " ORDER BY FIRSTCURRENCY DESC";
	        break;
	    case 5:
	    	orderString = " ORDER BY FIRSTCURRENCY";
	        break;
	    case 6:
	    	orderString = " ORDER BY SECONDCURRENCY DESC";
	        break;
	    case 7:
	    	orderString = " ORDER BY SECONDCURRENCY";
	        break;
	}
		
		
		try {
			PreparedStatement statement = connect.prepareStatement("SELECT * FROM " + dbName + orderString);
			
			resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	        	java.sql.Timestamp timestamp = resultSet.getTimestamp("DATEANDTIME");
	            String firstAmount = resultSet.getString("FIRSTAMOUNT");
	            String firstCurrency = resultSet.getString("FIRSTCURRENCY");
	            String secondAmount = resultSet.getString("SECONDAMOUNT");
	            String secondCurrency =  resultSet.getString("SECONDCURRENCY");
	            String comment = resultSet.getString("COMMENT");
	            
	            CurrencyConversionDataEntry entry = new CurrencyConversionDataEntry();
	            entry.setTimestamp(timestamp);
	            entry.setAmoutntOfMoney1(firstAmount);
	            entry.setCurrency1(firstCurrency);		
	    		entry.setAmoutntOfMoney2(secondAmount);
	    		entry.setCurrency2(secondCurrency);
	    		entry.setComment(comment);
	            entries.addEntry(entry);
	        }
	        
	        size = entries.getEntriesCount();
		}catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	
	public void putDataToDatabase(CurrencyDatabase entries)
	{
		try {
			PreparedStatement statement = connect.prepareStatement("DELETE FROM " + dbName);			
			statement.executeUpdate();
			statement.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		
		for(int i = 0; i < entries.getEntriesCount()/* - size*/; i++)
		{
			try {
				PreparedStatement statement = connect.prepareStatement("INSERT INTO " + dbName + 
						"(DATEANDTIME, FIRSTAMOUNT, FIRSTCURRENCY, SECONDAMOUNT, SECONDCURRENCY, COMMENT) values (?, ?, ?, ?, ?, ?)");
				statement.setTimestamp(1, entries.getEntry(i).getTimestamp());
				statement.setDouble(2, Double.parseDouble(entries.getEntry(i).getAmoutntOfMoney1()));
				statement.setString(3, entries.getEntry(i).getCurrency1());
				statement.setDouble(4, Double.parseDouble(entries.getEntry(i).getAmoutntOfMoney2()));
				statement.setString(5, entries.getEntry(i).getCurrency2());
				statement.setString(6, entries.getEntry(i).getComment());
				
				statement.executeUpdate();
			} catch (SQLException sqlExcept) {
				sqlExcept.printStackTrace();
			}
		}
		
		size = entries.getEntriesCount();
	}
	

	public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

	public void deleteAllEntries(CurrencyDatabase entries) {
		try {
			PreparedStatement statement = connect.prepareStatement("DELETE FROM " + dbName);			
			statement.executeUpdate();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		
		entries.eraseEntries();
		size = 0;
	}
}
