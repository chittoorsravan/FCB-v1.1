package com.attestationhub.sailpoint.server;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class H2DatabaseExport {
	public static void main(String[] args) {
        String jdbcUrl = "jdbc:h2:file:/data/attestationhub;DB_CLOSE_ON_EXIT=FALSE"; // Update with your database path
        String user = "attestationhub"; // Update with your username
        String password = "attestationhub"; // Update with your password
        String tableName = "Entitlement";
        String outputFileName = "Entitlement-output.sql";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
             Statement stmt = conn.createStatement();
             FileWriter fileWriter = new FileWriter(outputFileName)) {

            String query = "SELECT * FROM " + tableName;
            ResultSet rs = stmt.executeQuery(query);

            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO ").append(tableName).append(" VALUES (");
                for (int i = 1; i <= columnCount; i++) {
                    sb.append("'").append(rs.getString(i)).append("'");
                    if (i < columnCount) sb.append(", ");
                }
                sb.append(");\n");
                fileWriter.write(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
