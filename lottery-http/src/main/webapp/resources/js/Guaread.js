/**
 * Created by Administrator on 2017/8/11.
 */

$(function () {
    window.onload= function(){
        $('#articleBox').find('img').each(function(index){
            $(this).width('100%')
        });
    }
    var titleindex = null;
    var flag = true;
    FastClick.attach(document.body);
    /*让文字和标签的大小随着屏幕的尺寸做变化 等比缩放*/
    var html = document.getElementsByTagName('html')[0];
    /*取到屏幕的宽度*/
    var width = window.innerWidth;
    /*640 100  320 50*/
    if (width > 720) {
        width = 720
    } else if (width < 320) {
        width = 320
    }
    /*设置fontsize*/
    var fontSize = 100 / 720 * width;
    html.style.fontSize = fontSize + 'px';
    window.onresize = function () {
        var html = document.getElementsByTagName('html')[0];
        /*取到屏幕的宽度*/
        var width = window.innerWidth;
        if (width > 720) {
            width = 720
        } else if (width < 320) {
            width = 320
        }
        /*640 100  320 50*/
        var fontSize = 100 / 720 * width;
        /*设置fontsize*/
        html.style.fontSize = fontSize + 'px';
    }
    //get nav JS
    getindexnav();
    function bindNavEvent() {
        $('#1').addClass('active');
        $('li').on('click', '.t-title', function () {
            $('.made > li').children().removeClass('active');
            $(this).addClass('active');
            var madeliLength = $(this).parent().width();
            if($(this).parent().index() >= 3) {
                $('.made').offset({left:-madeliLength});
            } else {
                $('.made').offset({left:0});
            }
            // $('.swiper-slide').hide();
            // $($('.swiper-slide')[$(this).parent().index()]).show();
        });
    }
    function getindexnav() {
        $.ajax({
            url: "article/ArticleType",
            data: {"Infversion": "1.0",},
            success: function (data) {
                var data = JSON.parse(data)
                var dataArr = data.data;
                var arr = [];
                for(var j = 0;j<dataArr.length;j++){
                    var value = dataArr[j];
                    arr.push(value.item);
                }
                var html = template("indexNav", data);
                $('#wrapper03').html(html);
                bindNavEvent();
                function iScrollClick(){
                    if (/iPhone|iPad|iPod|Macintosh/i.test(navigator.userAgent)) return false;
                    if (/Chrome/i.test(navigator.userAgent)) return (/Android/i.test(navigator.userAgent));
                    if (/Silk/i.test(navigator.userAgent)) return false;
                    if (/Android/i.test(navigator.userAgent)) {
                        var s=navigator.userAgent.substr(navigator.userAgent.indexOf('Android')+8,3);
                        return parseFloat(s[0]+s[3]) < 44 ? false : true
                    }
                }
                var myScroll = new IScroll('#wrapper03', {
                    scrollX: true,
                    scrollY: true,
                    click: iScrollClick()
                });

                var obj = {};
                obj.value = arr[0];
                    var html = template("Slideshow", obj);
                    $('#picshow').html(html);

                document.addEventListener('touchmove', function (e) {
                    e.preventDefault();
                }, false);
                getArticlelist(1,arr);
                $('#navAs li .t-title').each(function (i,v) {
                    $(v).click(function(){

                        getArticlelist(v.id,arr);

                    })
                })
            }
        });
    }
    //auto slideshow init JS
    var swiper = new Swiper('.swiper-container', {
        pagination: '.pagination',
        loop: true,
        grabCursor: true,
        autoplay: 3000,
        autoplayDisableOnInteraction: false
    });


    //上拉加载数据
    var i;
    function pullUpAction(catalogid,myScroll) {
        if (!flag) {
            return;
        }
        flag = false;
        $(".cover").show();
        $(".loading").show();
        i++;
        console.log(i)
        $.ajax({
            type: "post",
            dataType: "json",
            url: "article/ArticleList",
            data: {
                "UID": "1500724505",
                "pageSize": "20",
                "pageIndex": i,
                "catalogid": catalogid,
                "Infversion": "1.0"
            },
            success: function (data) {
                var html = template('Articlelist', data);
                $("#Content").append(html);
                console.log(myScroll);
                myScroll.refresh();
                flag = true;
                $(".cover").fadeOut(100);
                $(".loading").fadeOut(100);
            }
        });
    }

//下拉刷新当前数据
    function pullDownAction() {
        $(".cover").show();
        $(".loading").show();
        $.ajax({
            type: "post",
            dataType: "json",
            url: "article/ArticleList",
            data: {
                "UID": "1500724505",
                "pageSize": "20",
                "pageIndex": "1",
                "catalogid": titleindex,
                "Infversion": "1.0"
            },
            success: function (data) {
                var html = template('Articlelist', data);
                $("#Content").html(html);
                $(".cover").fadeOut(100);
                $(".loading").fadeOut(100);
                return;
            }
        });
    }


    function getArticlelist(catalogid,arr) {
        $('#Content').html(" ");
        titleindex = catalogid;
        for(var k = 0;k<arr.length;k++){
            if(k+1==catalogid){
                var obj = {};
                obj.value = arr[k];
                console.log(arr[k]);
                var html = template("Slideshow", obj);
                $('#picshow').html(html);
            }
        }
        $.ajax({
            url: "article/ArticleList",
            data: {
                "UID": "1500724505",
                "pageSize": "20",

                "pageIndex": "1",
                "catalogid": catalogid,
                "Infversion": "1.0"
            },
            success: function (data) {
                data = JSON.parse(data)
                var html = template('Articlelist', data);
                $('#Content').html(html);
                // $(window).scrollTop(0);
                var scrollTop = $(window).scrollTop();               //滚动条距离顶部的高度
                var scrollHeight = $(document).height();                   //当前页面的总高度
                var windowHeight = $(window).height();
                var pullDown = document.querySelector("#PullDown"),
                    pullUp = document.querySelector("#PullUp"),
                    isPulled = false; // 拉动标记

                var myScroll = new IScroll('#MyScroller', {
                    probeType: 3,
                    mouseWheel: true,
                    scrollbars: true,
                    preventDefault: false,
                    autoHeight: true,
                    fadeScrollbars: true
                }), liHeight = $('#Content>li').height() + $('').height();
                myScroll.maxScrollY -= liHeight;

                i = 1;
                document.body.style.height = Math.max(document.documentElement.clientHeight, window.innerHeight || 0) + 'px';
                myScroll.on('scroll', function () {
                    var height = this.y,
                        bottomHeight = this.maxScrollY - height;
                    // console.log("bottomHeight:" + bottomHeight);
                    // console.log("bottomHeight:" + bottomHeight);
                    // 控制下拉显示
                    if (height >= 30) {
                        pullDown.style.display = "block";
                        isPulled = true;
                        pullDownAction(titleindex);
                    } else if (height < 30 && height >= 0) {
                        pullDown.style.display = "none";
                        return;
                    }
                    // 控制上拉显示
                    if (bottomHeight >= 30) {
                        pullUp.style.display = "block";
                        isPulled = true;
                        pullUpAction(catalogid,myScroll);
                        return;
                    } else if (bottomHeight < 30 && bottomHeight >= 0) {
                        pullUp.style.display = "none";
                        return;
                    }
                });

                myScroll.on('scrollEnd', function () { // 滚动结束
                    if (isPulled) { // 如果达到触发条件，则执行加载
                        isPulled = false;
                    }
                });
            }
        });
    }
});