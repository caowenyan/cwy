package cn.cindy.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * .
 */
public class CreateExcelTest {
    public static void main(String[] args) throws Exception{
        create();
    }
    //创建excel表格
    public static void create() throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("first");

        Map map = init();
        Set<String> set = map.keySet();
        int line = 0;
        for(String key:set){
            XSSFRow row = sheet.createRow(line++);
            Object[]objs = (Object[])map.get(key);
            for (int i = 0; i < objs.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(""+objs[i]);
            }
        }

        FileOutputStream outputStream = new FileOutputStream(new File("CreateExcelTest.xlsx"));
        workbook.write(outputStream);
    }

    public static Map init(){

        Map< String, Object[] > empinfo =
                new TreeMap< String, Object[] >();
        empinfo.put( "1", new Object[] {
                "EMP ID", "EMP NAME", "DESIGNATION" });
        empinfo.put( "2", new Object[] {
                "tp01", "Gopal", "Technical Manager" });
        empinfo.put( "3", new Object[] {
                "tp02", "Manisha", "Proof Reader" });
        empinfo.put( "4", new Object[] {
                "tp03", "Masthan", "Technical Writer" });
        empinfo.put( "5", new Object[] {
                "tp04", "Satish", "Technical Writer" });
        empinfo.put("6", new Object[]{
                "tp05", "Krishna", "Technical Writer"});
        return empinfo;
    }
}
