package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconn {
	
	Connection conn=null;
	
	String coninfo = "jdbc:oracle:thin:@127.0.0.1:1521:xe"; //�������� : �ܿ��� �ϴ°�
	String idinfo = "system";
	String pwdinfo = "1234";
	
	
	public Connection getConnection() {                                       //DB�� �����ϴ� Ŭ��
		try {
			                                       
			Class.forName("oracle.jdbc.driver.OracleDriver");                 //����̹����� OracleDriver�� ã�´�
			conn = DriverManager.getConnection(coninfo, idinfo, pwdinfo);     //DriverManagerŬ�������� ���������� ������ �����Ѵ� 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
