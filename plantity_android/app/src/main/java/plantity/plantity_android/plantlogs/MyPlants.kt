package plantity.plantity_android.plantlogs

data class MyPlants(val isSuccess: Boolean, val code: Int, val message: String, val result: ArrayList<MyPlantInfo>)

data class MyPlantInfo(val myPlantId: Int, val plantName: String, val plantNickName: String, val plantAdaptTime: String, val filePath: String)

data class MyPlantLog(val success: Boolean, val msg: String, val timestamp: String, val data: MyPlantLogDetail)

data class MyPlantLogDetail(val plantId: Int, val date: String, val water: Boolean, val look: Boolean, val sun: Boolean, val repot: Boolean)