package org.hopto.tiempoplaya.modelo;

import java.io.Serializable;

/**
 * Created by jpenaab on 22/02/2019.
 */

public class TFotografias implements Serializable {

    private TPlayas TPlayas;
    private TUsuarios TUsuarios;
    private byte[] BFotografia;

    public org.hopto.tiempoplaya.modelo.TPlayas getTPlayas() {
        return TPlayas;
    }

    public void setTPlayas(org.hopto.tiempoplaya.modelo.TPlayas TPlayas) {
        this.TPlayas = TPlayas;
    }

    public org.hopto.tiempoplaya.modelo.TUsuarios getTUsuarios() {
        return TUsuarios;
    }

    public void setTUsuarios(org.hopto.tiempoplaya.modelo.TUsuarios TUsuarios) {
        this.TUsuarios = TUsuarios;
    }

    public byte[] getBFotografia() {
        return BFotografia;
    }

    public void setBFotografia(byte[] BFotografia) {
        this.BFotografia = BFotografia;
    }
}
