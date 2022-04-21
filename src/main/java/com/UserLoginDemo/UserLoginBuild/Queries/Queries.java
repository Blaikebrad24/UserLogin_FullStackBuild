package com.UserLoginDemo.UserLoginBuild.Queries;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class Queries {

    private static final String propFileName = "queries.properties";
    private static Properties props;

    public static Properties getQueries() throws SQLException {
        //singleton
        if(props == null)
        {
            props = new Properties();
            InputStream is = Queries.class.getResourceAsStream("src/main/resources/" + propFileName);
            try{
                props.load(is);
            }catch(IOException e)
            {
                throw new SQLException("Unable to load property file: " + propFileName);
            }
        }
        return props;
    }

    public static String getQuery(String query) throws SQLException{
        return getQueries().getProperty(query);
    }
}
 // PreparedStatement queryStatement = c.preparedStatement(Queries.getQuery("queryName");