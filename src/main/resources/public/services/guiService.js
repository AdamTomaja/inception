myApp.service("guiService", function (renderService) {

    var buffer = [];
    var timeoutId;

    var scene = renderService.getScene();

    var canvas = new BABYLON.ScreenSpaceCanvas2D(scene, {
        id: "ScreenCanvas",
        backgroundFill: "#FF404000",
        backgroundRoundRadius: 50
    });

    var element = new BABYLON.Text2D(buffer, {
        text: "Inception game",
        parent: canvas,
        id: "text",
        marginAlignment: "h: left, v:top",
        fontName: "15pt Arial",
        paddingLeft: 10,
        paddingTop: 10
    });

    element.levelVisible = true;

    this.println = function (text) {
        clearTimeout(timeoutId);

        buffer.push(text);
        if (buffer.length > 5) {
            buffer.splice(0, 1);
        }

        element.text = buffer.join("\r\n");

        element.levelVisible = true;

        timeoutId = setTimeout(function () {
            element.levelVisible = false;
        }, 3 * 1000);
    }

    scene.actionManager = new BABYLON.ActionManager(scene);
    scene.actionManager.registerAction(new BABYLON.ExecuteCodeAction(BABYLON.ActionManager.OnKeyUpTrigger, function (evt) {
        if (evt.sourceEvent.key == "Escape") {
            element.levelVisible = !element.levelVisible;
        }
    }));
});