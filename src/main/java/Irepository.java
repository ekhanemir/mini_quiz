import java.util.List;

public interface Irepository {
    public void insertQuestion(Question question);

    public Question getQuestionById(int id);

    public List<Question> getQuestions();

    public void updateQuestion(Question question);

    public void deleteQuestion(int id);

    public void createTable();
}
