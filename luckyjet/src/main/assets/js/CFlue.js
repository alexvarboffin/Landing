function CFlue(iXPos, iYPos, iType, iID, oParentContainer) {

    var _oParentContainer;
    var _oObject;
    var _oRectangleDown;
    var _oRectangleMeasureDown;
    var _oRectangleUp;
    var _oRectangleMeasureUp;
    var _oShape = null;
    var _iWidth;
    var _iHeight;
    var _iHeightHalf;
    var _iID;
    var _iType;
    var _bPlayerUp = false;

    this._init = function (iXPos, iYPos, iType, iID, oParentContainer) {

        _oParentContainer = oParentContainer;

        _iID = iID;

        _iType = iType;

        var oSprite = s_oSpriteLibrary.getSprite("object_" + iType);

        var iDivH = 2;
        var iDivW = 5;

        var oData = {
            images: [oSprite],
            // width, height & registration point of each sprite
            frames: {width: oSprite.width / iDivW, height: oSprite.height / iDivH, regX: 0, regY: (oSprite.height / 2) / iDivH},
            animations: {
                normal: [0, 9, "reverse", 0.5],
                reverse: {
                    frames: [8, 7, 6, 5, 4, 3, 2, 1],
                    next: "normal",
                    speed: 0.5
                }
            }
        };
        var oSpriteSheet = new createjs.SpriteSheet(oData);
        _oObject = createSprite(oSpriteSheet, "normal", 0, (oSprite.height / 2) / iDivH, oSprite.width / iDivW, oSprite.height / iDivH);
        _oObject.stop();

        _iWidth = oSprite.width / iDivW;
        _iHeight = oSprite.height / iDivH;

        _oObject.x = iXPos;
        _oObject.y = iYPos - _iHeight / 8;

        _iHeightHalf = _iHeight * 0.5;

        _oRectangleMeasureDown = {x: iXPos + OFFSET_FLUE_COLLISION_DOWN.x, y: iYPos + OFFSET_FLUE_COLLISION_DOWN.y, w: _iWidth + OFFSET_FLUE_COLLISION_DOWN.w, h: _iHeight + OFFSET_FLUE_COLLISION_DOWN.h};

        _oRectangleDown = new createjs.Rectangle(_oRectangleMeasureDown.x, _oRectangleMeasureDown.y, _oRectangleMeasureDown.w, _oRectangleMeasureDown.h);

        _oRectangleMeasureUp = {x: iXPos + OFFSET_FLUE_COLLISION_UP.x, y: iYPos + OFFSET_FLUE_COLLISION_UP.y, w: _iWidth + OFFSET_FLUE_COLLISION_UP.w, h: _iHeight + OFFSET_FLUE_COLLISION_UP.h};

        _oRectangleUp = new createjs.Rectangle(_oRectangleMeasureUp.x, _oRectangleMeasureUp.y, _oRectangleMeasureUp.w, _oRectangleMeasureUp.h);

        if (SHOW_COLLISION_SHAPE) {
            _oShape = new createjs.Shape();
            _oShape.graphics.beginFill("#00ff00").drawRect(_oRectangleUp.x, _oRectangleUp.y, _oRectangleUp.width, _oRectangleUp.height);
            _oShape.alpha = 0.5;
            _oParentContainer.addChild(_oShape);
        }

        _oParentContainer.addChild(_oObject);

    };

    this.setPosition = function (iXPos, iYPos) {
        _oObject.x = iXPos;
        _oObject.y = iYPos;

        this.rectMovement();
    };

    this.rectMovement = function () {

        var oPosLocal = this.getPosLocal();

        _oRectangleMeasureDown = {x: oPosLocal.x + OFFSET_FLUE_COLLISION_DOWN.x, y: oPosLocal.y + OFFSET_FLUE_COLLISION_DOWN.y,
            w: _iWidth + OFFSET_FLUE_COLLISION_DOWN.w, h: _iHeight + OFFSET_FLUE_COLLISION_DOWN.h};

        _oRectangleDown.setValues(_oRectangleMeasureDown.x, _oRectangleMeasureDown.y, _oRectangleMeasureDown.w, _oRectangleMeasureDown.h);

        _oRectangleMeasureUp = {x: oPosLocal.x + OFFSET_FLUE_COLLISION_UP.x, y: oPosLocal.y + OFFSET_FLUE_COLLISION_UP.y, w: _iWidth + OFFSET_FLUE_COLLISION_UP.w, h: _iHeight + OFFSET_FLUE_COLLISION_UP.h};
        _oRectangleUp = new createjs.Rectangle(_oRectangleMeasureUp.x, _oRectangleMeasureUp.y, _oRectangleMeasureUp.w, _oRectangleMeasureUp.h);

        if (SHOW_COLLISION_SHAPE) {
            if (_oShape) {
                _oParentContainer.removeChild(_oShape);
                _oShape = null;
            }

            _oShape = new createjs.Shape();
            _oShape.graphics.beginFill("#00ff00").drawRect(_oRectangleDown.x, _oRectangleDown.y, _oRectangleDown.width, _oRectangleDown.height);
            _oShape.alpha = 0.5;
            _oParentContainer.addChild(_oShape);
        }
    };

    this.getX = function () {
        return _oObject.x;
    };
    this.getY = function () {
        return _oObject.y;
    };

    this.getRectX = function () {
        return _oRectangleDown.x;
    };

    this.getWidth = function () {
        return _iWidth;
    };

    this.setVisible = function (bVal) {
        if (_oObject.visible === false && bVal === true) {
            _oObject.play();
        } else if (_oObject.visible === true && bVal === false) {
            _oObject.stop();
        }
        _oObject.visible = bVal;
    };

    this.getVisible = function () {
        return _oObject.visible;
    };

    this.getPosLocal = function () {
        return _oParentContainer.localToGlobal(_oObject.x, _oObject.y);
    };

    this.unload = function () {
        _oParentContainer.removeChild(_oObject);
    };

    this.getID = function () {
        return _iID;
    };

    this.setID = function (iVal) {
        _iID = iVal;
    };

    this.getType = function () {
        return _iType;
    };

    this.controlCollision = function (oPlayerInfo) {
        if (!_oRectangleDown) {
            return;
        }

        if (_oRectangleDown.intersects(oPlayerInfo.rect)) {
            s_oGame.characterDead();
        } else if (s_oGame.getState() === GAME_STATE_PLAY && _oRectangleUp.intersects(oPlayerInfo.rect)) {
            if (!_bPlayerUp) {
                _bPlayerUp = true;
                s_oGame.flueUp(_bPlayerUp);
            }
        } else {
            if (_bPlayerUp) {
                _bPlayerUp = false;
                s_oGame.flueUp(false);
            }
        }
    };



    this.update = function () {

    };

    this._init(iXPos, iYPos, iType, iID, oParentContainer);

    return this;
}





