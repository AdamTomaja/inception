myApp.service("renderService", function () {

    var models = {};
    var canvas = document.getElementById("render-canvas");
    var engine = new BABYLON.Engine(canvas, true);

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

    // create a basic light, aiming 0,1,0 - meaning, to the sky
    var light = new BABYLON.HemisphericLight('light1', new BABYLON.Vector3(0, 1, 0), scene);

    var playerMaterial = new BABYLON.StandardMaterial("playerMaterial", scene);
    playerMaterial.diffuseColor = new BABYLON.Color3(1, 0.2, 0.7);

    var worldMaterial = new BABYLON.StandardMaterial("worldMaterial", scene);
    worldMaterial.diffuseColor = new BABYLON.Color3(1, 1, 0.7);

    var materialMapping = {"World": worldMaterial, "Player": playerMaterial};

    // Skybox
    var skybox = BABYLON.Mesh.CreateBox("skyBox", 10000.0, scene);
    var skyboxMaterial = new BABYLON.StandardMaterial("skyBox", scene);
    skyboxMaterial.backFaceCulling = false;
    skyboxMaterial.reflectionTexture = new BABYLON.CubeTexture("textures/skybox", scene);
    skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE;
    skyboxMaterial.diffuseColor = new BABYLON.Color3(0, 0, 0);
    skyboxMaterial.specularColor = new BABYLON.Color3(0, 0, 0);
    skyboxMaterial.disableLighting = true;
    skybox.material = skyboxMaterial;

    function addNode(entry) {
        var sphere = BABYLON.Mesh.CreateSphere('sphere1' + entry.name, 16, 2, scene);
        sphere.position.x = entry.location.x
        sphere.position.y = entry.location.y
        sphere.position.z = entry.location.z;
        sphere.material = materialMapping[entry.type];
        models[entry.name] = sphere;


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

        models[entry.name + "_plane"] = plane;
    }

    this.renderScene = function (sceneData) {
        models = {};
        console.log("Rendering", scene);

        for (var nodeid in models) {
            models[nodeid].dispose();
        }

        sceneData.children.forEach(function (entry) {
            console.log(entry);
            addNode(entry);
        });
    }

    this.getCameraPosition = function () {
        return camera.position;
    }

    this.updateNodePosition = function (nodeid, position) {
        var animationBoxX = new BABYLON.Animation("myAnimationX", "position.x", 30, BABYLON.Animation.ANIMATIONTYPE_FLOAT, BABYLON.Animation.ANIMATIONLOOPMODE_CYCLE);
        var animationBoxY = new BABYLON.Animation("myAnimationY", "position.y", 30, BABYLON.Animation.ANIMATIONTYPE_FLOAT, BABYLON.Animation.ANIMATIONLOOPMODE_CYCLE);
        var animationBoxZ = new BABYLON.Animation("myAnimationZ", "position.z", 30, BABYLON.Animation.ANIMATIONTYPE_FLOAT, BABYLON.Animation.ANIMATIONLOOPMODE_CYCLE);
        animationBoxX.setKeys([{frame: 0, value: models[nodeid].position.x}, {frame: 100, value: position.x}]);
        animationBoxY.setKeys([{frame: 0, value: models[nodeid].position.y}, {frame: 100, value: position.y}]);
        animationBoxZ.setKeys([{frame: 0, value: models[nodeid].position.z}, {frame: 100, value: position.z}]);

        var model = models[nodeid];
        model.animations.push(animationBoxX);
        model.animations.push(animationBoxY);
        model.animations.push(animationBoxZ);

        scene.beginAnimation(model, 0, 100, true);
    }

    this.addNode = addNode;

    this.removeNode = function (nodeid) {
        models[nodeid].dispose();
    }

    engine.runRenderLoop(function () {
        scene.render();
    });

    window.addEventListener('resize', function () {
        engine.resize();
    });

    console.log("Render service initialized.");
});