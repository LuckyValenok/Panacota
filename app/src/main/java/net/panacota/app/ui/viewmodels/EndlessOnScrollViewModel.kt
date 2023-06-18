package net.panacota.app.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.*

abstract class EndlessOnScrollViewModel<T, P> : ViewModel() {
    private val liveData = MutableLiveData<List<T>>(mutableListOf())
    private var job: Job? = null
    private var loadMoreJob: Job? = null
    private var lastLoadMoreCounts: Int = 0
    private var arg: P? = null

    fun load(arg: P?) {
        this.arg = arg
        job?.cancel("New load").apply {
            onCancel(liveData)
        }
        if (!checkArgument(arg)) {
            onCancel(liveData)
            return
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            val list = getResult(arg, 0)
            lastLoadMoreCounts = list.size
            withContext(Dispatchers.Main) {
                liveData.postValue(list)
            }
        }
    }

    fun loadMore() {
        val size = size()
        if (job == null || loadMoreJob != null || lastLoadMoreCounts == 0 || size == null || size == 0) {
            return
        }
        loadMoreJob = viewModelScope.launch(Dispatchers.IO) {
            val list = getResult(arg, size)
            lastLoadMoreCounts = list.size
            withContext(Dispatchers.Main) {
                liveData.postValue(liveData.value?.plus(list))
                loadMoreJob = null
            }
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<T>>) {
        liveData.observe(owner, observer)
    }

    private fun size(): Int? = liveData.value?.size

    protected abstract suspend fun getResult(arg: P?, offset: Int): List<T>

    protected abstract fun onCancel(liveData: MutableLiveData<List<T>>)

    protected abstract fun checkArgument(arg: P?): Boolean
}