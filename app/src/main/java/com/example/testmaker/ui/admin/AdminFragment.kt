package com.example.testmaker.ui.admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.AdminScreens
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentAdminBinding
import com.example.testmaker.models.admin.Teacher
import com.example.testmaker.ui.admin.adapters.AdminAdapter
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class AdminFragment: Fragment(R.layout.fragment_admin) {
    private lateinit var adapter: AdminAdapter

    private val binding by viewBinding(FragmentAdminBinding::bind)
    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTeacher.setOnClickListener {
            router.navigateTo(AdminScreens.addTeacher())
        }

        configureAdapters()

        // TODO test data
        adapter.set(
            listOf(
                Teacher("1", "jmih1@mail.ru", "Жмышенко Михаил Петрович"),
                Teacher("2", "jmih2@mail.ru", "Игонин Юрий Андреевич"),
                Teacher("3", "jmih3@mail.ru", "Игонян Фанзиль Фунялович"),
                Teacher("4", "jmih4@mail.ru", "Максимова Варвара Глебовна"),
                Teacher("5", "jmih5@mail.ru", "Алешин Кирилл Владимирович"),
                Teacher("6", "jmih6@mail.ru", "Юдин Илья Максимович"),
                Teacher("7", "jmih7@mail.ru", "Тимофеева Алиса Егоровна"),
                Teacher("8", "jmih8@mail.ru", "Кузнецова Амина Александровна"),
                Teacher("9", "jmih9@mail.ru", "Макаров Владислав Михайлович"),
            )
        )
    }

    private fun configureAdapters() {
        adapter = AdminAdapter()

        adapter.onChangeClicked = { teacher ->
            router.navigateTo(AdminScreens.addTeacher(teacher))
        }
        adapter.onDeleteClicked = {
            // TODO delete teacher
        }

        binding.recyclerView.adapter = adapter
    }
}