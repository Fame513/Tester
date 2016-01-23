package pp.ua.fame.dao.impl;

import pp.ua.fame.dao.TaskDao;
import pp.ua.fame.model.Task;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;

public class TaskDaoImp implements TaskDao {
    private EntityManager em;

    public TaskDaoImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public Task getTask(long id) {
        return em.find(Task.class, id);
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
