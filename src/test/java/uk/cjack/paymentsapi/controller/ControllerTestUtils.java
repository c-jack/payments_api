package uk.cjack.paymentsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Common setup and methods for the controller tests
 */
public class ControllerTestUtils
{
    @Autowired
    protected MockMvc mockMvc;

    /**
     * Base path for the JSON resource files
     */
    public static final String TEST_RESOURCES_PATH = "src/test/resources/";

    /**
     * Reads the provided JSON file from the test resources
     *
     * @param file the name of the file (with extension) to be read
     * @return the JSON as a {@link String}
     * @throws IOException if the file can't be read (doesn't exist, etc)
     */
    protected String readJson( final String file ) throws IOException
    {
        return new String( Files.readAllBytes(Paths.get(TEST_RESOURCES_PATH + file)));
    }
}
