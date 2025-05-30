package com.example.languagelearningapp

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch



class FillBlankViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase3.getDatabase(application).fillBlankDao()

//    private val _questions = MutableLiveData<List<FillBlankEntity>>()
//    val questions: LiveData<List<FillBlankEntity>> = _questions
//
//    private val _currentIndex = MutableLiveData(0)
//    val currentIndex: LiveData<Int> = _currentIndex


//    val currentQuestion: LiveData<FillBlankEntity?> = _questions.map { list ->
//        list.getOrNull(_currentIndex.value ?: 0)
//    }
    private val _questions = MutableLiveData<List<FillBlankEntity>>()
    private val _currentIndex = MutableLiveData(0)

    // ✅ MediatorLiveData to update currentQuestion when either list or index changes
    val currentQuestion = MediatorLiveData<FillBlankEntity?>().apply {
        addSource(_questions) { updateCurrentQuestion() }
        addSource(_currentIndex) { updateCurrentQuestion() }
    }

    private fun updateCurrentQuestion() {
        val list = _questions.value
        val index = _currentIndex.value ?: 0
        currentQuestion.value = list?.getOrNull(index)
    }




    fun preloadDataIfNeeded() = viewModelScope.launch {
        if (dao.getAll().isEmpty()) {
            dao.insertAll(
                 listOf(
            FillBlankEntity(question = "How ___ you?", answer = "are"),
            FillBlankEntity(question = "Good ___", answer = "morning"),
            FillBlankEntity(question = "Good ___", answer = "night"),
            FillBlankEntity(question = "What is your ___?", answer = "name"),
            FillBlankEntity(question = "Where ___ you from?", answer = "are"),
            FillBlankEntity(question = "Nice to ___ you", answer = "meet"),
                     FillBlankEntity(question = "___ morning!", answer = "Good"),
                     FillBlankEntity(question = "___ night!", answer = "Good"),
                     FillBlankEntity(question = "___ you very much", answer = "Thank"),
                     FillBlankEntity(question = "How ___ you?", answer = "are"),
                     FillBlankEntity(question = "___ to meet you", answer = "Nice"),
                     FillBlankEntity(question = "Please ___ down", answer = "sit"),
                     FillBlankEntity(question = "___ me a glass of water", answer = "Give"),
                     FillBlankEntity(question = "What is your ___?", answer = "name"),
                     FillBlankEntity(question = "___ to meet you", answer = "Nice"),
                     FillBlankEntity(question = "Have a ___ day!", answer = "nice"),
                     FillBlankEntity(question = "Where are ___ from?", answer = "you"),
            )

            )
        }
        _questions.postValue(dao.getAll())
    }


    fun checkAnswer(userAnswer: String): Boolean {
        return currentQuestion.value?.answer?.trim()?.equals(userAnswer.trim(), ignoreCase = true) == true
    }

    fun hasNext(): Boolean {
        return (_questions.value?.size ?: 0) > (_currentIndex.value ?: 0) + 1
    }

    fun goToNext() {
        _currentIndex.value = (_currentIndex.value ?: 0) + 1
    }
}
