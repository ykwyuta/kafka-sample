# テストコード対応表

現時点でのユニットテスト実装状況をまとめた対応表です。

| 分類 | ファイル名 (役割) | テストコード有無 | 備考 |
|---|---|:---:|---|
| **Entry Point** | `KafkasampleApplication.java` | 〇 | `KafkasampleApplicationTests.java` あり。<br>Spring Contextのロードを確認。 |
| **Consumer** | `KafkaConsumerImpl.java` (コンシューマー実装) | 〇 | `KafkaConsumerImplTest.java` あり。<br>Mockitoを使用し、ServiceとAckをモック化。 |
| **Producer** | `KafkaProducerImpl.java` (プロデューサー実装) | 〇 | `KafkaProducerImplTest.java` あり。<br>Mockitoを使用し、KafkaTemplateをモック化。 |
| **Service** | `KafkaProducerServiceImpl.java` (送信サービス) | 〇 | `KafkaProducerServiceImplTest.java` あり。<br>Mockitoを使用し、Producerをモック化。 |
| **Service** | `KafkaConsumerServiceImpl.java` (受信処理サービス) | 〇 | `KafkaConsumerServiceImplTest.java` あり。<br>Mockitoを使用し、UserRepositoryをモック化。 |
| **Repository** | `UserRepositoryImpl.java` (リポジトリ実装) | 〇 | `UserRepositoryImplTest.java` あり。<br>Mockitoを使用し、UserMapperをモック化。 |
| **Mapper** | `UserMapper.java` (MyBatis Mapper) | 〇 | `UserMapperTest.java` あり。<br>`@MybatisTest`を使用し、H2データベースを用いたテスト。 |

## 概要
*   **現状**: READMEに記載されている主要なクラスに対するテストコードを一通り作成しました。
*   **不足**: 特になし。必要に応じて詳細な異常系テストやE2Eテストを追加してください。
