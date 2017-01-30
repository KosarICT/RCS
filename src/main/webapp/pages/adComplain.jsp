<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<style>
    .modal select {
        display: block;
    }

    table {
        font-size: 13px !important;
    }

    tbody tr {
        height: 59px !important;
    }

    tr td {
        text-align: right !important;
        border-bottom: 1px solid #c8c8c8 !important;
    }

    #grvComplaint th input[type=text]:focus:not([readonly]) {
        border-bottom: none !important;
        box-shadow: none !important;
    }

    .textBox{
        width: 90%;
        border: 1px solid #bfbfbf !important;
        height: 33px !important;
        background: #fff !important;
        color: #a8a8a8;
    }

    .k-grid-pager a{
        margin-top: 3px !important;
    }
    .k-grid-pager a span{
        margin-top: 5px !important;
    }

    .k-dropdown span{
        margin-top: 5px !important;
    }
</style>

<div class="row">
    <div class="k-rtl">
        <div id="grvComplaint"></div>
    </div>
</div>

<div id="complainWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        مشاهده اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">
            <div class="row">
                <label for="txtComplainantTitle" style="font-size: 13px; font-weight: 500; color: #707070">شکایت
                    کننده:</label>
                <input disabled id="txtComplainantTitle" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtName" style="font-size: 13px; font-weight: 500; color: #707070">نام بیمار:</label>
                <input disabled id="txtName" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کدملی
                    بیمار:</label>
                <input disabled id="txtNationalCode" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtTel" style="font-size: 13px; font-weight: 500; color: #707070">تلفن تماس:</label>
                <input disabled id="txtTel" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtMobile" style="font-size: 13px; font-weight: 500; color: #707070">تلفن همراه:</label>
                <input disabled id="txtMobile" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtRelationName" style="font-size: 13px; font-weight: 500; color: #707070">نام همراه
                    بیمار:</label>
                <input disabled id="txtRelationName" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtRelationNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کدملی
                    همراه بیمار:</label>
                <input disabled id="txtRelationNationalCode" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txComplaintTypeTitle" style="font-size: 13px; font-weight: 500; color: #707070">نوع
                    شکایت:</label>
                <input disabled id="txComplaintTypeTitle" type="text" class="validate notification-text">

            </div>

            <input disabled id="hiddenHospitalId" type="text" class="validate notification-text" hidden>
            <input disabled id="hiddenTicketId" type="text" class="validate notification-text" hidden>

            <div class="row">
                <label for="txtShiftTitle" style="font-size: 13px; font-weight: 500; color: #707070">شیفت کاری:</label>
                <input disabled id="txtShiftTitle" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtSectionTitle" style="font-size: 13px; font-weight: 500; color: #707070">بخش کاری:</label>
                <input disabled id="txtSectionTitle" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtSubject" style="font-size: 13px; font-weight: 500; color: #707070">موضوع:</label>
                <input disabled id="txtSubject" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtDescription" style="font-size: 13px; font-weight: 500; color: #707070">توضیحات:</label>
                <textarea class="materialize-textarea validate notification-text" disabled id="txtDescription"
                          length="4000"></textarea>
            </div>
            <div class="row">
                <label for="txtSubmitDate" style="font-size: 13px; font-weight: 500; color: #707070">تاریخ
                    ارسال:</label>
                <input disabled id="txtSubmitDate" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtEmail" style="font-size: 13px; font-weight: 500; color: #707070">پست الکترونیک:</label>
                <input disabled id="txtEmail" type="text" class="validate notification-text">
            </div>
        </div>
        <div class="row"></div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnErrand" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="complaintWindowToolBarItemClick(this)">
            <img src="/static/icon/ok3.png" class="windowToolbarImage">ارجاع
        </a>
        <a href="#" id="btnStop" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="complaintWindowToolBarItemClick(this)">
            <img src="/static/icon/stop5.png" class="windowToolbarImage">خاتمه
        </a>
        <a href="#" id="btnOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="complaintWindowToolBarItemClick(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
    </div>
</div>

<div id="errandWindow" class="modal modal-fixed-footer " style="width: 400px">
    <div class="windowHeader">
        ارجاع
    </div>

    <div class="modal-content">
        <div class="col m3 l3"></div>
        <div class="row">
            <div class="row">
                <label style="font-size: 13px; font-weight: 500; color: #707070">انتخاب بخش:</label>
                <select id="ddlSection" onchange="ddlSectionChange();">
                    <option value="" disabled selected>بخش موردنظر انتخاب نمائید</option>
                    <c:if test="${not empty hospitalSectionList}">
                        <c:forEach var="hospitalSectionEntry" items="${hospitalSectionList}">
                            <option value="${hospitalSectionEntry.section.sectionId}">${hospitalSectionEntry.section.title}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            <div class="row">
                <label style="font-size: 13px; font-weight: 500; color: #707070">انتخاب کاربر:</label>
                <select id="ddlUser">
                </select>
            </div>
            <div class="row">
                <div class="col s12">
                    <label for="txtErrandDescription"
                           style="font-size: 13px; font-weight: 500; color: #707070">توضیحات:</label>
                    <textarea id="txtErrandDescription" class="materialize-textarea" length="4000"></textarea>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnErrandOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="errandWindowToolBarItemClick(this)">
            <img src="/static/icon/ok3.png" class="windowToolbarImage">تایید
        </a>
        <a href="#" id="btnErrandCancel" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="errandWindowToolBarItemClick(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
    </div>
</div>

<script>
    var ticketTypeId = 3;

    $(document).ready(function () {
        $(".page-title").text("شکایات");
        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initGrid();
        initWindow();
    });

    function initGrid() {
        $("#grvComplaint").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "/ticket/api/getData",
                        type: "POST",
                        contentType: "application/json",
                        dataType: "json",
                    },
                    parameterMap: function () {

                        return ticketTypeId.toString();
                    }
                },
                pageSize: 5
            },
            sortable: {
                mode: "single",
                allowUnsort: false
            },
            toolbar: [{name: "excel", text: "دریافت فایل اکسل"}],
            excel: {
                fileName: "شکایات.xlsx",
                filterable: true
            },
            filterable: {
                mode: "row"
            },
            selectable: "single",
            pageable: {
                pageSizes: true,
                messages: {
                    itemsPerPage: "",
                    display: "نمایش {0}-{1} آیتم از {2} آیتم",
                    empty: "اطلاعاتی برای نمایش وجود ندارد"
                }
            },
            columns: [
                {field: "ticketId", title: "UserId", hidden: true},
                {
                    field: "complainantTitle", title: "شکایت کننده", width: "100px", filterable: {
                    cell: {
                        showOperators: false,
                        operator: "contains",
                        template: function (args) {
                            args.element.css("width", "90%").addClass("textBox").keydown(function(e){
                                setTimeout(function(){
                                    $(e.target).trigger("change");
                                });
                            });
                        },
                    }
                }
                },
                {
                    field: "name", title: "بیمار", filterable: {
                    cell: {
                        showOperators: false,
                        operator: "contains",
                        template: function (args) {
                            args.element.css("width", "90%").addClass("textBox").keydown(function(e){
                                setTimeout(function(){
                                    $(e.target).trigger("change");
                                });
                            });
                        },
                    }
                }
                },
                {
                    field: "nationalCode", title: "کدملی", width: "120px", filterable: {
                    cell: {
                        showOperators: false,
                        operator: "contains",
                        template: function (args) {
                            args.element.css("width", "90%").addClass("textBox").keydown(function(e){
                                setTimeout(function(){
                                    $(e.target).trigger("change");
                                });
                            });
                        },
                    }
                }
                },
                {
                    field: "hospitalName", title: "بیمارستان", filterable: {
                    cell: {
                        showOperators: false,
                        operator: "contains",
                        template: function (args) {
                            args.element.css("width", "90%").addClass("textBox").keydown(function(e){
                                setTimeout(function(){
                                    $(e.target).trigger("change");
                                });
                            });
                        },
                    }
                }
                },
                {
                    field: "complaintTypeTitle", title: "نوع شکایت", filterable: {
                    cell: {
                        showOperators: false,
                        operator: "contains",
                        template: function (args) {
                            args.element.css("width", "90%").addClass("textBox").keydown(function(e){
                                setTimeout(function(){
                                    $(e.target).trigger("change");
                                });
                            });
                        },
                    }
                }
                },
                {
                    field: "sendTypeTitle", title: "طریقه ارتباط", width: "100px", filterable: {
                    cell: {
                        showOperators: false,
                        operator: "contains",
                        template: function (args) {
                            args.element.css("width", "90%").addClass("textBox").keydown(function(e){
                                setTimeout(function(){
                                    $(e.target).trigger("change");
                                });
                            });
                        },
                    }
                }
                },
                {
                    field: "submitDate", title: "تاریخ", width: "100px", filterable: {
                    cell: {
                        showOperators: false,
                        operator: "contains",
                        template: function (args) {
                            args.element.css("width", "90%").addClass("textBox").keydown(function(e){
                                setTimeout(function(){
                                    $(e.target).trigger("change");
                                });
                            });
                        },
                    }
                }
                },
                {
                    command: {
                        text: "مشاهده", click: function (e) {
                            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                            btnViewComplainClick(dataItem);
                        }
                    }, title: "&nbsp;", width: "120px"
                }
            ]
        });
    }

    function initWindow() {
        $('#complainWindow').modal({
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

        $('#errandWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '10%',
            }
        );
    }

    function btnViewComplainClick(dataItem) {

        if (dataItem != null) {
            var ticketId = dataItem.ticketId;
            $.ajax({
                type: "POST",
                url: "/ticket/api/findTicketByTicketId",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: ticketId.toString(),
                success: function (data) {

                    $("#txtComplainantTitle").val(data.complainantTitle);
                    $("#txtName").val(data.firstName + " " + data.lastName);
                    $("#txtNationalCode").val(data.nationalCode);
                    $("#txtTel").val(data.tel);
                    $("#txtMobile").val(data.mobile);

                    var complainatId = data.complainatId;
                    if (complainatId == 2) {
                        $("#txtRelationName").val(data.complainerName);
                        $("#txtRelationNationalCode").val(data.complainerNationalCode);
                    }


                    $("#txComplaintTypeTitle").val(data.complaintTypeTitle);
                    $("#txtShiftTitle").val(data.shiftTitle);
                    $("#hiddenHospitalId").val(data.hospitalId);
                    $("#hiddenTicketId").val(ticketId);
                    $("#txtSectionTitle").val(data.sectionTitle);
                    $("#txtSubject").val(data.subject);
                    $("#txtDescription").val(data.description);
                    $("#txtSubmitDate").val(data.submitDate);
                    $("#txtEmail").val(data.email);

                    $('#complainWindow').modal('open');
                }
            });
        } else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
    }

    function refreshTable() {
        $('#grvComplaint').data('kendoGrid').dataSource.read();
        $('#grvComplaint').data('kendoGrid').refresh();
    }

    function complaintWindowToolBarItemClick(sender) {
        var id = sender.id;

        switch (id) {
            case "btnOk":
                $('#complainWindow').modal('close');
                break;
            case "btnStop":
                finishTicket();
                break;
            case "btnErrand":
                openErrandWindow();
                break;
        }
    }

    function errandWindowToolBarItemClick(sender) {
        var id = sender.id;

        switch (id) {
            case "btnErrandOk":
                saveErrandComplain();
                break;
            case "btnErrandCancel":
                $('#errandWindow').modal('close');
                break;
        }
    }

    function saveErrandComplain() {
        var userId = $("#ddlUser option:selected").val();
        var description = $("#txtErrandDescription").val();
        var ticketId = $("#hiddenTicketId").val();

        var dataArray = [];
        var dataItem = {};

        dataItem["ticketId"] = ticketId;
        dataItem["userId"] = userId;
        dataItem["description"] = description;

        dataArray.push(dataItem);

        $.ajax({
            type: "POST",
            url: "/adComplain/api/saveComplainErrand",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(dataArray),
            success: function (data) {
                if (data) {
                    ticketId = 0;
                    $('#errandWindow').modal('close');
                    $('#complainWindow').modal('close');
                    Materialize.toast('عملیات با موفقیت انجام شد', 4000, 'success-toast');
                } else {
                    Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
                }
            }
        });
    }

    function ddlSectionChange() {
        var sectionId = $("#ddlSection option:selected").val();

        $.ajax({
            type: "POST",
            url: "/adComplain/api/getUserOfSection",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: sectionId.toString(),
            success: function (data) {
                $("#ddlUser option").remove();

                var defaultOption = $("<option>");
                defaultOption.text("کاربر موردنظر را انتخاب نمائید")
                    .prop("disabled", true)
                    .prop("selected", true)
                    .attr("value", "");

                $("#ddlUser").append(defaultOption);

                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {
                        var option = $("<option>");
                        option.text(dataItem.name).attr("value", dataItem.userId);

                        $("#ddlUser").append(option);
                    });
                }
            }
        });
    }
    function openErrandWindow() {
        var hospitalId = $("#hiddenHospitalId").val();

        $.ajax({
            type: "POST",
            url: "/adComplain/api/getHospitalSection",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: hospitalId.toString(),
            success: function (data) {
                $("#ddlSection option").remove();

                var defaultOption = $("<option>");
                defaultOption.text("کاربر موردنظر را انتخاب نمائید")
                    .prop("disabled", true)
                    .prop("selected", true)
                    .attr("value", "");

                $("#ddlSection").append(defaultOption);

                if (data.length > 0) {
                    $.each(data, function (index, dataItem) {
                        var option = $("<option>");
                        option.text(dataItem.sectionTitle).attr("value", dataItem.sectionId);

                        $("#ddlSection").append(option);
                    });
                }
                $('#errandWindow').modal('open');
            }
        });

    }

    function finishTicket() {
        var ticketId = $("#hiddenTicketId").val();

        $.ajax({
            type: "POST",
            url: "/adComplain/api/finishTicket",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: ticketId.toString(),
            success: function (data) {
                if (data) {
                    ticketId = 0;
                    $('#complainWindow').modal('close');
                    Materialize.toast('عملیات با موفقیت انجام شد', 4000, 'success-toast');
                } else {
                    Materialize.toast('خطا در انجام عملیات', 4000, 'error-toast');
                }
            }
        });
    }
</script>

<%@include file="footer.jsp" %>