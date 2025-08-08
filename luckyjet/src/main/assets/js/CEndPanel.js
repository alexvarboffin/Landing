function CEndPanel(oSpriteBg, iScore, iBestScore) {

    var _oGroup;
    var _oGroupMsgBox;
    var _oBg;
    var _oFade;
    var _oButMenu;
    var _oButRestart;
    var _iWidth;

    this._init = function (oSpriteBg, iScore, iBestScore) {

        _oGroup = new createjs.Container();

        _oGroupMsgBox = new createjs.Container();
        _iWidth = oSpriteBg.width;

        _oGroupMsgBox.alpha = 0;

        _oBg = createBitmap(oSpriteBg);

        _oBg.x = CANVAS_WIDTH * 0.5;
        _oBg.y = CANVAS_HEIGHT * 0.5;
        _oBg.regX = oSpriteBg.width * 0.5;
        _oBg.regY = oSpriteBg.height * 0.5;

        _oGroupMsgBox.addChild(_oBg);

        _oFade = new createjs.Shape();
        _oFade.graphics.beginFill("black").drawRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        _oFade.alpha = 0;

        _oFade.on("click", function () {});

        _oGroup.addChild(_oFade);

        var oTextPoint;
        var oTextPointStruct;
        var oTextScore;
        var oTextScoreStruct;
        var oTextBestScore;
        var oTextBestScoreStroke;

        oTextScoreStruct = new CTLText(_oGroupMsgBox, 
                    500, 140, 136, 32, 
                    32, "center", "#000", FONT_GAME, 1,
                    0, 0,
                    TEXT_SCORE,
                    true, true, false,
                    false );
        oTextScoreStruct.setOutline(4);            
        

        oTextScore = new CTLText(_oGroupMsgBox, 
                    500, 140, 136, 32, 
                    32, "center", TEXT_COLOR_1, FONT_GAME, 1,
                    0, 0,
                    TEXT_SCORE,
                    true, true, false,
                    false );

        oTextPointStruct = new CTLText(_oGroupMsgBox, 
                    450, 170, 236, 32, 
                    32, "center", "#000", FONT_GAME, 1,
                    0, 0,
                    iScore,
                    true, true, false,
                    false );
        oTextPointStruct.setOutline(4);   
        

        oTextPoint = new CTLText(_oGroupMsgBox, 
                    500, 170, 136, 32, 
                    32, "center", TEXT_COLOR_1, FONT_GAME, 1,
                    0, 0,
                    iScore,
                    true, true, false,
                    false );


        oTextBestScoreStroke = new CTLText(_oGroupMsgBox, 
                    450, 245, 230, 64, 
                    28, "center", "#000", FONT_GAME, 1,
                    0, 0,
                    TEXT_BEST_SCORE + "\n" + iBestScore,
                    true, true, true,
                    false );
         oTextBestScoreStroke.setOutline(4);           
        

        oTextBestScore = new CTLText(_oGroupMsgBox, 
                    450, 245, 230, 64,  
                    28, "center", TEXT_COLOR_1, FONT_GAME, 1,
                    0, 0,
                    TEXT_BEST_SCORE + "\n" + iBestScore,
                    true, true, true,
                    false );


        _oGroup.addChild(_oGroupMsgBox);
        s_oStage.addChild(_oGroup);

        var oSpriteRestart = s_oSpriteLibrary.getSprite("but_restart");
        var oSpriteHome = s_oSpriteLibrary.getSprite("but_home");
        _oButMenu = new CGfxButton(450, 475, oSpriteHome, _oGroupMsgBox);
        _oButMenu.addEventListener(ON_MOUSE_UP, this._onExit, this);
        _oButRestart = new CGfxButton(665, 475, oSpriteRestart, _oGroupMsgBox);
        _oButRestart.addEventListener(ON_MOUSE_UP, this._onRestart, this);
        _oButRestart.pulseAnimation();

        createjs.Tween.get(_oFade).to({alpha: 0.75}, 750, createjs.Ease.cubicOut);

        createjs.Tween.get(_oGroupMsgBox).to({alpha: 1}, 1000, createjs.Ease.cubicOut).call(function () {
            if (s_iAdsLevel === NUM_LEVEL_FOR_ADS) {
                $(s_oMain).trigger("show_interlevel_ad");
                s_iAdsLevel = 1;
            } else {
                s_iAdsLevel++;
            }
        }
        );
        $(s_oMain).trigger("save_score", iScore);
        $(s_oMain).trigger("share_event", iScore);
    };

    this.unload = function () {
        var oParent = this;
        createjs.Tween.get(_oGroupMsgBox).to({alpha: 0}, 1000, createjs.Ease.cubicIn).call(function () {
            oParent.unloadButton();
        });
    };

    this.unloadButton = function () {
        _oButMenu.unload();
        _oButMenu = null;
        _oButRestart.unload();
        _oButRestart = null;
    };

    this.blockAllBut = function () {
        _oButRestart.block(true);
        _oButMenu.block(true);
    };

    this._onRestart = function () {
        this.blockAllBut();

        this.unload();
        createjs.Tween.get(_oFade).to({alpha: 1}, 1000, createjs.Ease.cubicIn).call(function () {
            s_oGame.restartLevel();
            s_oGame.changeState(GAME_STATE_PLAY);

            createjs.Tween.get(_oFade).to({alpha: 0}, 1000, createjs.Ease.cubicOut).call(function () {
                s_oStage.removeChild(_oGroup);
            });
        });

    };

    this._onExit = function () {
        this.unload();
        this.blockAllBut();

        s_oGame.onExit();
    };

    this._init(oSpriteBg, iScore, iBestScore);

    return this;
}