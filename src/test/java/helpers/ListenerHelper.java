package helpers;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerHelper implements ITestListener
{
    public void onTestFailure(ITestResult result)
    {
        System.out.println("test is failed -listener : "+result.getName());
    }

//    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) {
//
//        //Convert web driver object to TakeScreenshot
//
//        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
//
//        //Call getScreenshotAs method to create image file
//
//                File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
//
//            //Move image file to new destination
//
//                File DestFile = new File(fileWithPath);
//
//                //Copy file at destination
//
//                FileUtils.copyFile(SrcFile, DestFile);
//
//    }
}
