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
    }
</style>

<div id="mainSection" class="row">
    <table id="tblHospital" class="bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">نام</th>
            <th class="center">شماره پیامک</th>
            <th class="center">ایمیل</th>
            <th class="center">ادرس وبسایت</th>
        </tr>
        </thead>

        <c:if test="${not empty lists}">
            <c:set var="row" value="1" scope="page"/>

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${lists}">
                <tr>
                    <td class="center" style="display: none">${entry.hospitalId}</td>
                    <td class="center counter"><c:out value="${row}"/></td>
                    <td class="center">${entry.name}</td>
                    <td class="center">${entry.smsNumber}</td>
                    <td class="center">${entry.email}</td>
                    <td class="center">${entry.url}</td>
                    <td class="center">
                        <a id="btnEdit_${entry.hospitalId}" href="#" class="mainTextColor"
                           style="margin-left: 5px"
                           onclick="getHospitalForEdit(this);">
                            <img src="/static/icon/edit1.png">
                        </a>
                        <a id="btnDelete_${entry.hospitalId}" href="#" class="mainTextColor"
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
    <a class="btn-floating btn-large waves-effect waves-light slideColor " href="#" onclick="showHospitalWindow();"
       style="float: left; margin-left: 4%"><i class="material-icons">add</i></a>
</div>


