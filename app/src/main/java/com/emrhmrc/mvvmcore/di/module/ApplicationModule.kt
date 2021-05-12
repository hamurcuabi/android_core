package com.emrhmrc.mvvmcore.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emrhmrc.mvvmcore.BuildConfig
import com.emrhmrc.mvvmcore.data.local.AppDatabase
import com.emrhmrc.mvvmcore.data.local.dao.UserDao
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Named("BASE_URL")
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
        okHttpClient: OkHttpClient, @Named("BASE_URL") baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideAppDatabase(
        app: Application,
        callback: AppDatabase.DbCallback,
        @Named("DATABASE_NAME") dataBaseName: String
    ): AppDatabase = Room.databaseBuilder(app, AppDatabase::class.java, dataBaseName)
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

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

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}
