myApp.service("websocketService", function () {

    function createWebsocketUrl(s) {
        var l = window.location;
        return ((l.protocol === "https:") ? "wss://" : "ws://") + l.host + l.pathname + s;
    }

    var onMessageListeners = [];
    var onOpenListeners = [];
    var onCloseListeners = [];

    var url = createWebsocketUrl("/player");
    var socket = new WebSocket(url);

    socket.onopen = function () {
        console.log("Connected to", url);

        onOpenListeners.forEach(function (listener) {
            listener();
        })
    }

    socket.onmessage = function (ev) {
        console.log("Message received", ev.data);
        onMessageListeners.forEach(function (listener) {
            listener(ev.data)
        });
    }

    socket.onclose = function (ev) {
        console.log("Connection closed!");
        onCloseListeners.forEach(function (listener) {
            listener();
        })
    }

    this.addOnMessageListener = function (listener) {
        onMessageListeners.push(listener);
    }

    this.addOnOpenListener = function (listener) {
        onOpenListeners.push(listener);
    }

    this.addOnCloseListener = function (listener) {
        onCloseListeners.push(listener);
    }

    this.send = function(data) {
        socket.send(data);
    }
});