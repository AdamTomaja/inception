myApp.service("renderService", function () {

    var models = [];
    var canvas = document.getElementById("render-canvas");
    var engine = new BABYLON.Engine(canvas, true);
    var cameraPositionListeners = [];


    // create a basic BJS Scene object
    var scene = new BABYLON.Scene(engine);

    // create a FreeCamera, and set its position to (x:0, y:5, z:-10)
    var camera = new BABYLON.FreeCamera("FreeCamera", new BABYLON.Vector3(0, 1, -15), scene);

    camera.keysUp.push(87); // "w"
    camera.keysDown.push(83); // "s"
    camera.keysLeft.push(65); // "a"
    camera.keysRight.push(68); // "d"

    // target the camera to scene origin
    camera.setTarget(BABYLON.Vector3.Zero());

    // attach the camera to the canvas
    camera.attachControl(canvas, false);

    camera.inputs.add(new WSADCameraKeyboardInput());

    setInterval(function () {
        cameraPositionListeners.forEach(function (listener) {
            listener(camera.position);
        });
    }, 1000);

    // create a basic light, aiming 0,1,0 - meaning, to the sky
    var light = new BABYLON.HemisphericLight('light1', new BABYLON.Vector3(0, 1, 0), scene);


    var playerMaterial = new BABYLON.StandardMaterial("playerMaterial", scene);
    playerMaterial.diffuseColor = new BABYLON.Color3(1, 0.2, 0.7);

    var worldMaterial = new BABYLON.StandardMaterial("worldMaterial", scene);
    worldMaterial.diffuseColor = new BABYLON.Color3(1, 1, 0.7);

    var materialMapping = {"World": worldMaterial, "Player": playerMaterial};

    this.renderScene = function (sceneData) {
        console.log("Rendering", scene);

        models.forEach(function (model) {
            model.dispose();
        });

        sceneData.children.forEach(function (entry) {
            console.log(entry);
            var sphere = BABYLON.Mesh.CreateSphere('sphere1' + entry.name, 16, 2, scene);
            sphere.position.x = entry.location.x
            sphere.position.y = entry.location.y
            sphere.position.z = entry.location.z;
            sphere.material = materialMapping[entry.type];
            models.push(sphere);


            var plane = BABYLON.Mesh.CreatePlane("plane", 3, scene);
            var planeMaterial = new BABYLON.StandardMaterial("plane material", scene);
            planeMaterial.backFaceCulling = false;
            var planeTexture = new BABYLON.DynamicTexture("dynamic texture", 512, scene, true);
            planeTexture.hasAlpha = true;
            planeTexture.drawText(entry.name, 20, 250, "bold 130px Segoe UI", "white", "#555555");
            planeMaterial.diffuseTexture = planeTexture;
            planeMaterial.specularColor = new BABYLON.Color4(0, 0, 0, 0);
            plane.billboardMode = BABYLON.Mesh.BILLBOARDMODE_ALL;
            plane.material = planeMaterial;

            plane.position.x = sphere.position.x;
            plane.position.y = sphere.position.y + 3;
            plane.position.z = sphere.position.z;

            models.push(plane);

        });
    }

    this.getCameraPosition = function () {
        return camera.position;
    }

    engine.runRenderLoop(function () {
        scene.render();
    });

    window.addEventListener('resize', function () {
        engine.resize();
    });

    console.log("Render service initialized.");
});