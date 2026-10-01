package nuIl.updater.ui.theme
import android.os.Build
import android.widget.ProgressBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import nuIl.updater.MainActivity.OtaMetadata.noUpdatesFound
import nuIl.updater.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(deviceModel: String)
{
    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background))
    {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.app_name), fontFamily = Inter,
                    fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            },
            actions = {
                IconButton(onClick = {})
                {
                    Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
        Card(modifier = Modifier.fillMaxWidth().padding(top = 5.dp, start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(1.dp, LocalAppColors.current.otaBorder))
        {
            Column(modifier = Modifier.padding(20.dp))
            {
                // head
                Text(text = stringResource(R.string.current_device_title), fontFamily = Inter, fontWeight = FontWeight.Medium,
                    fontSize = 11.sp, letterSpacing = 0.06.em, color = LocalAppColors.current.otaTextMuted);
                // device with manufacturer, it looks like this: Samsung Galaxy A35
                Text("${Build.MANUFACTURER} $deviceModel", modifier = Modifier.padding(top = 8.dp),
                    fontFamily = Inter, fontWeight = FontWeight.Bold,
                    fontSize = 32.sp, color = MaterialTheme.colorScheme.primary);
                // codename
                Card(modifier = Modifier.padding(top = 10.dp), shape = RoundedCornerShape(9.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(0.dp))
                {
                    Text(Build.MODEL, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = LocalAppColors.current.otaTextMuted);
                }
                // divider bro
                HorizontalDivider(modifier = Modifier.padding(top = 20.dp, bottom = 16.dp),
                    thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
                // checking progress
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
                {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    Text(text = "checking", modifier = Modifier.padding(start = 12.dp).weight(1f),
                        fontFamily = Inter, fontWeight = FontWeight.Medium,
                        fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                /*
                if(noUpdatesFound)
                {
                    // last checked: stays hidden until certain conditions met
                    Text(text = "lastChecked", modifier = Modifier.padding(top = 8.dp),
                        fontFamily = Inter, fontWeight = FontWeight.Medium,
                        fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                else {
                    // same for the changelog, it stays hidden
                    Text(text = "changelog", modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        fontFamily = Inter, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }*/
            }
        }
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            .padding(10.dp), contentAlignment = Alignment.BottomCenter)
        {
            LinearProgressIndicator(Modifier.width(350.dp))
        }
    }
}

@Preview(name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun FreakingPreview()
{
    SystemUpdatesTheme {
        HomeScreen("13s");
    }
}