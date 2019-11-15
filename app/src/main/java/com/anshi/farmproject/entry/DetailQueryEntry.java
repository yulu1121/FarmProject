package com.anshi.farmproject.entry;

public class DetailQueryEntry {
    private String time;
    private String company;
    private String number;

    public DetailQueryEntry(String time, String company, String number) {
        this.time = time;
        this.company = company;
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
