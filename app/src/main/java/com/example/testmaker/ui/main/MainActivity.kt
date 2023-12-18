package com.example.testmaker.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.testmaker.AuthScreens
import com.example.testmaker.core.utils.extensions.coroutine.observeOnCreated
import com.example.testmaker.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.example.testmaker.ui.main.viewModels.MainViewModel
import org.koin.android.ext.android.inject

class MainActivity: AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModel()

    private val navigator = AppNavigator(this, R.id.main_container)
    private val router: Router by inject()
    private val navigatorHolder: NavigatorHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservables()

        router.replaceScreen(AuthScreens.loginScreen())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    private fun initObservables() {
        observeOnCreated(viewModel.errors) { error ->
            binding.notificationsLayout.showNotification(error)
        }
    }
}