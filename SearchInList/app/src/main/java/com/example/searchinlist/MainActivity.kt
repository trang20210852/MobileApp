package com.example.searchinlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.searchinlist.R

class MainActivity : AppCompatActivity() {

    data class Student(
        val name: String,
        val mssv: String
    ) {
        override fun toString(): String {
            return "$name - $mssv"
        }
    }

    private lateinit var studentList: List<Student>
    private lateinit var filteredList: MutableList<Student>
    private lateinit var arrayAdapter: ArrayAdapter<Student>
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.searchEditText)
        val listView = findViewById<ListView>(R.id.listView)

        // Danh sách sinh viên ban đầu
        studentList = listOf(
            Student("Ha Quynh Trang", "20210001"),
            Student("Le Thi Nhung", "20210002"),
            Student("Do Thuy Duong", "20210003"),
            Student("Dinh Minh Anh", "20210004"),
            Student("Dang Hai Mai Linh", "20210005"),
            Student("Pham Van Anh", "20210006"),
            Student("Le Cao Phong", "20210007"),
            Student("Nguyen Thi Thao Nguyen", "20210008"),
            Student("Bui Duy Thanh", "20210009"),
        )

        // Sao chép danh sách ban đầu vào filteredList
        filteredList = studentList.toMutableList()

        // Thiết lập adapter cho ListView
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filteredList)
        listView.adapter = arrayAdapter

        // Thêm TextWatcher để lọc danh sách khi nhập từ khóa
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Hàm lọc danh sách sinh viên
    private fun filterList(query: String) {
        filteredList.clear()
        if (query.length > 2) {
            val lowerCaseQuery = query.lowercase()
            filteredList.addAll(studentList.filter {
                it.name.lowercase().contains(lowerCaseQuery) || it.mssv.contains(query)
            })
        } else {
            filteredList.addAll(studentList)
        }
        arrayAdapter.notifyDataSetChanged()
    }
}


