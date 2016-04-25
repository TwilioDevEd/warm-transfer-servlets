package com.twilio.warmtransfer.servlets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vshyba on 25/04/16.
 */
public class ActiveCallsService {
    public static Map<String, String> localPersistence = new HashMap<>();

    public static void saveNewConference(String agentId, String conference_id){
        localPersistence.put(agentId, conference_id);
    }

    public static String getConferenceFromAgentID(String agentId){
        return localPersistence.get(agentId);
    }
}
