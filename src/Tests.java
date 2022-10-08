import jdk.jfr.StackTrace;
import model.Database;
import model.Person;
import model.Product;
import model.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import util.Gender;
import util.exceptions.NoSuchPersonException;
import util.exceptions.NoSuchProductException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tests {
    private final Database database = Reader.readFileToDatabase(Main.pathToFile);

    @Test
    public void testAll() {
        try {
            // Check if file was read correctly
            Assertions.assertNotEquals(null, database);

            test_searchPerson();
            test_searchProduct();

            test_productNetwork();
            test_companyNetwork();

            System.out.println("Alles bestanden!");
        } catch (AssertionFailedError e1) {
            System.err.println("Folgender Test wurde nicht bestanden: " + e1.getStackTrace()[5].getMethodName());
            if (e1.getActual() != null) {
                System.err.println("Bekommener Wert: " + e1.getActual().getValue());
                System.err.println("Erwarteter Wert: " + e1.getExpected().getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_searchPerson() throws NoSuchPersonException {
        if (database != null) {
            Person p = database.getPersonById(13);
            Assertions.assertEquals("Raymond Wong", p.getName());
            Assertions.assertEquals(6, p.getFriends().size());

            p = database.getPersonById(45);
            Assertions.assertEquals("Danny Watkins", p.getName());
            Assertions.assertEquals(0, p.getProducts().size());

            p = database.getPersonById(1);
            Assertions.assertEquals(Gender.MALE, p.getGender());
            Assertions.assertTrue(p.hasFriend(database.getPersonById(140)));

            // Two people have "Roger" in their name
            List<Person> results = database.getPeopleByName("Roger").collect(Collectors.toList());
            Assertions.assertEquals(2, results.size());
        }
    }

    @Test
    public void test_searchProduct() throws NoSuchProductException {
        if (database != null) {
            Product p = database.getProductById(205);
            Assertions.assertEquals("iPad Mini", p.getName());
            Assertions.assertEquals("Apple", p.getMadeBy().getName());

            p = database.getProductById(210);
            Assertions.assertEquals("Samsung Galaxy Tab 3", p.getName());
            Assertions.assertEquals("Samsung", p.getMadeBy().getName());

            // Three products contain the sequence "iP" in their name
            List<Product> results = database.getProductsByName("iP").collect(Collectors.toList());
            Assertions.assertEquals(3, results.size());
        }
    }

    @Test
    public void test_productNetwork() throws NoSuchPersonException {
        if (database != null) {
            Assertions.assertEquals("Google Nexus 7,iPhone,MacBook Air,Samsung Galaxy Tab 3", Main.createOutputString(database.getNetwork(database.getPersonById(15), Person::getProducts)));
            Assertions.assertEquals("Google Nexus 7,MacBook Pro", Main.createOutputString(database.getNetwork(database.getPersonById(1), Person::getProducts)));
        }
    }

    @Test
    public void test_companyNetwork() throws NoSuchPersonException {
        if (database != null) {
            Assertions.assertEquals("Apple", Main.createOutputString(database.getNetwork(database.getPersonById(69), Person::getCompanies)));
            Assertions.assertEquals("Samsung", Main.createOutputString(database.getNetwork(database.getPersonById(23), Person::getCompanies)));
        }
    }
}
