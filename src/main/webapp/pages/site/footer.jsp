<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer class="page-footer blue-grey darken-1" style="padding-right: 0!important;">

    <div class="row">
        <div class="col m1 l1"></div>
        <div class="col s12 m10 l10">
            <div class="col l6 m6 s12 right white-text">
                <h5 style="
    font-size: 20px;
    font-weight: bold">لینک های مرتبط</h5>
                <ul>
                    <li>
                        <%--<img src="../static/icon/menu/back.png">--%>
                        <a class="grey-text text-lighten-3" href="#!" style="font-size: 18px;font-weight: 400">سایت
                            بیمارستان</a>
                    </li>

                    <li>
                        <%--<img src="../static/icon/menu/back.png">--%>
                        <a class="grey-text text-lighten-3" href="#!" style="font-size: 18px;font-weight: 400">تماس با
                            دفتر نظارت بر درمان دانشگاه</a>
                    </li>
                    <li>
                        <%--<img src="../static/icon/menu/back.png">--%>
                        <a class="grey-text text-lighten-3" href="http://vct.iums.ac.ir/"
                           style="font-size: 18px;font-weight: 400">سایت معاونت درمان دانشگاه علوم پزشکی ایران</a>
                    </li>
                    <li>
                        <a class="grey-text text-lighten-3" href="#!" style="font-size: 18px;font-weight: 400">راهنمای
                            استفاده از سیستم</a>
                    </li>
                </ul>
            </div>
            <div class="col l6 m6 s12 left">
                <div class="container">
                    <div class="row">
                        <div class="col s12">
                            <div id="map-canvas"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col m1 l1">
            <div id="map"></div>
        </div>
    </div>

    <div class="footer-copyright" style="text-align: center">
        <div>
            <label style="color: #fff;font-size: 12px;font-weight: bold; display: inline-block"> © کلیه حقوق محفوظ
                است</label>
            <img src="/static/icon/logouu.png" style="width: 30px; height: 30px; display: inline-block">
        </div>
    </div>
</footer>
</body>

<script>
    function myMap() {
        var mapOptions = {
            center: new google.maps.LatLng(35.7489889,51.3805624),
            zoom: 15,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };


        var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(35.7489889,51.3805624),
            map: map,
            title: 'دانشگاه علوم پزشکی ایران'
        });
    }
</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCVqej1Cg5xO--DKyFoxsXXFuICY9_pcpc&callback=myMap"></script>

</html>