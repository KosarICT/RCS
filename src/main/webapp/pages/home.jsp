<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="header.jsp" %>

<style>
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

    #grvAdminTicket th {
        text-align: right !important;
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

    .k-operator-hidden{
        padding-right: 0 !important;
    }
</style>

<%--<div class="row">
    <div class="col s12 m6 l3">
        <div class="card" style="background: #6580b1">
            <div class="card-content white-text">
                <span class="card-title title-widget">پیشنهادات امروز</span>
                <p class="number-widget">450</p>
            </div>
        </div>
    </div>
    <div class="col s12 m6 l3">
        <div class="card" style="background: #65c184">
            <div class="card-content white-text">
                <span class="card-title title-widget">تقدیرهای امروز</span>
                <p class="number-widget">102</p>
            </div>
        </div>
    </div>
    <div class="col s12 m6 l3">
        <div class="card" style="background: #ffb986">
            <div class="card-content white-text">
                <span class="card-title title-widget">انتقادات امروز</span>
                <p class="number-widget">291</p>
            </div>
        </div>
    </div>

    <div class="col s12 m6 l3">
        <div class="card" style="background: #f9838c">
            <div class="card-content white-text">
                <span class="card-title title-widget">شکایات امروز</span>
                <p class="number-widget">782</p>
            </div>
        </div>
    </div>
</div>--%>



<div>
    <div id="grvAdminTicket"></div>
</div>

<script>

    jQuery(document).ready(function ($) {
        $(".page-title").text("صفحه اصلی");

        initGrid();
        resizeGird();
    });

    function initGrid() {
        $("#grvAdminTicket").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        url: "/admin/api/getTop10Ticket",
                        type: "GET",
                        contentType: "application/json",
                        dataType: "json",
                    }
                },
                pageSize: 5
            },
            filterable: {
                mode: "row"
            },
            sortable: {
                mode: "single",
                allowUnsort: false
            },
            pageable: {
                pageSizes: true,
                messages: {
                    itemsPerPage: "",
                    display: "نمایش {0}-{1} آیتم از {2} آیتم",
                    empty: "اطلاعاتی برای نمایش وجود ندارد"
                }
            },
            columns: [
                {field: "appreciationId", hidden: true},
                {field: "ticketStatusId", hidden: true},
                {field: "ticketTypeId", hidden: true},
                {
                    field: "ticketTypeTitle", title: "نوع گزارش", width: "100px", filterable: {
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
                {field: "ticketSubject", title: "عنوان گزارش", filterable: {
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
                }},
                {field: "sectionTitle", title: "واحد", width: "120px", filterable: {
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
                }},
                {field: "sendTypeTitle", title: "نوع ارسال", width: "100px", filterable: {
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
                }},
                {field: "submitDate", title: "تاریخ و ساعت", width: "100px", filterable: {
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
                }},
                {field: "responseTime", title: "زمان/روز", width: "100px", filterable: {
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
                }},
                {field: "hospitalName", title: "نام مرکز", width: "200px", filterable: {
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
                }},
                {field: "ticketStatusTitle", title: "وضعیت", width: "100px" ,filterable: {
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
                }}
            ],
            dataBound: function (e) {
                var grid = $("#grvAdminTicket").data("kendoGrid");
                var gridData = grid.dataSource.view();

                for (var i = 0; i < gridData.length; i++) {

                    var ticketTypeId = gridData[i].ticketTypeId;

                    if (ticketTypeId == 3) {

                        var calender = new PersianDate();

                        var responseDay = gridData[i].responseTime;
                        var submitDate = gridData[i].submitDate;

                        var dateArray = submitDate.split("/");
                        var y = dateArray[0];
                        var m = dateArray[1];
                        var d = dateArray[2];
                        var gregorianDateArray = calender.jalaliToGregorian(y, m, d);
                        var gregorianDate = gregorianDateArray[0] + "/" + gregorianDateArray[1] + "/" + gregorianDateArray[2];
                        var startDate = new Date(gregorianDate);
                        startDate.setHours(0, 0, 0, 0);

                        var date = new Date();
                        date.setHours(0, 0, 0, 0);

                        var differDays = new Date(date.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);

                        var days = parseInt(differDays);

                        var len = parseInt(responseDay);

                        var value = days / len;
                        var colorCode = getColor(value);

                        $('tr[data-uid="' + gridData[i].uid + '"] ').css("background-color", colorCode);

                    }

                }
            }
        });
    }

    function resizeGird() {
        var height = $(window).height() - 70;
        $("#grvAdminTicket").css('height', height)
    }

    function getColor(value) {
        //value from 0 to 1
        var hue = ((1 - value) * 120).toString(10);
        return ["hsl(", hue, ",100%,92%)"].join("");
    }
</script>

<%@include file="footer.jsp" %>

