/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author usuario
 */
@Embeddable
public class SubseccionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CodSubseccion")
    private int codSubseccion;
    @Basic(optional = false)
    @Column(name = "CodSeccion")
    private int codSeccion;

    public SubseccionPK() {
    }

    public SubseccionPK(int codSubseccion, int codSeccion) {
        this.codSubseccion = codSubseccion;
        this.codSeccion = codSeccion;
    }

    public int getCodSubseccion() {
        return codSubseccion;
    }

    public void setCodSubseccion(int codSubseccion) {
        this.codSubseccion = codSubseccion;
    }

    public int getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(int codSeccion) {
        this.codSeccion = codSeccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codSubseccion;
        hash += (int) codSeccion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubseccionPK)) {
            return false;
        }
        SubseccionPK other = (SubseccionPK) object;
        if (this.codSubseccion != other.codSubseccion) {
            return false;
        }
        if (this.codSeccion != other.codSeccion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.SubseccionPK[ codSubseccion=" + codSubseccion + ", codSeccion=" + codSeccion + " ]";
    }
    
}
