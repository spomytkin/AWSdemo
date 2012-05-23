package com.dtcc.awsticker.dao;

import org.junit.Before;

public class DaoTickerDynamoDBTest extends IDaoTickerImplTest{
	@Before
	public void setup(){
		 dao = new DaoTickerDynamoDB();		
	}

}
