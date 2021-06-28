package com.example.batchproc.demo.config;

import com.example.batchproc.demo.model.Stock;
import com.example.batchproc.demo.listener.JobCompletionNotificationListener;
import com.example.batchproc.demo.processor.StockGroupProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Stock> writer) {
        return stepBuilderFactory.get("step1")
                .<Stock, Stock> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    private LineMapper<Stock> createStockLineMapper() {
        DefaultLineMapper<Stock> studentLineMapper = new DefaultLineMapper<>();

        LineTokenizer stockLineTokenizer = createStockLineTokenizer();
        studentLineMapper.setLineTokenizer(stockLineTokenizer);

        FieldSetMapper<Stock> stockInformationMapper =
                createStockInformationMapper();
        studentLineMapper.setFieldSetMapper(stockInformationMapper);

        return studentLineMapper;
    }

    private LineTokenizer createStockLineTokenizer() {
        DelimitedLineTokenizer stockLineTokenizer = new DelimitedLineTokenizer();
        stockLineTokenizer.setDelimiter(",");
        stockLineTokenizer.setNames(new String[]{"Security_Code","Issuer_Name","Security_Id","Security_Name","Status","Group",
                "Face_Value","ISIN_No","Industry","Instrument"});
        return stockLineTokenizer;
    }

    private FieldSetMapper<Stock> createStockInformationMapper() {
        BeanWrapperFieldSetMapper<Stock> stockInformationMapper =
                new BeanWrapperFieldSetMapper<>();
        stockInformationMapper.setTargetType(Stock.class);
        return stockInformationMapper;
    }

    @Bean
    public FlatFileItemReader<Stock> reader() {
        LineMapper<Stock> stockLineMapper = createStockLineMapper();

        return new FlatFileItemReaderBuilder<Stock>()
                .name("stockItemReader")
                .resource(new ClassPathResource("Equity.csv"))
                .linesToSkip(1)
                .lineMapper(stockLineMapper)
                .build();
    }

    @Bean
    public StockGroupProcessor processor() {
        return new StockGroupProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Stock> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Stock>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO equity VALUES (:securityCode,:issuerName,:securityId,:securityName,:status,:group,:faceValue,:ISIN,:industry,:instrument)")
                .dataSource(dataSource)
                .build();
    }
}
