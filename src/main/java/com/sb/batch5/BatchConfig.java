package com.sb.batch5;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobRepository jobRepository;

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    @Bean
    public RestApiReader reader() {
        return new RestApiReader("http://demo3710532.mockable.io/person");
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JpaItemWriter<Person> writer() {
        return new JpaItemWriterBuilder<Person>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

//    @Bean
//    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
//        return jobRepository.createJobBuilder("importUserJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .flow(step1)
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step step1(PlatformTransactionManager transactionManager) {
//        return stepBuilderFactory.get("step1")
//                .<Person, Person>chunk(10)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .transactionManager(transactionManager)
//                .build();
//    }
//
//

    @Bean
    public Job importUserJob(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(importUserStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step importUserStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder("importUserStep", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
