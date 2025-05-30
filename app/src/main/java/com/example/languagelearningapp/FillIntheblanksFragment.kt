package com.example.languagelearningapp



import com.example.languagelearningapp.databinding.FragmentFillIntheblanksBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


class FillIntheblanksFragment : Fragment() {

//    private var _binding: FragmentFillIntheblanksBinding? = null
//    private var binding get() = _binding!!

    private lateinit var binding:FragmentFillIntheblanksBinding

    private var currentIndex = 0
    //private lateinit var viewModel: McqViewModel
    private lateinit var viewModel: McqRoomViewModel

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var optionButtons: List<RadioButton>
    private lateinit var checkBtn: Button
    private lateinit var nextBtn: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentFillIntheblanksBinding.inflate(layoutInflater)


        binding.nextbutton.setOnClickListener {
            findNavController().navigate(R.id.action_fillIntheblanksFragment_to_fill_the_blanksFragment)

        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        viewModel = McqViewModel()
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[McqRoomViewModel::class.java]
        questionText = view.findViewById(R.id.questionText)
        optionsGroup = view.findViewById(R.id.optionsGroup)
        optionButtons = listOf(
            view.findViewById(R.id.option1),
            view.findViewById(R.id.option2),
            view.findViewById(R.id.option3),
            view.findViewById(R.id.option4)
        )
        checkBtn = binding.checkBtn
        nextBtn = binding.nextbtn

//        showQuestion()
//        checkBtn.setOnClickListener {
//            val selectedId = optionsGroup.checkedRadioButtonId
//            val selectedIndex = optionButtons.indexOfFirst { it.id == selectedId }
//
//            if (selectedIndex == -1) {
//                Toast.makeText(requireContext(), "Please select an option", Toast.LENGTH_SHORT).show()
//            } else {
//                val isCorrect = viewModel.checkAnswer(selectedIndex)
//                val msg = if (isCorrect) "Correct 🎉" else "Wrong ❌"
//                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
//                nextBtn.visibility = View.VISIBLE
//                checkBtn.visibility = View.GONE
//            }
//        }
//
//        nextBtn.setOnClickListener {
//            if (viewModel.hasNext()) {
//                viewModel.goToNext()
//                showQuestion()
//                nextBtn.visibility = View.GONE
//                checkBtn.visibility = View.VISIBLE
//            } else {
//                Toast.makeText(requireContext(), "No more questions", Toast.LENGTH_SHORT).show()
//                nextBtn.visibility = View.GONE
//            }
//        }


        viewModel.preloadDataIfNeeded()

        viewModel.currentQuestion.observe(viewLifecycleOwner) { mcq ->
            mcq?.let {
                questionText.text = it.question
                optionsGroup.clearCheck()
                optionButtons[0].text = it.option1
                optionButtons[1].text = it.option2
                optionButtons[2].text = it.option3
                optionButtons[3].text = it.option4
                checkBtn.visibility = View.VISIBLE
                nextBtn.visibility = View.GONE
            }
        }
        checkBtn.setOnClickListener {
            val selectedId = optionsGroup.checkedRadioButtonId
            val selectedIndex = optionButtons.indexOfFirst { it.id == selectedId }

            if (selectedIndex == -1) {
                Toast.makeText(requireContext(), "Please select an answer", Toast.LENGTH_SHORT).show()
            } else {
                val correct = viewModel.checkAnswer(selectedIndex)
                Toast.makeText(requireContext(), if (correct) "Correct 🎉" else "Wrong ❌", Toast.LENGTH_SHORT).show()
                nextBtn.visibility = View.VISIBLE
                checkBtn.visibility = View.GONE
            }
        }

        nextBtn.setOnClickListener {
            if (viewModel.hasNext()) {
                viewModel.nextQuestion()
            } else {
                Toast.makeText(requireContext(), "All questions done!", Toast.LENGTH_SHORT).show()
                nextBtn.visibility = View.GONE
            }
        }

    }
}