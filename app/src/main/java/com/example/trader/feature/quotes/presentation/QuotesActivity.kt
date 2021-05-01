package com.example.trader.feature.quotes.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trader.R
import com.example.trader.common.SpaceDecoration
import com.example.trader.common.extension.showNow
import com.example.trader.databinding.ActivityQuotesBinding
import com.example.trader.feature.quotes.di.provideQuotesViewModel
import com.example.trader.feature.quotes.presentation.adapter.QuotesAdapter
import com.google.android.material.snackbar.Snackbar

class QuotesActivity : AppCompatActivity() {

    private val viewModel: QuotesViewModel by lazy { provideQuotesViewModel() }

    private lateinit var binding: ActivityQuotesBinding

    private val quotesAdapter = QuotesAdapter()

    private val networkStateReceiver = NetworkStateReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvQuotes.apply {
            adapter = quotesAdapter
            addItemDecoration(
                SpaceDecoration(
                    spacingTop = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    spacingBottom = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    spacingRight = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    spacingLeft = resources.getDimensionPixelSize(R.dimen.spacing_small)
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

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(networkStateReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkStateReceiver)
    }

    private fun initObservers() {
        viewModel.listItems.observe(this, {
            quotesAdapter.submitList(it)
            binding.progressQuotes.showNow(false)
        })
        viewModel.showMessage.observe(this, {
            Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_SHORT).show()

        })
        viewModel.showProgress.observe(this, {
            binding.progressQuotes.showNow(it)
        })
        viewModel.showErrorConnectionMessage.observe(this, {
            binding.tvQuotesConnection.showNow(it)
        })
    }

    private inner class NetworkStateReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            viewModel.onNetworkStateChanged()
        }
    }
}
