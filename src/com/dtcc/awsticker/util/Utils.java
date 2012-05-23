package com.dtcc.awsticker.util;

import java.util.Random;

import com.dtcc.awsticker.domain.TickerRow;

public class Utils {

	public static void log(String message) {
		System.out.println(message);
	}

	public static boolean oneOutOfN(int n) {
		boolean b = false;
		int r = randomInt(1, n);
		if ( r == n ) {
			b = true;
		}
		return b;
	}
	
//	public static void makeData(long recordsPerDay, long totalRecords, long startRecord) {
//	
//		for ( int i = 0; i < totalRecords; i++ ) {
//			makeDayRecords(recordsPerDay, startRecord)
//		}
//		
//		
//	}
	
	
	public static TickerRow makeTickerRow(Integer thisRowNumber) {
		TickerRow tickerRow = new TickerRow();
		tickerRow.setIndex(thisRowNumber);
		tickerRow.setName("Item " + thisRowNumber);
		tickerRow.setPrice(Utils.makePriceValue());
		tickerRow.setNumber(Utils.makeNumberValue());
		tickerRow.setDateTime(makeDateTimeValue());
		return tickerRow;
	}

	public static int randomInt(int min, int max) {
		Random random = new Random(); 
		int randomNum = random.nextInt(max - min + 1) + min; 
		return randomNum;
	}

	public static String makeDateTimeValue() {
		return "20120" + Utils.randomInt(1, 9) + Utils.randomInt(10, 30) + Utils.randomInt(11, 23) + Utils.randomInt(11, 59) + Utils.randomInt(11, 59) + Utils.randomInt(111, 999);
	}
	
	public static String makeDateValue() {
		return ("" + Utils.randomInt(1, 5) + "-" + Utils.randomInt(1, 28) + "-2012");
	}

	public static String makeTimeValue() {
		return ("" + Utils.randomInt(1, 24) + ":" + Utils.randomInt(10, 59) + ":" + Utils.randomInt(10, 59));
	}

	public static String makeNumberValue() {
		return ("" + Utils.randomInt(1000, 20000));
	}

	public static String makePriceValue() {
		return ("" + Utils.randomInt(100, 10000) + "." + Utils.randomInt(0, 99));
	}

}
