package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JSONTest {

    @Disabled
    @Test
    public void jsonPointer() throws JsonProcessingException {

        AtomicInteger atomicInteger = new AtomicInteger();

        String json = "{\n" +
            "    \"firstName\": \"John\",\n" +
            "    \"lastName\": \"Doe\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"21 2nd Street\",\n" +
            "      \"city\": \"New York\",\n" +
            "      \"postalCode\": \"10021-3100\",\n" +
            "      \"coordinates\": {\n" +
            "        \"latitude\": 40.7250387,\n" +
            "        \"longitude\": -73.9932568\n" +
            "      }\n" +
            "    },\n" +
            "    \"phone\":[\"139\",\"137\"],\n" +
            "    \"grade\":[\n" +
            "        {\"name\":\"math\",\"score\":99},\n" +
            "        {\"name\":\"english\",\"score\":97}\n" +
            "    ]\n" +
            "  }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        JsonNode firstName = node.at("/firstName");
        System.out.println("firstName:" + firstName.asText());

        JsonNode coordinatesNode = node.at("/address/coordinates");
        System.out.println("coordinatesNode:" + coordinatesNode.isPojo());

        JsonNode xxxxNode = node.at("/xxxx/yyyy");
        System.out.println("xxxxNode:" + xxxxNode.isMissingNode() + "," + xxxxNode.isNull() + "," + xxxxNode.isEmpty());
        JsonNode phoneNode = node.at("/phone");
        JsonNode nameNode = node.at("/grade/0/name");

        System.out.println("name:" + nameNode.asText());

        JsonNode name2Node = node.findPath("name");

        List<JsonNode> values = node.findValues("name");

        List<String> valuesAsText = node.findValuesAsText("name");

        System.out.println("name2Node:" + name2Node.asText());

        if (phoneNode.isArray()) {
            Iterator<JsonNode> elements = phoneNode.elements();
            while (elements.hasNext()) {
                JsonNode jsonNode = elements.next();
                System.out.println("phone:" + jsonNode.asText());
            }
        }

    }

    @Disabled
    @Test
    public void jsonPointer2() throws JsonProcessingException, UnsupportedEncodingException {


        String json = "{\n" +
            "    \"firstName\": \"John\",\n" +
            "    \"lastName\": \"Doe\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"21 2nd Street\",\n" +
            "      \"city\": \"New York\",\n" +
            "      \"postalCode\": \"10021-3100\",\n" +
            "      \"coordinates\": {\n" +
            "        \"latitude\": 40.7250387,\n" +
            "        \"longitude\": -73.9932568\n" +
            "      }\n" +
            "    },\n" +
            "    \"phone\":[\"139\",\"137\"],\n" +
            "    \"grade\":[\n" +
            "        {\"name\":\"math\",\"score\":99},\n" +
            "        {\"name\":\"english\",\"score\":97}\n" +
            "    ]\n" +
            "  }";

        JsonReader reader = Json.createReader(new ByteArrayInputStream(json.getBytes("UTF-8")));

        JsonArray arrays = reader.readArray();

        JsonPointer p = Json.createPointer("/address/city");
        JsonValue name = p.getValue(arrays);

        System.out.println("json value ::" + name);

    }
}
