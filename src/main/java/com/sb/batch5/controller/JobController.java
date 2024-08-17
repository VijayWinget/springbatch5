//package com.sb.batch5.controller;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecutionException;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/batch")
//public class JobController {
//
//	@Autowired
//	private JobLauncher jobLauncher;
//	@Autowired
//	private Job job;
//
//	@GetMapping("/run")
//	public ResponseEntity<String> runBatchJob() {
//		try {
//			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
//					.toJobParameters();
//
//			jobLauncher.run(job, jobParameters);
//
//			return ResponseEntity.ok("Batch job has been invoked");
//
//		} catch (JobExecutionException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Batch job failed: " + e.getMessage());
//		}
//	}
//
//}
