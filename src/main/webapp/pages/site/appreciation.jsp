<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@include file="header.jsp" %>

<div class="row">
    <div class="col m3 l3"></div>

    <div class="card col s12 m10 l6 row" style="padding: 30px 60px 30px 60px">


        <div class="row">
            <div class="col s12 m4 l4 right marginTop">
                <label for="ddlHospital">انتخاب بیمارستان:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <select id="ddlHospital" onchange="ddlHospitalChange();" disabled style="font-weight: 400; color: #989898">
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
                <label for="ddlSection">بخش:</label>
            </div>
            <div class="col s12 m8 l8 left">
                <select id='ddlSection'>
                </select>
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="ddlShift">شیفت کاری:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <select id='ddlShift'>

                </select>
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="appreciationName">نام:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="appreciationName" placeholder="نام:علی" type="text" class="validate" lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="appreciationFamily">نام خانوادگی:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="appreciationFamily" placeholder="نام خانوادگی:رضایی" type="text" class="validate" lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="nationalCode">کد ملی:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="10" id="nationalCode" placeholder="کد ملی: 09212678392" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="telephone">تلفن ثابت:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="12" id="telephone" type="text" class="validate" placeholder="تلفن ثابت: 02188468290"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="mobile">تلفن همراه:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="12" id="mobile" type="text" class="validate" placeholder="تلفن همراه:09121187653"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="appreciationUserName">نام پرسنل:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="appreciationUserName" type="text" class="validate persian-digit"
                       placeholder="نام پرسنل: احمد" lang="fa-IR"/>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="appreciationUserFamily">نام خانوادگی پرسنل:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="appreciationUserFamily" placeholder="نام خانوادگی پرسنل: شریفی" type="text" class="translate" lang="fa-IR"/>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="appreciationSubject">موضوع:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="500" id="appreciationSubject" type="text" placeholder="موضوع" class="translate" lang="fa-IR"/>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="appreciationDescription">متن قدردانی:</label>
            </div>

            <div class="col s12 m8 l8 left" class="input-field">
                <textarea maxlength="4000" id="appreciationDescription" placeholder="متن قدردانی" class="materialize-textarea"
                          lang="fa-IR"></textarea>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="appreciationEmail">ایمیل:</label>
            </div>

            <div class="col s12 m8 l8 left" class="input-field">
                <input maxlength="100" id="appreciationEmail" type="email" placeholder="ایمیل:ali@gmail.com" class="validate">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label>مستندات :</label>
            </div>

            <div class="col s12 m8 l8 left input-field file-field">
                <div class="btn" style="width: 48%;float: none">
                    <span>بارگذاری مستندات</span>
                    <input type="file">
                </div>
                <div class="file-path-wrapper" style="width:50%;float: right">
                    <input class="file-path validate" type="text">
                </div>
                <div style="width: 2%;float: left">
                    <a href="#" class="modal-action  waves-effect waves-light white-text" onclick="addAttachement()"
                       style="margin-top: 17px;margin-right: 14px" ><img src="../static/icon/add.png" style="margin-left: 12px"/></a>
                </div>
            </div>

            <div id="attachmentDive" ></div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop"></div>

            <div class="col s12 m8 l8 left">
                <label>فایل های با پسوند rar. و zip. تا سقف 3072KB مجاز می باشند</label>
            </div>

        </div>

        <div class="row">
            <div class="col s12 m4 l4 right marginTop"></div>

            <div class="col s12 m8 l8 left">
                <input type="text" id="txtCaptcha" disabled
                       style="
                   background-image: url(/static/icon/captcha.JPG);
                   text-align:center;
                   border:1px solid #a1a1a1;
                   color: #000 !important;
                   font-size: 17px;
                   display: inline-block;
                   width: 73% !important;
                   direction: ltr;
                    font-weight:bold;"/>
                <a href="#!" class="waves-effect waves-light btn" id="btnrefresh" onclick="DrawCaptcha();"
                   style="display: inline-block">
                    <i class="material-icons">autorenew</i>
                </a>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label>کد امنیتی :</label>
            </div>
            <div class="col s12 m8 l8 left">
                <input type="text" id="txtInput"/>
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
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
        var hospitalName = "${hospitalImage}";
        var webUrl = "${webUrl}";
        var hospitalId = "${hospitalId}";
        $("#webURL").val(webUrl);

        $("#hospitalUrl").attr("href", $("#webURL").val());

        $("#ddlHospital").val(hospitalId);
        $(".brand-logo").text($("#ddlHospital option:selected").text());
        $("#imgHospital").attr("src", "/static/hospitalImage/" + hospitalName);
        ddlHospitalChange();

        initWindow();

        $("#appreciationName, #appreciationFamily, #appreciationUserName,#appreciationUserFamily, #appreciationSubject, #appreciationDescription").farsiInput();
    });

    $('.translate').text(function(i, v) {
        var chars = v.split('');
        for (var i = 0; i < chars.length; i++) {
            if (/\d/.test(chars[i])) {
                chars[i] = arabicNumbers[chars[i]];
            }
        }
        return chars.join('');
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

        } else if(!checkMelliCode(nationalCodeAppreciation)){
            $("#nationalCode").focus();
            Materialize.toast('کد ملی خود را صحیح وارد کنید', 3000, 'info-toast');
        } else if (telAppreciation == "") {
            $("#telephone").focus();
            Materialize.toast('تلفن ثابت خود را وارد کنید', 3000, 'info-toast');

        } else if (telAppreciation.length ==111){
            $("#telephone").focus();
            Materialize.toast('تلفن خود را صحیح وارد کنید', 3000, 'info-toast');
        } else if (mobileAppreciation == "") {
            $("#mobile").focus();
            Materialize.toast('موبایل خود را وارد کنید', 3000, 'info-toast');

        }  else if(mobileAppreciation.length!=11 && !isMobile(mobileAppreciation)){
            $("#mobile").focus();
            Materialize.toast('موبایل خود را صحیح وارد کنید', 3000, 'info-toast');
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

    function addAttachement() {
        var attachmentDive=$("#attachmentDive");
        var mainDiv=$("<div>");
        mainDiv.addClass("col s12 m8 l8 file-field input-field left");
        var btnDiv=$("<div>");
        btnDiv.addClass("btn");
        btnDiv.css("float","none").css("width","48%");
        var btnSpan=$("<span>");
        btnSpan.text("بارگذاری مستندات");
        btnDiv.append(btnSpan);
        var btnInput=$("<input>");
        btnInput.attr("type","file");
        btnDiv.append(btnInput);

        var validationDiv=$("<div>");
        validationDiv.addClass("file-path-wrapper");
        validationDiv.css("float","right").css("width","50%");
        var validationInput=$("<input>");
        validationInput.attr("type","text");
        validationInput.addClass("file-path validate");
        validationDiv.append(validationInput);

        var btnAddDiv=$("<div>").css("width","2%").css("float","left");
        var btnAdd=$("<a>").css("margin-right","14px").css("margin-top","17px");
        btnAdd.addClass("modal-action  waves-effect waves-light white-text");
        btnAdd.attr("href","#");
        btnAdd.attr("onClick","addAttachement()")
        var imgbtn=$("<img>").css("margin-left","12px");
        imgbtn.attr("src","../static/icon/add.png")
        btnAdd.append(imgbtn);
        btnAddDiv.append(btnAdd);

        mainDiv.append(btnDiv);
        mainDiv.append(validationDiv);
        mainDiv.append(btnAddDiv);
        attachmentDive.append(mainDiv);
    }


    function checkMelliCode(meli_code){
        if (meli_code.length == 10)
        {
            if(meli_code=="1111111111" ||
                meli_code=="0000000000" ||
                meli_code=="2222222222" ||
                meli_code=="3333333333" ||
                meli_code=="4444444444" ||
                meli_code=="5555555555" ||
                meli_code=="6666666666" ||
                meli_code=="7777777777" ||
                meli_code=="8888888888" ||
                meli_code=="9999999999" )
            {
                return false;
            }
            var c = parseInt(meli_code.charAt(9));
            var n = parseInt(meli_code.charAt(0))*10 +
                parseInt(meli_code.charAt(1))*9 +
                parseInt(meli_code.charAt(2))*8 +
                parseInt(meli_code.charAt(3))*7 +
                parseInt(meli_code.charAt(4))*6 +
                parseInt(meli_code.charAt(5))*5 +
                parseInt(meli_code.charAt(6))*4 +
                parseInt(meli_code.charAt(7))*3 +
                parseInt(meli_code.charAt(8))*2;
            var r = n - parseInt(n/11)*11;
            if ((r == 0 && r == c) || (r == 1 && c == 1) || (r > 1 && c == 11 - r))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
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
        $.ajax({
            type: "POST",
            url: "/offer/api/getSection",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: hospitalId.toString(),
            success: function (data) {
                $("#ddlSection option").remove();
                $("#ddlShift option").remove();

                var defaultSection = $("<option>");
                defaultSection.text("بخش موردنظر را انتخاب نمائید")
                    .prop("disabled", true)
                    .prop("selected", true)
                    .attr("value", "");

                var defaultShifit = $("<option>");
                defaultShifit.text("شیفت موردنظر را انتخاب نمائید")
                    .prop("disabled", true)
                    .prop("selected", true)
                    .attr("value", "");
                $("#ddlSection").append(defaultSection);
                $("#ddlShift").append(defaultShifit);
                if (data.length > 0) {
                    $.each(data[0].sections, function (index, dataItem) {
                        var option = $("<option>");
                        option.text(dataItem.title).attr("value", dataItem.sectionId);

                        $("#ddlSection").append(option);
                    });

                    $.each(data[0].shifits, function (index, dataItem) {
                        var option = $("<option>");
                        option.text(dataItem.title).attr("value", dataItem.shiftId);

                        $("#ddlShift").append(option);
                    });
                }
            }
        });
    }

    function isEmail(email) {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }

    function isMobile(mobile) {
        var regex=/09(1[0-9]|3[1-9]|2[1-9])-?[0-9]{3}-?[0-9]{4}  /
        return regex.test(mobile);
    }

    function saveAppreciation(dataArray) {

        $.ajax({
            type: "POST",
            url: "/ticket/api/saveTicket",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(dataArray),
            success: function (data) {
                if (data == false) {
                    Materialize.toast('خطا درانجام عملیات', 4000, 'error-toast');
                } else {
                    $("#lblMessage").text("پیام شما با موفقیت ثبت شد");
                    $('#alertWindow').modal('open');
                }
            }
        });
    }

</script>

<%@include file="footer.jsp" %>
