package org.example.model;

public class Discipline {

    private int order;
    private double result;
    private int points;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(String result) {
        String[] time = result.split(":");
        if (time.length > 1)
            this.result = Integer.parseInt(time[0]) * 60 + Double.parseDouble(time[1]);
        else
            this.result = Double.parseDouble(time[0]);
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
