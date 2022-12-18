package plantity.plantity_android.plantlogs

data class MyPlantDetailResponse(val isSuccess: Boolean, val code: Int, val message: String, val result: MyPlantDetail)

data class MyPlantDetail(
    val myPlantId: Int,
    val plantNickName: String,
    val plantName: String,
    val adaptDate: String,
    val waterCycle: String, // "흙을 촉촉하게 유지함(물에 잠기지 않도록 주의)"
    val filePath: String  //"https://plantity-bucket.s3.ap-northeast-2.amazonaws.com/postImg/node.png")
)