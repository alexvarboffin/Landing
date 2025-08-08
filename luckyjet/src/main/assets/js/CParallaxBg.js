function CParallaxBg(iXPos, iYPos, oParentContainer) {
    var _oParentContainer;
    var _oContainer;
    var _aBg;
    var _fSpeed = 0;
    var _iWidthBg;

    this._init = function (iXPos, iYPos, oParentContainer) {

        _aBg = new Array();

        _oParentContainer = oParentContainer;

        _oContainer = new createjs.Container();
        _oContainer.x = iXPos;
        _oContainer.y = iYPos;

        _oParentContainer.addChild(_oContainer);
        _oParentContainer.setChildIndex(_oContainer, 1);

        var oSpriteBg = s_oSpriteLibrary.getSprite("bg_" + 0);
        _iWidthBg = oSpriteBg.width;

        this.addBg();
    };

    this.addBg = function () {
        var iX = 0;
        var iY = 0;
        for (var i = 0; i < BG_TYPES; i++) {
            var oSpriteBg = s_oSpriteLibrary.getSprite("bg_" + i);
            _aBg[i] = new CParallaxeBg(iX, iY, oSpriteBg, _oContainer);
            if (iX > CANVAS_WIDTH + _iWidthBg) {
                _aBg[i].setVisible(false);
            }
            iX += _iWidthBg;
        }
    };

    this.unload = function () {
        _oParentContainer.removeChild(_oContainer);
        _oContainer = null;
    };

    this.unloadObject = function () {
        for (var i = 0; i < _aBg.length; i++) {
            _oContainer.removeChild(_aBg[i]);
        }
    };

    this.setSpeed = function (fValue) {
        _fSpeed = fValue;
    };

    this.update = function () {
        for (var i = 0; i < NUM_PARALLAX_BG; i++) {
            var iNewX = _aBg[i].getX() + _fSpeed;
            var iNewY = _aBg[i].getY();

            _aBg[i].setPosition(iNewX, iNewY);
            if (_aBg[i].getX() < -_iWidthBg) {
                var iNext = i + NUM_PARALLAX_BG - 1;
                var oTemp = _aBg.shift();
                oTemp.setVisible(false);
                _aBg.push(oTemp);
                _aBg[iNext].setVisible(true);
                _aBg[iNext].setPosition(_aBg[iNext - 1].getX() + _iWidthBg, _aBg[iNext - 1].getY());
                _aBg[i].setPosition(_aBg[i].getX() +  _fSpeed, iNewY);
            }
        }
    };

    this._init(iXPos, iYPos, oParentContainer);

    return this;
}
