package com.example.trader.feature.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trader.R
import com.example.trader.common.extension.showNow
import com.example.trader.databinding.ActivityMainBinding
import com.example.trader.feature.main.di.provideMainViewModel
import com.example.trader.feature.quotes.presentation.QuotesFragment

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy { provideMainViewModel() }

    private lateinit var binding: ActivityMainBinding

    private val networkStateReceiver = NetworkStateReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, QuotesFragment())
                .commit()
        }
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
        viewModel.showErrorConnectionMessage.observe(this, {
            binding.tvMainConnection.showNow(it)
        })
    }

    private inner class NetworkStateReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            viewModel.onNetworkStateChanged()
        }
    }
}