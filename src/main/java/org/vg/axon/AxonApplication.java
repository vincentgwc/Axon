package org.vg.axon;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.gateway.IntervalRetryScheduler;
import org.axonframework.commandhandling.gateway.RetryScheduler;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AxonApplication {

	public static void main(String[] args) {
		SpringApplication.run(AxonApplication.class, args);
	}
	
	@Bean
	public CommandGateway commandGateway() {
		Configurer configurer = DefaultConfigurer.defaultConfiguration();
		CommandBus commandBus = configurer.buildConfiguration().commandBus();
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		RetryScheduler rs = IntervalRetryScheduler.builder().retryExecutor(scheduledExecutorService).maxRetryCount(5).retryInterval(1000).build();
		CommandGateway commandGateway = DefaultCommandGateway.builder().commandBus(commandBus).retryScheduler(rs).build();
		return commandGateway;
	}

}
