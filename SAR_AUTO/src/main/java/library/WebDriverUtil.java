package library;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/** WebDriver ユーティリティクラス  */
public class WebDriverUtil {
	protected WebDriver _driver;
	protected WebDriverWait _wait;
	
	/** ブラウザ種類(Internet Explorer) */
	public static final String BLOWSER_IE = "internetexplorer";
	/** ブラウザ種類(Google Chrome) */
	public static final String BLOWSER_CHROME = "googlechrome";
	/** ブラウザ種類(Firefox) */
	public static final String BLOWSER_FIREFOX = "firefox";
	/** ブラウザ一覧 */
	public static final String[] BLOWSER_LIST = {
			BLOWSER_IE, BLOWSER_CHROME, BLOWSER_FIREFOX
	};
	
	/** WebDriver最大待機時間(秒) */
	protected static final int MAX_WAIT_TIME = 30;
	
	public WebDriverUtil(WebDriver driver) {
		_driver = driver;
		_wait = new WebDriverWait(_driver, MAX_WAIT_TIME);
		_driver.manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
	}

	
	/**
	 * WebDriverServerへexeを登録し、InternetExplorerDriverインスタンスを返却します。
	 * @return InternetExplorerDriverインスタンス
	 */
	public static WebDriver getWebDriver(String blowserName, String blowserBinary) {
		WebDriver driver = null;
		
		if (blowserName.equals(WebDriverUtil.BLOWSER_IE)) {
			driver = WebDriverUtil.getInternetExplorerDriver();
		} else if (blowserName.equals(WebDriverUtil.BLOWSER_CHROME)) { 
			driver = WebDriverUtil.getChromeDriver();
		} else if (blowserName.equals(WebDriverUtil.BLOWSER_FIREFOX)) {
	    	if (blowserBinary != null && blowserBinary.equals("")) {
		    	driver = WebDriverUtil.getFirefoxDriver(blowserBinary);
	    	} else {
	    		driver = WebDriverUtil.getFirefoxDriver();
	    	}
		}
		return driver;
	}
	
	/**
	 * WebDriverServerへexeを登録し、WebDriverインスタンスを返却します。
	 * @param blowserName ブラウザ名(internetexplorer, googlechrome, firefox)
	 * @return InternetExplorerDriverインスタンス
	 */
	public static WebDriver getWebDriver(String blowserName) {
		return getWebDriver(blowserName, null);
	}
	
	/**
	 * WebDriverServerへexeを登録し、InternetExplorerDriverインスタンスを返却します。
	 * @return InternetExplorerDriverインスタンス
	 */
	private static WebDriver getInternetExplorerDriver() {
		String path = ResourceLoader.getExeDirPath() + "IEDriverServer.exe";
		System.setProperty("webdriver.ie.driver", path);
		return new InternetExplorerDriver();
	}

	/**
	 * WebDriverServerへexeを登録し、ChromeDriverインスタンスを返却します。
	 * @return ChromeDriverインスタンス
	 */
	private static WebDriver getChromeDriver() {
		return _getChromeDriver();
	}

	/**
	 * WebDriverServerへexeを登録し、ChromeDriverインスタンスを返却します。
	 * @param binary : ブラウザのバイナリファイルのパス
	 * @return ChromeDriverインスタンス
	 */
	public static WebDriver getChromeDriver(String binary) {
		if(binary != null && !binary.equals("")) {
			System.setProperty("webdriver.chrome.bin", binary);
		}
		return _getChromeDriver();
	}

	private static WebDriver _getChromeDriver() {
		String path = ResourceLoader.getExeDirPath() + "chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", path);
			
