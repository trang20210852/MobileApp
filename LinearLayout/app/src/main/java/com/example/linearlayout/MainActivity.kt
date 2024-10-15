//package com.example.linearlayout
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}
//



package com.example.linearlayout

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var operationTextView: TextView

    private var firstOperand: Double? = null
    private var secondOperand: Double? = null
    private var currentOperation: String? = null
    private var isNewOperation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.textView4)
        operationTextView = findViewById(R.id.textView3)

        setupNumberButtons()
        setupOperationButtons()
    }

    private fun setupNumberButtons() {
        val buttons = listOf<Button>(
            findViewById(R.id.button22),  // 0
            findViewById(R.id.button19),  // 1
            findViewById(R.id.button18),  // 2
            findViewById(R.id.button17),  // 3
            findViewById(R.id.button15),  // 4
            findViewById(R.id.button14),  // 5
            findViewById(R.id.button13),  // 6
            findViewById(R.id.button11),  // 7
            findViewById(R.id.button9),   // 8
            findViewById(R.id.button5)    // 9
        )

        for ((index, button) in buttons.withIndex()) {
            button.setOnClickListener {
                onNumberClick(index.toString())
            }
        }

        findViewById<Button>(R.id.button21).setOnClickListener {
            onNumberClick(".")
        }
    }

    private fun setupOperationButtons() {
        findViewById<Button>(R.id.button4).setOnClickListener { onOperationClick("/") }
        findViewById<Button>(R.id.button7).setOnClickListener { onOperationClick("*") }
        findViewById<Button>(R.id.button12).setOnClickListener { onOperationClick("-") }
        findViewById<Button>(R.id.button16).setOnClickListener { onOperationClick("+") }

        findViewById<Button>(R.id.button20).setOnClickListener { onEqualClick() }
        findViewById<Button>(R.id.button).setOnClickListener { onClearEntry() }
        findViewById<Button>(R.id.button3).setOnClickListener { onClearAll() }
    }

    private fun onNumberClick(value: String) {
        if (isNewOperation) {
            resultTextView.text = ""
            isNewOperation = false
        }

        val currentText = resultTextView.text.toString()
        if (currentText == "0" && value != ".") {
            resultTextView.text = value
        } else {
            resultTextView.text = currentText + value
        }
    }

    private fun onOperationClick(operation: String) {
        firstOperand = resultTextView.text.toString().toDoubleOrNull()
        currentOperation = operation
        operationTextView.text = "${resultTextView.text}$operation"
        isNewOperation = true
    }

    private fun onEqualClick() {
        secondOperand = resultTextView.text.toString().toDoubleOrNull()

        if (firstOperand != null && secondOperand != null && currentOperation != null) {
            val result = calculate(firstOperand!!, secondOperand!!, currentOperation!!)
            resultTextView.text = result.toString()
            operationTextView.text = "${firstOperand}${currentOperation}${secondOperand}="
            firstOperand = result
            secondOperand = null
            isNewOperation = true
        }
    }

    private fun calculate(first: Double, second: Double, operation: String): Double {
        return when (operation) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> if (second != 0.0) first / second else 0.0
            else -> 0.0
        }
    }

    private fun onClearEntry() {
        resultTextView.text = "0"
        isNewOperation = true
    }

    private fun onClearAll() {
        resultTextView.text = "0"
        operationTextView.text = "phep toan"
        firstOperand = null
        secondOperand = null
        currentOperation = null
        isNewOperation = false
    }
}
