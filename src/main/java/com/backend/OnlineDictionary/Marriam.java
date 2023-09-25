package com.backend.OnlineDictionary;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Marriam {
    public static void main(String[] args) {
        // JSON data as a string (provided in your question)
        String jsonData = "[\n" +
                "  {\n" +
                "    \"meta\": {\n" +
                "      \"id\": \"voluminous\",\n" +
                "      \"uuid\": \"0d01b967-971f-4ec5-8fe0-10513d29c39b\",\n" +
                "      \"sort\": \"220130400\",\n" +
                "      \"src\": \"collegiate\",\n" +
                "      \"section\": \"alpha\",\n" +
                "      \"stems\": [\n" +
                "        \"voluminous\",\n" +
                "        \"voluminously\",\n" +
                "        \"voluminousness\",\n" +
                "        \"voluminousnesses\"\n" +
                "      ],\n" +
                "      \"offensive\": false\n" +
                "    },\n" +
                "    \"hwi\": {\n" +
                "      \"hw\": \"vo*lu*mi*nous\",\n" +
                "      \"prs\": [\n" +
                "        {\n" +
                "          \"mw\": \"v\\u0259-\\u02c8l\\u00fc-m\\u0259-n\\u0259s\",\n" +
                "          \"sound\": {\n" +
                "            \"audio\": \"volumi02\",\n" +
                "            \"ref\": \"c\",\n" +
                "            \"stat\": \"1\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"fl\": \"adjective\",\n" +
                "    \"def\": [\n" +
                "      {\n" +
                "        \"sseq\": [\n" +
                "          [\n" +
                "            [\n" +
                "              \"sense\",\n" +
                "              {\n" +
                "                \"sn\": \"1 a\",\n" +
                "                \"dt\": [\n" +
                "                  [\n" +
                "                    \"text\",\n" +
                "                    \"{bc}having or marked by great {a_link|volume} or bulk {bc}{sx|large||} \"\n" +
                "                  ],\n" +
                "                  [\n" +
                "                    \"vis\",\n" +
                "                    [\n" +
                "                      {\n" +
                "                        \"t\": \"long {wi}voluminous{/wi} tresses\"\n" +
                "                      }\n" +
                "                    ]\n" +
                "                  ]\n" +
                "                ],\n" +
                "                \"sdsense\": {\n" +
                "                  \"sd\": \"also\",\n" +
                "                  \"dt\": [\n" +
                "                    [\n" +
                "                      \"text\",\n" +
                "                      \"{bc}{sx|full||} \"\n" +
                "                    ],\n" +
                "                    [\n" +
                "                      \"vis\",\n" +
                "                      [\n" +
                "                        {\n" +
                "                          \"t\": \"a {wi}voluminous{/wi} skirt\"\n" +
                "                        }\n" +
                "                      ]\n" +
                "                    ]\n" +
                "                  ]\n" +
                "                }\n" +
                "              }\n" +
                "            ],\n" +
                "            [\n" +
                "              \"sense\",\n" +
                "              {\n" +
                "                \"sn\": \"b\",\n" +
                "                \"dt\": [\n" +
                "                  [\n" +
                "                    \"text\",\n" +
                "                    \"{bc}{sx|numerous||} \"\n" +
                "                  ],\n" +
                "                  [\n" +
                "                    \"vis\",\n" +
                "                    [\n" +
                "                      {\n" +
                "                        \"t\": \"trying to keep track of {wi}voluminous{/wi} slips of paper\"\n" +
                "                      }\n" +
                "                    ]\n" +
                "                  ]\n" +
                "                ]\n" +
                "              }\n" +
                "            ]\n" +
                "          ],\n" +
                "          [\n" +
                "            [\n" +
                "              \"sense\",\n" +
                "              {\n" +
                "                \"sn\": \"2 a\",\n" +
                "                \"dt\": [\n" +
                "                  [\n" +
                "                    \"text\",\n" +
                "                    \"{bc}filling or capable of filling a large volume or several {a_link|volumes} \"\n" +
                "                  ],\n" +
                "                  [\n" +
                "                    \"vis\",\n" +
                "                    [\n" +
                "                      {\n" +
                "                        \"t\": \"a {wi}voluminous{/wi} literature on the subject\"\n" +
                "                      }\n" +
                "                    ]\n" +
                "                  ]\n" +
                "                ]\n" +
                "              }\n" +
                "            ],\n" +
                "            [\n" +
                "              \"sense\",\n" +
                "              {\n" +
                "                \"sn\": \"b\",\n" +
                "                \"dt\": [\n" +
                "                  [\n" +
                "                    \"text\",\n" +
                "                    \"{bc}writing or speaking much or at great length \"\n" +
                "                  ],\n" +
                "                  [\n" +
                "                    \"vis\",\n" +
                "                    [\n" +
                "                      {\n" +
                "                        \"t\": \"a {wi}voluminous{/wi} correspondent\"\n" +
                "                      }\n" +
                "                    ]\n" +
                "                  ]\n" +
                "                ]\n" +
                "              }\n" +
                "            ]\n" +
                "          ],\n" +
                "          [\n" +
                "            [\n" +
                "              \"sense\",\n" +
                "              {\n" +
                "                \"sn\": \"3\",\n" +
                "                \"dt\": [\n" +
                "                  [\n" +
                "                    \"text\",\n" +
                "                    \"{bc}consisting of many folds, coils, or convolutions {bc}{sx|winding|winding:2|}\"\n" +
                "                  ]\n" +
                "                ]\n" +
                "              }\n" +
                "            ]\n" +
                "          ]\n" +
                "        ]\n" +
                "      }\n" +
                "    ],\n" +
                "    \"uros\": [\n" +
                "      {\n" +
                "        \"ure\": \"vo*lu*mi*nous*ly\",\n" +
                "        \"fl\": \"adverb\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ure\": \"vo*lu*mi*nous*ness\",\n" +
                "        \"fl\": \"noun\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"et\": [\n" +
                "      [\n" +
                "        \"text\",\n" +
                "        \"Late Latin {it}voluminosus{/it}, from Latin {it}volumin-, volumen{/it}\"\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"date\": \"1611{ds||3||}\",\n" +
                "    \"shortdef\": [\n" +
                "      \"having or marked by great volume or bulk : large; also : full\",\n" +
                "      \"numerous\",\n" +
                "      \"filling or capable of filling a large volume or several volumes\"\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON data into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonData);

            // Access specific fields within the JSON structure
            String inputWord = jsonNode.get(0).get("meta").get("id").asText();
            String pronunciation = jsonNode.get(0).get("hwi").get("hw").asText();
            String wordType = jsonNode.get(0).get("fl").asText();
            JsonNode definitions = jsonNode.get(0).get("def");

            // Print the parsed information
            System.out.println("Input Word: " + inputWord);
            System.out.println("Pronunciation: " + pronunciation);
            System.out.println("Word Type: " + wordType);

            // Iterate through definitions and examples
            for (JsonNode definition : definitions) {
                String definitionText = definition.get("sseq").get(0).get(0).get(1).get("dt").get(0).get(1).asText();
                System.out.println("Definition: " + definitionText);

                JsonNode examples = definition.get("sseq").get(0).get(0).get(1).get("dt").get(1).get(1);
                for (JsonNode example : examples) {
                    String exampleText = example.get("t").asText();
                    System.out.println("Example: " + exampleText);
                }
            }
            System.out.println("=============================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
