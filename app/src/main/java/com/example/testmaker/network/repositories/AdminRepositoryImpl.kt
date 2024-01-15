package com.example.testmaker.network.repositories

import com.example.testmaker.models.admin.TeacherBody
import com.example.testmaker.models.users.Teacher
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.services.AdminService

class AdminRepositoryImpl(private val apiService: AdminService): SuperRepository(), AdminRepository {
    override suspend fun delete(id: String): ApiResponse<Unit>  {
        return ApiResponse.Success(Unit)
//        request(apiService.deleteTeacher(id))
    }

    override suspend fun updateTeachers(): ApiResponse<List<Teacher>> {
        return ApiResponse.Success(listOf(
            Teacher("1", "jmih1@mail.ru", "Жмышенко Валерий Альбертович"),
            Teacher("2", "jmih2@mail.ru", "Игонин Юрий Андреевич"),
            Teacher("3", "jmih3@mail.ru", "Игонян Фанзиль Фунялович"),
            Teacher("4", "jmih4@mail.ru", "Максимова Варвара Глебовна"),
            Teacher("5", "jmih5@mail.ru", "Алешин Кирилл Владимирович"),
            Teacher("6", "jmih6@mail.ru", "Юдин Илья Максимович"),
            Teacher("7", "jmih7@mail.ru", "Тимофеева Алиса Егоровна"),
            Teacher("8", "jmih8@mail.ru", "Кузнецова Амина Александровна"),
            Teacher("9", "jmih9@mail.ru", "Макаров Владислав Михайлович")
        ))
//        request(apiService.updateTeachers())
    }

    override suspend fun changeTeacher(id: String, teacherBody: TeacherBody): ApiResponse<Unit> {
        return ApiResponse.Success(Unit)
        //  request(apiService.changeTeacher(id, login, name, password))
    }

    override suspend fun registerTeacher(teacherBody: TeacherBody): ApiResponse<Unit> {
        return ApiResponse.Success(Unit)
        //  request(apiService.registerTeacher(teacherRegisterBody))
    }
}