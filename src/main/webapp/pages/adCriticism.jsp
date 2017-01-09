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
                    <a href="#" class="notification-text" onclick="btnViewCriticismClick()">
                        <i class="material-icons right notification-text">visibility</i>مشاهده
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <table id="tblCriticism" class="left bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">نام و نام خانوادگی انتقاد کننده</th>
            <th class="center">کد ملی</th>
            <th class="center"> موضوع</th>
            <th class="center">نام بیمارستان</th>
            <th class="center">بخش بیمارستان</th>
            <th class="center">تاریخ انتقاد</th>
            <th class="center">طریقه ارتباط</th>
        </tr>
        </thead>

        <c:if test="${not empty criticismLists}">
            <c:set var="row" value="1" scope="page"/>

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${criticismLists}">

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

<div id="criticismWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        مشاهده اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">
            <div class="row">
                <label for="txtName" style="font-size: 13px; font-weight: 500; color: #707070">نام انتقاد کننده:</label>
                <input disabled id="txtName" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کد ملی :</label>
                <input disabled id="txtNationalCode" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtMobile" style="font-size: 13px; font-weight: 500; color: #707070">تلفن همراه:</label>
                <input disabled id="txtMobile" type="text" class="validate notification-text">

            </div>

            <div class="row">
                <label for="txtSectionTitle" style="font-size: 13px; font-weight: 500; color: #707070">بخش کاری:</label>
                <input disabled id="txtSectionTitle" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtSubject" style="font-size: 13px; font-weight: 500; color: #707070">موضوع:</label>
                <input disabled id="txtSubject" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtDescription" style="font-size: 13px; font-weight: 500; color: #707070"> متن انتقاد
                    :</label>
                <textarea class="materialize-textarea validate notification-text" disabled id="txtDescription"
                          length="4000"></textarea>
            </div>
            <div class="row">
                <label for="txtSubmitDate" style="font-size: 13px; font-weight: 500; color: #707070">تاریخ
                    انتقاد:</label>
                <input disabled id="txtSubmitDate" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtEmail" style="font-size: 13px; font-weight: 500; color: #707070">پست الکترونیک:</label>
                <input disabled id="txtEmail" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtTrackingCode" style="font-size: 13px; font-weight: 500; color: #707070">کد رهگیری
                    :</label>
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
        <a href="#" id="btnErrand" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="criticismWindowToolBarItemClick(this)">
            <img src="/static/icon/ok3.png" class="windowToolbarImage">ارجاع
        </a>
        <a href="#" id="btnStop" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="criticismWindowToolBarItemClick(this)">
            <img src="/static/icon/stop5.png" class="windowToolbarImage">خاتمه
        </a>
        <a href="#" id="btnOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="criticismWindowToolBarItemClick(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
    </div>
</div>

<div id="criticismErrandWindow" class="modal modal-fixed-footer " style="width: 400px">
    <div class="windowHeader">
        ارجاع
    </div>

    <div class="modal-content">
        <div class="col m3 l3"></div>
        <div class="row">
            <div class="row">
                <label style="font-size: 13px; font-weight: 500; color: #707070">انتخاب بخش:</label>
                <select id="ddlSection" onchange="ddlSectionChange();">
                    <option value="" disabled selected>بخش موردنظر انتخاب نمائید</option>
                    <c:if test="${not empty hospitalSectionList}">
                        <c:forEach var="hospitalSectionEntry" items="${hospitalSectionList}">
                            <option value="${hospitalSectionEntry.section.sectionId}">${hospitalSectionEntry.section.title}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            <div class="row">
                <label style="font-size: 13px; font-weight: 500; color: #707070">انتخاب کاربر:</label>
                <select id="ddlUser">
                </select>
            </div>
            <div class="row">
                <div class="col s12">
                    <label for="txtErrandDescription"
                           style="font-size: 13px; font-weight: 500; color: #707070">توضیحات:</label>
                    <textarea id="txtErrandDescription" class="materialize-textarea" length="4000"></textarea>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnErrandOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="criticismErrandWindowToolBarItemClick(this)">
            <img src="/static/icon/ok3.png" class="windowToolbarImage">تایید
        </a>
        <a href="#" id="btnErrandCancel" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="criticismErrandWindowToolBarItemClick(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
    </div>
</div>


<script>
    var criticizeId = 0;

    $(document).ready(function () {
        $(".page-title").text("انتقادات");
        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initWindow();
    });

    function initWindow() {
        $('#criticismWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '4%',
                complete: function () {
                    refreshTable();
                }
            }
        );

        $('#criticismErrandWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '10%',
            }
        );
    }

    function btnViewCriticismClick() {
        if ($('.data-wrapper .selected').length > 0) {

            criticizeId = $('.data-wrapper .selected').data('uid');

            $.ajax({
                type: "POST",
                url: "/ticket/api/findTicketByTicketId",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: criticizeId.toString(),
                success: function (data) {

                    $("#txtName").val(data.name);
                    $("#txtNationalCode").val(data.nationalCode);
                    $("#txtMobile").val(data.mobile);
                    $("#txtSectionTitle").val(data.sectionTitle);
                    $("#txtSubject").val(data.subject);
                    $("#txtDescription").val(data.description);
                    $("#txtEmail").val(data.email);
                    $("#txtTrackingCode").val(data.trackingCode);
                    $("#txtSubmitDate").val(data.trackingCode);
                    $("#txtAttachmentName").val(data.attachList[0].fileName);

                }
            });

            $('#criticismWindow').modal('open');
        } else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
    }

    function refreshTable() {
        $.ajax({
            type: "GET",
            url: "/adCriticism/api/getCriticismData",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $("#tblCriticism tbody tr").remove();

                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {

                        var tr = $("<tr>").attr("data-uid", dataItem.ticketId);

                        var tdCounter = $("<td>").addClass("center").text(index + 1);
                        var tdName = $("<td>").addClass("center").text(dataItem.name);
                        var tdNationalCode = $("<td>").addClass("center").text(dataItem.nationalCode);
                        var tdSubject = $("<td>").addClass("center").text(dataItem.subject);
                        var tdHospitalName = $("<td>").addClass("center").text(dataItem.hospitalName);
                        var tdSectionTitle = $("<td>").addClass("center").text(dataItem.sectionTitle);
                        var tdSubmitDate = $("<td>").addClass("center").text(dataItem.submitDate);
                        var tdSendTypeTitle = $("<td>").addClass("center").text(dataItem.sendTypeTitle);

                        tr.append(tdCounter);
                        tr.append(tdName);
                        tr.append(tdNationalCode);
                        tr.append(tdSubject);
                        tr.append(tdHospitalName);
                        tr.append(tdSectionTitle);
                        tr.append(tdSubmitDate);
                        tr.append(tdSendTypeTitle);

                        $("#tblCriticism tbody").append(tr);
                    });
                }

                $(".data-wrapper tr").click(function () {
                    $(this).addClass('selected').siblings().removeClass("selected");
                });
            }
        });
    }

    function criticismWindowToolBarItemClick(sender) {
        var id = sender.id;

        switch (id) {
            case "btnOk":
                $('#criticismWindow').modal('close');
                break;
            case "btnStop":
                break;
            case "btnErrand":
                clearErrandWindow();
                $('#criticismErrandWindow').modal('open');
                break;
        }
    }

    function criticismErrandWindowToolBarItemClick(sender) {
        var id = sender.id;

        switch (id) {
            case "btnErrandOk":
                saveErrandCriticism();
                break;
            case "btnErrandCancel":
                $('#criticismErrandWindow').modal('close');
                break;
        }
    }

    function saveErrandCriticism() {
        var userId = $("#ddlUser option:selected").val();
        var description = $("#txtErrandDescription").val();

        var dataArray = [];
        var dataItem = {};

        dataItem["criticizeId"] = criticizeId;
        dataItem["userId"] = userId;
        dataItem["description"] = description;

        dataArray.push(dataItem);

        $.ajax({
            type: "POST",
            url: "/adCriticism/api/saveCriticismErrand",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(dataArray),
            success: function (data) {
                if (data) {
                    criticizeId = 0;
                    $('#criticismErrandWindow').modal('close');
                    $('#criticismWindow').modal('close');
                    Materialize.toast('عملیات با موفقیت انجام شد', 4000, 'success-toast');
                } else {
                    Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
                }
            }
        });
    }

    function ddlSectionChange() {
        var sectionId = $("#ddlSection option:selected").val();

        $.ajax({
            type: "POST",
            url: "/adComplain/api/getUserOfSection",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: sectionId.toString(),
            success: function (data) {
                $("#ddlUser option").remove();

                var defaultOption = $("<option>");
                defaultOption.text("کاربر موردنظر را انتخاب نمائید")
                    .prop("disabled", true)
                    .prop("selected", true)
                    .attr("value", "");

                $("#ddlUser").append(defaultOption);

                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {
                        var option = $("<option>");
                        option.text(dataItem.name).attr("value", dataItem.userId);

                        $("#ddlUser").append(option);
                    });
                }
            }
        });
    }

    function clearErrandWindow() {
        $("#ddlSection").val("0");
        $("#ddlUser").prop("disabled", false).val("0");
        $("#txtErrandDescription").val("");
    }
</script>

<%@include file="footer.jsp" %>