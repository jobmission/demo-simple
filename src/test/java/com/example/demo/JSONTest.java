package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class JSONTest {


    @Ignore
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

        if (phoneNode.isArray()) {
            Iterator<JsonNode> elements = phoneNode.elements();
            while (elements.hasNext()) {
                JsonNode jsonNode = elements.next();
                System.out.println("phone:" + jsonNode.asText());
            }
        }

    }
}
