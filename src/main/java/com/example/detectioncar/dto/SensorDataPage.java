package com.example.detectioncar.dto;

import java.util.List;

public class SensorDataPage {

    private long total;
    private List<SensorDataRow> list;

    public SensorDataPage() {}

    public SensorDataPage(long total, List<SensorDataRow> list) {
        this.total = total;
        this.list = list;
    }

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }

    public List<SensorDataRow> getList() { return list; }
    public void setList(List<SensorDataRow> list) { this.list = list; }
}
