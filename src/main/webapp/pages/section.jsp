<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="header.jsp" %>

<style>
    .modal {
        max-height: 90% !important;
        top: 5% !important;
        width: 500px;
    }
</style>

<div id="tblUser" class="row">
    <table class="bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">نام بخش</th>
            <th class="center">توضیحات</th>
        </tr>
        </thead>

        <c:if test="${not empty lists}">

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${lists}">
                <c:set var="userId" value="${entry.sectionId}" scope="page"/>
                <tr>
                    <td class="center" style="display: none">${entry.sectionId}</td>
                    <td class="center counter"><c:out value="${row}"/></td>
                    <td class="center">${entry.title}</td>
                    <td class="center">${entry.description}</td>
                    <td class="center">
                        <a id="btnEdit_${entry.sectionId}" href="#" class="mainTextColor"
                           style="margin-left: 5px" onclick="getSectionForEdit(this);">
                            <img src="/static/icon/edit1.png">
                        </a>
                    </td>
                </tr>

                <c:set var="row" value="${row + 1}" scope="page"/>
            </c:forEach>
            </tbody>
        </c:if>
    </table>
</div>

<div class="custom-fixed-action-btn">
    <a class="btn-floating btn-large waves-effect waves-light slideColor " href="#" onclick="showSectionWindow();"
       style="bottom: 35px; left: 35px;">
        <i class="material-icons">add</i>
    </a>
</div>


<div id="sectionWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        اضافه/ویرایش بخش
    </div>
    <div class="modal-content">
        <div class="row">

            <div class="col s12 m12 l12 right">

                <div class="row">
                    <label style="font-size: 13px; font-weight: 500; color: #707070">انتخاب کاربر:</label>
                    <input placeholder="نام بخش" id="txtSectionName" type="text" autocomplete="false" autofocus>
                </div>

                <div class="row">
                    <div class="col s12">
                        <label for="txtDescription"
                               style="font-size: 13px; font-weight: 500; color: #707070">توضیحات:</label>
                        <textarea id="txtDescription" class="materialize-textarea" length="4000"></textarea>
                    </div>
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

<script type="text/javascript">

    var sectionId = 0;

    $(document).ready(function () {
        $(".page-title").text("بخش ها");
        debugger;
        var section = "${lists}";



        var height = screen.height - 170;
        $("#container").css("height", height + "px").css("overflow", "auto").persiaNumber();

        initWindow();

        $('.fixed-action-btn').openFAB();
    });

    function initWindow() {
        $('#sectionWindow').modal({
                dismissible: false, // Modal can be dismissed by clicking outside of the modal
                opacity: .5, // Opacity of modal background
                in_duration: 300, // Transition in duration
                out_duration: 200, // Transition out duration
                starting_top: '10%', // Starting top style attribute
                ending_top: '10%',
                ready: function () {

                }
            }
        );
    }

    function btnAddClick() {
        var title = $("#txtSectionName").val();
        var description = $("#txtDescription").val();

        if (title == "") {
            Materialize.toast('تمامی فیلدها اجباری می باشد', 4000, 'info-toast');
        } else {

            var dataArray = [];
            var dataItem = {};

            dataItem["sectionId"] = sectionId;
            dataItem["title"] = title;
            dataItem["description"] = description;

            dataArray.push(dataItem);
            $.ajax({
                type: "POST",
                url: "/section/api/save",
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

    function getSectionForEdit(sender) {
        sectionId = sender.id.split("_")[1];

        $.ajax({
            type: "POST",
            url: "/section/api/getSectionForEdit",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: sectionId.toString(),
            success: function (data) {

                $("#txtSectionName").val(data[0].title);
                $("#txtDescription").val(data[0].description);

                $('#sectionWindow').modal('open');
            }
        });
    }

    function showSectionWindow() {
        clearForm();
        $('#sectionWindow').modal('open');
        $("#txtSectionName").focus();
    }

    function clearForm() {
        $("#sectionWindow").val("");
        $("#txtSectionName").val("");
    }

</script>

<%@include file="footer.jsp" %>