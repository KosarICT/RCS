<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@include file="header.jsp" %>

<div class="row">
    <div class="col m3 l3"></div>

    <div class="card col s12 m10 l6 row" style="padding: 30px 60px 30px 60px">


        <div class="row">
            <div class="col s12 m4 l4 right">
                <label for="ddlHospital">انتخاب بیمارستان:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <select id="ddlHospital" onchange="ddlHospitalChange();">
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

            <div class="col s12 m4 l4 right">
                <label for="ddlSection">بخش</label>
            </div>
            <div class="col s12 m8 l8 left">
                <select id='ddlSection'>
                    <option value="0" disabled selected>بخش مورد نظر</option>
                </select>
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="ddlShift">شیفت کاری:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <select id='ddlShift'>
                    <c:if test="${not empty shiftLists}">
                        <option value="0" disabled selected>شیفت مورد نظر</option>
                        <c:forEach var="entry" items="${shiftLists}">
                            <option value="${entry.shiftId}">${entry.title}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="appreciationName">نام:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="appreciationName" type="text" class="validate">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="appreciationFamily">نام خانوادگی:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="appreciationFamily" type="text" class="validate">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="nationalCode">کد ملی:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="10" id="nationalCode" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="telephone">تلفن ثابت:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="12" id="telephone" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="mobile">تلفن همراه:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="12" id="mobile" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="appreciationUserName">نام پرسنل:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="appreciationUserName" type="text" class="validate">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="appreciationUserFamily">نام خانوادگی پرسنل:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="appreciationUserFamily" type="text" class="validate">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="appreciationSubject">موضوع:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="500" id="appreciationSubject" type="text" class="validate">
            </div>

        </div>

        <div class="row">

            <div style="display: inline-block;width: 29%;text-align: right; vertical-align: top;">
                <label for="appreciationDescription">متن قدردانی:</label>
            </div>

            <div class="col s12 m8 l8 left" class="input-field">
                <textarea maxlength="4000" id="appreciationDescription" class="materialize-textarea"></textarea>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="appreciationEmail">ایمیل:</label>
            </div>

            <div class="col s12 m8 l8 left" class="input-field">
                <input maxlength="100" id="appreciationEmail" type="email" class="validate">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label>مستندات :</label>
            </div>

            <div class="col s12 m8 l8 left input-field file-field">
                <div class="btn">
                    <span>بارگذاری مستندات</span>
                    <input type="file">
                </div>
                <div class="file-path-wrapper">
                    <input class="file-path validate" type="text">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right"></div>

            <div class="col s12 m8 l8 left">
                <label>فایل های با پسوند rar. و zip. تا سقف 3072KB مجاز می باشند</label>
            </div>

        </div>

        <div class="row">
            <div class="col s12 m4 l4 right"></div>
            <div class="col s12 m8 l8 left">
                <input type="text" id="txtCaptcha" disabled
                       style="
                   background-image: url(/static/icon/captcha.JPG);
                   text-align:center;
                   border:1px solid #a1a1a1;
                   color: #000;
                   font-size: 17px;
                   display: inline-block;
                   width: auto;
                   direction: ltr;
                    font-weight:bold;"/>
                <a href="#!" class="waves-effect waves-light btn" id="btnrefresh" onclick="DrawCaptcha();"
                   style="display: inline-block">
                    <i class="material-icons">autorenew</i>
                </a>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label>کد امنیتی :</label>
            </div>
            <div class="col s12 m8 l8 left">
                <input type="text" id="txtInput"/>
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
            </div>

            <a class="waves-effect waves-light btn"
               onclick="appreciationSubmit()">ثبت قدردانی

            </a>

        </div>

    </div>

    <div class="col m1 l3"></div>
</div>

