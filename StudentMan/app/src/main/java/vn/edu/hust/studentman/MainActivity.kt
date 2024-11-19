
package vn.edu.hust.studentman

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
    // Thêm sinh viên mẫu nếu cần
  )
  private lateinit var studentAdapter: StudentAdapter
  private var lastDeletedStudent: StudentModel? = null
  private var lastDeletedPosition: Int = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(students).apply {
      onEdit = { position -> showEditDialog(position) }
      onDelete = { position -> confirmDelete(position) }
    }

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddNewDialog()
    }
  }

  private fun showAddNewDialog() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
    val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
    val editTextID = dialogView.findViewById<EditText>(R.id.editTextID)

    AlertDialog.Builder(this)
      .setTitle("ADD STUDENT")
      .setView(dialogView)
      .setPositiveButton("ADD") { _, _ ->
        val name = editTextName.text.toString()
        val id = editTextID.text.toString()
        if (name.isNotBlank() && id.isNotBlank()) {
          students.add(StudentModel(name, id))
          studentAdapter.notifyItemInserted(students.size - 1)
        } else {
          Toast.makeText(this, "Please fill in all information", Toast.LENGTH_SHORT).show()
        }
      }
      .setNegativeButton("CANCEL", null)
      .show()
  }

  private fun showEditDialog(position: Int) {
    val student = students[position]
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
    val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
    val editTextID = dialogView.findViewById<EditText>(R.id.editTextID)

    editTextName.setText(student.studentName)
    editTextID.setText(student.studentId)

    AlertDialog.Builder(this)
      .setTitle("EDIT STUDENT")
      .setView(dialogView)
      .setPositiveButton("SAVE") { _, _ ->
        val newName = editTextName.text.toString()
        val newID = editTextID.text.toString()
        if (newName.isNotBlank() && newID.isNotBlank()) {
          students[position] = StudentModel(newName, newID)
          studentAdapter.notifyItemChanged(position)
        } else {
          Toast.makeText(this, "Please fill in all information", Toast.LENGTH_SHORT).show()
        }
      }
      .setNegativeButton("CANCEL", null)
      .show()
  }

  private fun confirmDelete(position: Int) {
    AlertDialog.Builder(this)
      .setTitle("DELETE STUDENT")
      .setMessage("Are you sure you want to delete this student?")
      .setPositiveButton("DELETE") { _, _ -> deleteStudent(position) }
      .setNegativeButton("CANCEL", null)
      .show()
  }

  private fun deleteStudent(position: Int) {
    lastDeletedStudent = students[position]
    lastDeletedPosition = position
    students.removeAt(position)
    studentAdapter.notifyItemRemoved(position)

    Snackbar.make(findViewById(R.id.main), "Student deleted", Snackbar.LENGTH_LONG)
      .setAction("UNDO") {
        lastDeletedStudent?.let {
          students.add(lastDeletedPosition, it)
          studentAdapter.notifyItemInserted(lastDeletedPosition)
        }
      }
      .show()
  }
}
