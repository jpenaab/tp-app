package org.hopto.tiempoplaya.modelo;

import java.security.Timestamp;

public class TDatos {

    private int viento;
    private int oleaje;
    private int nubosidad;
    private int ocupacion;
    private int limpiezaAgua;
    private int limpiezaArena;
    private int banderaMar;
    private int medusas;

    public int getViento() {
        return viento;
    }

    public void setViento(int viento) {
        this.viento = viento;
    }

    public int getOleaje() {
        return oleaje;
    }

    public void setOleaje(int oleaje) {
        this.oleaje = oleaje;
    }

    public int getNubosidad() {
        return nubosidad;
    }

    public void setNubosidad(int nubosidad) {
        this.nubosidad = nubosidad;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(int ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getLimpiezaAgua() {
        return limpiezaAgua;
    }

    public void setLimpiezaAgua(int limpiezaAgua) {
        this.limpiezaAgua = limpiezaAgua;
    }

    public int getLimpiezaArena() {
        return limpiezaArena;
    }

    public void setLimpiezaArena(int limpiezaArena) {
        this.limpiezaArena = limpiezaArena;
    }

    public int getBanderaMar() {
        return banderaMar;
    }

    public void setBanderaMar(int banderaMar) {
        this.banderaMar = banderaMar;
    }

    public int getMedusas() {
        return medusas;
    }

    public void setMedusas(int medusas) {
        this.medusas = medusas;
    }

    @Override
    public String toString() {
        return "TDatosAverage{" + "viento=" + viento + ", oleaje=" + oleaje + ", nubosidad=" + nubosidad + ", ocupacion=" + ocupacion + ", limpiezaAgua=" + limpiezaAgua + ", limpiezaArena=" + limpiezaArena + ", medusas=" + medusas + ", banderaMar=" + banderaMar + '}';
    }
}
