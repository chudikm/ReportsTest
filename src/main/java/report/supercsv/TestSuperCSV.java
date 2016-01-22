package report.supercsv;

import com.google.common.collect.Lists;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.io.dozer.ICsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;
import report.beans.MyReportBean;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TestSuperCSV
{
    public static void main(String... args) throws IOException
    {

        System.out.println("Going to generate data ");
        List<MyReportBean> myReportBeanList = generateData();


        StringWriter sw = new StringWriter();

        ICsvDozerBeanWriter beanWriter = new CsvDozerBeanWriter(sw, CsvPreference.STANDARD_PREFERENCE);


        SuperCSVConfiguration.SuperCSVConfigurationBuilder configurationBuilder = new SuperCSVConfiguration.SuperCSVConfigurationBuilder();
        configurationBuilder.withHeaderTemplate("PERSON FIRST NAME,PERSON LAST NAME,VALUE,$diskSpeeds");
        configurationBuilder.addFieldFormatNumeric("VALUE", "0.00");
        configurationBuilder.addFieldFormatNumeric("$diskSpeeds", "0.00");
        configurationBuilder.withDiskSpeeds(getDiskSpeeds());

        SuperCSVConfiguration configuration = configurationBuilder.build();
        beanWriter.configureBeanMapping(MyReportBean.class, configuration.getFieldMappings());

        System.out.println("Going to generate report ");
        long start = System.currentTimeMillis();

        // write the header
        beanWriter.writeHeader(configuration.getHeaderNames());
        CellProcessor[] processors = configuration.getCellProcessors();
        // write the beans
        for (final MyReportBean customer : myReportBeanList)
        {
            beanWriter.write(customer, processors);
        }
        beanWriter.close();

        long end = System.currentTimeMillis();
        System.out.println("Finished generate report ");


        System.out.println("-----------------------------");
        System.out.println(sw.getBuffer().toString());
        System.out.println("-----------------------------");
        System.out.println("Time to generate report: " + (end - start) + " ms");

    }

    private static List<MyReportBean> generateData()
    {
        MyReportBean myReportBean = new MyReportBean();

        myReportBean.setName("MILAN");
        myReportBean.setLastname("CH");
        myReportBean.setValue(1.4567);
        List<Long> disks = new ArrayList<>();
        disks.add(2l);//STANDARD
        disks.add(null);//ECONOMY
        disks.add(null);//HIGHPERFORMANCE
        myReportBean.setDisks(disks);


        List<MyReportBean> myReportBeanList = new ArrayList<>();
        myReportBeanList.add(myReportBean);

        myReportBean = new MyReportBean();

        myReportBean.setName("DD");
        myReportBean.setLastname("Dimension, data ireland");
        myReportBean.setValue(12.1567);
        myReportBeanList.add(myReportBean);
        disks = new ArrayList<>();
        disks.add(null);//STANDARD
        disks.add(null);//ECONOMY
        disks.add(30l);//HIGHPERFORMANCE
        myReportBean.setDisks(disks);
        return myReportBeanList;
    }

    private static List<MyReportBean> generateBigData()
    {
        List<MyReportBean> myReportBeanList = new ArrayList<>();

        for (int i = 0; i < 1000000; i++)
        {
            MyReportBean myReportBean = new MyReportBean();
            myReportBean.setName("MILAN" + i);
            myReportBean.setLastname("CH" + i);
            myReportBean.setValue(1.4567 * i);
            List<Long> disks = new ArrayList<>();
            disks.add(2l * i);//STANDARD
            disks.add(null);//ECONOMY
            disks.add(null);//HIGHPERFORMANCE
            myReportBean.setDisks(disks);

            myReportBeanList.add(myReportBean);
        }


        return myReportBeanList;
    }


    private static List<String> getDiskSpeeds()
    {
        return Lists.newArrayList("STANDARD", "ECONOMY", "HIGHSPEED");
    }
}
