package ATM;

import java.util.List;
import java.util.Scanner;

public class ATMSystem {
    CustomerDAO dao = new CustomerDAO();
    Scanner input = new Scanner(System.in);
    private String account = "-1";
    private String adminAccount = "-1";

    public void begin() {
        int num = -1;
        int Num = 0;
        System.out.println("欢迎来到**ATM自助服务系统");
        System.out.println("请选择功能：");
        System.out.println("*************************");
        System.out.println("1.游客登录");
        System.out.println("2.管理员登录");
        System.out.println("*************************");
        Num = input.nextInt();
        switch (Num) {
            case 1:
                if (login()) {
                    while (num != 0) {
                        System.out.println("*******************************************************");
                        System.out.println("欢迎您");
                        System.out.println("请选择功能：");
                        System.out.println("1.存款    2.取款    3.查询    4.转账    5.修改密码    6.交易明细    0.退出");
                        System.out.println("*******************************************************");
                        num = input.nextInt();
                        switch (num) {
                            case 1:
                                save();
                                break;
                            case 2:
                                get();
                                break;
                            case 3:
                                query();
                                break;
                            case 4:
                                change();
                                break;
                            case 5:
                                changePassword();
                                break;
                            case 6:
                                showDetail();
                                break;
                            case 0:
                                System.out.println("谢谢使用！");
                                return;
                        }
                    }
                } else {
                    System.out.println("登录失败，账号、密码错误！");
                }
                break;
            case 2:
                if (adminLogin()) {
                    while (num != 0) {
                        System.out.println("********************");
                        System.out.println("欢迎您，管理员");
                        System.out.println("请选择功能：");
                        System.out.println("1.查看交易明细     2.查询系统余额     3.添加金钱     4.修改密码     0.退出");
                        System.out.println("********************");
                        num = input.nextInt();
                        switch (num) {
                            case 1:
                                showAllDetail();
                                break;
                            case 2:
                                showLastMoney();
                                break;
                            case 3:
                                addMoney();
                                break;
                            case 4:
                                changeAdminPassword();
                                break;
                            case 0:
                                System.out.println("谢谢使用！");
                                break;
                        }
                    }
                } else {
                    System.out.println("登录失败，账号、密码错误！");
                }
                break;
            default:
                break;
        }
    }

    public ATMSystem() {
    }

    public void addMoney(){
        int money = input.nextInt();
        dao.addMoney(money);
    }

    public void showLastMoney(){
        int money = dao.getMoney();
        System.out.println("系统剩余金额："+money);
    }

    public void showAllDetail(){
        List<Detail> list = dao.getAllDetail();
        for(Detail d : list){
            System.out.println("账户："+d.getAccount());
            System.out.println("时间："+d.getTime());
            System.out.println("类型："+d.getType());
            System.out.println("内容："+d.getMessage());
            System.out.println("++++++++++++++++++++++");
        }
    }

    public boolean adminLogin() {
        System.out.println("请输入管理员账户：");
        String account = input.next();
        System.out.println("请输入管理员密码：");
        String password = input.next();
        adminAccount = dao.adminLogin(password,account);
        if (adminAccount.equals("-1")) {
            System.out.println("登录失败！");
            return false;
        } else {
            System.out.println("管理员登陆成功！");
            return true;
        }
    }

    /*
     * *************************************
     */

    public void changeAdminPassword(){
        System.out.println("请输入旧的密码");
        String oldPassword = input.next();
        System.out.println("请输入新密码");
        String newPassword = input.next();
        dao.changeAdminPwd(oldPassword,newPassword,adminAccount);
    }

    /**
     * 修改密码
     */
    public void changePassword(){
        System.out.println("请输入旧的密码");
        String oldPassword = input.next();
        System.out.println("请输入新密码");
        String newPassword = input.next();
        dao.changePwd(oldPassword,newPassword,account);
    }

    /**
     * 显示用户交易细节
     */
    public void showDetail(){
        List<Detail> list = dao.getDetail(account);
        for(Detail d : list){
            System.out.println("账户："+d.getAccount());
            System.out.println("时间："+d.getTime());
            System.out.println("类型："+d.getType());
            System.out.println("内容："+d.getMessage());
            System.out.println("++++++++++++++++++++++");
        }
    }

    /**
     * 登录
     */
    public boolean login() {
        System.out.println("请输入卡号：");
        String _account = input.next();
        System.out.println("请输入密码：");
        String pwd = input.next();
        account = dao.login(pwd,_account);
        if (account.equals("-1")) {
            System.out.println("登录失败！");
            return false;
        } else {
            System.out.println("登陆成功！");
            return true;
        }
    }

    /**
     * 用户信息
     */
    public void query() {
        Customer customer = dao.query(account);
        System.out.println("********************");
        System.out.println("卡号：" + customer.getAccount());
        System.out.println("余额：" + customer.getMoney());
        System.out.println("ID：" + customer.getId());
        System.out.println("********************");
    }

    /**
     * 存款
     */
    public void save() {
        System.out.println("输入存款金额：");
        int money = input.nextInt();
        dao.deposit(account, money);
    }

    /**
     * 取钱
     */
    public void get() {
        System.out.println("********************");
        System.out.println("输入取款金额：");
        int money = input.nextInt();
        dao.update_get(account, money);
        System.out.println("********************");
    }

    /**
     * 转账
     */
    public void change() {
        System.out.println("请输入转账卡号：");
        String toAccount = input.next();
        System.out.println("请输入转账金额：");
        int money = input.nextInt();
        dao.transfer(account, toAccount, money);
    }

//    public void Regist() {
//        System.out.println("注册卡号：");
//        int myid = input.nextInt();
//        System.out.println("注册用户名：");
//        String myname = input.next();
//        System.out.println("注册密码：");
//        String mypwd = input.next();
//        System.out.println("输入存入金额：");
//        int mymoney = input.nextInt();
//        dao.Regist(myid, mypwd, myname, mymoney);
//    }
}