package com.jaewoo.srs.batch.app

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TaskletJob(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun taskletJobBatchBuild(): Job {
        return jobBuilderFactory.get("taskletJob")
            .start(taskletJobStep1())
            .next(taskletJobStep2(null))
            .build()
    }

    @Bean
    fun taskletJobStep1(): Step {
        return stepBuilderFactory.get("taskletJobStep1")
            .tasklet { contribution: StepContribution, chunkContext: ChunkContext ->
                println("job -> step1 : ok!!")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    @JobScope
    fun taskletJobStep2(@Value("#{jobParameters[date]}") date: String?): Step {
        return stepBuilderFactory.get("taskletJobStep2")
            .tasklet { contribution: StepContribution, chunkContext: ChunkContext ->
                println("step1 -> step2 : ${date}")
                RepeatStatus.FINISHED
            }.build()
    }
}
