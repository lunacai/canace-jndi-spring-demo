package com.canace.jndi;


//import com.pamirs.pradar.Pradar;
import org.springframework.web.bind.annotation.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


@RestController
public class ControllerDemo {

    private Connection conn = null;

    @GetMapping("/get")
    public String index() {
//        System.out.println(Pradar.isClusterTest());
        Statement stmt = getConnecting();
        String sql = "SELECT * FROM user limit 10";
        Map<String, String> map = new HashMap<>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String content = rs.getString("content");
                System.out.print("id: " + id);
                System.out.println(", name: " + name);
                map.put("id", String.valueOf(id));
                map.put("name", name);
                map.put("content", content);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map.toString();
    }

    @PostMapping("/addData")
    public String add(@RequestBody userMedul userMedul) {
//        System.out.println(Pradar.isClusterTest());
        Statement stmt = getConnecting();
        String sql = "insert into user(`name`,`content`) value('" + userMedul.getUsername() + "','" + userMedul.getContent() + "');";
        try {
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "add successful";
    }

    @PostMapping("/updateData")
    public String update(@RequestBody userMedul userMedul) {
//        System.out.println(Pradar.isClusterTest());
        Statement stmt = getConnecting();
        String sql = "update user set `name`='"+ userMedul.getUsername()+"',`content`='" + userMedul.getContent() + "' where id="+userMedul.getId()+";";
        try {
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "update successful";
    }

    @DeleteMapping("/delete/{ids}")
    public String delete(@PathVariable Long ids) {
//        System.out.println(Pradar.isClusterTest());
        Statement stmt = getConnecting();
        String sql = "delete from user where id="+ids+";";
        try {
            System.out.println(sql);
            stmt.execute(sql);
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "delete successful";
    }

    public Statement getConnecting() {
        //根据资源名称搜索
        Context ctx = null;
        try {
            ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbcMydb");
            conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            return stmt;
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
