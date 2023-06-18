package net.panacota.app.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import dagger.*
import dagger.multibindings.IntoMap
import net.panacota.app.BuildConfig
import net.panacota.app.MainActivity
import net.panacota.app.domain.api.RecipesApi
import net.panacota.app.domain.database.RecipesDao
import net.panacota.app.domain.database.RecipesDatabase
import net.panacota.app.domain.repository.Repository
import net.panacota.app.domain.repository.RepositoryImpl
import net.panacota.app.domain.usecases.getRecipesByFilters.GetRecipesByFiltersUseCase
import net.panacota.app.domain.usecases.getRecipesByFilters.GetRecipesByFiltersUseCaseImpl
import net.panacota.app.ui.fragments.RecipesFragment
import net.panacota.app.ui.fragments.MainFragment
import net.panacota.app.ui.viewmodels.RecipesViewModel
import net.panacota.app.ui.viewmodels.MainViewModel
import net.panacota.app.ui.viewmodels.SearchViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun injectMainFragment(mainFragment: MainFragment)

    fun injectRecipesFragment(recipesFragment: RecipesFragment)

    fun injectMainActivity(mainActivity: MainActivity)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipesViewModel::class)
    abstract fun recipesViewModel(viewModel: RecipesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun searchViewModel(viewModel: SearchViewModel): ViewModel
}

@Module
abstract class NetworkModule {

    companion object {
        @Singleton
        @Provides
        fun provideHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder().addHeader("x-api-key", BuildConfig.API_KEY)
                            .build()
                    )
                }
                .build()
        }

        @Singleton
        @Provides
        fun provideConverterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }

        @Singleton
        @Provides
        fun provideRetrofitInstance(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(RecipesApi.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
        }


        @Singleton
        @Provides
        fun provideApiService(retrofit: Retrofit): RecipesApi {
            return retrofit.create(RecipesApi::class.java)
        }

        @Provides
        fun provideRecipesDatabase(context: Context): RecipesDatabase =
            Room.databaseBuilder(context.applicationContext, RecipesDatabase::class.java, RecipesDatabase.NAME).build()

        @Provides
        fun provideDao(recipesDatabase: RecipesDatabase): RecipesDao = recipesDatabase.recipesDao()
    }

    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    @Singleton
    abstract fun bindGetRecipesByFiltersUseCase(useCase: GetRecipesByFiltersUseCaseImpl): GetRecipesByFiltersUseCase
}
