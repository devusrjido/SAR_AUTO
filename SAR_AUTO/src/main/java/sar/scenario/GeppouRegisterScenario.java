package sar.scenario;

import main.ScenarioParamKey;
import sar.page.common.SARPageHeader;
import sar.page.geppou.GeppouDetailsPage;
import sar.page.geppou.GeppouListPage;
import scenario.Scenario;
import scenario.ScenarioPameter;
import scenario.data.ScenarioDataHolder;

/**
 * 月報(登録)シナリオクラス  
 * SARにて月報(登録)・月報エクスポートを行う 
 */
public class GeppouRegisterScenario implements Scenario {
	private ScenarioPameter _params;
	
	public GeppouRegisterScenario(ScenarioPameter params) {
		_params = params;
	}
	
	public GeppouRegisterScenario() {
	}
	
	public void start() {		
		// 現在のページを取得
		ScenarioDataHolder dataHolder = ScenarioDataHolder.getInstance();
		Object currentPage = dataHolder.getObject(ScenarioParamKey.CURRENT_PAGE);
		
		GeppouListPage geppouListPage = null;
		if ( currentPage instanceof GeppouListPage) {
			geppouListPage = (GeppouListPage)currentPage;
		} else {
			SARPageHeader header = (SARPageHeader)currentPage;
			geppouListPage = header.navigateToGeppouListPage();
		}
		
		System.out.println("月報(登録)を開始します。");
		
		// 未作成のリンクを押下し、月報(詳細)ページへ遷移
		GeppouDetailsPage geppouDetailsPage = geppouListPage.clickUncreatedDateLink();
				
		// 保存ボタンを押下
		geppouDetailsPage.clickSaveButton();
		// 月締ボタンを押下
		geppouListPage = geppouDetailsPage.clickTsukijimeButton();
		
		System.out.println("月報(登録)が完了しました。");
		
		System.out.println("月報をエクスポートを開始します。");
		// 月締後、もう一度月報(詳細)ページへ遷移
		geppouDetailsPage = geppouListPage.clickUncreatedDateLink();
		// Excelを選択
		geppouDetailsPage.selectAction(GeppouDetailsPage.SELECT_ACTION_EXCEL);
		// 実行ボタンを押下
		geppouDetailsPage.clickActionButton();
		System.out.println("月報をエクスポートが完了しました。");
		
		// 現在のページを更新
		dataHolder.setValue(ScenarioParamKey.CURRENT_PAGE, geppouDetailsPage);
	}

	public void error() {}
	
	public void always() {}
	
	public String getScenarioName(){
		return "月報(登録)・月報エクスポート";
	}
}