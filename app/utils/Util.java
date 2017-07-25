package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;

public class Util {

	public static class Message {
		public String type = "error";
		public String text = "";
	}
	
	public static ObjectNode jsonResponse(Object response, boolean ok) {
         
        ObjectNode result = Json.newObject();
        result.put("success", ok);
        if (response instanceof String) {
            Message msg = new Message();
            msg.text = (String) response;
            if (ok) msg.type = "success";
            
            result.put("message", (JsonNode) Json.toJson(msg));
        } else {
            result.put("data",(JsonNode) response);
        }
 
        return result;
    }
	
}
