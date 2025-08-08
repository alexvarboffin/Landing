function CParallaxeBg(iXPos, iYPos, oSprite, oParentContainer) {
    var _oBg;
    var _oParentContainer;

    this._init = function (iXPos, iYPos, oSprite, oParentContainer) {

        _oParentContainer = oParentContainer;

        _oBg = createBitmap(oSprite);

        this.setPosition(iXPos, iYPos);

        _oParentContainer.addChild(_oBg);

    };

    this.setPosition = function (iX, iY) {
        _oBg.x = iX;
        _oBg.y = iY;
    };

    this.getX = function () {
        return _oBg.x;
    };

    this.getY = function () {
        return _oBg.y;
    };

    this.getVisible = function () {
        return _oBg.visible;
    };

    this.setVisible = function (bVal) {
        _oBg.visible = bVal;
    };

    this.unload = function () {
        _oParentContainer.removeChild(_oBg);
        _oBg = null;
    };

    this._init(iXPos, iYPos, oSprite, oParentContainer);

    return this;
}