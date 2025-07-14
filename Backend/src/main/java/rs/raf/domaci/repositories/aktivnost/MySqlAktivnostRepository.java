package rs.raf.domaci.repositories.aktivnost;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.repositories.MySqlAbstractRepository;
import rs.raf.domaci.repositories.destinations.DestinationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlAktivnostRepository extends MySqlAbstractRepository implements AktivnostRepository {
    @Override
    public Aktivnost addAktivnost(Aktivnost aktivnost) {
        List<Aktivnost> sve = allAktivnosti();
        for(Aktivnost a:sve){
            if(a.getNaziv().equals(aktivnost.getNaziv()))
                return null;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO aktivnost (naziv) VALUES(?)", generatedColumns);
            preparedStatement.setString(1, aktivnost.getNaziv());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                aktivnost.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return aktivnost;
    }

    @Override
    public List<Aktivnost> allAktivnosti() {
        List<Aktivnost> aktivnosti = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from aktivnost");

            while (resultSet.next()){
                aktivnosti.add(new Aktivnost(resultSet.getInt("id"),resultSet.getString("naziv")));
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return aktivnosti;
    }

    @Override
    public Aktivnost findAktivnost(Integer id) {
        Aktivnost aktivnost = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM aktivnost where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                aktivnost = new Aktivnost(resultSet.getInt("id"),resultSet.getString("naziv"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return aktivnost;
    }

    @Override
    public void deleteAktivnost(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM aktivnost where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void editAktivnost(Integer id, Aktivnost aktivnost) {
        String query = "UPDATE aktivnost SET naziv = COALESCE(?, naziv) WHERE id = ?";

        try (Connection connection = newConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, aktivnost.getNaziv());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
