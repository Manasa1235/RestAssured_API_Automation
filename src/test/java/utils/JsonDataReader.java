
package utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

/**
 * JsonDataReader
 * --------------
 * Utility class for loading test data from JSON files under /resources/test-data.
 * Provides a reusable method to fetch data by key.
 */

public class JsonDataReader {
    private static JsonNode testData;

    static {
        try (InputStream is = JsonDataReader.class.getClassLoader()
                .getResourceAsStream("test-data/bookings.json")) {
            testData = new ObjectMapper().readTree(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data", e);
        }
    }

    public static JsonNode getData(String key) {
        return testData.get(key);
    }
}
