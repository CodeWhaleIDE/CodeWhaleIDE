package com.bluewhaleyt.codewhaleide.sdk.ui

import android.content.Context
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import java.io.File

@Composable
fun Icon(
    icon: Icon,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    when (icon) {
        is Icon.Painter -> {
            androidx.compose.material3.Icon(
                modifier = modifier,
                painter = icon.data,
                contentDescription = icon.contentDescription,
                tint = Color.Unspecified
            )
        }
        is Icon.Vector -> {
            androidx.compose.material3.Icon(
                modifier = modifier,
                imageVector = icon.data,
                contentDescription = icon.contentDescription
            )
        }
        is Icon.Bitmap -> {
            androidx.compose.material3.Icon(
                modifier = modifier,
                bitmap = icon.data,
                contentDescription = icon.contentDescription,
                tint = Color.Unspecified
            )
        }
        is Icon.Drawable -> {
            Image(
                modifier = modifier,
                painter = rememberDrawablePainter(icon.data),
                contentDescription = icon.contentDescription
            )
        }
        is Icon.File -> {
            val model = when (icon.data.extension.lowercase()) {
                "svg" -> icon.data.coilSvg(context)
                "gif" -> icon.data.coilGif(context)
                else -> ImageRequest.Builder(context)
                    .data(icon.data)
                    .build()
            }
            AsyncImage(
                modifier = modifier,
                model = model,
                contentDescription = icon.contentDescription
            )
        }
    }
}

sealed interface Icon {
    val data: Any
    val contentDescription: String?

    data class Painter(
        override val data: androidx.compose.ui.graphics.painter.Painter,
        override val contentDescription: String?
    ) : Icon

    data class Vector(
        override val data: ImageVector,
        override val contentDescription: String?
    ) : Icon

    data class Bitmap(
        override val data: ImageBitmap,
        override val contentDescription: String?
    ) : Icon

    data class Drawable(
        override val data: android.graphics.drawable.Drawable,
        override val contentDescription: String?
    ) : Icon

    data class File(
        override val data: java.io.File,
        override val contentDescription: String?
    ) : Icon
}

private fun File.coilGif(context: Context) = ImageRequest.Builder(context)
    .data(this)
    .decoderFactory(
        if (Build.VERSION.SDK_INT >= 28) AnimatedImageDecoder.Factory()
        else GifDecoder.Factory()
    )
    .build()

private fun File.coilSvg(context: Context) = ImageRequest.Builder(context)
    .data(this)
    .decoderFactory(SvgDecoder.Factory())
    .build()