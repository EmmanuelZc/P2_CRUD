package ipn.mx.loginmovil.ui.theme.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipn.mx.loginmovil.data.api.RetrofitClient
import ipn.mx.loginmovil.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {

    val userLiveData = MutableLiveData<User?>()
    val usersLiveData = MutableLiveData<List<User>?>()
    val errorMessage = MutableLiveData<String>()

    // Método para registrar un usuario
    fun registerUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val call = RetrofitClient.instance.registerUser(user)
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        userLiveData.postValue(response.body())
                    } else {
                        errorMessage.postValue("Error al registrar usuario")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun login(username: String, password: String, callback: (Boolean, User?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = User(username = username, password = password) // Llenar solo con username y password para el login
            val call = RetrofitClient.instance.loginUser(user)
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val loggedInUser = response.body()
                        userLiveData.postValue(loggedInUser)
                        callback(true, loggedInUser)
                    } else {
                        errorMessage.postValue("Error en la autenticación")
                        callback(false, null)
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    callback(false, null)
                }
            })
        }
    }

    fun getAllUsers(callback: (Boolean, List<User>?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val call = RetrofitClient.instance.getAllUsers()
            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val usersList = response.body()
                        usersLiveData.postValue(usersList)
                        callback(true, usersList)
                    } else {
                        errorMessage.postValue("Error al cargar la lista de usuarios")
                        callback(false, null)
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    callback(false, null)
                }
            })
        }
    }


    // Método para obtener el perfil del usuario
    fun getUserProfile(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val call = RetrofitClient.instance.getUserProfile(username)
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        userLiveData.postValue(response.body())
                    } else {
                        errorMessage.postValue("Error al obtener el perfil del usuario")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }
            })
        }
    }
}