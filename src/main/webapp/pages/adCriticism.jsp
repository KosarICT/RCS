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

    #grvCriticism th input[type=text]:focus:not([readonly]) {
        border-bottom: none !important;
        box-shadow: none !important;
    }
</style>

<div class="row">

    <div class="k-rtl">
        <div id="grvCriticism"></div>
    </div>
</div>

<div id="criticismWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        مشاهده اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">
            <input disabled id="hiddenTicketId" type="text" class="validate notification-text" hidden>

            <div class="row">
                <label for="txtName" style="font-size: 13px; font-weight: 500; color: #707070">نام انتقاد کننده:</label>
                <input disabled id="txtName" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtNationalCode" style="font-size: 13px; font-weight: 500; color: #707070">کد ملی :</label>
                <input disabled id="txtNationalCode" type="text" class="validate notification-text">

            </div>
            <div class="row">
                <label for="txtMobile" style="font-size: 13px; font-weight: 500; color: #707070">تلفن همراه:</label>
                <input disabled id="txtMobile" type="text" class="validate notification-text">

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
                <label for="txtDescription" style="font-size: 13px; font-weight: 500; color: #707070"> متن انتقاد
                    :</label>
                <textarea class="materialize-textarea validate notification-text" disabled id="txtDescription"
                          length="4000"></textarea>
            </div>
            <div class="row">
                <label for="txtSubmitDate" style="font-size: 13px; font-weight: 500; color: #707070">تاریخ
                    انتقاد:</label>
                <input disabled id="txtSubmitDate" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtEmail" style="font-size: 13px; font-weight: 500; color: #707070">پست الکترونیک:</label>
                <input disabled id="txtEmail" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtTrackingCode" style="font-size: 13px; font-weight: 500; color: #707070">کد رهگیری
                    :</label>
                <input disabled id="txtTrackingCode" type="text" class="validate notification-text">
            </div>
        </div>
        <div class="row"></div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="criticismWindowToolBarItemClick(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
        <a href="#" id="btnStop" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="criticismWindowToolBarItemClick(this)">
            <img src="/static/icon/stop5.png" class="windowToolbarImage">خاتمه
        </a>
    </div>
</div>

<script>
    var ticketTypeId = 4;

    $(document).ready(function () {
        $(".page-title").text("انتقادات");
        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initGrid();
        initWindow();
    });

    function initGrid() {
        $("#grvCriticism").kendoGrid({
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
            },
            sortable: {
                mode: "single",
                allowUnsort: false
            },
            toolbar: [{name: "excel", text: "دریافت فایل اکسل"}],
            excel: {
                fileName: "انتقادات.xlsx",
                filterable: true
            },
            filterable: {
                mode: "row"
            },
            selectable: "single",
            columns: [
                {field: "ticketId", title: "UserId", hidden: true},
                {
                    field: "name", title: "نام و نام خانوادگی", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "nationalCode", title: "کدملی", width: "120px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "subject", title: "موضوع", width: "120px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "hospitalName", title: "بیمارستان", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "sectionName", title: "بخش", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "sendTypeTitle", title: "طریقه ارتباط", width: "100px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "submitDate", title: "تاریخ", width: "100px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    command: {
                        text: "مشاهده", click: function (e) {
                            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                            btnViewCriticismClick(dataItem);
                        }
                    }, title: "&nbsp;", width: "120px"
                }
            ]
        });
    }

    function initWindow() {
        $('#criticismWindow').modal({
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

        $('#criticismErrandWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '10%',
            }
        );
    }

    function btnViewCriticismClick(dataItem) {
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
                    $("#txtMobile").val(data.mobile);
                    $("#txtSectionTitle").val(data.sectionTitle);
                    $("#txtSubject").val(data.subject);
                    $("#txtDescription").val(data.description);
                    $("#txtEmail").val(data.email);
                    $("#txtTrackingCode").val(data.trackingCode);
                    $("#txtSubmitDate").val(data.trackingCode);
                    $("#hiddenTicketId").val(ticketId);

                }
            });

            $('#criticismWindow').modal('open');
        } else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
    }

    function refreshTable() {
        $('#grvCriticism').data('kendoGrid').dataSource.read();
        $('#grvCriticism').data('kendoGrid').refresh();
    }

    function criticismWindowToolBarItemClick(sender) {
        var id = sender.id;

        switch (id) {
            case "btnOk":
                $('#criticismWindow').modal('close');
                break;
            case "btnStop":
                finishCriticism();
                break;
        }
    }
    function finishCriticism() {
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
                    $('#criticismWindow').modal('close');
                    Materialize.toast('عملیات با موفقیت انجام شد', 4000, 'success-toast');
                } else {
                    Materialize.toast('خطا در انجام عملیات', 4000, 'error-toast');
                }
            }
        });
    }
</script>

<%@include file="footer.jsp" %>