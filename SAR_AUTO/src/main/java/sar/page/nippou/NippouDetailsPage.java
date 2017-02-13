package sar.page.nippou;

import org.openqa.selenium.WebDriver;

import main.ProcKind;
import sar.page.common.AbstractSARPage;
import sar.page.common.CSSSelector;
import sar.page.form.SagyouListForm;

/** 日報(詳細)ページクラス  */
public class NippouDetailsPage extends AbstractSARPage {	
	public NippouDetailsPage(WebDriver driver){
		super(driver);
	}
	
	/** 
	 * "日付"を入力します。  
	 *　@param value : 入力情報 
	 */
	public void typeKinmuDate(String value) {
		typeValue(CSSSelector.TXT_KINMU_DATE, value);
	}
	
	/**
	 * "勤務種別"を選択します。
	 *　@param value : optionのvalue値
	 */
	public void selectKinmuShubetsu(String value) {
		selectByValue(CSSSelector.SELECT_KINMU_SHUBETSU, value.trim());
	}
	
	/**
	 * "出勤時間(時)"を選択します。
	 *　@param value : optionのvalue値
	 */
	public void selectShukkinTimeHour(String value) {
		selectByValue(CSSSelector.SELECT_SHUKKIN_TIME_HOUR, value);
	}
	
	/**
	 * "出勤時間(分)"を選択します。
	 *　@param value : optionのvalue値
	 */
	public void selectShukkinTimeMin(String value) {
		selectByValue(CSSSelector.SELECT_SHUKKIN_TIME_MIN, value);
	}
	
	/**
	 * "退勤時間(時)"を選択します。
	 *　@param value : optionのvalue値
	 */
	public void selectTaikinTimeHour(String value) {
		selectByValue(CSSSelector.SELECT_TAIKIN_TIME_HOUR, value);
	}
	
	/**
	 * "退勤時間(分)"を選択します。
	 *　@param value : optionのvalue値
	 */
	public void selectTaikinTimeMin(String value) {
		selectByValue(CSSSelector.SELECT_TAIKIN_TIME_MIN, value);
	}

	/** 
	 * "保存"ボタン押下する 
	 */
	public void clickSaveButton() {
		click(CSSSelector.BTN_SAVE);
	    alertAccept();
	    
	    waitForSARPageLoaded();
	}

	/** 
	 *　"登録"ボタン押下する 
	 */
	public NippouListPage clickRegisterButton() {
		click(CSSSelector.BTN_REGISTER);
		alertAccept();
		
		waitForSARPageLoaded();
		return new NippouListPage(_driver);
	}
	
	/**
	 * 作業一覧(実績)フォームを取得する
	 * @return 作業一覧(実績)フォーム
	 */
	public SagyouListForm getSagyouListJissekiForm() {
		return new SagyouListForm(_driver, ProcKind.NIPPOU_REGISTER);
	}
}