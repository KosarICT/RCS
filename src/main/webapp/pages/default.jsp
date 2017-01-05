<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="site/header.jsp" %>

<div class="row">
    <div class="col m1 l1"></div>
    <div class="col m10 l10 row">

        <div class="col s12 m6 l3 right">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">شکایات</span>
                    <p>شما میتوانید شکایت خود را در این قسمت وارد و برای ما ارسال نمائید</p>
                </div>
                <div class="card-action">
                    <a href="/complaint">ثبت شکایت</a>
                </div>
            </div>
        </div>

        <div class="col s12 m6 l3 right">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">انتقادات</span>
                    <p>انتقادات خود را در جهت بهبود عملکرد مجموعه برای ما ارسال نمائید</p>
                </div>
                <div class="card-action">
                    <a href="/criticisms">ثبت انتقاد</a>
                </div>
            </div>
        </div>

        <div class="col s12 m6 l3 right">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">پیشنهادات</span>
                    <p>پیشنهادات خود را در جهت بهبود عملکرد مجموعه برای ما ارسال نمائید</p>
                </div>
                <div class="card-action">
                    <a href="/offer">ثبت پیشنهاد</a>
                </div>
            </div>
        </div>

        <div class="col s12 m6 l3 right">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">تقدیر و تشکر</span>
                    <p>ثبت تقدیر و تشکرات شما از مجموعه و پرسنل جهت بهبود</p>
                </div>
                <div class="card-action">
                    <a href="/appreciation">ثبت تقدیر و تشکر</a>
                </div>
            </div>
        </div>

        <div class="col s12 m6 l3 right">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">پیگیری</span>
                    <p>پیگیری از وضعیت درخواست های ثبت شده شما</p>
                </div>
                <div class="card-action">
                    <a href="#">سیستم پیگیری</a>
                </div>
            </div>
        </div>
    </div>
    <div class="col m1 l1"></div>
</div>

<%@include file="site/footer.jsp" %>


