<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<style>
    tr td {
        text-align: right !important;
        border-bottom: 1px solid #c8c8c8 !important;
        font-weight: normal !important;
        font-size: 14px !important;
    }
</style>

<div>
    <div id="mainToolbar"></div>
    <div class="k-rtl">
        <div class="k-rtl" id="grvRequestList"></div>
    </div>
</div>


<script>
    $(document).ready(function () {
        initGrid();
    });

    function initGrid() {
        $("#grvRequestList").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "/request/api/getData",
                        type: "GET",
                        contentType: "application/json",
                        dataType: "json",
                    }
                },

                pageSize: 5
            },
            toolbar: [{name: "excel", text: "دریافت فایل اکسل"}],
            excel: {
                fileName: "پیشنهادات.xlsx",
                filterable: true
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
            dataBound: function (e) {
                var grid = $("#grvRequestList").data("kendoGrid");
                var gridData = grid.dataSource.view();

                for (var i = 0; i < gridData.length; i++) {

                    var requestStatusId = gridData[i].requestStatusId;

                    if (requestStatusId == 1) {
                        $('tr[data-uid="' + gridData[i].uid + '"] ').css("background-color", "#FFF9C4");
                    } else if (requestStatusId == 2) {
                        $('tr[data-uid="' + gridData[i].uid + '"] ').css("background-color", "#C8E6C9");
                    } else {
                        $('tr[data-uid="' + gridData[i].uid + '"] ').css("background-color", "#FFCDD2");
                    }

                }
            },
            columns: [
                {field: "requestId", title: "Id", hidden: true},
                {field: "hospitalTitle", title: "بیمارستان"},
                {field: "user", title: "کاربر"},
                {field: "requestStatusTitle", title: "وضعیت"},
                {
                    command: {
                        text: "فعالسازی", click: function (e) {
                            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                            changeRequestStatus(dataItem, true);
                        }
                    }, title: "&nbsp;", width: "120px"
                },
                {
                    command: {
                        text: "غیرفعال", click: function (e) {
                            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                            changeRequestStatus(dataItem, false);
                        }
                    }, title: "&nbsp;", width: "120px"
                }
            ]
        });
    }

    function changeRequestStatus(selectedItem, status) {
        var requestId = selectedItem.requestId;

        var dataArray = [];
        var dataItem = {};

        dataItem["requestId"] = requestId;
        dataItem["status"] = status;

        dataArray.push(dataItem);

        $.ajax({
            type: "POST",
            url: "/request/api/changeUserStatus",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(dataArray),
            success: function (data) {
                if (data) {
                    Materialize.toast('عملیات با موفقیت انجام شد', 4000, 'success-toast');

                    refreshGrid();
                } else {
                    Materialize.toast('خطا در انجام عملیات', 4000, 'error-toast');
                }
            }
        });
    }

    function refreshGrid() {
        $('#grvRequestList').data('kendoGrid').dataSource.read();
        $('#grvRequestList').data('kendoGrid').refresh();
    }
</script>

<%@include file="footer.jsp" %>