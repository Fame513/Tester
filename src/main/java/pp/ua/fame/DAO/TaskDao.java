package pp.ua.fame.DAO;

import pp.ua.fame.persistence.Task;

public interface TaskDao {
    Task getTask(long id);

    void addTask(String description, String source);

    void deleteTask(long id);

    void addTest(String test, String answer);

    void deleteTest(long id);
}
