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


    var canvas = document.getElementById("render-canvas");
    var engine = new BABYLON.Engine(canvas, true);

    var createScene = function() {
        // create a basic BJS Scene object
        var scene = new BABYLON.Scene(engine);

        // create a FreeCamera, and set its position to (x:0, y:5, z:-10)
        var camera = new BABYLON.FreeCamera('camera1', new BABYLON.Vector3(0, 5,-10), scene);

        // target the camera to scene origin
        camera.setTarget(BABYLON.Vector3.Zero());

        // attach the camera to the canvas
        camera.attachControl(canvas, false);

        // create a basic light, aiming 0,1,0 - meaning, to the sky
        var light = new BABYLON.HemisphericLight('light1', new BABYLON.Vector3(0,1,0), scene);

        // create a built-in "sphere" shape; its constructor takes 4 params: name, subdivisions, radius, scene
        var sphere = BABYLON.Mesh.CreateSphere('sphere1', 16, 2, scene);

        // move the sphere upward 1/2 of its height
        sphere.position.y = 1;

        // create a built-in "ground" shape; its constructor takes 5 params: name, width, height, subdivisions and scene
        var ground = BABYLON.Mesh.CreateGround('ground1', 6, 6, 2, scene);

        // return the created scene
        return scene;
    }

    var scene = createScene();

    engine.runRenderLoop(function() {
        scene.render();
    });

    window.addEventListener('resize', function() {
        engine.resize();
    });

    console.log("Controller initialized");
});