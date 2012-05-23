package com.dtcc.awsticker.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodb.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodb.model.AttributeValue;
import com.amazonaws.services.dynamodb.model.ComparisonOperator;
import com.amazonaws.services.dynamodb.model.Condition;
import com.amazonaws.services.dynamodb.model.DeleteItemRequest;
import com.amazonaws.services.dynamodb.model.GetItemRequest;
import com.amazonaws.services.dynamodb.model.GetItemResult;
import com.amazonaws.services.dynamodb.model.Key;
import com.amazonaws.services.dynamodb.model.PutItemRequest;
import com.amazonaws.services.dynamodb.model.PutItemResult;
import com.amazonaws.services.dynamodb.model.ScanRequest;
import com.amazonaws.services.dynamodb.model.ScanResult;
import com.dtcc.awsticker.domain.TickerRow;

public class DaoTickerDynamoDB implements IDaoTicker {

	private static final String tableName = "ticker";
	private static AmazonDynamoDBClient db;
	private static final Logger log = Logger.getLogger("DaoTickerSimpleDB");

	/*
	 * static{ try { init(); } catch (IOException e) {
	 * log.severe("filed to init"+e); e.printStackTrace(); } }
	 */
	public DaoTickerDynamoDB() {
		try {
			init();
		} catch (IOException e) {
			log.severe("filed to init" + e);
			e.printStackTrace();
		}
	}

