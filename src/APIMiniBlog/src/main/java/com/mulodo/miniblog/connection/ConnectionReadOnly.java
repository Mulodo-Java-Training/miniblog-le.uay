package com.mulodo.miniblog.connection;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

public class ConnectionReadOnly implements Work
{

    @Override
    public void execute(Connection connection) throws SQLException
    {
        System.out.println("hibernate set read only on session jdbc connection");
        connection.setReadOnly(true);

    }

}
