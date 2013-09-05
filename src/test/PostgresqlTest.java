package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

public class PostgresqlTest {

	public void postgresqlTest() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://59.151.121.36:6543/pv_ad?charSet=gbk";
			Connection con = DriverManager.getConnection(url, "postgres",
					"1234");
			Statement st = con.createStatement();
			String sql = "select * from dim_area";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				System.out.print(rs.getInt("area_id") + " ");
				String city = rs.getString("city");
				System.out.println(city);
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PostgresqlTest().postgresqlTest();
	}

	public static String sent(String remoteUrl) {
		if (StringUtils.isEmpty(remoteUrl)) {
			return "";
		}
		HttpURLConnection url_con = null;
		String readLine;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		InputStreamReader in = null;
		URL url = null;
		try {
			url = new URL(remoteUrl);

			url_con = (HttpURLConnection) url.openConnection();
			url_con.setConnectTimeout(1000 * 30);
			url_con.setReadTimeout(1000 * 30);
			in = new InputStreamReader(url_con.getInputStream(), "utf-8");

			br = new BufferedReader(in);
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return sb.toString();
	}

}
