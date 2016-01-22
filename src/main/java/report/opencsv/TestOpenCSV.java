package report.opencsv;

import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import report.beans.MyReportBean;


import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TestOpenCSV
{
    public static void main(String... args) throws IOException
    {
        ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
        strat.setType(MyReportBean.class);
        String[] columns = new String[]{"name", "lastname"}; // the fields to bind do in your JavaBean
        strat.setColumnMapping(columns);

        MyReportBean myReportBean = new MyReportBean();

        myReportBean.setName("MILAN");
        myReportBean.setLastname("CH");
        myReportBean.setValue(1.4567);

        List<MyReportBean> myReportBeanList = new ArrayList<>();
        myReportBeanList.add(myReportBean);

        myReportBean = new MyReportBean();

        myReportBean.setName("DD");
        myReportBean.setLastname("CbnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnH2");
        myReportBean.setValue(2.1567);
        myReportBeanList.add(myReportBean);

        StringWriter sw = new StringWriter();

        BeanToCsv beanToCsv = new BeanToCsv();
        beanToCsv.write(strat, sw, myReportBeanList);


        System.out.println(sw.getBuffer().toString());

    }
}
