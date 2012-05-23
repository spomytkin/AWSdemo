package com.dtcc.awsticker.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.dtcc.awsticker.domain.TickerRow;

public class DaoTickerSimpleDB implements IDaoTicker {

	private static final String myDomain = "ticker";
	private static AmazonSimpleDB sdb;
	private static final Logger log= Logger.getLogger("DaoTickerSimpleDB");
/*
	static{
		try {
			init();
		} catch (IOException e) {
			log.severe("filed to init"+e);
			e.printStackTrace();
		}
	}
	*/
	public DaoTickerSimpleDB(){
		try {
			init();
		} catch (IOException e) {
			log.severe("filed to init"+e);
			e.printStackTrace();
		}
	}
	
	private static void init() throws IOException {
/*
		Properties p = System.getProperties();
		String env = System.getenv("env");
		env = p.getProperty("env", env == null ? "default_config" : env);
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
				p.getProperty("accessKey", System.getenv("AWS_ACCESS_KEY_ID")),
				p.getProperty("secretKey",
						System.getenv("AWS_SECRET_ACCESS_KEY")));

		sdb = new AmazonSimpleDBClient(awsCredentials,
				new ClientConfiguration());
		*/
		  sdb = new AmazonSimpleDBClient( new PropertiesCredentials(DaoTickerSimpleDB.class.getResourceAsStream("AwsCredentials.properties")));
		 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtcc.awsticker.dao.IDaoTicker#getTickerRow(java.lang.Integer)
	 */
	public TickerRow getTickerRow(Integer index) {
		// return LocalTickerTable.get(index);
		String selectUserExpression = "select * from `" + myDomain
				+ "` where itemName() = '" + index + "'";

		SelectRequest selectRequest = new SelectRequest(selectUserExpression);
		List<Item> items = sdb.select(selectRequest).getItems();
		if (items == null || items.isEmpty())
			return null;
		Item item = items.get(0);
		TickerRow tr = new TickerRow();
		tr.setIndex(index);
		

		ItemToModel(item, tr);

		return tr;

	}

	/**
	 * transfers data from Item to TickerRow
	 */
	private void ItemToModel(Item item, TickerRow tr) {
		for (Attribute attribute : item.getAttributes()) {
			if (attribute.getName().equals("Name")) {
				tr.setName(attribute.getValue() + " ");
			} else if (attribute.getName().equals("Price")) {
				tr.setPrice(attribute.getValue() + " ");
			} else if (attribute.getName().equals("Number")) {
				tr.setNumber(attribute.getValue() + " ");
			} else if (attribute.getName().equals("Time")) {
				tr.setDateTime(attribute.getValue());
			} 

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtcc.awsticker.dao.IDaoTicker#putTickerRow(java.lang.Integer,
	 * com.dtcc.awsticker.domain.TickerRow)
	 */
	public void putTickerRow(Integer index, TickerRow tickerRow) {
		// LocalTickerTable.put(index, tickerRow);
		ArrayList<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();

		attributes.add(new ReplaceableAttribute("Name", tickerRow.getName(),
				false));
		attributes.add(new ReplaceableAttribute("Number",
				tickerRow.getNumber(), false));
		attributes.add(new ReplaceableAttribute("Price", tickerRow.getPrice(),
				false));
		attributes.add(new ReplaceableAttribute("Time", tickerRow.getDateTime(),
				false));
		attributes.add(new ReplaceableAttribute("CreatedTimeMs", ""
				+tickerRow.getLastUpdateTime(), false));

		PutAttributesRequest putAttributesRequest = new PutAttributesRequest(
				myDomain, index + "", attributes);

		sdb.putAttributes(putAttributesRequest);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtcc.awsticker.dao.IDaoTicker#getAllTickerRows()
	 */
	public List<TickerRow> getAllTickerRows() {
		//return LocalTickerTable.getAll();
		
		String selectUserExpression = "select * from `" + myDomain + "`";

		return selectRows(selectUserExpression);
			
	}

	private List<TickerRow> selectRows(	String selectUserExpression) {
		List<TickerRow> rows= new ArrayList<TickerRow>();;

		SelectRequest selectRequest = new SelectRequest(selectUserExpression);
		List<Item> items = sdb.select(selectRequest).getItems();
		if (items == null || items.isEmpty())return rows;
		  for (Item item : sdb.select(selectRequest).getItems()) {
			  
			  Integer index=new Integer(0);
			try{
				index=Integer.decode(item.getName());				  
			}catch(NumberFormatException e){
				log.warning("bad key "+e);
			}
			  
			TickerRow tr = new TickerRow();
			tr.setIndex(index);
						
			  ItemToModel(item, tr);
			  rows.add(tr);
		  }

			  
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dtcc.awsticker.dao.IDaoTicker#addTickerRow(com.dtcc.awsticker.domain
	 * .TickerRow)
	 */
	public Integer addTickerRow(TickerRow tickerRow) {
		Integer index = getNextIndex();
		putTickerRow(index, tickerRow);
		return index;
	}

	private Integer getNextIndex() {
		int nextIndex = 0;
		// TODO not efficient, but...
		List<TickerRow> list = getAllTickerRows();
		// Get the maximum value.
		for (TickerRow tickerRow : list) {
			if (tickerRow.getIndex().intValue() > nextIndex) {
				nextIndex = tickerRow.getIndex().intValue();
			}
		}
		// Add one for the next one.
		nextIndex += 1;
		return new Integer(nextIndex);
	}

	/**
	 * by attribute "Name"
	 */
	public List<TickerRow> getTickerRowsByName(String name) {
		String selectUserExpression = "select * from `" + myDomain + "` where Name= '"+name+"'";

		return selectRows(selectUserExpression);
	}

	/**
	 * by attribute "Price" range
	 */
	public List<TickerRow> getTickerRowsByPriceRange(String priceFrom,
			String priceTo) {
		String selectUserExpression = "select * from `" + myDomain + "` where Price > '"+priceFrom+"' and Price < '"+priceTo+"'";

		return selectRows(selectUserExpression);

	}

	/**
	 * by attribute "Number" range
	 */
	public List<TickerRow> getTickerRowsByNumberRange(String numberFrom,
			String numberTo) {
		String selectUserExpression = "select * from `" + myDomain + "` where Number > '"+numberFrom+"' and Number < '"+numberTo+"'";

		return selectRows(selectUserExpression);
	}

	@Override
	public List<TickerRow> getTickerRowsByCriteria(TickerRow cr, TickerRow crTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTickerRow(Integer index) {
        DeleteAttributesRequest request = new DeleteAttributesRequest();
        request.setDomainName(myDomain);
        request.setItemName(index.toString());		
        sdb.deleteAttributes(request);
	}

}
