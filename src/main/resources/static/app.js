var stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  } else {
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
    stompClient.subscribe('/topic/storage', updateStorageTableAndSelectOptions);
    stompClient.subscribe("/user/topic/storage/status", updateStorageTableAndSelectOptions);

    stompClient.send("/app/storage/status");
  });
}

function updateStorageTableAndSelectOptions(storageMessage) {
  let tbody = $("#stock_state tbody");
  tbody.html("")

  let selectEl = $("#sell_product_id");
  selectEl.empty();
  $.each(JSON.parse(storageMessage.body), function (index, item) {
    $("<tr></tr>")
        .append($("<td></td>").text(item.name))
        .append($("<td></td>").text(item.quantity))
        .appendTo(tbody);

    selectEl.append($("<option></option>")
        .attr("value", item.name)
        .text(item.name)
    );
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

$(function () {
  $("#connect").click(function () {
    connect();
  });
  $("#disconnect").click(function () {
    disconnect();
  });
  $("#btn_test").click(function () {
    testMethod();
  });
  $("#add_stock").click(function () {
    storageAdd();
  });
  $("#sell_product").click(function () {
    shopping();
  });
});
