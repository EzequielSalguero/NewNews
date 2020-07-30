<%-- 
    Document   : EditarNoticia
    Created on : 12-mar-2019, 10:29:33
    Author     : Séfora
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Noticia</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2.0, minimum-scale=1.0,  user-scalable=yes">
        <link rel="stylesheet" type="text/css" href="CSS/reset.css">
        <link rel="stylesheet" type="text/css" href="CSS/redactor.css">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

        <!-- CSS de Bootstrap -->
        <link href="cssbs/bootstrap.min.css" rel="stylesheet" media="screen">
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
                
                /*$("#busq").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $(".table tbody tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                    });
                });*/
            });
        </script>
    </head>
    <body>
        <jsp:useBean id="bEditar" scope="request" class="logicaNegocio.beanEditar"/>
        <jsp:setProperty name="bEditar" property="*"/>

        <header class="row">
            <div class="col text-center">
                <a href="index.jsp"><img  src="IMAGENES/logo.png"></a>
            </div>
        </header>
        <c:choose>
            <c:when test="${!empty param.codNot}">
                <form class=" m-1 p-2 m-md-5 p-md-3" action="EditarNoticia.jsp" id="editor" method="POST" enctype="multipart/form-data">
                    <c:set var="noticia" scope="request" value="${bEditar.obtenerNoticia(param.codNot)}"/>
                    <p class="h2 my-3 my-md-5">Edita tu noticia<p>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="titulo">Titular de la noticia:</label>
                        <input class="form-control" type="text" name="titulo" id="titulo" value="${noticia.titulo}">
                    </div>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="subtitulo">Copete:</label>
                        <textarea class="form-control" type="text" name="subtitulo" id="subtitulo" rows="4" cols="80">${noticia.subtitulo}</textarea>
                    </div>

                    <div class="form-group col col-md-10 col-lg-10 offset-md-1">
                        <label for="cuerpo">Cuerpo de la noticia:</label>
                        <textarea class="form-control" name="cuerpo" id="cuerpo" rows="10" cols="80">${noticia.cuerpo}</textarea>
                    </div>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="foto">Fotografía:</label>
                        <input class="form-control" type="text" name="foto" id="foto" value="${noticia.foto}">
                    </div>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="foto">Nueva fotografía:</label>
                        <input class="form-control-file" type="file" name="foto" id="foto">
                    </div>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="pieFoto">Epígrafe:</label>
                        <input class="form-control" type="text" name="pieFoto" id="pieFoto" value="${noticia.pieFoto}">
                    </div>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="localidad">Localidad:</label>
                        <input class="form-control" type="text" name="localidad" id="localidad" value="${noticia.localidad}">
                    </div>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="seccion">Sección de la noticia</label>
                        <input class="form-control" type="text" name="seccionA" value="${noticia.seccion}">
                    </div>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="seccion">Nueva sección:</label>
                        <select id="seccion" name="seccion"></select>
                    </div>  

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="subseccion">Subsección de la noticia</label>
                        <input class="form-control" type="text" name="subseccionA" value="${noticia.subseccion}">
                    </div>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <label for="subseccion">Nueva subsección:</label>
                        <select id="subseccion" name="subseccion"></select>
                    </div>

                    <div class="form-group col col-md-8 col-lg-6 offset-md-2 offset-lg-1">
                        <input class="btn btn-info" type="submit" value="Editar noticia" name="btn">
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="tabla col-lg-12 table-responsive">
                    <!--<form action="Administrador.jsp" class="mb-3">
                        <input id="busq" type="text" placeholder="Buscar..">
                    </form>-->
                    <table class="table table-bordered table-hover table-striped">
                        <thead class="thead-light">
                            <tr>
                                <th>Título</th>
                                <th>Sección</th>
                                <th>Subsección</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="noticia" items="${bEditar.obtenerTodasLasNoticias()}">
                                <tr>
                                    <td>${noticia.titulo}</td>
                                    <td>${noticia.seccion}</td>
                                    <td>${noticia.subseccion}</td>
                                    <td>
                                        <form class="form-inline">
                                            <input type="submit" value="Editar" class="btn-primary rounded col-12 col-lg-auto mb-2 mb-lg-0">
                                            <input type="hidden" name="codNot" value="${noticia.codNoticia}">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </body>
</html>
