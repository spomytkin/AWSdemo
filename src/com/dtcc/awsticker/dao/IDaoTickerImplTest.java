package com.dtcc.awsticker.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.dtcc.awsticker.domain.TickerRow;

public class IDaoTickerImplTest {

	IDaoTicker dao ;

	
	@Test
	public void testGetTickerRow() {
		Integer index = new Integer(0);
		TickerRow tickerRow = dao.getTickerRow(index);
		assertNotNull(tickerRow);
	}

	@Test
	public void testPutTickerRow() {
		Integer index = new Integer(6);
		TickerRow tickerRow = createTestObject(index);
		dao.putTickerRow(index, tickerRow);
		
		tickerRow = dao.getTickerRow(index);
		assertNotNull(tickerRow);
	}
	
	
	private TickerRow createTestObject(Integer index) {
		TickerRow tickerRow = new TickerRow();
		tickerRow.setName("n1");
		tickerRow.setDateTime("20011212180012345");
		tickerRow.setNumber("1");
		tickerRow.setPrice("2");
		tickerRow.setIndex(index);
		return tickerRow;
	}

	@Test
	public void testGetAllTickerRows() {
		List<TickerRow> l = dao.getAllTickerRows();
		assertNotNull(l);
	}

	@Test
	public void testAddTickerRow() {
		Integer index = new Integer(7);
		TickerRow tickerRow = createTestObject(index);
		dao.addTickerRow(tickerRow);
	}
	
	@Test
	public void testDeleteTickerRow() {
		//create
		Integer index = new Integer(13);
		TickerRow tickerRow = createTestObject(index);
		dao.putTickerRow(index, tickerRow);
		 tickerRow = dao.getTickerRow(index);
		assertNotNull(tickerRow);
		//recird in db
		dao.deleteTickerRow(index);
		//wait
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//check deleted
		 tickerRow = dao.getTickerRow(index);
		 assertNull(tickerRow);
		
	}
	

}
