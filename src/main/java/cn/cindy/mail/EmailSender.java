package cn.cindy.mail;

import java.util.Calendar;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.HtmlEmail;

/**
 * 邮箱              POP3服务器（端口995） SMTP服务器（端口465或587） 
 * qq.com         pop.qq.com                   smtp.qq.com
 * sina.com       pop.sina.com                 smtp.sina.com
 */
public class EmailSender {
	public static void main(String[] args) {
		try {
			//load configuration about email
			//不知道如何使用中文,找不到设置字符集的地方
			Configuration config = new PropertiesConfiguration("src/main/resources/email.properties");
			
			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setCharset(config.getString("chartset"));//字符编码
			email.setAuthentication(config.getString("user"), config.getString("password"));// 设置授权信息
			email.setHostName(config.getString("hostName"));//pop3(接受)和smtp(发送)服务,邮件的传输协议
			email.addTo(config.getString("toEmail"), config.getString("toUser"));
			email.setFrom(config.getString("fromEmail"), config.getString("fromUser"));
			email.setSubject("工资条");

			StringBuffer sb = new StringBuffer();
			sb.append("<style>.datalist{border:1px solid #0058a3;	/* 表格边框 */"
					+ "border-collapse:collapse;	/* 边框重叠 */" + "background-color:#eaf5ff;	/* 表格背景色 */"
					+ "font-size:14px;}" + ".datalist td{border:1px solid #0058a3;	/* 单元格边框 */"
					+ "text-align:left;padding-top:4px; " + "padding-bottom:4px;" + "padding-left:10px; "
					+ "padding-right:10px;}</style>");
			sb.append("Dear xxx ：<br>");
			sb.append("     你好，以下是" + (Calendar.getInstance().get(Calendar.MONTH)+1)+ "月工资信息，请查收！<br><br>");
			sb.append("<table class='datalist'>");
			sb.append("<tr>");

			sb.append("<td>姓名</td>");
			sb.append("<td>基本工资</td>");
			sb.append("<td>津贴</td>");
			sb.append("<td>合计</td>");
			sb.append("<td>补发</td>");
			sb.append("<td>请假/其他扣款</td>");
			sb.append("<td>小计</td>");
			sb.append("<td>养老</td>");
			sb.append("<td>失业</td>");
			sb.append("<td>医疗</td>");
			sb.append("<td>公积金</td>");
			sb.append("<td>社保/公积金合计</td>");
			sb.append("<td>补贴</td>");
			sb.append("<td>计税工资</td>");
			sb.append("<td>个税</td>");
			sb.append("<td>实发工资</td>");
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td>***</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("<td>**.00</td>");
			sb.append("<td>***.00</td>");
			sb.append("</tr>");

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
}