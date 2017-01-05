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
        width: 500px;

    }

    .modal select {
        display: block;
    }

    [type="checkbox"] + label {
        padding-left: 19px !important;
    }
</style>

<nav>
    <div class="nav-wrapper grey lighten-4" style="border: 1px solid #e0e0e0">
        <ul class="left ">
            <li>
                <a href="#" class="notification-text" onclick="btnViewHospitalSection()">
                    <i class="material-icons right notification-text">visibility</i>مشاهده
                </a>
            </li>
        </ul>
    </div>
</nav>

<div id="tblHospitalSection" class="row">
    <table class="bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">نام بیمارستان</th>
            <th class="center">بخش های بیمارستان</th>
        </tr>
        </thead>

        <c:if test="${not empty hospitalSectionList}">

            <c:set var="row" value="1" scope="page"/>

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${hospitalSectionList}">

                <tr data-uid="${entry.hospitalId}">
                        <%--<td class="center" style="display: none">${entry.hospitalId}</td>--%>
                    <td class="center counter"><c:out value="${row}"/></td>
                    <td class="center">${entry.hospitalName}</td>

                    <td class="center">${entry.sectionName}</td>
                </tr>

                <c:set var="row" value="${row + 1}" scope="page"/>
            </c:forEach>
            </tbody>

        </c:if>
    </table>

</div>

<div class="custom-fixed-action-btn">
    <a class="btn-floating btn-large waves-effect waves-light slideColor " href="#" onclick="showHospitalSectionWindow();"
       style="bottom: 35px; left: 35px;">
        <i class="material-icons">add</i>
    </a>
</div>

