package rs.raf.domaci.repositories.user;

import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.entities.Korisnik;
import rs.raf.domaci.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository{
    @Override
    public Korisnik findKorisnik(String email) {
        Korisnik korisnik = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM korisnik where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                korisnik = new Korisnik(resultSet.getInt("id"), resultSet.getString("email"),resultSet.getString("ime"), resultSet.getString("prezime"), resultSet.getString("tipKorisnika"), resultSet.getInt("status"), resultSet.getString("lozinka"));
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

        return korisnik;
    }

    @Override
    public Korisnik addKorisnik(Korisnik korisnik) {
        List<Korisnik> svi = allKorisnici();
        for(Korisnik k:svi){
            if(k.getEmail().equals(korisnik.getEmail()))
                return null;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO korisnik (email, ime, prezime, tipKorisnika, status, lozinka) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, korisnik.getEmail());
            preparedStatement.setString(2, korisnik.getIme());
            preparedStatement.setString(3, korisnik.getPrezime());
            preparedStatement.setString(4, korisnik.getTipKorisnika());
            preparedStatement.setInt(5, korisnik.getStatus());
            preparedStatement.setString(6, korisnik.getLozinka());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                korisnik.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return korisnik;
    }

    @Override
    public List<Korisnik> allKorisnici() {
        List<Korisnik> korisnici = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from korisnik");

            while (resultSet.next()){
                korisnici.add(new Korisnik(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("ime"), resultSet.getString("prezime"), resultSet.getString("tipKorisnika"), resultSet.getInt("status"), resultSet.getString("lozinka")));
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return korisnici;
    }

    @Override
    public void deleteKorisnik(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM korisnik where id = ?");
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
    public void promeniStatus(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE korisnik SET status = CASE WHEN status = true THEN false ELSE true END WHERE id = ?");
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
    public void editProfile(Integer id, Korisnik korisnik) {
        String query = "UPDATE korisnik SET email = COALESCE(?, email), ime = COALESCE(?, ime), prezime = COALESCE(?, prezime), tipKorisnika = COALESCE(?, tipKorisnika), lozinka = COALESCE(?, lozinka) WHERE id = ?";


        try (Connection connection = newConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, korisnik.getEmail());
            preparedStatement.setString(2, korisnik.getIme());
            preparedStatement.setString(3, korisnik.getPrezime());
            preparedStatement.setString(4, korisnik.getTipKorisnika());
            preparedStatement.setString(5, korisnik.getLozinka());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Korisnik findKorisnikWithId(Integer id) {
        Korisnik korisnik = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM korisnik where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                korisnik = new Korisnik(resultSet.getInt("id"),resultSet.getString("email"),resultSet.getString("ime"),resultSet.getString("prezime"),resultSet.getString("tipKorisnika"));
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

        return korisnik;
    }

    @Override
    public Korisnik findKorisnikWithEmail(String email) {
        Korisnik korisnik = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM korisnik where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                korisnik = new Korisnik(resultSet.getInt("id"),resultSet.getString("email"),resultSet.getString("ime"),resultSet.getString("prezime"),resultSet.getString("tipKorisnika"));
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

        return korisnik;
    }

}
