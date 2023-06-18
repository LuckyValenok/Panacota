package net.panacota.app

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import net.panacota.app.databinding.MainActivityBinding
import net.panacota.app.domain.repository.SharedPreferencesRepository
import net.panacota.app.ui.adapters.RecipesAdapter
import net.panacota.app.ui.listeners.EndlessRecyclerOnScrollListener
import net.panacota.app.ui.viewmodels.SearchViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository
    lateinit var binding: MainActivityBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        installSplashScreen()

        (application as MainApplication).component.injectMainActivity(this)

        binding = MainActivityBinding.inflate(layoutInflater)

        val adapter = RecipesAdapter(this) {
            it.root.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        }
        binding.apply {
            searchView.setupWithSearchBar(searchBar)

            recipes.adapter = adapter
            searchView.editText.addTextChangedListener {
                searchViewModel.load(it)
            }
            recipes.addOnScrollListener(EndlessRecyclerOnScrollListener {
                searchViewModel.loadMore()
            })
        }

        searchViewModel = viewModelFactory.create(SearchViewModel::class.java)

        searchViewModel.observe(this) {
            adapter.submitList(it)
        }

        setContentView(binding.root)
    }
}