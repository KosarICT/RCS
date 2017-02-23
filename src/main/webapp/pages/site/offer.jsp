<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>
<div class="row">
    <div class="col m1 l3"></div>

    <div class="card col s12 m10 l6 row" style="padding: 30px 60px 30px 60px">

        <div class="row" >
            <div class="col s12 m4 l4 right marginTop">
                <label for="ddlHospital">انتخاب بیمارستان:</label>
            </div>

            <div class="col s12 m8 l8 m12 left">
                <select id="ddlHospital" onchange="loadSection(this)" disabled>
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

            <div class="col s12 m8 l8 m12 left">
                <select id='ddlSection'>

                </select>
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="name">نام پیشنهاد دهنده:</label>
            </div>

            <div class="col s12 m8 l8 m12 left">
                <input id="name" type="text" class="validate" maxlength="100" lang="fa-IR">
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="family">نام خانوادگی پیشنهاد دهنده:</label>
            </div>

            <div class="col s12 m8 l8 m12 left">
                <input id="family" type="text" class="validate" maxlength="100" lang="fa-IR">
            </div>
        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="nationalCode">کد ملی:</label>
            </div>

            <div class="col s12 m8 l8 m12 left">
                <input maxlength="10" id="nationalCode" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="telephone">تلفن ثابت:</label>
            </div>

            <div class="col s12 m8 l8 m12 left">
                <input maxlength="12" id="telephone" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="mobile">تلفن همراه:</label>
            </div>

            <div class="col s12 m8 l8 m12 left">
                <input maxlength="12" id="mobile" type="text" class="validate"
                       onkeypress='return event.charCode >= 48 && event.charCode <= 57;'>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="offerSubject">موضوع:</label>
            </div>

            <div class="col s12 m8 l8 m12 left">
                <input maxlength="500" id="offerSubject" type="text" class="validate" lang="fa-IR">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="offerDescription">متن پیشنهاد:</label>
            </div>

            <div class="col s12 m8 l8 m12 input-field left">
                <textarea maxlength="4000" id="offerDescription" class="materialize-textarea" lang="fa-IR"></textarea>
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label for="email">ایمیل:</label>
            </div>

            <div class="col s12 m8 l8 m12 input-field left">
                <input maxlength="100" id="email" type="email" class="validate">
            </div>

        </div>

        <div class="row">

            <div class="col s12 m4 l4 right marginTop">
                <label>مستندات :</label>
            </div>

            <div class="col s12 m8 l8 file-field input-field left">
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
               onclick="offerSubmit()">ثبت پیشنهاد

            </a>

        </div>

        <div class="col m1 l3"></div>
    </div>
</div>

<div id="alertWindow" class="modal" style="width: 320px">
    <div class="modal-content" style="text-align: center;vertical-align: middle">
        <br/>
        <br/>
        <label style="margin-bottom: 10px">اطلاعات شما با موفقیت ثبت شد.</label>
        <br/>
        <br/>
    </div>
    <div class="divider"></div>
    <div class="modal-footer">
        <a class="waves-effect waves-light btn-flat" href="/">تایید</a>
    </div>
</div>

