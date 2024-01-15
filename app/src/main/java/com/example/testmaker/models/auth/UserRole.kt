package com.example.testmaker.models.auth

enum class UserRole {
    ADMIN,
    STUDENT,
    TEACHER;

    companion object {
        fun fromString(role: String): UserRole = when (role) {
            "Admin" -> ADMIN
            "Student" -> STUDENT
            "Teacher" -> TEACHER
            else -> STUDENT
        }
    }
}