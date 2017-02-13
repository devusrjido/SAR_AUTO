package sar.page.form;

import org.openqa.selenium.WebDriver;

import library.WebDriverUtil;
import main.ProcKind;
import sar.bean.SagyouJisseki;
import sar.bean.SagyouKeikaku;
import sar.page.common.CSSSelector;

/** 作業一覧(計画/実績)フォーム */
public class SagyouListForm extends WebDriverUtil {
	private String _shoriKbnStr;
	private int _index;
	
	public SagyouListForm(WebDriver driver, String shoriKbn){
		super(driver);
		_index = 0;
		
		if (shoriKbn.equals(ProcKind.NIPPOU_REGISTER)) {
			_shoriKbnStr = "jisseki";
		} else if (shoriKbn.equals(ProcKind.SHUUHOU_KEIKAKU_REGISTER)) {
			_shoriKbnStr = "keikaku";
		}		
	}
	
	/**
	 * 作業一覧へレコードを追加します。
	 * @param sagyouKeikakuRecord : 作業計画レコード
	 */
	public void addRecord(SagyouKeikaku sagyouKeikakuRecord) {
		// "追加" ボタン押下
		click(CSSSelector.BTN_ADD);
		waitLoad(1000);
			
		/* 下記項目を入力 */
		// 部門名
		selectByValue(buildSelector(CSSSelector.SELECT_BUMON_NAME), sagyouKeikakuRecord.getBumonName());
		// PJ名
		selectByValue(buildSelector(CSSSelector.SELECT_PROJECT_NAME), sagyouKeikakuRecord.getProjectName());
		// 工程
		selectByValue(buildSelector(CSSSelector.SELECT_PJ_KOUTEI), sagyouKeikakuRecord.getProjectKoutei());
		// 作業時間(時)
		selectByValue(buildSelector(CSSSelector.SELECT_SAGYOU_TIME_HOUR), sagyouKeikakuRecord.getSagyouTimeHour());
		// 作業時間(分)
		selectByValue(buildSelector(CSSSelector.SELECT_SAGYOU_TIME_MIN), sagyouKeikakuRecord.getSagyouTimeMin());
		// タスク
		selectByValue(buildSelector(CSSSelector.SELECT_TASK_ID), sagyouKeikakuRecord.getTaskId());
		// 備考
		typeValue(buildSelector(CSSSelector.TXT_BIKOU), sagyouKeikakuRecord.getBikou());
		
		_index++;
	}
	
	/**
	 * 作業一覧へレコードを追加します。
	 * @param sagyouJissekiRecord : 作業実績レコード
	 */
	public void addRecord(SagyouJisseki sagyouJissekiRecord) {
		// "追加" ボタン押下
		click(CSSSelector.BTN_ADD);
		waitLoad(1000);
			
		/* 下記項目を入力 */
		// 部門名
		selectByValue(buildSelector(CSSSelector.SELECT_BUMON_NAME), sagyouJissekiRecord.getBumonName());
		// PJ名
		selectByValue(buildSelector(CSSSelector.SELECT_PROJECT_NAME), sagyouJissekiRecord.getProjectName());
		// 工程
		selectByValue(buildSelector(CSSSelector.SELECT_PJ_KOUTEI), sagyouJissekiRecord.getProjectKoutei());
		// 作業時間(時)
		selectByValue(buildSelector(CSSSelector.SELECT_SAGYOU_TIME_HOUR), sagyouJissekiRecord.getSagyouTimeHour());
		// 作業時間(分)
		selectByValue(buildSelector(CSSSelector.SELECT_SAGYOU_TIME_MIN), sagyouJissekiRecord.getSagyouTimeMin());
		// タスク
		selectByValue(buildSelector(CSSSelector.SELECT_TASK_ID), sagyouJissekiRecord.getTaskId());
		// 備考
		typeValue(buildSelector(CSSSelector.TXT_BIKOU), sagyouJissekiRecord.getBikou());
		
		_index++;
	}
	
	/**
	 * 要素セレクタを生成する
	 * @param selector : 要素セレクタ
	 * @return 生成後の要素セレクタ
	 */
	private String buildSelector(String[] selector) {
		return selector[0] + _shoriKbnStr + selector[1] + _index; 
	}
}