<div id="hospitalSectionWindow" class="modal modal-fixed-footer">
    <div class="windowHeader">
        اضافه/ویرایش کاربر
    </div>
    <div class="modal-content">
        <div>
            <div>

                <div>
                    <label for="ddlHospital">انتخاب بیمارستان:</label>
                    <select id="ddlHospital">
                        <option value="0" disabled selected>بیمارستان موردنظر انتخاب نمائید</option>
                        <c:if test="${not empty hospitalList}">
                            <c:forEach var="entry" items="${hospitalList}">
                                <option value="${entry.hospitalId}">${entry.name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
            </div>

        </div>
        <div id="sectionListDiv">
        </div>
    </div>

    <div class="modal-footer" style="background-color: #4f6789">
        <a href="#" class="modal-action  waves-effect waves-light btn-flat white-text" onclick="saveHospitalSection();"><img
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
    var isEdit = false;

    $(document).ready(function () {
        $(".page-title").text("بخش های بیمارستان");

        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        var height = screen.height - 200;
        $("#commentsDiv").css('height', height + 'px').css('overflow-y', 'auto');

        initWindow();
        cretSectionDiv();

        $('.fixed-action-btn').openFAB();

        $('.modal').modal();
    });

    function initWindow() {
        $('#hospitalSectionWindow').modal({
                dismissible: false, // Modal can be dismissed by clicking outside of the modal
                opacity: .5, // Opacity of modal background
                in_duration: 300, // Transition in duration
                out_duration: 200, // Transition out duration
                starting_top: '1%', // Starting top style attribute
                ending_top: '1%'
            }
        );
    }

    function showHospitalSectionWindow() {

        $('#hospitalSectionWindow').modal('open');
        clearForm();

    }

    function clearForm() {
        isEdit = false;
        var checkList = $("#sectionListDiv").children();

        $.each(checkList, function (count, dataItem) {
            dataItem.childNodes[0].childNodes[0].checked = false;
        });

        $('#ddlHospital').val("0");
    }

    function cretSectionDiv() {
        <c:if test="${not empty sectionList}">
        <c:forEach var="entry" items="${sectionList}">

        var sectionListDiv = $("#sectionListDiv");

        var div = $("<div>").css("width", "48%").css("display", "inline-block").css("margin", "2px");

        var pTag = $('<p>');

        var lblSectionName = $("<label>").attr("for", "${entry.sectionId}").css("width", "0");
        var chkSection = $("<input>").attr("type", "checkBox").attr("value", "${entry.sectionId}").attr("id", "${entry.sectionId}");
        var lblSectionText = $("<label>").text("${entry.title}").attr("for", "${entry.sectionId}").css("vertical-align", "top").css("margin-right", "8px");

        pTag.append(chkSection);
        pTag.append(lblSectionName);
        pTag.append(lblSectionText);

        div.append(pTag);

        sectionListDiv.append(div);

        </c:forEach>
        </c:if>

    }

    function saveHospitalSection() {
        var hospitalId = $("#ddlHospital option:selected").val();
        var sectionArray = getCheckListArray();

        if (hospitalId == 0) {
            Materialize.toast('بیمارستان مورد نظر را انتخاب کنید', 3000, 'error-toast');

        } else if (sectionArray.length == 0 && !isEdit) {
            Materialize.toast('یک یا چند بخش را انتخاب کنید', 3000, 'error-toast');

        } else {
            var dataArray = [];
            var dataItem = {};

            dataItem["hospitalId"] = hospitalId;
            dataItem["hospitalSectionArray"] = sectionArray;

            dataArray.push(dataItem);

            $.ajax({
                type: "POST",
                url: "/hospitalSection/api/saveHospitalSection",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: JSON.stringify(dataArray),
                success: function (data) {
                    if (data) {

                        $('#hospitalSectionWindow').modal('close');

                        Materialize.toast('عملیات با موفقیت انجام شد', 4000, 'success-toast');

                        refreshHospitalSectionTable();
                    } else {
                        Materialize.toast('خطا در انجام عملیات', 4000, 'error-toast');
                    }
                }
            });


        }
    }

    function getCheckListArray() {
        var checkList = $("#sectionListDiv").children();
        var array = [];
        $.each(checkList, function (count, dataItem) {
            if (dataItem.childNodes[0].childNodes[0].checked) {

                var item = {};
                item["sectionId"] = dataItem.childNodes[0].childNodes[0].value;
                array.push(item);
            }
            ;
        });
        return array;
    }

    function refreshHospitalSectionTable() {

        $.ajax({
            type: "GET",
            url: "/hospitalSection/api/getHospitalSectionData",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $("#tblHospitalSection tbody tr").remove();
debugger;
                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {

                        var tr = $("<tr>").attr("data-uid", dataItem.hospitalId);

                        var tdCounter = $("<td>").addClass("center").text(index + 1);
                        var tdHospitalName = $("<td>").addClass("center").text(dataItem.hospitalName);
                        var tdSectionName = $("<td>").addClass("center").text(dataItem.sectionName);;


                        tr.append(tdCounter);
                        tr.append(tdHospitalName);
                        tr.append(tdSectionName);

                        $("#tblHospitalSection tbody").append(tr);
                    });
                }

                $(".data-wrapper tr").click(function () {
                    $(this).addClass('selected').siblings().removeClass("selected");
                });
            }
        });
    }

    function btnViewHospitalSection() {
        if ($('.data-wrapper .selected').length > 0) {

            hospitalId = $('.data-wrapper .selected').data('uid');

            $.ajax({
                type: "POST",
                url: "/hospitalSection/api/findHospitalSectionByHospitalId",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: hospitalId.toString(),
                success: function (data) {
                    clearForm();
                    checkHospitalSection(data);
                    $('#ddlHospital').val(hospitalId).prop("disabled",true);

                    $('#hospitalSectionWindow').modal('open');

                    isEdit = true;
                }
            });

            $('#complainWindow').modal('open');
        } else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
    }

    function checkHospitalSection(sectionList) {
        var checkList = $("#sectionListDiv").children();
        var array = [];
        $.each(checkList, function (count, dataItem) {
            $.each(sectionList, function (count, sectionItem) {

                if (dataItem.childNodes[0].childNodes[0].value == sectionItem.sectionId) {
                    dataItem.childNodes[0].childNodes[0].checked = true;

                }
            });
        });
        return array;
    }


</script>

<%@include file="footer.jsp" %>