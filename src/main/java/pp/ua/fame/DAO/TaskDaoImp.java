package pp.ua.fame.DAO;

import pp.ua.fame.persistence.Task;

import javax.persistence.EntityManager;

public class TaskDaoImp implements TaskDao{
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

    }

    @Override
    public void deleteTask(long id) {

    }

    @Override
    public void addTest(String test, String answer) {

    }

    @Override
    public void deleteTest(long id) {

    }
}
