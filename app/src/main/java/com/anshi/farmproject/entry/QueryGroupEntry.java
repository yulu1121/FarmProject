package com.anshi.farmproject.entry;

/**
 *
 * Created by asus on 2019/11/22.
 */

public class QueryGroupEntry {
    private String name;
    private String time;
    private int total;

    public QueryGroupEntry(String name, String time, int total) {
        this.name = name;
        this.time = time;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
