package life.lordseriala.android.activity

import com.walhalla.landing.activity.CinemaWebActivity
import com.walhalla.landing.config.Cfg
import life.lordseriala.android.Cfg9


class MainActivity(
    override var url: String= Cfg9.url,
    override var cfg: Cfg = Cfg9.cfg
) : CinemaWebActivity()
