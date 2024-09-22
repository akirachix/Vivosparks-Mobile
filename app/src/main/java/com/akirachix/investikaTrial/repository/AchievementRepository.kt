import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.models.AchievementResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akirachix.investikaTrial.models.Achievement

class AchievementRepository {

    private val api = ApiClient.api

    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> get() = _achievements

    fun fetchAchievements() {
        api.achievements().enqueue(object : Callback<AchievementResponse> {
            override fun onResponse(call: Call<AchievementResponse>, response: Response<AchievementResponse>) {
                if (response.isSuccessful) {
                    _achievements.postValue(response.body()?.achievements ?: emptyList())
                }
            }

            override fun onFailure(call: Call<AchievementResponse>, t: Throwable) {
                // Handle the error case, e.g. logging or showing a message
                _achievements.postValue(emptyList())
            }
        })
    }
}
