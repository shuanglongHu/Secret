package view;

import controller.SecretController;
import controller.UserController;
import domain.Secret;
import domain.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MainView {
    private SecretController secretController = new SecretController();
    private UserController userController = new UserController();

    /**
     * 视图层，显示操作界面
     */
    public void run(){
        Scanner scanner = new Scanner(System.in);
        /**
         * 首先显示操作界面，根据输入的数字来选择进行的操作
         */
        while (true) {
            System.out.println("--- ----------------------Serect-------------------------------");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("                         用户菜单                              ");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("        1.用户注册                     2.用户登录               ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("请输入注册账号");
                    /**
                     * 判断账号是否为空或者已经存在
                     */
                    String username = scanner.next();
                    if (username == null) {
                        System.out.println("账号不能为空");
                        break;
                    }
                    User user1 = userController.findUser(username);
                    if (user1 != null) {
                        System.out.println("账号已存在！请重新设置账号：");
                        break;
                    }

                    //判断密码是否为空
                    System.out.println("请输入注册密码：");
                    String password = scanner.next();
                    while(password == null) {
                        System.out.println("密码不能为空");
                        password = scanner.next();
                    }

                    //注册账号
                    int res = userController.register(username, password);
                    if(res == 1) {
                        System.out.println("恭喜您注册成功！");
                    }
                    break;
                case 2:
                    System.out.println("请输入账号");
                    username = scanner.next();
                    if (username == null) {
                        System.out.println("账号不能为空！");
                        break;
                    }
                    User user2 = userController.findUser(username);
                    if(user2 == null) {
                        System.out.println("此账号不存在！");
                        break;
                    }
                    System.out.println("请输入密码");
                    password = scanner.next();

                    if(!user2.getPassword().equals(password)) {
                        System.out.println("您输入的密码错误！");
                        break;
                    } else {
                        System.out.println("您已成功登录！");
                        encrypt(user2.getUsername());
                    }
                    break;
                default:
                    System.out.println("对不起，这是无效选项，请重新选择");
            }
        }
    }

    private void encrypt(String username) {
        System.out.println("1.加密文件   2.显示所有已加密文件    3.解密文件");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice){
            case 1:
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入您需要加密的文件地址");
                String inputFilePath = scanner.next();
                System.out.println("请输入您需要加密的文件保存地址");
                String outputFilePath = scanner.next();
                File inputFile = new File(inputFilePath);
                File outputFile = new File(outputFilePath);
                if (!outputFile.exists()) {
                    outputFile.getParentFile().mkdir();
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException("创建解密文件路径出错！");
                    }
                }
                if((secretController.desEncryptFile(username, inputFile,outputFile))>0){
                    System.out.println("加密成功");
                }
                break;
            case 2:
                showALLSecret(username);
                break;
            case 3:
                List<Secret> list = secretController.showAllSecret(username);
                if (list.isEmpty()) {
                    System.out.println("您还没有已加密的文件！");
                    break;
                }
                showALLSecret(username);
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("请选择你想要解密的文件序号,序号编号由1开始");
                int index = sc.nextInt();
                System.out.println("请输入文件解密后的保存路径");
                String decryptPath = sc.next();
                Secret secret = list.get(index - 1);
                File input = new File(secret.getFilename());
                File output = new File(decryptPath);
                secretController.desDecryptFile(input,output,secret.getSecretKey());
                break;
            default:
                System.out.println("选择的功能不存在，请重新输入");
                break;
        }
    }

    private void showALLSecret(String username) {
        List<Secret> list =  secretController.showAllSecret(username);
        System.out.println("-------------------------------------------");
        System.out.println("文件列表：");
        if(list.isEmpty()) {
            System.out.println("您还没有已加密的文件！");
        }
        for (Secret s: list){
            System.out.println(s);
        }
        System.out.println("-------------------------------------------");
    }
}
