package kr.uicom;

import kr.uicom.mybatis.MyBatisConnectionFactory;
import kr.uicom.vo.Attachment;
import kr.uicom.vo.Issue;
import kr.uicom.vo.Project;
import kr.uicom.vo.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String args[]) {
        // 3가지 인자를 받을 수 있다. 프로젝트 identifier, 프로젝트버전, 일감아이디패턴3자리
        System.out.println(args[0]);    // 프로젝트 identifier
        System.out.println(args[1]);    // 프로젝트버전
        System.out.println(args[2]);    // 일감아이디패턴3자리
        System.out.println("Angeluce Manual Generator V1");

//        String projectName = "엔젤루체";
        String projectName = args[0];   // 프로젝트 identifier
        String pmsAttachmentURL = "http://ct.e-kiss.co.kr:9000/attachments/download/";
        String apiKey = "3050edd4ad0ebcb56fab963d71e9a8947c9e7cce";

        RedmineDAO redmineDAO = new RedmineDAO(MyBatisConnectionFactory.getSqlSessionFactory());

//        List<Project> list = redmineDAO.selectAll();
//        List<Project> list = redmineDAO.selectById(4);
        Project resultProject = redmineDAO.selectProjectByName(projectName);
        System.out.println(resultProject.getName());

        // 버전의 ID 가져오기
        Version version = new Version();
        version.setProject_id(resultProject.getId());
        version.setName(args[1]);
        Version resultVersion = redmineDAO.selectVersionByIdName(version);
        System.out.println("프로젝트명 AND 버전의 ID : " + resultVersion.getId());
//        logger.debug(resultVersion.getId());
//        System.out.println(resultVersion.getName());


        /*
        해당 프로젝트에서 일감 목록을 가져온다. 이 규칙은 PMS(Redmine)의 작성 규칙을 반영해야 한다.
         */
        Issue issue = new Issue();
        issue.setProject_id(resultProject.getId());
        issue.setFixed_version_id(resultVersion.getId());
        List<Issue> issueList = redmineDAO.selectIssuesById(issue);
        for (Issue item : issueList) {
            System.out.println(item.getAuid());
        }

        File file = new File("angeluce_and/angeluce_and.rst");

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println("엔젤루체 안드로이드용 사용설명서");
            pw.println("========================================================================");
            pw.println("");
            pw.println(".. toctree::\n" +
                    "   :maxdepth: 2");
            pw.println("");

            for (Issue item : issueList) {
                // 일감목록을 tocTree로 만든다
                pw.println("   " + item.getAuid());
                /*
                하부 일감을 가지고 문서를 생성하는 부분
                일감목록을 가지고 파일을 생성하고 첨부된 파일을 다운로드 한다
                 */
                File rstFile = new File("angeluce_and/" + item.getAuid() + ".rst");
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(rstFile)));
                printWriter.println(item.getSubject()); // 첫번째줄 제목 만들기
                printWriter.println("----------------------------------------------------------------------------");
                printWriter.println("");


                printWriter.println(item.getDescription()); // 내용 만들기
                printWriter.println("");

                //이미지 파일 다운로드하기 redmine의 public 프로젝트가 아닌경우는 API KEY를 사용하여 접근한다
                List<Attachment> attachmentList = redmineDAO.selectAttachments(item.getId());
                for (Attachment attachment : attachmentList) {
                    File outputFile = new File("angeluce_and/" + attachment.getFilename());
                    String imageUrl = pmsAttachmentURL + attachment.getId() +"/" + attachment.getFilename() + "?key=" + apiKey;
                    saveImage(imageUrl, outputFile, "png");

                    // rst 문서에 이미지 첨부하기
                    printWriter.println(".. image:: " + attachment.getFilename());
                    printWriter.println("");
                }

                printWriter.close();    // 하부일감 AUIXXX.rst 파일
            }   // for (Issue item : issueList)



            pw.close(); // angeluce_and.rst

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveImage(String imageUrl, File saveFile, String fileFormat) {

        URL url = null;
        BufferedImage bi = null;

        try {
            url = new URL(imageUrl); // 다운로드 할 이미지 URL
            bi = ImageIO.read(url);
            ImageIO.write(bi, fileFormat, saveFile); // 저장할 파일 형식, 저장할 파일명

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
