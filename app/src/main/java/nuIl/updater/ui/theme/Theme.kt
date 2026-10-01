package nuIl.updater.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
// custom colors:
data class AppColors(
    val otaBorder: Color,
    val otaTextMuted: Color
)
private val LightAppColors = AppColors(otaBorder = Color(0xFFE1E3E8), otaTextMuted = Color(0xFF8B8F99))
private val DarkAppColors = AppColors(otaBorder = Color(0xFF303239), otaTextMuted = Color(0xFF858994))
val LocalAppColors = staticCompositionLocalOf { LightAppColors }
@Composable
fun SystemUpdatesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit)
{
    val context = LocalContext.current
    val appColors = if(darkTheme) DarkAppColors else LightAppColors;
    val colorScheme = if(darkTheme) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicDarkColorScheme(context) else darkColorScheme()
    }
    else {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicLightColorScheme(context) else lightColorScheme()
    }
    CompositionLocalProvider(LocalAppColors provides appColors)
    {
        MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content);
    }
}