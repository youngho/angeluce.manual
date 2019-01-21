package kr.uicom.mybatis;
 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
 
public class MyBatisConnectionFactory {
 
    private static SqlSessionFactory sqlSessionFactory;
 
    static {
        try {
 
//            String resource = "src/main/java/kr.uicom/mybatis/mybatis-config.xml";
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);

            if (sqlSessionFactory == null) {
//                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}

