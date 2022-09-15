package plantity.plantity_android.plantlogs

data class MyPlantData(val name: String, val plantType: String, val since: String, val logData: ArrayList<MyPlantLogData>)

data class MyPlantLogData(
    val date: String,
    val watering: Boolean,
    val repotting: Boolean,
    val sunlight: Boolean,
    val caring: Boolean
    )