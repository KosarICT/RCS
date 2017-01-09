<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>


<div id="tblUser" class="row">
    <table class="bordered responsive-table textColor">
        <thead>
        <tr>
            <th class="center">ردیف</th>
            <th class="center">نام نقش</th>
            <th class="center">توضیحات</th>
        </tr>
        </thead>

        <c:if test="${not empty lists}">
            <c:set var="row" value="1" scope="page"/>

            <tbody class="data-wrapper">
            <c:forEach var="entry" items="${lists}">
                <c:set var="userId" value="${entry.roleId}" scope="page"/>
                <tr>
                    <td class="center" style="display: none">${entry.roleId}</td>
                    <td class="center counter"><c:out value="${row}"/></td>
                    <td class="center">${entry.name}</td>
                    <td class="center">${entry.description}</td>
                    <td class="center">
                        <a id="btnEdit_${entry.roleId}" href="#" class="mainTextColor"
                           style="margin-left: 5px" onclick="getRoleForEdit(this);">
                            <img src="/static/icon/edit1.png">
                        </a>
                    </td>
                </tr>

                <c:set var="row" value="${row + 1}" scope="page"/>
            </c:forEach>
            </tbody>
        </c:if>
    </table>
</div>

<div class="custom-fixed-action-btn">
    <a class="btn-floating btn-large waves-effect waves-light slideColor " href="#" onclick="showRoleWindow();"
       style="bottom: 35px; left: 35px;">
        <i class="material-icons">add</i>
    </a>
</div>

<div id="roleWindow" class="modal modal-fixed-footer modalHeight">
    <div class="windowHeader">
        اضافه/ویرایش نقش
    </div>
    <div class="modal-content">
        <div class="row">

            <ul id="roleTab" class="tabs" style="padding-right: 0">
                <li class="tab col s3 right"><a href="#roleInformation">مشخصات</a></li>
                <li class="tab col s3 right"><a href="#rols">کاربران</a></li>
            </ul>
            <div id="roleInformation">
                <div class="col s12 m6 l5 right">

                    <div class="input-field">
                        <input placeholder="نام نقش" id="txtRoleName" type="text" autocomplete="false" autofocus>
                    </div>

                    <div class="input-field ">
                        <input placeholder="توضیحات" id="txtDescription" type="text" maxlength="500">
                    </div>
                </div>
            </div>
            <div id="rols" >
                <div class="col s12 m6  l5 right">
                    <div id="userListDiv"></div>
                </div>
            </div>
        </div>

    </div>
    <div class="modal-footer" style="background-color: #4f6789">
        <a href="#" class="modal-action  waves-effect waves-light btn-flat white-text" onclick="btnAddClick();"><img
                src="../static/icon/ok3.png" style="margin-left: 12px"/>تایید</a>
        <a href="#" class="modal-action modal-close waves-effect waves-light btn-flat white-text"><img
                src="../static/icon/cancel2.png" style="margin-left: 12px"/>انصراف</a>
    </div>
</div>

