<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Title</title>
    <link href="<c:url value='/static/css/web/materialIcon.css'/>" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/css/web/materialize.min.css'/>"
          media="screen,projection"/>
    <link href="<c:url value='/static/css/web/common.css'/>" rel="stylesheet">


    <script type="text/javascript" src="<c:url value='/static/js/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/materialize.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/persiaNumber.min.js'/>"></script>

    <style>
        #map-canvas {
            width: 100%;
            height: 300px;
            margin: 0;
            padding: 15px;
        }

        label {
            font-size: 13px;
            color: black;
            font-weight: bold;
        }

        .card select {
            display: block;
        }
    </style>
</head>

<body >

<nav class="blue-grey darken-1">
    <div class="nav-wrapper">
        <img class="responsive-img right"  src="../static/icon//logouu.png" style="display: inline-block; margin-top: 7px">
        <h5 class="brand-logo right" style="display: inline-block; margin-top: 20px;font-size: 22px !important; margin-right: 40px">دانشگاه علوم پزشکی ایران</h5>
    </div>
</nav>
