package nuIl.updater.ota.api
import nuIl.updater.MainActivity.OtaURL.METADATA_URL
import nuIl.updater.MainActivity.OtaURL.MODEL_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
data class DeviceInfo(
    val brand: String,
    val name: String
)
data class OtaResponse (
    val models: Map<String, OtaModel>,
    val supported: String
)
data class OtaModel(
    val versions: Map<String, ChangelogReference>,
    val version: String
)
data class ChangelogReference (
    val changelogs: List<String>,
    val buildid: String,
    val isIncremental: Boolean,
    val isFull: Boolean,
    val url: String,
    val sha256: String,
    val size: String,
)
object ClientManager {
    private const val BASE_URL = "https://raw.githubusercontent.com/";
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val api: ClientInterface by lazy {
        retrofit.create(ClientInterface::class.java)
    }
    suspend fun getDevices(): Map<String, DeviceInfo> = api.getDevices(MODEL_URL);
    suspend fun getOtaInfo(): OtaResponse = api.getOtaInfo(METADATA_URL);
}