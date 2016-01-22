package report.beans;

import lombok.Data;

import java.util.List;

@Data
public class MyReportBean
{
    @FieldHeader("PERSON FIRST NAME")
    private String name;
    @FieldHeader("PERSON LAST NAME")
    private String lastname;
    @FieldHeader("VALUE")
    private Double value;
    @FieldHeader("$diskSpeeds")
    private List<Long> disks;
}
