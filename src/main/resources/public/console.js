var myApp = angular.module('inceptionGame', ['luegg.directives']);

function createWebsocketUrl(s) {
    var l = window.location;
    return ((l.protocol === "https:") ? "wss://" : "ws://") + l.host + l.pathname + s;
}

myApp.controller('consoleController', function($scope) {
    $scope.consoleItems = [];
    $scope.disconnected = true;

    var url = createWebsocketUrl("/player");
    var socket = new WebSocket(url);

    function addConsoleItem(content, style) {
        $scope.consoleItems.push({content: content, style: style});
    }

    function addConsoleSentItem(content) {
        addConsoleItem(content, "alert-success");
    }

    function addConsoleReceivedItem(content) {
        addConsoleItem(content, "alert-info");
    }

    socket.onopen = function() {
       console.log("Connected to", url);
       addConsoleReceivedItem("Connected!");
       $scope.disconnected = false;
    }

    socket.onmessage = function(ev) {
       addConsoleReceivedItem(ev.data);
       $scope.$apply();
    }

    socket.onclose = function(ev) {
        console.log("Connection closed!", ev);
        addConsoleReceivedItem("Connection closed!");
        $scope.disconnected = true;
        $scope.$apply();
    }

    $scope.ws = socket;
    $scope.execute = function() {
        $scope.ws.send($scope.command);
        addConsoleSentItem("Command sent: " + $scope.command);
        $scope.command = "";
    }

    console.log("Controller initialized");
});