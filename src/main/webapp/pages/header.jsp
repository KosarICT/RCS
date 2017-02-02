<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>پنل مدیریت</title>
    <link rel="icon"
          type="image/png"
          href="/static/hospitalImage/heart.png">
    <link href="<c:url value='/static/css/web/rtl.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/web/kendo.common.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/web/Skin/kendo.fiori.min.css'/>" rel="stylesheet">
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
    <%--<script type="text/javascript" src="<c:url value='/static/js/Chart.min.js'/>"></script>--%>
    <script type="text/javascript" src="<c:url value='/static/js/kendo.all.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/jszip.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/js/PersianDate.js '/>"></script>
<%--    <script type="text/javascript" src="<c:url value='/static/js/datatables.min.js '/>"></script>--%>

    <style>
        span.badge {
            min-width: 27px;
            padding: 0 6px;
            /* margin-right: 38px; */
            text-align: center;
            font-size: 1rem;
            line-height: inherit;
            color: #757575;
            float: left;
            box-sizing: border-box;
        }

        span.badge.new {
            font-weight: 300;
            font-size: 0.8rem;
            color: #fff;
            background-color: #26a69a;
            border-radius: 2px;
            z-index: 10;
        }

        .inline{
            display: inline-block !important;
        }

        .side-nav a{
            padding:0 10px !important;
        }

        .side-nav .collapsible-header, .side-nav.fixed .collapsible-header{
            padding: 0 10px !important;
        }
    </style>

    <script>

        $(document).ready(function () {
            getTabList();

            $('.button-collapse').sideNav({
                menuWidth: 180,
                edge: 'right'
            });

            $(".dropdown-button").dropdown({
                belowOrigin: true,
                alignment: 'right'
            });
        });

        function getTabList() {
            $.ajax({
                type: "GET",
                url: "/home/api/getMenu",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success: function (data) {
                    $("#currentUserDisplayName").text(data[0][0].displayName);
                    $("#imgUserImage").attr("src", "/static/userImage/" + data[0][0].imageName);

                    if (data.length > 0) {
                        $.each(data[1], function (index, dataItem) {
                            var data = dataItem;

                            var liParent = $("<li>");

                            if (data.child.length > 0) {
                                liParent.addClass("no-padding");

                                var ul = $("<ul>").addClass("collapsible collapsible-accordion").css("padding-right", "0");

                                var liChild = $("<li>");

                                var a = $("<a>").text(data.title).addClass("collapsible-header white-text");

                                var i = $("<i>").addClass("material-icons white-text").text("arrow_drop_down");

                                var div = $("<div>").addClass("collapsible-body");

                                var ulDropDown = $("<ul>").css("padding-right", "0").addClass("slideColor");

                                $.each(data.child, function (counter, item) {
                                    var liDropDown = $("<li>");
                                    var aDropDown = $("<a>").text(item.title).attr("href", item.url).addClass("white-text");

                                    liDropDown.append(aDropDown);

                                    ulDropDown.append(liDropDown);
                                });

                                div.append(ulDropDown);

                                a.append(i);
                                liChild.append(a);
                                liChild.append(div);

                                ul.append(liChild);

                                liParent.append(ul);

                                $("#nav-mobile").append(liParent);

                            } else {
                                var a = $("<a>").addClass("white-text");
                                var span = $("<span>").addClass("new badge").text(data.count).attr("data-badge-caption", "");


                                liParent.append(a);

                                if (data.url == "logout") {
                                    a.attr("href", "<c:url value="/logout" />");
                                } else if (data.url == "admin" || data.url == "setting") {
                                    a.attr("href", data.url).text(data.title);
                                } else {
                                    a.attr("href", data.url).text(data.title);
                                    if(data.count>0){
                                    a.append(span);}
                                }

                                $("#nav-mobile").append(liParent);
                            }

                            setTimeout(function () {
                                selectTab();
                            }, 50);
                        });


                        $('.collapsible').collapsible();
                    }
                }
            });
        }

        function selectTab() {
            var url = window.location.pathname;

            switch (url) {
                case "/admin":
                    debugger;
                    $("#nav-mobile li a[href=admin]").parent().addClass("active");
                    break;
                case "/adAppreciation":
                    $("#nav-mobile li a[href=adAppreciation]").parent().addClass("active");
                    break;
                case "/adComplain":
                    $("#nav-mobile li a[href=adComplain]").parent().addClass("active");
                    break;
                case "/adOffer":
                    $("#nav-mobile li a[href=adOffer]").parent().addClass("active");
                    break;
                case "/adCriticism":
                    $("#nav-mobile li a[href=adCriticism]").parent().addClass("active");
                    break;
                case "/adArchive":
                    $("#nav-mobile li a[href=adArchive]").parent().addClass("active");
                    break;
                case "/user":
                    $("#nav-mobile li a[href=user]").parent().parent().parent().css("display", "block");
                    $("#nav-mobile li a[href=user]").parent().addClass("active");
                    break;
                case "/role":
                    $("#nav-mobile li a[href=role]").parent().parent().parent().css("display", "block");
                    $("#nav-mobile li a[href=role]").parent().addClass("active");
                    break;
                case "/hospital":
                    $("#nav-mobile li a[href=hospital]").parent().parent().parent().css("display", "block");
                    $("#nav-mobile li a[href=hospital]").parent().addClass("active");
                    break;
                case "/complaintType":
                    $("#nav-mobile li a[href=complaintType]").parent().parent().parent().css("display", "block");
                    $("#nav-mobile li a[href=complaintType]").parent().addClass("active");
                    break;
                case "/hospitalSection":
                    $("#nav-mobile li a[href=hospitalSection]").parent().parent().parent().css("display", "block");
                    $("#nav-mobile li a[href=hospitalSection]").parent().addClass("active");
                    break;
                    case "/section":
                    $("#nav-mobile li a[href=section]").parent().parent().parent().css("display", "block");
                    $("#nav-mobile li a[href=section]").parent().addClass("active");
                    break;
            }
        }
    </script>
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
                    <a href="<c:url value="/logout" />" style="color: #555555; font-weight: 400">خروج از سیستم</a>
                </li>
            </ul>
        </div>
    </nav>
    <ul id="nav-mobile" class="side-nav no-padding fixed slideColor" style="transform: translateX(0%);height: 100%">

        <li>
            <div class="userView" style="text-align: -webkit-center">
                <img id="imgUserImage" class="circle" style="background: transparent">
            </div>
            <div>
                <ul style="padding-right: 0">
                    <li>
                        <a id="currentUserDisplayName" class="dropdown-button center white-text" href="#"
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