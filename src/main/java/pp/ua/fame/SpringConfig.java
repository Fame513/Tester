package pp.ua.fame;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@Configuration
public class SpringConfig {
    @Bean
    public EntityManager em(){
        return Persistence.createEntityManagerFactory("TesterDB").createEntityManager();
    }

    public ScriptEngine nashorn(){
        return new NashornScriptEngineFactory().getScriptEngine("--no-java");
    }

    public ScriptEngine rhino(){
        return new ScriptEngineManager().getEngineByName("rhino");
    }

    @Bean
    public ScriptEngine scriptEngine(){
        return nashorn();
    }
}