<script>
    var roleId = 0;

    $(document).ready(function () {
        $(".page-title").text("نقش ها");

        $('#userTab.tabs').tabs('select_tab', 'roleInformation');


        var height = screen.height - 170;
        $("#container").css("height", height + "px").css("overflow", "auto").persiaNumber();

        initWindow();
        creatRoleDive();

        $('.fixed-action-btn').openFAB();

        $('.modal').modal();


    });

    function initWindow() {
        $('#roleWindow').modal({
                dismissible: false, // Modal can be dismissed by clicking outside of the modal
                opacity: .5, // Opacity of modal background
                in_duration: 300, // Transition in duration
                out_duration: 200, // Transition out duration
                starting_top: '0', // Starting top style attribute
                ending_top: '0',
                ready:function () {

                }
            }
        );
    }

    function creatRoleDive() {
        <c:if test="${not empty userList}">
        <c:forEach var="entry" items="${userList}">

        var roleListDiv = $("#userListDiv");

        var div = $("<div>").css("width", "48%").css("display", "inline-block").css("margin", "2px");

        var pTag = $('<p>');

        var lblUserName = $("<label>").attr("for", "${entry.userId}").css("width", "0");
        var chkUser = $("<input>").attr("type", "checkBox").attr("value", "${entry.userId}").attr("id", "${entry.userId}");
        var lblUserText = $("<label>").text("${entry.displayName}").attr("for", "${entry.userId}").css("vertical-align", "top").css("margin-right", "8px");

        pTag.append(chkUser);
        pTag.append(lblUserName);
        pTag.append(lblUserText);

        div.append(pTag);

        roleListDiv.append(div);

        </c:forEach>
        </c:if>

    }

    function btnAddClick() {
        var roleName = $("#txtRoleName").val();
        var description = $("#txtDescription").val();

        if (roleName =="") {
            Materialize.toast('تمامی فیلدها اجباری می باشد', 4000, 'info-toast');
        } else {
            var users = getCheckListArray();
                var dataArray = [];
                var dataItem = {};

                dataItem["roleId"] = roleId;
                dataItem["roleName"] = roleName;
                dataItem["description"] = description;
                dataItem["users"] = users;

                dataArray.push(dataItem);
                $.ajax({
                    type: "POST",
                    url: "/role/api/addRole",
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    data: JSON.stringify(dataArray),
                    success: function (data) {
                        if (data) {
                            window.location.reload();
                        } else {
                            Materialize.toast('خطا در انجام عملیات!', 4000, 'error-toast');
                        }
                    }
                });
            }
    }

    function getCheckListArray() {
        var checkList = $("#userListDiv").children();
        var array = [];
        $.each(checkList, function (count, dataItem) {
            if (dataItem.childNodes[0].childNodes[0].checked) {

                var item = {};
                item["userId"] = dataItem.childNodes[0].childNodes[0].value;
                array.push(item);
            }
            ;
        });
        return array;
    }

    function getRoleForEdit(sender) {
        roleId = sender.id.split("_")[1];
        debugger;
        $.ajax({
            type: "POST",
            url: "/role/api/getRoleForEdit",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: roleId.toString(),
            success: function (data) {
                if (data == "false") {
                    Materialize.toast('خطا در انجام عملیات!', 4000, 'error-toast');
                } else {
                    debugger;
                    var role = data[0];
                    clearForm();
                    $("#txtRoleName").val(role.name);
                    $("#txtDescription").val(role.description);

                    checkUsers(role.roles)
                    $('#roleWindow').modal('open');
                }
            }
        });
    }

    function checkUsers(userList) {
        var checkList = $("#userListDiv").children();
        $.each(checkList, function (count, dataItem) {
            $.each(userList, function (count, user) {

                if (dataItem.childNodes[0].childNodes[0].value == user.users.userId) {
                    dataItem.childNodes[0].childNodes[0].checked = true;

                }
            });
        });
    }

    function showRoleWindow() {
        clearForm();
        $('#roleWindow').modal('open');
        $("#txtUserName").focus();
    }

    function clearForm() {
        $("#txtRoleName").val("");
        $("#txtDescription").val("");

        var checkList = $("#userListDiv").children();

        $.each(checkList, function (count, dataItem) {
            dataItem.childNodes[0].childNodes[0].checked = false;
        });

        $('ul.tabs').tabs('select_tab', 'roleInformation');
    }

    function pagination() {
        var req_num_row = 8;
        var $tr = jQuery('tbody tr');
        var total_num_row = $tr.length;
        var num_pages = 0;
        if (total_num_row % req_num_row == 0) {
            num_pages = total_num_row / req_num_row;
        }
        if (total_num_row % req_num_row >= 1) {
            num_pages = total_num_row / req_num_row;
            num_pages++;
            num_pages = Math.floor(num_pages++);
        }
        for (var i = 1; i <= num_pages; i++) {
            if (i == 1) {
                jQuery('.pagination').append("<li class='active'><a href='#!'>" + i + "</a></li>");
            } else
                jQuery('.pagination').append("<li class='waves-effect'><a href='#!'>" + i + "</a></li>");
        }

        jQuery('.pagination').append("<li class='waves-effect'><a href='#!' onclick='nextPage();'><i class='material-icons'>chevron_left</i></a></li>");

        $tr.each(function (i) {
            jQuery(this).hide();
            if (i + 1 <= req_num_row) {
                $tr.eq(i).show();
            }

        });

        jQuery('#pagination a').click(function (e) {
            e.preventDefault();
            $tr.hide();
            var page = jQuery(this).text();
            var temp = page - 1;
            var start = temp * req_num_row;
            //alert(start);

            for (var i = 0; i < req_num_row; i++) {

                $tr.eq(start + i).show();

            }
        });
    }

    function nextPage() {
        var currentPage = $(".pagination .active").text();

        debugger;
    }
</script>




<%@include file="footer.jsp" %>