package com.magic.alladin.modules.main.vm

import android.content.Context
import androidx.lifecycle.*
import com.magic.alladin.core.vm.BaseViewModel
import com.magic.alladin.modules.main.model.RecordsModel
import com.magic.alladin.modules.main.repository.RecordsRepository
import kotlinx.coroutines.launch

class MainViewModel(private val recordsRepository: RecordsRepository) : BaseViewModel() {

    private val mRecords = MutableLiveData<ArrayList<RecordsModel>>().apply {
        viewModelScope.launch {
            val value = recordsRepository.records()
            postValue(value)
        }
    }
    val records: LiveData<ArrayList<RecordsModel>> = mRecords

    fun isRecord(value: Int): Boolean {
        var records = records.value?:recordsRepository.records()
        if (records.isNullOrEmpty()) {
            return true
        }
        records.forEach {
            if (it.value > value) {
                return true
            }
        }
        return false
    }

    fun saveNewRecord(newRecord: RecordsModel) {
        val records = mRecords.value
        if (!records.isNullOrEmpty()) {
            records.add(newRecord)
            records.sortBy {
                it.value
            }
            if (records.size > 3) {
                for (i in 3 until records.size) {
                    records.remove(records[i])
                }
            }
            viewModelScope.launch {
                recordsRepository.saveRecords(records)
                mRecords.postValue(recordsRepository.records())
            }
        } else {
            viewModelScope.launch {
                val items = ArrayList<RecordsModel>()
                items.add(newRecord)
                recordsRepository.saveRecords(items)
                mRecords.postValue(recordsRepository.records())
            }
        }

    }


    companion object {
        fun viewModelFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {

                val recordsRepository: RecordsRepository = RecordsRepository.Base(context)
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(recordsRepository) as T
                }
            }
        }
    }
}