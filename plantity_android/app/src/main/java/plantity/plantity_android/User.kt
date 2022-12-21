package plantity.plantity_android

import java.io.Serializable

data class User(val isSuccess : Boolean,val code : Int, val message:String, val result:Userarr)

data class Userarr(val responseDto:UserInfo,val myPlantResponseDtos:ArrayList<UserPlantInfo>)

data class UserInfo(val nickName:String, val rating : String, val score : Int)

data class UserPlantInfo(val myPlantId: Int, val plantName: String, val plantNickName: String, val plantAdaptTime: String, val filePath: String)