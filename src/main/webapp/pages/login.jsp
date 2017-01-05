<%--
  Created by IntelliJ IDEA.
  User: Sadegh-Pc
  Date: 11/28/2016
  Time: 5:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Title</title>

    <link href="<c:url value='/static/css/web/login1.css'/>" rel="stylesheet"/>
</head>
<body>
<div class="login-container">
    <section class="login" id="login">

        <header>
            <h2 style="text-align: center">پنل مدیریت سیستم شکایت دانشگاه علوم پزشکی</h2>
            <c:if test="${not empty error}"><div style="text-align: right">${error}</div></c:if>
            <c:if test="${not empty message}"><div style="text-align: right">${message}</div></c:if>
        </header>

        <form class="login-form" action="${loginUrl}" method="post" >
            <input type="text" name="userName" class="login-input" placeholder="نام کاربری" required autofocus/>
            <input type="password" name="password" class="login-input" placeholder="کلمه عبور" required/>
            <div class="submit-container">
                <button type="submit" class="login-button">ورود به سیستم</button>
            </div>
        </form>

    </section>
</div>
</body>
</html>
