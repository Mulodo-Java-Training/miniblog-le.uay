package com.mulodo.miniblog.connection;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

public class ConnectionReadWrite implements Work
{

    @Override
    public void execute(Connection connection) throws SQLException
    {
        connection.setReadOnly(false);
    }

}
