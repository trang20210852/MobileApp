package com.example.studentman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {

    // Tách việc khởi tạo danh sách sinh viên ra thành một phương thức riêng.
    private val _students = MutableLiveData<List<StudentModel>>().apply {
        value = initializeStudents()
    }
    val students: LiveData<List<StudentModel>> get() = _students

    // Phương thức khởi tạo danh sách sinh viên
    private fun initializeStudents(): List<StudentModel> {
        return listOf(
            StudentModel("Le Thi Nhung", "20210662"),
            StudentModel("Do Thuy Duong", "20215019"),
            StudentModel("Pham Van Anh", "20214990"),

        )
    }

    // Thêm sinh viên mới vào danh sách
    fun addStudent(student: StudentModel) {
        val updatedList = _students.value?.toMutableList() ?: mutableListOf()
        updatedList.add(student)
        _students.postValue(updatedList)
    }

    // Cập nhật thông tin sinh viên theo id
    fun updateStudent(oldId: String, newName: String, newId: String) {
        val updatedList = _students.value?.map {
            if (it.studentId == oldId) it.copy(studentName = newName, studentId = newId)
            else it
        } ?: return
        _students.postValue(updatedList)
    }

    // Xóa sinh viên khỏi danh sách
    fun deleteStudent(student: StudentModel) {
        val updatedList = _students.value?.toMutableList()?.apply {
            remove(student)
        } ?: return
        _students.postValue(updatedList)
    }

    // Lấy thông tin sinh viên theo id
    fun getStudentById(id: String?): StudentModel? {
        return _students.value?.find { it.studentId == id }
    }
}
