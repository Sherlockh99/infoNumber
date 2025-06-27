package com.sh.work.infonumber.ui.main

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sh.work.infonumber.api.RetrofitSingleton
import com.sh.work.infonumber.dao.AppDatabase
import com.sh.work.infonumber.entity.HistoryEntity
import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val historyDao = db.historyDao()

    private val _histories = mutableStateListOf<HistoryEntity>()
    val histories: List<HistoryEntity> get() = _histories

    var inputNumber by mutableStateOf("")
        private set

    fun onInputNumberChange(newValue: String) {
        inputNumber = newValue
    }

    init {
        viewModelScope.launch {
            _histories.clear()
            _histories.addAll(historyDao.getAll())
        }
    }

    fun sendQuery(number: Int, onSuccess: ()->Unit = {}) {
        viewModelScope.launch {
            try {
                // Здесь должен быть реальный запрос, сейчас — заглушка
                val result = RetrofitSingleton.api.getInfoNumber(number)

                val history = HistoryEntity(
                    dateQuery = Date(),
                    number = number,
                    description = result
                )
                historyDao.insert(history)

                // Обновим список
                _histories.clear()
                _histories.addAll(historyDao.getAll())

                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendStaticQuery() {
        viewModelScope.launch {
            try {
                val result = RetrofitSingleton.api.getRandomInfo()

                val history = HistoryEntity(
                    dateQuery = Date(),
                    number = -1,
                    description = result
                )
                historyDao.insert(history)

                _histories.clear()
                _histories.addAll(historyDao.getAll())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}