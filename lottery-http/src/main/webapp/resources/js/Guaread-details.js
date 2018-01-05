/**
 * Created by pw on 2017/8/9.
 */

$(function () {
    window.onload= function(){
        $('#articleBox').find('img').each(function(index){
            $(this).width('100%')
        });
    }
    // var ste = setInterval((function(){var $talls = $('#articleBox').find('img,span')}))
    // $ths = $('#articleBox').find("img");
    // $talls.each(function(i, item) {
    //     var nodeName = $(item).prop("nodeName");
    //     if (nodeName != "IMG") {
    //         $(item).css({
    //             "font-size": "",
    //             "line-height": "",
    //             "width": "",
    //             "height": ""
    //         });
    //     }
    // });
    // 让文字和标签的大小随着屏幕的尺寸做变话 等比缩放
    var html = document.getElementsByTagName('html')[0];
// 取到屏幕的宽度
    var width = window.innerWidth;
// 640 100  320 50
    if (width > 720) {
        width = 720
    }
    else if (width < 320) {
        width = 320
    }
    var fontSize = 100 / 720 * width;
// 设置fontsize

    html.style.fontSize = fontSize + 'px';
    window.onresize = function () {
        var html = document.getElementsByTagName('html')[0];
        // 取到屏幕的宽度
        var width = window.innerWidth;
        if (width > 720) {
            width = 720
        }
        else if (width < 320) {
            width = 320
        }
        // 640 100  320 50
        var fontSize = 100 / 720 * width;
        // 设置fontsize
        html.style.fontSize = fontSize + 'px';
    }



});


$(".cover").show();
$(".loading").show();

getContent();
function getContent() {
    $.ajax({
        type:"post",
        url: "ArticleDetail",
        data:{"articleid": $("#artilceid").val(), "Infversion": "1.0"},
        dataType: "json",
        success: function (obj) {
            var html = template('content',  obj.data);
            $('#articleBox').html(html);
        }
    })
}

getReadList();
function getReadList(){
    $.ajax({
        type:"post",
        url: "ArticleRelatedReading",
        data:{"articleid": $("#artilceid").val(), "Infversion": "1.0"},
        success: function (res) {
            var odata = JSON.parse(res)
            var html = template('ReadList', odata);
            $('.read-list').html(html);

            $(".cover").fadeOut(1000);
            $(".loading").fadeOut(1000);
        }
    })
}

/*
var img = getElementByTagName("img");
for( var i = 0; i <img.length; i++){
    img[i].style("width","100%");
}
*/


