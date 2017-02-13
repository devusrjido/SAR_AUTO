package sar.scenario;

import java.util.ArrayList;
import java.util.List;

import library.DateStringUtil;
import main.ScenarioParamKey;
import sar.bean.SagyouKeikaku;
import sar.page.common.SARPageHeader;
import sar.page.form.SagyouListForm;
import sar.page.shuuhou.keikaku.ShuuhouKeikakuDetailsPage;
import sar.page.shuuhou.keikaku.ShuuhouKeikakuListPage;
import scenario.Scenario;
import scenario.ScenarioPameter;
import scenario.data.ScenarioDataHolder;

/** 
 * 週報計画(登録)シナリオクラス  
 *　SARにて週報計画(登録)を行う 
 */
public class ShuuhouKeikakuRegisterScenario implements Scenario {
	private ScenarioPameter _params;
	
	public ShuuhouKeikakuRegisterScenario(ScenarioPameter params) {
		_params = params;
	}
	
	public void start() {
		// シナリオパラメータを取得
		List<SagyouKeikaku> sagyouKeikakuRecordList = 
				(List<SagyouKeikaku>)_params.getObject(ScenarioParamKey.SAGYOU_KEIKAKU_REGISTER_RECORD);
		
		// 現在のページを取得
		ScenarioDataHolder dataHolder = ScenarioDataHolder.getInstance();
		Object currentPage = dataHolder.getObject(ScenarioParamKey.CURRENT_PAGE);
		
		ShuuhouKeikakuListPage keikakuListPage = null;
		if ( currentPage instanceof ShuuhouKeikakuListPage ) {
			keikakuListPage = (ShuuhouKeikakuListPage)currentPage;
		} else {
			SARPageHeader header = (SARPageHeader)currentPage;
			keikakuListPage = header.navigateToShuuhouKeikakuListPage();
		}
		
		System.out.println("週報計画(登録)を開始します。");
		
		// 未作成日付のリンクをクリックし、週報計画(詳細)ページへ遷移
		ShuuhouKeikakuDetailsPage keikakuDetailsPage = keikakuListPage.clickUncreatedDateLink();
		
		// 作業一覧(計画)の日付リストを取得
		List<String> dateList = keikakuDetailsPage.getDateList();
		
		// 月曜 ～ 日曜まで繰り返す
		for (String date : dateList) {
		    String weekStr = "(" + DateStringUtil.getJapaneseWeekName(date) + ")";
		    
		    // 対象日のレコードを抽出
		    List<SagyouKeikaku> dailySagyouKeikakuRecordList = extractDailySagyouKeikakuRecord(sagyouKeikakuRecordList, date);
		    
		    int dailySagyouKeikakuRecordListSize = dailySagyouKeikakuRecordList.size();
		    
		    // レコードが存在しない
		    if(dailySagyouKeikakuRecordListSize == 0) {
		    	System.out.println(date + weekStr + "はレコードが存在しません。");
		    	continue;
		    }
		    
		    // レコードが存在しないかつ、平日の祝日はスキップ
		    if(dailySagyouKeikakuRecordListSize == 0 && !DateStringUtil.isHoliday(date) && DateStringUtil.isPublicHoliday(date)) {
		    	System.out.println(date + weekStr + "はレコード0件、かつ祝日(" + 
		    			DateStringUtil.getPublicHolidayName(date) + ")なのでスキップします。");
		    	continue;
		    }

		    // レコードが存在しないかつ、休日かつ
		    if(dailySagyouKeikakuRecordListSize == 0 && DateStringUtil.isHoliday(date)) {
		    	System.out.println(date + weekStr + "はレコード0件、かつ休日なのでスキップします。");
		    	continue;
		    }
		    
		    // 日付をクリックし、日ごとの作成画面へ遷移
		    keikakuDetailsPage.clickDailyDateLink(date);
		    
		    // レコードが存在する場合、作業計画一覧へ追加
		    if(dailySagyouKeikakuRecordListSize > 0) {
		    	System.out.println(date + weekStr + "のレコードを" + dailySagyouKeikakuRecordListSize + "件登録します。");
		    	
		    	// 作業計画一覧フォーム取得
				SagyouListForm sagyouListKeikakuForm = keikakuDetailsPage.getSagyouListKeikakuForm();
				
				// フォームへレコードを追加
		    	for(SagyouKeikaku record : dailySagyouKeikakuRecordList) {
					sagyouListKeikakuForm.addRecord(record);
		    	}
		    }
		    
			// "勤務計画保存"ボタンを押下
		    keikakuDetailsPage.clickKinmuKeikakuSaveButton();
			
			// 作業計画未追加時に表示される確認aleart対応
		    if(dailySagyouKeikakuRecordListSize == 0) {
		    	keikakuDetailsPage.alertAccept();
		    }
		}
		
		// "申請"ボタンを押下
		currentPage = keikakuDetailsPage.clickRequestButton();
		
		System.out.println("週報計画(登録)が完了しました。");
		
		// 現在のページを更新
		dataHolder.setValue(ScenarioParamKey.CURRENT_PAGE, currentPage);
	}
	
	/**
	 * 指定された勤務日に該当するレコードを抽出します。
	 * @param sagyouKeikakuRecordList : 作業実績レコードリスト
	 * @param kinmuDate : 勤務日
	 * @return 指定された勤務日に該当するレコードリスト
	 */
	public List<SagyouKeikaku> extractDailySagyouKeikakuRecord(List<SagyouKeikaku> sagyouKeikakuRecordList, String kinmuDate) {
		List<SagyouKeikaku> list = new ArrayList<SagyouKeikaku>();
		
		for (SagyouKeikaku record : sagyouKeikakuRecordList) {
			if (record.getSagyouDate().equals(kinmuDate)) {
				list.add(record);
			}
		}
		
		return list;
	}

	public void error() {}
	
	public void always() {}
	
	public String getScenarioName(){
		return "週報計画(登録)";
	}
}