package plantity.plantity_android.search

data class SearchData(val isSuccess: Boolean, val statusCode: Int, val statusMessage: String, val result: SearchResult)

data class SearchResult(val content: ArrayList<Content>)

data class Content(
    val plantId: Int,
    val cntntsNo: String,  // 고유 식물 번호
    val cntntsSj: String,  // 식물 명
    val plntbneNm: String,  // 학명
    val plntzrNm: String,  // 식물 영문명
    val adviseInfo: String,  // 식물 설명,
    val orgplceInfo: String,  // 원산지
    val lightdemanddoCodeNm: String,  // 광도
    val ignSeasonCodeNm: String,  // 개화 시기
    val flclrCodeNm: String,  // 꽃 색
    val watercycleSprngCodeNm: String,  // 물 주기
    val managelevelCode: String,  // 관리 수준 코드 - 난이도
    // val plantFollowings: ArrayList<Int>  -> 서버 아직 구현 안된듯?
)
