layui.define(['layer', 'form', 'laydate'], function (exports) {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;


    $(".commonMonth").each(function () {
        laydate.render({
            elem: this,
            format: 'yyyy-MM',
            type: 'month',
            value: new Date(),
            theme: '#9400D3',
            btns: ['clear', 'confirm'],
            min: '2018-05-01',
            max: 0
        });
    });


    function drawGoodsSale(monthTime,goodsType) {
        var titleText = monthTime+'月商品销售情况';
        var legendDate = [],
            xAjaxDate = [],
            saleMoneyDate = [],
            saleCountDate = [],
            originalMoneyDate = [],
            gainMoneyList = [],
            series = [];
        $.get('/statistics/getMonthGoodsSale', {
            'monthTime': formatStrYMD(monthTime),
            'goodsType': goodsType
        }, function (result) {
            if (result.code == 0) {
                $.each(result.data, function (index, data) {
                    xAjaxDate.push(data.goodsName);
                    saleMoneyDate.push(data.saleMoney);
                    saleCountDate.push(data.saleCount);
                    originalMoneyDate.push(data.originalMoney);
                    gainMoneyList.push(data.gainMoney);
                });
                legendDate.push('总售量');
                legendDate.push('总售额');
                legendDate.push('总成本');
                legendDate.push('总利润');
                var markLineObj = new Object();
                markLineObj.type='average';
                markLineObj.name='平均值';
                var unSelected = new Object();
                unSelected.总售额 = false;
                unSelected.总成本 = false;
                unSelected.总利润 = false;
                series.push({"type": "bar","markLine":{data:[markLineObj]}, "name": "总售量", "data": saleMoneyDate});
                series.push({"type": "bar","markLine":{data:[markLineObj]}, "name": "总售额", "data": saleCountDate});
                series.push({"type": "bar","markLine":{data:[markLineObj]}, "name": "总成本", "data": originalMoneyDate});
                series.push({"type": "bar","markLine":{data:[markLineObj]}, "name": "总利润", "data": gainMoneyList});
                var option = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    title: {
                        text: titleText,
                        x: 'center',
                        y: 0,
                        textStyle: {
                            color: '#9400D3',
                            fontSize: 16,
                            fontWeight: 'bold'
                        }
                    },
                    legend: {
                        y: 30,
                        data: legendDate,
                        selected: unSelected
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            magicType : {show: false, type: ['bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    xAxis: [
                        {
                            type : 'category',
                            data: xAjaxDate
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: series
                };
                echarts.init(document.getElementById('goodsSaleByMonth')).setOption(option);
            } else {
                layer.msg(result.msg, {
                    icon: 2
                });
            }

        });
    }

    form.on('submit(queryGoodsSaleByMonth)', function (data) {
        drawGoodsSale(data.field.monthTime, data.field.goodsType);
        return false;
    });

    /*初始化页面*/
    $(function () {
        var date = new Date();
        var yyyyMM = getYMByDate(date);
        drawGoodsSale(yyyyMM,0);
    });

    /*由日期对象获取yyyy-MM-dd*/
    function getYMDByDate(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = (month > 9) ? month : "0" + month;
        var day = date.getDate();
        day = (day > 9) ? day : "0" + day;
        return year + '-' + month + '-' + day;
    }

    /*由日期对象获取yyyy-MM*/
    function getYMByDate(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = (month > 9) ? month : "0" + month;
        return year + '-' + month;
    }

    /*去除str日期中的-*/
    function formatStrYMD(strDate) {
        return strDate.replace(/-/g, '');
    }

    /*获取查询日期对象的前n天*/
    function getDateBefore(selectDate, n) {
        return new Date(selectDate.getTime() - n * 24 * 60 * 60 * 1000);
    }

    exports('xecharts', {});
});
