$(function () {
    $('input[type=checkbox]').css("margin-top", "-3px");

    if (isnew == "1") {
        addrule();
    }else{
        $('.rule').not($('.rule').eq(0)).each(function (i, v) {
            sortable($(v));
        });
    }

    $('.newrule').click(function () {
        addrule();
    });

    $('.del_station').click(function () {
        var station = $(this).parents('.station');
        var stid = station.find('.stid').val();
        // console.debug('***************' + stid);
        station.parents('.rule').find('.station_id').each(function (i, v) {
            var ckb = $(v).parent().find('[type=checkbox]');
            if (stid == $(v).val()) {
                // console.debug('***********' + $(v).val());
                ckb.prop('checked', false);
                addStation(ckb)
            }
        })
    });


    $('.bs_all,.es_all').click(function () {
        $(this).parents('.s_left').find('input[type=checkbox]').prop("checked", $(this).prop("checked"));
        addStation(this);
    });

    $("input[type=checkbox]").not('.bs_all,.es_all').click(function () {
        addStation(this);
    });

    $('.rule').not('.rule:first').each(function (i, v) {
        timepicker($(v).find('.begintime'));
        timepicker($(v).find('.endtime'));
    });

    $('.submit').click(function () {
        var params = {};
        params.rulename = $('.rule_name').val();
        params.id = $('.rule_id').val();
        params.isdefault = $('.isdefault').prop("checked") ? 1 : 0;
        params.lmid = $lmid;

        var rules = $('.rule').not('.rule:first');

        if ($('.bs_right').find('.station').length == 0 && isnew == "1") {
            layer.msg("请添加始发城市途径站点！", {offset: '30%'});
            return;
        }
        if ($('.es_right').find('.station').length == 0 && isnew == "1") {
            layer.msg("请添加到达城市途径站点！", {offset: '30%'});
            return;
        }

        var issubmit = true;
        rules.each(function (i, v) {
            if (!issubmit) {
                return;
            }
            var r = $(v);
            var rprefix = 'rules';
            var sprefix = 'stations';
            params[rprefix + '[' + i + '].begintime'] = r.find('.begintime').val();
            params[rprefix + '[' + i + '].endtime'] = r.find('.endtime').val();
            params[rprefix + '[' + i + '].id'] = r.find('.time_rule_id').val();

            var stations = r.find('.station');

            stations.each(function (j, v) {
                if (!issubmit) {
                    return;
                }
                var s = $(v);
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].lmid'] = $lmid;
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].id'] = s.find('.id').val();
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].sort'] = s.find('.sort').val();
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].sortname'] = s.find('.sort').val() === "0" ? '始发站' : '到达站';
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].stid'] = s.find('.stid').val();
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].stname'] = s.find('.cityname').text();
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].sortnum'] = j;
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].requiretime'] = s.find('.requiretime').val();
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].isdel'] = s.find('.isdel').val();
                params[rprefix + '[' + i + '].' + sprefix + '[' + j + '].submileage'] = s.find('.submileage').val();

                if (s.find('.submileage').val() == '') {
                    layer.msg(s.find('.cityname').text() + '，里程不能为空！');
                    issubmit = false;
                }
                if (s.find('.requiretime').val() == '') {
                    layer.msg(s.find('.cityname').text() + '，间隔时间不能为空！');
                    issubmit = false;
                }

            });
        });
        if (!issubmit) {
            return;
        }
        console.debug(params);
        // return
        ajax(params, basePath + "/user/editStationTimeRule", function (data) {
            if (data >= 1) {
                layer.msg('操作成功');
                location.href = basePath + "/user/lineadd.do?id=" + $lmid + "#3";
            } else if (data == -100) {
                layer.msg('记录已经存在');
            } else {
                layer.msg('操作失败');
            }
        })

    });


});

function hideFirst() {
    $('.rule').each(function (i, v) {
        var station = $(v).find('.bs_right').find('.station:visible').first();
        station.find('.requiretime,.submileage').hide().val(0);
        $(v).find('.station').not(station).find('.requiretime,.submileage').show();
    });
}

function addStation(e) {
    var right;
    if ($(e).hasClass("bs_all") || $(e).hasClass("bs")) {
        right = $(e).parents('.rule').find('.bs_right');
    } else if ($(e).hasClass("es_all") || $(e).hasClass("es")) {
        right = $(e).parents('.rule').find('.es_right');
    }
    if (right == false) {
        return;
    }
    var ckbs = $(e).parents('.s_left').find("input[type=checkbox]").not($('.bs_all,.es_all'));
    ckbs.each(function (i, v) {
        var tr = $(v).parents("td");
        var sid = tr.find('.station_id').val();
        var item = right.find('[sid=' + sid + ']');
        var len = item.length;
        if (len == 0 && $(v).prop("checked")) {
            var template = $('.bs_info_template').eq(0).clone(true).attr("sid", sid).addClass('station').show();
            template.find(".stid").val(sid);
            template.find(".sort").val($(v).hasClass("bs") ? 0 : 1);
            //template.find(".requiretime").val(tr.find('.requiretime').val());
            //template.find(".submileage").val(tr.find('.submileage').val());
            template.find(".cityname").text(tr.find('.station_name').text());
            right.find("tr").last().after(template);
        } else if (len >= 1) {
            // right.find('[sid='+sid+']').remove();
            var station = item;
            if ($(v).prop("checked")) {
                station.show().find('.isdel').val(0);
            } else {
                if (station.find('.id').val()) {
                    station.hide().find('.isdel').val(1);
                } else {
                    station.remove();
                }
            }
        }
    });
    hideFirst();
}

function sortable(rule) {
    setTimeout(function () {
        rule.find(".sortable").sortable({
            items: "tr:not(.ui-state-disabled)",
            stop: function (e) {
                console.debug(e);
                hideFirst();
            }
        });
    },100)
}

function addrule() {
    var rule = $('.rule').eq(0);
    var newrule = rule.clone(true).show();
    $('.extend[on=1]').not(newrule.find(".extend")).not(rule.find(".extend")).click();
    $('.rule').last().after(newrule);
    timepicker(newrule.find(".begintime"));
    timepicker(newrule.find(".endtime"));
    sortable(newrule);
}

function timepicker(e) {
    e.timepicker({
        showMeridian: false,
        showInputs: true,
        defaultTime: false,
        format: 'H:i',
        minuteStep: 1
    });
}