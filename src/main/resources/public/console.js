myApp.controller('consoleController', function ($scope, renderService, websocketService) {
    $scope.consoleItems = [];
    $scope.models = [];
    $scope.disconnected = true;

    var intervalId;

    var cameraPositionListener = function (cameraPosition) {
        console.log("Camera position", cameraPosition);
        websocketService.send(JSON.stringify(cameraPosition));
    };

    websocketService.addOnOpenListener(function () {
        addConsoleReceivedItem("Connected!");
        $scope.disconnected = false;
        // intervalId = setInterval(cameraPositionListener, 1000);
    });

    websocketService.addOnCloseListener(function (ev) {
        console.log("Connection closed!", ev);
        addConsoleReceivedItem("Connection closed!");
        $scope.disconnected = true;
        $scope.$apply();
        clearInterval(intervalId);
    });

    websocketService.addOnMessageListener(function (data) {
        console.log(data);
        var serverEvent = JSON.parse(data);
        switch (serverEvent.type) {
            case "consoleEvent":
                addConsoleReceivedItem(serverEvent.content);
                break;
            case "renderEvent":
                renderService.renderScene(serverEvent.scene);
                break;
        }
        $scope.$apply();
    });

    function addConsoleItem(content, style) {
        $scope.consoleItems.push({content: content, style: style});
    }

    function addConsoleSentItem(content) {
        addConsoleItem(content, "alert-success");
    }

    function addConsoleReceivedItem(content) {
        addConsoleItem(content, "alert-info");
    }


    $scope.execute = function () {
        websocketService.sendEvent({"type": "CommandEvent", "command": $scope.command});
        addConsoleSentItem("Command sent: " + $scope.command);
        $scope.command = "";
    }

    console.log("Controller initialized");
});