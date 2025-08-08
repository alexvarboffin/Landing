function CCoinBoard(iX, iY) {

    var _pStartPosContainer;
    var _oContainer;
    var _oCoin;
    var _oCoinText;
    var _oCoinStroke;
    var _aCoinsEffect;

    this._init = function (iX, iY) {
        _pStartPosContainer = {x: iX, y: iY};

        _oContainer = new createjs.Container();
        _oContainer.x = _pStartPosContainer.x;
        _oContainer.y = _pStartPosContainer.y;

        _oCoinStroke = new createjs.Text("x " + 0, "32px " + FONT_GAME, "#000");
        _oCoinStroke.x = 30;
        _oCoinStroke.textAlign = "left";
        _oCoinStroke.textBaseline = "middle";
        _oCoinStroke.outline = 4;
        _oContainer.addChild(_oCoinStroke);

        _oCoinText = new createjs.Text("x " + 0, "32px " + FONT_GAME, TEXT_COLOR);
        _oCoinText.x = 30;
        _oCoinText.textAlign = "left";
        _oCoinText.textBaseline = "middle";
        _oContainer.addChild(_oCoinText);

        _oCoin = this.createCoin(-15, 0, _oContainer, true);

        s_oStage.addChild(_oContainer);

        _aCoinsEffect = new Array();

        for (var i = 0; i < MAX_COIN_EFFECT_INSTANCE; i++) {
            _aCoinsEffect.push(this.createCoin(-15, 0, s_oStage, false));
        }


    };

    this.animCoinEffect = function (oCoinPos) {
        var i = 0;
        for (i = 0; i < _aCoinsEffect.length; i++) {
            if (!_aCoinsEffect[i].visible) {
                break;
            }
        }
        
        _aCoinsEffect[i].visible = true;
        _aCoinsEffect[i].x = oCoinPos.x;
        _aCoinsEffect[i].y = oCoinPos.y;
        createjs.Tween.get(_aCoinsEffect[i]).to({x: _oCoin.x + _oContainer.x, y: _oCoin.y + _oContainer.y}, MS_ANIM_COIN, createjs.Ease.cubicOut).call(function () {
            _aCoinsEffect[i].visible = false;
        });
    };

    this.getStartPosition = function () {
        return _pStartPosContainer;
    };

    this.setPosition = function (iX, iY) {
        _oContainer.x = iX;
        _oContainer.y = iY;
    };

    this.createCoin = function (iX, iY, oContainer, bVis) {
        var oCoin;
        var oSpriteCoin = s_oSpriteLibrary.getSprite("coin");
        oCoin = createBitmap(oSpriteCoin);
        oCoin.x = iX;
        oCoin.y = iY;
        oCoin.regX = oSpriteCoin.width * 0.5;
        oCoin.regY = oSpriteCoin.height * 0.5;
        oCoin.visible = bVis;

        oContainer.addChild(oCoin);

        return oCoin;
    };

    this.getPos = function () {
        return {x: _oContainer.x, y: _oContainer.y};
    };

    this.unload = function () {
        s_oStage.removeChild(_oContainer);
    };

    this.refresh = function (szText) {
        _oCoinText.text = szText;
        _oCoinStroke.text = szText;
    };

    this._init(iX, iY);

    return this;
}
