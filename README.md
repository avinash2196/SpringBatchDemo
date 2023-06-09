# SpringBatchDemo
sampleTasklet bean is created using the sampleTask class .
The sampleStep bean is then defined, which references the sampleTasklet bean. 
Finally, the sampleTaskletJob bean is created, which starts with the sampleStep.
runSampleJob finally schedules sampleTaskletJob.

Spring Batch uses a database-based job repository to store metadata about the job executions, job statuses, 
and other related information.
The DataSource is required to establish a connection to the database and store the job metadata.

However, it's important to note that not all tasklet jobs require a DataSource. 
If your job doesn't involve any database operations or you're using a non-relational data source,
you can configure your job to exclude the DataSource auto-configuration as did in withoutdatabase branch.

