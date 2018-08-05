var Utils = {
    getYMDByDate(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = (month > 9) ? month : "0" + month;
        var day = date.getDate();
        day = (day > 9) ? day : "0" + day;
        return year + '-' + month + '-' + day;
    },
    getYMDHmsByDate(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = (month > 9) ? month : "0" + month;
        var day = date.getDate();
        day = (day > 9) ? day : "0" + day;
        var hour = date.getHours();
        hour = (hour > 9) ? hour : "0" + hour;
        var min = date.getMinutes();
        min = (min > 9) ? min : "0" + min;
        var second = date.getSeconds();
        second = (second > 9) ? second : "0" + second;
        return year + '-' + month + '-' + day + ' ' + hour + ':' + min + ':' + second;
    },
    getHmsByDate(date) {
        var hour = date.getHours();
        hour = (hour > 9) ? hour : "0" + hour;
        var min = date.getMinutes();
        min = (min > 9) ? min : "0" + min;
        var second = date.getSeconds();
        second = (second > 9) ? second : "0" + second;
        return hour + ':' + min + ':' + second;
    },
    isBefore(time, comparedTime) {
        let re = new RegExp("^([01][0-9]|[2][0-3]):[0-5][0-9]:[0-5][0-9]$");
        if (re.test(time) && re.test(comparedTime)) {
            let HHmmss = time.split(':');
            let comparedHHmmss = comparedTime.split(':');
            for (let i = 0; i < HHmmss.length; i++) {
                if (parseInt(HHmmss[i]) < parseInt(comparedHHmmss[i])) {
                    return true;
                }
            }
            return false;
        }
        re = new RegExp("^[1-2][0-9][0-9][0-9]-([1][0-2]|0?[1-9])-([12][0-9]|3[01]|0?[1-9]) ([01][0-9]|[2][0-3]):[0-5][0-9]:[0-5][0-9]$");
        if (re.test(time) && re.test(comparedTime)) {
            let date = new Date(time);
            let comparedDate = new Date(comparedTime);
            if (date.getTime() < comparedDate.getTime()) {
                return true;
            }
            return false;
        }
    },
    /*获取查询日期对象的后n天*/
    getDateAfter(selectDate, n) {
        return new Date(selectDate.getTime() + n * 24 * 60 * 60 * 1000);
    },
    getCNWeek(weekday) {
        weekday = Number(weekday);
        switch(weekday){
            case 1:
                return '周一';
            case 2:
                return '周二';
            case 3:
                return '周三';
            case 4:
                return '周四';
            case 5:
                return '周五';
            case 6:
                return '周六';
            case 7:
                return '周日';
        }
    }
};
