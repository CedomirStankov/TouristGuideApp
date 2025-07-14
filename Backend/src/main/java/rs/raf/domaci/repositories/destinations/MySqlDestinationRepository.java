package rs.raf.domaci.repositories.destinations;

import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.entities.Post;
import rs.raf.domaci.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDestinationRepository extends MySqlAbstractRepository implements DestinationRepository{
    @Override
    public Destinacija addDestination(Destinacija destinacija) {
        List<Destinacija> sve = allDestinations();
        for(Destinacija d:sve){
            if(d.getNaziv().equals(destinacija.getNaziv()))
                return null;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO destinacija (naziv,opis) VALUES(?, ?)", generatedColumns);
            preparedStatement.setString(1, destinacija.getNaziv());
            preparedStatement.setString(2, destinacija.getOpis());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                destinacija.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return destinacija;
    }

    @Override
    public List<Destinacija> allDestinations() {
        List<Destinacija> destinacije = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from destinacija");

            while (resultSet.next()){
                destinacije.add(new Destinacija(resultSet.getInt("id"),resultSet.getString("naziv"),resultSet.getString("opis")));
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return destinacije;
    }

    @Override
    public Destinacija findDestination(Integer id) {
        Destinacija destinacija = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM destinacija where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                destinacija = new Destinacija(resultSet.getInt("id"),resultSet.getString("naziv"),resultSet.getString("opis"));
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

        return destinacija;
    }

    @Override
    public Integer deleteDestination(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if(postojiClanak(id))
            return -1;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM destinacija where id = ?");
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
        return 1;
    }

    @Override
    public Integer editDestination(Integer id, Destinacija destinacija) {
        List<Destinacija> destinacije = allDestinations();
        for(Destinacija d:destinacije){
            if(d.getNaziv().equals(destinacija.getNaziv()) && d.getId()!=id)
                return -1;
        }
        String query = "UPDATE destinacija SET naziv = COALESCE(?, naziv), opis = COALESCE(?, opis) WHERE id = ?";

        try (Connection connection = newConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, destinacija.getNaziv());
            preparedStatement.setString(2, destinacija.getOpis());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private boolean postojiClanak(Integer destinationId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM clanak WHERE destinacija_id = ?");
            preparedStatement.setInt(1, destinationId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int articleCount = resultSet.getInt("count");
                return articleCount > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return false;
    }
}
