package com.example.tchong.alpha.Adapters;

public class ListitemAcciones {
    private String desc;
    private int Foto;

    public ListitemAcciones(String desc, int foto) {
        this.desc = desc;
        Foto = foto;
    }

    public String getDesc() {
        return desc;
    }

    public int getFoto() {
        return Foto;
    }
}
