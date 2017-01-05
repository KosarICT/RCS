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

<div class="row">
    <canvas id="myChart" width="400" height="125"></canvas>
</div>

<script>

    $(document).ready(function () {
        //$(".number-widget").persiaNumber();
        $(".page-title").text("صفحه اصلی");

        jQuery(document).ready(function ($) {
            $('.number-widget').counterUp({
                delay: 10,
                time: 1000
            });
        });

        var ctx = $("#myChart");

        var myLineChart = new Chart(ctx, {
            type: 'line',
            data: data,
        });
    });

    var data = {
        labels: ["فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"],
        datasets: [
            {
                label: "تست",
                fill: true,
                lineTension: 0.1,
                backgroundColor: "rgba(42,63,84,0.4)",
                borderColor: "rgba(42,63,84,1)",
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: "rgba(85,156,160,1)",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(42,63,84,1)",
                pointHoverBorderColor: "rgba(220,220,220,1)",
                pointHoverBorderWidth: 2,
                pointRadius: 1,
                pointHitRadius: 10,
                data: [65, 59, 80, 81, 56, 55, 40],
                spanGaps: false,
            }
        ]
    };


</script>

<%@include file="footer.jsp" %>

