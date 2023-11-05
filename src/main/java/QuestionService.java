import java.util.List;
import java.util.Scanner;

public class QuestionService {

    QuestionPgRepository repository = new QuestionPgRepository();
    Question quiz = new Question();
    Scanner input = new Scanner(System.in);

    public void createTable(){
        repository.createTable();
    }

    public void addQuestion() {
        System.out.println("Lütfen soru giriniz.");
        String question = input.nextLine();
        quiz.setQuestion(question);

        System.out.println("Lütfen dogru şıkkı giriniz(a/b/c/d) cevap giriniz.");
        String answer = input.nextLine();
        quiz.setAnswer(answer);

        System.out.println("Lütfen a şıkkını giriniz.");
        String option1 = input.nextLine();
        quiz.setOption1(option1);

        System.out.println("Lütfen b şıkkını giriniz.");
        String option2 = input.nextLine();
        quiz.setOption2(option2);

        System.out.println("Lütfen c şıkkını giriniz.");
        String option3 = input.nextLine();
        quiz.setOption3(option3);

        System.out.println("Lütfen d şıkkını giriniz.");
        String option4 = input.nextLine();
        quiz.setOption4(option4);

        repository.insertQuestion(quiz);
    }

    public void getAll() {
        listQuestions("getAll");

    }

    public void listQuestions(String select){
        Irepository questionRepository = new QuestionPgRepository();
        List<Question> questions = questionRepository.getQuestions();
        int counter = 1;
        int score = 0;

        for (Question question : questions) {
            if(select.equals("listAll")) System.out.print("ID " + question.getId() + " - ");
            System.out.println("Soru " + counter + " : " + question.getQuestion());
            System.out.println("A - " + question.getOption1());
            System.out.println("B - " + question.getOption2());
            System.out.println("C - " + question.getOption3());
            System.out.println("D - " + question.getOption4());

            if(select.equals("getAll")){
                System.out.println("Cevap seciniz");
                String cevap = input.next();
                if (cevap.equalsIgnoreCase(question.getAnswer())) {
                    score += 10;
                    System.out.println("Dogru cevap puaniniz " + score);
                } else {
                    System.out.println("Yanlis cevap puaniniz " + score);
                }
                counter++;
            } else if (select.equals("listAll")) {
                System.out.println("Dogru Cevap - " + question.getAnswer());
            }
        }
    }

    public void deleteQuestion(){
        System.out.println("Sorulari listelemek ister misniz (e/h).");
        String answer = input.next();

        if(answer.equalsIgnoreCase("e")) listQuestions("listAll");

        System.out.println("Silmek istediginiz sorunun ID sini giriniz.");
        int id = input.nextInt();
        repository.deleteQuestion(id);
    }

    public void updateQuestion(){
        System.out.println("Sorulari listelemek ister misniz (e/h).");
        String answer = input.next();

        if(answer.equalsIgnoreCase("e")) listQuestions("listAll");

        System.out.println("Guncellemek istediginiz sorunun ID sini giriniz.");
        int id = input.nextInt();
        input.nextLine();

        Question ques = repository.getQuestionById(id);
        System.out.println(ques);

        String str = ques.getQuestion();
        System.out.println("Yeni soru giriniz.");
        System.out.print(str);
        String soru = input.nextLine();
        ques.setQuestion(soru);

        System.out.println("Yeni a şıkkı giriniz.");
        System.out.print(ques.getOption1());
        String option1 = input.nextLine();
        ques.setOption1(option1);

        System.out.println("Yeni b şıkkı giriniz.");
        System.out.print(ques.getOption2());
        String option2 = input.nextLine();
        ques.setOption2(option2);

        System.out.println("Yeni c şıkkı giriniz.");
        System.out.print(ques.getOption3());
        String option3 = input.nextLine();
        ques.setOption3(option3);

        System.out.println("Yeni d şıkkı giriniz.");
        System.out.print(ques.getOption4());
        String option4 = input.nextLine();
        ques.setOption4(option4);

        System.out.println("Yeni dogru cevabı giriniz.");
        System.out.print(ques.getAnswer());
        String newAnswer = input.nextLine();
        ques.setAnswer(newAnswer);

        repository.updateQuestion(ques);


    }

}
