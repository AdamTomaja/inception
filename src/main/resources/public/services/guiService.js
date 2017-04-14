myApp.service("guiService", function (renderService) {

    this.lineReceivedListener = function() {
        // dummy listener
    }

    $this = this;

    var buffer = [];
    var timeoutId;

    var scene = renderService.getScene();
    var consoleEnabled = false;
    var inputBuffer = "";

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

    var inputText = new BABYLON.Text2D("Hello", {
        parent: canvas,
        id: "text",
        marginAlignment: "h: left, v:bottom",
        fontName: "15pt Arial",
        paddingLeft: 10,
        paddingTop: 0
    });

    canvas.levelVisible = true;

    this.println = function (text) {
        clearTimeout(timeoutId);

        buffer.push(text);
        if (buffer.length > 5) {
            buffer.splice(0, 1);
        }

        element.text = buffer.join("\r\n");

        canvas.levelVisible = true;

        timeoutId = setTimeout(function () {
            canvas.levelVisible = false;
        }, 3 * 1000);
    }

    scene.actionManager = new BABYLON.ActionManager(scene);
    scene.actionManager.registerAction(new BABYLON.ExecuteCodeAction(BABYLON.ActionManager.OnKeyUpTrigger, function (evt) {
        var key = evt.sourceEvent.key;
        switch(key) {
            case "Escape":
                canvas.levelVisible = !canvas.levelVisible;
                consoleEnabled = canvas.levelVisible;
                inputBuffer = "";
                clearTimeout(timeoutId);
                break;
            case "Backspace":
                inputBuffer = inputBuffer.substr(0, inputBuffer.length - 1);
                break;
            case "Enter":
                $this.lineReceivedListener(inputBuffer);
                inputBuffer = "";
                break;
            default:
                if(canvas.levelVisible && key.length == 1) {
                    inputBuffer += key;
                }
        }

        inputText.text = inputBuffer;
    }));
});