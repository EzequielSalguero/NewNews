package DTO;

import DTO.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2019-03-12T10:12:46")
@StaticMetamodel(Noticia.class)
public class Noticia_ { 

    public static volatile SingularAttribute<Noticia, Integer> codNoticia;
    public static volatile SingularAttribute<Noticia, String> seccion;
    public static volatile SingularAttribute<Noticia, String> subtitulo;
    public static volatile SingularAttribute<Noticia, Date> fechaNoticia;
    public static volatile SingularAttribute<Noticia, String> foto;
    public static volatile SingularAttribute<Noticia, String> pieFoto;
    public static volatile SingularAttribute<Noticia, Usuario> idUsuario;
    public static volatile SingularAttribute<Noticia, Integer> numVisitas;
    public static volatile SingularAttribute<Noticia, String> titulo;
    public static volatile SingularAttribute<Noticia, String> subseccion;
    public static volatile SingularAttribute<Noticia, String> localidad;
    public static volatile SingularAttribute<Noticia, String> cuerpo;

}