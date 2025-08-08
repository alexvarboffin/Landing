function CCharacter(iXPos, iYPos, oSprite, oParentContainer) {

    var _oCharacter;
    var _oParentContainer;
    var _oRectangle;
    var _oShape = null;
    var _iSpeedAccDown;
    var _iSpeedDown;
    var _iIDPlatform;
    var _iWidth;
    var _iHeight;
    var _iLimitY;
    var _oRectangleMeasure;
    var _bCollide = false;
    var _bOnPlatform = false;
    var _bMouseDown = false;
    var _bFlueUp = false;

    this._init = function (iXPos, iYPos, oSprite, oParentContainer) {

        _oParentContainer = oParentContainer;

        var oData = {
            images: [oSprite],
            // width, height & registration point of each sprite
            frames: {width: oSprite.width / 5, height: oSprite.height / 8, regX: (oSprite.width / 2) / 5, regY: (oSprite.height) / 8},
            animations: {
                run: [0, 14, "run", 1],
                fly_die: [24, 30, "fly_die_stay", 1],
                fly_die_stay: 30,
                die: [31, 37, "die_stay"],
                die_stay: 37,
                fly: [15, 23, "fly"]
            }
        };
        var oSpriteSheet = new createjs.SpriteSheet(oData);
        _oCharacter = createSprite(oSpriteSheet, "run", (oSprite.width / 2) / 5, (oSprite.height) / 8, oSprite.width / 5, oSprite.height / 8);

        _oCharacter.x = iXPos;
        _oCharacter.y = iYPos;

        _iSpeedAccDown = HERO_DOWN_ACCELLERATION;

        _iWidth = oSprite.width / 5;
        _iHeight = oSprite.height / 8;

        _iLimitY = CHARECTER_LIMIT_Y + _iHeight * 0.5;

        _oRectangleMeasure = {x: iXPos - _iWidth + OFFSET_PLAYER_COL_POINT.x, y: iYPos - _iHeight + OFFSET_PLAYER_COL_POINT.y,
            w: _iWidth + OFFSET_PLAYER_COL_DIMENSION.x, h: _iHeight + OFFSET_PLAYER_COL_DIMENSION.y};

        _oRectangle = new createjs.Rectangle(_oRectangleMeasure.x, _oRectangleMeasure.y,
                _oRectangleMeasure.w, _oRectangleMeasure.h);

        if (SHOW_COLLISION_SHAPE) {
            _oShape = new createjs.Shape();
            _oShape.graphics.beginFill("#00ff00").drawRect(_oRectangle.x, _oRectangle.y,
                    _oRectangle.width, _oRectangle.height);
            _oShape.alpha = 0.5;
            _oParentContainer.addChild(_oShape);
        }

        _iSpeedDown = 0;
        _iIDPlatform = 0;

        playSound("step", 1,true);
        playSound("jet", 0.3, true);

        this.stopStepSound();
        this.stopJetSound();

        _oParentContainer.addChild(_oCharacter);

    };

    this.getX = function () {
        return _oCharacter.x;
    };

    this.getY = function () {
        return _oCharacter.y;
    };

    this.onPlatform = function (bVal) {
        _bOnPlatform = bVal;
    };

    this.setScale = function (fVal) {
        _oCharacter.scaleX = _oCharacter.scaleY = fVal;
    };

    this.getOnPlatform = function () {
        return _bOnPlatform;
    };

    this.setFlue = function (bVal) {
        _bFlueUp = bVal;
    };

    this.animationStartGame = function () {
        var oParent = this;
        _oCharacter.gotoAndPlay("start_0");
        _oCharacter.on("animationend", function () {
            _oCharacter.removeAllEventListeners();
            _oCharacter.gotoAndPlay("start_1");
            oParent.stayMouseDown(true);
            _iSpeedDown = -HERO_DOWN_ACCELLERATION * (JUMP_MULTIPLY) * 0.5;
            _iSpeedAccDown = -HERO_DOWN_ACCELLERATION;
            _bCollide = false;
            s_oGame.increasePlatformSpeedAnim(true);
            _oCharacter.on("animationend", function () {
                _oCharacter.removeAllEventListeners();
                _oCharacter.gotoAndPlay("fly");
                oParent.stayMouseDown(false);
                s_oGame.increasePlatformSpeedAnim(false);
                s_oGame.startAfterAnim();
            });
        });
    };

    this.setPosition = function (iXPos, iYPos) {
        if (iXPos === null) {

        } else {
            _oCharacter.x = iXPos;
        }
        if (iYPos === null) {

        } else {
            _oCharacter.y = iYPos;
        }

        this.rectMovement();
    };

    this.playSoundStep = function () {
        playExistingSound("step");
    };

    this.stopStepSound = function () {
        stopSound("step");
    };

    this.playSoundJet = function () {
        playExistingSound("jet");
    };

    this.stopJetSound = function () {
        stopSound("jet");
    };

    this.rotate = function (iValue) {
        _oCharacter.scaleX = iValue;
    };

    this.setVisible = function (bVal) {
        _oCharacter.visible = bVal;
    };

    this.changeState = function (szState) {
        _oCharacter.gotoAndPlay(szState);
    };

    this.stopAnimation = function () {
        _oCharacter.stop();
    };

    this.playAnimation = function () {
        _oCharacter.play();
    };

    this.onFinishAnimation = function (oCallFunc) {
        _oCharacter.on("animationend", function () {
            oCallFunc();
        });
    };

    this.collisionWithPlatform = function (bVal) {
        if (_bCollide === true) {
            _oCharacter.gotoAndPlay("jump");
        }
        _bCollide = bVal;
    };

    this.getCollisionWithPlatform = function () {
        return _bCollide;
    };

    this.getRectangle = function () {
        return _oRectangle;
    };

    this.moveUp = function (fFactor) {
        _iSpeedDown = -HERO_DOWN_ACCELLERATION * fFactor;
        _iSpeedAccDown = -HERO_DOWN_ACCELLERATION;
    };

    this.stayMouseDown = function (bVal) {
        _bMouseDown = bVal;
    };

    this.unload = function () {
        _oParentContainer.removeChild(_oCharacter);
        stopSound("step");
        stopSound("jet");
        s_oCharacter = null;
    };

    this.playerIDPlatform = function (iVal) {
        _iIDPlatform = iVal;
    };

    this.getIDPlatform = function () {
        return _iIDPlatform;
    };

    this.resetJump = function () {
        _iSpeedAccDown = HERO_DOWN_ACCELLERATION;
    };

    this.rectMovement = function () {
        _oRectangleMeasure = {x: _oCharacter.x - _iWidth + OFFSET_PLAYER_COL_POINT.x, y: _oCharacter.y - _iHeight + OFFSET_PLAYER_COL_POINT.y,
            w: _iWidth + OFFSET_PLAYER_COL_DIMENSION.x, h: _iHeight + OFFSET_PLAYER_COL_DIMENSION.y};

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

    this.removeTweens = function () {
        createjs.Tween.removeTweens(_oCharacter);
    };

    this.update = function () {
        if (_bMouseDown || _bFlueUp) {
            if (_iSpeedAccDown > MAX_DECELLERATION_VEL) {
                _iSpeedAccDown += DECELLERATION_MOUSE_DOWN;
            } else {
                _iSpeedAccDown = MAX_DECELLERATION_VEL;
            }
        } else {
            if (_iSpeedAccDown < HERO_DOWN_ACCELLERATION) {
                _iSpeedAccDown += DECELLERATION_NORMAL;
            } else {
                _iSpeedAccDown = HERO_DOWN_ACCELLERATION;
            }
        }

        if (!_bCollide) {
            _iSpeedDown += _iSpeedAccDown;
            _oCharacter.y += _iSpeedDown;
        } else {
            _iSpeedDown = 0;
        }

        if (_oCharacter.y < _iLimitY) {
            _oCharacter.y = _iLimitY;
            _iSpeedDown = 0;
        }


        this.rectMovement();
    };

    s_oCharacter = this;
    this._init(iXPos, iYPos, oSprite, oParentContainer);
}

var s_oCharacter;

