package report.supercsv;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.util.Map;

public class MapProcessor extends CellProcessorAdaptor
{
    String key;

    public MapProcessor(String key)
    {
        super();
        this.key = key;
    }

    public MapProcessor(String key,CellProcessor next)
    {
        // this constructor allows other processors to be chained after ParseDay
        super(next);
        this.key = key;
    }

    public Object execute(Object value, CsvContext context)
    {

        if(value==null){
           return next.execute(value, context);
        }
        Map mapValue = (Map) value;

        Object result = mapValue.get(key);
        return next.execute(result, context);
    }
}