		ChromeOptions options = new ChromeOptions();
		List<String> args = Arrays.asList(
			// --ignore-certificate-errorの出力抑止
			"test-type",
			// 拡張機能無効化しない
			"--disable-extensions=false"
		);
		options.addArguments(args);
		return new ChromeDriver(options);
	}
	
	/**
	 * WebDriverServerへexeを登録し、FirefoxDriverインスタンスを返却します。
	 * @return FirefoxDriverインスタンス
	 */
	private static WebDriver getFirefoxDriver() {
		String path = ResourceLoader.getExeDirPath() + "wires.exe";
		System.setProperty("webdriver.gecko.driver", path);
		return new FirefoxDriver();
	}

	/**
	 * WebDriverServerへexeを登録し、FirefoxDriverインスタンスを返却します。
	 * @param binary : ブラウザのバイナリファイルのパス
	 * @return FirefoxDriverインスタンス
	 */
	private static WebDriver getFirefoxDriver(String binary) {
		if (binary != null && !binary.equals("")) {
			System.setProperty("webdriver.firefox.bin", binary);
		}
		return getFirefoxDriver();
	}

	/** 
	 * 指定されたURLへ画面遷移を行う
	 * @param url: 遷移先URL
	 */
	public void navigate(String url) {
		_driver.get(url);
	}

	/**
	 * 要素セレクタをからエレメントを取得する(要素が可視状態まで待機する)
	 * @param selector: CSSセレクタ
	 * @param millis : 待機時間(ミリ秒)
	 */
	public WebElement findElement(String selector, long millis) {
		WebElement elem = _wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector(selector))));
		waitLoad(millis);
		return elem;
	}

	/**
	 * 要素セレクタをからエレメントを取得する(要素が可視状態まで待機する)
	 * @param selector: CSSセレクタ
	 */
	public WebElement findElement(String selector) {
		WebElement elem = _wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector(selector))));
		waitLoad(300);
		return elem;
	}
	
	/**
	 * 要素セレクタをからエレメントを取得する(複数)
	 * @param selector: CSSセレクタ
	 */
	public List<WebElement> findElements(String selector) {
		return _driver.findElements(By.cssSelector(selector));
	}
	
	/**
	 * 指定された要素へ値を入力する(要素がクリック可能状態まで待機する)
	 * @param selector: CSSセレクタ
	 * @param value : 入力情報
	 */
	public void typeValue(String selector, String value) {
		findElement(selector).sendKeys(value);
	}
	
	/**
	 * 指定された要素をクリックする(要素がクリック可能状態まで待機する)
	 * @param selector : CSSセレクタ
	 */
	public void click(String selector) {
		findElement(selector).click();
	}
	
	/**
	 * 指定された要素をクリックする(要素がクリック可能状態まで待機する)
	 * @param selector : CSSセレクタ
	 * @param millis : 待機時間(ミリ秒)
	 */
	public void click(String selector, long millis) {
		findElement(selector, millis).click();
	}
	
	/**
	 * 指定された要素をクリックする(要素がクリック可能状態まで待つ)
	 * @param selector : CSSセレクタ
	 */
	public void submit(String selector) {
		findElement(selector).submit();
	}
	
	/** 
	 * 指定された要素をクリックする(要素がクリック可能状態まで待つ)
	 * @param selector : CSSセレクタ
	 * @param millis : 待機時間(ミリ秒)
	 */
	public void submit(String selector, long millis) {
		findElement(selector, millis).submit();
	}
	
	/**
	 * 指定されたselect要素をvalue値をもとに選択する(要素が可視状態まで待つ)
	 * @param selector: CSSセレクタ
	 * @param value   : optionのvalue値
	 */
	public void selectByValue(String selector, String value) {
		if ( _driver instanceof InternetExplorerDriver ||
			 _driver instanceof ChromeDriver) {
			new Select(findElement(selector)).selectByValue(value);
		} else if (_driver instanceof FirefoxDriver) {
			String script = "document.querySelector('" + selector + " option[value=\"" + value + "\"]').selected = true";
			((JavascriptExecutor) _driver).executeScript(script);
		}
	}
	
	/**
	 * 指定されたselect要素をインデックスをもとに選択する(要素が可視状態まで待つ)
	 * @param select : CSSセレクタ
	 * @param index  : 選択したいoptionのインデックス
	 */
	public void selectByIndex(String selector, int index) {
		new Select(findElement(selector)).selectByIndex(index);
	}
	
	/**
	 * 指定された時間待機する
	 * @param millis : 待機時間(ミリ秒)
	 */
	public void waitLoad(long millis) {
		try {
			//System.out.print("waitLoad " + millis + "ms ... ");
			Thread.sleep(millis);
			//System.out.println("end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ページの読込完了まで待機する
	 */
	public void waitForPageLoaded() {
    	System.out.print("waitForPageLoaded ... ");
    	new WebDriverWait(_driver, MAX_WAIT_TIME) { 
    	}.until(new ExpectedCondition<Boolean>() {
        	public Boolean apply(WebDriver driver) {
        		return (Boolean)((JavascriptExecutor) driver)
        				.executeScript("return document.readyState == 'complete'").equals(true);
        	}
    	});
    	waitLoad(2000);
    	System.out.println("end");
    }
    
	/**
	 * SARのページの読込完了まで待機する 
	 */
    protected void waitForSARPageLoaded() {
    	System.out.print("waitForSARPageLoaded ... ");
		if ( _driver instanceof InternetExplorerDriver ||
			 _driver instanceof ChromeDriver) {
	    	new WebDriverWait(_driver, MAX_WAIT_TIME) { 
	    	}.until(new ExpectedCondition<Boolean>() {
	        	public Boolean apply(WebDriver driver) {
	        		return (Boolean)((JavascriptExecutor) driver)
	        				.executeScript("return document.readyState == 'complete' && document.title != 'Trustpro 1.0 KUMADE'").equals(true);
	        	}
	    	});
    	
		} else if (_driver instanceof FirefoxDriver) {
	    	_wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dctp_footer")));
 		}

    	waitLoad(2000);
    	System.out.println("end");
    }
	
	/** 
	 * alert表示時、OKを選択する 
	 */
	public void alertAccept() {
		waitLoad(1000);
		_wait.until(ExpectedConditions.alertIsPresent()).accept();
	}
	
	/** 
	 * 現在のURLを取得する 
	 */	
	public String getCurrentUrl() {
		return _driver.getCurrentUrl();
	}
	
	/**
	 * 現在のページタイトルを取得する 
	 */	
	public String getTitle() {
		return _driver.getTitle();
	}
	
	/** 
	 * 指定された要素へフォーカスを当てる
	 * @param selector : CSSセレクタ
	 */
	public void foucs(String selector) {
		findElement(selector).sendKeys(Keys.CONTROL);
	}
	
	/** 
	 * ブラウザを閉じ処理を終了する 
	 */
	public void quit() {
		_driver.quit();
	}
	
	public static boolean invalidBlowserName(String blowserName) {
		if(blowserName == null) return false;
		
		for(String b : BLOWSER_LIST) {
			if(b.toLowerCase().equals(blowserName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
