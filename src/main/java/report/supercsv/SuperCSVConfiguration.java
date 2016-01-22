package report.supercsv;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.FmtNumber;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import report.beans.FieldHeader;
import report.beans.MyReportBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperCSVConfiguration
{

    private String[] headerNames;
    private CellProcessor[] cellProcessors;
    private String[] fieldMappings;

    public SuperCSVConfiguration(String[] headerNames, CellProcessor[] cellProcessors, String[] fieldMappings)
    {
        this.headerNames = headerNames;
        this.cellProcessors = cellProcessors;
        this.fieldMappings = fieldMappings;
    }

    public String[] getHeaderNames()
    {
        return headerNames;
    }

    public CellProcessor[] getCellProcessors()
    {
        return cellProcessors;
    }

    public String[] getFieldMappings()
    {
        return fieldMappings;
    }

    public static class SuperCSVConfigurationBuilder
    {
        String headerTemplate;
        Map<String, CellProcessor> cellProcessorsConfigs = new HashMap<>();
        List<String> fieldMappings = new ArrayList<>();
        List<String> headerNames = new ArrayList<>();
        Map<String, List<String>> variableData  = new HashMap<>();
        List<CellProcessor> cellProcessors = new ArrayList<>();

        public SuperCSVConfigurationBuilder withHeaderTemplate(String headerTemplate)
        {
            this.headerTemplate = headerTemplate;
            return this;
        }

        public SuperCSVConfigurationBuilder withDiskSpeeds(List<String> diskSpeeds)
        {
            variableData.put("$diskSpeeds", diskSpeeds);
            return this;
        }

        public SuperCSVConfiguration build()
        {

            String[] headers = headerTemplate.split(",");
            for (String header : headers)
            {

                Field fieldName = getFieldByAnnotation(MyReportBean.class, header.trim());

                if (List.class.isAssignableFrom(fieldName.getType()))
                {
                    processList( header, fieldName);
                }
                else
                {
                    processSimpleField(header, fieldName);
                }
            }


            return new SuperCSVConfiguration(headerNames.toArray(new String[headerNames.size()]),
                    cellProcessors.toArray(new CellProcessor[cellProcessors.size()]),
                    fieldMappings.toArray(new String[fieldMappings.size()]));

        }

        private void processSimpleField(String header, Field fieldName)
        {
            CellProcessor cellProcessor = cellProcessorsConfigs.get(header.trim());
            if (cellProcessor != null)
            {
                cellProcessors.add(new Optional(cellProcessor));
            }
            else
            {
                cellProcessors.add(new Optional());
            }
            headerNames.add(header);
            fieldMappings.add(fieldName.getName());
        }

        private void processList( String header, Field fieldName)
        {
            CellProcessor cellProcessor = cellProcessorsConfigs.get(header.trim());
            List<String> data = variableData.get(header);
            int i = 0;
            for (String diskSpeed : data)
            {
                if (cellProcessor != null)
                {
                    cellProcessors.add(cellProcessorsConfigs.get(header));
                }
                else
                {
                    cellProcessors.add(new Optional());
                }
                headerNames.add(diskSpeed);
                fieldMappings.add(fieldName.getName()+"[" + (i++) + "]");
            }
        }

        private Field getFieldByAnnotation(Class<MyReportBean> myReportBeanClass, String header)
        {

            for (Field field : myReportBeanClass.getDeclaredFields())
            {
                FieldHeader fieldHeader = field.getAnnotation(FieldHeader.class);
                if (fieldHeader == null)
                {
                    continue;
                }
                if (header.equalsIgnoreCase(fieldHeader.value()))
                {
                    return field;

                }
            }
            return null;
        }

        public void addFieldFormatNumeric(String header, String format)
        {
            cellProcessorsConfigs.put(header.trim(), new Optional(new FmtNumber(format)));
        }

        public void addFieldFormatNumeric(String header, String format, Object defaultValue)
        {
            cellProcessorsConfigs.put(header.trim(), new ConvertNullTo(defaultValue,new FmtNumber(format)));
        }
    }

}
