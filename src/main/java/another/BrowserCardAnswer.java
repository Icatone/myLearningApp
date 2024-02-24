package another;

public class BrowserCardAnswer {
    String question;
    String answer;

    public BrowserCardAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public BrowserCardAnswer() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
