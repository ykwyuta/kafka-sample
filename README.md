# Kafka Sample Project

このプロジェクトは、Spring Bootを使用してApache Kafkaと連携するサンプルアプリケーションです。Avroスキーマを使用したメッセージのシリアライズ・デシリアライズ、プロデューサーによるバッチメッセージ送信、およびコンシューマーによるバッチ受信と手動確認（Acknowledgment）、H2データベースへの保存を実証しています。

## ディレクトリ構造

主要なディレクトリとファイルの構成は以下の通りです。

```
d:\workspace\kafkasample\
├── src\
│   ├── main\
│   │   ├── avro\
│   │   │   └── user.avsc                      # Avroスキーマ定義
│   │   ├── java\
│   │   │   └── com\example\kafkasample\
│   │   │       ├── KafkasampleApplication.java # アプリケーションのエントリーポイント
│   │   │       ├── consumer\
│   │   │       │   ├── KafkaConsumer.java     # コンシューマーのインターフェース
│   │   │       │   └── impl\
│   │   │       │       └── KafkaConsumerImpl.java # コンシューマーの実装 (バッチ受信・手動Ack)
│   │   │       ├── producer\
│   │   │       │   ├── KafkaProducer.java     # プロデューサーのインターフェース
│   │   │       │   └── impl\
│   │   │       │       └── KafkaProducerImpl.java # プロデューサーの実装 (送信ロジック)
│   │   │       ├── service\
│   │   │       │   ├── KafkaConsumerService.java
│   │   │       │   ├── KafkaProducerService.java
│   │   │       │   └── impl\
│   │   │       │       ├── KafkaConsumerServiceImpl.java # 受信データ処理（DB保存など）
│   │   │       │       └── KafkaProducerServiceImpl.java # 定期実行される送信サービス
│   │   │       ├── repository\
│   │   │       │   ├── UserMapper.java        # MyBatis Mapper
│   │   │       │   └── UserRepository.java    # データアクセスリポジトリ
│   │   └── resources\
│   │       └── application.properties         # Kafka接続設定、DB設定など
├── pom.xml                                    # Maven依存関係定義 (Avro, Kafka, MyBatis, H2)
├── docker-compose.yml                         # Kafka環境などのコンテナ構成定義
└── HELP.md                                    # Spring Boot生成時のヘルプ
```

## 各ファイルの役割

### 1. Avro Schema (`src/main/avro/`)
*   **user.avsc**: `User` オブジェクトのデータ構造を定義したAvroスキーマファイルです。`name` (String), `favorite_number` (int), `favorite_color` (String) などのフィールドを持ちます。ビルド時にJavaクラスが自動生成されます。

### 2. Consumer (`com.example.kafkasample.consumer`)
*   **KafkaConsumer.java**: コンシューマー処理のインターフェース定義です。
*   **KafkaConsumerImpl.java**: `KafkaConsumer` の実装クラスです。
    *   `@KafkaListener`: Batch Listenerとして動作し、`List<User>` を受け取ります。
    *   `Acknowledgment`: 手動でオフセットのコミット（Ack）を行います (`manual_immediate`)。
    *   受信したメッセージリストの処理は `KafkaConsumerService` に委譲します。

### 3. Producer (`com.example.kafkasample.producer`)
*   **KafkaProducer.java**: メッセージ送信のインターフェース定義です。
*   **KafkaProducerImpl.java**: `KafkaProducer` の実装クラスです。
    *   `KafkaTemplate` を使用して、Avro形式の `User` オブジェクトを "user" トピックに送信します。非同期送信を行い、`CompletableFuture` を返します。

### 4. Service (`com.example.kafkasample.service`)
*   **KafkaProducerService.java**: メッセージ送信を管理するサービスのインターフェースです。
*   **KafkaProducerServiceImpl.java**: `KafkaProducerService` の実装クラスです。
    *   `@Scheduled(fixedRate = 10)`: 10ミリ秒ごとに定期的に実行されます。
    *   1回の実行につき20件のメッセージを生成し、`KafkaProducer` を通じて並列送信します。全ての送信完了 (`CompletableFuture.allOf`) を待機します。
    *   ランダムなデータ（UUIDのName、ランダムな色、数値）を持つ `User` オブジェクトを生成します。
    *   起動から10分経過すると送信を停止するロジックが含まれています。
*   **KafkaConsumerService.java**: メッセージ受信処理を管理するサービスのインターフェースです。
*   **KafkaConsumerServiceImpl.java**: `KafkaConsumerService` の実装クラスです。
    *   `KafkaConsumerImpl` から受け取った `User` オブジェクトのリストをループ処理し、`UserRepository` を通じてデータベース（H2）に保存します。

### 5. Repository (`com.example.kafkasample.repository`)
*   **UserRepository.java**: データベース操作を行うリポジトリクラスです。MyBatisのMapperを利用してデータの永続化を行います。

### 6. Config (`src/main/resources/`)
*   **application.properties**:
    *   **Kafka**: ブローカーアドレス、Schema Registry URL、シリアライザー設定、バッチリスナー設定 (`batch`, `manual_immediate`) など。
    *   **Database**: H2 Database (In-Memory) の接続設定。
    *   **MyBatis**: マッピング設定など。
