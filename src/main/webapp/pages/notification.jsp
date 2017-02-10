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

    .modal {
        max-height: 90% !important;
        top: 5% !important;
    }

    #grvComplaint th input[type=text]:focus:not([readonly]) {
        border-bottom: none !important;
        box-shadow: none !important;
    }

    .textBox {
        width: 90%;
        border: 1px solid #bfbfbf !important;
        height: 33px !important;
        background: #fff !important;
        color: #a8a8a8;
    }

    .k-grid-pager a {
        margin-top: 3px !important;
    }

    .k-grid-pager a span {
        margin-top: 5px !important;
    }

    .k-dropdown span {
        margin-top: 5px !important;
    }
</style>

<div class="row">
    <div class="k-rtl">
        <div id="grvNotification"></div>
    </div>
</div>
<div id="addNotificationWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        اطلاعیه جدید
    </div>
    <div class="modal-content">
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
        </div>
        <div class="row">
            <label style="font-size: 13px; font-weight: 500; color: #707070">انتخاب کاربر:</label>
            <select id="ddlUser" multiple="multiple" data-placeholder="Select attendees...">
            </select>
        </div>
        <div class="row">
            <div class="col s12">
                <label for="txtSubject"
                       style="font-size: 13px; font-weight: 500; color: #707070">موضوع اطلاعیه:</label>
                <textarea id="txtSubject" class="materialize-textarea" length="500"></textarea>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <label for="txtBody"
                       style="font-size: 13px; font-weight: 500; color: #707070">متن اطلاعیه:</label>
                <textarea id="txtBody" class="materialize-textarea" length="4000"></textarea>
            </div>
        </div>
        <div class="modal-footer">
            <a href="#" id="btnNotificationOk" class="modal-action waves-effect waves-light btn-flat notification-text"
               onclick="notificationWindowToolBarItemClick(this)">
                <img src="/static/icon/ok3.png" class="windowToolbarImage">تایید
            </a>
            <a href="#" id="btnNotificationCancel"
               class="modal-action waves-effect waves-light btn-flat notification-text"
               onclick="notificationWindowToolBarItemClick(this)">
                <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
            </a>
        </div>
    </div>
</div>

<div id="notificationInfoWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        نمایش اطلاعیه
    </div>
    <div class="modal-content">
        <div class="row">
            <div class="col  m1 l1"></div>
            <div class="col s12 m10 l10">
                <input disabled id="notificationId" type="text" class="validate notification-text" hidden>

                <div class="row">
                    <label for="txtDateTime" style="font-size: 13px; font-weight: 500; color: #707070"> تاریخ اطلاعیه</label>
                    <input disabled id="txtDateTime" type="text" class="validate notification-text">
                </div>
                <div class="row">
                    <label for="txtShowSubject" style="font-size: 13px; font-weight: 500; color: #707070"> موضوع اطلاعیه</label>
                    <input disabled id="txtShowSubject" type="text" class="validate notification-text">
                </div>
                <div class="row">
                    <label for="txtShowBody" style="font-size: 13px; font-weight: 500; color: #707070">متن اطلاعیه</label>
                    <input disabled id="txtShowBody" type="text" class="validate notification-text">
                </div>
                <div>
                    <label for="ddlSelectUser" style="font-size: 13px; font-weight: 500; color: #707070">کاربران</label>
                    <select disabled id="ddlSelectUser" multiple="multiple" data-placeholder="Select attendees...">
                    </select>
                </div>
                <div class="row">پاسخ ها</div>
                <div id="pnlAnswers"></div>
                <div >
                    <a href="#" id="btnAnswerOk" class="modal-action waves-effect waves-light btn-flat notification-text"
                       onclick="answerWindowToolBarItemClick(this)">
                        <img src="/static/icon/ok3.png" class="windowToolbarImage">پاسخ
                    </a>
                    <a href="#" id="btnAnswerCancel"
                       class="modal-action waves-effect waves-light btn-flat notification-text"
                       onclick="answerWindowToolBarItemClick(this)">
                        <img src="/static/icon/cancel2.png" class="windowToolbarImage">انصراف
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="answerInfo" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
پاسخ
    </div>
    <div>
        <div class="row">
            <label style="font-size: 13px; font-weight: 500; color: #707070">انتخاب کاربر:</label>
            <select id="ddAnswelUser" multiple="multiple" data-placeholder="Select attendees...">
            </select>
        </div>
    </div>
</div>


<div class="custom-fixed-action-btn">
    <a class="btn-floating btn-large waves-effect waves-light slideColor " href="#" onclick="showNotificationWindow();"
       style="bottom: 35px; left: 35px;">
        <i class="material-icons">add</i>
    </a>
</div>

