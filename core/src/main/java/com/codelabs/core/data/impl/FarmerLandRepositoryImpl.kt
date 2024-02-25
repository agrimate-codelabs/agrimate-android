package com.codelabs.core.data.impl

import android.util.Log
import com.codelabs.core.data.source.local.LocalDataStore
import com.codelabs.core.data.source.remote.FarmerLandRemoteDataSource
import com.codelabs.core.data.source.remote.RegionRemoteDataSource
import com.codelabs.core.data.source.remote.body.CreateFarmerLandBody
import com.codelabs.core.domain.model.CreateFarmerLandModel
import com.codelabs.core.domain.model.FarmerLandListItemModel
import com.codelabs.core.domain.model.FarmerLandModel
import com.codelabs.core.domain.model.PlantNutritionModel
import com.codelabs.core.domain.model.RemainingLandAreaModel
import com.codelabs.core.domain.model.UploadImageModel
import com.codelabs.core.domain.repository.FarmerLandRepository
import com.codelabs.core.mapper.CreateFarmerLandMapper
import com.codelabs.core.mapper.RemainingLandAreaMapper
import com.codelabs.core.mapper.UploadImageMapper
import com.codelabs.core.utils.Resource
import com.codelabs.core.utils.apiRequestFlow
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

class FarmerLandRepositoryImpl @Inject constructor(
    private val farmerLandRemoteDataSource: FarmerLandRemoteDataSource,
    private val localDataStore: LocalDataStore,
    private val regionDataSource: RegionRemoteDataSource,
    private val createFarmerLandMapper: CreateFarmerLandMapper,
    private val uploadImageMapper: UploadImageMapper,
    private val remainingLandAreaMapper: RemainingLandAreaMapper,
    private val firebaseDB: FirebaseDatabase
) :
    FarmerLandRepository {

    override fun createFarmerLand(body: CreateFarmerLandBody): Flow<Resource<CreateFarmerLandModel>> =
        apiRequestFlow(
            call = {
                farmerLandRemoteDataSource.createFarmerLand(body)
            },
            mapper = createFarmerLandMapper
        )

    override fun updateFarmerLand(
        id: String,
        body: CreateFarmerLandBody
    ): Flow<Resource<CreateFarmerLandModel>> =
        apiRequestFlow(
            call = {
                farmerLandRemoteDataSource.update(id, body)
            },
            mapper = createFarmerLandMapper
        )

    override fun getFarmerLand(): Flow<Resource<List<FarmerLandListItemModel>>> = flow {
        emit(Resource.Loading())
        try {
            val response = farmerLandRemoteDataSource.getFarmerLand()
            val listModel = response.data?.map {
                val model = FarmerLandListItemModel(
                    id = it.id,
                    name = it.name,
                    district = it.district,
                    landArea = it.landArea,
                    lastActivity = it.updatedAt,
                    image = it.photo
                )
                model
            } ?: emptyList()
            emit(Resource.Success(listModel))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message().toString()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getFarmerLand(id: String): Flow<Resource<FarmerLandModel>> = flow {
        try {
            val response = farmerLandRemoteDataSource.getFarmerLand(id)
            val model = response.data.let {
                val listOfProvinceResponse = regionDataSource.getRegions(null, "province")
                val province =
                    listOfProvinceResponse.data?.find { region -> region.name == it?.province }
                Log.d("TEST FARMER", "$province")

                val listOfCityResponse = regionDataSource.getRegions(province?.code, "city")
                val city = listOfCityResponse.data?.find { region -> region.name == it?.city }
                Log.d("TEST FARMER", "$city")

                val listOfDistrict = regionDataSource.getRegions(city?.code, "district")
                val district =
                    listOfDistrict.data?.find { region -> region.name == it?.district }
                Log.d("TEST FARMER", "$city")

                val listOfVillage = regionDataSource.getRegions(district?.code, "village")
                val village =
                    listOfVillage.data?.find { region -> region.name == it?.village }
                Log.d("TEST FARMER", "$province")

                FarmerLandModel(
                    id = it?.id,
                    name = it?.name,
                    province = it?.province,
                    city = it?.city,
                    district = it?.district,
                    village = it?.village,
                    address = it?.address,
                    polygon = it?.polygon ?: emptyList(),
                    landArea = it?.landArea,
                    photo = it?.photo,
                    provinceCode = province?.code,
                    cityCode = city?.code,
                    districtCode = district?.code,
                    villageCode = village?.code
                )
            }
            emit(Resource.Success(model))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message().toString()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun uploadImage(image: MultipartBody.Part): Flow<Resource<UploadImageModel>> =
        apiRequestFlow(
            call = { farmerLandRemoteDataSource.uploadImage(image) },
            mapper = uploadImageMapper
        )

    override fun deleteLand(id: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val response = farmerLandRemoteDataSource.deleteLand(id)
            Log.d("Success", "Response $response")
            emit(Resource.Success("Berhasil Menghapus Data!"))
        } catch (e: Exception) {
            Log.d("Test Error", "${e.message}")
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override fun getPlantNutrition(id: String): Flow<PlantNutritionModel?> = callbackFlow {
        Log.d("TEST PlantNutrition", "land id: $id")
        val userId = localDataStore.userId.firstOrNull()
        Log.d("TEST", "userId $userId")
        val reference =
            firebaseDB.getReference("$userId/$id/npk_sensor")
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("TEST Snapshot", "$snapshot")
                val kalium = snapshot.child("kalium").getValue<Int>()
                val nitrogen = snapshot.child("nitrogen").getValue<Int>()
                val soilMoisture = snapshot.child("soilMoisture").getValue<Int>()
                val phosphorus = snapshot.child("phosphorus").getValue<Int>()
                val value = PlantNutritionModel(soilMoisture, nitrogen, kalium, phosphorus)
                trySendBlocking(value)
                Log.d("TEST DATA CHANGE", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TEST Canceled", error.toException().toString())
            }

        }

        reference.addValueEventListener(valueEventListener)

        awaitClose {
            reference.removeEventListener(valueEventListener)
        }
    }

    override fun getRemainingLandArea(id: String): Flow<Resource<RemainingLandAreaModel>> =
        apiRequestFlow(
            call = { farmerLandRemoteDataSource.getRemainingLandArea(id) },
            mapper = remainingLandAreaMapper
        )
}