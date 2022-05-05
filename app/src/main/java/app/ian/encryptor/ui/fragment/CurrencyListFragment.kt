package app.ian.encryptor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.ian.encryptor.databinding.CurrencyListFragmentBinding
import app.ian.encryptor.model.CurrencyInfo
import app.ian.encryptor.ui.adapter.CurrencyListAdapter
import app.ian.encryptor.ui.listener.OnItemClickedListener
import app.ian.encryptor.ui.viewmodel.CurrencyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CurrencyListFragment : Fragment() {

    companion object {
        fun newInstance() = CurrencyListFragment()
    }

    private val viewModel: CurrencyListViewModel by activityViewModels()

    private var _binding: CurrencyListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CurrencyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrencyListFragmentBinding.inflate(inflater, container, false)
        with(binding) {
            this.recyclerview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this.recyclerview.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = CurrencyListAdapter(object : OnItemClickedListener {
                override fun onItemClicked(item: CurrencyInfo) {
                    viewModel.onItemClicked(item)
                }
            })
            this.recyclerview.adapter = adapter
        }

        lifecycleScope.launch {
            lifecycleScope.launchWhenStarted {
                viewModel.uiState.collect {
                    adapter.submitList(it.currencyInfoList)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}