<script>
    var dataArray = [];
    var userList = [];
    var answerUser=[];
    $(document).ready(function () {
        $(".page-title").text("اطلاعیه ها");
        $(".row").persiaNumber();

        $(".data-wrapper tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });

        initGrid();
        initWindow();
        initMultiSelect();
    });

    function showNotificationWindow() {
        clearForm();
        $('#addNotificationWindow').modal('open');
        //$("#txtSectionName").focus();

    }

    function clearForm() {
        $("#txtSubject").val("");
        $("#txtBody").val("");

        var multi = $("#ddlUser").data("kendoMultiSelect");
        multi.value("");
    }

    function initWindow() {
        $('#addNotificationWindow').modal({
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

        $('#notificationInfoWindow').modal({
                dismissible: false,
                opacity: .5,
                in_duration: 300,
                out_duration: 200,
                starting_top: '4%',
                ending_top: '10%',
            }
        );
    }

    function notificationWindowToolBarItemClick(sender) {
        var id = sender.id;
        switch (id){
            case "btnNotificationOk":
                saveNotification();
                break;
            case "btnNotificationCancel":
                $('#addNotificationWindow').modal('close');
                break
        }
    }

    function answerWindowToolBarItemClick(sender) {
        var id = sender.id;
        switch (id){
            case "btnNotificationOk":
                $('#answerInfo').modal('open');
                break;
            case "btnNotificationCancel":
                $('#notificationInfoWindow').modal('close');
                break
        }
    }
    function saveNotification() {
        var subject = $("#txtSubject").val();
        var body = $("#txtBody").val();
        var userIds = $("#ddlUser").data('kendoMultiSelect').value();

        var dataArray = [];
        var dataItem = {};

        dataItem["subject"] = subject;
        dataItem["body"] = body;
        dataItem["userIds"] = userIds;

        dataArray.push(dataItem);
        $.ajax({
            type: "POST",
            url: "/notificationController/api/save",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(dataArray),
            success: function (data) {
                if (data) {
                    $('#addNotificationWindow').modal('close');
                    Materialize.toast('عملیات با موفقیت انجام شد', 4000, 'success-toast');
                } else {
                    Materialize.toast('خطا در انجام عملیات!', 4000, 'error-toast');
                }
            }
        });

    }

    function initGrid() {
        $("#grvNotification").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "/notificationController/api/getData",
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
                fileName: "اعلانات.xlsx",
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
                {field: "notificationId", title: "", hidden: true},
                {
                    field: "datetime", title: "تاریخ ایجاد", width: "100px", filterable: {
                    cell: {
                        showOperators: false,
                        operator: "contains",
                        template: function (args) {
                            args.element.css("width", "90%").addClass("textBox").keydown(function (e) {
                                setTimeout(function () {
                                    $(e.target).trigger("change");
                                });
                            });
                        },
                    }
                }
                },
                {
                    field: "subject", title: "موضوع", filterable: {
                    cell: {
                        showOperators: false,
                        operator: "contains",
                        template: function (args) {
                            args.element.css("width", "90%").addClass("textBox").keydown(function (e) {
                                setTimeout(function () {
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
                            btnViewNotificationClick(dataItem);
                        }
                    }, title: "&nbsp;", width: "120px"
                }
            ]
        });

    }

    function initMultiSelect() {
        $("#ddlUser").kendoMultiSelect({
            dataTextField: "name",
            dataValueField: "userId",
            dataSource: dataArray
        });

        $("#ddlSelectUser").kendoMultiSelect({
            dataTextField: "name",
            dataValueField: "userId",
            dataSource:userList
        });
    }

    function btnViewNotificationClick(dataItem) {
        if(dataItem !=null){
            var notificationId = dataItem.notificationId;
            $.ajax({
                type: "POST",
                url: "/notificationController/api/getNotification",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: notificationId.toString(),
                success: function (data) {

                    var info=data[0];
                    $("#txtShowSubject").val(info.subjct);
                    $("#txtShowBody").val(info.body);
                    $("#txtDateTime").val(info.dateTime);
                    userList = info.notificationAssigns;

                    var answers=info.notificationAnswers;


                    $('#notificationInfoWindow').modal('open');

                    var ctrl = $("#ddlSelectUser").data('kendoMultiSelect');
                    ctrl.setDataSource(new kendo.data.DataSource({ data: userList }));
                    var selectedUser=[];
                    $.each(userList, function (index, dataItem) {selectedUser.push(dataItem.userId)});

                    ctrl.value(selectedUser);

                    $("#pnlAnswers").empty();
                    $.each(answers, function (index, dataItem) {
                        debugger;
                            var div = $("<div>").css("border", "1px solid #c0c0c0").addClass("shadow");
                            var header = $("<div>");
                            var date = $("<div>");
                            var body = $("<div>");
                            var line = $("<hr>").css("margin-bottom", "5px");

                            if (index % 2 == 0) {
                                div.css("background", "#efefef")
                            } else {
                                div.css("background", "#fff")
                            }

                            header.text(dataItem.submitUser.firstName +" "+dataItem.submitUser.lastName);
                            date.text(dataItem.datetime);
                            body.text(dataItem.body);

                            div.append(header);
                            div.append(line);
                            div.append(date);
                            div.append(body);

                            $("#pnlAnswers").append(div);
                    });

                }

            });
        }else {
            Materialize.toast('هیچ ردیفی انتخاب نشده است', 4000, 'error-toast');
        }
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
                dataArray = data;
                var ctrl = $("#ddlUser").data('kendoMultiSelect');

                ctrl.setDataSource(new kendo.data.DataSource({ data: data }));
            }
        });
    }
</script>
<%@include file="footer.jsp" %>