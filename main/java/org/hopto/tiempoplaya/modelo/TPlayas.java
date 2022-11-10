package org.hopto.tiempoplaya.modelo;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * Created by jpenaab on 04/01/2019.
 */

public class TPlayas implements Serializable{

    private static final NumberFormat DF = NumberFormat.getInstance(new Locale("de", "DE"));

    private int id;
    private String nombre;
    private String coordUTMx;
    private String coordUTMy;
    private String coordUTMz;
    private String utmzone;
    private String municipio;
    private int cp;
    private String pais;
    private double distance;
    private int dataRating;
    private String webcam;

    public TPlayas() {
    }

    public TPlayas(String nombre, String coordUTMx, String coordUTMy, String coordUTMz, String utmzone, String municipio, int cp, String pais, double distance, int dataRating) {
        this.nombre = nombre;
        this.coordUTMx = coordUTMx;
        this.coordUTMy = coordUTMy;
        this.coordUTMz = coordUTMz;
        this.utmzone = utmzone;
        this.municipio = municipio;
        this.cp = cp;
        this.pais = pais;
        this.distance = distance;
        this.dataRating = dataRating;
    }

    public TPlayas(int id, String nombre, String coordUTMx, String coordUTMy, String coordUTMz, String utmzone, String municipio, int cp, String pais, double distance, int dataRating) {
        this.id = id;
        this.nombre = nombre;
        this.coordUTMx = coordUTMx;
        this.coordUTMy = coordUTMy;
        this.coordUTMz = coordUTMz;
        this.utmzone = utmzone;
        this.municipio = municipio;
        this.cp = cp;
        this.pais = pais;
        this.distance = distance;
        this.dataRating = dataRating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getUtmzone() {
        return utmzone;
    }

    public void setUtmzone(String utmzone) {
        this.utmzone = utmzone;
    }

    public String getMunicipio() {
        return this.municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getCp() {
        return this.cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getPais() {
        return this.pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getDataRating() {
        return dataRating;
    }

    public void setDataRating(int dataRating) {
        this.dataRating = dataRating;
    }

    public String getWebcam() {
        return webcam;
    }

    public void setWebcam(String webcam) {
        this.webcam = webcam;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "TPlayas{" + "id=" + id + ", nombre=" + nombre + ", coordUTMx=" + coordUTMx + ", coordUTMy=" + coordUTMy + ", coordUTMz=" + coordUTMz + ", municipio=" + municipio + ", cp=" + cp + ", pais=" + pais + '}';
    }

}
