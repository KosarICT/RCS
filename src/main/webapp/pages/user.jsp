<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<style>
    .textColor {
        font-size: 13px;
        color: #000000;
    }

    .modal {
        max-height: 90% !important;
        top: 5% !important;
    }

    .modal select {
        display: block;
    }

    .input-field {
        margin-top: 0 !important;
    }
</style>

<div id="tblUser" class="row">
    <table class="bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">نام کاربری</th>
            <th class="center">نام</th>
            <th class="center">نام خانوادگی</th>
            <th class="center">نام نمایشی</th>
            <th class="center">وضعیت</th>
            <th class="center">عملیات</th>
        </tr>
        </thead>

        <c:if test="${not empty lists}">
            <c:set var="row" value="1" scope="page"/>

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${lists}">
                <c:set var="userId" value="${entry.userId}" scope="page"/>
                <tr>
                    <td class="center" style="display: none">${entry.userId}</td>
                    <td class="center counter"><c:out value="${row}"/></td>
                    <td class="center">${entry.userName}</td>
                    <td class="center">${entry.firstName}</td>
                    <td class="center">${entry.lastName}</td>
                    <td class="center">${entry.displayName}</td>
                    <td class="center">
                        <c:choose>
                            <c:when test="${entry.locked != 0}">
                                <img src="/static/icon/cancel2.png">
                            </c:when>
                            <c:otherwise>
                                <img src="/static/icon/ok3.png">
                            </c:otherwise>
                        </c:choose>

                    </td>
                    <td class="center">
                        <c:choose>
                            <c:when test="${entry.userName == 'admin'}">
                            </c:when>
                            <c:otherwise>
                                <a id="btnEdit_${entry.userId}" href="#" class="mainTextColor"
                                   style="margin-left: 5px" onclick="getUserForEdit(this);">
                                    <img src="/static/icon/edit1.png">
                                </a>
                                <a id="btnDelete_${entry.userId}" href="#" class="mainTextColor"
                                   onclick="showConfirm(this)">
                                    <img src="/static/icon/cancel2.png">
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

                <c:set var="row" value="${row + 1}" scope="page"/>
            </c:forEach>
            </tbody>
        </c:if>
    </table>
</div>

<div class="custom-fixed-action-btn">
    <a class="btn-floating btn-large waves-effect waves-light slideColor " href="#" onclick="showUserWindow();"
       style="bottom: 35px; left: 35px;">
        <i class="material-icons">add</i>
    </a>
</div>

