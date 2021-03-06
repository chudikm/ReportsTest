package report.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

public class SimpleReport
{

    DefaultTableModel tableModel;

    public SimpleReport()
    {
        JasperPrint jasperPrint = null;
        TableModelData();
        try
        {
            JasperCompileManager.compileReportToFile("/home/milan/projects/innovation_days/JasperTest/src/main/resources/report/report.jrxml","/home/milan/projects/JasperTest/src/main/resources/report/report.jasper");
            jasperPrint = JasperFillManager.fillReport("/home/milan/projects/innovation_days/JasperTest/src/main/resources/report/report.jasper", new HashMap(),
                    new JRTableModelDataSource(tableModel));
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        }
        catch (JRException ex)
        {
            ex.printStackTrace();
        }

    }

    private void TableModelData()
    {
        String[] columnNames = {"Id", "Name", "Department", "Email"};
        String[][] data = {
                {"111", "G Conger", " Orthopaedic", "jim@wheremail.com"},
                {"222", "A Date", "ENT", "adate@somemail.com"},
                {"333", "R Linz", "Paedriatics", "rlinz@heremail.com"},
                {"444", "V Sethi", "Nephrology", "vsethi@whomail.com"},
                {"555", "K Rao", "Orthopaedics", "krao@whatmail.com"},
                {"666", "V Santana", "Nephrology", "vsan@whenmail.com"},
                {"777", "J Pollock", "Nephrology", "jpol@domail.com"},
                {"888", "H David", "Nephrology", "hdavid@donemail.com"},
                {"999", "P Patel", "Nephrology", "ppatel@gomail.com"},
                {"101", "C Comer", "Nephrology", "ccomer@whymail.com"}
        };
        tableModel = new DefaultTableModel(data, columnNames);
    }

    public static void main(String[] args)
    {
        new SimpleReport();
    }
}
