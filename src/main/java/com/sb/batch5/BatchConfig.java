package com.sb.batch5;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobRepository jobRepository;

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    @Bean
    RestApiReader reader() {
        return new RestApiReader("http://localhost:3000/user");
    }

    @Bean
    PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    JpaItemWriter<Person> writer() {
        return new JpaItemWriterBuilder<Person>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    Job importUserJob(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(importUserStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    Step importUserStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder("importUserStep", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    
    
    
    
    //Job Two Test
    @Bean
    Job jobingTwo(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
    	return new JobBuilder("jobingTwo", jobRepository)
    			.start(importUserStep(jobRepository, transactionManager))
    			.build();
    }
    
    @Bean
    Step jobingTwoSteping(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
    	return new StepBuilder("jobingTwoSteping", jobRepository)
    			.<Person, Person>chunk(10, transactionManager)
    			.reader(reader())
    			.processor(processor())
    			.writer(writer())
    			.build();
    }

}
