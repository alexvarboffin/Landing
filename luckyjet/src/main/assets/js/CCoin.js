function CCoin(iXPos, iYPos, iType, iID, oParentContainer, oSection) {

    var _oParentContainer;
    var _oObject;
    var _oRectangle;
    var _oRectangleMeasure;
    var _oShape = null;
    var _oSection = oSection;
    var _iWidth;
    var _iHeight;
    var _iHeightHalf;
    var _iID;
    var _iType;
    var _bToken = false;

    this._init = function (iXPos, iYPos, iType, iID, oParentContainer) {

        _oParentContainer = oParentContainer;

        _iID = iID;

        _iType = iType;

        var oSprite = s_oSpriteLibrary.getSprite("object_" + iType);

        var iDivH = 2;
        var iDivW = 7;

        var oData = {
            images: [oSprite],
            // width, height & registration point of each sprite
            frames: {width: oSprite.width / iDivW, height: oSprite.height / iDivH, regX: 0, regY: (oSprite.height / 2) / iDivH},
            animations: {
                normal: [0, 13, "normal", 0.5]
            }
        };
        var oSpriteSheet = new createjs.SpriteSheet(oData);
        _oObject = createSprite(oSpriteSheet, "normal", 0, (oSprite.height / 2) / iDivH, oSprite.width / iDivW, oSprite.height / iDivH);
        _oObject.stop();

        _oObject.x = iXPos;
        _oObject.y = iYPos;

        _iWidth = oSprite.width / iDivW;
        _iHeight = oSprite.height / iDivH;

        _iHeightHalf = _iHeight * 0.5;

        _oRectangleMeasure = {x: iXPos, y: iYPos - _iHeightHalf, w: _iWidth, h: _iHeight};

        _oRectangle = new createjs.Rectangle(iXPos, iYPos, _iWidth, _iHeight);

        if (SHOW_COLLISION_SHAPE) {
            _oShape = new createjs.Shape();
            _oShape.graphics.beginFill("#00ff00").drawRect(_oRectangle.x, _oRectangle.y, _oRectangle.width, _oRectangle.height);
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

        _oRectangleMeasure = {x: oPosLocal.x, y: oPosLocal.y - _iHeightHalf, w: _iWidth, h: _iHeight};

        _oRectangle.setValues(_oRectangleMeasure.x, _oRectangleMeasure.y, _oRectangleMeasure.w, _oRectangleMeasure.h);

        if (SHOW_COLLISION_SHAPE) {
            if (_oShape) {
                _oParentContainer.removeChild(_oShape);
                _oShape = null;
            }

            _oShape = new createjs.Shape();
            _oShape.graphics.beginFill("#00ff00").drawRect(_oRectangle.x, _oRectangle.y, _oRectangle.width, _oRectangle.height);
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

    this.getPosLocal = function () {
        return _oParentContainer.localToGlobal(_oObject.x, _oObject.y);
    };

    this.getRectX = function () {
        return _oRectangle.x;
    };

    this.getWidth = function () {
        return _iWidth;
    };

    this.setToken = function (bVal) {
        _bToken = bVal;
    };

    this.setVisible = function (bVal) {
        if (_bToken) {
            return;
        }
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
        if (!_oRectangle) {
            return;
        }
        var oParent = this;
        if (_oRectangle.intersects(oPlayerInfo.rect)) {
            if (!_bToken) {
                s_oGame.addScore(COIN_SCORE);
                playSound("coin", 1, false);
                s_oGame.addObtainCoin();
                _bToken = true;
                _oObject.visible = false;
                s_oInterface.coinEffect(oParent.getPosLocal());
            }
        }
    };

    this.update = function () {

    };

    this._init(iXPos, iYPos, iType, iID, oParentContainer);

    return this;
}





