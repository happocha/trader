package com.example.trader.feature.quotes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trader.common.SingleLiveEvent
import com.example.trader.feature.quotes.domain.QuotesUseCase
import com.example.trader.feature.quotes.domain.model.QuoteModel
import com.example.trader.feature.quotes.presentation.model.QuoteViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

interface QuotesViewModel {
    val listItems: LiveData<List<QuoteViewData>>
    val showMessage: LiveData<String>
    val showProgress: LiveData<Boolean>
}

class QuotesViewModelImpl(
    private val quotesUseCase: QuotesUseCase,
    private val quotesConverter: QuotesConverter
) : QuotesViewModel, ViewModel() {

    private var job: Job? = null

    private val cachedMap = HashMap<String, QuoteModel>()
    private val currentMap = HashMap<String, QuoteModel>()

    override val listItems = MutableLiveData<List<QuoteViewData>>()
    override val showProgress = MutableLiveData<Boolean>()
    override val showMessage = SingleLiveEvent<String>()

    init {
        getQuotes()
    }

    private fun getQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            quotesUseCase.getQuotes()
                .onStart { showProgress.postValue(true) }
                .catch {
                    if (it.localizedMessage.isNullOrEmpty().not()) {
                        showMessage.postValue(it.localizedMessage)
                    }
                    showProgress.postValue(false)
                }
                .filter { it?.c != null }
                .collect { response ->
                    response?.let { model ->
                        model.c?.let {
                            currentMap[it] = model
                        }
                    }
                    job?.cancel()
                    job = launch(Dispatchers.IO) {
                        delay(2000)
                        listItems.postValue(
                            quotesConverter.convert(
                                currentMap,
                                cachedMap
                            )
                        )
                    }
                    job = null
                }
        }
    }
}
