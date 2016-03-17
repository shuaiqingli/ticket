$(function () {
    var tabIndex = parseInt(location.hash.split('#')[1]);
    if (!isNaN(tabIndex) && $('.nav-tabs li').length > tabIndex) {
        $('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
        $('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
    }
});