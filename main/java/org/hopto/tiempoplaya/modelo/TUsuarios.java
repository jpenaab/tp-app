package org.hopto.tiempoplaya.modelo;

import java.io.Serializable;

/**
 * Created by jpenaab on 18/02/2019.
 */

public class TUsuarios implements Serializable {

    private Integer id;
    private String nombre;
    private String apellidos;
    private String email;
    private String usuario;
    private String tk;

    public TUsuarios() {
    }

    public TUsuarios(int id, String nombre, String apellidos, String email, String usuario, String tk) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.usuario = usuario;
        this.tk = tk;
    }

    public TUsuarios(String nombre, String apellidos, String email, String usuario, String tk) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.usuario = usuario;
        this.tk = tk;
    }

    public TUsuarios(String nombre, String apellidos, String email, String usuario) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    @Override
    public String toString() {
        return "TUsuarios{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", usuario=" + usuario + ", email=" + email + ", tk=" + tk + '}';
    }
}
