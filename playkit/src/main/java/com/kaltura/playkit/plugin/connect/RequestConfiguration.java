package com.kaltura.playkit.plugin.connect;

/**
 * Created by tehilarozin on 27/10/2016.
 */

public interface RequestConfiguration {

    long getReadTimeout();
    long getWriteTimeout();
    long getConnectTimeout();
    int getRetry();
}