	private static void init() throws IOException {
		/*
		  Properties p = System.getProperties(); String env =
		  System.getenv("env"); env = p.getProperty("env", env == null ?
		  "default_config" : env); BasicAWSCredentials awsCredentials = new
		  BasicAWSCredentials( p.getProperty("accessKey",
		  System.getenv("AWS_ACCESS_KEY_ID")), p.getProperty("secretKey",
		  System.getenv("AWS_SECRET_ACCESS_KEY")));
		  
		  sdb = new AmazonSimpleDBClient(awsCredentials, new
		  ClientConfiguration());
		 */

  /*      DTCCProxySettings dtccProxySettings = new DTCCProxySettings(
        		DaoTickerDynamoDB.class.getResourceAsStream("DTCCProxy.properties"));

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProxyHost(dtccProxySettings.getProxyHost());
        clientConfiguration.setProxyPort(dtccProxySettings.getProxyPort());
        clientConfiguration.setProxyDomain(dtccProxySettings.getProxyDomain());
        clientConfiguration.setProxyUsername(dtccProxySettings.getProxyUsername());
        clientConfiguration.setProxyPassword(dtccProxySettings.getProxyPassword());
        clientConfiguration.setProxyWorkstation(dtccProxySettings.getProxyWorkstation());
            clientConfiguration.setProtocol(Protocol.HTTPS);

            
		db = new AmazonDynamoDBClient(new PropertiesCredentials(
				DaoTickerDynamoDB.class
						.getResourceAsStream("AwsCredentials.properties")),clientConfiguration);*/
		db = new AmazonDynamoDBClient(new PropertiesCredentials(
				DaoTickerDynamoDB.class
						.getResourceAsStream("AwsCredentials.properties")));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtcc.awsticker.dao.IDaoTicker#getTickerRow(java.lang.Integer)
	 */
	public TickerRow getTickerRow(Integer index) {
		// return LocalTickerTable.get(index);

		AttributeValue id = new AttributeValue().withN(index + "");
		Key primaryKey = new Key().withHashKeyElement(id);
		GetItemRequest request = new GetItemRequest().withTableName(tableName)
				.withKey(primaryKey);

		GetItemResult result = db.getItem(request);
		if(result.getItem()==null)return null;
		TickerRow tr = new TickerRow();
		tr.setIndex(index);
		ItemToModel(result.getItem(), tr);

		return tr;

	}

	/**
	 * transfers data from Item to TickerRow
	 */
	private void ItemToModel(Map<String, AttributeValue> item, TickerRow tr) {
		for (String attribute : item.keySet()) {
			if (attribute.equals("Name")) {
				tr.setName(item.get(attribute).getS());
			} else if (attribute.equals("Price")) {
				tr.setPrice(item.get(attribute).getS());
			} else if (attribute.equals("Number")) {
				tr.setNumber(item.get(attribute).getS());
			} else if (attribute.equals("Time")) {
				tr.setDateTime(item.get(attribute).getS());
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

		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();

		item.put("Index", new AttributeValue().withN("" + tickerRow.getIndex()));
		//item.put("Date", new AttributeValue(tickerRow.getDate()));
		item.put("Name", new AttributeValue(tickerRow.getName()));
		item.put("Number", new AttributeValue(tickerRow.getNumber()));
		item.put("Price", new AttributeValue(tickerRow.getPrice()));
		item.put("Time", new AttributeValue(tickerRow.getDateTime()));
		item.put("CreatedTimeMs",
				new AttributeValue("" + System.currentTimeMillis()));

		PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
		PutItemResult putItemResult = db.putItem(putItemRequest);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtcc.awsticker.dao.IDaoTicker#getAllTickerRows()
	 */
	public List<TickerRow> getAllTickerRows() {
		// return LocalTickerTable.getAll();
		List<TickerRow> rows = new ArrayList<TickerRow>();

		ScanRequest scanRequest = new ScanRequest(tableName);
		ScanResult scanResult = db.scan(scanRequest);
		List<Map<String, AttributeValue>> items = scanResult.getItems();
		if (items == null || items.isEmpty())
			return rows;
		for (Map<String, AttributeValue> item : items) {

			Integer index = new Integer(0);
			try {
				index = Integer.decode(item.get("Index").getN());
			} catch (NumberFormatException e) {
				log.warning("bad key " + e);
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

	
	public List<TickerRow> getTickerRowsByName(String name) {
		Map<String, Condition> scanFilter= new HashMap<String, Condition>();
		Condition condition = new Condition().withComparisonOperator(
				ComparisonOperator.BEGINS_WITH.toString()).withAttributeValueList(
				new AttributeValue().withS(name));
				scanFilter.put("Name", condition);
		return scan(scanFilter);
	}

	public List<TickerRow> getTickerRowsByPriceRange(String priceFrom,
			String priceTo) {
		Map<String, Condition> scanFilter= new HashMap<String, Condition>();
		Condition condition = new Condition().withComparisonOperator(
				ComparisonOperator.BETWEEN.toString()).withAttributeValueList(
				new AttributeValue().withS(priceFrom),new AttributeValue().withS(priceTo));
				scanFilter.put("Price", condition);
		return scan(scanFilter);
	}

	public List<TickerRow> getTickerRowsByNumberRange(String numberFrom,
			String numberTo) {
		Map<String, Condition> scanFilter= new HashMap<String, Condition>();
		Condition condition = new Condition().withComparisonOperator(
				ComparisonOperator.BETWEEN.toString()).withAttributeValueList(
				new AttributeValue().withS(numberFrom),new AttributeValue().withS(numberTo));
				scanFilter.put("Number", condition);
		return scan(scanFilter);
	}



	private List<TickerRow> scan(Map<String, Condition> scanFilter) {
		ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
		ScanResult scanResult = db.scan(scanRequest);
		
		List<Map<String, AttributeValue>> items = scanResult.getItems();
		List<TickerRow> rows = new ArrayList<TickerRow>();
		if (items == null || items.isEmpty())
			return rows;
		for (Map<String, AttributeValue> item : items) {

			Integer index = new Integer(0);
			try {
				index = Integer.decode(item.get("Index").getN());
			} catch (NumberFormatException e) {
				log.warning("bad key " + e);
			}

			TickerRow tr = new TickerRow();
			tr.setIndex(index);
			ItemToModel(item, tr);
			rows.add(tr);
		}

		return rows;
	}


	public List<TickerRow> getTickerRowsByCriteria(TickerRow cr, TickerRow crTo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteTickerRow(Integer index) {

		AttributeValue val = new AttributeValue().withN(index.toString());

		Key primaryKey = new Key().withHashKeyElement(val);

		DeleteItemRequest deleteItemRequest = new DeleteItemRequest()
				.withTableName(tableName).withKey(primaryKey);

		db.deleteItem(deleteItemRequest);

	}

}