<div id="hospitalWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        اضافه/ویرایش بیمارستان
    </div>
    <div class="modal-content">

        <div class="row">
            <div class="col s12 m6 l6 right">
                <%--<div class="input-field">--%>
                    <input placeholder="نام بیمارستان" id="txtHospitalName" type="text" autocomplete="false" autofocus>
                <%--</div>--%>

                <div class="input-field">
                    <input placeholder="شماره خط پیام رسان" id="txtSmsNumber" type="text" class="validate"
                           onkeypress="return event.charCode >= 48 && event.charCode <=57">
                </div>

                <div class="input-field">
                    <input placeholder="پست الکترونیک" id="txtEmail" type="email" class="validate">
                </div>

                <div class="input-field ">
                    <input placeholder="تارنمای الکترونیکی" id="txtUrl" type="text">
                </div>

                <div class="input-field ">
                    <textarea id="txtAddress" class="materialize-textarea" placeholder="آدرس" length="4000"></textarea>
                </div>

                <div class="input-field ">
                    <textarea id="txtDescription" placeholder="توضیحات" class="materialize-textarea"
                              length="4000"></textarea>
                </div>

            </div>
            <div class="col s12 m6 l6 left">
                <div class="center" style="margin-bottom: 10px">
                    <img style="width: 64px; height: 64px" class="card" id="imgHospital"
                         src="../static/hospitalImage/heart.png">
                </div>
                <div>
                    <div class="file-field input-field">
                        <div class="btn">
                            <span>بارگذاری فایل</span>
                            <input type="file" id="hospitalImageUploader" accept=".jpg,.jpeg,.png">
                        </div>
                        <div class="file-path-wrapper">
                            <input class="file-path validate" type="text">
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="modal-footer" style="background-color: #4f6789">
        <a href="#" class="modal-action  waves-effect waves-light btn-flat white-text" onclick="saveHospital();"><img
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
    var hospitalId = 0;
    var imageName = "heart.png";

    $(document).ready(function () {
        $(".page-title").text("بیمارستان ها");
        var height = screen.height - 270;
        $("#mainSection").css("height", height + "px");

        initWindow();

        $(".row").persiaNumber();

        $('.fixed-action-btn').openFAB();

        $("#hospitalImageUploader").change(function () {
            processUpload();
        });
    });

    function initWindow() {
        $('#confirmWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '1%',
                ending_top: '1%',
                complete: function () {
                    refreshTable();
                }
            }
        );

        $('#hospitalWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '2%',
                ending_top: '1%',
            }
        );
    }

    function refreshTable() {
        $.ajax({
            type: "GET",
            url: "/hospital/api/getData",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $("#tblHospital tbody tr").remove();

                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {

                        var tr = $("<tr>").attr("data-uid", dataItem.hospitalId);

                        var tdHospitalId = $("<td>").addClass("center").text(dataItem.hospitalId).css("display", "none");
                        var tdCounter = $("<td>").addClass("center").text(index + 1);
                        var tdName = $("<td>").addClass("center").text(dataItem.name);
                        var tdSMSNumber = $("<td>").addClass("center").text(dataItem.smsNumber);
                        var tdEmail = $("<td>").addClass("center").text(dataItem.email);
                        var tdURL = $("<td>").addClass("center").text(dataItem.url);

                        tr.append(tdHospitalId);
                        tr.append(tdCounter);
                        tr.append(tdName);
                        tr.append(tdSMSNumber);
                        tr.append(tdEmail);
                        tr.append(tdURL);

                        $("#tblHospital tbody").append(tr);
                    });
                }

                $(".data-wrapper tr").click(function () {
                    $(this).addClass('selected').siblings().removeClass("selected");
                });
            }
        });
    }

    function showHospitalWindow() {
        clearForm();
        $('#hospitalWindow').modal('open');
        $("#txtHospitalName").focus();
    }

    function getHospitalForEdit(sender) {
        hospitalId = sender.id.split("_")[1];

        $.ajax({
            type: "POST",
            url: "/hospital/api/getHospitalForEdit",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: hospitalId.toString(),
            success: function (data) {
                var hospital = data[0];

                $("#txtHospitalName").val(hospital.name);
                $("#txtSmsNumber").val(hospital.smsNumber);
                $("#txtEmail").val(hospital.email);
                $("#txtUrl").val(hospital.url);
                $("#txtAddress").val(hospital.address);
                $("#txtDescription").val(hospital.description);

                $("#imgHospital").attr("src", "/static/hospitalImage/" + hospital.imageName);

                imageName = hospital.imageName;

                $('#hospitalWindow').modal('open');
            }
        });
    }

    function saveHospital() {
        var hospitalName = $("#txtHospitalName").val();
        var smsNumber = $("#txtSmsNumber").val();
        var email = $("#txtEmail").val();
        var url = $("#txtUrl").val();
        var address = $("#txtAddress").val();
        var description = $("#txtDescription").val();

        if (hospitalName == "") {
            Materialize.toast('نام مرکز درمانی را وارد نمائید.', 4000, 'info-toast');
        } else {

            var dataArray = [];
            var dataItem = {};

            dataItem["hospitalId"] = hospitalId;
            dataItem["hospitalName"] = hospitalName;
            dataItem["smsNumber"] = smsNumber;
            dataItem["email"] = email;
            dataItem["url"] = url;
            dataItem["address"] = address;
            dataItem["description"] = description;
            dataItem["imageName"] = imageName;

            dataArray.push(dataItem);

            $.ajax({
                type: "POST",
                url: "/hospital/api/saveHospital",
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
        hospitalId = 0;

        $("#txtHospitalName").val("");
        $("#txtSmsNumber").val("");
        $("#txtEmail").val("");
        $("#txtUrl").val("");
        $("#txtAddress").val("");
        $("#txtDescription").val("");

        imageName = "heart.png";
    }

    function showConfirm(sender) {
        hospitalId = sender.id.split("_")[1];

        $('#messageDialog').text('آیا برای حذف این کاربر اطمینان دارید؟');
        $('#confirmWindow').modal('open');
    }

    function btnConfirmClick() {
        $.ajax({
            type: "POST",
            url: "/hospital/api/deleteHospital",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: hospitalId.toString(),
            success: function (data) {
                if (data) {
                    window.location.reload();
                } else {
                    Materialize.toast('خطا در انجام عملیات', 4000, 'error-toast');
                }
            }
        });
    }

    function processUpload() {
        var oMyForm = new FormData();
        var file_data = $('input[type=file]')[0].files[0];

        oMyForm.append('file', file_data);
        imageName = file_data.name;

        $.ajax({
            dataType: 'json',
            url: "/hospital/api/uploadImage",
            data: oMyForm,
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (result) {
                if (result) {
                    var reader = new FileReader();
                    reader.onload = function () {
                        $('#imgHospital').attr("src", reader.result);
                    };
                    reader.readAsDataURL($('input[type=file]')[0].files[0]);
                }
            },
            error: function (result) {
                alert("error");
            }
        });
    }
</script>

<%@include file="footer.jsp" %>
