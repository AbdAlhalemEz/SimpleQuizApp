// This is an example Android activity that implements a simple quiz game.
// It has a list of questions with true or false answers, and the user can navigate between them
// and answer each question by clicking a true or false button.
// After answering, the app shows whether the user's answer was correct or incorrect, and displays an explanation.

package com.example.geoquiz

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.Questions.Questions

class MainActivity : AppCompatActivity() {

    // Declare the UI elements
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var preButton: Button
    private lateinit var questionTextView: TextView

    // Initialize the list of questions
    private val questionBank = listOf(
        Questions(R.string.question_canada, true, ""),
        Questions(R.string.question_syria, false,", The capital of syria is damascus"),
        Questions(R.string.question_france, true, ""),
        Questions(R.string.question_usa, false,", Washington is the USA Capital")
    )

    // Keep track of the current question index
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the UI elements by their IDs
        trueButton = findViewById(R.id.buttonTrue)
        falseButton = findViewById(R.id.buttonFalse)
        nextButton = findViewById(R.id.QuestionNext)
        preButton = findViewById(R.id.QuestionPrev)
        questionTextView = findViewById(R.id.quset1)

        // Set the click listeners for the buttons
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        preButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
            updateQuestion()
        }

        // Display the first question
        updateQuestion()
    }

    // Update the UI with the current question text
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].TextResId
        questionTextView.setText(questionTextResId)
    }

    // Check the user's answer and display a message with feedback and explanation
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val correction = questionBank[currentIndex].correction

        // Determine if the answer is correct and set the feedback message
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.toast_correct
        } else {
            R.string.toast_incorrect
        }

        // Determine the color of the text based on whether the answer is correct or incorrect
        val textColorResId = if (userAnswer == correctAnswer) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }

        // Animate the color of the text
        val colorAnimator = ValueAnimator.ofArgb(
            resources.getColor(android.R.color.primary_text_light),
            resources.getColor(textColorResId),
            resources.getColor(android.R.color.primary_text_light)
        )
        colorAnimator.duration = 1000
        colorAnimator.addUpdateListener { animator ->
            questionTextView.setTextColor(animator.animatedValue as Int)
        }


        colorAnimator.start()

        Toast.makeText(this, getString(messageResId) + " " + correction, Toast.LENGTH_SHORT).show()
    }
}