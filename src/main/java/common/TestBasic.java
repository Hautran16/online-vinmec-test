package common;

import model.RelativeModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class TestBasic {
    public WebDriver driver;
    String FILE_CONFIG = "/src/main/resources/config/ProjectConfiguration.properties";
    String DEFAULT_FILE_TEST_CONFIG = "src/test/resources/testData/testConfig/TestConfigLive.properties";
    String FILE_TEST_CONFIG;

    public TestBasic() {
        if(Objects.equals(System.getProperty("env"), "live")) {
            FILE_TEST_CONFIG = "src/test/resources/testData/testConfig/TestConfigLive.properties";
        } else if(Objects.equals(System.getProperty("env"), "uat")) {
            FILE_TEST_CONFIG = "src/test/resources/testData/testConfig/TestConfigUat.properties";
        } else {
            FILE_TEST_CONFIG = DEFAULT_FILE_TEST_CONFIG;
        }
    }

    public void openWebsite(String browser) {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            setupWindowsBrowser(browser);
        } else if (osName.contains("mac")) {
            setupMacBrowser(browser);
        }
        driver.get(readConfigValueByKey("url",FILE_TEST_CONFIG));
    }

    private void setupWindowsBrowser(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", readConfigValueByKey("gecko_driver_win", FILE_CONFIG));
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", readConfigValueByKey("chrome_driver_win", FILE_CONFIG));
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("start-maximized");
            driver = new ChromeDriver(chromeOptions);
        }
    }

    private void setupMacBrowser(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", readConfigValueByKey("gecko_driver_mac", FILE_CONFIG));
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", readConfigValueByKey("chrome_driver_mac", FILE_CONFIG));
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("start-maximized");
            driver = new ChromeDriver(chromeOptions);
        }
    }

    public String getEmailFormTestConfigByEnv(){
        return readConfigValueByKey("email", FILE_TEST_CONFIG);
    }

    public String getPasswordFormTestConfigByEnv(){
        return readConfigValueByKey("password", FILE_TEST_CONFIG);
    }

    public String readConfigValueByKey(String key, String fileConfig) {
        String resultValue = "";

        Properties properties = new Properties();
        InputStream inputStream = null;
        String currentDir = System.getProperty("user.dir");
        try {
            inputStream = new FileInputStream(currentDir + "/" + fileConfig);
            properties.load(inputStream);
            resultValue = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultValue;
    }

    public void closeBrowser() {
        driver.quit();// close toàn bộ cửa sổ
    }

    public void scrollBrowser() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,250)");
    }

    public void maximizeBrowser() {
        driver.manage().window().maximize();
    }

    public void setSizeBrowser() {
        Dimension dm = new Dimension(700, 1000);
        // Setting the current window to that dimension
        driver.manage().window().setSize(dm);
    }

    public void scrollToElement(By element) {
        WebElement iframe = driver.findElement(element);
//	        new Actions(driver)      Lệnh sroll của selenium.
//	                .moveToElement(iframe)
//	                .perform();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", iframe); // Dung js
    }

    public ArrayList<String> getColumnValuesList(By columnLocator) {
        ArrayList<String> columnValuesList = new ArrayList<String>();
        List<WebElement> columnElements = driver.findElements(columnLocator);
        for (WebElement e : columnElements) {// for each => chỉ áp dụng cho danh sách
            String columnValue = e.getText();
            columnValuesList.add(columnValue);
        }

        return columnValuesList;
    }

    public Object[][] getTestData(String excelFilePath, String sheetName) throws IOException {
        FileInputStream inputStream = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        int columnCount = sheet.getRow(0).getLastCellNum();
        Object[][] testData = new Object[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1);
            for (int j = 0; j < columnCount; j++) {
                if(row.getCell(j) == null){
                    testData[i][j] = "";
                } else {
                    Cell cell = row.getCell(j);
                    testData[i][j] = cell.toString();
                }
            }
        }
        workbook.close();
        inputStream.close();

        return testData;
    }

    public Long getTimeOutFromFileConfig(){
        String timeOut = readConfigValueByKey("time_out", FILE_TEST_CONFIG);
        long time_out = Long.parseLong(timeOut);
        return time_out;
    }

    public String[] getMonths(){
        String[] months = {
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec",
        };

        return months;
    }

    public Object[][] getDataFromExcelObject(String excelFilePath, String sheetName, Class targetClass) throws IOException{
        List<RelativeModel> dataList = mapExcelToObject(excelFilePath, sheetName, targetClass);
        Object[][] data = new Object[dataList.size()][1];

        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);
        }

        return data;
    }

    public <T> List<T> mapExcelToObject(String filePath, String sheetName, Class<T> targetClass) throws IOException {
        List<T> objects = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        Iterator<Row> rowIterator = sheet.iterator();
        if (rowIterator.hasNext()) {
            Row headerRow = rowIterator.next();
            List<String> columnNames = getColumnNames(headerRow);

            while (rowIterator.hasNext()) {
                Row dataRow = rowIterator.next();
                T object = createInstance(targetClass);

                for (int i = 0; i < columnNames.size(); i++) {
                    Cell cell = dataRow.getCell(i);
                    String columnName = columnNames.get(i);
                    setFieldValue(object, columnName, cell);
                }

                objects.add(object);
            }
        }

        workbook.close();
        fileInputStream.close();

        return objects;
    }

    private static List<String> getColumnNames(Row headerRow) {
        List<String> columnNames = new ArrayList<>();
        Iterator<Cell> cellIterator = headerRow.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            columnNames.add(cell.getStringCellValue());
        }

        return columnNames;
    }

    private static <T> T createInstance(Class<T> targetClass) {
        try {
            return targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of target class.", e);
        }
    }

    private static <T> void setFieldValue(T object, String fieldName, Cell cell) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (cell != null) {
                if (field.getType() == String.class) {
                    if (cell.getCellType() == CellType.STRING) {
                        field.set(object, cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        field.set(object, String.valueOf(cell.getNumericCellValue()));
                    }
                } else if (field.getType() == int.class || field.getType() == Integer.class) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        field.set(object, (int) cell.getNumericCellValue());
                    }
                }
            } else {
                if (field.getType() == String.class) {
                    field.set(object, "");
                } else if (field.getType() == int.class || field.getType() == Integer.class) {
                    field.set(object, 0);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field value.", e);
        }
    }

}