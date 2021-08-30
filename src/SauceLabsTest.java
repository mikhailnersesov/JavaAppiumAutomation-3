import lib.CoreTestCaseSauceLabs;
import lib.ui.MainPageObjectSauceLabs;
import lib.ui.SearchPageObjectSauceLabs;
import org.junit.Test;

public class SauceLabsTest extends CoreTestCaseSauceLabs {

    private MainPageObjectSauceLabs MainPageObjectSauceLabs;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObjectSauceLabs = new MainPageObjectSauceLabs(driver);
    }

    @Test
    public void testSearch() {

        SearchPageObjectSauceLabs SearchPageObjectSauceLabs = new SearchPageObjectSauceLabs(driver);

        SearchPageObjectSauceLabs.Login("standard_user","secret_sauce");
        SearchPageObjectSauceLabs.PutIntoCart();
        SearchPageObjectSauceLabs.CleanTheCart();

        System.out.println("Test succeeded");
    }
}
