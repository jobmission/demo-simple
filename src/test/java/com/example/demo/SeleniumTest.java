package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    @Disabled
    @Test
    public void autoTest() throws Exception {
//        谷歌浏览器的驱动下载地址：https://chromedriver.storage.googleapis.com/index.html
//        最新稳定版下载地址：https://chromedriver.storage.googleapis.com/index.html?path=2.40/
        System.setProperty("webdriver.chrome.driver", "E:\\webdriver\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
//        火狐浏览器的驱动下载地址：https://github.com/mozilla/geckodriver/releases
//        System.setProperty("webdriver.gecko.driver", "D://selenium/geckodriver.exe");
//        WebDriver webDriver = new FirefoxFilter();

//        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        // 与浏览器同步非常重要，必须等待浏览器加载完毕
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //打开目标地址
        webDriver.get("https://www.baidu.com");

        Thread.sleep(1000);

        //输入关键字搜索
        webDriver.findElement(By.cssSelector("input#kw")).sendKeys("海康威视");
        webDriver.findElement(By.cssSelector("input#su")).click();
        Thread.sleep(3000);

        webDriver.findElement(By.partialLinkText("资讯")).click();
        Thread.sleep(3000);

        List<String> urls = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            pageLoop(webDriver, urls);
        }

        //暂停5秒钟后关闭
        Thread.sleep(5000);
//        webDriver.quit();

//        urls.forEach(url -> {
//            webDriver.get(url);
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("title:" + webDriver.getTitle());
//        });

        //暂停5秒钟后关闭
        Thread.sleep(2000);
        webDriver.quit();

        System.out.println("urls:" + urls);

    }


    public void pageLoop(WebDriver webDriver, List<String> urls) {

        webDriver.findElements(By.cssSelector(".result")).forEach(x -> {
            String title = x.findElement(By.cssSelector(".c-title a")).getText();
            String url = x.findElement(By.cssSelector(".c-title a")).getDomAttribute("href");
            String abstractContent = x.findElement(By.cssSelector(".c-summary")).getText();
            System.out.println(title);
            urls.add(url);
            System.out.println(url);
            System.out.println(abstractContent);
            System.out.println("--------------------");
        });

        webDriver.findElement(By.partialLinkText("下一页")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, 700)");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int hashValue(char value[]) {
        int h = 0;
        if (h == 0 && value.length > 0) {
            char val[] = value;
            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
        }
        return h;
    }
}
