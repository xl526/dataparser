/*
Copyright (c) Microsoft Open Technologies, Inc.
All Rights Reserved
See License.txt in the project root for license information.
*/

package com.ts.zx.ig.fbox.conn.signalr.internal;

/**
 * A thread that can release resources when stopped
 */
abstract class NetworkThread extends Thread {

    /**
     * Initializes the NetworkThread
     *
     * @param target
     *            runnable to execute
     */
    public NetworkThread(Runnable target) {
        super(target);
    }

    /**
     * Releases resources and stops the thread
     */
    abstract void releaseAndStop();
}
