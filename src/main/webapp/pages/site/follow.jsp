<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<div class="row">
    <div class="col m3 l3"></div>

    <div class="card col s12 m10 l6 row" style="padding: 30px 60px 30px 60px">
        <div class="row">
            <div class="col s12 m4 l4 right marginTop">
                <label>کد پیگیری:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="txtTrackingCode" type="text" class="validate">
            </div>

        </div>
        <div class="row">
            <div class="col s12 m4 l4 right marginTop"></div>

            <a class="waves-effect waves-light btn"
               onclick="appreciationSubmit()">جستجو

            </a>
        </div>
    </div>

    <div class="col m1 l3"></div>
</div>

<script>
    $(document).ready(function () {
        var id = "${hospitalId}";
        var hospitalName = "${hospitalName}";

        $(".brand-logo").text($("#ddlHospital option:selected").text());
        $("#imgHospital").attr("src", "/static/hospitalImage/" + hospitalName);

    });
</script>

<%@include file="footer.jsp" %>