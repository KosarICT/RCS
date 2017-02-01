<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<input type="hidden" id="hiddenField">

<script>
    function createDynamicURL() {
        var hospitalId = $("#ddlHospital option:selected").val();

        var URL = '';

        URL += hospitalId + "/";

        return URL;
    }
</script>


<div class="row">
    <div class="col m1 l3"></div>

    <div class="card col s11 m10 l6 row" style="padding: 30px 60px 30px 60px" >

        <div class="row">
            <div class="col s12 m4 l4 right marginTop">
                <label for="ddlHospital">انتخاب بیمارستان:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <select id="ddlHospital">
                    <option value="" disabled selected>بیمارستان موردنظر انتخاب نمائید</option>
                    <c:if test="${not empty hospitalList}">
                        <c:forEach var="entry" items="${hospitalList}">
                            <option value="${entry.hospitalId}">${entry.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
            </div>

            <a class="waves-effect waves-light btn"
                href="javascript:window.location=createDynamicURL();">انتخاب بیمارستان

            </a>

        </div>

        <div class="col m1 l3"></div>
    </div>

    <div class="col m1 l3"></div>

</div>

<%@include file="footer.jsp" %>