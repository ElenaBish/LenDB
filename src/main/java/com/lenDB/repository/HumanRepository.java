package com.lenDB.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elena on 21.06.2017.
 */
public class HumanRepository {
    private Connection connection;
    private PreparedStatement psInsert;
    private PreparedStatement psSelectAll;
    private PreparedStatement psTruncate;
    private PreparedStatement psSelectId;
    private PreparedStatement psUpdate;


    public HumanRepository() {

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:jtds:sqlserver://127.0.0.1:1433/testitnow","sa_test","test123");
            psInsert=connection.prepareStatement("insert into human values(?,?,?)");
            psSelectAll=connection.prepareStatement("select h.id,h.first_name,h.last_name from human h");
            psSelectId=connection.prepareStatement("select h.id,h.first_name,h.last_name from human h where h.id=?");
            psTruncate=connection.prepareStatement("truncate TABLE human");
            psUpdate=connection.prepareStatement("update human set first_name=?, last_name=? where id=?");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
          catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Human human) {
        try {
            Human byId= findById(human.getId());
            if (byId==null) {
                psInsert.setString(1, human.getId());
                psInsert.setString(2, human.getFirstName());
                psInsert.setString(3, human.getLastName());
                psInsert.execute();
            }
            else
            {
                psUpdate.setString(1,human.getFirstName());
                psUpdate.setString(2,human.getLastName());
                psUpdate.setString(3,human.getId());
                psUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Human> findAll() {
        try {

            ResultSet    resultSet = psSelectAll.executeQuery();

            return getHumans(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Human> getHumans(ResultSet resultSet) throws SQLException {
        List<Human> humans = new ArrayList<>();
        while (resultSet.next()){
            Human human = new Human(resultSet.getString("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
            humans.add(human);

        }
        return humans;
    }

    public void deleteAll() {
            try {
            psTruncate.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public Human findById(String id) {
        try {
            psSelectId.setString(1,id);
            ResultSet resultSet = psSelectId.executeQuery();
            List <Human> humans=getHumans(resultSet);
            return humans.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
}