<script>

    var captchaString = "";

    $(window).load(function () {
        DrawCaptcha();
    });

    $(document).ready(function () {
        var id = "${hospitalId}";
        var hospitalName = "${hospitalName}";

        $("#webURL").val(webUrl);

        $("#hospitalUrl").attr("href",  $("#webURL").val());

        $("#ddlHospital").val(id);
        $(".brand-logo").text($("#ddlHospital option:selected").text());
        $("#imgHospital").attr("src", "/static/hospitalImage/" + hospitalName);
        loadSection();

        initWindow();

        $("#name, #family, #offerSubject, #offerDescription").farsiInput();
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

    function offerSubmit() {

        if (!validCaptcha()) {
            Materialize.toast('کدامنیتی بدرستی وارد نشده است', 3000, 'info-toast');
            $("#txtInput").focus();
            return;
        }

        var hospitalId = $("#ddlHospital option:selected").val();
        var sectionId = $("#ddlSection option:selected").val();
        var shiftId = $("#ddlShift option:selected").val();
        var name = $("#name").val();
        var family = $("#family").val();
        var nationalCode = $("#nationalCode").val();
        var tel = $("#telephone").val();
        var mobile = $("#mobile").val();
        var offerSubject = $("#offerSubject").val();
        var offerDescription = $("#offerDescription").val();
        var email = $("#email").val();

        if (hospitalId == 0) {
            $("#ddlHospital").focus();
            Materialize.toast('بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');
        } else if (sectionId == 0) {
            $("#ddlSection").focus();
            Materialize.toast('بخش بیمارستان مورد نظر را انتخاب کنید', 3000, 'info-toast');
        } else if (name == 0) {
            $("#name").focus();
            Materialize.toast('نام خود را وارد کنید', 3000, 'info-toast');
        } else if (family == 0) {
            $("#family").focus();
            Materialize.toast('نام خانوادگی خود را وارد کنید', 3000, 'info-toast');
        } else if (nationalCode == 0) {
            $("#nationalCode").focus();
            Materialize.toast('کد خود را وارد کنید', 3000, 'info-toast');
        } else if (tel == 0) {
            $("#telephone").focus();
            Materialize.toast('تلفن خود را وارد کنید', 3000, 'info-toast');
        } else if (mobile == 0) {
            $("#mobile").focus();
            Materialize.toast('موبایل خود را وارد کنید', 3000, 'info-toast');
        } else if (offerSubject == 0) {
            $("#offerSubject").focus();
            Materialize.toast('موضوع پیشنهاد خود را وارد کنید', 3000, 'info-toast');
        } else if (offerDescription == 0) {
            $("#offerDescription").focus();
            Materialize.toast('پبشنهاد خود را وارد کنید', 3000, 'info-toast');
        } else if (email != "" && !isEmail(email)) {
            $("#offerDescription").focus();
            Materialize.toast('پست الکترونیکی بدرستی وارد نشده است', 3000, 'info-toast');
        } else {

            var dataArray = [];
            var dataItem = {};

            dataItem["ticketId"] = 0;
            dataItem["ticketTypeId"] = 2;
            dataItem["hospitalId"] = hospitalId;
            dataItem["sectionId"] = sectionId;
            dataItem["shiftId"] = shiftId;
            dataItem["name"] = name;
            dataItem["family"] = family;
            dataItem["nationalCode"] = nationalCode;
            dataItem["tel"] = tel;
            dataItem["mobile"] = mobile;
            dataItem["subject"] = offerSubject;
            dataItem["description"] = offerDescription;
            dataItem["email"] = email;


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
                        if (result== false) {
                            Materialize.toast('خطا درانجام عملیات', 4000, 'error-toast');

                        } else {
                            dataItem["fileName"]=result.fileName;
                            dataArray.push(dataItem);
                            saveOffer(dataArray)
                        }
                    },
                    error: function (result) {
                        alert("error");
                    }
                });
            } else {
                dataItem["fileName"]="";
                dataArray.push(dataItem);
                saveOffer(dataArray)
            }


        }
    }
    
    function saveOffer(dataArray) {
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
                    $('#alertWindow').modal('open');
                }
            }
        });
        
    }

    function loadSection(sender) {
        var hospitalId = $("#ddlHospital option:selected").val();

        $.ajax({
            type: "POST",
            url: "/offer/api/getSection",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: hospitalId.toString(),
            success: function (data) {
                debugger;
                if (!data) {
                    Materialize.toast('خطا درانجام عملیات', 4000, 'error-toast');
                } else {
                    $("#ddlSection option").remove();

                    var defaultOption = $("<option>");
                    defaultOption.text("بخش موردنظر را انتخاب نمائید")
                        .prop("disabled", true)
                        .prop("selected", true)
                        .attr("value", "");

                    $("#ddlSection").append(defaultOption);

                    $.each(data[0].sections, function (index, dataItem) {
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

    function removeSpaces(string) {
        return string.split(' ').join('');
    }
</script>
<%@include file="footer.jsp" %>

