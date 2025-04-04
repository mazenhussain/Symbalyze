import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Canvas as AndroidCanvas
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import com.g5.symbalyze.ui.screens.Line

fun convertCanvasToBase64(lines: List<Line>): Result<String> {
    return try {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels

        val canvasWidth = screenWidth
        val canvasHeight = (screenHeight * 0.8).toInt()

        val bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
        val androidCanvas = AndroidCanvas(bitmap)

        androidCanvas.drawColor(Color.WHITE)

        lines.forEach { line ->
            androidCanvas.drawLine(
                line.start.x, line.start.y,
                line.end.x, line.end.y,
                android.graphics.Paint().apply {
                    color = Color.BLACK
                    strokeWidth = 10f
                }
            )
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        Result.success(Base64.encodeToString(byteArray, Base64.NO_WRAP))
    } catch (e: Exception) {
        Log.e("debug", "error converting canvas to base64", e)
        Result.failure(e)
    }
}
