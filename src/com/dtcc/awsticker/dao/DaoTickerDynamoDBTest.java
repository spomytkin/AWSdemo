package com.dtcc.awsticker.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.dtcc.awsticker.domain.TickerRow;

public class DaoTickerDynamoDBTest {

	@Test
	public void testGetTickerRow() {
		DaoTickerDynamoDB dao = new DaoTickerDynamoDB();
		Integer index = new Integer(0);
		TickerRow tickerRow = dao.getTickerRow(index);
	}

	@Test
	public void testPutTickerRow() {
		DaoTickerDynamoDB dao = new DaoTickerDynamoDB();
		Integer index = new Integer(0);
		TickerRow tickerRow = createTestObject(index);
		dao.putTickerRow(index, tickerRow);
	}

	private TickerRow createTestObject(Integer index) {
		TickerRow tickerRow = new TickerRow(index);
		tickerRow.setName("n1");
		tickerRow.setDate("d1");
		tickerRow.setNumber("num1");
		tickerRow.setPrice("p1");
		tickerRow.setTime("t1");
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
