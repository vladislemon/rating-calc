package com.miker9.GUtil.IO.Network;

/**
 * miker9
 * 25.04.14
 */
public interface ConnectionHandler {
    void onUpdate(Connection connection);
    void onConnecting(Connection connection);
    void onDisconnected(Connection connection);
    void onDisconnectedWithError(Connection connection);
}
