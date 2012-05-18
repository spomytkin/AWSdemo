package com.dtcc.awsticker.domain;

import java.lang.reflect.Field;

public class TickerRow implements Comparable<TickerRow> {

                private Integer index;
                private long lastUpdateTime;
                private String name;
                private String price;
                private String number;
                private String time;
                private String date;
                
                public TickerRow(Integer thisRowNumber) {
                                super();
                                this.index = thisRowNumber;
                                this.name = "Item " + this.index;
                                updateTimestamp();
                }

                // Copy constructor
                public TickerRow(TickerRow tickerRow) {
                                this.index = tickerRow.index;
                                this.lastUpdateTime = tickerRow.lastUpdateTime;
                                this.name = tickerRow.name;
                                this.price = tickerRow.price;
                                this.number = tickerRow.number;
                                this.time = tickerRow.time;
                                this.date = tickerRow.date;
                }

                private void updateTimestamp() {
                                this.setLastUpdateTime(System.currentTimeMillis());
                }

                public Integer getIndex() {
                                return this.index;
                }

                public void setIndex(Integer index) {
                                updateTimestamp();
                                this.index = index;
                }

                public String getName() {
                                return this.name;
                }

                public void setName(String name) {
                                updateTimestamp();
                                this.name = name;
                }

                public String getPrice() {
                                return this.price;
                }

                public void setPrice(String price) {
                                updateTimestamp();
                                this.price = price;
                }

                public String getNumber() {
                                return this.number;
                }

                public void setNumber(String number) {
                                updateTimestamp();
                                this.number = number;
                }

                public String getTime() {
                                return this.time;
                }

                public void setTime(String time) {
                                updateTimestamp();
                                this.time = time;
                }

                public String getDate() {
                                return this.date;
                }

                public void setDate(String date) {
                                updateTimestamp();
                                this.date = date;
                }

   

                public void setLastUpdateTime(long lastUpdateTime) {
                                this.lastUpdateTime = lastUpdateTime;
                }

                public long getLastUpdateTime() {
                                return this.lastUpdateTime;
                }
                
                @Override
                public String toString() {
                                Class<? extends Object> clazz = this.getClass();
                                Field[] fields = clazz.getDeclaredFields();

                                StringBuilder buf = new StringBuilder(clazz.getName());
                                buf.append("[");
                                try {
                                                for (int i = 0; i < fields.length; i++) {
                                                                Field field = fields[i];
                                                                buf.append(field.getName());
                                                                buf.append("==");
                                                                buf.append(field.get(this));
                                                                if (i < fields.length - 1) {
                                                                                buf.append(",");
                                                                } else {
                                                                                buf.append("]");
                                                                }
                                                }
                                } catch (Exception e) {
                                                // ignore error
                                }
                                return buf.toString();
                }

                public int compareTo(TickerRow o) {
                                int result = 0;
                                if ( o != null ) {
                                                if ( this.index != null ) {
                                                                result = this.index.compareTo(o.index);
                                                } else {
                                                                if ( o.index == null ) {
                                                                                result = 0;
                                                                } else {
                                                                                result = -1;
                                                                }
                                                }
                                } else {
                                                result = 1;
                                }
                                return result;
                }
                
                
}
