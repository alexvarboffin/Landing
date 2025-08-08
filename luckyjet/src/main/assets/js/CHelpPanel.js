function CHelpPanel(iXPos, iYPos, oSprite) {
    var _oHelpBg;
    var _oGroup;
    var _oFade;
    var _oGroup;
    var _oButContinue;
    var _bClick = false;

    this._init = function (iXPos, iYPos, oSprite) {
        _oHelpBg = createBitmap(oSprite);
        _oHelpBg.x = CANVAS_WIDTH * 0.5;
        _oHelpBg.y = CANVAS_HEIGHT * 0.5;
        _oHelpBg.regX = oSprite.width * 0.5;
        _oHelpBg.regY = oSprite.height * 0.5;

        _oGroup = new createjs.Container();
        _oGroup.x = iXPos;
        _oGroup.y = iYPos;

        _oFade = new createjs.Shape();
        _oFade.graphics.beginFill("black").drawRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        _oFade.alpha = 0.5;

        _oGroup.addChild(_oFade);

        _oGroup.addChild(_oHelpBg);

        s_oStage.addChild(_oGroup);

        this.page1();

        _oGroup.on("pressup", function () {
            s_oHelpPanel._onExitHelp();
        }, null, true);

        if (!s_bMobile)
            _oGroup.cursor = "pointer";
    };

    this.page1 = function () {
        var oPage1Cont = new createjs.Container();
        oPage1Cont.y = -20;
        _oGroup.addChild(oPage1Cont);
        var iSizeText = 30;

        var oInputTextStroke = new CTLText(oPage1Cont, 
                    CANVAS_WIDTH/2-40, 250, 150, iSizeText, 
                    iSizeText, "left", "#000", FONT_GAME, 1,
                    0, 0,
                    TEXT_HELP_INPUT,
                    true, true, false,
                    false );
                    
        oInputTextStroke.setOutline(3);

        var oInputText = new CTLText(oPage1Cont, 
                    CANVAS_WIDTH/2-40, 250, 150, iSizeText, 
                    iSizeText, "left", TEXT_COLOR, FONT_GAME, 1,
                    0, 0,
                    TEXT_HELP_INPUT,
                    true, true, false,
                    false );

        var oSpriteCharacter = s_oSpriteLibrary.getSprite("player");

        var oCharacter = new CCharacter(oInputText.getX() - 40, oInputTextStroke.getY() + 40, oSpriteCharacter, _oGroup);
        oCharacter.changeState("fly");
        oCharacter.setScale(0.8);

        oCharacter.changeState("start_jump_help");

        var oSpriteInput;
        if (s_bMobile) {
            oSpriteInput = s_oSpriteLibrary.getSprite("help_touch");
        } else {
            oSpriteInput = s_oSpriteLibrary.getSprite("help_mouse");
        }

        var oInput = createBitmap(oSpriteInput);

        oInput.x = oInputTextStroke.getX() + 200;
        oInput.y = oInputTextStroke.getY()+20;
        oInput.regX = oSpriteInput.width * 0.5;
        oInput.regY = oSpriteInput.height * 0.5;
        oInput.scaleX = oInput.scaleY = 0.7;

        oPage1Cont.addChild(oInput);

        var oText1Struct = new CTLText(oPage1Cont, 
                    CANVAS_WIDTH/2-40, CANVAS_HEIGHT * 0.5 + 10, 200, iSizeText, 
                    iSizeText, "left", "#000", FONT_GAME, 1,
                    0, 0,
                    TEXT_HELP_COIN,
                    true, true, false,
                    false );


        var oText1 = new CTLText(oPage1Cont, 
                    CANVAS_WIDTH/2-40, CANVAS_HEIGHT * 0.5 + 10, 200, iSizeText, 
                    iSizeText, "left", TEXT_COLOR, FONT_GAME, 1,
                    0, 0,
                    TEXT_HELP_COIN,
                    true, true, false,
                    false );

        var oSpriteCoin = s_oSpriteLibrary.getSprite("object_8");

        var iDivH = 2;
        var iDivW = 7;

        var oData = {
            images: [oSpriteCoin],
            // width, height & registration point of each sprite
            frames: {width: oSpriteCoin.width / iDivW, height: oSpriteCoin.height / iDivH, regX: (oSpriteCoin.width / 2) / iDivW, regY: (oSpriteCoin.height / 2) / iDivH},
            animations: {
                normal: [0, 13, "normal", 0.5]
            }
        };
        var oSpriteSheet = new createjs.SpriteSheet(oData);
        var oCoin = createSprite(oSpriteSheet, "normal", (oSpriteCoin.width / 2) / iDivW, (oSpriteCoin.height / 2) / iDivH, oSpriteCoin.width / iDivW, oSpriteCoin.height / iDivH);

        oCoin.x = CANVAS_WIDTH * 0.5 - 100;
        oCoin.y = oText1Struct.getY()+10;

        oPage1Cont.addChild(oCoin);

        var oText2Struct = new CTLText(oPage1Cont, 
                    CANVAS_WIDTH/2-40, CANVAS_HEIGHT * 0.5 + 85, 200, iSizeText, 
                    iSizeText, "left", "#000", FONT_GAME, 1,
                    0, 0,
                    TEXT_HELP_SPIKE,
                    true, true, false,
                    false );


        var oText2 = new CTLText(oPage1Cont, 
                    CANVAS_WIDTH/2-40, CANVAS_HEIGHT * 0.5 + 85, 200, iSizeText, 
                    iSizeText, "left", TEXT_COLOR, FONT_GAME, 1,
                    0, 0,
                    TEXT_HELP_SPIKE,
                    true, true, false,
                    false );


        var oSpriteSpike = s_oSpriteLibrary.getSprite("object_6");

        var oCog = createBitmap(oSpriteSpike);
        oCog.x = CANVAS_WIDTH * 0.5 - 100;
        oCog.y = oText2Struct.getY()+10;
        oCog.regX = oSpriteSpike.width / 2;
        oCog.regY = oSpriteSpike.height / 2;
        oCog.scaleX = oCog.scaleY = 0.6;

        oPage1Cont.addChild(oCog);

        this.animCog(oCog);

        createjs.Tween.get(_oGroup).to({alpha: 1}, 300, createjs.Ease.cubicOut);

        var oSpriteContinue = s_oSpriteLibrary.getSprite("but_continue");

        _oButContinue = new CGfxButton(CANVAS_WIDTH * 0.5 , CANVAS_HEIGHT * 0.5 + 180, oSpriteContinue, oPage1Cont);
        _oButContinue.addEventListener(ON_MOUSE_UP, this._onExitHelp, this);
        _oButContinue.pulseAnimation();

        s_oStage.addChild(_oGroup);
    };

    this.animCog = function (oCog) {
        createjs.Tween.get(oCog, {loop: true}).to({rotation: 360}, 1000).call(function () {
            oCog.rotation = 0;
        });
    };

    this.unload = function () {
        s_oStage.removeChild(_oGroup);
        s_oHelpPanel = null;
        _oGroup.off("click", function () {});
        _oButContinue.unload();
        _oButContinue = null;
    };

    this._onExitHelp = function () {
        if (_bClick) {
            return;
        }
        _bClick = true;

        createjs.Tween.get(_oGroup).to({alpha: 0}, 500).call(function () {
            s_oGame._onExitHelp();
        });

    };

    s_oHelpPanel = this;

    this._init(iXPos, iYPos, oSprite);

}

var s_oHelpPanel = null;