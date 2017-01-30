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

    #grvArchive th input[type=text]:focus:not([readonly]) {
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
        <div id="grvArchive"></div>
    </div>
</div>

<div id="archiveWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        مشاهده اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">
            <input disabled id="hiddenTicketId" type="text" class="validate notification-text" hidden>

            <div class="row">
                <label for="txtTicketTypeTitle" style="font-size: 13px; font-weight: 500; color: #707070">نوع
                    گزارش:</label>
                <input disabled id="txtTicketTypeTitle" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtName" style="font-size: 13px; font-weight: 500; color: #707070">نام نام خانوادگی بیمار :
                    </label>
                <input disabled id="txtName" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کدملی
                    بیمار:</label>
                <input disabled id="txtNationalCode" type="text" class="validate notification-text">

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
                <label for="txtSectionTitle" style="font-size: 13px; font-weight: 500; color: #707070">بخش
                    بیمارستان:</label>
                <input disabled id="txtSectionTitle" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtSubject" style="font-size: 13px; font-weight: 500; color: #707070">موضوع:</label>
                <input disabled id="txtSubject" type="text" class="validate notification-text">
            </div>

            <div class="row">
                <label for="txtDescription" style="font-size: 13px; font-weight: 500; color: #707070"> توضیحات:</label>
                <textarea class="materialize-textarea validate notification-text" disabled id="txtDescription"
                          length="4000"></textarea>
            </div>

            <div id="appreciationDiv" style="display: none">
                <div class="row">
                    <label for="txtPersonnelName" style="font-size: 13px; font-weight: 500; color: #707070">نام پرسنل
                        :</label>
                    <input disabled id="txtPersonnelName" type="text" class="validate notification-text">

                </div>
            </div>

            <div id="complainDiv" style="display: none">
                <div class="row">
                    <label for="txtComplainantTitle" style="font-size: 13px; font-weight: 500; color: #707070">شکایت
                        کننده:</label>
                    <input disabled id="txtComplainantTitle" type="text" class="validate notification-text">
                </div>
                <div class="row">
                    <label for="txComplaintTypeTitle" style="font-size: 13px; font-weight: 500; color: #707070">نوع
                        شکایت:</label>
                    <input disabled id="txComplaintTypeTitle" type="text" class="validate notification-text">

                </div>
                <div class="row">
                    <label for="txtShiftTitle" style="font-size: 13px; font-weight: 500; color: #707070">شیفت
                        کاری:</label>
                    <input disabled id="txtShiftTitle" type="text" class="validate notification-text">
                </div>
            </div>

        </div>
        <div class="row"></div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="archiveWindowToolbarButtonClick()">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
    </div>
</div>

<script>
    $(document).ready(function () {
        $(".page-title").text("بایگانی");

        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initGrid();
        initWindow();
    });

    function initGrid() {
        $("#grvArchive").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "/archive/api/getArchiveList",
                        type: "GET",
                        contentType: "application/json",
                        dataType: "json",
                    },
                },
                pageSize: 5
            },
            sortable: {
                mode: "single",
                allowUnsort: false
            },
            toolbar: [{name: "excel", text: "دریافت فایل اکسل"}],
            excel: {
                fileName: "بایگانی.xlsx",
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

                {field: "ticketId", hidden: true},
                {
                    field: "ticketTypeTitle", title: "نوع گزارش", filterable: {
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
                    field: "ticketSubject", title: "عنوان گزارش", filterable: {
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
                    field: "hospitalName", title: "نام مرکز", width: "100px", filterable: {
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
                    field: "sectionTitle", title: "واحد", width: "120px", filterable: {
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
                    field: "sendTypeTitle", title: "نوع ارسال", filterable: {
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
                    field: "submitDate", title: "تاریخ و ساعت", filterable: {
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
                            btnViewArchiveClick(dataItem);
                        }
                    }, title: "&nbsp;", width: "120px"
                }
            ],
        });
    }

    function initWindow() {
        $('#archiveWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '4%',
                complete: function () {
                    refreshArchiveTable();
                }
            }
        );
    }

    function btnViewArchiveClick(dataItem) {
        if (dataItem != null) {

            $("#appreciationDiv").css("display", "none");
            $("#complainDiv").css("display", "none");
            var ticketId = dataItem.ticketId;

            $.ajax({
                type: "POST",
                url: "/ticket/api/findTicketByTicketId",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: ticketId.toString(),
                success: function (data) {

                    var ticketTypeId = data.ticketType_Id;

                    $("#txtTicketTypeTitle").val(data.ticketTypeTitle);
                    $("#txtSubject").val(data.subject);
                    $("#txtName").val(data.firstName + " " + data.lastName);
                    $("#txtNationalCode").val(data.nationalCode);
                    $("#txtMobile").val(data.mobile);
                    $("#txtHospitalName").val(data.hospitalName);
                    $("#txtSectionTitle").val(data.sectionTitle);
                    $("#txtDescription").val(data.description);
                    $("#hiddenTicketId").val(ticketId);

                    if (ticketTypeId == 1) {
                        $("#appreciationDiv").css("display", "block");
                        $("#txtPersonnelName").val(data.persnolFirstName + " " + data.persnolLastName);
                    }

                    if (ticketTypeId == 3) {
                        $("#complainDiv").css("display", "block");
                        $("#txtComplainantTitle").val(data.complainantTitle);
                        $("#txComplaintTypeTitle").val(data.complaintTypeTitle);
                        $("#txtShiftTitle").val(data.shiftTitle);
                    }
                }
            });

            $('#archiveWindow').modal('open');
        } else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
    }

    function refreshArchiveTable() {
        $('#grvArchive').data('kendoGrid').dataSource.read();
        $('#grvArchive').data('kendoGrid').refresh();
    }

    function archiveWindowToolbarButtonClick() {
        $('#archiveWindow').modal('close');
    }

</script>

<%@include file="footer.jsp" %>