<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<style>
    .textColor {
        font-size: 13px;
        color: #5a5a5a;
    }

    .modal {
        max-height: 90% !important;
        top: 5% !important;
        width: 500px;
    }
</style>

<div id="mainSection" class="row">
    <table id="tblComplaintType" class="bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">عنوان</th>
            <th class="center">زمان پاسخگویی</th>
            <th class="center">توضیحات</th>
            <th class="center"></th>
        </tr>
        </thead>

        <c:if test="${not empty lists}">
            <c:set var="row" value="1" scope="page"/>

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${lists}">
                <tr>
                    <td class="center" style="display: none">${entry.complaintTypeId}</td>
                    <td class="center counter"><c:out value="${row}"/></td>
                    <td class="center">${entry.title}</td>
                    <td class="center">${entry.responceTime}</td>
                    <td class="center">${entry.description}</td>
                    <td class="center">
                        <a id="btnEdit_${entry.complaintTypeId}" href="#" class="mainTextColor"
                           style="margin-left: 5px"
                           onclick="getComplaintTypeForEdit(this);">
                            <img src="/static/icon/edit1.png">
                        </a>
                        <a id="btnDelete_${entry.complaintTypeId}" href="#" class="mainTextColor"
                           onclick="showConfirm(this);">
                            <img src="/static/icon/cancel2.png">
                        </a>
                    </td>
                </tr>

                <c:set var="row" value="${row + 1}" scope="page"/>
            </c:forEach>
            </tbody>

        </c:if>
    </table>
</div>

<div>
    <a class="btn-floating btn-large waves-effect waves-light slideColor " href="#" onclick="showComplaintTypeWindow();"
       style="float: left; margin-left: 4%"><i class="material-icons">add</i></a>
</div>


<div id="complaintTypeWindow" class="modal modal-fixed-footer">
    <div class="windowHeader">
        اضافه/ویرایش نوع شکایت
    </div>
    <div class="modal-content">
        <div class="row">
            <div class="col m2 l1"></div>
            <div class="col s12 m8 l10">
                <div class="input-field">
                    <input placeholder="عنوان شکایت" id="txtTitle" type="text" autocomplete="false" autofocus>
                </div>

                <div class="input-field ">
                    <textarea id="txtDescription" placeholder="توضیحات" class="materialize-textarea"
                              length="4000"></textarea>
                </div>
                <div>
                    <input maxlength="10" placeholder="زمان پاسخگویی" id="responsTime" type="text" class="validate"
                           onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
                </div>
            </div>
            <div class="col m2 l1"></div>
        </div>

    </div>
    <div class="modal-footer" style="background-color: #4f6789">
        <a href="#" class="modal-action  waves-effect waves-light btn-flat white-text" onclick="saveComplaintType();"><img
                src="../static/icon/ok3.png" style="margin-left: 12px"/>تایید</a>
        <a href="#" class="modal-action modal-close waves-effect waves-light btn-flat white-text"><img
                src="../static/icon/cancel2.png" style="margin-left: 12px"/>انصراف</a>
    </div>
</div>

<div id="confirmWindow" class="modal" style="width: 290px">
    <div class="modal-content">
        <p id="messageDialog"></p>
    </div>
    <div class="divider"></div>
    <div class="modal-footer">
        <input type="button" class=" modal-action modal-close btn-flat" value="تایید" onclick="btnConfirmClick()">
        <input type="button" class=" modal-action modal-close btn-flat" value="انصراف">
    </div>
</div>


<script>
    var complaintTypeId = 0;

    $(document).ready(function () {
        $(".page-title").text("نوع شکایات");

        var height = screen.height - 270;
        $("#mainSection").css("height", height + "px");

        initWindow();

        $(".row").persiaNumber();

        $('.fixed-action-btn').openFAB();
    });

    function initWindow() {
        $('#confirmWindow').modal({
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

        $('#complaintTypeWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '2%',
                ending_top: '1%',
            }
        );
    }

    function showComplaintTypeWindow() {
        clearForm();
        $('#complaintTypeWindow').modal('open');
        $("#txtTitle").focus();
    }

    function getComplaintTypeForEdit(sender) {
        complaintTypeId = sender.id.split("_")[1];

        $.ajax({
            type: "POST",
            url: "/complaintType/api/getComplainTypeForEdit",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: complaintTypeId.toString(),
            success: function (data) {
                var complaintType = data[0];

                $("#txtTitle").val(complaintType.title);
                $("#txtDescription").val(complaintType.description);
                $("#responsTime").val(complaintType.responsTime);

                $('#complaintTypeWindow').modal('open');
            }
        });
    }

    function saveComplaintType() {
        var title = $("#txtTitle").val();
        var description = $("#txtDescription").val();
        var responsTime = $("#responsTime").val();

        if (title == "") {
            Materialize.toast('عنوان را وارد نمائید.', 4000, 'info-toast');
        } else {

            var dataArray = [];
            var dataItem = {};

            dataItem["complaintTypeId"] = complaintTypeId;
            dataItem["title"] = title;
            dataItem["description"] = description;
            dataItem["responsTime"] = responsTime;

            dataArray.push(dataItem);

            $.ajax({
                type: "POST",
                url: "/complaintType/api/saveComplainType",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: JSON.stringify(dataArray),
                success: function (data) {
                    if (data) {
                        window.location.reload();
                    } else {
                        Materialize.toast('خطا در انجام عملیات', 4000, 'error-toast');
                    }
                }
            });
        }
    }

    function clearForm() {
        complaintTypeId = 0;

        $("#txtTitle").val("");
        $("#txtDescription").val("");
        $("#responsTime").val("")
    }

    function showConfirm(sender) {
        complaintTypeId = sender.id.split("_")[1];

        $('#messageDialog').text('آیا برای حذف این کاربر اطمینان دارید؟');
        $('#confirmWindow').modal('open');
    }

    function btnConfirmClick() {
        $.ajax({
            type: "POST",
            url: "/complaintType/api/deleteComplainType",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: complaintTypeId.toString(),
            success: function (data) {
                if (data) {
                    window.location.reload();
                } else {
                    Materialize.toast('خطا در انجام عملیات', 4000, 'error-toast');
                }
            }
        });
    }
</script>

<%@include file="footer.jsp" %>
