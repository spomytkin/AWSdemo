package com.dtcc.awsticker.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.dtcc.awsticker.domain.TickerRow;

public class DaoTickerDynamoDBTest {
	
	@Test
	public void testGetTickerRowsByName() {
		DaoTickerDynamoDB dao = new DaoTickerDynamoDB();
		List<TickerRow> tickerRows = dao.getTickerRowsByName("n1");
		assertNotNull(tickerRows);
	}

	@Test
	public void testGetTickerRowsByPriceRange() {
		DaoTickerDynamoDB dao = new DaoTickerDynamoDB();
		List<TickerRow> tickerRows = dao.getTickerRowsByPriceRange("0","99");
		assertNotNull(tickerRows);
	}

	@Test
	public void testGetTickerRowsByNumberRange() {
		DaoTickerDynamoDB dao = new DaoTickerDynamoDB();
		List<TickerRow> tickerRows = dao.getTickerRowsByNumberRange("0","99");
		assertNotNull(tickerRows);
	}

	@Test
	public void testGetTickerRow() {
		DaoTickerDynamoDB dao = new DaoTickerDynamoDB();
		Integer index = new Integer(0);
		TickerRow tickerRow = dao.getTickerRow(index);
	}

	@Test
	public void testPutTickerRow() {
		DaoTickerDynamoDB dao = new DaoTickerDynamoDB();
		Integer index = new Integer(3);
		TickerRow tickerRow = createTestObject(index);
		dao.putTickerRow(index, tickerRow);
	}

	private TickerRow createTestObject(Integer index) {
		TickerRow tickerRow = new TickerRow(index);
		tickerRow.setName("n1");
		tickerRow.setDate("20011212");
		tickerRow.setNumber("1");
		tickerRow.setPrice("2");
		tickerRow.setTime("20011212180012345");
		return tickerRow;
	}

	@Test
	public void testGetAllTickerRows() {
		DaoTickerDynamoDB dao = new DaoTickerDynamoDB();
		List<TickerRow> l = dao.getAllTickerRows();
	}

	@Test
	public void testAddTickerRow() {
		DaoTickerDynamoDB dao = new DaoTickerDynamoDB();
		Integer index = new Integer(0);
		TickerRow tickerRow = createTestObject(index);
		dao.addTickerRow(tickerRow);
	}

}
