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

    #grvAppreciation th input[type=text]:focus:not([readonly]) {
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
        <div id="grvAppreciation"></div>
    </div>
</div>

<div id="appreciationWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        مشاهده اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">
            <input disabled id="hiddenTicketId" type="text" class="validate notification-text" hidden>

            <div class="row">
                <label for="txtName" style="font-size: 13px; font-weight: 500; color: #707070">نام نام خانوادگی:</label>
                <input disabled id="txtName" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کدملی
                    بیمار:</label>
                <input disabled id="txtNationalCode" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtPersonnelName" style="font-size: 13px; font-weight: 500; color: #707070">نام پرسنل
                    :</label>
                <input disabled id="txtPersonnelName" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtMobile" style="font-size: 13px; font-weight: 500; color: #707070">تلفن همراه:</label>
                <input disabled id="txtMobile" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtHospitalName" style="font-size: 13px; font-weight: 500; color: #707070">نام بیمارستان
                    :</label>
                <input disabled id="txtHospitalName" type="text" class="validate notification-text">

            </div>

            <div class="row">
                <label for="txtSectionTitle" style="font-size: 13px; font-weight: 500; color: #707070">بخش کاری:</label>
                <input disabled id="txtSectionTitle" type="text" class="validate notification-text">
            </div>

            <div class="row">
                <label for="txtDescription" style="font-size: 13px; font-weight: 500; color: #707070">متن
                    قدردانی:</label>
                <textarea class="materialize-textarea validate notification-text" disabled id="txtDescription"
                          length="4000"></textarea>
            </div>

            <div class="row">
                <label style="display:block; color: #707070; font-weight: 500; font-size: 13px; margin-bottom: 20px">فایل
                    های پیوست:</label>

                <div class="row" id="attachmentArea"></div>
            </div>
        </div>
        <div class="row"></div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="appreciationWindowToolbarClickButton(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
        <a href="#" id="btnStop" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="appreciationWindowToolbarClickButton(this)">
            <img src="/static/icon/stop5.png" class="windowToolbarImage">خاتمه
        </a>
    </div>
</div>

<script>
    var ticketTypeId = 1;
    var screenHeight;


    $(document).ready(function () {
        $(".page-title").text("قدردانی");


        $(".row").persiaNumber();

        screenHeight = window.height - 60;

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initGrid();
        initWindow();
    });

    function initGrid() {
        $("#grvAppreciation").kendoGrid({
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
                fileName: "قدردانی.xlsx",
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
                    field: "name", title: "نام و نام خانوادگی", filterable: {
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
                    field: "personnelName", title: "نام و نام خانوادگی پرسنل", filterable: {
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
                    field: "sectionName", title: "بخش", filterable: {
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
                            btnViewClick(dataItem);
                        }
                    }, title: "&nbsp;", width: "120px"
                }
            ]
        });
    }

    function initWindow() {
        $('#appreciationWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '4%',
                complete: function () {
                    refreshAppreciationTable();
                }
            }
        );
    }

    function btnViewClick(dataItem) {

        if (dataItem != null) {

            var ticketId = dataItem.ticketId;
            $.ajax({
                type: "POST",
                url: "/ticket/api/findTicketByTicketId",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: ticketId.toString(),
                success: function (data) {

                    $("#txtName").val(data.firstName + " " + data.lastName);
                    $("#txtNationalCode").val(data.nationalCode);
                    $("#txtPersonnelName").val(data.persnolFirstName + " " + data.persnolLastName);
                    $("#txtMobile").val(data.mobile);
                    $("#txtHospitalName").val(data.hospitalName);
                    $("#txtSectionTitle").val(data.sectionTitle);
                    $("#txtDescription").val(data.description);
                    $("#hiddenTicketId").val(ticketId);

                    var attachList = data.ticketAttachmentList;

                    for (var i = 0; i < attachList.length; i++) {

                        var div = $("<div>").addClass("card").addClass("attachment").addClass("left");
                        var img = $("<img>").attr("src", "../static/icon/attach.png").attr("onclick", "btnDownloadAttachment('" + attachList[i].fileName + "." + attachList[i].fileType + "');");

                        div.append(img);

                        $("#attachmentArea").append(div);
                    }
                }
            });

            $('#appreciationWindow').modal('open');
        } else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
    }

    function refreshAppreciationTable() {
        $('#grvAppreciation').data('kendoGrid').dataSource.read();
        $('#grvAppreciation').data('kendoGrid').refresh();
    }

    function appreciationWindowToolbarClickButton(sender) {
        var id = sender.id;

        switch (id) {
            case "btnOk":
                $('#appreciationWindow').modal('close');
                break;
            case "btnStop":
                finishAppreciation();
                break;
        }
    }

    function finishAppreciation() {
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
                    $('#appreciationWindow').modal('close');
                    Materialize.toast('عملیات با موفقیت انجام شد', 4000, 'success-toast');
                } else {
                    Materialize.toast('خطا در انجام عملیات', 4000, 'error-toast');
                }
            }
        });
    }

</script>

<%@include file="footer.jsp" %>