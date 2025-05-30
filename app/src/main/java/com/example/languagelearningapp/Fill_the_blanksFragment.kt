package com.example.languagelearningapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.languagelearningapp.databinding.FragmentFillTheBlanksBinding


class Fill_the_blanksFragment : Fragment() {

    private lateinit var viewModel: FillBlankViewModel

    private lateinit var questionText: TextView
    private lateinit var answerInput: EditText
    private lateinit var checkBtn: Button
    private lateinit var nextBtn: Button

    private lateinit var binding: FragmentFillTheBlanksBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentFillTheBlanksBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[FillBlankViewModel::class.java]

        questionText = view.findViewById(R.id.fillQuestionText)
        answerInput = view.findViewById(R.id.fillAnswerInput)
        checkBtn = view.findViewById(R.id.fillCheckBtn)
        nextBtn = view.findViewById(R.id.fillNextBtn)

        viewModel.preloadDataIfNeeded()

        viewModel.currentQuestion.observe(viewLifecycleOwner) { question ->
            if (question != null) {
                questionText.text = question.question
                answerInput.setText("")
                checkBtn.visibility = View.VISIBLE
                nextBtn.visibility = View.GONE
            } else {
                questionText.text = "No questions available"
            }
        }
        checkBtn.setOnClickListener {
            val userAnswer = answerInput.text.toString()
            val isCorrect = viewModel.checkAnswer(userAnswer)
            Toast.makeText(requireContext(), if (isCorrect) "Correct 🎉" else "Wrong ❌", Toast.LENGTH_SHORT).show()
            checkBtn.visibility = View.GONE
            nextBtn.visibility = View.VISIBLE
        }

        nextBtn.setOnClickListener {
            if (viewModel.hasNext()) {
                viewModel.goToNext()
            } else {
                Toast.makeText(requireContext(), "You've completed all questions!", Toast.LENGTH_LONG).show()
                nextBtn.visibility = View.GONE
            }
        }
    }


}