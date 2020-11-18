package ATM;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CustomerDAO {
    private Connection conn = null;
    private java.sql.PreparedStatement p = null;

    public CustomerDAO() {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?characterEncoding=utf8", "root", "1501719577");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String adminLogin(String mypwd, String account) {
        String sql = "select * from admin where password = ? and account = ?";
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, mypwd);
            p.setString(2, account);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return rs.getString("account");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    // 登录功能， 输入用户名和密码
    // 返回用户id
    public String login(String mypwd, String account) {
        String sql = "select * from user where password = ? and account = ?";
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, mypwd);
            p.setString(2, account);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return rs.getString("account");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public Customer query(String account) {
        Customer cust = new Customer();
        try {
            String sql = "select * from user where account = ?";
            p = conn.prepareStatement(sql);
            p.setString(1, account);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                cust.setId(rs.getInt("id"));
                cust.setMoney(rs.getInt("money"));
                cust.setAccount(rs.getString("account"));
                cust.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return cust;

    }

    public void update_get(String account, int money) {
        String sql = "select * from user where account = ?";
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, account);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                if (rs.getInt("money") < money) {
                    System.out.println("余额不足！");
                } else {
                    sql = "update user set money = money - ? where account = ?";
                    p = conn.prepareStatement(sql);
                    p.setInt(1, money);
                    p.setString(2, account);
                    p.executeUpdate();
                    resetMoney(money,2);
                    System.out.println("取款成功！");
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void addMoney(int money){
        String sql = "update init set money = money + ?";
        try {
            p = conn.prepareStatement(sql);
            p.setInt(1, money);
            p.executeUpdate();
            System.out.println("添加金钱"+money+"元成功");
        } catch (Exception ignore) {
        }
    }

    /**
     * 存款
     */
    public void deposit(String account, int money) {
        String sql = "update user set money = money + ? where account = ?";
        try {
            p = conn.prepareStatement(sql);
            p.setInt(1, money);
            p.setString(2, account);
            p.executeUpdate();
            log(account,"存款"+money+"元","存款");
            resetMoney(money,1);
            System.out.println("存款成功！");
        } catch (Exception ignored) {
        }
    }

    /**
     * 转账
     */
    public void transfer(String myAccount, String toAccount, int money) {
        try {
            String sql = "select * from user where account = ?";
            p = conn.prepareStatement(sql);
            p.setString(1, toAccount);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                sql = "select * from user where account = ?";
                p = conn.prepareStatement(sql);
                p.setString(1, myAccount);
                rs = p.executeQuery();
                if (rs.next()) {
                    if (rs.getInt("money") < money) {
                        System.out.println("余额不足！");
                        return;
                    } else {
                        sql = "update user set money = money - ? where account = ?";
                        p = conn.prepareStatement(sql);
                        p.setInt(1, money);
                        p.setString(2, myAccount);
                        p.executeUpdate();
                        sql = "update user set money = money + ? where account = ?";
                        p = conn.prepareStatement(sql);
                        p.setInt(1, money);
                        p.setString(2, toAccount);
                        p.executeUpdate();
                        String message = myAccount+"向"+toAccount+"转账"+money+"元";
                        log(myAccount,message,"转账");
                        System.out.println("转账成功");
                    }
                }
            } else {
                System.out.println("卡号错误！");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void log(String account, String message,String type){
        String sql = "insert into detail values(?,?,?,?)";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, account);
            p.setString(2, time);
            p.setString(3,message);
            p.setString(4,type);
            p.executeUpdate();
        } catch (Exception ignored) {
        }
    }

    public int getMoney(){
        String sql = "select * from init limit 1";
        try {
            p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return rs.getInt("money");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    public void changeAdminPwd(String oldPassword,String newPassword,String account){
        String sql = "select * from admin where account = ? and password = ?";
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, account);
            p.setString(2, oldPassword);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                sql = "update admin set password = ? where account = ?";
                p = conn.prepareStatement(sql);
                p.setString(1,newPassword);
                p.setString(2,account);
                p.executeUpdate();
                System.out.println("修改密码成功");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void changePwd(String oldPassword,String newPassword,String account){
        String sql = "select * from user where account = ? and password = ?";
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, account);
            p.setString(2, oldPassword);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                sql = "update user set password = ? where account = ?";
                p = conn.prepareStatement(sql);
                p.setString(1,newPassword);
                p.setString(2,account);
                p.executeUpdate();
                System.out.println("修改密码成功");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public List<Detail> getAllDetail(){
        List<Detail> list = new ArrayList<>();
        String sql = "select * from detail";
        try {
            p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Detail detail = new Detail();
                detail.setAccount(rs.getString("account"));
                detail.setType(rs.getString("type"));
                detail.setTime(rs.getString("time"));
                detail.setMessage(rs.getString("message"));
                list.add(detail);
            }
            return list;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public List<Detail> getDetail(String account){
        List<Detail> list = new ArrayList<>();
        String sql = "select * from detail where account = ?";
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, account);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Detail detail = new Detail();
                detail.setAccount(rs.getString("account"));
                detail.setType(rs.getString("type"));
                detail.setTime(rs.getString("time"));
                detail.setMessage(rs.getString("message"));
                list.add(detail);
            }
            return list;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

//    public void Regist(int myid, String mypwd, String myname, int mymoney) {
//        String sql;
//        try {
//            sql = "select * from user where id = ?";
//            p = conn.prepareStatement(sql);
//            p.setInt(1, myid);
//            ResultSet rs = p.executeQuery();
//            if (rs.next()) {
//                System.out.println("此卡已注册，请勿重复注册！！！！");
//                return;
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//
//        try {
//            sql = "insert into user(id,pwd,money,name) values(?,?,?,?)";
//            p = conn.prepareStatement(sql);
//            p.setInt(1, myid);
//            p.setString(2, mypwd);
//            p.setInt(3, mymoney);
//            p.setString(4, myname);
//            p.executeUpdate();
//            System.out.println("注册成功！");
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }

    /**
     * 1为+， 2为-
     */
    public void resetMoney(int money,int type){
        String sql;
        if(type==1){
            sql = "update init set money = money + ?";
        }else{
            sql = "update init set money = money - ?";
        }
        try {
            p = conn.prepareStatement(sql);
            p.setInt(1, money);
            p.executeUpdate();
        } catch (Exception ignore) {
        }
    }
}