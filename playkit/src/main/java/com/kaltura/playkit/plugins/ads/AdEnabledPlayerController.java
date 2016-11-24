package com.kaltura.playkit.plugins.ads;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kaltura.playkit.PKAdInfo;
import com.kaltura.playkit.PKLog;
import com.kaltura.playkit.PlayerDecorator;

import java.util.List;

import static com.kaltura.playkit.plugins.ads.AdsConfig.AD_TAG_LANGUAGE;
import static com.kaltura.playkit.plugins.ads.AdsConfig.AD_TAG_URL;
import static com.kaltura.playkit.plugins.ads.AdsConfig.AD_VIDEO_BITRATE;
import static com.kaltura.playkit.plugins.ads.AdsConfig.AUTO_PLAY_AD_BREAK;
import static com.kaltura.playkit.plugins.ads.AdsConfig.ENABLE_BG_PLAYBACK;
import static com.kaltura.playkit.plugins.ads.AdsConfig.VIDEO_MIME_TYPES;

/**
 * Created by gilad.nadav on 20/11/2016.
 */

public class AdEnabledPlayerController extends PlayerDecorator {
    public static final String  TAG = "AdEnablController";
    private static final PKLog log = PKLog.get(TAG);

    //IMASimplePlugin imaSimplePlugin;
    AdsProvider adsProvider;
    public AdEnabledPlayerController(AdsProvider adsProvider) {//(IMASimplePlugin imaSimplePlugin) {
        log.d("Init AdEnabledPlayerController");
        this.adsProvider = adsProvider;
    }

    @Override
    public long getDuration() {
        return super.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return super.getCurrentPosition();
    }

    @Override
    public void seekTo(long position) {
        super.seekTo(position);
    }

    @Override
    public void play() {
        log.d("AdEnabledPlayerController PLAY");
        if (!adsProvider.isAdDisplayed() && adsProvider.isAdRequested()) {
            super.play();
        } else if (adsProvider.isAdDisplayed()) {
            adsProvider.start(false);
        } else {
            super.pause();
            if (!adsProvider.isAdRequested()) {
                adsProvider.requestAd();
            }
        }
    }

    @Override
    public void pause() {
        log.d("AdEnabledPlayerController PAUSE");
        if (!adsProvider.isAdDisplayed()) {
            super.pause();
        } else {
            adsProvider.pause();
        }
    }

    @Override
    public void onApplicationResumed() {
        super.onApplicationResumed();
        if (!adsProvider.isAdPaused()) {
            log.d("AdEnabledPlayerController onApplicationResumed");

        }
    }

    @Override
    public void onApplicationPaused() {
        log.d("AdEnabledPlayerController onApplicationPaused");
        super.onApplicationPaused();
    }

    @Override
    public PKAdInfo getAdInfo() {
        return adsProvider.getAdInfo();
    }

    @Override
    public void updatePluginConfig(@NonNull String pluginName, @NonNull String key, @Nullable Object value) {
        if (value == null) {
            return;
        }
        if (adsProvider.getPluginName().equals(pluginName)) {
            if (key.equals(AD_TAG_LANGUAGE)) {
                adsProvider.getAdsConfig().setLanguage((String) value);
            } else if (key.equals(AD_TAG_URL)) {
                adsProvider.getAdsConfig().setAdTagUrl((String) value);
            } else if (key.equals(ENABLE_BG_PLAYBACK)) {
                adsProvider.getAdsConfig().setEnableBackgroundPlayback((boolean) value);
            } else if (key.equals(AUTO_PLAY_AD_BREAK)) {
                adsProvider.getAdsConfig().setAutoPlayAdBreaks((boolean) value);
            } else if (key.equals(AD_VIDEO_BITRATE)) {
                adsProvider.getAdsConfig().setVideoBitrate((int) value);
            } else if (key.equals(VIDEO_MIME_TYPES)) {
                adsProvider.getAdsConfig().setVideoMimeTypes((List<String>) value);
            }
            adsProvider.requestAd();
        }
    }
}
