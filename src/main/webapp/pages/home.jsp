<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="header.jsp" %>

<div class="row">
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
</div>
<div>
    <div id="grvAdminTicket"></div>
</div>
<%--<div class="row">--%>
<%--<canvas id="myChart" width="400" height="125"></canvas>--%>
<%--</div>--%>

<script>

    jQuery(document).ready(function ($) {

//        $('.number-widget').counterUp({
//            delay: 10,
//            time: 1000
//        });
        initGrid();
        $(".page-title").text("صفحه اصلی");
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
            },
            sortable: {
                mode: "single",
                allowUnsort: false
            },

            filterable: {
                mode: "row"
            },
            selectable: "single",
            columns: [
                {field: "appreciationId", hidden: true},
                {field: "ticketStatusId", hidden: true},
                {field: "ticketTypeId", hidden: true},
                {
                    field: "ticketTypeTitle", title: "نوع گزارش", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "ticketSubject", title: "عنوان گزارش", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "sectionTitle", title: "واحد", width: "120px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "sendTypeTitle", title: "نوع ارسال", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "submitDate", title: "تاریخ و ساعت", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "responseTime", title: "زمان /روز", width: "100px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "hospitalName", title: "نام مرکز", width: "100px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
                {
                    field: "ticketStatusTitle", title: "وضعیت", width: "100px", filterable: {
                    cell: {
                        showOperators: false
                    }
                }
                },
            ],
            dataBound: function (e) {
                var grid = $("#grvAdminTicket").data("kendoGrid");
                var gridData = grid.dataSource.view();

                for (var i = 0; i < gridData.length; i++) {

                    var ticketTypeId = gridData[i].ticketTypeId;

                    if (ticketTypeId == 3) {

                        var calender = new PersianDate();

                        var responseDay = gridData[i].responseTime
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

                        debugger;
                        $('tr[data-uid="' + gridData[i].uid + '"] ').css("background-color", colorCode);

                    }

                }
            }
        });
    }

    //    var ctx = $("#myChart");
    //
    //    var myLineChart = new Chart(ctx, {
    //        type: 'line',
    //        data: data,
    //    });
    //
    //    var data = {
    //        labels: ["فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"],
    //        datasets: [
    //            {
    //                label: "تست",
    //                fill: true,
    //                lineTension: 0.1,
    //                backgroundColor: "rgba(42,63,84,0.4)",
    //                borderColor: "rgba(42,63,84,1)",
    //                borderCapStyle: 'butt',
    //                borderDash: [],
    //                borderDashOffset: 0.0,
    //                borderJoinStyle: 'miter',
    //                pointBorderColor: "rgba(85,156,160,1)",
    //                pointBackgroundColor: "#fff",
    //                pointBorderWidth: 1,
    //                pointHoverRadius: 5,
    //                pointHoverBackgroundColor: "rgba(42,63,84,1)",
    //                pointHoverBorderColor: "rgba(220,220,220,1)",
    //                pointHoverBorderWidth: 2,
    //                pointRadius: 1,
    //                pointHitRadius: 10,
    //                data: [65, 59, 80, 81, 56, 55, 40],
    //                spanGaps: false,
    //            }
    //        ]
    //    };
    function getColor(value) {
        //value from 0 to 1
        var hue = ((1 - value) * 120).toString(10);
        return ["hsl(", hue, ",100%,85%)"].join("");
    }
</script>

<%@include file="footer.jsp" %>

