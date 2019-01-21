package kr.uicom;

import kr.uicom.vo.Project;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class RedmineDAO {
    private SqlSessionFactory sqlSessionFactory = null;

    public RedmineDAO(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Returns the list of all Project instances from the database.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Project> selectAll(){
        List<Project> list = null;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            list = session.selectList("Project.selectAll");
        } finally {
            session.close();
        }
        System.out.println("selectAll() --> "+list);
        return list;

    }
}
