import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONObject;

public class CurrencyHistoricalRatesURL {

	private URL url;
	private HttpURLConnection connection;
	private int responseCode;
	private Scanner sc;
	private String inline = "";
	private JSONObject jsonObject;
	private List<ChartEntry> listChartEntries;
	
	public CurrencyHistoricalRatesURL(String urlAddress, String firstCurrency, String startDate, String endDate, String secondCurrency) {
		listChartEntries = new ArrayList<ChartEntry>();
		query(urlAddress, firstCurrency, startDate, endDate, secondCurrency);	
	}
	
public void query(String urlAddressRaw, String firstCurrency, String startDate, String endDate, String secondCurrency) {
		
		String urlAddress = urlAddressRaw + firstCurrency + "&start_at=" + startDate + "&end_at=" + endDate + "&symbols=" + secondCurrency;
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
			System.out.println(inline);
			
			sc.close();
		}
		
		connection.disconnect();

			jsonObject = new JSONObject(inline);
			jsonObject = (JSONObject)jsonObject.get("rates");
			
			System.out.println(jsonObject.length());
			
			paraseJsonObject(jsonObject);

	}

	public void paraseJsonObject(JSONObject jsonObj) {
	    for (String keyStr : jsonObj.keySet()) {
	        Object keyvalue = jsonObj.get(keyStr);
	
	        //System.out.println("key: "+ keyStr);
	
	        if (keyvalue instanceof JSONObject)
	        {
	        	JSONObject jsonObj2 = (JSONObject)keyvalue;
	        	for (String keyStr2 : jsonObj2.keySet()) {
	    	        Object keyvalue2 = jsonObj2.get(keyStr2);
	    	        
	    	        //System.out.println(" value: " + keyvalue2);
	    	        
	    	        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    	        Date date;
					try {
						date = format.parse(keyStr);
						listChartEntries.add(new ChartEntry(date, keyvalue2.toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        }
	       // if (keyvalue instanceof JSONObject)
	        //    printJsonObject((JSONObject)keyvalue);
	    }
	    
	    printHtml();
	}
	
	
	public void printHtml() {
		Collections.sort(listChartEntries);
		
		String chartHtml = "<html>\r\n" + 
				"  <head>\r\n" + 
				"    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\r\n" + 
				"    <script type=\"text/javascript\">\r\n" + 
				"      google.charts.load('current', {'packages':['corechart']});\r\n" + 
				"      google.charts.setOnLoadCallback(drawChart);\r\n" + 
				"\r\n" + 
				"      function drawChart() {\r\n" + 
				"        var data = google.visualization.arrayToDataTable([\r\n" + 
				"          ['Date', 'Rate'],";
		
		for(ChartEntry chartEntry:listChartEntries)
		{
			Date oDate = (Date) chartEntry.getDate();        
			DateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = oDateFormat.format(oDate);
			
			chartHtml = chartHtml + "[new Date('" + date + "'),  " + chartEntry.getRate() + "],\r\n";
			
			//System.out.println(startDate);
		}
		
		chartHtml = chartHtml + "]);\r\n" + 
				"\r\n" + 
				"        var options = {\r\n" + 
				"          title: 'Historical rates',\r\n" + 
				"          curveType: 'none',\r\n" + 
				"          legend: { position: 'bottom' }\r\n" + 
				"        };\r\n" + 
				"\r\n" + 
				"        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));\r\n" + 
				"\r\n" + 
				"        chart.draw(data, options);\r\n" + 
				"      }\r\n" + 
				"    </script>\r\n" + 
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    <div id=\"curve_chart\" style=\"width: 900px; height: 500px\"></div>\r\n" + 
				"  </body>\r\n" + 
				"</html>";
		
		try {
			PrintWriter out = new PrintWriter("P:\\test2.html");
			out.print(chartHtml);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
