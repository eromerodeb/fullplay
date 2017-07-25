package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;

public class Util {

	public static ObjectNode jsonResponse(Object response, boolean ok) {
         
        ObjectNode result = Json.newObject();
        result.put("success", ok);
        if (response instanceof String) {
            result.put("data", (String) response);
        } else {
            result.put("data",(JsonNode) response);
        }
 
        return result;
    }
	
}
