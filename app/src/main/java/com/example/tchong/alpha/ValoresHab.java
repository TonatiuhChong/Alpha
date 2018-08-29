package com.example.tchong.alpha;

public class ValoresHab {
    private static final ValoresHab ourInstance = new ValoresHab();

    public static ValoresHab getInstance() {
        return ourInstance;
    }

    private ValoresHab() {
    }

    String Thab,Tsensor;
    Boolean logicos;
    Integer analogicos;

    public String getThab() {
        return Thab;
    }

    public void setThab(String thab) {
        Thab = thab;
    }

    public String getTsensor() {
        return Tsensor;
    }

    public void setTsensor(String tsensor) {
        Tsensor = tsensor;
    }

    public Boolean getLogicos() {
        return logicos;
    }

    public void setLogicos(Boolean logicos) {
        this.logicos = logicos;
    }

    public Integer getAnalogicos() {
        return analogicos;
    }

    public void setAnalogicos(Integer analogicos) {
        this.analogicos = analogicos;
    }
}
