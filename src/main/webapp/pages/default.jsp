<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="site/header.jsp" %>

<input type="hidden" id="hiddenField">

<script>

    $(".brand-logo ").text("${hospitalName}");

    function createDynamicURL(command) {
        var URL = '';

        URL += command;

        return URL;
    }
</script>


<div class="row">
    <div class="col m1 l1"></div>
    <div class="col m10 l10 row">

        <div class="row">
            <div class="col s12 m6 l3 right">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <span class="card-title">شکایات</span>
                        <p>شما میتوانید شکایت خود را در این قسمت وارد و برای ما ارسال نمائید</p>
                    </div>
                    <div class="card-action">
                        <a href="javascript:window.location=createDynamicURL('complaint');">ثبت شکایت</a>
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
                        <a href="javascript:window.location=createDynamicURL('criticisms');">ثبت انتقاد</a>
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
                        <a href="javascript:window.location=createDynamicURL('offer');">ثبت پیشنهاد</a>
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
                        <a href="javascript:window.location=createDynamicURL('appreciation');">ثبت تقدیر و تشکر</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col m3 l3 right"></div>

            <div class="col s12 m6 l6 right">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <span class="card-title">پیگیری</span>
                        <p>پیگیری از وضعیت درخواست های ثبت شده شما</p>
                    </div>
                    <div class="card-action">
                        <a href="javascript:window.location=createDynamicURL('follow');">سیستم پیگیری</a>
                    </div>
                </div>
            </div>

            <div class="col m3 l3 right"></div>
        </div>
    </div>
    <div class="col m1 l1"></div>
</div>

<%@include file="site/footer.jsp" %>


