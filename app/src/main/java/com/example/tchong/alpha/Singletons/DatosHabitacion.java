package com.example.tchong.alpha.Singletons;

public class DatosHabitacion {
    private static final DatosHabitacion ourInstance = new DatosHabitacion();

    public static DatosHabitacion getInstance() {
        return ourInstance;
    }

    private DatosHabitacion() {
    }
    String habitacion,modo,tipo,valor,tsensores;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTsensores() {
        return tsensores;
    }

    public void setTsensores(String tsensores) {
        this.tsensores = tsensores;
    }

    public DatosHabitacion(String tsensores) {

        this.tsensores = tsensores;
    }

    public DatosHabitacion(String habitacion, String modo, String tipo, String valor) {
        this.habitacion = habitacion;
        this.modo = modo;
        this.tipo = tipo;

        this.valor = valor;
    }

    public String getHabitacion() {

        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }


    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
