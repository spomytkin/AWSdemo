package com.dtcc.awsticker.dao;

import java.util.List;

import com.dtcc.awsticker.domain.TickerRow;

public interface IDaoTicker {

	public abstract List<TickerRow> getTickerRowsByName(String name);

	public abstract List<TickerRow> getTickerRowsByPriceRange(String priceFrom,
			String priceTo);

	public abstract List<TickerRow> getTickerRowsByNumberRange(
			String numberFrom, String numberTo);

	public abstract List<TickerRow> getTickerRowsByCriteria(TickerRow cr,
			TickerRow crTo);

	public abstract TickerRow getTickerRow(Integer index);

	public abstract void putTickerRow(Integer index, TickerRow tickerRow);

	public abstract List<TickerRow> getAllTickerRows();

	public abstract Integer addTickerRow(TickerRow tickerRow);

}
