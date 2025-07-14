package rs.raf.domaci.repositories.clanakaktivnost;

import rs.raf.domaci.entities.Clanak;
import rs.raf.domaci.repositories.MySqlAbstractRepository;
import rs.raf.domaci.repositories.clanak.ClanakRepository;

import java.sql.*;
import java.time.LocalDateTime;

public class MySqlClanakAktivnostRepository extends MySqlAbstractRepository implements ClanakAktivnostRepository {
    @Override
    public String findAktivnostiZaClanak(Integer clanakId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;

        String aktivnosti = "";
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT aktivnost_id FROM clanakaktivnost where clanak_id = ?");
            preparedStatement.setInt(1, clanakId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int aktivnostId = resultSet.getInt("aktivnost_id");
                preparedStatement2 = connection.prepareStatement("select naziv from aktivnost where id = ?");
                preparedStatement2.setInt(1, aktivnostId);
                resultSet2 = preparedStatement2.executeQuery();
                if(resultSet2.next()){
                    aktivnosti+=resultSet2.getString("naziv")+" ";
                }
            }

            resultSet.close();
            resultSet2.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeStatement(preparedStatement2);
            this.closeResultSet(resultSet);
            this.closeResultSet(resultSet2);
            this.closeConnection(connection);
        }

        return aktivnosti.substring(0,aktivnosti.length()-1);

    }
}
