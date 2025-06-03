package br.com.itau.geradornotafiscal.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;

@EnableAsync
@Configuration
public class AsyncConfig  implements AsyncConfigurer {

    @Bean
    @Primary
    public AsyncTaskExecutor asyncTaskExecutor(){
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }
}
