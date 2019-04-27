import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JButton;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;



import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class CurrencyMainFrame extends JFrame {
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(System.getProperty("java.runtime.version"));
				try {
					CurrencyMainFrame currencyMainFrame = new CurrencyMainFrame();
					currencyMainFrame.addWindowListener(new WindowAdapter() {
			            @Override
			            public void windowClosing(WindowEvent e) {
			                currencyMainFrame.dispose();
			                System.exit(0);
			            }
			        });
					currencyMainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		});
	}
	
	private JLabel labelCurrency;
	private JTextField textFieldCurrencyToBeConverted;
	private JComboBox<String> listCurrency;
	private JLabel labelCurrency2;
	private JLabel labelConvertedCurrency;
	private JComboBox<String> listCurrency2;
	private JButton btnCalculate;
	private CurrenncyConversionURL currenncyConversionURL;
	private JTable table;
	private CurrencyDatabase entries = new CurrencyDatabase();
	private DatabaseHelper databaseHelper = new DatabaseHelper();
	
	private Double amoutOfMoney;
	private JButton btnSaveToDatabase;
	private JButton btnDeleteAllEntries;
	private JTextField commentTextField;
	private final JLabel lblComment = new JLabel("Comment");
	private JComboBox<String> listCurrencyHistory;
	private JTabbedPane tabbedPane_1;
	private JLabel lblCurrency_1;
	private JComboBox<String> listCurrencyHistory2;
	private JCheckBox chckbxRepeat;
	private JLabel lblSortBy;
	private JComboBox<String> sortBy;
	private Browser browser;
	
	public CurrencyMainFrame() {		
		currenncyConversionURL= new CurrenncyConversionURL("https://api.exchangeratesapi.io/latest?base=", "PLN");		
		JPanel panel;
		panel = new JPanel();
		setContentPane(panel);		
		panel.setLayout(new MigLayout("", "[163.00,grow][430.00,grow][197.00,grow][57.00,grow]", "[][][][][][][][][][][][26.00][20px:n][600.00,grow]"));
		
		JLabel lblExchangeCalculator = new JLabel("Exchange calculator");
		lblExchangeCalculator.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblExchangeCalculator, "cell 0 0 3 1,alignx center");
		labelCurrency = new JLabel("Currency to be converted");
		panel.add(labelCurrency, "cell 0 1,alignx left");		
		textFieldCurrencyToBeConverted = new JTextField();
		textFieldCurrencyToBeConverted.setText("0.0");
		panel.add(textFieldCurrencyToBeConverted, "cell 1 1,growx");
		textFieldCurrencyToBeConverted.setColumns(10);	
		listCurrency = new JComboBox(currenncyConversionURL.getArrayListWithCurrencyNames().toArray());
		panel.add(listCurrency, "cell 2 1");	
		labelCurrency2 = new JLabel("Converted currency");
		panel.add(labelCurrency2, "cell 0 2,alignx left");
		labelConvertedCurrency = new JLabel("0.0");
		panel.add(labelConvertedCurrency, "cell 1 2,growx");
		listCurrency2 = new JComboBox(currenncyConversionURL.getArrayListWithCurrencyNames().toArray());
		panel.add(listCurrency2, "cell 2 2");
		panel.add(lblComment, "cell 0 3");
		
		commentTextField = new JTextField();
		panel.add(commentTextField, "cell 1 3,growx");
		commentTextField.setColumns(10);
		
		lblSortBy = new JLabel("Sort by");
		panel.add(lblSortBy, "cell 0 4,alignx left");
		
		String[] sortByStrings = {"Date ascending", "Date descending", "Amount ascending", "Amount descending",
				"Currency ascending", "Currency descending", "Converted currency ascending", "Converted currency descending"};
		sortBy = new JComboBox(sortByStrings);
		panel.add(sortBy, "cell 1 4");
		
		chckbxRepeat = new JCheckBox("Refresh", false);
		panel.add(chckbxRepeat, "flowx,cell 1 5");
		
		btnDeleteAllEntries = new JButton("Delete all entries");
		panel.add(btnDeleteAllEntries, "cell 1 5,alignx right");
		
		btnSaveToDatabase = new JButton("Save to database");
		panel.add(btnSaveToDatabase, "cell 1 5,alignx right");
		btnCalculate = new JButton("Calculate");
		panel.add(btnCalculate, "cell 2 5,growx");
		
		UtilDateModel model = new UtilDateModel();
		model.setDate(2019, 1, 1);
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		
		lblCurrency_1 = new JLabel("Currency");
		panel.add(lblCurrency_1, "cell 0 8,alignx left");
		
		listCurrencyHistory2 = new JComboBox(currenncyConversionURL.getArrayListWithCurrencyNames().toArray());
		panel.add(listCurrencyHistory2, "cell 1 8");
		JDatePickerImpl datePickerStart = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		panel.add(datePickerStart, "cell 1 9");
		
		
		UtilDateModel model2 = new UtilDateModel();
		model2.setDate(2019, 1, 1);
		model2.setSelected(true);
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		panel.add(datePickerEnd, "cell 1 10");
		

		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnShow, "cell 2 11,growx");
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane_1, "cell 0 13 4 1,grow");
		
		
		JLabel lblHistoricalRates = new JLabel("Historical rates");
		lblHistoricalRates.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblHistoricalRates, "cell 0 6 3 1,alignx center");
		
		JLabel lblCurrency = new JLabel("Base currency");
		panel.add(lblCurrency, "cell 0 7");
		
		listCurrencyHistory = new JComboBox(currenncyConversionURL.getArrayListWithCurrencyNames().toArray());
		panel.add(listCurrencyHistory, "cell 1 7");
		
		JLabel lblStartDate = new JLabel("Start date");
		panel.add(lblStartDate, "cell 0 9");
		

		
		JLabel lblEndDate = new JLabel("End date");
		panel.add(lblEndDate, "cell 0 10");
		

		BrowserContext context = new BrowserContext(
		new BrowserContextParams("P:\\test3"));
		browser = new Browser(context);
		
		
		table = new JTable();
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(table);
		tabbedPane_1.addTab("Database", null, scrollPane, null);
		BrowserView browserView = new BrowserView(browser);
		tabbedPane_1.addTab("Chart", null, browserView, null);
		
			
		
		//Load entries from db
		databaseHelper.getDataFromDatabase(entries, 0);
		
		
		//Button - Calculate
		btnCalculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculate();				
			}
		});
		
		//Button - Save to database
		btnSaveToDatabase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				databaseHelper.putDataToDatabase(entries);
			}
		});
		
		//Button - delete
		btnDeleteAllEntries.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				databaseHelper.deleteAllEntries(entries);
			}
		});
		
		//Button - show
		btnShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date oDate = (Date) datePickerStart.getModel().getValue();        
				DateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String startDate = oDateFormat.format(oDate);
				
				Date endoDate = (Date) datePickerEnd.getModel().getValue();
				String endDate = oDateFormat.format(endoDate);
				
				//System.out.println(startDate);
				//System.out.println(endDate);
				
				if(endoDate.compareTo(oDate) > 0)
				{
					new CurrencyHistoricalRatesURL("https://api.exchangeratesapi.io/history?base=", 
					listCurrencyHistory.getSelectedItem().toString(), startDate, endDate, listCurrencyHistory2.getSelectedItem().toString());
					browser.loadURL("P:\\test2.html");
				}else {
					JOptionPane.showMessageDialog(null, "End date must be after start date");
				}

			}
		});
		
		
		
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

		pack();
		initDataBindings();
		
		ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("Calculate");
                calculate();
            }
        };
        Timer timer = new Timer(10000 ,taskPerformer);
        timer.setRepeats(true);
        
      //Checkbox
      	chckbxRepeat.addActionListener(new ActionListener() {
      			
      		@Override
      		public void actionPerformed(ActionEvent arg0) {
      			if(chckbxRepeat.isSelected()) {
      				timer.start();
      				System.out.println("Start");
      			}
      			else {
      				timer.stop();
      				System.out.println("Stop");
      			}
      				
      		}
      	});
      		
      //Sort by
      sortBy.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//System.out.println(sortBy.getSelectedIndex());
			entries.eraseEntries();
			databaseHelper.getDataFromDatabase(entries, sortBy.getSelectedIndex());
		}
	});

	}
	
	
	
	/**
	 * Put all current data to container 
	 * @return entry @see CurrencyConversionDataEntry 
	 */
	private CurrencyConversionDataEntry getNewCurrencyConversionData() {
		CurrencyConversionDataEntry newEntry = new CurrencyConversionDataEntry();
		
		newEntry.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
		newEntry.setAmoutntOfMoney1(textFieldCurrencyToBeConverted.getText());
		newEntry.setCurrency1(listCurrency.getSelectedItem().toString());		
		newEntry.setAmoutntOfMoney2(String.format(Locale.US, "%.2f", amoutOfMoney));
		newEntry.setCurrency2(listCurrency2.getSelectedItem().toString());
		newEntry.setComment(commentTextField.getText());
		
		return newEntry;
	}
	
	
	/**
	 * Initialization of all bindings
	 */
	protected void initDataBindings() {
		BeanProperty<CurrencyDatabase, List<CurrencyConversionDataEntry>> currencyDatabaseBeanProperty = BeanProperty.create("entries");
		JTableBinding<CurrencyConversionDataEntry, CurrencyDatabase, JTable> jTableBinding = SwingBindings.createJTableBinding(UpdateStrategy.READ, entries, currencyDatabaseBeanProperty, table);
		//
		BeanProperty<CurrencyConversionDataEntry, LocalDateTime> currencyConversionDataEntryBeanPropert = BeanProperty.create("timestamp");
		jTableBinding.addColumnBinding(currencyConversionDataEntryBeanPropert).setColumnName("Time");
		//
		BeanProperty<CurrencyConversionDataEntry, String> currencyConversionDataEntryBeanProperty = BeanProperty.create("amoutntOfMoney1");
		jTableBinding.addColumnBinding(currencyConversionDataEntryBeanProperty).setColumnName("Amount");
		//
		BeanProperty<CurrencyConversionDataEntry, String> currencyConversionDataEntryBeanProperty_1 = BeanProperty.create("currency1");
		jTableBinding.addColumnBinding(currencyConversionDataEntryBeanProperty_1).setColumnName("Currency");
		//
		BeanProperty<CurrencyConversionDataEntry, String> currencyConversionDataEntryBeanProperty_2 = BeanProperty.create("amoutntOfMoney2");
		jTableBinding.addColumnBinding(currencyConversionDataEntryBeanProperty_2).setColumnName("Converted amount");
		//
		BeanProperty<CurrencyConversionDataEntry, String> currencyConversionDataEntryBeanProperty_3 = BeanProperty.create("currency2");
		jTableBinding.addColumnBinding(currencyConversionDataEntryBeanProperty_3).setColumnName("Converted currency");
		//
		BeanProperty<CurrencyConversionDataEntry, String> currencyConversionDataEntryBeanProperty_4 = BeanProperty.create("comment");
		jTableBinding.addColumnBinding(currencyConversionDataEntryBeanProperty_4).setColumnName("Comment");
		//
		jTableBinding.bind();
	}
	
	private void calculate() {
		//System.out.println(Double.toString(ratio));				
		try {
			amoutOfMoney = Double.parseDouble(textFieldCurrencyToBeConverted.getText());
			
		} catch (NumberFormatException e1) {
			System.out.println("Brak nr");
			amoutOfMoney = (double) 0;
			JOptionPane.showMessageDialog(null, "Enter number");
			
			return;
		}
		
		CurrenncyConversionURL currenncyConversionURLtmp = 
				new CurrenncyConversionURL("https://api.exchangeratesapi.io/latest?base=", 
				listCurrency.getSelectedItem().toString());				
		double ratio = currenncyConversionURLtmp.getRate(listCurrency2.getSelectedItem().toString());
		
		amoutOfMoney = amoutOfMoney*ratio;
		
		labelConvertedCurrency.setText(Double.toString(amoutOfMoney));

		CurrencyConversionDataEntry entry = getNewCurrencyConversionData();
		entries.addEntry(entry);
	}
	
	public void dispose() {
		browser.dispose();
		databaseHelper.close();
	}
}
