package com.emrhmrc.mvvmcore.di.module

import android.content.Context
import androidx.room.Room
import com.emrhmrc.mvvmcore.BuildConfig
import com.emrhmrc.mvvmcore.data.local.AppDatabase
import com.emrhmrc.mvvmcore.data.local.DatabaseHelper
import com.emrhmrc.mvvmcore.data.local.DatabaseHelperImpl
import com.emrhmrc.mvvmcore.data.network.ApiHelper
import com.emrhmrc.mvvmcore.data.network.ApiHelperImpl
import com.emrhmrc.mvvmcore.data.network.ApiService
import com.emrhmrc.mvvmcore.mapper.UserApiMapper
import com.emrhmrc.mvvmcore.utils.DataStoreHelper
import com.emrhmrc.mvvmcore.utils.DispatcherImpl
import com.emrhmrc.mvvmcore.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.SERVICE_API_URL

    @Provides
    @Named("DATABASE_NAME")
    fun provideDatabaseName() = BuildConfig.DATABASE_NAME


    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @Named("DATABASE_NAME") DatabaseName: String
    ): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DatabaseName
        ).fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideDatabaseHelper(databaseHelper: DatabaseHelperImpl): DatabaseHelper = databaseHelper

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStoreHelper =
        DataStoreHelper(context)

    @Provides
    @Singleton
    fun provideUserApiMapper() = UserApiMapper()

    @Provides
    @Singleton
    fun provideDispatchers(dispatcherImpl: DispatcherImpl): DispatcherProvider = dispatcherImpl
}