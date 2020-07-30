<%-- 
    Document   : NoticiasUsuario
    Created on : 12-mar-2019, 10:54:54
    Author     : Séfora
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Noticias</title>
        <link href="cssbs/bootstrap.min.css" rel="stylesheet" media="screen">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="JS/jquery.min.js"><\/script>')</script>
        <script>
            $(function(){
                $("#busq").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $(".table tbody tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                    });
                });
            });
        </script>
    </head>
    <body>
        
        <jsp:useBean id="bNoticias" scope="request" class="logicaNegocio.beanNoticias"/>
        <c:if test="${!empty param.editar}">
            <jsp:forward page="EditarNoticia.jsp"/>
        </c:if>

        <header class="row">
            <div class="col text-center">
                <a href="index.jsp"><img  src="IMAGENES/logo.png"></a>
            </div>
        </header>
        
        <div class="tabla col-lg-12 table-responsive">
            <form action="Administrador.jsp" class="mb-3">
                <input id="busq" type="text" placeholder="Buscar..">
            </form>
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
                <c:forEach var="noticia" items="${bNoticias.obtenerNoticiasUsu(sessionScope.usu.idUsuario)}">
                    <tr>
                        <td>${noticia.titulo}</td>
                        <td>${noticia.seccion}</td>
                        <td>${noticia.subseccion}</td>
                        <td>
                            <form class="form-inline">
                                <input type="submit" value="Editar" class="btn-primary rounded col-12 col-lg-auto mb-2 mb-lg-0" name="editar">
                                <input type="hidden" name="codNot" value="${noticia.codNoticia}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
