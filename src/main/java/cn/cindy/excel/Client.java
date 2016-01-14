package cn.cindy.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Client {
	public static void main(String[] args) throws IOException {
		//需要版本一定要另存,只是修改名字是不起作用的
		File file = new File("src/main/java/cn/cindy/excel/student.xls");
//		File file = new File("src/main/java/cn/cindy/excel/student.xlsx");
		
        String excel = file.getAbsolutePath();
        // read the 2010 excel
        List<Student> list1 = new ReadExcel().readExcel(excel);
        if (list1 != null) {
            for (Student student : list1) {
                System.out.println("No. : " + student.getNo() + ", name : " + student.getName() + ", age : " + student.getAge() + ", score : " + student.getScore());
            }
        }
    }
}
