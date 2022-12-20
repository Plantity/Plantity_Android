package plantity.plantity_android.main

import retrofit2.Call
import retrofit2.http.*

interface AssignmentService {

}
// water, sun, look, repot
interface WaterAssService {
    @PUT("/myplant/water/{userId}/{myPlantId}")

    fun putWaterAssignment(
        @Path("userId") userId: Int,
        @Path("myPlantId") myPlantId: Int
    ): Call<PostAssignmentDTO>
}

interface SunAssService {
    @PUT("/myplant/sun/{userId}/{myPlantId}")

    fun putSunAssignment(
        @Path("userId") userId: Int,
        @Path("myPlantId") myPlantId: Int
    ): Call<PostAssignmentDTO>
}

interface LookAssService {
    @PUT("/myplant/look/{userId}/{myPlantId}")

    fun putLookAssignment(
        @Path("userId") userId: Int,
        @Path("myPlantId") myPlantId: Int
    ): Call<PostAssignmentDTO>
}

interface RepotAssService {
    @PUT("/myplant/repot/{userId}/{myPlantId}")

    fun putRepotAssignment(
        @Path("userId") userId: Int,
        @Path("myPlantId") myPlantId: Int
    ): Call<PostAssignmentDTO>
}