<%-- 
    Document   : Redactor
    Created on : 07-feb-2019, 14:18:12
    Author     : Ezequiel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2.0, minimum-scale=1.0,  user-scalable=yes">
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="CSS/reset.css">
        <link rel="stylesheet" type="text/css" href="CSS/redactor.css">
        <link rel="stylesheet" type="text/css" href="CSS/footer.css">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

        <!-- CSS de Bootstrap -->
        <link href="cssbs/bootstrap.min.css" rel="stylesheet" media="screen">
        <title>Redactor</title>
        <!-- Make sure the path to CKEditor is correct. -->
        <script src="ckeditor/ckeditor.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="JS/jquery.min.js"><\/script>')</script>
        <script>

            $(function () {
                CKEDITOR.replace('cuerpo');

                $.ajax({
                    type: 'GET',
                    data: 'seccion=seccion',
                    dataType: 'JSON',
                    url: 'BuscaSeccionSubseccion',
                    success: cargarSecciones, //recibe (result,status,xhr)
                    error: function (xhr, status, error) {//(xhr,status,error)
                        alert("Error en la peticion->" + xhr.statusText + ':' + xhr.status);
                    }
                });

                function cargarSecciones(secciones, status, xhr) {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        //console.log(secciones);
                        for (sec in secciones) {
                            seccion.add(new Option(secciones[sec], sec));
                        }
                    }
                }

                $("#seccion").on("change", obtenerSubsecciones);

                function obtenerSubsecciones() {
                    subseccion.options.length = 0;
                    var seccionSeleccionada = seccion[seccion.selectedIndex].value;
                    $.ajax({
                        type: 'GET',
                        data: 'subseccion=' + seccionSeleccionada,
                        dataType: 'JSON',
                        url: 'BuscaSeccionSubseccion',
                        success: cargarSubsecciones, //recibe (result,status,xhr)
                        error: function (xhr, status, error) {//(xhr,status,error)
                            alert("Error en la peticion->" + xhr.statusText + ':' + xhr.status);
                        }
                    });
                }

                function cargarSubsecciones(subsecciones, status, xhr) {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        //console.log(subsecciones);
                        for (sub in subsecciones) {
                            subseccion.add(new Option(subsecciones[sub], sub));
                        }
                    }
                }
            });

        </script>
        <script src="jsbs/jquery-3.3.1.min.js"></script>
        <script src="jsbs/popper.min.js"></script>
        <script src="jsbs/bootstrap.min.js"></script>
    </head>
    <body>
        <%--<c:if test="${!empty param.crear}">
            <jsp:useBean id="noticia" scope="request" class="DTO.Noticia">
                <jsp:setProperty name="noticia" property="*"/>
            </jsp:useBean>
            <jsp:include page="NuevaNoticia"/>
        </c:if>--%>
        <% if ( request.getContentType()!=null)  {%>
            <jsp:include page="NuevaNoticia"/>
        <%}%>

        <header class="row">
            <div class="col text-center">
                <a href="index.jsp"><img  src="IMAGENES/logo.png"></a>
            </div>
        </header>

        <form class=" m-1 p-2 m-md-5 p-md-3" action="Redactor.jsp" id="editor" method="POST" enctype="multipart/form-data">

            <p class="h2 my-3 my-md-5">Perfil de Redactor - Redacta tu noticia<p>

            <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                <label for="titulo">Titular de la noticia:</label>
                <input class="form-control" type="text" name="titulo" id="titulo">
            </div>

            <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                <label for="subtitulo">Copete:</label>
                <textarea class="form-control" type="text" name="subtitulo" id="subtitulo" rows="4" cols="80"></textarea>
            </div>

            <div class="form-group col col-md-10 col-lg-10 offset-md-1">
                <label for="cuerpo">Cuerpo de la noticia:</label>
                <textarea class="form-control" name="cuerpo" id="cuerpo" rows="10" cols="80"></textarea>
            </div>

            <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                <label for="foto">Fotografía:</label>
                <input class="form-control-file" type="file" name="foto" id="foto">
            </div>

            <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                <label for="pieFoto">Epígrafe:</label>
                <input class="form-control" type="text" name="pieFoto" id="pieFoto">
            </div>

            <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                <label for="localidad">Localidad:</label>
                <input class="form-control" type="text" name="localidad" id="localidad">
            </div>

            <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                <label for="seccion">Sección de la noticia</label>
                <!--<input class="form-control" type="text" name="seccion" id="seccion">-->
                <select id="seccion" name="seccion"></select>
            </div>  

            <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                <label for="subseccion">Subsección de la noticia</label>
                <!--<input class="form-control" type="text" name="subseccion" id="subseccion">-->
                <select id="subseccion" name="subseccion"></select>
            </div>

            <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                <input class="btn btn-info" type="submit" value="Crear noticia" name="btn">
            </div>
        </form>

        <footer>

            <div class="footer-left">

                <p class="footer-links">
                    <a href="index.jsp">Inicio</a> -
                    <a href="AvisoLegal.html">Aviso Legal</a> - 
                    <a href="https://www.prisa.com/es/info/politica-de-cookies-publi">Política de Cookies</a> - 
                    <a href="mailto:newnews@gmail.com">Contacto</a> 
                </p>

                <p class="autores">Séfora y Ezequiel Project &copy; 2019</p>

                <div class="footer-icons"> 
                    <a href="#"><i class="fa fa-facebook"></i></a> 
                    <a href="#"><i class="fa fa-twitter"></i></a> 
                    <a href="#"><i class="fa fa-linkedin"></i></a> 
                    <a href="#"><i class="fa fa-github"></i></a>
                </div>

            </div>

            <div class="footer-right">
                <p>Contáctanos:</p>
                <form action="index.jsp" method="post">
                    <input type="text" name="email" placeholder="Email" />
                    <textarea name="message" placeholder="Mensaje"></textarea>
                    <button>Send</button>
                </form>
            </div>

        </footer>
    </body>
</html>
