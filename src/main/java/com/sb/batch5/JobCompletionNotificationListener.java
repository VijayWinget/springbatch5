package com.sb.batch5;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job Started!");
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        log.info("Job Finished!");
        List<Person> results = entityManager.createQuery("from Person", Person.class).getResultList();
        for (Person person : results) {
            log.info("Found <" + person + "> in the database.");
        }
    }
}
