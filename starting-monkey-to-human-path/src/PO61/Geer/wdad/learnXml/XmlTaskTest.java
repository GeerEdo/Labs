package PO61.Geer.wdad.learnXml;

public class XmlTaskTest {
    public static void main(String[] args) {
        try {
            XmlTask xmlTask = new XmlTask("C:\\Users\\Кто то там\\Desktop\\Labs\\starting-monkey-to-human-path\\src\\PO61\\Geer\\wdad\\learnXml\\first.xml");
            System.out.println(xmlTask.salaryAverage());
            System.out.println(xmlTask.salaryAverage("Managers"));

            xmlTask.setJobTitle("Johny", "Zurich", JobTitleEnum.assistant);
            xmlTask.setSalary("Alex", "Merge", 20000);
            xmlTask.fireEmployee("Serge", "Hire");

            xmlTask.save("C:\\Users\\Кто то там\\Desktop\\Labs\\starting-monkey-to-human-path\\src\\PO61\\Geer\\wdad\\learnXml\\sekond.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}