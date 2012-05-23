package com.dtcc.awsticker.dao;

import org.junit.Before;

public class DaoTickerSimpleDBTest extends IDaoTickerImplTest{
	@Before
	public void setup(){
		 dao = new DaoTickerSimpleDB();		
	}

}
