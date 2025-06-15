package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {
        String path = ".\\testData\\Opencart_LoginData.xlsx";
        ExcelUtility xlutil = new ExcelUtility(path);

        int totalRows = xlutil.getRowCount("Sheet1");
        int totalCols = xlutil.getCellCount("Sheet1", 1); // assuming row 1 has all columns

        String[][] loginData = new String[totalRows][totalCols];

        System.out.println("Reading Excel Data:");
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                String data = xlutil.getCellData("Sheet1", i, j);
                loginData[i - 1][j] = data;
                System.out.print(data + " ");
            }
            System.out.println();
        }

        return loginData;
    }
}
