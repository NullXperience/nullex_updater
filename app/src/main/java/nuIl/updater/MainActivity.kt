package nuIl.updater
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import nuIl.updater.MainActivity.OtaMetadata.load
import nuIl.updater.additionals.MODEL_NAME
import nuIl.updater.additionals.UPDATER_PREFERENCES
import nuIl.updater.ota.api.ChangelogReference
import nuIl.updater.ota.api.ClientManager
import nuIl.updater.ota.api.OtaModel
import nuIl.updater.ui.theme.HomeScreen
import nuIl.updater.ui.theme.LoadingWindowFrame
import nuIl.updater.ui.theme.SystemUpdatesTheme
class MainActivity : ComponentActivity()
{
    private lateinit var calendarData: Calendar;
    private lateinit var sharedPreferences: SharedPreferences;
    override fun onCreate(savedInstanceState: Bundle?)
    {
        calendarData = Calendar.getInstance();
        sharedPreferences = this.getSharedPreferences(UPDATER_PREFERENCES, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContent {
            SystemUpdatesTheme {
                var deviceModel by remember { mutableStateOf<String?>(null) };
                LaunchedEffect(Unit)
                {
                    ///load();
                    val savedModel = sharedPreferences.getString(MODEL_NAME, null)
                    if(savedModel != null) deviceModel = savedModel
                    else
                    {
                        val model = ClientManager.getDevices()[Build.MODEL]?.name ?: Build.MODEL;
                        sharedPreferences.edit { putString(MODEL_NAME, model) };
                        deviceModel = model;
                    }
                }
                if(deviceModel == null) LoadingWindowFrame("Fetching device model data");
                else HomeScreen(deviceModel = deviceModel!!);
            }
        }
    }
    fun isInternetAvailable(): Boolean
    {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager;
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork);
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true;
    }
    object OtaURL {
        // url for retrofit, we would change this later on in the settings so i thought it would be a great idea to
        // just keep it this way so we can mod it later.
        var MODEL_URL = "https://raw.githubusercontent.com/bsthen/device-models/refs/heads/main/devices.json";
        var OTA_URL = "https://github.com/NullXperience/json_ota/releases/download/test/";
        var METADATA_URL = "https://raw.githubusercontent.com/NullXperience/json_ota/refs/heads/main/ota.json";
    }
    object OtaMetadata {
        lateinit var actualDeviceName: String;
        var deviceModel: String? = null;
        var buildID: String? = null
            private set
        var OTAUrl: String? = null
            private set
        var SHA256: String? = null
            private set
        var size: String? = null
            private set
        var latestVersion: String? = null
            private set
        var preferredModel: OtaModel? = null
            private set
        var versionSpecific: ChangelogReference? = null
            private set
        var isIncremental: Boolean = false
            private set
        var isSupported: Boolean = true
            private set
        var currentSystemVersion: String? = null
            private set;
        var noUpdatesFound: Boolean = false
            private set
        suspend fun load()
        {
            val metadata = ClientManager.getOtaInfo();
            //init
            currentSystemVersion = "1.0.0";
            actualDeviceName = Build.MODEL.toString();
            isSupported = actualDeviceName.let { name -> metadata.supported.split(",").any { it.trim().equals(name.trim(), ignoreCase = true) } } == true;
            if(isSupported)
            {
                preferredModel = metadata.models[actualDeviceName];
                latestVersion = preferredModel?.version;
                versionSpecific = preferredModel?.versions[latestVersion];
                OTAUrl = versionSpecific!!.url;
                SHA256 = versionSpecific!!.sha256;
                size = versionSpecific!!.size;
                buildID = versionSpecific!!.buildid;
                isIncremental = versionSpecific!!.isIncremental;
            }
            else noUpdatesFound = true;
        }
    }
}