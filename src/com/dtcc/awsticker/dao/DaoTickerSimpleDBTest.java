package com.dtcc.awsticker.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.dtcc.awsticker.domain.TickerRow;

public class DaoTickerSimpleDBTest {

	@Test
	public void testGetTickerRow() {
		DaoTickerSimpleDB dao = new DaoTickerSimpleDB();
		Integer index = new Integer(0);
		TickerRow tickerRow = dao.getTickerRow(index);
	}

	@Test
	public void testPutTickerRow() {
		DaoTickerSimpleDB dao = new DaoTickerSimpleDB();
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
		DaoTickerSimpleDB dao = new DaoTickerSimpleDB();
		List<TickerRow> l = dao.getAllTickerRows();
	}

	@Test
	public void testAddTickerRow() {
		DaoTickerSimpleDB dao = new DaoTickerSimpleDB();
		Integer index = new Integer(0);
		TickerRow tickerRow = createTestObject(index);
		dao.addTickerRow(tickerRow);
	}

}
