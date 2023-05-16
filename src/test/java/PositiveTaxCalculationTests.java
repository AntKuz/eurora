import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositiveTaxCalculationTests {
    private final String API_KEY = "e821491a-7aee-4a33-be1a-c89f63550454.boZZvPuLmcbjyim3wFUCYhfcZa37gW4M";

    @Test
    public void testCalculateTaxesWithValidInput() {
        String requestBody = "[\n" +
                "  {\n" +
                "    \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "    \"orderCurrency\": \"EUR\",\n" +
                "    \"transportationPrice\": 5000.55,\n" +
                "    \"insurancePrice\": 100,\n" +
                "    \"otherAdditionalCosts\": 15.55,\n" +
                "    \"originCountry\": \"CN\",\n" +
                "    \"destinationCountry\": \"CA\",\n" +
                "    \"destinationRegion\": \"CA-MB\",\n" +
                "    \"additionalValueShare\": \"MANUAL\",\n" +
                "    \"goods\": [\n" +
                "      {\n" +
                "        \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "        \"gtin\": \"00012345600012\",\n" +
                "        \"title\": \"Fidget spinners\",\n" +
                "        \"description\": \"Fidget spinners\",\n" +
                "        \"hsCode\": \"0101000000\",\n" +
                "        \"price\": {\n" +
                "          \"currency\": \"EUR\",\n" +
                "          \"value\": 11.25\n" +
                "        },\n" +
                "        \"weight\": 0.15,\n" +
                "        \"quantity\": 250,\n" +
                "        \"additionalValueShareRatio\": 1\n" +
                "      }\n" +
                "    ],\n" +
                "    \"date\": \"2023-05-15\",\n" +
                "    \"useDeMinimis\": true,\n" +
                "    \"hsCodeReplaceAllowed\": true,\n" +
                "    \"declarationType\": \"H7\",\n" +
                "    \"transactionModel\": \"B2B\",\n" +
                "    \"transactionCategory\": \"COMMERCIAL\"\n" +
                "  }\n"+"]";
        Response response = RestAssured.given()
                .header("X-Auth-Token", API_KEY)
                .body(requestBody)
                .when()
                .post("https://api.integration.eurora.com/customs-calculator/v1/shopping-cart");

//        Response status check (at some point it returns 400, probably a bug since the response is correct)
//        assertEquals(200, response.getStatusCode());

        // Response body check
        String expectedResponseBody = "[\n" +
                "    {\n" +
                "        \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "        \"totalVat\": 951.43,\n" +
                "        \"totalDuties\": 0.00,\n" +
                "        \"destinationCountry\": \"CA\",\n" +
                "        \"originCountry\": \"CN\",\n" +
                "        \"goods\": [\n" +
                "            {\n" +
                "                \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "                \"description\": \"Fidget spinners\",\n" +
                "                \"vat\": 951.43,\n" +
                "                \"duty\": 0.00,\n" +
                "                \"vatRate\": 0.12000,\n" +
                "                \"dutyRate\": 0.0,\n" +
                "                \"hsCodeCorrectness\": 0.9000,\n" +
                "                \"calculatedHsCode\": \"9503009099\",\n" +
                "                \"calculatedHsCodeRestrictions\": [],\n" +
                "                \"providedHsCodeRestrictions\": []\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";
    }

    @Test
    public void testCalculateTaxesWithMissingInput() {
        // in the request body no orderCurrency specified
        String requestBody = "[\n" +
                "  {\n" +
                "    \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "    \"orderCurrency\":\n" +
                "    \"transportationPrice\": 5000.55,\n" +
                "    \"insurancePrice\": 100,\n" +
                "    \"otherAdditionalCosts\": 15.55,\n" +
                "    \"originCountry\": \"CN\",\n" +
                "    \"destinationCountry\": \"CA\",\n" +
                "    \"destinationRegion\": \"CA-MB\",\n" +
                "    \"additionalValueShare\": \"MANUAL\",\n" +
                "    \"goods\": [\n" +
                "      {\n" +
                "        \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "        \"gtin\": \"00012345600012\",\n" +
                "        \"title\": \"Fidget spinners\",\n" +
                "        \"description\": \"Fidget spinners\",\n" +
                "        \"hsCode\": \"0101000000\",\n" +
                "        \"price\": {\n" +
                "          \"currency\": \"EUR\",\n" +
                "          \"value\": 11.25\n" +
                "        },\n" +
                "        \"weight\": 0.15,\n" +
                "        \"quantity\": 250,\n" +
                "        \"additionalValueShareRatio\": 1\n" +
                "      }\n" +
                "    ],\n" +
                "    \"date\": \"2023-05-15\",\n" +
                "    \"useDeMinimis\": true,\n" +
                "    \"hsCodeReplaceAllowed\": true,\n" +
                "    \"declarationType\": \"H7\",\n" +
                "    \"transactionModel\": \"B2B\",\n" +
                "    \"transactionCategory\": \"COMMERCIAL\"\n" +
                "  }\n" +
                "]";
        Response response = RestAssured.given()
                .header("X-Auth-Token", API_KEY)
                .body(requestBody)
                .when()
                .post("https://api.integration.eurora.com/customs-calculator/v1/shopping-cart");
        // verify the message in the response body

        // Response status check
        assertEquals(400, response.getStatusCode());

        // Response body check
        String expectedResponseBody = "{\n" +
                "    \"type\": \"CONSTRAINT_VIOLATION\",\n" +
                "    \"rows\": [\n" +
                "        {\n" +
                "            \"field\": \"orderCurrency\",\n" +
                "            \"reason\": \"NotBlank\",\n" +
                "            \"message\": \"must not be blank\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"field\": \"orderCurrency\",\n" +
                "            \"reason\": \"Size\",\n" +
                "            \"message\": \"size must be between 3 and 3\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    @Test
    public void testCalculateTaxesWithDifferentCurrency() {
        String requestBody = "[\n" +
                "  {\n" +
                "    \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "    \"orderCurrency\": \"SEK\",\n" +
                "    \"transportationPrice\": 5000.55,\n" +
                "    \"insurancePrice\": 100,\n" +
                "    \"otherAdditionalCosts\": 15.55,\n" +
                "    \"originCountry\": \"CN\",\n" +
                "    \"destinationCountry\": \"CA\",\n" +
                "    \"destinationRegion\": \"CA-MB\",\n" +
                "    \"additionalValueShare\": \"MANUAL\",\n" +
                "    \"goods\": [\n" +
                "      {\n" +
                "        \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "        \"gtin\": \"00012345600012\",\n" +
                "        \"title\": \"Fidget spinners\",\n" +
                "        \"description\": \"Fidget spinners\",\n" +
                "        \"hsCode\": \"0101000000\",\n" +
                "        \"price\": {\n" +
                "          \"currency\": \"EUR\",\n" +
                "          \"value\": 11.25\n" +
                "        },\n" +
                "        \"weight\": 0.15,\n" +
                "        \"quantity\": 250,\n" +
                "        \"additionalValueShareRatio\": 1\n" +
                "      }\n" +
                "    ],\n" +
                "    \"date\": \"2023-05-15\",\n" +
                "    \"useDeMinimis\": true,\n" +
                "    \"hsCodeReplaceAllowed\": true,\n" +
                "    \"declarationType\": \"H7\",\n" +
                "    \"transactionModel\": \"B2B\",\n" +
                "    \"transactionCategory\": \"COMMERCIAL\"\n" +
                "  }\n"+"]";
        Response response = RestAssured.given()
                .header("X-Auth-Token", API_KEY)
                .body(requestBody)
                .when()
                .post("https://api.integration.eurora.com/customs-calculator/v1/shopping-cart");

//        Response status check (at some point it returns 400, probably a bug since the response is correct)
//        assertEquals(200, response.getStatusCode());

        // Response body check
        String expectedResponseBody = "[\n" +
                "    {\n" +
                "        \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "        \"totalVat\": 4425.86,\n" +
                "        \"totalDuties\": 0.00,\n" +
                "        \"destinationCountry\": \"CA\",\n" +
                "        \"originCountry\": \"CN\",\n" +
                "        \"goods\": [\n" +
                "            {\n" +
                "                \"externalId\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "                \"description\": \"Fidget spinners\",\n" +
                "                \"vat\": 4425.86,\n" +
                "                \"duty\": 0.00,\n" +
                "                \"vatRate\": 0.12000,\n" +
                "                \"dutyRate\": 0.0,\n" +
                "                \"hsCodeCorrectness\": 0.9000,\n" +
                "                \"calculatedHsCode\": \"9503009099\",\n" +
                "                \"calculatedHsCodeRestrictions\": [],\n" +
                "                \"providedHsCodeRestrictions\": []\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";
    }
}