package pp.ua.fame.model;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String description;

    @Column(name = "source")
    private String Skeleton;

    @OneToMany(mappedBy = "task")
    private List<Test> tests;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkeleton() {
        return Skeleton;
    }

    public void setSkeleton(String skeleton) {
        Skeleton = skeleton;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}
