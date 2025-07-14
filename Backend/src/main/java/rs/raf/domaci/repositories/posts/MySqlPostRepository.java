package rs.raf.domaci.repositories.posts;

import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.entities.Post;
import rs.raf.domaci.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlPostRepository{// extends MySqlAbstractRepository implements PostRepository{
//    @Override
//    public Post addPost(Post post) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = this.newConnection();
//
//            String[] generatedColumns = {"id"};
//
//            preparedStatement = connection.prepareStatement("INSERT INTO post (title, author, content, date) VALUES(?, ?, ?, ?)", generatedColumns);
//            preparedStatement.setString(1, post.getTitle());
//            preparedStatement.setString(2, post.getAuthor());
//            preparedStatement.setString(3, post.getContent());
//            preparedStatement.setString(4, post.getDate());
//            preparedStatement.executeUpdate();
//            resultSet = preparedStatement.getGeneratedKeys();
//
//            if (resultSet.next()) {
//                post.setId(resultSet.getInt(1));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            this.closeStatement(preparedStatement);
//            this.closeResultSet(resultSet);
//            this.closeConnection(connection);
//        }
//
//        return post;
//    }
//
//    @Override
//    public synchronized List<Post> allPosts() {
//        List<Post> posts = new ArrayList<>();
//
//        Connection connection = null;
//        Statement statement = null;
//        Statement statement2 = null;
//        ResultSet resultSet = null;
//        ResultSet resultSet2 = null;
//        try{
//            connection = this.newConnection();
//
//            statement = connection.createStatement();
//            statement2 = connection.createStatement();
//            resultSet = statement.executeQuery("select * from post");
//
//            while (resultSet.next()){
//                Integer postId = resultSet.getInt("id");
//                String postTitle = resultSet.getString("title");
//                String postContent = resultSet.getString("content");
//                String postAuthor = resultSet.getString("author");
//                String postDate = resultSet.getString("date");
//                List<Komentar> comments = new ArrayList<>();
//                resultSet2 = statement2.executeQuery("select * from comment where post_id_fk="+resultSet.getInt("id"));
//                    while(resultSet2.next()){
//                        comments.add(new Komentar(resultSet2.getString("comment_author"), resultSet2.getString("comment_content")));
//                    }
//                posts.add(new Post(postId,postAuthor,postTitle,postContent,postDate,comments));
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            this.closeStatement(statement);
//            this.closeResultSet(resultSet);
//            this.closeStatement(statement2);
//            this.closeResultSet(resultSet2);
//            this.closeConnection(connection);
//        }
//
//        return posts;
//    }
//
//    @Override
//    public Post findPost(Integer id) {
//        Post post = null;
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        PreparedStatement preparedStatement2 = null;
//        ResultSet resultSet = null;
//        ResultSet resultSet2 = null;
//        try {
//            connection = this.newConnection();
//
//            preparedStatement = connection.prepareStatement("SELECT * FROM post where id = ?");
//            preparedStatement.setInt(1, id);
//            preparedStatement2 = connection.prepareStatement("SELECT * FROM comment where post_id_fk = ?");
//            preparedStatement2.setInt(1,id);
//            resultSet = preparedStatement.executeQuery();
//            resultSet2 = preparedStatement2.executeQuery();
//
//            if(resultSet.next()) {
//                int postId = resultSet.getInt("id");
//                String author = resultSet.getString("author");
//                String title = resultSet.getString("title");
//                String content = resultSet.getString("content");
//                String date = resultSet.getString("date");
//                List<Komentar> comments = new ArrayList<>();
//                while (resultSet2.next()){
//                    comments.add(new Komentar(resultSet2.getString("comment_author"), resultSet2.getString("comment_content")));
//                }
//                post = new Post(postId, author, title, content, date, comments);
//            }
//
//            resultSet.close();
//            resultSet2.close();
//            preparedStatement.close();
//            preparedStatement2.close();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            this.closeStatement(preparedStatement);
//            this.closeResultSet(resultSet);
//            this.closeStatement(preparedStatement2);
//            this.closeResultSet(resultSet2);
//            this.closeConnection(connection);
//        }
//
//        return post;
//    }
//
//    @Override
//    public Post addComment(Integer id, Komentar comment) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = this.newConnection();
//
//            preparedStatement = connection.prepareStatement("INSERT INTO comment (comment_author, comment_content, post_id_fk) VALUES(?, ?, ?)");
//            preparedStatement.setString(1, comment.getName());
//            preparedStatement.setString(2, comment.getComment());
//            preparedStatement.setInt(3, id);
//            preparedStatement.executeUpdate();
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            this.closeStatement(preparedStatement);
//            this.closeConnection(connection);
//        }
//
//        Post post = findPost(id);
//        post.addComment(comment);
//
//        return post;
//    }
}
