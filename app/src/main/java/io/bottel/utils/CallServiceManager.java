package io.bottel.utils;

import io.bottel.voip.VOIPService;

/**
 * Created by Omid on 9/17/2015.
 */
public class CallServiceManager {
    private static VOIPService callService = null;

    public static VOIPService getCallService() {
        return CallServiceManager.callService;
    }

    public static void setCallService(VOIPService callService) {
        CallServiceManager.callService = callService;
    }
}
