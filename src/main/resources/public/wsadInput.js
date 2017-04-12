var WSADCameraKeyboardInput = function () {
    this._keys = [];
    this.keysUp = [32];
    this.keysDown = [17];
    this.sensibility = 1;
}

WSADCameraKeyboardInput.prototype.attachControl = function (element, noPreventDefault) {
    var _this = this;
    if (!this._onKeyDown) {
        element.tabIndex = 1;
        this._onKeyDown = function (evt) {
            if (_this.keysUp.indexOf(evt.keyCode) !== -1 ||
                _this.keysDown.indexOf(evt.keyCode) !== -1) {
                var index = _this._keys.indexOf(evt.keyCode);
                if (index === -1) {
                    _this._keys.push(evt.keyCode);
                }
                if (!noPreventDefault) {
                    evt.preventDefault();
                }
            }
        };
        this._onKeyUp = function (evt) {
            if (_this.keysUp.indexOf(evt.keyCode) !== -1 ||
                _this.keysDown.indexOf(evt.keyCode) !== -1) {
                var index = _this._keys.indexOf(evt.keyCode);
                if (index >= 0) {
                    _this._keys.splice(index, 1);
                }
                if (!noPreventDefault) {
                    evt.preventDefault();
                }
            }
        };

        element.addEventListener("keydown", this._onKeyDown, false);
        element.addEventListener("keyup", this._onKeyUp, false);
        BABYLON.Tools.RegisterTopRootEvents([
            {name: "blur", handler: this._onLostFocus}
        ]);
    }
}

// Unhook
WSADCameraKeyboardInput.prototype.detachControl = function (element) {
    if (this._onKeyDown) {
        element.removeEventListener("keydown", this._onKeyDown);
        element.removeEventListener("keyup", this._onKeyUp);
        BABYLON.Tools.UnregisterTopRootEvents([
            {name: "blur", handler: this._onLostFocus}
        ]);
        this._keys = [];
        this._onKeyDown = null;
        this._onKeyUp = null;
    }
};

// This function is called by the system on every frame
WSADCameraKeyboardInput.prototype.checkInputs = function () {
    if (this._onKeyDown) {
        var camera = this.camera;
        // Keyboard
        for (var index = 0; index < this._keys.length; index++) {
            var keyCode = this._keys[index];
            if (this.keysUp.indexOf(keyCode) !== -1) {
                camera.position.y += this.sensibility;
            }
            else if (this.keysDown.indexOf(keyCode) !== -1) {
                camera.position.y -= this.sensibility;
            }
        }
    }
};
WSADCameraKeyboardInput.prototype.getTypeName = function () {
    return "WSADCameraKeyboardInput";
};
WSADCameraKeyboardInput.prototype._onLostFocus = function (e) {
    this._keys = [];
};
WSADCameraKeyboardInput.prototype.getSimpleName = function () {
    return "keyboardRotate";
};