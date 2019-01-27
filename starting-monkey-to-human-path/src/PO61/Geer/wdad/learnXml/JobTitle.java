package PO61.Geer.wdad.learnXml;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class JobTitle {
    JobTitleEnum value;

    public JobTitleEnum getValue() {
        return value;
    }

    @XmlAttribute
    public void setValue(JobTitleEnum value) {
        this.value = value;
    }
}