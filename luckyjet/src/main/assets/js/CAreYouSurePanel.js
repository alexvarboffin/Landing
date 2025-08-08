function CAreYouSurePanel(oParentContainer) {
    var _oMsgStroke;
    var _oMsg;
    var _oButYes;
    var _oButNo;
    var _oContainer;
    var _oParentContainer;

    this._init = function () {
        _oContainer = new createjs.Container();
        _oContainer.visible = false;
        _oParentContainer.addChild(_oContainer);

        var oFade;

        oFade = new createjs.Shape();
        oFade.graphics.beginFill("black").drawRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        oFade.alpha = 0.5;

        oFade.on("click", function () {});

        _oContainer.addChild(oFade);

        var oSpriteBg = s_oSpriteLibrary.getSprite('msg_box');
        var oBg = createBitmap(oSpriteBg);

        oBg.x = CANVAS_WIDTH * 0.5;
        oBg.y = CANVAS_HEIGHT * 0.5;
        oBg.regX = oSpriteBg.width * 0.5;
        oBg.regY = oSpriteBg.height * 0.5;

        _oContainer.addChild(oBg);

        _oMsgStroke = new CTLText(_oContainer, 
                    CANVAS_WIDTH/2-140, 220, 320, 100, 
                    40, "center", "#000", FONT_GAME, 1,
                    0, 0,
                    TEXT_ARE_SURE,
                    true, true, true,
                    false );
                    
        _oMsgStroke.setOutline(5);

        _oMsg =  new CTLText(_oContainer, 
                    CANVAS_WIDTH/2-140, 220, 320, 100, 
                    40, "center", "#fff", FONT_GAME, 1,
                    0, 0,
                    TEXT_ARE_SURE,
                    true, true, true,
                    false );

        _oButYes = new CGfxButton(CANVAS_WIDTH / 2 + 145, 400, s_oSpriteLibrary.getSprite('but_yes'), _oContainer);
        _oButYes.addEventListener(ON_MOUSE_UP, this._onButYes, this);

        _oButNo = new CGfxButton(CANVAS_WIDTH / 2 - 120, 400, s_oSpriteLibrary.getSprite('but_exit_big'), _oContainer);
        _oButNo.addEventListener(ON_MOUSE_UP, this._onButNo, this);
    };

    this.show = function () {
        s_oGame.unpause(false);
        _oContainer.visible = true;
    };

    this._onButYes = function () {
        s_oGame.unpause(true);
        s_oGame.onExit();
    };

    this._onButNo = function () {
        s_oGame.unpause(true);
        _oContainer.visible = false;
    };

    this.unload = function () {
        _oButYes.unload();
        _oButNo.unload();
    };

    _oParentContainer = oParentContainer;

    this._init();
}