$(function () {
    window.id = getUrlParam('id');
    loadData();
});


function loadData() {
    ajax({id: id}, "public/api/newsDetail", function (json) {
        var news = json.datas[0];
        //$('title').html(news.amtitle);
        $('body').html(news.amcontent);
    });
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}