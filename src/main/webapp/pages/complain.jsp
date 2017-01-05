<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>

<style>
    label {
        font-size: 13px;
        color: black;
        font-weight: bold;
    }
</style>

<div id="confirmWindow" class="modal" style="width: 290px">
    <div class="modal-content">
        <p id="messageDialog"></p>
    </div>
    <div class="divider"></div>
    <div class="modal-footer">
        <input type="button" class=" modal-action modal-close btn-flat" value="تایید" onclick="btnConfirmClick()">
        <input type="button" class=" modal-action modal-close btn-flat" value="انصراف">
    </div>
</div>



<script>
    var userId = 0;

    $(document).ready(function () {
        $(".row").persiaNumber();
        $('select').material_select();

        $('#radioGroup   input').on('change', function () {

            if ($('#withSick').is(':checked')) {
                $("#compalainerName").val("");
                $("#compalainerName").removeAttr('disabled');

                $("#compalainerFamily").val("");
                $("#compalainerFamily").removeAttr('disabled');

                $("#registerNationalCode").val("");
                $("#registerNationalCode").removeAttr('disabled');
            }
            else {
                $("#compalainerName").val("");
                $("#compalainerName").attr('disabled', 'disabled');

                $("#compalainerFamily").val("");
                $("#compalainerFamily").attr('disabled', 'disabled');

                $("#registerNationalCode").val("");
                $("#registerNationalCode").attr('disabled', 'disabled');
            }
        });

        var height = screen.height - 200;
        $("#commentsDiv").css('height', height + 'px').css('overflow-y', 'auto');

        initWindow();
        initDropDown();
    });

    function initDropDown() {
        $('#ddlHospital').dropdown({
            inDuration: 300,
            outDuration: 225,
            constrain_width: false,
            hover: true,
            gutter: 0,
            belowOrigin: false,
            alignment: 'right'
        });
    }

    function initWindow() {
        $('#confirmWindow').modal({
            dismissible: false,
            opacity: .5,
            in_duration: 300,
            out_duration: 200,
            starting_top: '4%',
            ending_top: '10%',
        });
    }

    function showConfirm(sender) {
        userId = sender.id.split("_")[1];

        $('#messageDialog').text('آیا برای حذف این کاربر اطمینان دارید؟');
        $('#confirmWindow').modal('open');
    }

    function btnConfirmClick() {
        $.ajax({
            type: "POST",
            url: "/user/api/deleteUser",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: userId.toString(),
            success: function (data) {
                if (data) {
                    window.location.reload();
                } else {
                    Materialize.toast('I am a toast!', 40000, 'success-toast');
                }
            }
        });
    }



</script>

<%@include file="footer.jsp" %>