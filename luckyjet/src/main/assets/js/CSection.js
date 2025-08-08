function CSection(iXPos, iYPos, iStartID, oParentContainer) {

    var _oParentContainer;
    var _oContainer;
    var _aObject;
    var _aPlatform;
    var _iID;
    var _iStartID = iStartID;
    var _bUsed = false;

    this._init = function (iXPos, iYPos, oParentContainer) {
        _oContainer = new createjs.Container();
        _oParentContainer = oParentContainer;

        this.setPosition(iXPos, iYPos);

        _aObject = new Array();
        _aPlatform = new Array();

        _oParentContainer.addChild(_oContainer);
    };

    this.addObjects = function (oObjectDiagram) {
        for (var i = 0; i < oObjectDiagram.length; i++) {
            switch (oObjectDiagram[i].gid) {
                case GID_OBJECTS[0]:
                    _aObject.push(new CCoin(oObjectDiagram[i].x, oObjectDiagram[i].y + 50, oObjectDiagram[i].gid, i, _oContainer, this));
                    break;
                case GID_OBJECTS[1]:
                case GID_OBJECTS[2]:
                case GID_OBJECTS[3]:
                case GID_OBJECTS[4]:
                case GID_OBJECTS[5]:
                case GID_OBJECTS[6]:
                case GID_OBJECTS[7]:
                    _aObject.push(new CCogWheel(oObjectDiagram[i].x, oObjectDiagram[i].y + 60, oObjectDiagram[i].gid, i, oObjectDiagram[i].vel_rotation, _oContainer));
                    break;
                case GID_OBJECTS[8]:
                    _aObject.push(new CFlue(oObjectDiagram[i].x, oObjectDiagram[i].y, oObjectDiagram[i].gid, i, _oContainer));
                    break;
            }
            var oPosLocal = _aObject[i].getPosLocal();
            var fPosWidth = oPosLocal.x + _aObject[i].getWidth();

            this.checkObjectVisibility(_aObject[i], fPosWidth, oPosLocal.x);
        }
    };

    this.addPlatform = function (aPlatformDiagram) {
        var iRow = aPlatformDiagram.length;
        var iCol = aPlatformDiagram[0].length;
        var iX = FIRST_PLATFORM_X;
        var iY = PLATFORM_Y_START;

        var iID = 0;
        for (var j = 0; j < iCol; j++) {
            iY = PLATFORM_Y_START;
            for (var i = 0; i < iRow; i++) {
                iY += 80;
                if (aPlatformDiagram[i][j] !== -1) {
                    _aPlatform[iID] = new CPlatform(iX, iY, iID, aPlatformDiagram[i][j], _oContainer);
                    iID++;
                }
            }
            iX += 80;
        }
    };

    this.setID = function (iVal) {
        _iID = iVal;
    };

    this.getID = function () {
        return _iID;
    };

    this.isUsed = function () {
        return _bUsed;
    };

    this.getStartID = function () {
        return _iStartID;
    };

    this.setUsed = function (bVal) {
        _bUsed = bVal;
    };

    this.setPosition = function (iXPos, iYPos) {
        _oContainer.x = iXPos;
        _oContainer.y = iYPos;
    };

    this.getX = function () {
        return _oContainer.x;
    };
    this.getY = function () {
        return _oContainer.y;
    };

    this.setVisible = function (bVal) {
        _oContainer.visible = bVal;
    };

    this.unload = function () {
        _oParentContainer.removeChild(_oContainer);
    };

    this.getPlatformByID = function (iVal) {
        return _aPlatform[iVal];
    };

    this.collisionPlatforms = function (oPlayerInfo) {
        var iID = oPlayerInfo.id;
        var oPlayerRect = oPlayerInfo.rect;
        var oCollision = _aPlatform[iID].controlCollision(oPlayerRect);

        if (oCollision.collision === COLLISION_NOTHING) {
            for (var i = oPlayerInfo.id + 1; i < oPlayerInfo.id + PREVIEW_COLLISION; i++) {
                if (i > _aPlatform.length - 1) {

                } else {
                    var oCollisionNext = _aPlatform[i].controlCollision(oPlayerRect);
                    if (oCollisionNext.collision === COLLISION_PLATFORM || oCollisionNext.collision === COLLISION_WALL) {
                        oCollisionNext.collide = oCollision.collide;
                        return oCollisionNext;
                    }
                }
            }
        }
        return oCollision;
    };

    this.checkObjectVisibility = function (oObject, fPosWidth, iXPos) {
        var fPosNegWidth = iXPos - oObject.getWidth();

        if (fPosNegWidth < CANVAS_WIDTH && fPosWidth > 0) {
            oObject.setVisible(true);
        } else {
            oObject.setVisible(false);
        }
    };

    this.manageObject = function (oPlayerInfo) {
        for (var i = 0; i < _aObject.length; i++) {
            _aObject[i].setPosition(_aObject[i].getX(), _aObject[i].getY());
            var oPosLocal = _aObject[i].getPosLocal();
            var fPosWidth = oPosLocal.x + _aObject[i].getWidth();

            this.checkObjectVisibility(_aObject[i], fPosWidth, oPosLocal.x);

            if (fPosWidth > 0 && _aObject[i].getVisible()) {
                _aObject[i].update();
                _aObject[i].controlCollision(oPlayerInfo);
            }
        }
    };

    this.resetCoinVisibility = function () {
        for (var i = 0; i < _aObject.length; i++) {
            if (_aObject[i].getType() === GID_OBJECTS[0]) {
                _aObject[i].setToken(false);
            }
        }
    };

    this.managePlatform = function () {
        for (var i = 0; i < _aPlatform.length; i++) {
            _aPlatform[i].setPosition(_aPlatform[i].getX(), _aPlatform[i].getY());
        }
    };

    this.update = function (iSpeed, oPlayerInfo) {
        _oContainer.x += iSpeed;

        this.managePlatform();

        this.manageObject(oPlayerInfo);

    };

    this._init(iXPos, iYPos, oParentContainer);

    return this;
}