<div id="userWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        اضافه/ویرایش کاربر
    </div>
    <div class="modal-content">
        <div class="row">

            <ul id="userTab" class="tabs" style="padding-right: 0">
                <li class="tab col s3 right"><a href="#userInformation">مشخصات</a></li>
                <li class="tab col s3 right"><a href="#rols">نقش ها</a></li>
            </ul>
            <div id="userInformation">
                <div class="col s12 m6 l5 right">

                    <div class="input-field">
                        <input placeholder="نام کاربری" id="txtUserName" type="text" autocomplete="false" autofocus>
                    </div>

                    <div class="input-field">
                        <input placeholder="کلمه عبور" id="txtPassword" type="password" >
                    </div>

                    <div class="input-field">
                        <input placeholder="تکرار کلمه عبور" id="txtRetryPassword" type="password" >
                    </div>

                    <div class="input-field ">
                        <input placeholder="شماره پرسنلی" id="txtPersonalNumber" type="text" maxlength="10"
                               onkeypress="return event.charCode >=48 && event.charCode <= 57">
                    </div>

                    <div class="input-field ">
                        <input placeholder="نام" id="txtFirstName" type="text" maxlength="255">
                    </div>

                    <div class="input-field ">
                        <input placeholder="نام خانوادگی" id="txtLastName" type="text" maxlength="255">
                    </div>

                    <div class="input-field ">
                        <input placeholder="نام نمایشی" id="txtDisplayName" type="text" maxlength="255">
                    </div>

                    <div class="input-field ">
                        <input placeholder="تلفن" id="txtTel" type="text" maxlength="11"
                               onkeypress="return event.charCode >=48 && event.charCode <= 57">
                    </div>

                    <div class="input-field ">
                        <input placeholder="موبایل" id="txtMobile" type="text" maxlength="12"
                               onkeypress="return event.charCode >=48 && event.charCode <= 57">
                    </div>

                    <div>
                        <input type="checkbox" class="filled-in" id="chkLocked"/>
                        <label for="chkLocked">غیر فعال</label>
                    </div>
                </div>
                <div class="col s12 m6 l6 left">
                    <div class="center" style="margin-bottom: 10px">
                        <img style="width: 250px; height: 250px" class="card" id="imgUser"
                             src="../static/userImage/boy.png">
                    </div>
                    <div>
                        <div class="file-field input-field">
                            <div class="btn">
                                <span>بارگذاری فایل</span>
                                <input type="file" id="userImageUploader">
                            </div>
                            <div class="file-path-wrapper">
                                <input class="file-path validate" type="text">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="rols" >
                <div class="col right ">
                    <div class="row">
                        <div class="col s12 m4 l4 right">
                            <label for="ddlHospital">انتخاب بیمارستان:</label>
                        </div>

                        <div class="col s12 m8 l8 left">
                            <select id="ddlHospital" onchange="ddlHospitalChange(this)">
                                <option value="0" disabled selected>بیمارستان موردنظر انتخاب نمائید</option>
                                <c:if test="${not empty hospitalList}">
                                    <c:forEach var="entry" items="${hospitalList}">
                                        <option value="${entry.hospitalId}">${entry.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </div>

                    <div id="roleListDiv"></div>
                </div>
            </div>
        </div>

    </div>
    <div class="modal-footer" style="background-color: #4f6789">
        <a href="#" class="modal-action  waves-effect waves-light btn-flat white-text" onclick="btnAddClick();"><img
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
    var userId = 0;
    var imageName = "boy.png";

    $(document).ready(function () {
        $(".page-title").text("کاربران");

        $('#userTab.tabs').tabs('select_tab', 'userInformation');


        var height = screen.height - 170;
        $("#container").css("height", height + "px").css("overflow", "auto").persiaNumber();

        initWindow();

        $('.fixed-action-btn').openFAB();

        $('.modal').modal();

        $("#userImageUploader").change(function () {
            processUpload();
        });

    });

    function initWindow() {
        $('#confirmWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '10%',
            }
        );

        $('#userWindow').modal({
                dismissible: false, // Modal can be dismissed by clicking outside of the modal
                opacity: .5, // Opacity of modal background
                in_duration: 300, // Transition in duration
                out_duration: 200, // Transition out duration
                starting_top: '0', // Starting top style attribute
                ending_top: '0',
                ready:function () {

                }
            }
        );
    }

    function ddlHospitalChange() {
        var hospitalId = $("#ddlHospital option:selected").val();
        $.ajax({
            type: "POST",
            url: "/hospitalSection/api/getHospitalSectionDataByHospitalId",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: hospitalId.toString(),
            success: function (data) {
                $("#roleListDiv").empty();
                if (data.length > 0) {
                    createHospitalSectionDiv(data);
                }
            }
        });
    }

    function createHospitalSectionDiv(data) {

        var roleListDiv = $("#roleListDiv");
        $.each(data, function (index, dataItem) {

            var div = $("<div>").css("width", "48%").css("display", "inline-block").css("margin", "2px");

            var pTag = $('<p>');

            var lblRoleName = $("<label>").attr("for", dataItem.hospitalSectionId).css("width", "0");
            var chkRole = $("<input>").attr("type", "checkBox").attr("value", dataItem.hospitalSectionId).attr("id", dataItem.hospitalSectionId.toString());
            var lblRoleText = $("<label>").text(dataItem.section.title).attr("for", dataItem.hospitalSectionId.toString()).css("vertical-align", "top").css("margin-right", "8px");

            pTag.append(chkRole);
            pTag.append(lblRoleName);
            pTag.append(lblRoleText);

            div.append(pTag);

            roleListDiv.append(div);

        });

    }

    function showConfirm(sender) {
        userId = sender.id.split("_")[1];

        $('#messageDialog').text('آیا برای حذف این کاربر اطمینان دارید؟');
        $('#confirmWindow').modal('open');
    }

    function btnConfirmClick() {
        $.ajax({
            type: "POST",
            url: "/user/api/deleteUser",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: userId.toString(),
            success: function (data) {
                if (data) {
                    window.location.reload();
                } else {
                    Materialize.toast('خطا در انجام عملیات!', 40000, 'error-toast');
                }
            }
        });
    }

    function btnAddClick() {
        var pas = $("#txtPassword").val();
        var retryPas = $("#txtRetryPassword").val();
        var checkUserName = $("#txtUserName").val();
        var personalNumber = $("#txtPersonalNumber").val();
        var firstName = $("#txtFirstName").val();
        var lastName = $("#txtLastName").val();
        var displayName = $("#txtDisplayName").val();
        var tel = $("#txtTel").val();
        var mobile = $("#txtMobile").val();

        if (checkUserName == "" || personalNumber == "" || firstName == "" || lastName == "" || displayName == "" ) {
            Materialize.toast('تمامی فیلدها اجباری می باشد', 4000, 'info-toast');
        } else if (userId <= 0 && (pas == "" || retryPas == "")) {
            Materialize.toast('تمامی فیلدها اجباری می باشد', 4000, 'info-toast');
        }
        else {
            var roles = getCheckListArray();
            if (userId <= 0) {
                $.ajax({
                    type: "POST",
                    url: "/user/api/checkUserName",
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    data: checkUserName.toString(),
                    success: function (data) {
                        if (!data) {
                            if (pas === retryPas) {
                                var dataArray = [];
                                var dataItem = {};


                                dataItem["userId"] = userId;
                                dataItem["userName"] = $("#txtUserName").val();
                                dataItem["password"] = $("#txtPassword").val();
                                dataItem["personalNumber"] = $("#txtPersonalNumber").val();
                                dataItem["firstName"] = $("#txtFirstName").val();
                                dataItem["lastName"] = $("#txtLastName").val();
                                dataItem["displayName"] = $("#txtDisplayName").val();
                                dataItem["tel"] = $("#txtTel").val();
                                dataItem["mobile"] = $("#txtMobile").val();
                                dataItem["locked"] = $("#chkLocked").is(':checked') ? 1 : 0;
                                dataItem["imageName"] = imageName;
                                dataItem["hospitalSections"] = roles;
                                dataArray.push(dataItem);
                                $.ajax({
                                    type: "POST",
                                    url: "/user/api/addUser",
                                    contentType: "application/json; charset=utf-8",
                                    dataType: 'json',
                                    data: JSON.stringify(dataArray),
                                    success: function (data) {
                                        if (data) {
                                            window.location.reload();
                                        } else {
                                            Materialize.toast('خطا در انجام عملیات!', 4000, 'error-toast');
                                        }
                                    }
                                });
                            } else {
                                Materialize.toast('کلمات عبور باهم برابر نیستند!', 4000, 'info-toast');
                            }
                        } else if (data == -1) {
                            Materialize.toast('خطا در انجام عملیات!', 4000, 'error-toast');
                        }
                        else {
                            Materialize.toast('نام کاربری قبلا انتخاب شده است!', 4000, 'error-toast');
                        }
                    }
                });
            } else {
                var dataArray = [];
                var dataItem = {};

                dataItem["userId"] = userId;
                dataItem["userName"] = $("#txtUserName").val();
                dataItem["password"] = $("#txtPassword").val();
                dataItem["personalNumber"] = $("#txtPersonalNumber").val();
                dataItem["firstName"] = $("#txtFirstName").val();
                dataItem["lastName"] = $("#txtLastName").val();
                dataItem["displayName"] = $("#txtDisplayName").val();
                dataItem["tel"] = $("#txtTel").val();
                dataItem["mobile"] = $("#txtMobile").val();
                dataItem["locked"] = $("#chkLocked").is(':checked') ? 1 : 0;
                dataItem["imageName"] = imageName;
                dataItem["hospitalSections"] = roles;

                dataArray.push(dataItem);
                $.ajax({
                    type: "POST",
                    url: "/user/api/addUser",
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    data: JSON.stringify(dataArray),
                    success: function (data) {
                        if (data) {
                            window.location.reload();
                        } else {
                            Materialize.toast('خطا در انجام عملیات!', 4000, 'error-toast');
                        }
                    }
                });
            }


        }
    }

    function getCheckListArray() {
        var checkList = $("#roleListDiv").children();
        var array = [];
        $.each(checkList, function (count, dataItem) {
            if (dataItem.childNodes[0].childNodes[0].checked) {

                var item = {};
                item["hospitalSectionId"] = dataItem.childNodes[0].childNodes[0].value;
                array.push(item);
            }
            ;
        });
        return array;
    }

    function getUserForEdit(sender) {
        userId = sender.id.split("_")[1];

        $.ajax({
            type: "POST",
            url: "/user/api/getUserForEdit",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: userId.toString(),
            success: function (data) {
                if (data == "false") {
                    Materialize.toast('خطا در انجام عملیات!', 4000, 'error-toast');
                } else {
                    var user = data[0];
                    clearForm();
                    $("#txtPassword").val("").prop("disabled", true);
                    $("#txtRetryPassword").val("").prop("disabled", true);
                    $("#txtUserName").val(user.userName).prop("disabled", true);
                    $("#txtPersonalNumber").val(user.personalNumber);
                    $("#txtFirstName").val(user.firstName);
                    $("#txtLastName").val(user.lastName);
                    $("#txtDisplayName").val(user.displayName);
                    $("#txtTel").val(user.tel);
                    $("#txtMobile").val(user.mobile);
                    $("#chkLocked").prop('checked', user.locked);
                    $("#imgUser").attr("src", "/static/userImage/" + user.imageName);

                    if(user.hospitalSection!= undefined &&user.hospitalSection.length>0)
                    {
                        $("#ddlHospital").val(user.hospitalSection[0].hospital.hospitalId.toString()).prop("disabled",true);
                        createHospitalSectionDiv(user.hospitalSection);
                        checkRoles(user.usershospitalSection);
                    }
                    imageName = user.imageName;

                    $('#userWindow').modal('open');
                }
            }
        });
    }

    function checkRoles(roleList) {
        var checkList = $("#roleListDiv").children();
        $.each(checkList, function (count, dataItem) {
            $.each(roleList, function (count, hospitalSection) {

                if (dataItem.childNodes[0].childNodes[0].value == hospitalSection.hospitalSection.hospitalSectionId) {
                    dataItem.childNodes[0].childNodes[0].checked = true;

                }
            });
        });
    }

    function showUserWindow() {
        clearForm();
        $('#userWindow').modal('open');
        $("#txtUserName").focus();
    }

    function processUpload() {
        var oMyForm = new FormData();
        var file_data = $('input[type=file]')[0].files[0];

        oMyForm.append('file', file_data);
        imageName = file_data.name;

        $.ajax({
            dataType: 'json',
            url: "/user/api/uploadImage",
            data: oMyForm,
            type: "POST",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (result) {
                if (result) {
                    var reader = new FileReader();
                    reader.onload = function () {
                        $('#imgUser').attr("src", reader.result);
                    };
                    reader.readAsDataURL($('input[type=file]')[0].files[0]);
                }
            },
            error: function (result) {
                alert("error");
            }
        });
    }

    function clearForm() {
        $("#txtPassword").prop("disabled", false).val("");
        $("#txtRetryPassword").prop("disabled", false).val("");
        $("#txtUserName").prop("disabled", false).val("");
        $("#txtPersonalNumber").val("");
        $("#txtFirstName").val("");
        $("#txtLastName").val("");
        $("#txtDisplayName").val("");
        $("#txtTel").val("");
        $("#txtMobile").val("");
        $("#chkLocked").prop("checked", false)

        $("#imgUser").attr("src", "/static/userImage/boy.png");
        $('#ddlHospital').val("0");
        $("#roleListDiv").empty();
        var checkList = $("#roleListDiv").children();

        $.each(checkList, function (count, dataItem) {
            dataItem.childNodes[0].childNodes[0].checked = false;
        });

        $('ul.tabs').tabs('select_tab', 'userInfo');
    }

    function pagination() {
        var req_num_row = 8;
        var $tr = jQuery('tbody tr');
        var total_num_row = $tr.length;
        var num_pages = 0;
        if (total_num_row % req_num_row == 0) {
            num_pages = total_num_row / req_num_row;
        }
        if (total_num_row % req_num_row >= 1) {
            num_pages = total_num_row / req_num_row;
            num_pages++;
            num_pages = Math.floor(num_pages++);
        }
        for (var i = 1; i <= num_pages; i++) {
            if (i == 1) {
                jQuery('.pagination').append("<li class='active'><a href='#!'>" + i + "</a></li>");
            } else
                jQuery('.pagination').append("<li class='waves-effect'><a href='#!'>" + i + "</a></li>");
        }

        jQuery('.pagination').append("<li class='waves-effect'><a href='#!' onclick='nextPage();'><i class='material-icons'>chevron_left</i></a></li>");

        $tr.each(function (i) {
            jQuery(this).hide();
            if (i + 1 <= req_num_row) {
                $tr.eq(i).show();
            }

        });

        jQuery('#pagination a').click(function (e) {
            e.preventDefault();
            $tr.hide();
            var page = jQuery(this).text();
            var temp = page - 1;
            var start = temp * req_num_row;
            //alert(start);

            for (var i = 0; i < req_num_row; i++) {

                $tr.eq(start + i).show();

            }
        });
    }

    function nextPage() {
        var currentPage = $(".pagination .active").text();

        debugger;
    }
</script>

<%@include file="footer.jsp" %>