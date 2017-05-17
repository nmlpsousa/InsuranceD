package pt.insuranced.services;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileImportServiceTest {
    @Test
    public void importClients() throws Exception {
        FileImportService fileImportService = new FileImportService();

        fileImportService.importClients("C:\\Workspace\\eclipse\\InsuranceD\\src\\main\\resources\\MOCK_DATA.csv");
    }

}