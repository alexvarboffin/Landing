function CGame(oData) {
    var _bStartGame;
    var _iScore;
    var _iState;
    var _iTotCoins;
    var _iObtainCoins;
    var _oInterface;
    var _oCharacter;
    var _oLevelSection;
    var _oLevelObject;
    var _oParallaxBg;
    var _oMouseDown = null;
    var _oPressUp = null;
    var _oHitArea;
    var _fAnimSpeedPlatform;
    var _bFromAir = false;
    var _bIncreaseSpeedPlatform = false;

    this._init = function () {

        _bStartGame = false;
        _iScore = 0;
        _iObtainCoins = 0;
        _iTotCoins = 0;

        _iState = GAME_STATE_HELP;

        this.createLevel();

        var oSpriteCharacter = s_oSpriteLibrary.getSprite("player");

        this._createPlayer(oSpriteCharacter, CHARACTER_POSITION.x, CHARACTER_POSITION.y, s_oStage);

        _oHitArea = new createjs.Shape();
        _oHitArea.graphics.beginFill("#0f0f0f").drawRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        _oHitArea.alpha = 0.01;
        s_oStage.addChild(_oHitArea);

        _oInterface = new CInterface();

        _oInterface.refreshScore(_iScore);

        _iState = GAME_STATE_HELP;

        this.activeListeners();

        setVolume("soundtrack", SOUNDTRACK_VOLUME_IN_GAME);
    };

    this.activeListeners = function () {
        if (_oMouseDown === null) {
            _oMouseDown = _oHitArea.on("mousedown", this._onMouseDown);
            _oPressUp = _oHitArea.on("pressup", this._onPressUp);
        }
    };

    this.deactiveListeners = function () {
        _oHitArea.off("mousedown", _oMouseDown);
        _oHitArea.off("pressup", _oPressUp);
        _oMouseDown = null;
        _oPressUp = null;
    };

    this.createParallaxBg = function () {
        _oParallaxBg = new CParallaxBg(0, 0, s_oStage);
        _oParallaxBg.setSpeed(PARALLAX_SPEED);
    };

    this._createPlayer = function (oSprite, iX, iY, oContainer) {
        _oCharacter = new CCharacter(iX, iY, oSprite, oContainer);
        _oCharacter.stopAnimation();
    };

    this.createLevel = function () {
        this.createParallaxBg();

        _oLevelSection = new CLevelSection(0, 0, s_oStage);

        _oLevelSection.createSections(s_aLevelDiagram);
    };

    this._onMouseDown = function () {
        if (!_bStartGame || _iState !== GAME_STATE_PLAY) {
            return;
        }

        if (_oCharacter.getCollisionWithPlatform()) {
            _oCharacter.stopStepSound();
            _oCharacter.changeState("fly");
            _oCharacter.playSoundJet();
        }
        _oCharacter.moveUp(JUMP_MULTIPLY);
        _oCharacter.stayMouseDown(true);
        _oCharacter.collisionWithPlatform(false);


    };

    this.characterDead = function () {
        if (_iState !== GAME_STATE_PLAY) {
            return;
        }
        _oCharacter.stayMouseDown(false);
        _iState = GAME_STATE_GAMEOVER;
        _oLevelSection.setSpeed(0);
        _oParallaxBg.setSpeed(0);
        playSound("die", 0.4, false);
        if (_oCharacter.getCollisionWithPlatform()) {
            _oCharacter.changeState("die");
            _oCharacter.stopStepSound();
            createjs.Tween.get(this).wait(250).call(function () {
                this.gameOver();
            });
        } else {
            _oCharacter.changeState("fly_die");
        }
    };

    this._onPressUp = function () {
        if (!_bStartGame || _iState !== GAME_STATE_PLAY) {
            return;
        }
        _oCharacter.stayMouseDown(false);
    };

    this.unload = function () {
        _bStartGame = false;

        _oInterface.unload();

        _oCharacter.unload();

        this.deactiveListeners();

        s_oStage.removeAllChildren();

        createjs.Tween.removeAllTweens();

    };

    this.onExit = function () {
        this.unload();
        $(s_oMain).trigger("end_level", 1);
        $(s_oMain).trigger("show_interlevel_ad");
        $(s_oMain).trigger("end_session");
        setVolume("soundtrack", 1);

        s_oMain.gotoMenu();
    };

    this._onExitHelp = function () {
        $(s_oMain).trigger("start_level", 1);

        _bStartGame = true;

        _oInterface.unloadHelpPanel();

        _oCharacter.playAnimation();
        s_oGame.startAfterAnim();
    };

    this.startAfterAnim = function () {
        _iState = GAME_STATE_PLAY;
        _oLevelSection.setSpeed(PLATFORM_SPEED);
        _oParallaxBg.setSpeed(PARALLAX_SPEED);
    };

    this.unpause = function (bVal) {
        _bStartGame = bVal;
        if (bVal === true) {
            if (_oCharacter.getCollisionWithPlatform()) {
                _oCharacter.playSoundStep();
            } else {
                _oCharacter.playSoundJet();
            }
            _oCharacter.playAnimation();
        } else {
            _oCharacter.stopAnimation();
            _oCharacter.stopJetSound();
            _oCharacter.stopStepSound();
        }
    };

    this.getScore = function () {
        return _iScore;
    };

    this.getState = function () {
        return _iState;
    };

    this.changeState = function (iState) {
        _iState = iState;
    };

    this.gameOver = function () {
        _iState = GAME_STATE_FINISH;
        _bStartGame = false;

        var iBestScore = getItem("bionic_best_score") !== null ? getItem("bionic_best_score") : 0;

        _oInterface.createEndPanel(_iScore, iBestScore);

        if (_iScore > getItem("bionic_best_score") || getItem("bionic_best_score") === null) {
            saveItem("bionic_best_score", _iScore);
        }

        $(s_oMain).trigger("end_level", 1);
    };

    this.addScore = function (iVal) {
        _iScore += iVal;
        _oInterface.refreshScore(_iScore);
    };

    this.restartLevel = function () {
        _oCharacter.removeTweens();
        _oLevelSection.reset();
        _iScore = 0;
        _iObtainCoins = 0;
        _oInterface.refreshScore(_iScore);
        _oInterface.refreshCoin(_iObtainCoins);
        _oCharacter.setPosition(CHARACTER_POSITION.x, CHARACTER_POSITION.y);
        _oCharacter.playerIDPlatform(0);
        this.startAfterAnim();
        _oCharacter.resetJump();
        _oCharacter.changeState("run");
        _oCharacter.playSoundStep();
        setVolume("soundtrack", SOUNDTRACK_VOLUME_IN_GAME);
        _bStartGame = true;
        $(s_oMain).trigger("restart_level", 1);
    };

    this.unloadLevel = function () {
        _oLevelSection.unload();
        _oLevelSection = null;
        _oParallaxBg.unload();
        _oParallaxBg = null;
    };

    this.collisionPlatform = function (oColInfo) {
        if (oColInfo.collide === false && _oCharacter.getCollisionWithPlatform() === false) {
            _oCharacter.collisionWithPlatform(true);
            _oCharacter.setPosition(_oCharacter.getX(), oColInfo.y + OFFSET_HEIGHT_PLATFORM_CHRACTER);
            if (_iState === GAME_STATE_PLAY) {
                _oCharacter.changeState("run");
                _oCharacter.playSoundStep();
                _oCharacter.stopJetSound();
            } else if (_iState === GAME_STATE_GAMEOVER) {
                _oCharacter.changeState("die");
                _oCharacter.stopJetSound();
                _oCharacter.stopStepSound();
                createjs.Tween.get(this).wait(250).call(function () {
                    this.gameOver();
                });
            }
        }
    };

    this.flueUp = function (bVal) {
        if (bVal)
            _oCharacter.moveUp(FLUE_POWER);
        _oCharacter.setFlue(bVal);
    };


    this.unloadObject = function (iID) {
        _oLevelObject.unloadObject(iID);
    };

    this.addObtainCoin = function () {
        _iObtainCoins++;
        _oInterface.refreshCoin(_iObtainCoins);
    };

    this.checkCollisionPlatform = function (oColInfo) {
        if (oColInfo.collision === COLLISION_PLATFORM) {
            this.collisionPlatform(oColInfo);
        } else if (oColInfo.collision === COLLISION_WALL) {
            this.collisionWall();
        } else if (oColInfo.collision === COLLISION_NOTHING) {
            _oCharacter.collisionWithPlatform(false);
            _oLevelSection.getPlatformByID(oColInfo.id).setCollideYet(false);
        }
    };

    this.increasePlatformSpeedAnim = function (bVal) {
        _fAnimSpeedPlatform = SPEED_ANIMATION;
        _bIncreaseSpeedPlatform = bVal;
    };

    this.update = function () {
        switch (_iState) {
            case GAME_STATE_HELP:

                break;
            case GAME_STATE_PLAY:
                if (_bStartGame) {
                    _oCharacter.update();

                    var oPlayerInfo = {rect: _oCharacter.getRectangle(), id: _oCharacter.getIDPlatform(), pos: {x: _oCharacter.getX(), y: _oCharacter.getY()}};

                    var oColInfo = _oLevelSection.update(oPlayerInfo);

                    _oCharacter.playerIDPlatform(oColInfo.id);

                    this.checkCollisionPlatform(oColInfo);

                    _oParallaxBg.update();
                }
                break;
            case GAME_STATE_FINISH:
                if (_bStartGame) {
                    if (!_bFromAir && _oCharacter.getCollisionWithPlatform() === false) {
                        var oPlayerInfo = {rect: _oCharacter.getRectangle(), id: _oCharacter.getIDPlatform(), pos: {x: _oCharacter.getX(), y: _oCharacter.getY()}};

                        var oColInfo = _oLevelSection.update(oPlayerInfo);

                        _oCharacter.playerIDPlatform(oColInfo.id);

                        if (oColInfo.collision === COLLISION_PLATFORM) {
                            this.collisionPlatform(oColInfo);
                        }
                        _oCharacter.update();
                    }
                }
                break;
            case GAME_STATE_GAMEOVER:
                var oPlayerInfo = {rect: _oCharacter.getRectangle(), id: _oCharacter.getIDPlatform(), pos: {x: _oCharacter.getX(), y: _oCharacter.getY()}};

                var oColInfo = _oLevelSection.update(oPlayerInfo);

                _oCharacter.playerIDPlatform(oColInfo.id);

                if (oColInfo.collision === COLLISION_PLATFORM) {
                    this.collisionPlatform(oColInfo);
                }
                _oCharacter.update();
                break;
            case GAME_STATE_ANIMATION:
                if (_bStartGame) {
                    _oCharacter.update();

                    var oPlayerInfo = {rect: _oCharacter.getRectangle(), id: _oCharacter.getIDPlatform()};

                    var oColInfo = _oLevelSection.update(oPlayerInfo);

                    _oCharacter.playerIDPlatform(oColInfo.id);

                    if (oColInfo.collision === COLLISION_PLATFORM) {
                        this.collisionPlatform(oColInfo);
                    }

                    if (_bIncreaseSpeedPlatform) {
                        _fAnimSpeedPlatform += PLATFORM_SPEED_INCREASE;
                        _oLevelSection.setSpeed(_fAnimSpeedPlatform);
                    }
                }
                break;
        }
    };

    s_oGame = this;

    PLATFORM_SPEED = oData.platform_velocity;
    SCORE_DIFFICULTY = oData.score_difficulty;
    HERO_DOWN_ACCELLERATION = oData.hero_down_speed;
    MAX_DECELLERATION_VEL = oData.max_decelleration_vel;
    DECELLERATION_MOUSE_DOWN = oData.decelleration_mouse_down;
    DECELLERATION_NORMAL = oData.decelleration_normal;
    NUM_LEVEL_FOR_ADS = oData.num_levels_for_ads;
    JUMP_MULTIPLY = oData.jump_multiplier;
    PARALLAX_SPEED = oData.parallax_speed;
    COIN_SCORE = oData.coin_score;
    PERCENTAGE_STARS = oData.percentage_stars;

    this._init();
}

var s_oGame;