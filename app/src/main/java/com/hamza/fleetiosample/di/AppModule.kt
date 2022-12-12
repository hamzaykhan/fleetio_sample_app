package com.hamza.fleetiosample.di

import android.app.Application
import androidx.room.Room
import com.hamza.fleetiosample.BuildConfig
import com.hamza.fleetiosample.feature_vehicle.data.local.FleetioDatabase
import com.hamza.fleetiosample.feature_vehicle.data.remote.FleetioApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request =  chain.request()
                    .newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Token ${BuildConfig.API_KEY}")
                    .addHeader("Account-Token", BuildConfig.ACCOUNT_TOKEN)
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideFleetioApi(okHttpClient: OkHttpClient): FleetioApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(FleetioApi::class.java)

    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): FleetioDatabase {
        return Room.databaseBuilder(
            app,
            FleetioDatabase::class.java,
            FleetioDatabase.DATABASE_NAME
        ).build()
    }
}