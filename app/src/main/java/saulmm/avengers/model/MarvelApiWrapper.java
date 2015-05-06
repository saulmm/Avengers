package saulmm.avengers.model;

import saulmm.avengers.model.rest.MarvelDataWrapper;

public class MarvelApiWrapper {

    private String code;
    private String status;

    private MarvelDataWrapper data;

    public String getCode() {

        return code;
    }

    public String getStatus() {

        return status;
    }

    public MarvelDataWrapper getData() {

        return data;
    }
}




