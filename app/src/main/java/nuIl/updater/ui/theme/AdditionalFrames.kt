package nuIl.updater.ui.theme
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingWindowFrame(progressText: String)
{
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        .padding(start = 10.dp, end = 10.dp, bottom = 25.dp), contentAlignment = Alignment.BottomCenter)
    {
        Column(Modifier.wrapContentWidth().background(color = LocalAppColors.current.otaBorder,
            shape = RoundedCornerShape(16.dp)).padding(24.dp))
        {
            Text("Please wait", // stringResource(R.string.pls_wait)
                fontFamily = Inter, fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
            Row(modifier = Modifier.fillMaxWidth()
                .padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically)
            {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp);
                Text(text = progressText, modifier = Modifier.padding(start = 14.dp),
                    fontFamily = Inter, fontWeight = FontWeight.Medium,
                    fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant);
            }
        }
    }
}

@Preview(showSystemUi = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AdditionalPreviews()
{
    SystemUpdatesTheme {
        LoadingWindowFrame("i see");
    }
}