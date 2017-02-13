package sar.scenario;

import java.util.ArrayList;
import java.util.List;

import library.DateStringUtil;
import main.ScenarioParamKey;
import sar.bean.Nippou;
import sar.bean.SagyouJisseki;
import sar.page.common.SARPageHeader;
import sar.page.form.SagyouListForm;
import sar.page.nippou.NippouDetailsPage;
import sar.page.nippou.NippouListPage;
import scenario.Scenario;
import scenario.ScenarioPameter;
import scenario.data.ScenarioDataHolder;

/**
 * 日報(登録)シナリオクラス  
 * SARにて日報(登録)を行う 
 */
public class NippouRegisterScenario implements Scenario {
	private ScenarioPameter _params;
	
	public NippouRegisterScenario(ScenarioPameter params) {
		_params = params;
	}
	
	public void start() {
		// シナリオパラメータを取得
		List<Nippou> nippouRecordList = 
				(List<Nippou>)_params.getObject(ScenarioParamKey.NIPPOU_REGISTER_RECORD);
		
		List<SagyouJisseki> sagyouJissekiRecordList = 
				(List<SagyouJisseki>)_params.getObject(ScenarioParamKey.SAGYOU_JISSEKI_REGISTER_RECORD);
		
		// 現在のページオブジェクトを取得
		ScenarioDataHolder dataHolder = ScenarioDataHolder.getInstance();
		Object currentPage = dataHolder.getObject(ScenarioParamKey.CURRENT_PAGE);
				
		NippouListPage nippouListPage = null;
		if ( currentPage instanceof NippouListPage ) {
			nippouListPage = (NippouListPage)currentPage;
		} else {
			SARPageHeader header = (SARPageHeader)currentPage;
			nippouListPage = header.navigateToNippouListPage();
		}
		
		for(Nippou nippouRecord : nippouRecordList) {
			String kinmuDate = nippouRecord.getKinmuDate();
			String weekStr = "(" + DateStringUtil.getJapaneseWeekName(kinmuDate) + ")";
			
		    // 作業計画(実績)から日報の日付に対応するレコードを抽出
			List<SagyouJisseki> dailySagyouJissekiRecordList = 
					extractDailySagyouJissekiRecord(sagyouJissekiRecordList, kinmuDate);
		     
		    int jissekiRecordSize = dailySagyouJissekiRecordList.size();
		    
		    // レコードが存在しないかつ、祝日はスキップ
		    if(jissekiRecordSize == 0 && !DateStringUtil.isHoliday(kinmuDate) && DateStringUtil.isPublicHoliday(kinmuDate)) {
		    	System.out.println(kinmuDate + weekStr + "はレコード0件、かつ祝日(" + 
		    			DateStringUtil.getPublicHolidayName(kinmuDate) + ")なのでスキップします。");
		    	continue;
		    }

		    // レコードが存在しないかつ、休日かつ
		    if(jissekiRecordSize == 0 && DateStringUtil.isHoliday(kinmuDate)) {
		    	System.out.println(kinmuDate + weekStr + "はレコード0件、かつ休日なのでスキップします。");
		    	continue;
		    }
			
			// 新規作成ボタンを押下し、日報(詳細)ページへ遷移
			NippouDetailsPage nippouDetailsPage = nippouListPage.clickCreateNewButton();
			
			/* 下記項目を入力 */
			// 日付
			nippouDetailsPage.typeKinmuDate(kinmuDate);
			// 勤務種別
			nippouDetailsPage.selectKinmuShubetsu(nippouRecord.getKinmuShubetsuView());
			
			// "保存" ボタン押下
			nippouDetailsPage.clickSaveButton();
			
		    // レコードが存在する場合、作業計画一覧へ追加
		    if(jissekiRecordSize != 0) {
		    	System.out.println(kinmuDate + weekStr + "のレコードを" + jissekiRecordSize + "件登録します。");
				// 出勤時間(時)
		    	nippouDetailsPage.selectShukkinTimeHour(nippouRecord.getShukkinTimeHour());
				// 出勤時間(分)
		    	nippouDetailsPage.selectShukkinTimeMin(nippouRecord.getShukkinTimeMin());
				// 退勤時間(時)
		    	nippouDetailsPage.selectTaikinTimeHour(nippouRecord.getTaikinTimeHour());
				// 退勤時間(分)
		    	nippouDetailsPage.selectTaikinTimeMin(nippouRecord.getTaikinTimeMin());

				// 作業実績一覧フォーム取得
		    	SagyouListForm sagyouListJissekiForm = nippouDetailsPage.getSagyouListJissekiForm();
		    	
				// フォームへレコードを追加
				for (SagyouJisseki sagyouJissekiRecord : dailySagyouJissekiRecordList) {
					sagyouListJissekiForm.addRecord(sagyouJissekiRecord);
				}
		    }

			// "登録" ボタン押下
		    currentPage = nippouDetailsPage.clickRegisterButton();
		    
			// 現在のページを更新
			dataHolder.setValue(ScenarioParamKey.CURRENT_PAGE, currentPage);
		}
	}
	
	/**
	 * 指定された勤務日に該当するレコードを抽出します。
	 * @param sagyouJissekiRecordList : 作業実績レコードリスト
	 * @param kinmuDate : 勤務日
	 * @return 指定された勤務日に該当するレコードリスト
	 */
	public List<SagyouJisseki> extractDailySagyouJissekiRecord(List<SagyouJisseki> sagyouJissekiRecordList, String kinmuDate) {
		List<SagyouJisseki> list = new ArrayList<SagyouJisseki>();
		
		for (SagyouJisseki record : sagyouJissekiRecordList) {
			if (record.getSagyouDate().equals(kinmuDate)) {
				list.add(record);
			}
		}
		
		return list;
	}

	public void error() {}
	
	public void always() {}
	
	public String getScenarioName(){
		return "日報(登録)";
	}
}