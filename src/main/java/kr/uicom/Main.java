package kr.uicom;

import kr.uicom.mybatis.MyBatisConnectionFactory;
import kr.uicom.vo.Project;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        System.out.println("Angeluce Manual Generator");

        RedmineDAO redmineDAO = new RedmineDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        List<Project> list = redmineDAO.selectAll();
        for (Project item : list) {
            System.out.println(item.getName());
        }

        File file = new File("redmine.txt");
        try {

//            FileWriter fw = new FileWriter(file);
//            FileWriter fw = new FileWriter(file,true);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));

            for (Project item : list) {
                pw.println(item.getName());
            }

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
