//该类代表时钟上的时、分、秒对象
//limit是极限值，currentNum至多取到limit-1
function Bit(limit) {
    this.limit = limit > 1 ? limit - 1 : 0;
    this.currentNum = '00'; //当前值

    //设置当前数字
    this.setCurrentNum = function(num) {
        this.currentNum = this.format(0 <= num && num <= this.limit ? num : 0);
    }
    //获取当前数字
    this.getCurrentNum = function() {
        return this.currentNum;
    }
    //当前数字加1
    this.add = function() {
        var currentNum = parseInt(this.currentNum);
        this.currentNum = this.format(currentNum < this.limit ? currentNum + 1 : 0);
    }
    //当前数字减1
    this.minus = function() {
        var currentNum = parseInt(this.currentNum); 
        this.currentNum = this.format(currentNum <= 0 ? this.limit : currentNum - 1);
    }

    //格式化数字0-9的函数
    this.format = function(num) {
        return (num < 10 ? '0' : '') + num;
    }
}

//时钟类
function Clock() {
    this.hour = new Bit(24);   //时
    this.minute = new Bit(60); //分
    this.second = new Bit(60); //秒

    //设置为当前时间
    this.setNowTime = function() {
        var now = new Date();
        this.hour.setCurrentNum(now.getHours());
        this.minute.setCurrentNum(now.getMinutes());
        this.second.setCurrentNum(now.getSeconds());
    }
    //时间归零
    this.clearTime = function() {
        this.hour.setCurrentNum(0);
        this.minute.setCurrentNum(0);
        this.second.setCurrentNum(0);
    }
    //获取时分秒(0, 1, 2)
    this.getTime = function() {
        return [this.hour.getCurrentNum(), this.minute.getCurrentNum(), this.second.getCurrentNum()];
    }
    //转字符串
    this.toString = function() {
        return this.hour.getCurrentNum() + ':' +
                this.minute.getCurrentNum() + ':' + 
                this.second.getCurrentNum();
    }
    //秒数加1并进位
    this.addSecond = function() {
        var tag = false;
        this.second.add();
        if (this.second.getCurrentNum() === '00') {
            tag = true;
            this.minute.add();
            if (this.minute.getCurrentNum() === '00') {
                this.hour.add();
            }
        }
        return tag;
    }
    //秒数减1并退位
    this.minusSecond = function() {
        var tag = false;
        this.second.minus();
        if (this.second.getCurrentNum() === '59') {
            tag = true;
            this.minute.minus();
            if (this.minute.getCurrentNum() === '59') {
                this.hour.minus();
            }
        }
        return tag;
    }
}