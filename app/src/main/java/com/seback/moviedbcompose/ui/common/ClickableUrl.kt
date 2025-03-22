package com.seback.moviedbcompose.ui.common

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri

@Composable
fun ClickableUrl(modifier: Modifier = Modifier, text: String, url: String) {
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
        pushStyle(
            SpanStyle(
                color = MaterialTheme.colors.secondaryVariant,
                textDecoration = TextDecoration.Underline
            )
        )
        append(text)
        addStringAnnotation(
            tag = "URL",
            annotation = url,
            start = 0,
            end = text.length
        )
    }

    Text(
        modifier = modifier.clickable {
            val annotation =
                annotatedText.getStringAnnotations(tag = "URL", start = 0, end = text.length)
                    .firstOrNull()
            annotation?.let {
                val intent = Intent(ACTION_VIEW, it.item.toUri())
                context.startActivity(intent)
            }
        },
        text = annotatedText
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewClickableUrl() {
    ClickableUrl(
        text = "Click here to visit the website",
        url = "https://www.example.com"
    )
}