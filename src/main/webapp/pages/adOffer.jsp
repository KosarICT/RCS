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

    #grvOffer th input[type=text]:focus:not([readonly]) {
        border-bottom: none !important;
        box-shadow: none !important;
    }
</style>

<div class="row">
    <%--    <nav>
            <div class="nav-wrapper grey lighten-4" style="border: 1px solid #e0e0e0">
                <ul class="left ">
                    <li>
                        <a href="#" class="notification-text" onclick="btnViewOfferClick()">
                            <i class="material-icons right notification-text">visibility</i>مشاهده
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <table id="tblOffer" class="left bordered responsive-table textColor">
            <thead>
            <tr>
                <th class="center">ردیف</th>
                <th class="center">نام و نام خانوادگی پیشنهاد دهنده</th>
                <th class="center">کد ملی</th>
                <th class="center">موضوع</th>
                <th class="center">نام بیمارستان</th>
                <th class="center">بخش بیمارستان</th>
            </tr>
            </thead>

            <c:if test="${not empty offerList}">
                <c:set var="row" value="1" scope="page"/>

                <tbody class="data-wrapper">
                <c:forEach var="entry" items="${offerList}">

                    <tr data-uid="${entry.offerId}">

                        <td class="center counter"><c:out value="${row}"/></td>
                        <td class="center">${entry.firstName} ${entry.lastName}</td>
                        <td class="center">${entry.nationalCode}</td>
                        <td class="center">${entry.subject}</td>
                        <td class="center">${entry.hospital.name}</td>
                        <td class="center">${entry.section.title}</td>

                    </tr>

                    <c:set var="row" value="${row + 1}" scope="page"/>
                </c:forEach>
                </tbody>

            </c:if>
        </table>--%>

    <div class="k-rtl">
        <div id="grvOffer"></div>
    </div>
</div>

<div id="offerWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        مشاهده اطلاعات
    </div>
    <div class="modal-content">
        <div class="row">
            <input disabled id="hiddenTicketId" type="text" class="validate notification-text" hidden>
            <div class="row">
                <label for="txtName" style="font-size: 13px; font-weight: 500; color: #707070">نام نام خانوادگی پیشنهاد
                    دهنده:</label>
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
                <label for="txtDescription" style="font-size: 13px; font-weight: 500; color: #707070"> متن
                    پیشنهاد:</label>
                <textarea class="materialize-textarea validate notification-text" disabled id="txtDescription"
                          length="4000"></textarea>
            </div>
            <div class="row">
                <label for="txtEmail" style="font-size: 13px; font-weight: 500; color: #707070">ایمیل:</label>
                <input disabled id="txtEmail" type="text" class="validate notification-text">
            </div>
            <div class="row">
                <label for="txtTrackingCode" style="font-size: 13px; font-weight: 500; color: #707070">کد
                    رهگیری:</label>
                <input disabled id="txtTrackingCode" type="text" class="validate notification-text">
            </div>
        </div>
        <div class="row"></div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btnOk" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="offerWindowToolbarButtonClick(this)">
            <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
        </a>
        <a href="#" id="btnStop" class="modal-action waves-effect waves-light btn-flat notification-text"
           onclick="offerWindowToolbarButtonClick(this)">
            <img src="/static/icon/stop5.png" class="windowToolbarImage">خاتمه
        </a>
    </div>
</div>

<script>
    var ticketTypeId = 2;

    $(document).ready(function () {
        $(".page-title").text("پیشنهادات");
        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initGrid();
        initWindow();
    });

    function initGrid() {
        $("#grvOffer").kendoGrid({
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
                fileName: "پیشنهادات.xlsx",
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
                    field: "mobile", title: "موبایل", width: "120px", filterable: {
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
                            btnViewOfferClick(dataItem);
                        }
                    }, title: "&nbsp;", width: "120px"
                }
            ]
        });
    }

    function initWindow() {
        $('#offerWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '4%',
                complete: function () {
                    refreshOfferTable();
                }
            }
        );
    }

    function btnViewOfferClick(dataItem) {
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
                    $("#txtSubject").val(data.subject);
                    $("#txtHospitalName").val(data.hospitalName);
                    $("#txtSectionTitle").val(data.sectionTitle);
                    $("#txtDescription").val(data.description);
                    $("#txtEmail").val(data.email);
                    $("#hiddenTicketId").val(ticketId);
                }
            });

            $('#offerWindow').modal('open');
        } else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
    }

    function refreshOfferTable() {
        $('#grvOffer').data('kendoGrid').dataSource.read();
        $('#grvOffer').data('kendoGrid').refresh();
    }

    function offerWindowToolbarButtonClick(sender) {
        var id = sender.id;

        switch (id) {
            case "btnOk":
                $('#offerWindow').modal('close');
                break;
            case "btnStop":
                finishOffer();
                break;
        }
    }
    function finishOffer() {
        var ticketId = $("#hiddenTicketId").val();
        debugger;
        $.ajax({
            type: "POST",
            url: "/adComplain/api/finishTicket",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: ticketId.toString(),
            success: function (data) {
                if (data) {
                    ticketId = 0;
                    $('#offerWindow').modal('close');
                    Materialize.toast('عملیات با موفقیت انجام شد', 4000, 'success-toast');
                } else {
                    Materialize.toast('خطا در انجام عملیات', 4000, 'error-toast');
                }
            }
        });
    }

</script>

<%@include file="footer.jsp" %>