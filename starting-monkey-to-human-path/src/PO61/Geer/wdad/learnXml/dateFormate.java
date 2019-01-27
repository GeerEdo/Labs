package PO61.Geer.wdad.learnXml;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
public class dateFormate extends XmlAdapter<String, Date>{
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Date unmarshal(String v) throws Exception {
        return null;
    }

    @Override
    public String marshal(Date v) throws Exception {
        return null;
    }
}
