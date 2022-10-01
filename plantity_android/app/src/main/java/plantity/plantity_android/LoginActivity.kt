package plantity.plantity_android

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import plantity.plantity_android.databinding.ActivityLoginBinding
import plantity.plantity_android.main.MainActivity


class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            Log.d("test", "key hash: ${Utility.getKeyHash(this)}")

//            여러 명이 하나의 프로젝트를 개발하는 경우 모든 사람들이 각자의 디버그 키 해시 값을 구해서 다 등록해야 함.
//            배포 시에는 디버그 키가 아닌 릴리즈 키 해시 값을 등록해야 함
//            KakaoSdk.init(this, "00e76c39754ce94d507a356e3f330371")  // native app key
//            Log.d("test", "after Kakao Sdk init")

            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                Log.d("test", "callback")
                if (error != null) {
                    Log.d("test", "카카오계정으로 로그인 실패", error)
                }
                else if (token != null) {
                    Log.d("test", "카카오계정으로 로그인 성공 ${token.accessToken}")
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
//                    intent.addFlags(FLAG_ACTIVITY_NO_ANIMATION)  // -> 수정 필요!!!

                    startActivity(intent)
                    overridePendingTransition(0,0)

                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.d("test", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.d("test", "카카오톡으로 로그인 성공 ${token.accessToken}")

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = FLAG_ACTIVITY_CLEAR_TASK
                        intent.addFlags(FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        // 토큰 만료 시 갱신 코드 필요?
//        if (AuthApiClient.instance.hasToken()) {
//            Log.d("test", "토큰 만료")
//            UserApiClient.instance.accessTokenInfo { _, error ->
//                if (error != null) {
//                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
//                        //로그인 필요
//                    }
//                    else {
//                        //기타 에러
//                    }
//                }
//                else {
//                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
//                }
//            }
//        }
//        else {
//            //로그인 필요
//        }
    }
    // 사용자 정보 서버로 전송 (함수 내용은 수정)
//    fun saveKakaoUser(token:String){
//        val user = User(
//            0,
//            1,
//            token,
//            "myNickName",
//            ""
//        )
//        viewModel.apply {
//            _dataLoading.postValue(true)
//            kakaoCallBack(user)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    _dataLoading.postValue(false)
//                    user.id = it
//                    userRepository.setUser(user)
//                    val intent = Intent(applicationContext, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                },{
//                    it.printStackTrace()
//                })
//        }
//    }
}