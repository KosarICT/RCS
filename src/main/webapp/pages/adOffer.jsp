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
                    <a href="#" class="notification-text" onclick="btnViewOfferClick()">
                        <i class="material-icons right notification-text">visibility</i>مشاهده
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <table id="tblOffer" class="left bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">نام و نام خانوادگی پیشنهاد دهنده</th>
            <th class="center">کد ملی</th>
            <th class="center">موضوع</th>
            <th class="center">نام بیمارستان</th>
            <th class="center"> نام بخش بیمارستان</th>
            <th class="center"> تاریخ پیشنهاد</th>
            <th class="center">طریقه ارتباط</th>
        </tr>
        </thead>

        <c:if test="${not empty offerList}">
            <c:set var="row" value="1" scope="page"/>

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${offerList}">

                <tr data-uid="${entry.ticketId}">

                    <td class="center counter"><c:out value="${row}"/></td>
                    <td class="center">${entry.firstName} ${entry.lastName}</td>
                    <td class="center">${entry.nationalCode}</td>
                    <td class="center">${entry.subject}</td>
                    <td class="center">${entry.hospital.name}</td>
                    <td class="center">${entry.section.title}</td>
                    <td class="center">${entry.submitDate}</td>
                    <td class="center">${entry.sendType.title}</td>

                </tr>

                <c:set var="row" value="${row + 1}" scope="page"/>
            </c:forEach>
            </tbody>

        </c:if>
    </table>
</div>

<div id="offerWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        مشاهده اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">

            <div class="row">
                <label for="txtName" style="font-size: 13px; font-weight: 500; color: #707070">نام نام خانوادگی پیشنهاد
                    دهنده:</label>
                <input disabled id="txtName" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کدملی
                    بیمار:</label>
                <input disabled id="txtNationalCode" type="text" class="validate notification-text">

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
                <label for="txtSectionTitle" style="font-size: 13px; font-weight: 500; color: #707070">بخش
                    بیمارستان:</label>
                <input disabled id="txtSectionTitle" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtSubject" style="font-size: 13px; font-weight: 500; color: #707070">موضوع:</label>
                <input disabled id="txtSubject" type="text" class="validate notification-text">
            </div>

            <div class="row">
                <label for="txtDescription" style="font-size: 13px; font-weight: 500; color: #707070"> متن
                    پیشنهاد:</label>
                <textarea class="materialize-textarea validate notification-text" disabled id="txtDescription"
                          length="4000"></textarea>
            </div>
            <div class="row">
                <label for="txtEmail" style="font-size: 13px; font-weight: 500; color: #707070">ایمیل:</label>
                <input disabled id="txtEmail" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtSubmitDate" style="font-size: 13px; font-weight: 500; color: #707070">تاریخ
                    پیشنهاد:</label>
                <input disabled id="txtSubmitDate" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtTrackingCode" style="font-size: 13px; font-weight: 500; color: #707070">کد
                    رهگیری:</label>
                <input disabled id="txtTrackingCode" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtAttachmentName" style="font-size: 13px; font-weight: 500; color: #707070">مستندات
                    :</label>
                <input disabled id="txtAttachmentName" type="text" class="validate notification-text">
            </div>
        </div>
        <div class="row"></div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="offerWindowToolbarButtonClick(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
    </div>

</div>

<script>
    var offerId = 0;

    $(document).ready(function () {
        $(".page-title").text("پیشنهادات");
        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initWindow();
    });

    function initWindow() {
        $('#offerWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '4%',
                complete: function () {
                    refreshOfferTable();
                }
            }
        );
    }

    function btnViewOfferClick() {
        if ($('.data-wrapper .selected').length > 0) {

            offerId = $('.data-wrapper .selected').data('uid');

            $.ajax({
                type: "POST",
                url: "/ticket/api/findTicketByTicketId",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: offerId.toString(),
                success: function (data) {
                    var dataItem = data[0];
                    $("#txtName").val(data.firstName + " " + data.lastName);
                    $("#txtNationalCode").val(data.nationalCode);
                    $("#txtMobile").val(data.mobile);
                    $("#txtSubject").val(data.subject);
                    $("#txtHospitalName").val(data.hospitalName);
                    $("#txtSectionTitle").val(data.sectionTitle);
                    $("#txtDescription").val(data.description);
                    $("#txtSubmitDate").val(data.submitDate);
                    $("#txtTrackingCode").val(data.trackingCode);
                    $("#txtEmail").val(data.email);
                    $("#txtAttachmentName").val(data.ticketAttachmentList[0].fileName);
                }
            });

            $('#offerWindow').modal('open');
        } else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
    }

    function refreshOfferTable() {
        $.ajax({
            type: "GET",
            url: "/adOffer/api/getAllOfferData",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $("#tblOffer tbody tr").remove();

                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {

                        var tr = $("<tr>").attr("data-uid", dataItem.ticketId);

                        var tdCounter = $("<td>").addClass("center").text(index + 1);
                        var tdName = $("<td>").addClass("center").text(dataItem.name);
                        var tdNationalCode = $("<td>").addClass("center").text(dataItem.nationalCode);
                        var tdsubject = $("<td>").addClass("center").text(dataItem.subject);
                        var tdHospitalName = $("<td>").addClass("center").text(dataItem.hospitalName);
                        var tdSectionName = $("<td>").addClass("center").text(dataItem.sectionName);
                        var tdSubmitDate = $("<td>").addClass("center").text(dataItem.submitDate);
                        var tdSendTypeTitle = $("<td>").addClass("center").text(dataItem.sendTypeTitle);

                        tr.append(tdCounter);
                        tr.append(tdName);
                        tr.append(tdNationalCode);
                        tr.append(tdsubject);
                        tr.append(tdHospitalName);
                        tr.append(tdSectionName);
                        tr.append(tdSubmitDate);
                        tr.append(tdSendTypeTitle);

                        $("#tblOffer tbody").append(tr);
                    });
                }

                $(".data-wrapper tr").click(function () {
                    $(this).addClass('selected').siblings().removeClass("selected");
                });
            }
        });
    }

    function offerWindowToolbarButtonClick(sender) {
        var id = sender.id;

        switch (id) {
            case "btnOk":
                $('#offerWindow').modal('close');
                break;
        }
    }

</script>

<%@include file="footer.jsp" %>