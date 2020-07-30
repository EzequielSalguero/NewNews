<%-- 
    Document   : index
    Created on : 31-ene-2019, 14:05:22
    Author     : Ezequiel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NewNews</title>
        <link rel="stylesheet" type="text/css" href="CSS/reset.css">
        <link rel="stylesheet" type="text/css" href="CSS/estilo.css">
        <link rel="stylesheet" type="text/css" href="CSS/footer.css">
        <link rel="stylesheet" type="text/css" href="cssbs/bootstrap.css">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

        <%--<script>
            $(function(){
                /*var desplegar=true;
                $("#inicioSesion").on("click",function(){
                    if(desplegar){
                        $("#logueo").slideDown();
                        desplegar=false;
                    }else{
                        $("#logueo").slideUp();
                        desplegar=true;
                    }
                });
                
                $("#entrar").on("submit",function(ev){
                    ev.preventDefault();
                    var datos = {idUsuario:$("#idUsuario").val(),
                                 pass:$("#pass").val()};
                    
                    var datosJson = JSON.stringify(login);						   
        
                    enviarPeticion("POST","ComprobarUsuario",true,comprobar,datosJson); 
                });
                
                function comprobar(data){
                    if(data.Acceso == "Si"){
                        $('h3.mensaje').remove();
                        $('#inicioSesion').trigger("click");
                    }else{
                        $("#entrar").after("<h3 class='mensaje'>Compruebe usuario y contraseña</h3>");
                    }
                }*/
                
                /*$("#log").validate({
                    rules: {
                        idUsuario: {
                            required:true
                        },
                        pass: {
                            required:true
                        }
                    },
                    messages: {
                        idUsuario: {
                            required:"Este campo es obligatorio"
                        },
                        pass: {
                            required:"Este campo es obligatorio"
                        }
                    },
                    submitHandler: function (form) {
                        var datos = {idUsuario:$("#idUsuario").val(),
                                     pass:$("#pass").val()};
                        
                        $.ajax({
                            method: 'GET',
                            data: datos,
                            url: 'ComprobarUsuario',
                            success: function (data, xhr) {
                                console.log(xhr);
                                $("#mensaje").text(data.Acceso);
                            }
                        });
                    }
                });*/
            });
        </script>--%>
    </head>
    <body>
        <jsp:useBean id="coment" scope="request" class="logicaNegocio.beanComentarios"/>

        <c:if test="${!empty param.textBuscar}">
            <jsp:include page="Buscar"/>
        </c:if>
        
        <c:if test="${!empty param.idUsuario}">
            <jsp:include page="ComprobarUsuario"/>
        </c:if>

        <jsp:include page="ObtenerNoticias"/>

        <c:if test="${!empty param.cod}">
            <jsp:include page="ObtenerNoticiaEspecifica"/>
        </c:if>

        <c:if test="${!empty param.comentario}">
            <jsp:include page="NuevoComentario"/>
        </c:if>

        <c:if test="${!empty param.cerrar}">
            <jsp:include page="CerrarSesion"/>
            <c:redirect url="index.jsp"/>
        </c:if>

        <c:if test="${!empty param.enviarContacto}">
            <jsp:include page="NuevoContacto"/>
        </c:if>

        <header>
            <a href="index.jsp"><img src="IMAGENES/logo.png" id="logo"></a>
            
            <div class="busqueda">
                <form>
                    <input type="text" name="textBuscar" placeholder="Búsqueda">
                    <input type="submit" value="Buscar">
                </form>
            </div>

            <c:choose>
                <c:when test="${!empty sessionScope.usu}">
                    <article id="cerrar">
                        <div class="usuario">
                            <img src="IMAGENES/user-symbol.png">
                            <span> ${sessionScope.usu.getIdUsuario()}</span>
                        </div>
                        <div id="cerrarSesion">
                            <c:if test="${!empty sessionScope.redactor}">
                                <a href="Redactor.jsp">Redactar Noticia</a>
                                <a href="Noticias.jsp">Editar Noticia</a>
                            </c:if>

                            <c:if test="${!empty sessionScope.administrador}">
                                <a href="Administrador.jsp">Perfil</a>
                            </c:if>

                            <a href="index.jsp?cerrar=si">Cerrar Sesión</a>
                        </div>
                    </article>
                </c:when>
                <c:otherwise>
                    <section id="login">
                        <a href="#" id="inicioSesion">Iniciar Sesión</a>
                        <p id="inicio">Iniciar Sesión</p>
                        <!--<a href="Registro.jsp">Registrarse</a>-->
                        <div id="logueo">
                            <form id="log">
                                <input type="text" name="idUsuario" id="idUsuario" placeholder="Nombre Usuario">
                                <input type="password" name="pass" id="pass" placeholder="Contraseña">
                                <input type="submit" value="Entrar" id="entrar">
                                <h3 id="mensaje">${requestScope.mensaje}</h3>
                                <label>No estás registrado, <a id="reg" href="Registro.jsp">hazlo aquí</a></label>
                            </form>
                        </div>
                    </section>
                </c:otherwise>
            </c:choose>

        </header>

        <div class="menu">
            <a href="#" class='letra_menu'>H</a>
            <a href="#" class='letra_menu'>X</a>
        </div>

        <nav>
            <ul>
                <li><a href="index.jsp?seccion=Internacional">Internacional</a></li>
                <li><a href="index.jsp?seccion=España">España</a><ul class="sub">
                        <li><a href="index.jsp?subseccion=Andalucía&sec=España">Andalucía</a></li>
                        <li><a href="index.jsp?subseccion=Madrid&sec=España">Madrid</a></li>
                        <li><a href="index.jsp?subseccion=Cataluña&sec=España">Cataluña</a></li>
                        <li><a href="index.jsp?subseccion=Baleares&sec=España">Baleares</a></li>
                        <li><a href="index.jsp?subseccion=Canarias&sec=España">Canarias</a></li>
                        <li><a href="index.jsp?subseccion=País%20Vasco&sec=España">País Vasco</a></li>
                        <li><a href="index.jsp?subseccion=Otras%20Provincias&sec=España">Otras Provincias</a></li>
                    </ul></li>
                <li><a href="index.jsp?seccion=Economía">Economía</a><ul class="sub">
                        <li><a href="index.jsp?subseccion=Mercados&sec=Economía">Mercados</a></li>
                        <li><a href="index.jsp?subseccion=Vivienda&sec=Economía">Vivienda</a></li>
                        <li><a href="index.jsp?subseccion=Formación&sec=Economía">Formación</a></li>
                    </ul></li>
                <li><a href="index.jsp?seccion=Deportes">Deportes</a><ul class="sub">
                        <li><a href="index.jsp?subseccion=Fútbol&sec=Deportes">Fútbol</a></li>
                        <li><a href="index.jsp?subseccion=Baloncesto&sec=Deportes">Baloncesto</a></li>
                        <li><a href="index.jsp?subseccion=Motor&sec=Deportes">Motor</a></li>
                        <li><a href="index.jsp?subseccion=Polideportivo&sec=Deportes">Polideportivo</a></li>
                    </ul></li>
                <li><a href="index.jsp?seccion=Tecnología">Tecnología</a><ul class="sub">
                        <li><a href="index.jsp?subseccion=Videojuegos&sec=Tecnología">Videojuegos</a></li>
                        <li><a href="index.jsp?subseccion=Redes%20Sociales&sec=Tecnología">Redes Sociales</a></li>
                        <li><a href="index.jsp?subseccion=Youtube&sec=Tecnología">Youtube</a></li>
                    </ul></li>
                <li><a href="index.jsp?seccion=Cultura">Cultura</a></li>
                <li><a href="index.jsp?seccion=Opinión">Opinión</a></li>
            </ul>

        </nav>

        <c:if test="${!empty param.seccion || !empty param.subseccion}">
            <div id="migas">
                <ul class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index.jsp">Inicio</a></li>
                        <c:if test="${!empty param.seccion}">
                        <li class="breadcrumb-item">${param.seccion}</li>
                        </c:if>
                        <c:if test="${!empty param.subseccion}">
                        <li class="breadcrumb-item"><a href="index.jsp?seccion=${param.sec}">${param.sec}</a></li>
                        <li class="breadcrumb-item">${param.subseccion}</li>
                        </c:if>
                </ul>
            </div>
        </c:if>
        
        <h2 id="donde">
            <c:choose>
                <c:when test="${!empty param.seccion}">
                    ${param.seccion}
                </c:when>
                <c:otherwise>
                    ${param.subseccion}
                </c:otherwise>
            </c:choose>
        </h2>

        <section id="notPrincDest">

            <c:choose>
                <c:when test="${!empty requestScope.noticia}">
                    <article id="notSelec">
                        <figure>
                            <img src="IMAGENES/${requestScope.noticia.foto}">
                            <figcaption>${requestScope.noticia.pieFoto}</figcaption>
                        </figure>
                        <h1>${requestScope.noticia.titulo}</h1>
                        <h2>${requestScope.noticia.subtitulo}</h2>
                        <div>
                            ${requestScope.noticia.cuerpo}
                        </div>
                        <div>
                            <h2>Comentarios</h2>
                            <c:forEach var="listaComent" items="${coment.obtenerComentarios(requestScope.noticia.codNoticia)}">
                                ${listaComent.idUsuario.idUsuario}
                                ${listaComent.comentario}
                            </c:forEach>
                        </div>
                        <c:if test="${!empty sessionScope.usu}">
                            <div id="comentarios">
                                <form action="index.jsp">
                                    <textarea cols="50" rows="10" placeholder="Escriba aquí su comentario..." name="comentario"></textarea>
                                    <input type="hidden" name="codNoticia" value="${requestScope.noticia.codNoticia}">
                                    <input type="submit" value="Enviar Comentario">
                                </form>
                            </div>
                        </c:if>
                    </article>
                </c:when>
                <c:when test="${!empty requestScope.listaNoticiasBuscadas}">
                    <section id="notBusc">
                        <c:forEach var="noticia" items="${requestScope.listaNoticiasBuscadas}">
                            <article>
                                <img src="IMAGENES/${noticia.foto}">
                                <h2><a href="index.jsp?cod=${noticia.codNoticia}">${noticia.titulo}</a></h2>
                                <h4>${noticia.subtitulo}</h4>
                            </article>
                        </c:forEach>
                    </section>
                </c:when>
                <c:otherwise>
                    <article id="notPrinc">
                        <c:forEach var="noticia" items="${requestScope.listadoNoticias}" varStatus="estado">

                            <c:if test="${estado.first}">
                                <img src="IMAGENES/${noticia.foto}" id="imgPrinc">
                                <h2><a href="index.jsp?cod=${noticia.codNoticia}">${noticia.titulo}</a></h2>
                                <h4>${noticia.subtitulo}</h4>
                            </c:if>

                        </c:forEach>

                        <section id="noticias">
                            <c:forEach var="noticia" items="${requestScope.listadoNoticias}" varStatus="estado">

                                <c:if test="${estado.count>1}">
                                    <article>
                                        <img src="IMAGENES/${noticia.foto}">
                                        <h2><a href="index.jsp?cod=${noticia.codNoticia}">${noticia.titulo}</a></h2>
                                        <h4>${noticia.subtitulo}</h4>
                                    </article>
                                </c:if>

                            </c:forEach>
                        </section>
                    </article>


                </c:otherwise>
            </c:choose>

            <section id="notDest">
                <h1>Noticias Destacadas</h1>
                <c:forEach var="noticia" items="${requestScope.listadoNoticiasNumVisitas}" varStatus="estado">
                    <c:if test="${estado.count<=3}">
                        <article>
                            <img src="IMAGENES/${noticia.foto}">
                            <h2><a href="index.jsp?cod=${noticia.codNoticia}">${noticia.titulo}</a></h2>
                            <h4>${noticia.subtitulo}</h4>
                        </article>
                    </c:if>
                </c:forEach>
            </section>
        </section>

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
                    <textarea name="mensaje" placeholder="Mensaje"></textarea>
                    <input type="submit" name="enviarContacto" value="Enviar">
                </form>
            </div>

        </footer>

        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="JS/jquery.min.js"><\/script>')</script>
        <script src="JS/menu.js"></script>
        <script src="JS/Validate/jquery.validate.js"></script>
        <script src="JS/Validate/ajax.js"></script>
        <script src="JS/Validate/messages_es.js"></script>
        <script src="JS/Validate/additional-methods.js"></script>
    </body>
</html>
