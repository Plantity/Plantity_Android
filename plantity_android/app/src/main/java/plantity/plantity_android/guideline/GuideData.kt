package plantity.plantity_android.guideline

data class GuideData (
    val guideTitle: String,
    val guideInfo: String
) {
    companion object
    val gdList = listOf(
        GuideData("타이틀1","샘플텍스트1"),
        GuideData("샘플타이틀2","샘플텍스트2"),
        GuideData("샘플타이틀3","샘플텍스트3"),
        GuideData("샘플타이틀4","샘플텍스트4"),
        GuideData("타이틀5","샘플텍스트5"),
    )
}
