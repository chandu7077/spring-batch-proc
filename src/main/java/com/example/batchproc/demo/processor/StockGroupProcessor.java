package com.example.batchproc.demo.processor;

import com.example.batchproc.demo.model.Stock;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;
import java.util.Map;

public class StockGroupProcessor implements ItemProcessor<Stock,Stock> {

    private Map<String,String> groupMapper = new HashMap<>();

    public StockGroupProcessor() {
        groupMapper.put("A","highly liquid");
        groupMapper.put("B","left behinf");
        groupMapper.put("T","trade-to-trade");
        groupMapper.put("S","small and medium");
        groupMapper.put("TS","mix of T and S");
        groupMapper.put("Z","risky or caution");
    }

    @Override
    public Stock process(Stock stock) throws Exception {
        String value = groupMapper.get(stock.getGroup());
        if(value == null) {
            stock.setGroup(stock.getGroup()+"/ "+"Unknown Group");
            return stock;
        }
        stock.setGroup(stock.getGroup()+" - "+value);
        return stock;
    }
}
