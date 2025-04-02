package com.example.test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Utility class for loading mock data from JSON files in unit tests.
 */
public class JsonTestUtils {
    
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    
    /**
     * Loads a single object from a JSON file.
     *
     * @param resourcePath The path to the JSON file (relative to the resources folder)
     * @param clazz The class type to deserialize to
     * @param <T> The type parameter
     * @return An instance of the specified class with data from the JSON file
     * @throws RuntimeException If there's an error reading or parsing the file
     */
    public static <T> T loadObject(String resourcePath, Class<T> clazz) {
        try (InputStream inputStream = JsonTestUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error loading mock from " + resourcePath, e);
        }
    }
    
    /**
     * Loads a list of objects from a JSON file containing an array.
     *
     * @param resourcePath The path to the JSON file (relative to the resources folder)
     * @param elementClass The class type of the list elements
     * @param <T> The type parameter
     * @return A list of instances of the specified class with data from the JSON file
     * @throws RuntimeException If there's an error reading or parsing the file
     */
    public static <T> List<T> loadList(String resourcePath, Class<T> elementClass) {
        try (InputStream inputStream = JsonTestUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            return objectMapper.readValue(
                    inputStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, elementClass)
            );
        } catch (IOException e) {
            throw new RuntimeException("Error loading mock list from " + resourcePath, e);
        }
    }
    
    /**
     * Converts a Java object to its JSON string representation.
     *
     * @param object The object to convert to JSON
     * @return JSON string representation of the object
     * @throws RuntimeException If there's an error during serialization
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException("Error converting object to JSON", e);
        }
    }
    
    /**
     * Creates an object from a JSON string.
     *
     * @param json The JSON string
     * @param clazz The class to deserialize to
     * @param <T> The type parameter
     * @return An instance of the specified class
     * @throws RuntimeException If there's an error during deserialization
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON to " + clazz.getSimpleName(), e);
        }
    }
}
