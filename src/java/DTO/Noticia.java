/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "noticia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Noticia.findAll", query = "SELECT n FROM Noticia n")
    , @NamedQuery(name = "Noticia.findByCodNoticia", query = "SELECT n FROM Noticia n WHERE n.codNoticia = :codNoticia")
    , @NamedQuery(name = "Noticia.findByTitulo", query = "SELECT n FROM Noticia n WHERE n.titulo = :titulo")
    , @NamedQuery(name = "Noticia.findBySubtitulo", query = "SELECT n FROM Noticia n WHERE n.subtitulo = :subtitulo")
    , @NamedQuery(name = "Noticia.findByFoto", query = "SELECT n FROM Noticia n WHERE n.foto = :foto")
    , @NamedQuery(name = "Noticia.findByPieFoto", query = "SELECT n FROM Noticia n WHERE n.pieFoto = :pieFoto")
    , @NamedQuery(name = "Noticia.findByLocalidad", query = "SELECT n FROM Noticia n WHERE n.localidad = :localidad")
    , @NamedQuery(name = "Noticia.findByFechaNoticia", query = "SELECT n FROM Noticia n WHERE n.fechaNoticia = :fechaNoticia")
    , @NamedQuery(name = "Noticia.findByNumVisitas", query = "SELECT n FROM Noticia n WHERE n.numVisitas = :numVisitas")
    , @NamedQuery(name = "Noticia.findBySeccion", query = "SELECT n FROM Noticia n WHERE n.seccion = :seccion")
    , @NamedQuery(name = "Noticia.findBySubseccion", query = "SELECT n FROM Noticia n WHERE n.subseccion = :subseccion")
    , @NamedQuery(name = "Noticia.busquedaGenerica", query = "SELECT n FROM Noticia n WHERE n.titulo LIKE :textoBuscar OR n.subtitulo LIKE :textoBuscar")
    , @NamedQuery(name = "Noticia.ordenadasNumVisitas", query = "SELECT n FROM Noticia n ORDER BY n.numVisitas DESC")
    , @NamedQuery(name = "Noticia.ordenadasFecha", query = "SELECT n FROM Noticia n ORDER BY n.fechaNoticia DESC")
    , @NamedQuery(name = "Noticia.ordenadasNumVisitasSeccion", query = "SELECT n FROM Noticia n WHERE n.seccion=:seccion ORDER BY n.numVisitas DESC")
    , @NamedQuery(name = "Noticia.ordenadasFechaSeccion", query = "SELECT n FROM Noticia n WHERE n.seccion=:seccion ORDER BY n.fechaNoticia DESC")
    , @NamedQuery(name = "Noticia.ordenadasNumVisitasSubseccion", query = "SELECT n FROM Noticia n WHERE n.subseccion=:subseccion ORDER BY n.numVisitas DESC")
    , @NamedQuery(name = "Noticia.ordenadasFechaSubseccion", query = "SELECT n FROM Noticia n WHERE n.subseccion=:subseccion ORDER BY n.fechaNoticia DESC")
    , @NamedQuery(name = "Noticia.obtenerNoticiasUsuario", query = "SELECT n FROM Noticia n WHERE n.idUsuario.idUsuario=:idUsuario")
})
public class Noticia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CodNoticia")
    private Integer codNoticia;
    @Basic(optional = false)
    @Column(name = "Titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "Subtitulo")
    private String subtitulo;
    @Basic(optional = false)
    @Lob
    @Column(name = "Cuerpo")
    private String cuerpo;
    @Basic(optional = false)
    @Column(name = "Foto")
    private String foto;
    @Basic(optional = false)
    @Column(name = "PieFoto")
    private String pieFoto;
    @Basic(optional = false)
    @Column(name = "Localidad")
    private String localidad;
    @Basic(optional = false)
    @Column(name = "FechaNoticia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNoticia;
    @Basic(optional = false)
    @Column(name = "NumVisitas")
    private int numVisitas;
    @Basic(optional = false)
    @Column(name = "Seccion")
    private String seccion;
    @Column(name = "Subseccion")
    private String subseccion;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public Noticia() {
    }

    public Noticia(Integer codNoticia) {
        this.codNoticia = codNoticia;
    }

    public Noticia(Integer codNoticia, String titulo, String subtitulo, String cuerpo, String foto, String pieFoto, String localidad, Date fechaNoticia, int numVisitas, String seccion) {
        this.codNoticia = codNoticia;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.cuerpo = cuerpo;
        this.foto = foto;
        this.pieFoto = pieFoto;
        this.localidad = localidad;
        this.fechaNoticia = fechaNoticia;
        this.numVisitas = numVisitas;
        this.seccion = seccion;
    }

    public Integer getCodNoticia() {
        return codNoticia;
    }

    public void setCodNoticia(Integer codNoticia) {
        this.codNoticia = codNoticia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPieFoto() {
        return pieFoto;
    }

    public void setPieFoto(String pieFoto) {
        this.pieFoto = pieFoto;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Date getFechaNoticia() {
        return fechaNoticia;
    }

    public void setFechaNoticia(Date fechaNoticia) {
        this.fechaNoticia = fechaNoticia;
    }

    public int getNumVisitas() {
        return numVisitas;
    }

    public void setNumVisitas(int numVisitas) {
        this.numVisitas = numVisitas;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getSubseccion() {
        return subseccion;
    }

    public void setSubseccion(String subseccion) {
        this.subseccion = subseccion;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codNoticia != null ? codNoticia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Noticia)) {
            return false;
        }
        Noticia other = (Noticia) object;
        if ((this.codNoticia == null && other.codNoticia != null) || (this.codNoticia != null && !this.codNoticia.equals(other.codNoticia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Noticia[ codNoticia=" + codNoticia + " ]";
    }
    
}
