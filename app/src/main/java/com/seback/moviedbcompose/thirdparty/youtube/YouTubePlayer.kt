package com.seback.moviedbcompose.thirdparty.youtube

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayer(modifier: Modifier, videoId: String) {
    val videoIdState by rememberSaveable { mutableStateOf(videoId) }
    var playbackTime by rememberSaveable { mutableStateOf(0f) }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            YouTubePlayerView(context).apply {
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        if (playbackTime > 0) {
                            youTubePlayer.loadVideo(videoIdState, playbackTime)
                        } else {
                            youTubePlayer.cueVideo(videoIdState, playbackTime)
                        }
                    }

                    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                        super.onCurrentSecond(youTubePlayer, second)
                        playbackTime = second
                    }
                })
            }
        },
    )
}
