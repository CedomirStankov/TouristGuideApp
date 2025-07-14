package rs.raf.domaci.repositories.clanak;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Clanak;
import rs.raf.domaci.repositories.MySqlAbstractRepository;
import rs.raf.domaci.repositories.destinations.DestinationRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySqlClanakRepository extends MySqlAbstractRepository implements ClanakRepository {
    @Override
    public Clanak addClanak(Clanak clanak) {
        List<Clanak> svi = allClanci();
        for(Clanak c:svi){
            if(c.getNaslov().equals(clanak.getNaslov()))
                return null;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO clanak (naslov,tekst,vremeKreiranja,brojPoseta,autor,destinacija_id) VALUES(?,?,?,?,?,?)", generatedColumns);
            preparedStatement.setString(1, clanak.getNaslov());
            preparedStatement.setString(2, clanak.getTekst());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(clanak.getVremeKreiranja()));
            preparedStatement.setInt(4, clanak.getBrojPoseta());
            preparedStatement.setString(5, clanak.getAutor());
            preparedStatement.setInt(6, clanak.getDestinacija_id());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                clanak.setId(resultSet.getInt(1));
            }
            dodajAktivnosti(clanak.getAktivnosti(), connection, clanak.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return clanak;
    }

    private void dodajAktivnosti(String aktivnosti, Connection connection, Integer clanakId) {
        PreparedStatement selectStatement = null;
        PreparedStatement insertAktivnostStatement = null;
        PreparedStatement insertClanakAktivnostStatement= null;
        ResultSet resultSet = null;

        String[] split = aktivnosti.split(" ");
        System.out.println("unutar dodajAktivnosti");
        try{
            String[] generatedColumns = {"id"};
            for(int i=0;i<split.length;i++){
                int aktivnostId=-1;
                selectStatement = connection.prepareStatement("select id from aktivnost where naziv=?");
                selectStatement.setString(1, split[i]);
                resultSet = selectStatement.executeQuery();
                if(resultSet.next()){
                    aktivnostId = resultSet.getInt(1);
                }else{ // ako ne vrati ni jednu tabelu resultSet to znaci da aktivnost sa tim imenom ne postoji u bazi i onda je dodajemo u bazu
                    insertAktivnostStatement = connection.prepareStatement("insert into aktivnost (naziv) values (?)",generatedColumns);
                    insertAktivnostStatement.setString(1, split[i]);
                    insertAktivnostStatement.executeUpdate();

                    ResultSet generatedKeys = insertAktivnostStatement.getGeneratedKeys();
                    if(generatedKeys.next()){
                        aktivnostId = generatedKeys.getInt(1);
                    }
                    generatedKeys.close();
                }
                resultSet.close();
                selectStatement.close();

                insertClanakAktivnostStatement = connection.prepareStatement("insert into clanakaktivnost (clanak_id,aktivnost_id) values (?,?)");
                insertClanakAktivnostStatement.setInt(1, clanakId);
                insertClanakAktivnostStatement.setInt(2, aktivnostId);
                insertClanakAktivnostStatement.executeUpdate();
                insertClanakAktivnostStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(selectStatement);
            this.closeStatement(insertAktivnostStatement);
            this.closeStatement(insertClanakAktivnostStatement);
            this.closeResultSet(resultSet);
        }

    }

    @Override
    public List<Clanak> allClanci() {
        List<Clanak> clanci = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from clanak ORDER BY vremeKreiranja DESC");

            while (resultSet.next()){
                Timestamp timestamp = resultSet.getTimestamp("vremeKreiranja");
                LocalDateTime vremeKreiranja = timestamp.toLocalDateTime();
                clanci.add(new Clanak(resultSet.getInt("id"),resultSet.getString("naslov"),resultSet.getString("tekst"),vremeKreiranja,resultSet.getInt("brojPoseta"),resultSet.getString("autor"),resultSet.getInt("destinacija_id")));
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return clanci;
    }

    @Override
    public Clanak findClanak(Integer id) {
        Clanak clanak = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM clanak where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("vremeKreiranja");
                LocalDateTime vremeKreiranja = timestamp.toLocalDateTime();
                clanak = new Clanak(resultSet.getInt("id"),resultSet.getString("naslov"),resultSet.getString("tekst"),vremeKreiranja,resultSet.getInt("brojPoseta"),resultSet.getString("autor"),resultSet.getInt("destinacija_id"));
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

        return clanak;
    }

    @Override
    public void deleteClanak(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM clanak where id = ?");
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
    public Clanak editClanak(Integer id, Clanak clanak) {
        String query = "UPDATE clanak SET naslov = COALESCE(?, naslov),tekst = COALESCE(?, tekst), vremeKreiranja = COALESCE(?, vremeKreiranja), brojPoseta = COALESCE(?, brojPoseta), autor = COALESCE(?, autor), destinacija_id = COALESCE(?, destinacija_id) WHERE id = ?";

        try (Connection connection = newConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clanak.getNaslov());
            preparedStatement.setString(2, clanak.getTekst());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(clanak.getVremeKreiranja()));
            preparedStatement.setInt(4, clanak.getBrojPoseta());
            preparedStatement.setString(5, clanak.getAutor());
            preparedStatement.setInt(6, clanak.getDestinacija_id());
            preparedStatement.setInt(7, id);

            preparedStatement.executeUpdate();
            System.out.println("unutar editClanak");
            editAktivnosti(clanak.getAktivnosti(), connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Clanak(id, clanak.getNaslov(), clanak.getTekst(), clanak.getVremeKreiranja(), clanak.getBrojPoseta(), clanak.getAutor(), clanak.getDestinacija_id());
    }

    @Override
    public Clanak povecajBrojPoseta(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Clanak clanak = findClanak(id);
        clanak.setBrojPoseta(clanak.getBrojPoseta()+1);
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE clanak SET brojPoseta = ? where id = ?");
            preparedStatement.setInt(1, clanak.getBrojPoseta());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return clanak;
    }

    @Override
    public List<Clanak> najcitanijiClanci() {
        List<Clanak> clanci = new ArrayList<>();
        int br=0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from clanak ORDER BY brojPoseta DESC");

            while (br<10 && resultSet.next()){
                br++;
                Timestamp timestamp = resultSet.getTimestamp("vremeKreiranja");
                LocalDateTime vremeKreiranja = timestamp.toLocalDateTime();
                clanci.add(new Clanak(resultSet.getInt("id"),resultSet.getString("naslov"),resultSet.getString("tekst"),vremeKreiranja,resultSet.getInt("brojPoseta"),resultSet.getString("autor"),resultSet.getInt("destinacija_id")));
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        System.out.println("broj clanaka: "+clanci.size() );
        return clanci;
    }

    @Override
    public List<Clanak> findClankeZaDestinaciju(Integer id) {
        List<Clanak> clanci = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from clanak where destinacija_id = ? ORDER BY brojPoseta DESC");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Timestamp timestamp = resultSet.getTimestamp("vremeKreiranja");
                LocalDateTime vremeKreiranja = timestamp.toLocalDateTime();
                clanci.add(new Clanak(resultSet.getInt("id"),resultSet.getString("naslov"),resultSet.getString("tekst"),vremeKreiranja,resultSet.getInt("brojPoseta"),resultSet.getString("autor"),resultSet.getInt("destinacija_id")));
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
        }
        return clanci;
    }

    @Override
    public List<Clanak> clanciAktivnosti(String nazivAktivnosti) {
        List<Clanak> clanci = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        ResultSet resultSet3 = null;
        int idAktivnosti=-1;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select id from aktivnost where naziv = ?");
            preparedStatement.setString(1, nazivAktivnosti);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                idAktivnosti=resultSet.getInt("id");
            }

            preparedStatement2 = connection.prepareStatement("select clanak_id from clanakaktivnost where aktivnost_id = ?");
            preparedStatement2.setInt(1, idAktivnosti);
            resultSet2 = preparedStatement2.executeQuery();

            while(resultSet2.next()){
                preparedStatement3 = null;
                resultSet3 = null;
                int clanakId = resultSet2.getInt("clanak_id");
                System.out.println("clanciId: "+ clanakId);
                preparedStatement3 = connection.prepareStatement("select * from clanak where id = ?");
                preparedStatement3.setInt(1, clanakId);
                resultSet3 = preparedStatement3.executeQuery();
                if(resultSet3.next()){
                    Timestamp timestamp = resultSet3.getTimestamp("vremeKreiranja");
                    LocalDateTime vremeKreiranja = timestamp.toLocalDateTime();
                    clanci.add(new Clanak(resultSet3.getInt("id"),resultSet3.getString("naslov"),resultSet3.getString("tekst"),vremeKreiranja,resultSet3.getInt("brojPoseta"),resultSet3.getString("autor"),resultSet3.getInt("destinacija_id")));
                }
            }

            preparedStatement.close();
            preparedStatement2.close();
            preparedStatement3.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeStatement(preparedStatement2);
            this.closeStatement(preparedStatement3);
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
            this.closeResultSet(resultSet2);
            this.closeResultSet(resultSet3);
        }
        return clanci;
    }

    private void editAktivnosti(String aktivnosti, Connection connection, Integer clanakId) {
        System.out.println("odma na ulazu");
        PreparedStatement deleteStatement = null;
        ResultSet resultSet = null;

        try{
            System.out.println("pre deletestatementa");
            deleteStatement = connection.prepareStatement("delete from clanakaktivnost where clanak_id = ?");
            deleteStatement.setInt(1, clanakId);
            deleteStatement.executeUpdate();
            System.out.println("posle deletestatementa");
            dodajAktivnosti(aktivnosti,connection,clanakId);
            System.out.println("nakon dodajAktivnosti");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(deleteStatement);
            this.closeResultSet(resultSet);
        }
        System.out.println("pre ulaska");

    }
}
