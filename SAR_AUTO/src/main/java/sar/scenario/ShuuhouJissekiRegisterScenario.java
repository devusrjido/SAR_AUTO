package sar.scenario;

import library.DateStringUtil;
import main.ScenarioParamKey;
import sar.bean.ShuuhouJisseki;
import sar.page.common.SARPageHeader;
import sar.page.shuuhou.jisseki.ShuuhouJissekiDetailsPage;
import sar.page.shuuhou.jisseki.ShuuhouJissekiListPage;
import sar.page.shuuhou.keikaku.ShuuhouKeikakuListPage;
import scenario.Scenario;
import scenario.ScenarioPameter;
import scenario.data.ScenarioDataHolder;

/** 
 * 週報実績(登録)シナリオクラス  
 *　SARにて週報計画(登録)を行う
　*/
public class ShuuhouJissekiRegisterScenario implements Scenario {
	private ScenarioPameter _params;
	
	public ShuuhouJissekiRegisterScenario(ScenarioPameter params) {
		_params = params;
	}
	
	public void start() {
		// シナリオパラメータを取得
		ShuuhouJisseki sagyouJissekiRecord = (ShuuhouJisseki)_params.getObject(ScenarioParamKey.SHUUHOU_JISSEKI_REGISTER_RECORD);
		
		// 現在のページオブジェクトを取得
		ScenarioDataHolder dataHolder = ScenarioDataHolder.getInstance();
		Object currentPage = dataHolder.getObject(ScenarioParamKey.CURRENT_PAGE);
		
		// 
		ShuuhouJissekiListPage jissekiListPage = null;
		if ( currentPage instanceof ShuuhouJissekiListPage ) {
			jissekiListPage = (ShuuhouJissekiListPage)currentPage;
		} else if ( currentPage instanceof ShuuhouKeikakuListPage ) {
			ShuuhouKeikakuListPage keikakuListPage = (ShuuhouKeikakuListPage)currentPage;
			jissekiListPage = keikakuListPage.navigateToShuuhouJissekiListPage();
		} else {
			SARPageHeader header = (SARPageHeader)currentPage;
			ShuuhouKeikakuListPage keikakuListPage = header.navigateToShuuhouKeikakuListPage();
			jissekiListPage = keikakuListPage.navigateToShuuhouJissekiListPage();
		}
				
		// "新規作成"ボタンを押下し、週報実績(詳細)ページへ遷移
		ShuuhouJissekiDetailsPage jissekiDetailsPage = jissekiListPage.clickCreateNewButton();
		
		String date = sagyouJissekiRecord.getKetsugouKinmuDate();
		
		String startDate = DateStringUtil.addDateStringDelimiter(date, "/");
		String lastDate = DateStringUtil.format(
				DateStringUtil.convertDateStringToLocalDateTime(date).plusDays(6), "yyyy/MM/dd");
		
		System.out.println(startDate + "(" + DateStringUtil.getJapaneseWeekName(startDate)+ ") ～  "  + 
						   lastDate + "(" + DateStringUtil.getJapaneseWeekName(lastDate)+ ")の週報実績を登録します。");
		
		/* 下記項目を入力 */
		// 作業開始日
		jissekiDetailsPage.typeKetsugouKinmuDate(date);
		// 評価
		jissekiDetailsPage.selectJissekiHyouka(sagyouJissekiRecord.getJissekiHyouka());
		// 評価理由
		jissekiDetailsPage.typeHyoukaRiyuu(sagyouJissekiRecord.getHyoukaRiyuu());
		
		// "保存" ボタン押下
		jissekiDetailsPage.clickSaveButton();
		
		// "申請"ボタンを押下
		currentPage = jissekiDetailsPage.clickApplyJisseki();
		
		System.out.println("週報計画(登録)が完了しました。");
		
		// 現在のページを更新
		dataHolder.setValue(ScenarioParamKey.CURRENT_PAGE, currentPage);
	}

	public void error() {}
	
	public void always() {}
	
	public String getScenarioName(){
		return "週報実績(登録)";
	}
}