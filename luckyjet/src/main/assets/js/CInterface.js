function CInterface() {
    var _pStartPosAudio;
    var _pStartPosExit;
    var _pStartPosPause;
    var _pStartPosScore;
    var _pStartPosFullscreen;
    
    var _oButExit;
    var _oButPause;
    var _oHelpPanel;
    var _oAudioToggle;
    var _oEndPanel = null;
    var _oPause;
    var _oScoreTextContainer;
    var _oScoreText;
    var _oScoreTextStroke;
    var _oTextScore;
    var _oTextScoreStroke;
    var _oAreYouSure;
    var _oCoinBoard;
    var _oRollingScore;
    var _oButFullscreen;
    var _fRequestFullScreen = null;
    var _fCancelFullScreen = null;

    this._init = function () {

        var oSprite = s_oSpriteLibrary.getSprite('but_exit');
        _pStartPosExit = {x: CANVAS_WIDTH - (oSprite.height / 2) - 10, y: (oSprite.height / 2) + 10};
        _oButExit = new CGfxButton(_pStartPosExit.x, _pStartPosExit.y, oSprite, s_oStage);
        _oButExit.addEventListener(ON_MOUSE_UP, this._onExit, this);

        var oSprite = s_oSpriteLibrary.getSprite('but_pause');
        _pStartPosPause = {x: _pStartPosExit.x - oSprite.height - 10, y: _pStartPosExit.y};
        _oButPause = new CGfxButton(_pStartPosPause.x, _pStartPosPause.y, oSprite, s_oStage);
        _oButPause.addEventListener(ON_MOUSE_UP, this._onPause, this);

        if (DISABLE_SOUND_MOBILE === false || s_bMobile === false) {
            var oSprite = s_oSpriteLibrary.getSprite('audio_icon');
            _pStartPosAudio = {x: _pStartPosPause.x - oSprite.height - 10, y: _pStartPosExit.y};
            _oAudioToggle = new CToggle(_pStartPosAudio.x, _pStartPosAudio.y, oSprite, s_bAudioActive, s_oStage);
            _oAudioToggle.addEventListener(ON_MOUSE_UP, this._onAudioToggle, this);
            
            oSprite = s_oSpriteLibrary.getSprite('but_fullscreen');
            _pStartPosFullscreen = {x:_pStartPosAudio.x - oSprite.width/2 - 10,y:_pStartPosAudio.y};
        }else{
            oSprite = s_oSpriteLibrary.getSprite('but_fullscreen');
            _pStartPosFullscreen = {x: _pStartPosPause.x - oSprite.height - 10, y: _pStartPosExit.y};
        }
        
        var doc = window.document;
        var docEl = doc.documentElement;
        _fRequestFullScreen = docEl.requestFullscreen || docEl.mozRequestFullScreen || docEl.webkitRequestFullScreen || docEl.msRequestFullscreen;
        _fCancelFullScreen = doc.exitFullscreen || doc.mozCancelFullScreen || doc.webkitExitFullscreen || doc.msExitFullscreen;
        
        if(ENABLE_FULLSCREEN === false){
            _fRequestFullScreen = false;
        }
        
        if (_fRequestFullScreen && screenfull.isEnabled){
            _oButFullscreen = new CToggle(_pStartPosFullscreen.x,_pStartPosFullscreen.y,oSprite,s_bFullscreen,s_oStage);
            _oButFullscreen.addEventListener(ON_MOUSE_UP, this._onFullscreenRelease, this);
        }
        
        _pStartPosScore = {x: CANVAS_WIDTH / 2 - 220, y: 23};

        _oScoreTextContainer = new createjs.Container();
        _oScoreTextContainer.x = _pStartPosScore.x;
        _oScoreTextContainer.y = _pStartPosScore.y;

        _oScoreTextStroke = new CTLText(_oScoreTextContainer, 
                    160, 0, 120, 36, 
                    36, "left", "#000", FONT_GAME, 1,
                    0, 0,
                    "0",
                    true, true, false,
                    false );
        _oScoreTextStroke.setOutline(4);

        _oScoreText = new CTLText(_oScoreTextContainer, 
                    160, 0, 120, 36, 
                    36, "left", TEXT_COLOR, FONT_GAME, 1,
                    0, 0,
                    "0",
                    true, true, false,
                    false );

        _oTextScoreStroke = new CTLText(_oScoreTextContainer, 
                    0, 0, 150, 36, 
                    36, "left", "#000", FONT_GAME, 1,
                    0, 0,
                    TEXT_SCORE,
                    true, true, false,
                    false );
                    
        _oTextScoreStroke.setOutline(4);


        _oTextScore =new CTLText(_oScoreTextContainer, 
                    0, 0, 150, 36, 
                    36, "left", TEXT_COLOR, FONT_GAME, 1,
                    0, 0,
                    TEXT_SCORE,
                    true, true, false,
                    false );

        _oRollingScore = new CRollingScore();

        _oCoinBoard = new CCoinBoard(60, 40);

        _oAreYouSure = new CAreYouSurePanel(s_oStage);

        s_oStage.addChild(_oScoreTextContainer);

        _oHelpPanel = new CHelpPanel(0, 0, s_oSpriteLibrary.getSprite('msg_box'));

        this.refreshButtonPos(s_iOffsetX, s_iOffsetY);
    };

    this.refreshButtonPos = function (iNewX, iNewY) {
        _oButExit.setPosition(_pStartPosExit.x - iNewX, iNewY + _pStartPosExit.y);
        _oButPause.setPosition(_pStartPosPause.x - iNewX, iNewY + _pStartPosPause.y);
        if (DISABLE_SOUND_MOBILE === false || s_bMobile === false) {
            _oAudioToggle.setPosition(_pStartPosAudio.x - iNewX, iNewY + _pStartPosAudio.y);
        }
        if (_fRequestFullScreen && screenfull.isEnabled){
            _oButFullscreen.setPosition(_pStartPosFullscreen.x - s_iOffsetX,_pStartPosFullscreen.y + s_iOffsetY);
        }
        _oScoreTextContainer.y = _pStartPosScore.y + iNewY;

        var oPosCoinBoard = _oCoinBoard.getStartPosition();
        _oCoinBoard.setPosition(oPosCoinBoard.x + iNewX, oPosCoinBoard.y + iNewY);
    };

    this.unload = function () {
        _oButExit.unload();
        _oButExit = null;
        _oButPause.unload();
        _oButPause = null;

        if (_oHelpPanel !== null) {
            _oHelpPanel.unload();
        }

        if (_oAreYouSure) {
            _oAreYouSure.unload();
        }

        if (DISABLE_SOUND_MOBILE === false || s_bMobile === false) {
            _oAudioToggle.unload();
            _oAudioToggle = null;
        }
        
        if (_fRequestFullScreen && screenfull.isEnabled){
            _oButFullscreen.unload();
        }

        s_oInterface = null;
    };

    this.createEndPanel = function (iScore, iBestScore) {
        _oEndPanel = new CEndPanel(s_oSpriteLibrary.getSprite("end_panel"), iScore, iBestScore);
    };

    this.unloadHelpPanel = function () {
        if (_oHelpPanel !== null) {
            _oHelpPanel.unload();
            _oHelpPanel = null;
        }
    };

    this.createPauseInterface = function () {
        _oPause = new CPause();
    };

    this.unloadPause = function () {
        _oPause.unload();
        _oPause = null;
    };

    this._onAudioToggle = function () {
        Howler.mute(s_bAudioActive);
        s_bAudioActive = !s_bAudioActive;
    };

    this._onExit = function () {
        _oAreYouSure.show();
    };

    this.refreshCoin = function (iCoin) {
        _oCoinBoard.refresh("x " + iCoin);
    };

    this.coinEffect = function (oCoinPos) {
        _oCoinBoard.animCoinEffect(oCoinPos);
    };

    this.refreshScore = function (iScore) {
        _oRollingScore.rolling(_oScoreText.getText(), _oScoreTextStroke.getText(), iScore);
    };

    this._onPause = function () {
        s_oGame.unpause(false);
        this.createPauseInterface();
        createjs.Ticker.paused = true;
    };
    
    this.resetFullscreenBut = function(){
	if (_fRequestFullScreen && screenfull.isEnabled){
		_oButFullscreen.setActive(s_bFullscreen);
	}
    };

    
    this._onFullscreenRelease = function(){
	if(s_bFullscreen) { 
		_fCancelFullScreen.call(window.document);
	}else{
		_fRequestFullScreen.call(window.document.documentElement);
	}
	
	sizeHandler();
};

    
    s_oInterface = this;

    this._init();

    return this;
}

var s_oInterface = null;