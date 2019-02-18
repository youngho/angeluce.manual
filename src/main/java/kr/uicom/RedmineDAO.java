package kr.uicom;

import kr.uicom.vo.Attachment;
import kr.uicom.vo.Issue;
import kr.uicom.vo.Project;
import kr.uicom.vo.Version;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RedmineDAO {

    private static final Logger logger = LogManager.getLogger(RedmineDAO.class);

    private SqlSessionFactory sqlSessionFactory = null;

    public RedmineDAO(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     *
     */
    public Project selectProjectByName(String name){
        Project list = null;
        SqlSession session = sqlSessionFactory.openSession();

        System.out.print("selectProjectByName : ");
        System.out.println(name);

        try {
            list = session.selectOne("Project.selectProjectByName", name);
        } finally {
            session.close();
        }
        System.out.println("selectProjectByName() --> "+list);
        return list;
    }

    /**
     *
     */
    public List<Issue> selectIssuesById(Issue issue){
        List<Issue> list = null;
        SqlSession session = sqlSessionFactory.openSession();

        System.out.print("selectIssues : ");
        System.out.println(issue.getProject_id());
        System.out.print("selectIssues : ");
        System.out.println(issue.getFixed_version_id());

        try {
            list = session.selectList("Project.selectIssuesById", issue);
        } finally {
            session.close();
        }
        System.out.println("selectIssues() --> "+list);
        return list;
    }

    /**
     *
     */
    public List<Attachment> selectAttachments(int id){
        List<Attachment> list = null;
        SqlSession session = sqlSessionFactory.openSession();

        System.out.println(id);

        try {
            list = session.selectList("Project.selectAttachments", id);
        } finally {
            session.close();
        }
        System.out.println("selectAttachments() --> "+list);
        return list;
    }

    /**
     *
     */
    public Version selectVersionByIdName(Version version){
        Version resultVersion = new Version();
        SqlSession session = sqlSessionFactory.openSession();

        System.out.println("selectVersionByIdName : " + version.getProject_id());
        System.out.println("selectVersionByIdName : " + version.getName());

        try {
            resultVersion = session.selectOne("Project.selectVersionByIdName", version);
        } finally {
            session.close();
        }
//        System.out.println("selectVersionByIdName() --> "+resultVersion);
        logger.debug("selectVersionByIdName() --> "+resultVersion);
        return resultVersion;
    }

}
