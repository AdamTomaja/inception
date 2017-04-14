myApp.service("renderService", function () {

    var $this = this;

    var models = {};
    var canvas = document.getElementById("render-canvas");
    var engine = new BABYLON.Engine(canvas, true);

    this.pickListener = function (node) {
        // dummy listener
    }

    // create a basic BJS Scene object
    var scene = new BABYLON.Scene(engine);

    scene.onPointerDown = function (evt, pickResult) {
        $this.pickListener(pickResult.pickedMesh.node);
    };

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

    function setModelPosition(model, position) {
        model.position.x = position.x;
        model.position.y = position.y;
        model.position.z = position.z;
    }

    function createPlayer(entry) {
        var sphere = BABYLON.Mesh.CreateSphere('sphere1' + entry.id, 16, 2, scene);
        sphere.node = entry;
        setModelPosition(sphere, entry.location);

        var material = new BABYLON.StandardMaterial("playerMaterial", scene);
        material.diffuseColor = new BABYLON.Color3(entry.color.r, entry.color.g, entry.color.b);

        sphere.material = material;
        models[entry.id] = sphere;

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

        setModelPosition(plane, sphere.position);
        plane.position.y = sphere.position.y + 3;

        models[entry.id + "_plane"] = plane;
    }

    function createWorld(entry) {
        var sphere = BABYLON.Mesh.CreateSphere('sphere1' + entry.id, 16, 10, scene);
        sphere.node = entry;

        setModelPosition(sphere, entry.location);

        var material = new BABYLON.StandardMaterial("playerMaterial", scene);
        material.diffuseColor = new BABYLON.Color3(entry.color.r, entry.color.g, entry.color.b);

        sphere.material = material;
        models[entry.id] = sphere;

        var plane = BABYLON.Mesh.CreatePlane("plane", 8, scene);
        var planeMaterial = new BABYLON.StandardMaterial("plane material", scene);
        planeMaterial.backFaceCulling = false;
        var planeTexture = new BABYLON.DynamicTexture("dynamic texture", 512, scene, true);
        planeTexture.hasAlpha = true;
        planeTexture.drawText(entry.name, 20, 250, "bold 130px Segoe UI", "white", "#555555");
        planeMaterial.diffuseTexture = planeTexture;
        planeMaterial.specularColor = new BABYLON.Color4(0, 0, 0, 0);
        plane.billboardMode = BABYLON.Mesh.BILLBOARDMODE_ALL;
        plane.material = planeMaterial;

        setModelPosition(plane, sphere.position);
        plane.position.y = sphere.position.y + 10;

        models[entry.id + "_plane"] = plane;
    }

    function createHeritage(entry) {
        var box = BABYLON.Mesh.CreateBox("box", 6.0, scene);
        box.node = entry;

        setModelPosition(box, entry.location);

        var planeMaterial = new BABYLON.StandardMaterial("plane material", scene);
        planeMaterial.backFaceCulling = false;
        var planeTexture = new BABYLON.DynamicTexture("dynamic texture", 512, scene, true);
        planeTexture.hasAlpha = true;
        planeTexture.drawText(entry.name, 20, 250, "bold 130px Segoe UI", "white", "#555555");
        planeMaterial.diffuseTexture = planeTexture;
        planeMaterial.specularColor = new BABYLON.Color3(entry.color.r, entry.color.g, entry.color.b);
        box.material = planeMaterial;


        models[entry.id] = box;
    }

    var renderersMapping = {"Player": createPlayer, "Heritage": createHeritage, "World": createWorld};

    function addNode(entry) {
        renderersMapping[entry.type](entry);
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
        setModelPosition(models[nodeid], position);
        setModelPosition(models[nodeid + "_plane"], position);
        models[nodeid + "_plane"].position.y = position.y + 3;
    }

    this.addNode = addNode;

    this.removeNode = function (nodeid) {
        models[nodeid].dispose();
        models[nodeid + "_plane"].dispose();
    }

    this.setPlayerPosition = function (position) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.position.z = position.z;
    }

    this.getScene = function () {
        return scene;
    }

    engine.runRenderLoop(function () {
        scene.render();
    });

    window.addEventListener('resize', function () {
        engine.resize();
    });

    console.log("Render service initialized.");
});