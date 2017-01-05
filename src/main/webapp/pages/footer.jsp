<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</div>
</main>

<script>
/*    $(window).load(function() {
        $("#loading").fadeOut(2000);
    });*/

    $(document).ready(function () {
        getTabList();

        $('.button-collapse').sideNav({
            menuWidth: 180,
            edge: 'right'
        });

        $(".dropdown-button").dropdown({
            belowOrigin: true,
            alignment:'right'
        });
    });

    function getTabList() {
        $.ajax({
            type: "GET",
            url: "/home/api/getMenu",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $("#currentUserDisplayName").text(data[0][0].displayName);
                $("#imgUserImage").attr("src","/static/userImage/" + data[0][0].imageName);

                if (data.length > 0) {
                    $.each(data[1], function (index, dataItem) {
                        var data = dataItem;

                        var liParent = $("<li>");

                        if (data.child.length > 0) {
                            liParent.addClass("no-padding");

                            var ul = $("<ul>").addClass("collapsible collapsible-accordion").css("padding-right","0");

                            var liChild = $("<li>");

                            var a = $("<a>").text(data.title).addClass("collapsible-header white-text");

                            var i = $("<i>").addClass("material-icons white-text").text("arrow_drop_down");

                            var div = $("<div>").addClass("collapsible-body");

                            var ulDropDown = $("<ul>").css("padding-right","0").addClass("slideColor");

                            $.each(data.child, function (counter, item) {
                                var liDropDown = $("<li>");
                                var aDropDown = $("<a>").text(item.title).attr("href", item.url).addClass("white-text");

                                liDropDown.append(aDropDown);

                                ulDropDown.append(liDropDown);
                            });

                            div.append(ulDropDown);

                            a.append(i);
                            liChild.append(a);
                            liChild.append(div);

                            ul.append(liChild);

                            liParent.append(ul);

                            $("#nav-mobile").append(liParent);

                        } else {
                            var a = $("<a>").text(data.title).addClass("white-text");

                            liParent.append(a);

                            if(data.url == "logout"){
                                a.attr("href","<c:url value="/logout" />");
                            }else{
                                a.attr("href", data.url);
                            }

                            $("#nav-mobile").append(liParent);
                        }

                        setTimeout(function () {
                            selectTab();
                        },50);
                    });


                    $('.collapsible').collapsible();
                }
            }
        });
    }

    function selectTab() {
        var url = window.location.pathname;

        switch (url){
            case "/admin":
                debugger;
                $("#nav-mobile li a[href=admin]").parent().addClass("active");
                break;
            case "/adAppreciation":
                $("#nav-mobile li a[href=adAppreciation]").parent().addClass("active");
                break;
            case "/adComplain":
                $("#nav-mobile li a[href=adComplain]").parent().addClass("active");
                break;
            case "/adOffer":
                $("#nav-mobile li a[href=adOffer]").parent().addClass("active");
                break;
            case "/adCriticism":
                $("#nav-mobile li a[href=adCriticism]").parent().addClass("active");
                break;
            case "/user":
                $("#nav-mobile li a[href=hospitalSection]").parent().parent().parent().css("display","block");
                $("#nav-mobile li a[href=user]").parent().addClass("active");
                break;
            case "/hospital":
                $("#nav-mobile li a[href=hospitalSection]").parent().parent().parent().css("display","block");
                $("#nav-mobile li a[href=hospital]").parent().addClass("active");
                break;
            case "/complaintType":
                $("#nav-mobile li a[href=hospitalSection]").parent().parent().parent().css("display","block");
                $("#nav-mobile li a[href=complaintType]").parent().addClass("active");
                break;
            case "/hospitalSection":
                $("#nav-mobile li a[href=hospitalSection]").parent().parent().parent().css("display","block");
                $("#nav-mobile li a[href=hospitalSection]").parent().addClass("active");
                break;
        }
    }
</script>

</body>
</html>