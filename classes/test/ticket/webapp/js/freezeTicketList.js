$(function () {
    initPage();
    $('input[name="rideDate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2000-00-00',
        endDate: '2030-00-00'
    });
    $('input[name="makeDate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2000-00-00',
        endDate: '2030-00-00'
    });
});

function initPage() {
    $('.item').mouseover(function () {
        $(this).css({
            'background': '#ccc'
        });
    });
    $('.item').mouseout(function () {
        $(this).css({
            'background': ''
        });
    });
}