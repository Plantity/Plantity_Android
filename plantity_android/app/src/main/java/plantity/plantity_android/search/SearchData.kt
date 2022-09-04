package plantity.plantity_android.search

import java.io.Serializable

data class SearchData(val isSuccess: Boolean, val code: Int, val message: String, val result: SearchResult): Serializable

data class SearchResult(val content: ArrayList<Content>, val last: Boolean): Serializable

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
    val plantFollowings: ArrayList<Int>  // 찜한 사람..?
): Serializable {
   //constructor(): this(0, "","","","","","","","","","", "", ArrayList<Int>())
}  // 받아온 데이터의 plantId가 전부 0임... -> constructor() 지우기
