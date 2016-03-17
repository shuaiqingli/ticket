$(function () {
    loadData();
});

function loadData() {
    var params = {};
    ajax(params, "web/api/customer/mileData", function (json) {
        var mileTotal = 0;
        var mileDataList = json.datas[0];
        if(mileDataList.length > 0){
            var mileElementList = [];
            for (var i = 0; i < mileDataList.length; i++) {
                var mileData = mileDataList[i];
                var mileElement = $('.main_container').find('.item').eq(0).clone(true).css('display', '');
                mileElement.find('.mile_time').text(mileData.RideDate);
                mileElement.find('.lines_detail').text(mileData.LineName);
                mileElement.find('.miles_num').text(mileData.Mileage||0 + 'km');
                if(i == mileDataList.length-1){
                    mileElement.find('.point_line div').eq(2).attr('class', 'line_last');
                }
                if(i == 0){
                    mileElement.find('.point_line div').eq(0).attr('class', 'line_first');
                }
                mileElementList.push(mileElement);
                mileTotal += (mileData.Mileage||0);
            }
            $('.main_container').append(mileElementList);
        }
        $('.mileTotal').text('总里程:' + mileTotal + 'km');
    });
}