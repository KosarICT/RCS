<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<style>
    .modal select {
        display: block;
    }
</style>

<div class="row">
    <nav>
        <div class="nav-wrapper grey lighten-4" style="border: 1px solid #e0e0e0">
            <ul class="left ">
                <li>
                    <a href="#" class="notification-text" onclick="btnViewClick()">
                        <i class="material-icons right notification-text">visibility</i>مشاهده
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <table id="tblAppreciation" class="left bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">نام و نام خانوادگی</th>
            <th class="center"> نام و نام خانوادگی پرسنل</th>
            <th class="center">کد ملی</th>
            <th class="center">بیمارستان</th>
            <th class="center"> بخش</th>
        </tr>
        </thead>

        <c:if test="${not empty appreciationListList}">
            <c:set var="row" value="1" scope="page"/>

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${appreciationListList}">

                <tr data-uid="${entry.appreciationId}">

                    <td class="center counter"><c:out value="${row}"/></td>
                    <td class="center">${entry.firstName} ${entry.lastName}</td>
                    <td class="center">${entry.persnolFirstName} ${entry.persnolLastName}</td>
                    <td class="center">${entry.nationalCode}</td>
                    <td class="center">${entry.hospital.name}</td>
                    <td class="center">${entry.section.title}</td>

                </tr>

                <c:set var="row" value="${row + 1}" scope="page"/>
            </c:forEach>
            </tbody>

        </c:if>
    </table>
</div>

<div id="appreciationWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        مشاهده اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">

            <div class="row">
                <label for="txtName" style="font-size: 13px; font-weight: 500; color: #707070">نام نام خانوادگی:</label>
                <input disabled id="txtName" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کدملی
                    بیمار:</label>
                <input disabled id="txtNationalCode" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtPersonnelName" style="font-size: 13px; font-weight: 500; color: #707070">نام پرسنل
                    :</label>
                <input disabled id="txtPersonnelName" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtMobile" style="font-size: 13px; font-weight: 500; color: #707070">تلفن همراه:</label>
                <input disabled id="txtMobile" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtHospitalName" style="font-size: 13px; font-weight: 500; color: #707070">نام بیمارستان
                    :</label>
                <input disabled id="txtHospitalName" type="text" class="validate notification-text">

            </div>

            <div class="row">
                <label for="txtSectionTitle" style="font-size: 13px; font-weight: 500; color: #707070">بخش کاری:</label>
                <input disabled id="txtSectionTitle" type="text" class="validate notification-text">
            </div>

            <div class="row">
                <label for="txtDescription" style="font-size: 13px; font-weight: 500; color: #707070">متن
                    قدردانی:</label>
                <textarea class="materialize-textarea validate notification-text" disabled id="txtDescription"
                          length="4000"></textarea>
            </div>
        </div>
        <div class="row"></div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="appreciationWindowToolbarClickButton(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
    </div>
</div>

<script>
    var appreciationId = 0;

    $(document).ready(function () {
        $(".page-title").text("قدردانی");

        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initWindow();
    });

    function initWindow() {
        $('#appreciationWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '4%',
                complete: function () {
                    refreshAppreciationTable();
                }
            }
        );
    }

    function btnViewClick() {
        if ($('.data-wrapper .selected').length > 0) {

            appreciationId = $('.data-wrapper .selected').data('uid');

            $.ajax({
                type: "POST",
                url: "/adAppreciation/api/findAppreciationById",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: appreciationId.toString(),
                success: function (data) {
                    var dataItem = data[0];

                    $("#txtName").val(dataItem.name);
                    $("#txtNationalCode").val(dataItem.nationalCode);
                    $("#txtPersonnelName").val(dataItem.personnelName);
                    $("#txtMobile").val(dataItem.mobile);
                    $("#txtHospitalName").val(dataItem.hospitalName);
                    $("#txtSectionTitle").val(dataItem.sectionTitle);
                    $("#txtDescription").val(dataItem.description);
                }
            });

            $('#appreciationWindow').modal('open');
        } else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
    }

    function refreshAppreciationTable() {
        $.ajax({
            type: "GET",
            url: "/adAppreciation/api/getAllAppreciationData",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $("#tblAppreciation tbody tr").remove();

                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {

                        var tr = $("<tr>").attr("data-uid", dataItem.appreciationId);

                        var tdCounter = $("<td>").addClass("center").text(index + 1);
                        var tdName = $("<td>").addClass("center").text(dataItem.name);
                        var tdPersonnelName = $("<td>").addClass("center").text(dataItem.personnelName);
                        var tdNationalCode = $("<td>").addClass("center").text(dataItem.nationalCode);
                        var tdHospitalName = $("<td>").addClass("center").text(dataItem.hospitalName);
                        var tdSectionName = $("<td>").addClass("center").text(dataItem.sectionName);

                        tr.append(tdCounter);
                        tr.append(tdName);
                        tr.append(tdPersonnelName);
                        tr.append(tdNationalCode);
                        tr.append(tdHospitalName);
                        tr.append(tdSectionName);

                        $("#tblAppreciation tbody").append(tr);
                    });
                }

                $(".data-wrapper tr").click(function () {
                    $(this).addClass('selected').siblings().removeClass("selected");
                });
            }
        });
    }

    function appreciationWindowToolbarClickButton(sender) {
        var id = sender.id;

        switch (id) {
            case "btnOk":
                $('#appreciationWindow').modal('close');
                break;
        }
    }

</script>

<%@include file="footer.jsp" %>