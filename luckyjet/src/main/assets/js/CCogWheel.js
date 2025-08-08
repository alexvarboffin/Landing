function CCogWheel(iXPos, iYPos, iType, iID, fSpeed, oParentContainer) {

    var _oParentContainer;
    var _oObject;
    var _iWidth;
    var _iHeight;
    var _iID;
    var _iType;
    var _fSpeed = fSpeed;
    var _pLocalPos;

    this._init = function (iXPos, iYPos, iType, iID, oParentContainer) {

        _oParentContainer = oParentContainer;

        _iID = iID;

        _iType = iType;

        var oSprite = s_oSpriteLibrary.getSprite("object_" + iType);

        _oObject = createBitmap(oSprite);

        _iWidth = oSprite.width * 0.5;
        _iHeight = oSprite.height * 0.5;

        _oObject.x = iXPos + _iWidth;
        _oObject.y = iYPos - _iHeight;

        _oObject.regY = _iHeight;
        _oObject.regX = _iWidth;

        _oParentContainer.addChild(_oObject);

    };

    this.setPosition = function (iXPos, iYPos) {
        _oObject.x = iXPos;
        _oObject.y = iYPos;
    };

    this.getX = function () {
        return _oObject.x;
    };
    this.getY = function () {
        return _oObject.y;
    };

    this.getType = function () {
        return _iType;
    };

    this.getWidth = function () {
        return _oObject.regX;
    };

    this.setVisible = function (bVal) {
        _oObject.visible = bVal;
    };

    this.getVisible = function () {
        return _oObject.visible;
    };

    this.unload = function () {
        _oParentContainer.removeChild(_oObject);
    };

    this.getPosLocal = function () {
        return  _pLocalPos = _oParentContainer.localToGlobal(_oObject.x, _oObject.y);
    };

    this.getID = function () {
        return _iID;
    };

    this.setID = function (iVal) {
        _iID = iVal;
    };

    this.controlCollision = function (oPlayerInfo) {
        var oPos = {x: _pLocalPos.x, y: _pLocalPos.y + 80};
        var fDistance = distanceSQRT(oPlayerInfo.pos, oPos);
        if (fDistance <= _iWidth + oPlayerInfo.rect.height * COG_COLLISION_FACTOR) {
            s_oGame.characterDead();
        }
    };

    this.update = function () {
        _oObject.rotation += _fSpeed;
    };

    this._init(iXPos, iYPos, iType, iID, oParentContainer);

    return this;
}





