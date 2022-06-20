# Github Api Wrapper for Jenkins

このライブラリはJenkinsからgithubの操作を行うための [GitHub API for Java
](https://github-api.kohsuke.org/)の薄いラッパーです。

## 主なできること

[GitHub API for Java
](https://github-api.kohsuke.org/)の機能のうち、Jenkinsでよく使う機能を重点的に実装しています。
もし、実装以上に機能が必要であれば、ライブラリをカスタムするか、rawプロパティでGHObjectを取得してください。
(PullRequest出してもいいのよ)

詳しくは [API Document](docs/index.html)へ
 * リポジトリ
    * リポジトリの作成 / 削除
    * ラベルの作成 / 削除
    * コミット間の差分を取得
 * PullRequest
    * 作成
    * マージ
    * コメント 

その他

# 設定

## System Configuration 

1. GitHub > GitHub Servers

Github Serverを追加する。
詳しくは[GitHub Plugin](https://plugins.jenkins.io/github/)

2. Global Pipeline Libraries

このリポジトリのURLまたはフォークしたリポジトリを設定する。

3. Jenkinsfileを書く
4. https://github.com/Marimoiro/jenkins-github-api-wrapper にStarをつける

## Samples について

[samples/Info.groovy] に操作するリポジトリの設定が記述されています。
お試しする場合は、これを自分がフォークしたリポジトリに書き換えてください。

# 制限

## CPS対応について

すべてのインスタンスはできれば、1つのscriptブロック内で使用することをおすすめします。
スコープを乗り越えることは出来ますが、テストは不十分です。

## 機能が足らない場合

ほとんどすべてのクラスは(getRaw)メンバでバックエンドの[GitHub API for Java
](https://github-api.kohsuke.org/)のオブジェクトにアクセスできます。

または、このライブラリはとても簡単なコードになっているので実装して、PullRequestを出すことも可能です。

## Starは必須です

ライセンスはMITですが、このライブラリを使用する場合はStarを押してください。
僕が幸せになります。
