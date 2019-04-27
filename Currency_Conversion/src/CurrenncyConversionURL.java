import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.json.JSONObject;



public class CurrenncyConversionURL {
	
	//private String urlAddress = "https://api.exchangeratesapi.io/latest";
	private URL url;
	private HttpURLConnection connection;
	private int responseCode;
	private Scanner sc;
	private String inline = "";
	private JSONObject jsonObject;

	public CurrenncyConversionURL(String urlAddress, String firstCurrency) {
		query(urlAddress, firstCurrency);		
	}
	
	public void query(String urlAddressRaw, String firstCurrency) {
		
		String urlAddress = urlAddressRaw + firstCurrency;
		System.out.println(urlAddress);
		
		try {
			url = new URL(urlAddress);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			responseCode = connection.getResponseCode();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		if(responseCode!= 200)
			throw new RuntimeException("HttpResponseCode: " +responseCode);
		else
		{			
			try {
				sc = new Scanner(url.openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(sc.hasNext())
			{
				inline+=sc.nextLine();
			}
			//System.out.println(inline);
			
			sc.close();
		}
		
		connection.disconnect();

			jsonObject = new JSONObject(inline);
			jsonObject = (JSONObject)jsonObject.get("rates");
			
			//System.out.println(Double.toString((Double)jsonObject.get(secondCurrency)));
	}
	
	
	public ArrayList<String> getArrayListWithCurrencyNames()
	{
		Iterator keysToCopyIterator = jsonObject.keys();
		ArrayList<String> keysList = new ArrayList<String>();
		while(keysToCopyIterator.hasNext()) {
		    String key = (String) keysToCopyIterator.next();
		    keysList.add(key);
		    //System.out.println(key);
		}
		
		return keysList;
	}
	
	
	public double getRate(String secondCurrency)
	{
		return (Double)jsonObject.get(secondCurrency);
	}
	

}
