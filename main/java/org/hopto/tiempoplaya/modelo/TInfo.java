package org.hopto.tiempoplaya.modelo;

import java.security.Timestamp;

public class TInfo {

    private int id;
    private String nombre;
    private String coordUTMx;
    private String coordUTMy;
    private String coordUTMz;
    private int viento;
    private int oleaje;
    private int nubosidad;
    private int ocupacion;
    private int limpiezaAgua;
    private int limpiezaArena;
    private int medusas;
    private int banderaMar;
    private String timestamp;

    public TInfo() {
    }

    public TInfo(String nombre, String coordUTMx, String coordUTMy, String coordUTMz, int viento, int oleaje, int nubosidad, int ocupacion, int limpiezaAgua, int limpiezaArena, int banderaMar, int medusas, String timestamp) {
        this.nombre = nombre;
        this.coordUTMx = coordUTMx;
        this.coordUTMy = coordUTMy;
        this.coordUTMz = coordUTMz;
        this.viento = viento;
        this.oleaje = oleaje;
        this.nubosidad = nubosidad;
        this.ocupacion = ocupacion;
        this.limpiezaAgua = limpiezaAgua;
        this.limpiezaArena = limpiezaArena;
        this.banderaMar = banderaMar;
        this.medusas = medusas;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoordUTMx() {
        return coordUTMx;
    }

    public void setCoordUTMx(String coordUTMx) {
        this.coordUTMx = coordUTMx;
    }

    public String getCoordUTMy() {
        return coordUTMy;
    }

    public void setCoordUTMy(String coordUTMy) {
        this.coordUTMy = coordUTMy;
    }

    public String getCoordUTMz() {
        return coordUTMz;
    }

    public void setCoordUTMz(String coordUTMz) {
        this.coordUTMz = coordUTMz;
    }

    public int getViento() {
        return this.viento;
    }

    public void setViento(int viento) {
        this.viento = viento;
    }

    public int getOleaje() {
        return this.oleaje;
    }

    public void setOleaje(int oleaje) {
        this.oleaje = oleaje;
    }

    public int getNubosidad() {
        return this.nubosidad;
    }

    public void setNubosidad(int nubosidad) {
        this.nubosidad = nubosidad;
    }

    public int getOcupacion() {
        return this.ocupacion;
    }

    public void setOcupacion(int ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getLimpiezaAgua() {
        return this.limpiezaAgua;
    }

    public void setLimpiezaAgua(int limpiezaAgua) {
        this.limpiezaAgua = limpiezaAgua;
    }

    public int getLimpiezaArena() {
        return this.limpiezaArena;
    }

    public void setLimpiezaArena(int limpiezaArena) {
        this.limpiezaArena = limpiezaArena;
    }

    public int getBanderaMar() {
        return this.banderaMar;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDataSended(){
        return "{ viento=" + viento + ", oleaje=" + oleaje + ", nubosidad=" + nubosidad + ", ocupacion=" + ocupacion + ", limpiezaAgua=" + limpiezaAgua + ", limpiezaArena=" + limpiezaArena + ", medusas=" + medusas + ", banderaMar=" + banderaMar + '}';
    }

    @Override
    public String toString() {
        return "TDatosReport{" + "id=" + id + ", nombre=" + nombre + ", coordUTMx=" + coordUTMx + ", coordUTMy=" + coordUTMy + ", coordUTMz=" + coordUTMz + ", viento=" + viento + ", oleaje=" + oleaje + ", nubosidad=" + nubosidad + ", ocupacion=" + ocupacion + ", limpiezaAgua=" + limpiezaAgua + ", limpiezaArena=" + limpiezaArena + ", medusas=" + medusas + ", banderaMar=" + banderaMar + ", timestamp=" + timestamp + '}';
    }

}
