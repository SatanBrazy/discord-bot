package me.comu.exeter.musicplayer.Spotify;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

class SpotifyAudioTrack extends YoutubeAudioTrack {

    public SpotifyAudioTrack(AudioTrackInfo trackInfo, YoutubeAudioSourceManager sourceManager) {
        super(trackInfo, sourceManager);
    }
}
