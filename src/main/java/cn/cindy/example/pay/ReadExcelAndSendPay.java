package cn.cindy.example.pay;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelAndSendPay {
	private static String path = "src/main/resources/pay.xlsx";
	private static HtmlEmail email = null;
	private static StringBuffer head = new StringBuffer();// 自己给自己挖坑,没有初始化,一直不知道哪错了
	private static int nameNum = 0;
	private static int mailNum = 0;
	private static String name = null;
	private static String mail = null;

	public static void init() throws EmailException, ConfigurationException {
		Configuration config = new PropertiesConfiguration("src/main/resources/email.properties");

		// Create the email message
		email = new HtmlEmail();
		email.setCharset(config.getString("chartset"));// 字符编码
		email.setAuthentication(config.getString("user"), config.getString("password"));// 设置授权信息
		email.setHostName(config.getString("hostName"));// pop3(接受)和smtp(发送)服务,邮件的传输协议
		email.setFrom(config.getString("fromEmail"), config.getString("fromUser"));
		email.setSubject("工资条(" + getYearMon() + ")");

	}

	public static void main(String[] args) {
		try {
			readPayAndSend();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getYearMon() {
		SimpleDateFormat format = new SimpleDateFormat("YYYYMM");
		return format.format(new Date());
	}

	/**
	 * 
	 */
	public static void readPayAndSend() throws Exception {
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		// read head
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		XSSFRow xssfRow = xssfSheet.getRow(0);
		int len = xssfRow.getLastCellNum();
		if (xssfRow != null) {
			for (int i = 0; i < len; i++) {
				
				if ("姓名".equals(xssfRow.getCell(i).getStringCellValue()))
					nameNum = i;

				if ("邮箱".equals(xssfRow.getCell(i).getStringCellValue())){
					mailNum = i;
					continue;
				}
				head.append("<td>" + xssfRow.getCell(i).getStringCellValue() + "</td>");
			}
		}
		// read body
		for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			xssfRow = xssfSheet.getRow(rowNum);
			if (xssfRow != null) {
				StringBuffer body = new StringBuffer();
				for (int i = 0; i < len; i++) {
					if (nameNum == i)
						name = xssfRow.getCell(i).getStringCellValue();
					if (mailNum == i){
						mail = xssfRow.getCell(i).getStringCellValue();
						continue;
					}
					body.append("<td>" + xssfRow.getCell(i) + "</td>");
				}
				Thread.sleep(10);
				send(body.toString());
			}
		}
	}

	public static void send(String body) {
		try {
			init();
			email.addTo(mail, name);
			StringBuffer sb = new StringBuffer();
			sb.append(grtStyle());
			sb.append("Dear " + name + "：<br>");
			sb.append("     你好，以下是" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "月工资信息，请查收！<br><br>");
			sb.append("<table class='datalist'>");
			sb.append("<tr>" + head + "</tr>");

			sb.append("<tr>" + body + "</tr>");

			sb.append("</table>");
			sb.append("<br>如有问题请与我联系，我的邮箱：xxx@xxx.xx");

			// set the html message
			email.setHtmlMsg(sb.toString());

			// send the email
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String grtStyle(){
		return "<style>.datalist{border:1px solid #0058a3;	/* 表格边框 */"
				+ "border-collapse:collapse;	/* 边框重叠 */" + "background-color:#eaf5ff;	/* 表格背景色 */"
				+ "font-size:14px;}" + ".datalist td{border:1px solid #0058a3;	/* 单元格边框 */"
				+ "text-align:left;padding-top:4px; " + "padding-bottom:4px;" + "padding-left:10px; "
				+ "padding-right:10px;}</style>";
	}
}
