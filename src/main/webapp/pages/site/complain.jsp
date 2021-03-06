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
                <select id="ddlHospital" onchange="ddlHospitalChange(this)" disabled>
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

            <div class="col s12 m6 l6 right">
                <select id='ddlShift' onchange="ddlShiftChange(this)">
                </select>
            </div>
            <div class="col s12 m2 l2 left">
                <label id="lblHourWork"></label>
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label>شکایت کننده:</label>
            </div>

            <div id="radioGroup" class="col s12 m8 l8 left">

                <p>
                    <input name="sickGroup" value="1" type="radio" id="sick" checked/>
                    <label for="sick"></label>
                    <label for="sick" style="vertical-align: top">خود بیمار</label>
                </p>
                <p>
                    <input name="sickGroup" value="2" type="radio" id="withSick"/>
                    <label for="withSick"></label>
                    <label style="vertical-align: top">همراه بیمار</label>
                </p>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="ddlRelation">نسبت ثبت کننده شکایت:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <select id='ddlRelation' disabled>
                    <c:if test="${not empty relationLists}">
                        <option value="0" disabled selected>نسبت با بیمار</option>
                        <c:forEach var="entry" items="${relationLists}">
                            <option value="${entry.relationId}">${entry.title}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="sickName">نام بیمار:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input id="sickName" placeholder="نام بیمار: علی" onblur="sickNameBlur(this)" type="text" class="validate" maxlength="100" lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="sickFamily">نام خانوادگی بیمار:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="sickFamily" onblur="sickFamilyBlur(this)" placeholder="نام خانوادگی بیمار: رضایی" type="text" class="validate" lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="nationalCode">کد ملی:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="10" id="nationalCode" onblur="nationalCodeBlur(this)" placeholder="کد ملی: 09212678392" type="tel" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="telephone">تلفن ثابت:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="12" id="telephone" type="tel" placeholder="تلفن ثابت: 02188468290" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="mobile">تلفن همراه:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="12" id="mobile" type="tel" placeholder="تلفن همراه:09121187653" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right">
                <label for="compalainerName">نام شکایت کننده:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="compalainerName" placeholder="نام شکایت کننده:علی" type="text" class="validate" disabled lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="compalainerFamily">نام خانوادگی شکایت کننده:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="100" id="compalainerFamily" placeholder="نام خانوادگی شکایت کننده:اصغری" type="text" class="validate" disabled lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="registerNationalCode"> کد ملی ثبت کننده:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="10" id="registerNationalCode"  placeholder="کد ملی ثبت کننده:09645893578" type="text" class="validate" disabled
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="ddlComplaintType">نوع شکایت:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <select id='ddlComplaintType'>

                    <c:if test="${not empty complainTypeLists}">
                        <option value="0" disabled selected>نوع شکایت</option>
                        <c:forEach var="entry" items="${complainTypeLists}">
                            <option value="${entry.complaintTypeId}">${entry.title}</option>
                        </c:forEach>
                    </c:if>

                </select>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="complainSubject">موضوع:</label>
            </div>

            <div class="col s12 m8 l8 left">
                <input maxlength="500" id="complainSubject" placeholder="موضوع" type="text" class="validate" lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="complainDescription">متن شکایت:</label>
            </div>

            <div class="col s12 m8 l8 input-field left">
                <textarea maxlength="4000" id="complainDescription" placeholder="متن شکایت" class="materialize-textarea"
                          lang="fa-IR"></textarea>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="complainEmail">ایمیل:</label>
            </div>

            <div class="col s12 m8 l8 input-field left">
                <input maxlength="100" id="complainEmail" placeholder="ایمیل:ali@gmail.com" type="email" class="validate">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label>مستندات :</label>
            </div>

            <div class="col s12 m8 l8 file-field input-field left">
                <div class="btn" style="width: 48%;float: none">
                    <span>بارگذاری مستندات</span>
                    <input id="complainAttachment" type="file">
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
                <input type="text" id="txtInput" />
            </div>
        </div>

        <div class="row">
            <div class="col s12 m4 l4 right marginTop"></div>

            <div class="col s12 m8 l8 left">
                <a class="waves-effect waves-light btn"
                   onclick="compainSubmit()">ثبت شکایت

                </a>
            </div>
        </div>

    </div>

    <div class="col m1 l3"></div>
