package database;

import java.sql.*;

public class Queries {

    private final DBConnection connection = new DBConnection();

    public Queries() {
    }

    public static String getArticles() {
        Connection conn = DBConnection.getConnection();
        String articles = "";

        try {
            PreparedStatement query = conn.prepareStatement(
                        "SELECT * FROM articles;");

            ResultSet rs = query.executeQuery();
            
            // Now interate through the children taking names and addresses
            if (rs.isBeforeFirst()) {//Does the child exist?
                while (rs.next()) {
                    articles = rs.getString("title");
                }
            } else {
                articles = "Articles not found.";
            }
            return articles;
        } catch (SQLException sqlE) {
            System.out.println("SQL code is broken");

        }

        //Now, just tidy up by closing connection
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return articles;
    }
    
    public static String getChildTable(int id) {
        Connection conn = DBConnection.getConnection();
        String table = "";

        try {
            PreparedStatement query = conn.prepareStatement(
                    "SELECT c.cid, name, address, g.gid, g.description \n"
                    + "FROM child c \n"
                    + "LEFT JOIN present p \n"
                    + "ON c.cid = p.cid \n"
                    + "LEFT JOIN gift g \n"
                    + "ON p.gid = g.gid \n"
                    + "WHERE c.cid = ?;");
            query.setInt(1, id);

            ResultSet rs = query.executeQuery();

            // Now interate through the children taking names and addresses
            if (rs.isBeforeFirst()) {//Does the child exist?
                table = "<table>"
                        + "<tr><th>Child ID</th><th>Name</th><th>Address</th><th>Gift ID</th><th>Description</th></tr>"
                        + "";
                boolean firstResult = true;
                while (rs.next()) {
                    if (firstResult) {
                        table += "<tr>"
                                + "<td rowspan=\"0\">" + rs.getInt("cid") + "</td>"
                                + "<td rowspan=\"0\">" + rs.getString("name") + "</td>"
                                + "<td rowspan=\"0\">" + rs.getString("address") + "</td>";

                        int gid = rs.getInt("gid");
                        if (gid == 0) {//Child exists and has no presents
                            table += "<td colspan=\"2\">No Presents</td>";
                        } else {
                            table += "<td>" + gid + "</td>"
                                    + "<td>" + rs.getString("description") + "</td>";
                        }
                        table += "</tr>";
                    } else {
                        table += "<tr>"
                                + "<td></td>"
                                + "<td></td>"
                                + "<td></td>";

                        int gid = rs.getInt("gid");
                        if (gid == 0) {//Child exists and has no presents
                            table += "<td colspan=\"2\">No Presents</td>";
                        } else {
                            table += "<td>" + gid + "</td>"
                                    + "<td>" + rs.getString("description") + "</td>";
                        }
                        table += "</tr>";
                    }
                    firstResult = false;
                }
                table += "</table>";
            } else {
                table = "<div id=\"child_not_found_error\">Child with ID = " + id + " not found.</div>";
            }
            return table;
        } catch (SQLException sqlE) {
            System.out.println("SQL code is broken");

        }

        //Now, just tidy up by closing connection
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return table;
    }

    public static String getHelperTable(int id) {
        Connection conn = DBConnection.getConnection();
        String table = "";

        try {
            PreparedStatement query = conn.prepareStatement(
                    "SELECT slh.slhid, slh.name as slh_name, c.name as child_name, c.address, g.gid, description\n"
                    + "FROM santas_little_helper slh\n"
                    + "LEFT JOIN present p\n"
                    + "ON slh.slhid = p.slhid\n"
                    + "LEFT JOIN child c\n"
                    + "ON c.cid = p.cid\n"
                    + "LEFT JOIN gift g\n"
                    + "ON g.gid = p.gid\n"
                    + "WHERE slh.slhid = ?;");
            query.setInt(1, id);

            ResultSet rs = query.executeQuery();
            if (rs.isBeforeFirst()) {//Does the child exist?
                table = "<table>"
                        + "<tr><th>Helper ID</th><th>Helper Name</th><th>Child Name</th><th>Child Address</th><th>Gift ID</th><th>Description</th></tr>"
                        + "";
                boolean firstResult = true;
                while (rs.next()) {
                    if (firstResult) {
                        table += "<tr>"
                                + "<td rowspan=\"0\">" + rs.getInt("slhid") + "</td>"
                                + "<td rowspan=\"0\">" + rs.getString("slh_name") + "</td>";

                        int gid = rs.getInt("gid");
                        if (gid == 0) {//Child exists and has no presents
                            table += "<td colspan=\"4\">No presents to deliver</td>";
                        } else {
                            table += "<td>" + rs.getString("child_name") + "</td>"
                                    + "<td>" + rs.getString("address") + "</td>"
                                    + "<td>" + gid + "</td>"
                                    + "<td>" + rs.getString("description") + "</td>";
                        }
                        table += "</tr>";
                    } else {
                        table += "<tr>"
                                + "<td></td>"
                                + "<td></td>";

                        int gid = rs.getInt("gid");
                        if (gid == 0) {//Helper exists and has no presents
                            table += "<td colspan=\"4\">No presents to deliver</td>";
                        } else {
                            table += "<td>" + rs.getString("child_name") + "</td>"
                                    + "<td>" + rs.getString("address") + "</td>"
                                    + "<td>" + gid + "</td>"
                                    + "<td>" + rs.getString("description") + "</td>";
                        }
                        table += "</tr>";
                    }
                    firstResult = false;
                }
                table += "</table>";
            } else {
                table = "<div id=\"helper_not_found_error\">Helper with ID = " + id + " not found.</div>";
            }
            return table;
        } catch (SQLException sqlE) {
            System.out.println("SQL code is broken");

        }

        //Now, just tidy up by closing connection
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return table;
    }
}
