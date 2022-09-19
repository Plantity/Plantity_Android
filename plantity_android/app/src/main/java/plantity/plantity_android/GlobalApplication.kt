package plantity.plantity_android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "00e76c39754ce94d507a356e3f330371")
    }
}