</div>

<div id="trackingNumberWindow" class="modal" style="width: 320px">
    <div class="modal-content" style="text-align: center;vertical-align: middle">
        <br/>
        <br/>
        <label style="margin-bottom: 10px">اطلاعات شما با موفقیت ثبت شد.</label>
        <br/>
        <br/>
        <label id="lblTrackingNumber"></label>
        <label id="lblresponceTime" style="display: none"></label>
        <label>روز</label>
    </div>
    <div class="divider"></div>
    <div class="modal-footer">
        <a class="waves-effect waves-light btn-flat" href="/">تایید</a>
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

<div id="reviewWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        بازبینی اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">
            <div class="col s12 m12 l8">
                <div class="col s12 m4 l4 right marginTop">
                    <label>نام بیمارستان</label>
                </div>
                <label id="lblHospitalShow"></label>
            </div>
        </div>
    </div>
</div>

<script>
    var captchaString = "";
    var hospitalId = 0;

    $(window).load(function () {
        DrawCaptcha();
    });

    function createDynamicURL() {
        var URL = '';

        URL + location.host + "/" + hospitalId + "/";

        return URL;
    }

    $(document).ready(function () {
        hospitalId = "${hospitalId}";
        var hospitalName = "${hospitalImage}";
        var webUrl = "${webUrl}";

        $("#webURL").val(webUrl);

        $("#hospitalUrl").attr("href",  $("#webURL").val());

        $("#ddlHospital").val(hospitalId);
        $(".brand-logo").text($("#ddlHospital option:selected").text());
        $("#imgHospital").attr("src", "/static/hospitalImage/" + hospitalName);
        ddlHospitalChange();

        $("#lblTrackingNumber").persiaNumber();
        initWindow();

        $('#radioGroup input').on('change', function () {

            if ($('#withSick').is(':checked')) {
                $("#ddlRelation").val("0");
                $("#ddlRelation").removeAttr('disabled');

                $("#compalainerName").val("");
                $("#compalainerName").removeAttr('disabled');

                $("#compalainerFamily").val("");
                $("#compalainerFamily").removeAttr('disabled');

                $("#registerNationalCode").val("");
                $("#registerNationalCode").removeAttr('disabled');
            }
            else {
                $("#ddlRelation").val("0");
                $("#ddlRelation").attr('disabled', 'disabled');

                $("#compalainerName").val("");
                $("#compalainerName").attr('disabled', 'disabled');

                $("#compalainerFamily").val("");
                $("#compalainerFamily").attr('disabled', 'disabled');

                $("#registerNationalCode").val("");
                $("#registerNationalCode").attr('disabled', 'disabled');
            }
        });


        $("#sickName, #sickFamily, #compalainerName,#compalainerFamily, #complainSubject, #complainDescription").farsiInput();
    });

    function initWindow() {

        $('#trackingNumberWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '10%',
                ending_top: '10%',
            }
        );

        $('#alertWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '10%',
                ending_top: '10%',
            }
        );

        $('#reviewWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '10%',
                ending_top: '10%',
            }
        );
    }

    function sickNameBlur(sender) {
        var compainer = $("input[type=radio]:checked").val();
        if(compainer==1)
            $("#compalainerName").val(sender.value)
    }

    function sickFamilyBlur(sender) {
        var compainer = $("input[type=radio]:checked").val();
        if(compainer==1)
            $("#compalainerFamily").val(sender.value)
    }

    function nationalCodeBlur(sender) {
        var compainer = $("input[type=radio]:checked").val();
        if(compainer==1)
            $("#registerNationalCode").val(sender.value)
    }

    function showReview() {

    }

    /**
     *Complain
     * **/
    function compainSubmit() {

        if (!validCaptcha()) {
            Materialize.toast('کدامنیتی بدرستی وارد نشده است', 3000, 'info-toast');
            $("#txtInput").focus();
            return;
        }

        var compainer = $("input[type=radio]:checked").val()
        var hospitalId = $("#ddlHospital option:selected").val();
        var sectionId = $("#ddlSection option:selected").val();
        var shiftId = $("#ddlShift option:selected").val();
        var relationId = $("#ddlRelation option:selected").val();
        var sickName = $("#sickName").val();
        var sickFamily = $("#sickFamily").val();
        var sickNationalCode = $("#nationalCode").val();
        var sickTel = $("#telephone").val();
        var sickMobile = $("#mobile").val();
        var complaintTypeId = $("#ddlComplaintType option:selected").val();
        var complainSubject = $("#complainSubject").val();
        var complainDescription = $("#complainDescription").val();
        var complainEmail = $("#complainEmail").val();
        var compalainerName = $("#compalainerName").val();
        var compalainerFamily = $("#compalainerFamily").val();
        var registerNationalCode = $("#registerNationalCode").val();

        if (hospitalId == 0) {
            Materialize.toast('بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');
            $("#ddlHospital").focus();

        } else if (sectionId == 0) {
            Materialize.toast('بخش بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');
            $("#ddlSection").focus();
        } else if (shiftId == 0) {
            Materialize.toast('شیفت بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');
            $("#ddlShift").focus();
        } else if (relationId == 0 && compainer == 2) {
            Materialize.toast('نسبت با بیمار را مشخص کنید', 3000, 'info-toast');
            $("#ddlRelation").focus();
        } else if (sickName == 0) {
            $("#sickName").focus();
            Materialize.toast('نام بیمار را وارد کنید', 3000, 'info-toast');

        } else if (sickFamily == 0) {
            $("#sickFamily").focus();
            Materialize.toast('نام خانوادگی بیمار را وارد کنید', 3000, 'info-toast');

        } else if (sickNationalCode == 0) {
            $("#nationalCode").focus();
            Materialize.toast('کد ملی بیمار را وارد کنید', 3000, 'info-toast');

        } else if(!checkMelliCode(sickNationalCode)){
            $("#nationalCode").focus();
            Materialize.toast('کد ملی بیمار را صحیح وارد کنید', 3000, 'info-toast');
        }  else if (sickTel == 0) {
            $("#telephone").focus();
            Materialize.toast('تلفن ثابت بیمار را وارد کنید', 3000, 'info-toast');

        } else if (sickTel.length ==111){
            $("#telephone").focus();
            Materialize.toast('تلفن بیمار را صحیح وارد کنید', 3000, 'info-toast');
        } else if (sickMobile == 0) {
            $("#mobile").focus();
            Materialize.toast('موبایل بیمار را وارد کنید', 3000, 'info-toast');

        }  else if(sickMobile.length!=11 && !isMobile(sickMobile)){
            $("#mobile").focus();
            Materialize.toast('موبایل خود را صحیح وارد کنید', 3000, 'info-toast');
        } else if (complaintTypeId == 0) {
            Materialize.toast('نوع شکایت را تعیین کنید', 3000, 'info-toast');

        } else if (complainSubject == 0) {
            $("#complainSubject").focus();
            Materialize.toast('موضوع شکایت را تعیین کنید', 3000, 'info-toast');

        } else if (complainDescription == 0) {
            $("#complainDescription").focus();
            Materialize.toast('متن توضیحات شکایت را وارد کنید', 3000, 'info-toast');

        } else if (compainer == 2 && compalainerName == "") {
            $("#compalainerName").focus();
            Materialize.toast('نام شکایت کننده را وارد کنید', 3000, 'info-toast');

        } else if (compainer == 2 && compalainerFamily == "") {
            $("#compalainerFamily").focus();
            Materialize.toast('نام خانوادگی شکایت کننده را وارد کنید', 3000, 'info-toast');

        } else if (compainer == 2 && registerNationalCode == "") {
            $("#registerNationalCode").focus();
            Materialize.toast('کد ملی شکایت کننده را وارد کنید', 3000, 'info-toast');

        }else if(compainer == 2 && !checkMelliCode(registerNationalCode)){
            $("#registerNationalCode").focus();
            Materialize.toast('کد ملی شکایت کننده را صحیح وارد کنید', 3000, 'info-toast');
        }  else if (complainEmail != "" && !isEmail(complainEmail)) {
            $("#registerNationalCode").focus();
            Materialize.toast('پست الکترونیکی بدرستی وارد نشده است', 3000, 'info-toast');

        } else {

            var dataArray = [];
            var dataItem = {};

            dataItem["ticketId"] = 0;
            dataItem["ticketTypeId"] = 3;
            dataItem["compainer"] = compainer;
            dataItem["hospitalId"] = hospitalId;
            dataItem["sectionId"] = sectionId;
            dataItem["shiftId"] = shiftId;
            dataItem["relationId"] = relationId;
            dataItem["name"] = sickName;
            dataItem["family"] = sickFamily;
            dataItem["nationalCode"] = sickNationalCode;
            dataItem["tel"] = sickTel;
            dataItem["mobile"] = sickMobile;

            if (compainer == 2) {

                dataItem["compalainerName"] = compalainerName;
                dataItem["compalainerFamily"] = compalainerFamily;
                dataItem["registerNationalCode"] = registerNationalCode;
            }

            dataItem["complaintTypeId"] = complaintTypeId;
            dataItem["subject"] = complainSubject;
            dataItem["description"] = complainDescription;
            dataItem["email"] = complainEmail;

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
                    async: false,
                    success: function (result) {
                        if (result == false) {
                            Materialize.toast('خطا درانجام عملیات', 4000, 'error-toast');
                        } else {
                            dataItem["fileName"] = result.fileName;
                            dataArray.push(dataItem);
                            saveComplaint(dataArray);
                        }
                    },
                    error: function (result) {
                        alert("error");
                    }
                });
            } else {
                dataItem["fileName"] = "";
                dataArray.push(dataItem);
                saveComplaint(dataArray);
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

    function isMobile(mobile) {
        var regex=/09(1[0-9]|3[1-9]|2[1-9])-?[0-9]{3}-?[0-9]{4}  /
        return regex.test(mobile);
    }

    function saveComplaint(dataArray) {
        $.ajax({
            type: "POST",
            url: "/ticket/api/saveTicket",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(dataArray),
            success: function (data) {
                debugger;
                if (data != false) {
                    $("#lblTrackingNumber").text("کد رهگیری شکایت شما:" + data.trackingNumber);
//                    $("#lblresponceTime").text("زمان پاسخگویی به شکایت شما " + data.responceTime);
                    $('#trackingNumberWindow').modal('open');
                } else {
                    Materialize.toast('خطا درانجام عملیات', 4000, 'error-toast');
                }
            }
        });
    }

    function isEmail(email) {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }


    /** captcha code */
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

    function ddlShiftChange() {
        debugger;
        var hourWork = $("#ddlShift option:selected").attr("hourWork");
        $("#lblHourWork").text(hourWork);
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
                        option.text(dataItem.title).attr("value", dataItem.shiftId).attr("hourWork",dataItem.hourWork);

                        $("#ddlShift").append(option);
                    });
                }
            }
        });
    }


    function removeSpaces(string) {
        return string.split(' ').join('');
    }

</script>

<%@include file="footer.jsp" %>
