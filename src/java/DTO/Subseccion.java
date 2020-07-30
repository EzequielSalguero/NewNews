/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "subseccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subseccion.findAll", query = "SELECT s FROM Subseccion s")
    , @NamedQuery(name = "Subseccion.findByNomSubseccion", query = "SELECT s FROM Subseccion s WHERE s.nomSubseccion = :nomSubseccion")
    , @NamedQuery(name = "Subseccion.findByCodSubseccion", query = "SELECT s FROM Subseccion s WHERE s.subseccionPK.codSubseccion = :codSubseccion")
    , @NamedQuery(name = "Subseccion.findByCodSeccion", query = "SELECT s FROM Subseccion s WHERE s.subseccionPK.codSeccion = :codSeccion")})
public class Subseccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SubseccionPK subseccionPK;
    @Basic(optional = false)
    @Column(name = "NomSubseccion")
    private String nomSubseccion;
    @JoinColumn(name = "CodSeccion", referencedColumnName = "CodSeccion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Seccion seccion;

    public Subseccion() {
    }

    public Subseccion(SubseccionPK subseccionPK) {
        this.subseccionPK = subseccionPK;
    }

    public Subseccion(SubseccionPK subseccionPK, String nomSubseccion) {
        this.subseccionPK = subseccionPK;
        this.nomSubseccion = nomSubseccion;
    }

    public Subseccion(int codSubseccion, int codSeccion) {
        this.subseccionPK = new SubseccionPK(codSubseccion, codSeccion);
    }

    public SubseccionPK getSubseccionPK() {
        return subseccionPK;
    }

    public void setSubseccionPK(SubseccionPK subseccionPK) {
        this.subseccionPK = subseccionPK;
    }

    public String getNomSubseccion() {
        return nomSubseccion;
    }

    public void setNomSubseccion(String nomSubseccion) {
        this.nomSubseccion = nomSubseccion;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subseccionPK != null ? subseccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subseccion)) {
            return false;
        }
        Subseccion other = (Subseccion) object;
        if ((this.subseccionPK == null && other.subseccionPK != null) || (this.subseccionPK != null && !this.subseccionPK.equals(other.subseccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Subseccion[ subseccionPK=" + subseccionPK + " ]";
    }
    
}
