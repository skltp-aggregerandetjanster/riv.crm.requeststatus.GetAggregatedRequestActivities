package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RequestCategoryTest {

  private static GARAAgpServiceFactoryImpl serviceFactory = new GARAAgpServiceFactoryImpl();

  private static final String RONTGEN = "1";
  private static final String LABB = "2";
  private static final String ALLMAN = "4";
  private static final String FYSIOLOG = "10";


  @Test
  public void nullCategoryMeansAllCategoriesAreCorrect(){
    boolean result = serviceFactory.isCorrectCategory(null, RONTGEN);
    assertTrue(result);
  }

  @Test
  public void emptyCategoryMeansAllCategoriesAreCorrect(){
    List<String> reqTypeOfRequestList = new ArrayList<String>();
    boolean result = serviceFactory.isCorrectCategory(reqTypeOfRequestList, RONTGEN);
    assertTrue(result);
  }

  @Test
  public void whenExactCorrectCategoryReturnTrue(){
    List<String> reqTypeOfRequestList = Collections.singletonList(RONTGEN);
    boolean result = serviceFactory.isCorrectCategory(reqTypeOfRequestList, RONTGEN);
    assertTrue(result);
  }

  @Test
  public void atLeastOneCorrectCategoryReturnTrue(){
    List<String> reqTypeOfRequestList = Arrays.asList(RONTGEN,LABB,ALLMAN,FYSIOLOG);
    boolean result = serviceFactory.isCorrectCategory(reqTypeOfRequestList, RONTGEN);
    assertTrue(result);
  }

  @Test
  public void whenNoExactCorrectCategoryReturnFalse(){
    List<String> reqTypeOfRequestList = Collections.singletonList(LABB);
    boolean result = serviceFactory.isCorrectCategory(reqTypeOfRequestList, RONTGEN);
    assertFalse(result);
  }

  @Test
  public void whenNoCorrectCategoryInListReturnFalse(){
    List<String> reqTypeOfRequestList = Arrays.asList(LABB,ALLMAN,FYSIOLOG);
    boolean result = serviceFactory.isCorrectCategory(reqTypeOfRequestList, RONTGEN);
    assertFalse(result);
  }

}
