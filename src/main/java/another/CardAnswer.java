package another;

public class CardAnswer {
    private Card card;
    private String answer;

    public CardAnswer(){

    }

    public CardAnswer(Card card,String answer) {
        this.card = card;
        this.answer = answer;
    }

    public Card getCard() {
        return card;
    }

    public boolean isAnswerRight(){
        return answer.equals(card.getAnswer());
    }

    public String getAnswer() {
        return answer;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
