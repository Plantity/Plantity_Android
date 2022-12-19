package plantity.plantity_android.plantlogs

data class MyPlants(val isSuccess: Boolean, val code: Int, val message: String, val result: ArrayList<MyPlantInfo>)

data class MyPlantInfo(val myPlantId: Int, val plantName: String, val plantNickName: String, val plantAdaptTime: String, val filePath: String)