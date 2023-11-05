import java.util.Scanner;

public class Menu {
    QuestionService questionService = new QuestionService();
    QuestionPgRepository repository = new QuestionPgRepository();
    Scanner input = new Scanner(System.in);
    boolean isRun = true;

    public void displayMenu() {
        while (isRun) {
            questionService.createTable();
            System.out.println("=".repeat(30));
            System.out.println("MENU");
            System.out.println("1. Soru Ekle");
            System.out.println("2. Soru Sil");
            System.out.println("3. Soru Güncelle");
            System.out.println("4. Quiz Başlat");
            System.out.println("0. Çıkış");
            int select = input.nextInt();

            switch (select) {
                case 1:
                    questionService.addQuestion();
                    break;
                case 2:
                    questionService.deleteQuestion();
                    break;
                case 3:
                    questionService.updateQuestion();
                    break;
                case 4:
                    questionService.getAll();
                    break;
                case 0:
                    isRun = false;
                    break;
            }
        }
    }


}
