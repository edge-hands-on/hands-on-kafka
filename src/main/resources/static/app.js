var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/storage', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function storageAdd() {
    stompClient.send("/app/storage", {}, JSON.stringify({
        'name': $("#storage_name").val(),
        'quantity': $("#storage_quantity").val()
    }));
}

function shopping() {
    stompClient.send("/app/shopping", {}, JSON.stringify({
        'name': $("#sell_product_id").val(),
        'quantity': $("#sell_quantity").val()
    }));
}

function testMethod() {
    console.log("test method called")
    stompClient.send("/app/test", {}, JSON.stringify({
        'name': '',
        'quantity': ''
    }));
    console.log("test method finishing")
}

function showGreeting(message) {
    console.log(message);
}

$(function () {
    $("#connect").click(function() { connect(); });
    $("#disconnect").click(function() { disconnect(); });
    $("#btn_test").click(function() { testMethod(); });
    $("#add_stock").click(function() { storageAdd(); });
    $("#sell_product").click(function() { shopping(); });
});
