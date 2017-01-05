<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>

    <link href="<c:url value='/static/css/web/materialIcon.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/web/loading.css'/>" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/css/web/materialize.min.css'/>"
          media="screen,projection"/>
    <link href="<c:url value='/static/css/web/common.css'/>" rel="stylesheet"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <script type="text/javascript" src="<c:url value='/static/js/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/materialize.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/persiaNumber.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/waypoints.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/jquery.counterup.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/jquery.noty.packaged.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/Chart.min.js'/>"></script>
</head>
<body>

<%--
<div id="loading" style="  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #12162B;">
    <div class="jawn"></div>
</div>
--%>

<header>
    <ul id="dropdown1" class="dropdown-content" style="padding-right: 0; background: #fff !important;">
        <li><a style="font-size: 14px" href="<c:url value="/logout" />">خروج از سیستم</a></li>
    </ul>
    <nav class="top-nav mainColor">
        <div class="nav-wrapper" style="margin: 0 25px 0 25px">
            <a href="#" data-activates="nav-mobile" class="button-collapse top-nav full hide-on-large-only right">
                <i class="material-icons notification-text">menu</i></a>
            <a class="page-title right" style="font-size: 20px; font-weight: bold !important; color: #436095">صفحه
                اصلی</a>
            <ul id="nav-mobile1" class="left hide-on-med-and-down">
                <li>
                    <a href="#"
                       style="font-size: 15px">
                        <i class="material-icons notification-text">notifications</i>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
    <ul id="nav-mobile" class="side-nav no-padding fixed slideColor" style="transform: translateX(0%);height: 100%">

        <li>
            <div class="userView" style="margin-top: 50px; text-align: -webkit-center">
                <img id="imgUserImage" class="circle" style="background: transparent">
            </div>
            <div>
                <ul style="padding-right: 0">
                    <li>
                        <a id="currentUserDisplayName" class="dropdown-button center white-text" href="#"
                           data-activates="dropdown1"
                           style="font-size: 15px !important; font-weight: bold !important;">
                        </a>
                    </li>
                </ul>
            </div>
        </li>
    </ul>
</header>

<main id="main-content">
    <div class="col s12 m12 l12" id="container">