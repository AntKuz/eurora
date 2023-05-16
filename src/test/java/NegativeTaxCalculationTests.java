import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NegativeTaxCalculationTests {
    private final String API_KEY = "e821491a-7aee-4a33-be1a-c89f63550454.boZZvPuLmcbjyim3wFUCYhfcZa37g";

    @Test
    public void testCalculateTaxesWithInvalidKey() {
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
                "  }\n" + "]";
        Response response = RestAssured.given()
                .header("X-Auth-Token", API_KEY)
                .body(requestBody)
                .when()
                .post("https://api.integration.eurora.com/customs-calculator/v1/shopping-cart");

//        Response status check (at some point it returns 400, probably a bug since the response is correct)
        assertEquals(401, response.getStatusCode());

    }
    }