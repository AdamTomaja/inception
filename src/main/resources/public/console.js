myApp.controller('consoleController', function ($scope, renderService, websocketService, guiService) {
    $scope.consoleItems = [];
    $scope.models = [];
    $scope.disconnected = true;

    var playerNodeId;
    var lastPosition;
    var intervalId;

    renderService.pickListener = function(node) {
        console.log("Node picked:", node);
        addConsoleReceivedItem("======== " + node.id + " ========");
        addConsoleReceivedItem("Item type: " + node.type);
        addConsoleReceivedItem("Item name: " + node.name);
        addConsoleReceivedItem("Item content: " + node.content);
    }

    guiService.lineReceivedListener = function(line) {
        console.log("Line received: ", line);
        sendCommand(line);
    }

    var cameraPositionListener = function () {
        var cameraPosition = renderService.getCameraPosition();
        if (!cameraPosition.equals(lastPosition)) {
            websocketService.sendEvent({"type": "PlayerPositionEvent", "location": cameraPosition});
            lastPosition = cameraPosition.clone();
        }
    };

    websocketService.addOnOpenListener(function () {
        addConsoleReceivedItem("Connected!");
        $scope.disconnected = false;
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
            case "ConsoleEvent":
                addConsoleReceivedItem(serverEvent.content);
                break;
            case "RenderEvent":
                renderService.renderScene(serverEvent.scene);
                break;
            case "JoinEvent":
                console.log("Joined to game!");
                intervalId = setInterval(cameraPositionListener, 100);
                renderService.setPlayerPosition(serverEvent.player.location);
                playerNodeId = serverEvent.player.id;
                break;
            case "NodePositionChangedEvent":
                if(serverEvent.node == playerNodeId) {
                    renderService.setPlayerPosition(serverEvent.location);
                } else {
                    renderService.updateNodePosition(serverEvent.node, serverEvent.location);
                }
                break;
            case "NodeCreatedEvent":
                renderService.addNode(serverEvent.node);
                break;
            case "NodeRemovedEvent":
                renderService.removeNode(serverEvent.node);
                break;
            default:
                console.error("Unkown event type: ", serverEvent.type);
        }
        $scope.$apply();
    });

    function addConsoleItem(content, style) {
        guiService.println(content);
        $scope.consoleItems.push({content: content, style: style});
    }

    function addConsoleSentItem(content) {
        addConsoleItem(content, "alert-success");
    }

    function addConsoleReceivedItem(content) {
        addConsoleItem(content, "alert-info");
    }

    function sendCommand(command) {
        websocketService.sendEvent({"type": "CommandEvent", "command": command});
    }

    $scope.execute = function () {
        sendCommand($scope.command);
        $scope.command = "";
    }

    $scope.fullscreen = function() {
       renderService.enterFullScreen();
    }

    console.log("Controller initialized");
});