package tn.esprit.gestionbancaire.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import tn.esprit.gestionbancaire.batch.JobCompletionListener;
import tn.esprit.gestionbancaire.batch.CreditProcessor;
import tn.esprit.gestionbancaire.enums.CreditStatus;
import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.repository.CreditRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Lazy
    private CreditRepository creditRepository;



    @Bean
    public RepositoryItemReader<Credit> reader() {
        RepositoryItemReader<Credit> reader = new RepositoryItemReader<>();
        reader.setRepository(creditRepository);
        reader.setMethodName("findAllByCreditStatus");

        List<Object> queryMethodArguments = new ArrayList<>();
        // for status
        queryMethodArguments.add(CreditStatus.OPEN);
        reader.setArguments(queryMethodArguments);
        reader.setPageSize(100);
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        reader.setSort(sorts);

        return reader;
    }



    @Bean
    public CreditProcessor processor() {
        return new CreditProcessor();
    }


    @Bean
    public Step step1(ItemReader<Credit> itemReader){
        return stepBuilderFactory.get("step1").<Credit, Credit> chunk(1)
				.reader(itemReader).processor(processor())
            .writer(Object::wait).build();

    }

    @Bean
    public Job profileUpdateJob(JobCompletionListener listener, Step step1)
            throws Exception {

        return this.jobBuilderFactory.get("profileUpdateJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(step1).build();
    }


}
