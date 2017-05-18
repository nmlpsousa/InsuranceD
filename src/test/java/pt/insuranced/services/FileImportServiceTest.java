package pt.insuranced.services;

import org.junit.Ignore;
import org.junit.Test;

public class FileImportServiceTest {
    @Test
    @Ignore
    public void importClients() throws Exception {
        FileImportService fileImportService = new FileImportService("postgres");

        fileImportService.importClients("C:\\Workspace\\eclipse\\InsuranceD\\src\\main\\resources\\MOCK_DATA.csv");
    }

}