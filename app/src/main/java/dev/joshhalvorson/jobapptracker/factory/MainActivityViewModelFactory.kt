package dev.joshhalvorson.jobapptracker.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.joshhalvorson.jobapptracker.network.ApplicationsApiInterface
import dev.joshhalvorson.jobapptracker.repository.ApplicationsRepository
import dev.joshhalvorson.jobapptracker.viewmodel.MainActivityViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory @Inject constructor (private val applicationsRepository: ApplicationsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(applicationsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}