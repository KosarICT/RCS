<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<style>
    .modal select {
        display: block;
    }

    table {
        font-size: 13px !important;
    }

    tbody tr {
        height: 59px !important;
    }

    tr td {
        text-align: right !important;
        border-bottom: 1px solid #c8c8c8 !important;
    }

    #grvComplaint th input[type=text]:focus:not([readonly]) {
        border-bottom: none !important;
        box-shadow: none !important;
    }
</style>

<div class="row">
    <%--<nav>
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

    <table id="tblComplain" class="left bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">شکایت کننده</th>
            <th class="center">بیمار</th>
            <th class="center">کد ملی</th>
            <th class="center">بیمارستان</th>
            <th class="center">نوع شکایت</th>
            <th class="center">تاریخ</th>
            <th class="center">طریقه ارتباط</th>
        </tr>
        </thead>

        <c:if test="${not empty lists}">
            <c:set var="row" value="1" scope="page"/>

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${lists}">

                <tr data-uid="${entry.complain.complainId}">
                    <td class="center" style="display: none">${entry.complain.complainId}</td>
                    <td class="center counter"><c:out value="${row}"/></td>
                    <td class="center">${entry.complain.complainant.title}</td>
                    <td class="center">${entry.complain.firstName} ${entry.complain.lastName}</td>
                    <td class="center">${entry.complain.nationalCode}</td>
                    <td class="center">${entry.complain.hospital.name}</td>
                    <td class="center">${entry.complain.complaintType.title}</td>
                    <td class="center">${entry.complain.submitDate}</td>
                    <td class="center">${entry.complain.sendType.title}</td>
                </tr>

                <c:set var="row" value="${row + 1}" scope="page"/>
            </c:forEach>
            </tbody>

        </c:if>
    </table>--%>

    <div class="k-rtl">
        <div id="grvComplaint"></div>
    </div>
</div>

