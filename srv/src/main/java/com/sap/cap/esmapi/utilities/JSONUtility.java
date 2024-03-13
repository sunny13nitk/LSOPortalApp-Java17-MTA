package com.sap.cap.esmapi.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;



public class JSONUtility
{
  
   

    public static List<String> getKeysInJsonUsingJsonString
    (String json, ObjectMapper mapper) throws JsonMappingException, JsonProcessingException 
    {

        List<String> keys = new ArrayList<>();
        JsonNode jsonNode = mapper.readTree(json);
        Iterator<String> iterator = jsonNode.fieldNames();
        iterator.forEachRemaining(e -> keys.add(e));
        return keys;
    }

    public static List<String> getKeysInJsonUsingJsonNodeFieldNames(JsonNode jsonNode) throws JsonMappingException, JsonProcessingException
    {

        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = jsonNode.fieldNames();
        iterator.forEachRemaining(e -> keys.add(e));
        return keys;
    }

    public static List<String> getKeysInJsonUsingJsonParser(String json) throws JsonParseException, IOException
    {

        List<String> keys = new ArrayList<>();
        JsonFactory factory = new JsonFactory();
        JsonParser jsonParser = factory.createParser(json);
        while (!jsonParser.isClosed())
         {
            if (jsonParser.nextToken() == JsonToken.FIELD_NAME)
            {
                keys.add((jsonParser.getCurrentName()));
            }
        }
        return keys;
    }

   
       
    
}
