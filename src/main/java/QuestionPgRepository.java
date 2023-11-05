import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionPgRepository implements Irepository {
    private final String URL = "jdbc:postgresql://localhost:5432/quiz";
    private final String USER = "techpro";
    private final String PASSWORD = "password";
    private Connection conn;
    private Statement st;
    private PreparedStatement prst;

    private void setConnection() {
        try {
            this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setStatement() {
        try {
            this.st = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setPreparedStatement(String sql) {
        try {
            this.prst = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTable(){
        setConnection();
        setStatement();
        try {
            st.execute("CREATE TABLE IF NOT EXISTS  questions(" +
                    "id SERIAL UNIQUE," +
                    "question VARCHAR(100) NOT NULL," +
                    "answer VARCHAR(1) NOT NULL," +
                    "option1 VARCHAR(50) NOT NULL," +
                    "option2 VARCHAR(50) NOT NULL," +
                    "option3 VARCHAR(50) NOT NULL," +
                    "option4 VARCHAR(50) NOT NULL)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void insertQuestion(Question question) {
        setConnection();
        String insertQuery =
                "INSERT INTO questions(question,answer,option1,option2,option3,option4) VALUES(?,?,?,?,?,?)";
        setPreparedStatement(insertQuery);
        try {
            prst.setString(1, question.getQuestion());
            prst.setString(2, question.getAnswer());
            prst.setString(3, question.getOption1());
            prst.setString(4, question.getOption2());
            prst.setString(5, question.getOption3());
            prst.setString(6, question.getOption4());
            prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                prst.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Question getQuestionById(int id) {
        setConnection();
        String getQuery = "SELECT * FROM questions WHERE id = ?";
        setPreparedStatement(getQuery);
        try {
            prst.setInt(1, id);
            ResultSet rst = prst.executeQuery();
            if (rst.next()) {
                String question = rst.getString("question");
                String answer = rst.getString("answer");
                String option1 = rst.getString("option1");
                String option2 = rst.getString("option2");
                String option3 = rst.getString("option3");
                String option4 = rst.getString("option4");

                Question q = new Question(id, question, answer, option1, option2, option3, option4);
                return q;
            } else {
                System.out.println("Id bulunamadi");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            try {
                prst.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public List<Question> getQuestions() {
        setConnection();
        setStatement();
        List<Question> questions = new ArrayList<>();
        try {
            ResultSet rst = st.executeQuery("SELECT * FROM questions");
            while (rst.next()) {
                int id = rst.getInt("id");
                String question = rst.getString("question");
                String answer = rst.getString("answer");
                String option1 = rst.getString("option1");
                String option2 = rst.getString("option2");
                String option3 = rst.getString("option3");
                String option4 = rst.getString("option4");

                Question q = new Question(id, question, answer, option1, option2, option3, option4);
                questions.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return questions;
    }

    @Override
    public void updateQuestion(Question question) {
        setConnection();
        String updateQuery = "UPDATE questions SET question = ?, answer = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ? WHERE id = ?";
        setPreparedStatement(updateQuery);
        try {
            prst.setString(1, question.getQuestion());
            prst.setString(2, question.getAnswer());
            prst.setString(3, question.getOption1());
            prst.setString(4, question.getOption2());
            prst.setString(5, question.getOption3());
            prst.setString(6, question.getOption4());
            prst.setInt(7, question.getId());

            int result = prst.executeUpdate();
            String sonuc = result > 0 ? "Soru guncellendi" : "Guncellemede hata olustu";
            System.out.println(sonuc);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                prst.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void deleteQuestion(int id) {
        setConnection();
        String deleteQuery = "DELETE FROM questions WHERE id = ?";
        setPreparedStatement(deleteQuery);
        try {
            prst.setInt(1, id);
            int result = prst.executeUpdate();
            if (result > 0){
                System.out.println("Silme islemi basarili.");
            } else {
                System.out.println("Silme islemi sirasinda hata olustu.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {

                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
