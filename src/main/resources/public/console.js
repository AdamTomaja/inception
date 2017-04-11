var myApp = angular.module('inceptionGame', ['luegg.directives']);

function createWebsocketUrl(s) {
    var l = window.location;
    return ((l.protocol === "https:") ? "wss://" : "ws://") + l.host + l.pathname + s;
}

myApp.controller('consoleController', function($scope) {
    $scope.consoleItems = [];
    $scope.models = [];
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

    function renderScene(scene) {
        console.log("Rendering", scene);

        $scope.models.forEach(function(model){
            model.dispose();
        });

        scene.children.forEach(function(entry){
            console.log(entry);
                var sphere = BABYLON.Mesh.CreateSphere('sphere1'+ entry.name, 16, 2, $scope.scene);
                sphere.position.x = entry.location.x
                sphere.position.y = entry.location.y
                sphere.position.z = entry.location.z;
                sphere.material = $scope.materialMapping[entry.type];
                $scope.models.push(sphere);


                 var plane = BABYLON.Mesh.CreatePlane("plane", 3, $scope.scene);
                var planeMaterial = new BABYLON.StandardMaterial("plane material", $scope.scene);
                planeMaterial.backFaceCulling = false;
                var planeTexture = new BABYLON.DynamicTexture("dynamic texture", 512, $scope.scene, true);
                planeTexture.hasAlpha = true;
                planeTexture.drawText(entry.name, 20, 250, "bold 130px Segoe UI", "white", "#555555");
                planeMaterial.diffuseTexture = planeTexture;
                planeMaterial.specularColor = new BABYLON.Color4(0, 0, 0, 0);
                plane.billboardMode = BABYLON.Mesh.BILLBOARDMODE_ALL;
                plane.material = planeMaterial;

                plane.position.x = sphere.position.x;
                plane.position.y = sphere.position.y + 3;
                plane.position.z =  sphere.position.z;

                $scope.models.push(plane);

        });
    }

    socket.onopen = function() {
       console.log("Connected to", url);
       addConsoleReceivedItem("Connected!");
       $scope.disconnected = false;
    }

    socket.onmessage = function(ev) {
        console.log(ev.data);
       var serverEvent = JSON.parse(ev.data);
       switch(serverEvent.type) {
            case "consoleEvent":
                   addConsoleReceivedItem(serverEvent.content);
                break;
            case "renderEvent":
                    renderScene(serverEvent.scene);
                break;
       }
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
        var camera = new BABYLON.FreeCamera('camera1', new BABYLON.Vector3(0, 5,-50), scene);

        // target the camera to scene origin
        camera.setTarget(BABYLON.Vector3.Zero());

        // attach the camera to the canvas
        camera.attachControl(canvas, false);

        // create a basic light, aiming 0,1,0 - meaning, to the sky
        var light = new BABYLON.HemisphericLight('light1', new BABYLON.Vector3(0,1,0), scene);

        // return the created scene
        return scene;
    }

    var scene = createScene();

    $scope.scene = scene;

    $scope.playerMaterial = new BABYLON.StandardMaterial("playerMaterial", $scope.scene);
    $scope.playerMaterial.diffuseColor = new BABYLON.Color3(1, 0.2, 0.7);

    $scope.worldMaterial = new BABYLON.StandardMaterial("worldMaterial", $scope.scene);
    $scope.worldMaterial.diffuseColor = new BABYLON.Color3(1, 1, 0.7);

    $scope.materialMapping = {"World": $scope.worldMaterial, "Player": $scope.playerMaterial};

    engine.runRenderLoop(function() {
        scene.render();
    });

    window.addEventListener('resize', function() {
        engine.resize();
    });

    console.log("Controller initialized");
});