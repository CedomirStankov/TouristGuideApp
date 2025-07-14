package rs.raf.domaci.repositories.komentar;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.repositories.MySqlAbstractRepository;
import rs.raf.domaci.repositories.destinations.DestinationRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySqlKomentarRepository extends MySqlAbstractRepository implements KomentarRepository {
    @Override
    public Komentar addKomentar(Komentar komentar) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO komentar (autor,komentar,datumKreiranja,clanak_id) VALUES(?,?,?,?)", generatedColumns);
            preparedStatement.setString(1, komentar.getAutor());
            preparedStatement.setString(2, komentar.getKomentar());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(komentar.getDatumKreiranja()));
            preparedStatement.setInt(4, komentar.getClanak_id());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                komentar.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return komentar;
    }

    @Override
    public List<Komentar> allKomentari(Integer id) {
        List<Komentar> komentari = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from komentar where clanak_id = ? ORDER BY datumKreiranja DESC");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Timestamp timestamp = resultSet.getTimestamp("datumKreiranja");
                LocalDateTime datumKreiranja = timestamp.toLocalDateTime();
                komentari.add(new Komentar(resultSet.getInt("id"),resultSet.getString("autor"),resultSet.getString("komentar"),datumKreiranja,resultSet.getInt("clanak_id")));
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return komentari;
    }

    @Override
    public Komentar findKomentar(Integer id) {
        Komentar komentar = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM komentar where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("datumKreiranja");
                LocalDateTime datumKreiranja = timestamp.toLocalDateTime();
                komentar = new Komentar(resultSet.getInt("id"),resultSet.getString("autor"),resultSet.getString("komentar"),datumKreiranja,resultSet.getInt("clanak_id"));
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

        return komentar;
    }

    @Override
    public void deleteKomentar(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM komentar where id = ?");
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

}
