package pp.ua.fame.dao;

import pp.ua.fame.model.Task;

public interface TaskDao {
    Task getTask(long id);

    void addTask(String description, String source);

    void deleteTask(long id);

    void addTest(String test, String answer);

    void deleteTest(long id);
}
