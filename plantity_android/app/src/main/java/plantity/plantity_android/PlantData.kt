package plantity.plantity_android

import java.io.Serializable

data class PlantData(val isSuccess: Boolean, val code: Int, val message: String, val result: PlantDataResult): Serializable

data class PlantDataResult(
    val plantIdx: Int,
    val cntntsNo: String,  // 고유 식물 번호
    val cntntsSj: String,  // 식물 명
    val plntbneNm: String,  // 학명
    val plntzrNm: String,  // 식물 영문명
    val adviseInfo: String,  // 식물 설명,
    val orgplceInfo: String,  // 원산지
    val lighttdemanddoCodeNm: String,  // 광도
    val ignSeasonCodeNm: String,  // 개화 시기
    val flclrCodeNm: String,  // 꽃 색
    val watercycleSprngCodeNm: String,  // 물 주기
    val managelevelCode: String,  // 관리 수준 코드 - 난이도
):Serializable
