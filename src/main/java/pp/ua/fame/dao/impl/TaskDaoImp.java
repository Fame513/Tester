package pp.ua.fame.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pp.ua.fame.dao.TaskDao;
import pp.ua.fame.model.Task;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;

@Component()
public class TaskDaoImp implements TaskDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Task getTask(long id) {
        return entityManager.find(Task.class, id);
    }

    @Override
    public void addTask(String description, String source) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteTask(long id) {
        throw new NotImplementedException();
    }

    @Override
    public void addTest(String test, String answer) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteTest(long id) {
        throw new NotImplementedException();
    }
}
