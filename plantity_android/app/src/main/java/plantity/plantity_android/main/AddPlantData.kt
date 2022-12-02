package plantity.plantity_android.main

import java.io.File

data class AddPlantData(val image: File, val plantName: String, val plantNickName: String, val plantAdaptTime: String, val cntntsNo: String)

data class AddPlantResponse(val isSuccess: Boolean, val code: Int, val message: String)
