package pp.ua.fame.model;

import javax.persistence.*;

@Entity
        //TODO Check table
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String test;
    private String answer;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "taskId")
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
