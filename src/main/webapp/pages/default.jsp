<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="site/header.jsp" %>

<input type="hidden" id="hiddenField">

<script>

    $(".brand-logo ").text("${hospitalName}");
    $("#imgHospital").attr("src", "/static/hospitalImage/" + "${hospitalImage}");

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
                        <img style="width: 40px; height: 40px; margin-left: 10px" src="${pageContext.request.contextPath}/static/icon/complaint.png">
                        <span style="vertical-align: bottom" class="card-title">شکایات</span>
                        <p style="margin-top: 10px">شما میتوانید شکایت خود را در این قسمت وارد و برای ما ارسال نمائید</p>
                    </div>
                    <div class="card-action">
                        <a href="javascript:window.location=createDynamicURL('complaint');">ثبت شکایت</a>
                    </div>
                </div>
            </div>

            <div class="col s12 m6 l3 right">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <img style="width: 40px; height: 40px; margin-left: 10px" src="${pageContext.request.contextPath}/static/icon/criticize.png">
                        <span style="vertical-align: bottom" class="card-title">انتقادات</span>
                        <p style="margin-top: 10px">انتقادات خود را در جهت بهبود عملکرد مجموعه برای ما ارسال نمائید</p>
                    </div>
                    <div class="card-action">
                        <a href="javascript:window.location=createDynamicURL('criticisms');">ثبت انتقاد</a>
                    </div>
                </div>
            </div>

            <div class="col s12 m6 l3 right">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <img style="width: 40px; height: 40px; margin-left: 10px" src="${pageContext.request.contextPath}/static/icon/offer.png">
                        <span style="vertical-align: bottom" class="card-title">پیشنهادات</span>
                        <p style="margin-top: 10px">پیشنهادات خود را در جهت بهبود عملکرد مجموعه برای ما ارسال نمائید</p>
                    </div>
                    <div class="card-action">
                        <a href="javascript:window.location=createDynamicURL('offer');">ثبت پیشنهاد</a>
                    </div>
                </div>
            </div>

            <div class="col s12 m6 l3 right">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <img style="width: 40px; height: 35px; margin-left: 10px" src="${pageContext.request.contextPath}/static/icon/appreciation.png">
                        <span style="vertical-align: bottom" class="card-title">تقدیر و تشکر</span>
                        <p style="margin-top: 10px"> ثبت تقدیر و تشکرات شما از مجموعه و پرسنل جهت بهبود</p>
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
                <div class="card">
                    <div class="card-content white-text">
                        <span class="card-title" style="color: #4d4d4d">کد پیگیری:</span>
                        <div class="col s12 m8 l8 left">
                            <input maxlength="12" id="txtTrackingCode" type="text" style="color: #4d4d4d; text-align: center"
                                   onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
                        </div>
                    </div>
                    <div class="card-action">
                        <a href="#">جستجو</a>
                    </div>
                </div>
            </div>

            <div class="col m3 l3 right"></div>
        </div>
    </div>
    <div class="col m1 l1"></div>
</div>

<script>
    $(document).ready(function () {
        var webUrl = "${webUrl}";

        $("#webURL").val(webUrl);

        $("#hospitalUrl").attr("href",  $("#webURL").val());
    });
</script>

<%@include file="site/footer.jsp" %>


