package com.example.trader.feature.quotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trader.R
import com.example.trader.common.SpaceDecoration
import com.example.trader.common.extension.showNow
import com.example.trader.databinding.FragmentQuotesBinding
import com.example.trader.feature.quotes.di.provideQuotesViewModel
import com.example.trader.feature.quotes.presentation.adapter.QuotesAdapter
import com.google.android.material.snackbar.Snackbar

class QuotesFragment : Fragment() {

    private val viewModel: QuotesViewModel by lazy { provideQuotesViewModel() }

    private lateinit var binding: FragmentQuotesBinding

    private val quotesAdapter = QuotesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quotes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuotesBinding.bind(view)
        binding.rvQuotes.apply {
            adapter = quotesAdapter
            addItemDecoration(
                SpaceDecoration(
                    spacingTop = resources.getDimensionPixelSize(R.dimen.spacing_medium),
                    spacingBottom = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    spacingRight = resources.getDimensionPixelSize(R.dimen.spacing_medium),
                    spacingLeft = resources.getDimensionPixelSize(R.dimen.spacing_large)
                )
            )
            addItemDecoration(
                QuotesDivideDecoration(
                    binding.rvQuotes.context
                )
            )
        }
        initObservers()
    }


    private fun initObservers() {
        viewModel.listItems.observe(this, {
            quotesAdapter.submitList(it)
            binding.progressQuotes.showNow(false)
        })
        viewModel.showMessage.observe(this, {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                it,
                Snackbar.LENGTH_SHORT
            ).show()

        })
        viewModel.showProgress.observe(this, {
            binding.progressQuotes.showNow(it)
        })
    }
}
