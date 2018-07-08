function regist() {
    var input = $("#idport").val();
    $.get("/regist", {port: input}, function (data) {
        $("#info").html(data);
    })
}

function conn() {
    $.get("/conn", function (data) {
        $("#info").html(data);
    })
}

function sendMessage() {
    var input = $("#idmsg").val();
    $.get("/broadcast", {msg: input}, function (data) {
        $("#info").html(data);
    })
}

function syncBlock() {
    loading.baosight.showPageLoadingMsg(false);
    $.get("/syndata", function (data) {
        $("#info").html(data);
        showList()
        loading.baosight.hidePageLoadingMsg();
    })
}