<div id="complainWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        مشاهده اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">
            <div class="row">
                <label for="txtComplainantTitle" style="font-size: 13px; font-weight: 500; color: #707070">شکایت
                    کننده:</label>
                <input disabled id="txtComplainantTitle" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtName" style="font-size: 13px; font-weight: 500; color: #707070">نام بیمار:</label>
                <input disabled id="txtName" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کدملی
                    بیمار:</label>
                <input disabled id="txtNationalCode" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtTel" style="font-size: 13px; font-weight: 500; color: #707070">تلفن تماس:</label>
                <input disabled id="txtTel" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtMobile" style="font-size: 13px; font-weight: 500; color: #707070">تلفن همراه:</label>
                <input disabled id="txtMobile" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtRelationName" style="font-size: 13px; font-weight: 500; color: #707070">نام همراه
                    بیمار:</label>
                <input disabled id="txtRelationName" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtRelationNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کدملی
                    همراه بیمار:</label>
                <input disabled id="txtRelationNationalCode" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txComplaintTypeTitle" style="font-size: 13px; font-weight: 500; color: #707070">نوع
                    شکایت:</label>
                <input disabled id="txComplaintTypeTitle" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtShiftTitle" style="font-size: 13px; font-weight: 500; color: #707070">شیفت کاری:</label>
                <input disabled id="txtShiftTitle" type="text" class="validate notification-text">
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
                <label for="txtDescription" style="font-size: 13px; font-weight: 500; color: #707070">توضیحات:</label>
                <textarea class="materialize-textarea validate notification-text" disabled id="txtDescription"
                          length="4000"></textarea>
            </div>
            <div class="row">
                <label for="txtSubmitDate" style="font-size: 13px; font-weight: 500; color: #707070">تاریخ
                    ارسال:</label>
                <input disabled id="txtSubmitDate" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtEmail" style="font-size: 13px; font-weight: 500; color: #707070">پست الکترونیک:</label>
                <input disabled id="txtEmail" type="text" class="validate notification-text">
            </div>
        </div>
        <div class="row"></div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnErrand" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="complaintWindowToolBarItemClick(this)">
            <img src="/static/icon/ok3.png" class="windowToolbarImage">ارجاع
        </a>
        <a href="#" id="btnStop" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="complaintWindowToolBarItemClick(this)">
            <img src="/static/icon/stop5.png" class="windowToolbarImage">خاتمه
        </a>
        <a href="#" id="btnOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="complaintWindowToolBarItemClick(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
    </div>
</div>

<div id="errandWindow" class="modal modal-fixed-footer " style="width: 400px">
    <div class="windowHeader">
        ارجاع
    </div>

    <div class="modal-content">
        <div class="col m3 l3"></div>
        <div class="row">
            <div class="row">
                <label style="font-size: 13px; font-weight: 500; color: #707070">انتخاب بخش:</label>
                <%--<select id="ddlSection" onchange="ddlSectionChange();">--%>
                <%--<option value="" disabled selected>بخش موردنظر انتخاب نمائید</option>--%>
                <%--<c:if test="${not empty hospitalSectionList}">--%>
                <%--<c:forEach var="hospitalSectionEntry" items="${hospitalSectionList}">--%>
                <%--<option value="${hospitalSectionEntry.section.sectionId}">${hospitalSectionEntry.section.title}</option>--%>
                <%--</c:forEach>--%>
                <%--</c:if>--%>
                <%--</select>--%>
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
           onclick="errandWindowToolBarItemClick(this)">
            <img src="/static/icon/ok3.png" class="windowToolbarImage">تایید
        </a>
        <a href="#" id="btnErrandCancel" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="errandWindowToolBarItemClick(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
    </div>
</div>

<script>
    var complainId = 0;

    $(document).ready(function () {
        $(".page-title").text("شکایات");
        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initGrid();
        initWindow();
    });

    function initGrid() {
        $("#grvComplaint").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "/adComplain/api/getData",
                        type: "GET",
                        contentType: "application/json",
                        dataType: "json",
                    }
                },
            },
            sortable: {
                mode: "single",
                allowUnsort: false
            },
            toolbar: [{name: "excel", text: "دریافت فایل اکسل"}],
            excel: {
                fileName: "شکایات.xlsx",
                filterable: true
            },
            filterable: {
                mode: "row"
            },
            selectable: "single",
            columns: [
                {field: "complainId", title: "UserId", hidden: true},
                {
                    field: "complainantTitle", title: "شکایت کننده", width: "100px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "name", title: "بیمار", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "nationalCode", title: "کدملی", width: "120px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "hospitalName", title: "بیمارستان", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "complaintTypeTitle", title: "نوع شکایت", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "sendTypeTitle", title: "طریقه ارتباط", width: "100px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "submitDate", title: "تاریخ", width: "100px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {command: {text: "مشاهده", click: btnViewClick}, title: "&nbsp;", width: "120px"}
            ]
        });
    }

    function initWindow() {
        $('#complainWindow').modal({
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

        $('#errandWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '10%',
            }
        );
    }

    /*    function btnViewClick() {
     if ($('.data-wrapper .selected').length > 0) {

     complainId = $('.data-wrapper .selected').data('uid');

     $.ajax({
     type: "POST",
     url: "/adComplain/api/findComplainById",
     contentType: "application/json; charset=utf-8",
     dataType: 'json',
     data: complainId.toString(),
     success: function (data) {
     var dataItem = data[0];

     $("#txtComplainantTitle").val(dataItem.complainantTitle);
     $("#txtName").val(dataItem.name);
     $("#txtNationalCode").val(dataItem.nationalCode);
     $("#txtTel").val(dataItem.tel);
     $("#txtMobile").val(dataItem.mobile);
     $("#txtRelationName").val("");
     $("#txtRelationNationalCode").val("");
     $("#txComplaintTypeTitle").val(dataItem.complaintTypeTitle);
     $("#txtShiftTitle").val(dataItem.shiftTitle);
     $("#txtSectionTitle").val(dataItem.sectionTitle);
     $("#txtSubject").val(dataItem.subject);
     $("#txtDescription").val(dataItem.description);
     $("#txtSubmitDate").val(dataItem.submitDate);
     $("#txtEmail").val(dataItem.email);
     }
     });

     $('#complainWindow').modal('open');
     } else {
     Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
     }
     }*/

    function btnViewClick(e) {
        var grid = $("#grvComplaint").data("kendoGrid");
        var dataItem = grid.dataItem($(e.currentTarget).closest("tr"));

        complainId = dataItem.complainId;

        $.ajax({
            type: "POST",
            url: "/adComplain/api/findComplainById",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: complainId.toString(),
            success: function (data) {
                var dataItem = data[0];

                $("#txtComplainantTitle").val(dataItem.complainantTitle);
                $("#txtName").val(dataItem.name);
                $("#txtNationalCode").val(dataItem.nationalCode);
                $("#txtTel").val(dataItem.tel);
                $("#txtMobile").val(dataItem.mobile);
                $("#txtRelationName").val("");
                $("#txtRelationNationalCode").val("");
                $("#txComplaintTypeTitle").val(dataItem.complaintTypeTitle);
                $("#txtShiftTitle").val(dataItem.shiftTitle);
                $("#txtSectionTitle").val(dataItem.sectionTitle);
                $("#txtSubject").val(dataItem.subject);
                $("#txtDescription").val(dataItem.description);
                $("#txtSubmitDate").val(dataItem.submitDate);
                $("#txtEmail").val(dataItem.email);

                $('#complainWindow').modal('open');
            }
        });
    }

    function refreshTable() {
        $.ajax({
            type: "GET",
            url: "/adComplain/api/getData",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $("#tblComplain tbody tr").remove();

                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {

                        var tr = $("<tr>").attr("data-uid", dataItem.complainId);

                        var tdComplainId = $("<td>").addClass("center").text(dataItem.complainId).css("display", "none");
                        var tdCounter = $("<td>").addClass("center").text(index + 1);
                        var tdComplainantTitle = $("<td>").addClass("center").text(dataItem.complainantTitle);
                        var tdName = $("<td>").addClass("center").text(dataItem.name);
                        var tdNationalCode = $("<td>").addClass("center").text(dataItem.nationalCode);
                        var tdHospitalName = $("<td>").addClass("center").text(dataItem.hospitalName);
                        var tdComplaintTypeTitle = $("<td>").addClass("center").text(dataItem.complaintTypeTitle);
                        var tdSubmitDate = $("<td>").addClass("center").text(dataItem.submitDate);
                        var tdSendTypeTitle = $("<td>").addClass("center").text(dataItem.sendTypeTitle);

                        tr.append(tdComplainId);
                        tr.append(tdCounter);
                        tr.append(tdComplainantTitle);
                        tr.append(tdName);
                        tr.append(tdNationalCode);
                        tr.append(tdHospitalName);
                        tr.append(tdComplaintTypeTitle);
                        tr.append(tdSubmitDate);
                        tr.append(tdSendTypeTitle);

                        $("#tblComplain tbody").append(tr);
                    });
                }

                $(".data-wrapper tr").click(function () {
                    $(this).addClass('selected').siblings().removeClass("selected");
                });
            }
        });
    }

    function complaintWindowToolBarItemClick(sender) {
        var id = sender.id;

        switch (id) {
            case "btnOk":
                $('#complainWindow').modal('close');
                break;
            case "btnStop":
                break;
            case "btnErrand":
                $('#errandWindow').modal('open');
                break;
        }
    }

    function errandWindowToolBarItemClick(sender) {
        var id = sender.id;

        switch (id) {
            case "btnErrandOk":
                saveErrandComplain();
                break;
            case "btnErrandCancel":
                $('#errandWindow').modal('close');
                break;
        }
    }

    function saveErrandComplain() {
        var userId = $("#ddlUser option:selected").val();
        var description = $("#txtErrandDescription").val();

        var dataArray = [];
        var dataItem = {};

        dataItem["complainId"] = complainId;
        dataItem["userId"] = userId;
        dataItem["description"] = description;

        dataArray.push(dataItem);

        $.ajax({
            type: "POST",
            url: "/adComplain/api/saveComplainErrand",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(dataArray),
            success: function (data) {
                if (data) {
                    complainId = 0;
                    $('#errandWindow').modal('close');
                    $('#complainWindow').modal('close');
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
</script>

<%@include file="footer.jsp" %>