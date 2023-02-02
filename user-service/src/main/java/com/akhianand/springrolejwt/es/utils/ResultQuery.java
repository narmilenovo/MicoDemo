package com.akhianand.springrolejwt.es.utils;

public class ResultQuery {

    private Float timeTook;
    private String numberOfResults;
    private String elements;

    public ResultQuery() {
    }

    public ResultQuery(Float timeTook, String numberOfResults, String resultQuery) {
        this.timeTook = timeTook;
        this.numberOfResults = numberOfResults;
        this.elements = resultQuery;
    }

    public Float getTimeTook() {
        return timeTook;
    }

    public void setTimeTook(Float timeTook) {
        this.timeTook = timeTook;
    }

    public String getNumberOfResults() {
        return numberOfResults;
    }

    public void setNumberOfResults(String numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    public String getElements() {
        return elements;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }

}
