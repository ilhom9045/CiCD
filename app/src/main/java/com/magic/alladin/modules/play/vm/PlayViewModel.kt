package com.magic.alladin.modules.play.vm

import android.content.Context
import androidx.lifecycle.*
import com.magic.alladin.core.repository.ResManager
import com.magic.alladin.core.vm.BaseViewModel
import com.magic.alladin.modules.play.callBack.CheckedFieldListener
import com.magic.alladin.modules.play.model.CheckedFieldDataModel
import com.magic.alladin.modules.play.model.PlayModel
import com.magic.alladin.modules.play.repository.CheckClickedFieldRepository
import com.magic.alladin.modules.play.repository.PlayFieldRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlayViewModel(
    private val repository: PlayFieldRepository,
    private val checkClickedFieldRepository: CheckClickedFieldRepository = CheckClickedFieldRepository.Base()
) : BaseViewModel(), CheckedFieldListener {

    private val mItems = MutableLiveData<List<PlayModel>>()
    val items: LiveData<List<PlayModel>> = mItems

    private val mAllSuccess = MutableSharedFlow<Int>()
    val allSuccess = mAllSuccess.asSharedFlow()

    private val mCounter = MutableLiveData(0)
    val counter: LiveData<Int> = mCounter

    fun loadCards(isFirst: Boolean) {
        if (isFirst) {
            viewModelScope.launch {
                val items = repository.playField(16)
                mItems.postValue(items)
            }
        }
    }

    fun check(item: PlayModel) {
        checkClickedFieldRepository.clockedItems(item, this)
    }

    override fun checkedField(checkPlayData: CheckedFieldDataModel) {
        val firstModel = checkPlayData.firstField
        val secondModel = checkPlayData.secondField
        if (!checkPlayData.isTrue) {
            firstModel.isClose = true
            secondModel.isClose = true
            firstModel.listener?.update(firstModel)
            secondModel.listener?.update(secondModel)
        } else {
            firstModel.isSuccess = true
            secondModel.isSuccess = true
            if (firstModel.isClose) {
                firstModel.isClose = false
                checkPlayData.firstField.listener?.update(checkPlayData.firstField)
            }
            if (secondModel.isClose) {
                secondModel.isClose = false
                checkPlayData.secondField.listener?.update(checkPlayData.secondField)
            }
        }
        var isAllTrue = true
        items.value?.forEach {
            if (!it.isSuccess) {
                isAllTrue = false
                return@forEach
            }
        }
        viewModelScope.launch {
            mCounter.postValue(mCounter.value!! + 1)
            if (isAllTrue) {
                mAllSuccess.emit(mCounter.value!!)
            }
        }
    }

    fun cleanAll() {
        viewModelScope.launch {
            mCounter.value = 0
            loadCards(true)
        }
    }

    companion object {
        fun viewModelFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {

                val resManager: ResManager = ResManager.Base(context)
                val repository: PlayFieldRepository = PlayFieldRepository.Base(resManager)

                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlayViewModel(repository) as T
                }
            }
        }
    }
}