<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<div class="row">
    <div class="col m1 l3"></div>

    <div class="card col s12 m10 l6 row" style="padding: 30px 60px 30px 60px">

        <div class="row">
            <div class="col s12 m4 l4 right marginTop">
                <label for="ddlHospital">انتخاب بیمارستان:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <select id="ddlHospital" onchange="ddlHospitalChange();" disabled>
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
                <label for="criticismName">نام:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="criticismName" type="text" class="validate" lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="criticismFamily">نام خانوادگی:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="criticismFamily" type="text" class="validate" lang="fa-IR">
            </div>

        </div>
        
        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="nationalCode">کد ملی:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="10" id="nationalCode" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="telephone">تلفن ثابت:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="12" id="telephone" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="mobile">تلفن همراه:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="12" id="mobile" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>
        
        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="criticismSubject">موضوع:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="500" id="criticismSubject" type="text" class="validate" lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="criticismDescription">متن انتقاد:</label>
            </div>

            <div class="col s12 m8 l8 left input-field" >
                <textarea maxlength="4000" id="criticismDescription" class="materialize-textarea" lang="fa-IR"></textarea>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="criticismEmail">ایمیل:</label>
            </div>

            <div class="col s12 m8 l8 left" class="input-field">
                <input maxlength="100" id="criticismEmail" type="email" class="validate">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
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

            <div class="col s12 m4 l4 right marginTop">
            </div>

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
               onclick="criticismSubmit()">ثبت انتقاد

            </a>

        </div>

        <div class="col m1 l3"></div>
    </div>

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
    var captchaString ="";
    
    $(window).load(function () {
        DrawCaptcha();
    });

    $(document).ready(function () {
        var id = "${hospitalId}";
        var hospitalName = "${hospitalName}";

        $("#ddlHospital").val(id);
        $(".brand-logo").text($("#ddlHospital option:selected").text());
        $("#imgHospital").attr("src", "/static/hospitalImage/" + hospitalName);
        ddlHospitalChange();

        $("#lblTrackingNumber").persiaNumber();
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
    
    function criticismSubmit() {
        if(!validCaptcha()){
            Materialize.toast('کد امنیتی بدرستی وارد نشده است .', 3000, 'info-toast');
            $("#txtinput").focus();
            return;
        }

        var hospitalId = $("#ddlHospital option:selected").val();
        var sectionId = $("#ddlSection option:selected").val();
        var shiftId = $("#ddlShift option:selected").val();

        var nationalCodeCriticism = $("#nationalCode").val();
        var telCriticism = $("#telephone").val();
        var mobileCriticism = $("#mobile").val();
        var criticismName = $("#criticismName").val();
        var criticismFamily = $("#criticismFamily").val();

        var criticismSubject = $("#criticismSubject").val();
        var criticismDescription = $("#criticismDescription").val();
        var criticismEmail = $("#criticismEmail").val();



        if (hospitalId == 0) {
            Materialize.toast('بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');

        } else if (sectionId == 0) {
            Materialize.toast('بخش بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');

        } else if (shiftId == 0) {
            Materialize.toast('شیفت بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');

        } else if (nationalCodeCriticism == "") {
            $("#nationalCode").focus();
            Materialize.toast('کد ملی خود را وارد کنید', 3000, 'info-toast');

        } else if (telCriticism == "") {
            $("#telephone").focus();
            Materialize.toast('تلفن ثابت خود را وارد کنید', 3000, 'info-toast');

        } else if (mobileCriticism == "") {
            $("#mobile").focus();
            Materialize.toast('موبایل خود را وارد کنید', 3000, 'info-toast');

        } else if (criticismSubject == "") {
            $("#criticismSubject").focus();
            Materialize.toast('موضوع انتقاد را تعیین کنید', 3000, 'info-toast');

        } else if (criticismDescription == "") {
            $("#criticismDescription").focus();
            Materialize.toast('متن انتقاد را وارد کنید', 3000, 'info-toast');

        } else if (criticismName == "") {
            $("#criticismName").focus();
            Materialize.toast('نام انتفاد کننده را وارد کنید', 3000, 'info-toast');

        } else if (criticismFamily == "") {
            $("#criticismFamily").focus();
            Materialize.toast('نام خانوادگی انتقاد کننده را وارد کنید', 3000, 'info-toast');

        } else if (criticismEmail != "" && !isEmail(criticismEmail)) {
            $("#criticismEmail").focus();
            Materialize.toast('پست الکترونیکی بدرستی وارد نشده است', 3000, 'info-toast');
        }  else {

            var dataArray = [];
            var dataItem = {};

            dataItem["ticketId"] = 0;
            dataItem["ticketTypeId"] = 4;
            dataItem["hospitalId"] = hospitalId;
            dataItem["sectionId"] = sectionId;
            dataItem["shiftId"] = shiftId;
            dataItem["name"] = criticismName;
            dataItem["family"] = criticismFamily;
            dataItem["nationalCode"] = nationalCodeCriticism;
            dataItem["tel"] = telCriticism;
            dataItem["mobile"] = mobileCriticism;
            dataItem["subject"] = criticismSubject;
            dataItem["description"] = criticismDescription;
            dataItem["email"] = criticismEmail;

          /*  dataArray.push(dataItem);*/
            if ($('input[type=file]')[0].files.length > 0){
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
                        if (result==false) {
                            Materialize.toast('خطا درانجام عملیات', 4000, 'error-toast');
                        } else {
                            dataItem["fileName"]=result.fileName;
                            dataArray.push(dataItem);
                            saveCriticisms(dataArray);
                        }
                    },
                    error: function (result) {
                        alert("error");
                    }
                });
            }else{
                dataItem["fileName"]="";
                dataArray.push(dataItem);
                saveCriticisms(dataArray);
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

    function saveCriticisms(dataArray){
        $.ajax({
            type: "POST",
            url: "/ticket/api/saveTicket",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(dataArray),
            success: function (data) {
                debugger;
                if (data == false) {
                    Materialize.toast('خطا درانجام عملیات', 4000, 'error-toast');
                } else {
                    $("#lblMessage").text("پیام شما با موفقیت ثبت شد");
                    $('#alertWindow').modal('open');
                }
            }
        });
    }

    function isEmail(email) {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }

</script>

<%@include file="footer.jsp" %>
