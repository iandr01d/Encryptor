package app.ian.encryptor.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import app.ian.encryptor.Constants
import app.ian.encryptor.R
import app.ian.encryptor.databinding.DemoActivityBinding
import app.ian.encryptor.model.CurrencyInfo
import app.ian.encryptor.ui.fragment.CurrencyListFragment
import app.ian.encryptor.ui.viewmodel.CurrencyListViewModel
import app.ian.encryptor.util.FileUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {

    @Inject
    lateinit var gson: Gson

    private lateinit var binding: DemoActivityBinding
    private val viewModel: CurrencyListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DemoActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CurrencyListFragment.newInstance())
                .commitNow()
        }

        with(binding) {
            this.insertButton.setOnClickListener {
                val jsonFileString =
                    FileUtil.getJsonDataFromAssets(applicationContext, Constants.INPUT_FILE)
                val listPersonType = object : TypeToken<List<CurrencyInfo>>() {}.type
                val currencyList: List<CurrencyInfo> =
                    gson.fromJson(jsonFileString, listPersonType)

                lifecycleScope.launch {
                    viewModel.insertAllCurrencies(currencyList)
                }
            }

            this.sortButton.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.getAllCurrencies(true)
                }
            }

            this.clearButton.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.deleteAllCurrencies()
                }
            }
        }
    }
}