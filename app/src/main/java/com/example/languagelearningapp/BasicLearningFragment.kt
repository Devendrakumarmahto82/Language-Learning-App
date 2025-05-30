package com.example.languagelearningapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.languagelearningapp.databinding.FragmentBasicLearningBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BasicLearningFragment : Fragment() {
    private lateinit var binding: FragmentBasicLearningBinding

    private lateinit var questionList: List<Question>
    private lateinit var questionText: TextView
    private lateinit var translationText: TextView
    private lateinit var nextBtn: Button
    private lateinit var userAnswerEditText: EditText
    private lateinit var feedbackTextView: TextView
    private lateinit var checkBtn: Button
    private var currentIndex = 0

    // Sample local data source: list of pairs (question, translation)
    private val questions = listOf(
        Question(questionText = "Hello", translationText = "नमस्ते"),
        Question(questionText = "How are you?", translationText = "आप कैसे हैं?"),
        Question(questionText = "Thank you", translationText = "धन्यवाद"),
        Question(questionText = "Good morning", translationText = "सुप्रभात"),
        Question(questionText = "Good night", translationText = "शुभ रात्रि"),
        Question(questionText = "What is your name?", translationText = "आपका नाम क्या है?"),
        Question(questionText = "My name is John", translationText = "मेरा नाम जॉन है"),
        Question(questionText = "Where are you from?", translationText = "आप कहां से हैं?"),
        Question(questionText = "I am from India", translationText = "मैं भारत से हूँ"),
        Question(questionText = "Nice to meet you", translationText = "आपसे मिलकर खुशी हुई"),
        Question(questionText = "Please", translationText = "कृपया"),
        Question(questionText = "Sorry", translationText = "माफ़ कीजिए"),
        Question(questionText = "Yes", translationText = "हाँ"),
        Question(questionText = "No", translationText = "नहीं"),
        Question(questionText = "What time is it?", translationText = "समय क्या हुआ है?"),
        Question(questionText = "I don’t understand", translationText = "मैं नहीं समझा"),
        Question(questionText = "Can you help me?", translationText = "क्या आप मेरी मदद कर सकते हैं?"),
        Question(questionText = "Where is the bathroom?", translationText = "बाथरूम कहाँ है?"),
        Question(questionText = "I am hungry", translationText = "मुझे भूख लगी है"),
        Question(questionText = "Let’s go", translationText = "चलिए")
    )



    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBasicLearningBinding.inflate(layoutInflater)

        // Initialize views
        questionText = binding.questionText
        translationText = binding.translationText
        nextBtn = binding.continueBtn


        userAnswerEditText = binding.userAnswerEditText
        feedbackTextView = binding.feedbackTextView
        checkBtn = binding.checkBtn



        loadQuestionsFromDatabase()

        checkBtn.setOnClickListener {
            checkAnswer()
        }

        nextBtn.setOnClickListener {
            moveToNextQuestion()
        }
        // Clear feedback when user starts typing a new answer
        userAnswerEditText.doOnTextChanged { _, _, _, _ ->
            feedbackTextView.text = ""
        }


//        // Show first question
//   showQuestion(currentIndex)
//
        // Handle Next button click
      binding.nextbtn.setOnClickListener {
       findNavController().navigate(R.id.action_basicLearningFragment_to_fillIntheblanksFragment)
      }

        return binding.root
    }




    private fun loadQuestionsFromDatabase() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())
            val dao = db.questionDao()

            val allQuestions = withContext(Dispatchers.IO) {
                dao.getAllQuestions()
            }

            if (allQuestions.isEmpty()) {
                val defaultQuestions = listOf(
                    Question(questionText = "Hello", translationText = "नमस्ते"),
                    Question(questionText = "How are you?", translationText = "आप कैसे हैं?"),
                    Question(questionText = "Thank you", translationText = "धन्यवाद"),
                    Question(questionText = "Good morning", translationText = "सुप्रभात"),
                    Question(questionText = "Good night", translationText = "शुभ रात्रि"),
                    Question(questionText = "What is your name?", translationText = "आपका नाम क्या है?"),
                    Question(questionText = "My name is John", translationText = "मेरा नाम जॉन है"),
                    Question(questionText = "Where are you from?", translationText = "आप कहां से हैं?"),
                    Question(questionText = "I am from India", translationText = "मैं भारत से हूँ"),
                    Question(questionText = "Nice to meet you", translationText = "आपसे मिलकर खुशी हुई"),
                    Question(questionText = "Please", translationText = "कृपया"),
                    Question(questionText = "Sorry", translationText = "माफ़ कीजिए"),
                    Question(questionText = "Yes", translationText = "हाँ"),
                    Question(questionText = "No", translationText = "नहीं"),
                    Question(questionText = "What time is it?", translationText = "समय क्या हुआ है?"),
                    Question(questionText = "I don’t understand", translationText = "मैं नहीं समझा"),
                    Question(questionText = "Can you help me?", translationText = "क्या आप मेरी मदद कर सकते हैं?"),
                    Question(questionText = "Where is the bathroom?", translationText = "बाथरूम कहाँ है?"),
                    Question(questionText = "I am hungry", translationText = "मुझे भूख लगी है"),
                    Question(questionText = "Let’s go", translationText = "चलिए")
                )



            withContext(Dispatchers.IO) {
                dao.deleteAll()
                    dao.insertAll(defaultQuestions)
                }
                questionList = defaultQuestions
            } else {
                questionList = allQuestions
            }
            showQuestion(currentIndex)
        }
    }

    private fun showQuestion(index: Int) {
        val question = questionList[index]
        questionText.text = question.questionText
        translationText.text = question.translationText
        userAnswerEditText.text.clear()
        feedbackTextView.text = ""


        updateNextButtonVisibility()  // Update Next button visibility here
    }

    private fun checkAnswer() {
        val userAnswer = userAnswerEditText.text.toString().trim()
        if (userAnswer.isEmpty()) {
            feedbackTextView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            feedbackTextView.text = "Please enter your answer"
            return
        }

        val correctAnswer = questionList[currentIndex].translationText.trim()

        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            feedbackTextView.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            feedbackTextView.text = "Correct!"
        } else {
            feedbackTextView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            feedbackTextView.text = "Incorrect! Correct answer: $correctAnswer"
        }
    }  private fun moveToNextQuestion() {
        if (::questionList.isInitialized && questionList.isNotEmpty()) {
            currentIndex++
            if (currentIndex >= questionList.size) {
                currentIndex = 0
            }
            showQuestion(currentIndex)
        }
    }
    private fun updateNextButtonVisibility() {
        nextBtn.visibility = if (currentIndex == questionList.size - 1) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}
