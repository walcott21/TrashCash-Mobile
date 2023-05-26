import android.content.Context
import android.content.SharedPreferences

class UserDataManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_ADDRESS = "address"
    }

    var name: String?
        get() = sharedPreferences.getString(KEY_NAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_NAME, value).apply()

    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) = sharedPreferences.edit().putString(KEY_EMAIL, value).apply()

    var address: String?
        get() = sharedPreferences.getString(KEY_ADDRESS, null)
        set(value) = sharedPreferences.edit().putString(KEY_ADDRESS, value).apply()

    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }
}
