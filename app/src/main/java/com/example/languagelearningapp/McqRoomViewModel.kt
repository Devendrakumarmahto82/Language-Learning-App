package com.example.languagelearningapp

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class McqRoomViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase2.getDatabase(application).mcqDao()

    private val _mcqList = MutableLiveData<List<McqEntity>>()
    val mcqList: LiveData<List<McqEntity>> = _mcqList

    private var currentIndex = 0
    private val _currentQuestion = MutableLiveData<McqEntity?>()
    val currentQuestion: LiveData<McqEntity?> = _currentQuestion

    init {
        loadMcqs()
    }

    private fun loadMcqs() = viewModelScope.launch {
        val questions = dao.getAllMcqs()
        if (questions.isNotEmpty()) {
            _mcqList.postValue(questions)
            _currentQuestion.postValue(questions[currentIndex])
        }
    }

    fun checkAnswer(selectedIndex: Int): Boolean {
        return _currentQuestion.value?.correctIndex == selectedIndex
    }

    fun hasNext(): Boolean {
        return (mcqList.value?.size ?: 0) > currentIndex + 1
    }

    fun nextQuestion() {
        currentIndex++
        _currentQuestion.value = mcqList.value?.getOrNull(currentIndex)
    }

    fun preloadDataIfNeeded() = viewModelScope.launch {
        if (dao.getAllMcqs().isEmpty()) {
            dao.insertAll(
                listOf(
                McqEntity(question = "How do you say 'नमस्ते' in English?", option1 = "Bye", option2 = "Thank you", option3 = "Hello", option4 = "Sorry", correctIndex = 2),
                McqEntity(question = "Which word means 'धन्यवाद'?", option1 = "Please", option2 = "Thank you", option3 = "Sorry", option4 = "Hello", correctIndex = 1),
                McqEntity(question = "What do you say in the morning?", option1 = "Good night", option2 = "Good morning", option3 = "Good evening", option4 = "Bye", correctIndex = 1),
                McqEntity(question = "What do you say before sleeping?", option1 = "Good night", option2 = "Good morning", option3 = "Thank you", option4 = "Hello", correctIndex = 0),
                McqEntity(question = "How do you ask someone’s name?", option1 = "Where are you from?", option2 = "How old are you?", option3 = "What is your name?", option4 = "How are you?", correctIndex = 2),
                McqEntity(question = "What is the opposite of 'Yes'?", option1 = "Okay", option2 = "No", option3 = "Thanks", option4 = "Sorry", correctIndex = 1),
                McqEntity(question = "Which one is a greeting?", option1 = "Sorry", option2 = "Hello", option3 = "Water", option4 = "Help", correctIndex = 1),
                McqEntity(question = "How do you say 'मुझे भूख लगी है'?", option1 = "I am tired", option2 = "I am hungry", option3 = "I am fine", option4 = "I am sleepy", correctIndex = 1),
                McqEntity(question = "What do you say when you meet someone for the first time?", option1 = "Good night", option2 = "Nice to meet you", option3 = "Bye", option4 = "Thank you", correctIndex = 1),
                McqEntity(question = "How do you say 'कृपया' in English?", option1 = "Thank you", option2 = "Please", option3 = "Sorry", option4 = "Help", correctIndex = 1),
                McqEntity(question = "What do you say when you make a mistake?", option1 = "Thank you", option2 = "Sorry", option3 = "Please", option4 = "Good night", correctIndex = 1),
                McqEntity(question = "Which is used to ask for help?", option1 = "Can you help me?", option2 = "What is your name?", option3 = "I am from India", option4 = "Nice to meet you", correctIndex = 0),
                McqEntity(question = "What is the English for 'शुभ रात्रि'?", option1 = "Good evening", option2 = "Good night", option3 = "Good morning", option4 = "Good day", correctIndex = 1),
                McqEntity(question = "Which sentence is correct?", option1 = "My name John", option2 = "My name is John", option3 = "Name is my John", option4 = "I name is John", correctIndex = 1),
                McqEntity(question = "How do you start a phone call?", option1 = "Goodbye", option2 = "Hello", option3 = "Thanks", option4 = "Sorry", correctIndex = 1),
                McqEntity(question = "Which of these is a polite word?", option1 = "Please", option2 = "No", option3 = "Go", option4 = "Sit", correctIndex = 0),
                McqEntity(question = "Which is a question?", option1 = "I am fine", option2 = "What is your name?", option3 = "Thank you", option4 = "Good morning", correctIndex = 1),
                McqEntity(question = "Which is used to say thanks?", option1 = "Sorry", option2 = "Please", option3 = "Thank you", option4 = "Excuse me", correctIndex = 2),
                McqEntity(question = "Which is a farewell phrase?", option1 = "Good night", option2 = "See you", option3 = "Good morning", option4 = "Nice to meet you", correctIndex = 1),
                McqEntity(question = "How do you ask for water?", option1 = "I want food", option2 = "Give me water", option3 = "Water is cold", option4 = "What is this?", correctIndex = 1)
            )

            )
            loadMcqs()
        }
    }
}
