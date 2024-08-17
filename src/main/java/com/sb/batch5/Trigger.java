package com.sb.batch5;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Trigger implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job1; // Inject your jobs

	@Autowired
	private Job job2;

	@Override
	public void run(String... args) throws Exception {
		// Check if a job name was passed as a command-line argument
		if (args.length > 0) {
			String jobName = args[0];

			if ("job1".equals(jobName)) {
				// Run job1
				jobLauncher.run(job1,
						new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
			} else if ("job2".equals(jobName)) {
				// Run job2
				jobLauncher.run(job2,
						new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
			} else {
				System.out.println("No matching job found.");
			}
		} else {
			System.out.println("No job name provided.");
		}

	}

}
