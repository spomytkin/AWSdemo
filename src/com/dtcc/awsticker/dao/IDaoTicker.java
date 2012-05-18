package com.dtcc.awsticker.dao;

import java.util.List;

import com.dtcc.awsticker.domain.TickerRow;

public interface IDaoTicker {

      public abstract TickerRow getTickerRow(Integer index);

      public abstract void putTickerRow(Integer index, TickerRow tickerRow);

      public abstract List<TickerRow> getAllTickerRows();

      public abstract Integer addTickerRow(TickerRow tickerRow);

}
