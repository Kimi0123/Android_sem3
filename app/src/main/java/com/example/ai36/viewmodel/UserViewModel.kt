package com.example.ai36.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ai36.model.UserModel
import com.example.ai36.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(private val repo: UserRepository) : ViewModel() {

    // --- Authentication methods ---
    fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) { repo.login(email, password, callback) }

    fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) { repo.register(email, password, callback) }

    fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) { repo.forgetPassword(email, callback) }

    fun logout(callback: (Boolean, String) -> Unit) { repo.logout(callback) }

    fun getCurrentUser(): FirebaseUser? = repo.getCurrentUser()

    // --- Database methods ---
    fun addUserToDatabase(
        userId: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    ) { repo.addUserToDatabase(userId, model, callback) }

    fun deleteAccount(
        userId: String,
        callback: (Boolean, String) -> Unit
    ) { repo.deleteAccount(userId, callback) }

    fun editProfile(
        userId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) { repo.editProfile(userId, data, callback) }

    // --- User retrieval (SINGULAR) ---
    private val _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?> get() = _user

    fun getUserById(userId: String) {
        repo.getUserById(userId) { success, _, data ->
            _user.postValue(if (success) data else null)
        }
    }
}
