package com.emrhmrc.mvvmcore.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.emrhmrc.mvvmcore.BuildConfig
import com.emrhmrc.mvvmcore.base.ErrorHandlingInterceptor
import com.emrhmrc.mvvmcore.data.local.AppDatabase
import com.emrhmrc.mvvmcore.data.local.dao.UserDao
import com.emrhmrc.mvvmcore.data.network.ApiHelper
import com.emrhmrc.mvvmcore.data.network.ApiHelperImpl
import com.emrhmrc.mvvmcore.data.network.ApiService
import com.emrhmrc.mvvmcore.di.DispatcherImpl
import com.emrhmrc.mvvmcore.di.DispatcherProvider
import com.emrhmrc.mvvmcore.helper.DataStoreHelper
import com.emrhmrc.mvvmcore.mapper.UserApiMapper
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
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        errorHandlingInterceptor: ErrorHandlingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(errorHandlingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("BASE_URL") baseUrl: String
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
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

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
