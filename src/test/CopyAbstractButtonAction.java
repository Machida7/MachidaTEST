package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//各ボタンのアクションの抽象クラス
public abstract class CopyAbstractButtonAction extends CopyDBConnection implements ActionListener {
	public abstract void buttonAction();
}

//入館ボタン
class loginButtonAction extends CopyAbstractButtonAction {
	@Override
	public void buttonAction() {
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	}
}

//新しく作るボタン
class openAddUserWindowButtonAction extends CopyAbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}
//新規ユーザー登録ボタン
class addNewUserButtonAction extends CopyAbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//ログイン画面に戻るボタン
class returnLoginPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//PW変更ボタン
class PWChangeButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}
	@Override
	public void buttonAction() {
		// TODO 自動生成されたメソッド・スタブ
	}
}

//本を探すボタン
class openFindBookPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}
	@Override
	public void buttonAction() {
		// TODO 自動生成されたメソッド・スタブ
	}
}

//本を追加するボタン
class openAddBookPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//お姉さんと遊ぶボタン
class openPlayWithWomanPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//本を返却するボタン
class openReturnBookPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//お姉さんのおすすめボタン
class openWomanRecommendationPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//お姉さんの部屋ボタン
class openWomanRoomPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//DBに本を追加するボタン
class addBookToDBButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}

}

//ホーム画面に戻るボタン
class returnHomePanalButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//本を返却するボタン
class returnBookButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {

	}
	@Override
	public void buttonAction() {
	}
}

//フリーワード検索ボタン
class freeWordSearchButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//全ての本を表示ボタン
class allBookListDisplayButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//本を借りるボタン
class borrowBookButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//他のおすすめ表示ボタン
class otherRecommendationDisplayButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//レビューを書くボタン
class openWriteReviewPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//レビュー画面から直前の画面に戻るボタン
class returnPreviousPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//レビューを投稿するボタン
class postReviewButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//レビュー画面に戻るボタン
class returnDisplayReviewPanelButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//ユーザー削除ボタン
class deleteUserButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//ユーザーネーム変更ボタン
class changeUserNameButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//ユーザーPW変更ボタン
class changeUserPWButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//本の情報更新ボタン
class updateBookInfomationButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}

//本の削除ボタン
class deleteBookButtonAction extends CopyAbstractButtonAction{
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void buttonAction() {
	}
}