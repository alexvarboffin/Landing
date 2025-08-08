function CLevelSection(iXPos, iYPos, oParentContainer) {

    var _oContainerSection;
    var _oParentContainer;
    var _aPlatform;
    var _aSectionEasy;
    var _aSectionMedium;
    var _aSectionHard;
    var _aAllSection;
    var _aChoosenSection;
    var _iSpeed;

    this._init = function (iXPos, iYPos, oParentContainer) {

        _oParentContainer = oParentContainer;

        _aPlatform = new Array();
        _aSectionEasy = new Array();
        _aSectionMedium = new Array();
        _aSectionHard = new Array();
        _aAllSection = new Array();

        _oContainerSection = new createjs.Container();
        _oContainerSection.x = iXPos;
        _oContainerSection.y = iYPos;

        _oParentContainer.addChild(_oContainerSection);
        _oParentContainer.setChildIndex(_oContainerSection, 2);

    };

    this.createSections = function (aLevelDiagram) {
        var iX = 0;
        for (var i = 0; i < aLevelDiagram.length; i++) {
            var oSection = new CSection(iX, 0, aLevelDiagram[i].section, _oContainerSection);

            oSection.addPlatform(aLevelDiagram[i].platform);
            oSection.addObjects(aLevelDiagram[i].objects);
            oSection.setID(i);
            _aAllSection.push(oSection);

            switch (aLevelDiagram[i].difficulty) {
                case EASY:
                    _aSectionEasy.push(oSection);
                    break;
                case MEDIUM:
                    _aSectionMedium.push(oSection);
                    break;
                case HARD:
                    _aSectionHard.push(oSection);
                    break;
            }
            iX += CANVAS_WIDTH;
        }
        this.chooseRandomSection(0, _aSectionEasy[0]);
    };

    this.setSpeed = function (iVal) {
        _iSpeed = iVal;
    };

    this.chooseRandomSection = function (iScore, oActualSection) {
        this.resetAllSection();
        _aChoosenSection = new Array();
        var aShuffleID;

        if (iScore >= SCORE_DIFFICULTY[4]) {
            aShuffleID = this.createShuffleIDArray(_aSectionHard);
        } else if (iScore >= SCORE_DIFFICULTY[3]) {
            aShuffleID = this.createShuffleIDArray(_aSectionHard.concat(_aSectionMedium));
        } else if (iScore >= SCORE_DIFFICULTY[2]) {
            aShuffleID = this.createShuffleIDArray(_aSectionMedium);
        } else if (iScore >= SCORE_DIFFICULTY[1]) {
            aShuffleID = this.createShuffleIDArray(_aSectionEasy.concat(_aSectionMedium));
        } else if (iScore < SCORE_DIFFICULTY[0] || iScore < SCORE_DIFFICULTY[1]) {
            aShuffleID = this.createShuffleIDArray(_aSectionEasy);
        }

        for (var i = 0; i < aShuffleID.length; i++) {
            _aChoosenSection.push(_aAllSection[aShuffleID[i]]);
        }

        var bFlag = false;
        for (var i = 0; i < _aChoosenSection.length; i++) {
            if (oActualSection.getID() === _aChoosenSection[i].getID()) {
                var oTempSection = _aChoosenSection[0];
                _aChoosenSection[0] = oActualSection;
                _aChoosenSection[i] = oTempSection;
                bFlag = true;
            }
        }

        if (bFlag === false) {
            _aChoosenSection.unshift(oActualSection);
        }

        var iX = CANVAS_WIDTH;
        _aChoosenSection[0].setID(_aChoosenSection.length - 1);
        for (var i = 1; i < _aChoosenSection.length; i++) {
            _aChoosenSection[i].setID(i - 1);
            _aChoosenSection[i].resetCoinVisibility();
            _aChoosenSection[i].setPosition(_aChoosenSection[0].getX() + iX, _aChoosenSection[i].getY());

            if (_aChoosenSection[i].getX() > CANVAS_WIDTH) {
                _aChoosenSection[i].setVisible(false);
            } else {
                _aChoosenSection[i].setVisible(true);
            }
            iX += CANVAS_WIDTH;
        }
    };

    this.resetAllSection = function () {
        for (var i = 0; i < _aAllSection.length; i++) {
            _aAllSection[i].setID(i);
        }
    };


    this.createShuffleIDArray = function (aSections) {
        var aShuffle = new Array();
        for (var i = 0; i < aSections.length; i++) {
            aShuffle.push(aSections[i].getID());
        }

        return  shuffle(aShuffle);
    };

    this.reset = function () {
        this.disableChoosenSection();
        this.resetAllSection();
        _aSectionEasy[0].setPosition(0, _aSectionEasy[0].getY());
        _aSectionEasy[0].setVisible(true);
        this.chooseRandomSection(0, _aSectionEasy[0]);
    };

    this.disableChoosenSection = function () {
        for (var i = 0; i < _aChoosenSection.length; i++) {
            _aChoosenSection[i].setVisible(false);
        }
    };

    this.setSpeed = function (fValue) {
        _iSpeed = fValue;
    };

    this.setPosition = function (iXPos, iYPos) {
        _oContainerSection.x = iXPos;
        _oContainerSection.y = iYPos;
    };

    this.unload = function () {
        _oParentContainer.removeChild(_oContainerSection);
        _oContainerSection = null;
        _aPlatform = null;
    };

    this.getPlatformByID = function (iVal) {
        return _aChoosenSection[0].getPlatformByID(iVal);
    };

    this.manageSections = function (oPlayerInfo) {
        for (var i = 0; i < 2; i++) {
            _aChoosenSection[i].update(_iSpeed, oPlayerInfo);
            if (_aChoosenSection[i].getX() < -CANVAS_WIDTH) {
                var oTemp = _aChoosenSection.shift();
                oTemp.setVisible(false);
                oTemp.setPosition(CANVAS_WIDTH, oTemp.getY());
                _aChoosenSection.push(oTemp);
                _aChoosenSection[i].update(_iSpeed, oPlayerInfo);
                _aChoosenSection[i + 1].setVisible(true);
                _aChoosenSection[i + 1].setPosition(_aChoosenSection[i].getX() + CANVAS_WIDTH, _aChoosenSection[i].getY());
                if (_aChoosenSection[1].getID() === _aChoosenSection.length - 1) {
                    this.randomizeSection(oPlayerInfo);
                }
            }
        }
    };

    this.randomizeSection = function (oPlayerInfo) {
        this.chooseRandomSection(s_oGame.getScore(), _aChoosenSection[0]);
        _aChoosenSection[0].update(_iSpeed, oPlayerInfo);
        _aChoosenSection[1].setPosition(_aChoosenSection[0].getX() + CANVAS_WIDTH, _aChoosenSection[1].getY());
        _aChoosenSection[1].setVisible(true);
    };

    this.update = function (oPlayerInfo) {
        this.manageSections(oPlayerInfo);

        return _aChoosenSection[0].collisionPlatforms(oPlayerInfo);
    };

    this._init(iXPos, iYPos, oParentContainer);

    return this;
}