<div id="alertWindow" class="modal" style="width: 320px">
    <div class="modal-content" style="text-align: center;vertical-align: middle">
        <br/>
        <br/>
        <label id="lblMessage" style="margin-bottom: 10px"></label>
        <br/>
        <br/>
    </div>
    <div class="divider"></div>
    <div class="modal-footer">
        <a class="waves-effect modal-close waves-light btn-flat" href="#!">تایید</a>
    </div>
</div>


<script>
    var captchaString = "";
    $(window).load(function () {
        DrawCaptcha();
    });

    $(document).ready(function () {
        initWindow();
    });

    function initWindow() {
        $('#alertWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '10%',
                ending_top: '10%',
            }
        );
    }

    function appreciationSubmit() {
        if (!validCaptcha()) {
            Materialize.toast('کد امنیتی بدرستی وارد نشده است .', 3000, 'info-toast');
            $("#txtInput").focus();
            return;
        }

        var hospitalId = $("#ddlHospital option:selected").val();
        var sectionId = $("#ddlSection option:selected").val();
        var shiftId = $("#ddlShift option:selected").val();

        var appreciationName = $("#appreciationName").val();
        var appreciationFamily = $("#appreciationFamily").val();
        var nationalCodeAppreciation = $("#nationalCode").val();
        var telAppreciation = $("#telephone").val();
        var mobileAppreciation = $("#mobile").val();

        var appreciationUserName = $("#appreciationUserName").val();
        var appreciationUserFamily = $("#appreciationUserFamily").val();


        var appreciationSubject = $("#appreciationSubject").val();
        var appreciationDescription = $("#appreciationDescription").val();
        var appreciationEmail = $("#appreciationEmail").val();


        if (hospitalId == 0) {
            $('#ddlHospital').focus();
            Materialize.toast('بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');

        } else if (sectionId == 0) {
            $('#ddlSection').focus();
            Materialize.toast('بخش بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');

        } else if (shiftId == 0) {
            $('#ddlShift').focus();
            Materialize.toast('شیفت بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');

        } else if (appreciationName == "") {
            $("#appreciationName").focus();
            Materialize.toast('نام خود را وارد کنید', 3000, 'info-toast');

        } else if (appreciationFamily == "") {
            $("#appreciationFamily").focus();
            Materialize.toast('نام خانوادگی خود را وارد کنید', 3000, 'info-toast');

        } else if (nationalCodeAppreciation == "") {
            $("#nationalCode").focus();
            Materialize.toast('کد ملی خود را وارد کنید', 3000, 'info-toast');

        } else if (telAppreciation == "") {
            $("#telephone").focus();
            Materialize.toast('تلفن ثابت خود را وارد کنید', 3000, 'info-toast');

        } else if (mobileAppreciation == "") {
            $("#mobile").focus();
            Materialize.toast('موبایل خود را وارد کنید', 3000, 'info-toast');

        } else if (appreciationUserName == "") {
            $("#appreciationUserName").focus();
            Materialize.toast('نام پرسنل مورد نظر را وارد کنید', 3000, 'info-toast');
        } else if (appreciationUserFamily == "") {
            $("#appreciationUserFamily").focus();
            Materialize.toast('نام خانوادگی پرسنل مورد نظر را وارد کنید', 3000, 'info-toast');
        }
        else if (appreciationSubject == "") {
            $("#appreciationSubject").focus();
            Materialize.toast('موضوع قدردانی را تعیین کنید', 3000, 'info-toast');

        } else if (appreciationDescription == "") {
            $("#appreciationDescription").focus();
            Materialize.toast('متن فدردانی را وارد کنید', 3000, 'info-toast');

        } else if (appreciationEmail != "" && !isEmail(appreciationEmail)) {
            $("#appreciationEmail").focus();
            Materialize.toast('پست الکترونیکی بدرستی وارد نشده است', 3000, 'info-toast');

        } else {

            var dataArray = [];
            var dataItem = {};

            dataItem["ticketId"] = 0;
            dataItem["ticketTypeId"] = 1;
            dataItem["hospitalId"] = hospitalId;
            dataItem["sectionId"] = sectionId;
            dataItem["shiftId"] = shiftId;
            dataItem["name"] = appreciationName;
            dataItem["family"] = appreciationFamily;
            dataItem["nationalCode"] = nationalCodeAppreciation;
            dataItem["tel"] = telAppreciation;
            dataItem["mobile"] = mobileAppreciation;
            dataItem["subject"] = appreciationSubject;
            dataItem["description"] = appreciationDescription;
            dataItem["email"] = appreciationEmail;

            dataItem["appreciationUserName"] = appreciationUserName;
            dataItem["appreciationUserFamily"] = appreciationUserFamily;

            /*dataArray.push(dataItem);*/


            if ($('input[type=file]')[0].files.length > 0) {
                var oMyForm = new FormData();

                var file_data = $('input[type=file]')[0].files[0];
                oMyForm.append('file', file_data);
                imageName = file_data.name;

                $.ajax({
                    dataType: 'json',
                    url: "/upload/api/uploadAttachment",
                    data: oMyForm,
                    type: "POST",
                    enctype: 'multipart/form-data',
                    processData: false,
                    contentType: false,
                    success: function (result) {
                        if (result == false) {
                            Materialize.toast('خطا درانجام عملیات', 4000, 'error-toast');

                        } else {
                            dataItem["fileName"] = result.fileName;
                            dataArray.push(dataItem);
                            saveAppreciation(dataArray);
                        }
                    },
                    error: function (result) {
                        alert("error");
                    }
                });
            } else {
                dataItem["fileName"] = "";
                dataArray.push(dataItem);
                saveAppreciation(dataArray);
            }


        }
    }

    function DrawCaptcha() {
        document.getElementById('txtInput').value = "";

        var a = Math.ceil(Math.random() * 10) + '';
        var b = Math.ceil(Math.random() * 10) + '';
        var c = Math.ceil(Math.random() * 10) + '';
        var d = Math.ceil(Math.random() * 10) + '';
        var e = Math.ceil(Math.random() * 10) + '';
        var f = Math.ceil(Math.random() * 10) + '';
        var g = Math.ceil(Math.random() * 10) + '';
        var code = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' ' + f + ' ' + g;
        captchaString = code;
        document.getElementById("txtCaptcha").value = code
    }

    function validCaptcha() {
        var str1 = removeSpaces(document.getElementById('txtCaptcha').value);
        var str2 = removeSpaces(document.getElementById('txtInput').value);

        if (str1 != removeSpaces(captchaString)) {
            return false;
        } else {
            if (str1 == str2) return true;
            return false;
        }


    }

    function removeSpaces(string) {
        return string.split(' ').join('');

    }

    function ddlHospitalChange() {
        var hospitalId = $("#ddlHospital option:selected").val();

        debugger;
        $.ajax({
            type: "POST",
            url: "/offer/api/getSection",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: hospitalId.toString(),
            success: function (data) {
                $("#ddlSection option").remove();

                var defaultOption = $("<option>");
                defaultOption.text("بخش موردنظر را انتخاب نمائید")
                    .prop("disabled", true)
                    .prop("selected", true)
                    .attr("value", "");

                $("#ddlSection").append(defaultOption);
                debugger;
                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {
                        var option = $("<option>");
                        option.text(dataItem.title).attr("value", dataItem.sectionId);

                        $("#ddlSection").append(option);
                    });
                }
            }
        });
    }

    function isEmail(email) {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }

    function saveAppreciation(dataArray) {

        $.ajax({
            type: "POST",
            url: "/ticket/api/saveTicket",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(dataArray),
            success: function (data) {
                debugger;
                if (data > 0) {
                    $("#lblMessage").text("پیام شما با موفقیت ثبت شد");
                    $('#alertWindow').modal('open');
                } else {
                    Materialize.toast('خطا درانجام عملیات', 4000, 'error-toast');
                }
            }
        });
    }

</script>

<%@include file="footer.jsp" %>
